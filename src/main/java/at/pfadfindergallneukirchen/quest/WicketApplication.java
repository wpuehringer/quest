package at.pfadfindergallneukirchen.quest;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import at.pfadfindergallneukirchen.quest.view.AktuelleLaeufe;
import at.pfadfindergallneukirchen.quest.view.AktuellerStand;
import at.pfadfindergallneukirchen.quest.view.VerfuegbareQuests;
import at.pfadfindergallneukirchen.quest.view.admin.AlleQuests;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see at.pfadfindergallneukirchen.quest.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<AktuellerStand> getHomePage() {
		return AktuellerStand.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		// add your configuration here
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		mountPage("verfuegbar", VerfuegbareQuests.class);
		mountPage("aktuell", AktuelleLaeufe.class);
		mountPage("stand", AktuellerStand.class);
		
		mountPage("admin" , AlleQuests.class);
	}
}
