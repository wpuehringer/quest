package at.pfadfindergallneukirchen.quest.dbo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Quest implements Serializable {
	private static final long serialVersionUID = 7225528108653494935L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long oid;
	private String name;
	private Integer anzahlInstanzen;
	private Integer anzahlGruppen;
	private Integer anzahlPersonen;
	private String bescheibung;
	
	private Integer punkte;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAnzahlInstanzen() {
		return anzahlInstanzen;
	}

	public void setAnzahlInstanzen(Integer anzahlInstanzen) {
		this.anzahlInstanzen = anzahlInstanzen;
	}

	public Integer getAnzahlPersonen() {
		return anzahlPersonen;
	}

	public void setAnzahlPersonen(Integer anzahlPersonen) {
		this.anzahlPersonen = anzahlPersonen;
	}
	
	public Integer getAnzahlGruppen() {
		return anzahlGruppen;
	}
	
	public void setAnzahlGruppen(Integer anzahlGruppen) {
		this.anzahlGruppen = anzahlGruppen;
	}

	public String getBescheibung() {
		return bescheibung;
	}

	public void setBescheibung(String bescheibung) {
		this.bescheibung = bescheibung;
	}

	public Integer getPunkte() {
		return punkte;
	}

	public void setPunkte(Integer punkte) {
		this.punkte = punkte;
	}
	
	
}
