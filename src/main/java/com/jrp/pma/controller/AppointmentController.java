package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jrp.pma.model.Appointment;
import com.jrp.pma.model.Dentist;
import com.jrp.pma.service.AppointmentService;
import com.jrp.pma.service.DentistService;

@Controller
@RequestMapping(value="/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	DentistService dentistService;
	
	// Make an appointment
	@PostMapping
	public ResponseEntity<Appointment> save(@RequestBody Appointment appointment) {
		Appointment newAppointment = appointmentService.save(appointment);
		return ResponseEntity.ok(newAppointment);
	}
	
	@GetMapping
	public ResponseEntity<List<Appointment>> getAll() {
		 List<Appointment> appointments = appointmentService.getAll();
		 return ResponseEntity.ok(appointments);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancel(@PathVariable Long id) {
		appointmentService.cancel(id);
	    return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@GetMapping("/appointmentsWeekly")
	public ResponseEntity<List<Appointment>> getWeekly() {
		List<Appointment> appointmentsWeekly = appointmentService.getWeekly();
		return ResponseEntity.ok(appointmentsWeekly);
	}
	
	@GetMapping("/dentists")
	public ResponseEntity<List<Dentist>> getDentists() {
		List<Dentist> dentists = dentistService.getAll();
		return ResponseEntity.ok(dentists);
	}
}
