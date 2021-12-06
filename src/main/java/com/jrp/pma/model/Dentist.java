package com.jrp.pma.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dentist")
public class Dentist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String jmbg;
	private int cancellationDeadline;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}	
	public int getCancellationDeadline() {
		return cancellationDeadline;
	}
	public void setCancellationDeadline(int cancellationDeadline) {
		this.cancellationDeadline = cancellationDeadline;
	}
	Dentist() {}
}