package at.pfadfindergallneukirchen.quest.view;

import java.util.Date;
import java.util.LinkedList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.view.component.Navigation;

public class AktuelleLaeufe extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;
	
	@SpringBean
	private QuestDao dao;
	
	public AktuelleLaeufe() {
		
		add(new Navigation("navigation"));
		
		Model<LinkedList<Lauf>> resultModel = new Model<LinkedList<Lauf>>(dao.getAktuelleLaeufe());
		add(new ListView<Lauf>("laufRow", resultModel) {
			private static final long serialVersionUID = -8239266017240842279L;

			@Override
            protected void populateItem(final ListItem<Lauf> item) {
                item.add(new Label("name", new PropertyModel<String>(new PropertyModel<Quest>(item.getModel(), "quest"), "name")));
                item.add(new Label("teilnehmer", new Model<String>(StringUtils.collectionToDelimitedString(item.getModelObject().getTeilnehmer(), ", "))));
                item.add(new Label("start", new PropertyModel<Date>(item.getModel(), "startDatum")));
                item.add(new Link<AktuelleLaeufe>("beenden") {
                	@Override
                	public void onClick() {
                		setResponsePage(new LaufBeenden(item.getModelObject()));
                	}
                });
            }
        });
	}
	
}
