package at.pfadfindergallneukirchen.quest.view;

import java.util.Date;
import java.util.LinkedList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.component.Recorder;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.dbo.Gruppe;
import at.pfadfindergallneukirchen.quest.dbo.Lauf;
import at.pfadfindergallneukirchen.quest.dbo.Person;
import at.pfadfindergallneukirchen.quest.dbo.Quest;
import at.pfadfindergallneukirchen.quest.dbo.Teilnehmer;
import at.pfadfindergallneukirchen.quest.view.component.Navigation;

public class LaufErstellen extends WebPage {
	private static final long serialVersionUID = -7274476677774168689L;

	@SpringBean
	private QuestDao dao;

	private Model<Lauf> model;

	private ListModel<Gruppe> assignedGruppen;

	private ListModel<Person> availablePersonen;
	private ListModel<Person> assignedPersonen;

	private Palette<Person> personen;
	
	private Model<String> passwortModel;

	public LaufErstellen(Quest quest) {
		
		passwortModel = new Model<String>();
		add(new Navigation("navigation"));
		add(new FeedbackPanel("error"));
		
		add(new Label("questName", new PropertyModel<String>(quest, "name")));
		add(new Label("anzahlGruppen", new PropertyModel<>(quest, "anzahlGruppen")));
		add(new Label("anzahl", new PropertyModel<>(quest, "anzahlPersonen")));
		model = new Model<Lauf>(new Lauf());
		model.getObject().setQuest(quest);

		Form<Lauf> form = new Form<Lauf>("form") {
			private static final long serialVersionUID = 3955920345707074665L;

			@Override
			protected void onSubmit() {

				Lauf lauf = model.getObject();
				
				if (!"sonnenschein".equals(passwortModel.getObject())) {
					error("Passwort falsch!");
					return;
				}
				
				if (assignedGruppen.getObject().size() < lauf.getQuest().getAnzahlGruppen()) {
					error("Für diesen Quest sind mindestens '" + lauf.getQuest().getAnzahlGruppen() + "' Gruppen erforderlich");
					return;
				}
				
				if (assignedPersonen.getObject().size() != lauf.getQuest().getAnzahlPersonen()) {
					error("Für diesen Quest sind genau '" + lauf.getQuest().getAnzahlPersonen() + "' Teilnehmer erforderlich");
					return;
				}
					
				lauf.setTeilnehmer(new LinkedList<Teilnehmer>());
				lauf.setStartDatum(new Date());
				dao.save(lauf);
				for(Person person : assignedPersonen.getObject()) {
					Teilnehmer teilnehmer = new Teilnehmer();
					teilnehmer.setPerson(person);
					teilnehmer.setLauf(lauf);
					teilnehmer.setPunkte(0);
					dao.save(teilnehmer);
					lauf.getTeilnehmer().add(teilnehmer);
				}
				setResponsePage(VerfuegbareQuests.class);
			}
			
		};
		form.add(new PasswordTextField("passwort", passwortModel));
		form.setOutputMarkupId(true);
		add(form);

		ListModel<Gruppe> available = new ListModel<>(dao.getGruppen());
		assignedGruppen = new ListModel<>(new LinkedList<Gruppe>());
		IChoiceRenderer<Gruppe> renderer = new ChoiceRenderer<Gruppe>("name", "name");

		final Palette<Gruppe> gruppen = new Palette<Gruppe>("gruppen", assignedGruppen, available, renderer, 10, false) {
			private static final long serialVersionUID = -1573326546015146089L;

			@Override
		    protected Recorder<Gruppe> newRecorderComponent() {
		      Recorder<Gruppe> recorder = super.newRecorderComponent();     
		      recorder.add(new AjaxFormComponentUpdatingBehavior("onchange") {
				private static final long serialVersionUID = -6814601962323056883L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					availablePersonen.setObject(dao.getPersonenForLauf(assignedGruppen.getObject()));
					target.add(personen);
				}
		      });
		      return recorder;
		    }
		};
		form.add(gruppen);
		
		availablePersonen = new ListModel<Person>(new LinkedList<Person>());
		assignedPersonen = new ListModel<Person>(new LinkedList<Person>());
		IChoiceRenderer<Person> personRenderer = new ChoiceRenderer<Person>("nachname", "nachname") {
			private static final long serialVersionUID = -1385596648997951762L;

			@Override
			public Object getDisplayValue(Person object) {
				return object.toString();
			}
		};

		personen = new Palette<Person>("personen", assignedPersonen, availablePersonen, personRenderer, 10, false);
		personen.setOutputMarkupId(true);
		form.add(personen);
	}

}
