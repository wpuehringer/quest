package at.pfadfindergallneukirchen.quest.dbo;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Person implements Serializable {
	private static final long serialVersionUID = -2629749441860922872L;
	
	@Id
	private Long oid;
	private String vorname;
	private String nachname;
	
	@ManyToOne
	@JoinColumn(name="GRP_OID")
	private Gruppe gruppe;
	
	@OneToMany(mappedBy="person")
	private Collection<Teilnehmer> teilnehmer;
	
	@Override
	public String toString() {
		return nachname + " " + vorname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Gruppe getGruppe() {
		return gruppe;
	}

	public void setGruppe(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	public Collection<Teilnehmer> getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(Collection<Teilnehmer> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}
	
}
