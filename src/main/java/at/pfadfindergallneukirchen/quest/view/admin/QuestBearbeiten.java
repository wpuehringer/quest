package at.pfadfindergallneukirchen.quest.view.admin;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Quest;

public class QuestBearbeiten extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;

	@SpringBean
	private QuestDao dao;

	private Model<Quest> model;

	public QuestBearbeiten(Quest quest) {
		
		model = new Model<Quest>(quest);

		Form<Lauf> form = new Form<Lauf>("form") {
			private static final long serialVersionUID = 3955920345707074665L;

			@Override
			protected void onSubmit() {
				dao.save(model.getObject());
				setResponsePage(AlleQuests.class);
			}
		};
		form.setOutputMarkupId(true);
		add(form);

		form.add(new TextField<String>("name", new PropertyModel<String>(model, "name")));
		form.add(new TextField<Integer>("anzahlPersonen", new PropertyModel<Integer>(model, "anzahlPersonen")));
		form.add(new TextField<Integer>("punkte", new PropertyModel<Integer>(model, "punkte")));
		form.add(new TextField<Integer>("anzahlInstanzen", new PropertyModel<Integer>(model, "anzahlInstanzen")));
		
	}

}
