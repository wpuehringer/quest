package at.pfadfindergallneukirchen.quest.view;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.view.component.Navigation;
import at.pfadfindergallneukirchen.quest.view.component.QuestListe;

public class VerfuegbareQuests extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;
	
	@SpringBean
	private QuestDao dao;
	
	public VerfuegbareQuests() {
		
		add(new Navigation("navigation"));
		
		add(new QuestListe("liste1", dao, 0, 20));
//		add(new QuestListe("liste2", dao, 20, 40));
		
		add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(30)));
	}
	
}
