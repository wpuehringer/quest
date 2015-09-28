package at.pfadfindergallneukirchen.quest.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.MessageSource;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Gruppe;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Person;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.dbo.Teilnehmer;

public class QuestDaoImpl implements QuestDao {

	private MessageSource messageSource;
	private SessionFactory sessionFactory;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public LinkedList<Quest> getVerfuegbareQuests(int start, int end) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Quest.class);
		criteria.add(Restrictions.gt("anzahlInstanzen", Integer.valueOf(0)));
		criteria.addOrder(Order.desc("anzahlInstanzen"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(end-start);
		return new LinkedList<>(criteria.list());
	}
	
	@Override
	public LinkedList<Quest> getQuests() {
		return new LinkedList<>(sessionFactory.getCurrentSession().createCriteria(Quest.class).addOrder(Order.asc("name")).list());
	}

	@Override
	public LinkedList<Lauf> getAktuelleLaeufe() {
		return new LinkedList<>(sessionFactory.getCurrentSession().createCriteria(Lauf.class).add(Restrictions.isNull("endDatum")).list());
	}

	@Override
	public LinkedList<Teilnehmer> getAktuelleTeilnehmer() {
		return new LinkedList<>(sessionFactory.getCurrentSession().createCriteria(Teilnehmer.class).createCriteria("lauf").add(Restrictions.isNull("endDatum")).list());
	}
	
	@Override
	public LinkedList<Gruppe> getGruppen() {
		return new LinkedList<>(sessionFactory.getCurrentSession().createCriteria(Gruppe.class).addOrder(Order.asc("name")).list());
	}

	@Override
	public void save(Serializable object) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(object);
	}

	@Override
	public LinkedList<Person> getPersonen(List<Gruppe> gruppen) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Person.class);
		if (!gruppen.isEmpty()) {
			crit.add(Restrictions.in("gruppe", gruppen));
		}
		return new LinkedList<>(crit.addOrder(Order.asc("nachname")).addOrder(Order.asc("vorname")).list());
	}
	
	@Override
	public LinkedList<Person> getPersonenForLauf(List<Gruppe> gruppen) {
		LinkedList<Person> personen = getPersonen(gruppen);
		LinkedList<Teilnehmer> teilnehmer = getAktuelleTeilnehmer();
		if (!teilnehmer.isEmpty()) {
			for (Iterator<Person> iterator = personen.iterator(); iterator.hasNext();) {
				Person person = (Person) iterator.next();
				if (CollectionUtils.containsAny(person.getTeilnehmer(), teilnehmer)) {
					iterator.remove();
				}
			}
		}
		return personen;
	}
	
	@Override
	public LinkedList<Object[]> getStand(int start, int end) {
		SQLQuery crit = sessionFactory.getCurrentSession().createSQLQuery("SELECT g.name name, count( t.punkte ) anzahl, sum( t.punkte ) / g.anzahl punkte FROM `lauf` l JOIN `quest` q ON l.que_oid = q.oid JOIN `teilnehmer` t ON t.lau_oid = l.oid JOIN `person` p ON p.oid = t.per_oid JOIN `gruppe` g ON g.oid = p.grp_oid where l.endDatum is not null GROUP BY g.name order by sum(t.punkte) / g.anzahl desc");
		crit.addScalar("name", StandardBasicTypes.STRING);
		crit.addScalar("anzahl", StandardBasicTypes.LONG);
		crit.addScalar("punkte", StandardBasicTypes.LONG);
		
		crit.setFirstResult(start);
		crit.setMaxResults(end-start);
		
		return new LinkedList<>(crit.list());
	}
}
