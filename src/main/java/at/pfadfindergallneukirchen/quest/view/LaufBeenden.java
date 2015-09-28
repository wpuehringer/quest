package at.pfadfindergallneukirchen.quest.view;

import java.util.Date;
import java.util.LinkedList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.dbo.Teilnehmer;
import at.pfadfindergallneukirchen.quest.view.component.Navigation;

public class LaufBeenden extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;

	@SpringBean
	private QuestDao dao;

	private Model<Lauf> model;

	private Model<String> passwortModel;

	public LaufBeenden(Lauf lauf) {
		
		
		passwortModel = new Model<String>();
		add(new Navigation("navigation"));
		add(new FeedbackPanel("error"));
		
		Quest quest = lauf.getQuest();
		
		add(new Label("questName", new PropertyModel<String>(quest, "name")));
		add(new Label("questPunkte", new PropertyModel<>(quest, "punkte")));
		model = new Model<Lauf>(lauf);

		Form<Lauf> form = new Form<Lauf>("form") {
			private static final long serialVersionUID = 3955920345707074665L;

			@Override
			protected void onSubmit() {
				Lauf lauf = model.getObject();
				
				Integer punkte = Integer.valueOf(0);
				for (Teilnehmer tn : lauf.getTeilnehmer()) {
					punkte += tn.getPunkte();
				}
				if (punkte > 0) {
					if (!punkte.equals(lauf.getQuest().getPunkte())) {
						error("Es müssen genau '" + lauf.getQuest().getPunkte() + "' Punkte vergeben werden.");
						return;
					}
				}
				
				if (!"sonnenschein".equals(passwortModel.getObject())) {
					error("Passwort falsch!");
					return;
				}
				
				lauf.setEndDatum(new Date());
				dao.save(lauf);
				for (Teilnehmer tn : lauf.getTeilnehmer()) {
					dao.save(tn);
				}
				setResponsePage(VerfuegbareQuests.class);
			}
		};
		
		form.add(new PasswordTextField("passwort", passwortModel));
		form.setOutputMarkupId(true);
		add(form);
		
		Model<LinkedList<Teilnehmer>> resultModel = new Model<LinkedList<Teilnehmer>>(new LinkedList<Teilnehmer>());
		resultModel.getObject().addAll(lauf.getTeilnehmer());
		form.add(new ListView<Teilnehmer>("teilnehmerRow", resultModel) {
			private static final long serialVersionUID = -8239266017240842279L;

			@Override
            protected void populateItem(final ListItem<Teilnehmer> item) {
                item.add(new Label("teilnehmerName", item.getModelObject().toString()));
                item.add(new TextField<Integer>("teilnehmerPunkte", new PropertyModel<Integer>(item.getModel(), "punkte")));
            }
        });

	}

}
