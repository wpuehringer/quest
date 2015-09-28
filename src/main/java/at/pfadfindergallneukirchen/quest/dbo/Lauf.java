package at.pfadfindergallneukirchen.quest.dbo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Lauf implements Serializable {

	private static final long serialVersionUID = 5649302761178015855L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long oid;

	private Date startDatum;
	private Date endDatum;

	@ManyToOne
	@JoinColumn(name = "QUE_OID")
	private Quest quest;

	@OneToMany(mappedBy="lauf")
	private Collection<Teilnehmer> teilnehmer;

	public Date getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(Date startDatum) {
		this.startDatum = startDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
		this.endDatum = endDatum;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public Collection<Teilnehmer> getTeilnehmer() {
		return teilnehmer;
	}
	
	public void setTeilnehmer(Collection<Teilnehmer> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}
}
