package at.pfadfindergallneukirchen.quest.view;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.view.component.Navigation;
import at.pfadfindergallneukirchen.quest.view.component.StandListe;

public class AktuellerStand extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;
	
	@SpringBean
	private QuestDao dao;
	
	public AktuellerStand() {
		
		add(new Navigation("navigation"));
		
		add(new StandListe("liste1", dao, 0, 20));
		add(new StandListe("liste2", dao, 20, 40));
		
		add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(30)));
	}
	
}
