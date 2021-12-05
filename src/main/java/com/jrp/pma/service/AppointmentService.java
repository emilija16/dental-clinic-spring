package com.jrp.pma.service;

import java.util.List;

import com.jrp.pma.model.Appointment;

public interface AppointmentService {
	Appointment save(Appointment appointment); 
	List<Appointment> getAll();
	void cancel(Long id);
	Appointment getById(Long id);
	List<Appointment> getWeekly();
}
