package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jrp.pma.model.Appointment;
import com.jrp.pma.model.Dentist;
import com.jrp.pma.service.AppointmentService;
import com.jrp.pma.service.DentistService;
import com.jrp.pma.service.EmailService;

@Controller
@RequestMapping(value="/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	DentistService dentistService;
	
	
	@Autowired
    EmailService emailService;
	
	// Make an appointment
	@PostMapping
	public ResponseEntity<Appointment> save(@RequestBody Appointment appointment) {
		Appointment newAppointment = appointmentService.save(appointment);
		String content = "Hi, " + appointment.getPatient() + ". " +
		"Your appointment has been booked with Dental Clinic." + " "
		+ "When: " + appointment.getDate() + " " + appointment.getTime() + " "
		+ "Duration: " + appointment.getDuration() + "min " 
		+ "Service: " + appointment.getExaminationType();
		
		try {
            emailService.sendSimpleEmail(appointment.getEmail(), "Dental Clinic", content);
        } catch (MailException mailException) {
            return new ResponseEntity("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return ResponseEntity.ok(newAppointment);
	}
	

    @GetMapping(value = "/simple-email/{user-email}")
    public @ResponseBody ResponseEntity sendSimpleEmail(@PathVariable("user-email") String email) {
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }
	
	@GetMapping
	public ResponseEntity<List<Appointment>> getAll() {
		 List<Appointment> appointments = appointmentService.getAll();
		 return ResponseEntity.ok(appointments);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancel(@PathVariable Long id) {
		appointmentService.cancel(id);
		
		Appointment appointment = appointmentService.getById(id);
		
		String content = "Hi, " + appointment.getPatient() + ". " +
				"Your appointment with Dental Clinic was cancelled." + " "
				+ "When: " + appointment.getDate() + " " + appointment.getTime() + " "
				+ "Duration: " + appointment.getDuration() + "min " 
				+ "Service: " + appointment.getExaminationType();
				
				try {
		            emailService.sendSimpleEmail(appointment.getEmail(), "Cancelled", content);
		        } catch (MailException mailException) {
		            return new ResponseEntity("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
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
	
	@PutMapping
	public ResponseEntity<Dentist> edit(@RequestBody Dentist dentist) {
		Dentist updated = dentistService.updateCancellationDeadline(dentist);
		return ResponseEntity.ok(updated);
	}
}