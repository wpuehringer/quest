package at.pfadfindergallneukirchen.quest.view.admin;

import java.util.LinkedList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Quest;

public class AlleQuests extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;

	@SpringBean
	private QuestDao dao;

	public AlleQuests() {

		Model<LinkedList<Quest>> resultModel = new Model<LinkedList<Quest>>(dao.getQuests());
		add(new ListView<Quest>("questRow", resultModel) {
			private static final long serialVersionUID = -8239266017240842279L;

			@Override
			protected void populateItem(final ListItem<Quest> item) {
				Link<QuestBearbeiten> link = new Link<QuestBearbeiten>("bearbeiten") {
					private static final long serialVersionUID = -7069647227164379748L;

					@Override
					public void onClick() {
						setResponsePage(new QuestBearbeiten(item.getModelObject()));
					}
				};
				item.add(link);
				link.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("anzahlPersonen", new PropertyModel<Integer>(item.getModel(), "anzahlPersonen")));
				item.add(new Label("punkte", new PropertyModel<Integer>(item.getModel(), "punkte")));
			}
		});

		add(new Link<QuestBearbeiten>("neu") {
			private static final long serialVersionUID = 1308699932531899114L;

			@Override
			public void onClick() {
				setResponsePage(new QuestBearbeiten(new Quest()));
			}
		});
	}

}
