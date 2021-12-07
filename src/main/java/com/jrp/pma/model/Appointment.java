package com.jrp.pma.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String patient;
	private String jmbg;
	private String phone;
	private LocalDate date;
	private LocalTime time;
	private String examinationType;
	private boolean canceled;
	private int duration;
	private String email;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	public String getExaminationType() {
		return examinationType;
	}
	public void setExaminationType(String examinationType) {
		this.examinationType = examinationType;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Appointment() {}
	
	public Appointment(Long id, String patient, String jmbg, String phone, LocalDate date, LocalTime time,
			String examinationType, boolean canceled, int duration, String email) {
		super();
		this.id = id;
		this.patient = patient;
		this.jmbg = jmbg;
		this.phone = phone;
		this.date = date;
		this.time = time;
		this.examinationType = examinationType;
		this.canceled = canceled;
		this.duration = duration;
		this.email = email;
	}
	
	public void cancel() {
		this.setCanceled(true);
	}
}