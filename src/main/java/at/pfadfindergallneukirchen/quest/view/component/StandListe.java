package at.pfadfindergallneukirchen.quest.view.component;

import java.util.LinkedList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;

public class StandListe extends Panel {
	private static final long serialVersionUID = -7274476677774168689L;
	
	public StandListe(String id, QuestDao dao, int start, int end) {
		super(id);
		
		Model<LinkedList<Object[]>> resultModel = new Model<LinkedList<Object[]>>(dao.getStand(start, end));
		add(new ListView<Object[]>("standRow", resultModel) {
			private static final long serialVersionUID = -8239266017240842279L;

			@Override
            protected void populateItem(final ListItem<Object[]> item) {
				String name = (String) item.getModelObject()[0];
				Long anzahl = (Long) item.getModelObject()[1];
				Long punkte = (Long) item.getModelObject()[2];
                
				item.add(new Label("name", new Model<String>(name)));
				item.add(new Label("anzahl", new Model<Long>(anzahl)));
				item.add(new Label("punkte", new Model<Long>(punkte)));
            }
        });
	}
	
}
