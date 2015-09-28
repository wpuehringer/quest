package at.pfadfindergallneukirchen.quest.dbo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Teilnehmer implements Serializable {
	private static final long serialVersionUID = -2629749441860922872L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long oid;
	
	@ManyToOne
	@JoinColumn(name="PER_OID")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="LAU_OID")
	private Lauf lauf;
	
	private Integer punkte;

	@Override
	public String toString() {
		if (getPerson() != null)  {
			return getPerson().toString();
		}
		return super.toString();
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Lauf getLauf() {
		return lauf;
	}

	public void setLauf(Lauf lauf) {
		this.lauf = lauf;
	}

	public Integer getPunkte() {
		return punkte;
	}

	public void setPunkte(Integer punkte) {
		this.punkte = punkte;
	}
	
}
