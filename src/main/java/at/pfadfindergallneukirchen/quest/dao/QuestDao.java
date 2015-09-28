package at.pfadfindergallneukirchen.quest.dao;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;

import at.pfadfindergallneukirchen.quest.dbo.Gruppe;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Person;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.dbo.Teilnehmer;


public interface QuestDao {

	public void setMessageSource(MessageSource messageSource);
	public void setSessionFactory(SessionFactory sessionFactory);
	
	public LinkedList<Lauf> getAktuelleLaeufe();
	LinkedList<Teilnehmer> getAktuelleTeilnehmer();
	public LinkedList<Gruppe> getGruppen();
	public void save(Serializable object);
	public LinkedList<Person> getPersonen(List<Gruppe> object);
	public LinkedList<Person> getPersonenForLauf(List<Gruppe> object);
	public LinkedList<Object[]> getStand(int start, int end);
	public LinkedList<Quest> getQuests();
	public LinkedList<Quest> getVerfuegbareQuests(int start, int end);
}
