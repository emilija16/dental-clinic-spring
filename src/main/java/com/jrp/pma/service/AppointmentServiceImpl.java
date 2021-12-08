package com.jrp.pma.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.exception.ResourceNotFoundException;
import com.jrp.pma.model.Appointment;
import com.jrp.pma.model.Dentist;
import com.jrp.pma.repository.AppointmentRepository;
import com.jrp.pma.repository.DentistRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	DentistRepository dentistRepository;

	@Override
	public Appointment save(Appointment appointment) {
		appointment.setCanceled(false);
		return appointmentRepository.save(appointment);
	}
	
	@Override
	public List<Appointment> getAll() {
		return appointmentRepository.findAllByCanceled(false);
	}

	@Override
	public void cancel(Long id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		List<Dentist> dentists = dentistRepository.findAll();
		Integer hoursLimit = 0;
		for(int i = 0; i < dentists.size(); i++) {
			hoursLimit = dentists.get(i).getCancellationDeadline();
		}		
		
		if (!appointmentRepository.existsById(id)) 
        	throw new ResourceNotFoundException("Appointment with provided id doesn't exists");
		
		Instant now = Instant.now();
		String stringDateTime = appointment.get().getDate() + " " + appointment.get().getTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(stringDateTime, formatter);
		Instant instantDateTime = dateTime.atZone(ZoneId.of("Europe/Belgrade")).toInstant();
		Boolean isWithinPriorHours = 
			    ( instantDateTime.isAfter( now.plus( hoursLimit , ChronoUnit.HOURS) ) ) 
			    && 
			    ( instantDateTime.isAfter( now ) 
			) ;
		System.out.println("----------------" + instantDateTime);
        System.out.println("----------------" + isWithinPriorHours);
        System.out.println("----------------" + now);
        System.out.println("----------------" + hoursLimit);

		if(!isWithinPriorHours) {
			throw new ResourceNotFoundException("Appointment can't be canceled!");
		}
        appointment.get().cancel();
        appointmentRepository.save(appointment.get());
	}

	@Override
	public Appointment getById(Long id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if(!appointment.isPresent() || appointment.get().isCanceled()) {
			throw new ResourceNotFoundException("Appointment with provided id doesn't exists");
		}
		return appointment.get();
	}

	@Override
	public List<Appointment> getWeekly() {
		LocalDate dateAfter6Days = LocalDate.now().plusDays(6);
		System.out.println("Dan za 6 dana:" + dateAfter6Days);
		LocalDate now = LocalDate.now();
		System.out.println("Danasnji: " + now);
		List<Appointment> appointmentsWeekly = new ArrayList<>();
		List<Appointment> appointments = appointmentRepository.findAllByCanceled(false);
		for(int i = 0; i < appointments.size(); i++) {
			if((appointments.get(i).getDate().isAfter(now) || appointments.get(i).getDate().isEqual(now))
				&& (appointments.get(i).getDate().isBefore(dateAfter6Days) || appointments.get(i).getDate().equals(dateAfter6Days))) {
				appointmentsWeekly.add(appointments.get(i));
			}
		}
		return appointmentsWeekly;
	}
}