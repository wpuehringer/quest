package at.pfadfindergallneukirchen.quest.dbo;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Gruppe implements Serializable {
	private static final long serialVersionUID = 2795692927706027614L;
	
	@Id
	private Long oid;
	private String name;
	
	@OneToMany(mappedBy="gruppe")
	private Collection<Person> personen;
	
	private Integer anzahl;
}
