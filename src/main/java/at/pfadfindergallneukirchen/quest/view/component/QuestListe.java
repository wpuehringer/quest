package at.pfadfindergallneukirchen.quest.view.component;

import java.util.LinkedList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.view.LaufErstellen;
import at.pfadfindergallneukirchen.quest.view.VerfuegbareQuests;

public class QuestListe extends Panel {
	private static final long serialVersionUID = -7274476677774168689L;
	
	public QuestListe(String id, final QuestDao dao, int start, int end) {
		super(id);
		
		Model<LinkedList<Quest>> resultModel = new Model<LinkedList<Quest>>(dao.getVerfuegbareQuests(start, end));
		add(new ListView<Quest>("questRow", resultModel) {
			private static final long serialVersionUID = -8239266017240842279L;

			@Override
            protected void populateItem(final ListItem<Quest> item) {
				Link<LaufErstellen> link = new Link<LaufErstellen>("link") {
					private static final long serialVersionUID = -5719798710371709181L;
					
					@Override
					public void onClick() {
						Quest quest = item.getModelObject();
						if (quest.getAnzahlInstanzen() > 0) {
							quest.setAnzahlInstanzen(quest.getAnzahlInstanzen()-1);
							dao.save(quest);
							setResponsePage(new LaufErstellen(item.getModelObject()));
						} else {
							setResponsePage(VerfuegbareQuests.class);
						}
					}
				};
                link.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
                item.add(link);
                item.add(new Label("anzahlGruppen", new PropertyModel<Integer>(item.getModel(), "anzahlGruppen")));
                item.add(new Label("anzahlPersonen", new PropertyModel<Integer>(item.getModel(), "anzahlPersonen")));
                item.add(new Label("punkte", new PropertyModel<Integer>(item.getModel(), "punkte")));
            }
        });
	}
}
