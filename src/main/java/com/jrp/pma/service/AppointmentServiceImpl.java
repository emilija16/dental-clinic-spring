package com.jrp.pma.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jrp.pma.model.Appointment;
import com.jrp.pma.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public Appointment save(Appointment appointment) {
		appointment.setCanceled(false);
		return appointmentRepository.save(appointment);
	}
	
	@Override
	public List<Appointment> getAll() {
		return appointmentRepository.findAll();
	}

	@Override
	public void cancel(Long id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointmentRepository.existsById(id)) 
        	System.out.println("ne postoji");
        appointment.get().cancel();
        appointmentRepository.save(appointment.get());
        
	}

	@Override
	public Appointment getById(Long id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if(!appointment.isPresent() || appointment.get().isCanceled()) {
			System.out.println("");
		}
		return appointment.get();
	}

	@Override
	public List<Appointment> getWeekly() {
		LocalDate dateBefore6Days = LocalDate.now().minusDays(6);
		System.out.println("Dan pre 6 dana:" + dateBefore6Days);
		LocalDate now = LocalDate.now();
		System.out.println("Danasnji: " + now);
		List<Appointment> appointmentsWeekly = new ArrayList<>();
		List<Appointment> appointments = appointmentRepository.findAll();
		for(int i = 0; i < appointments.size(); i++) {
			if(appointments.get(i).getDate().isAfter(dateBefore6Days) && (appointments.get(i).getDate().isBefore(now)
					|| appointments.get(i).getDate().isEqual(now))) {
				appointmentsWeekly.add(appointments.get(i));
				System.out.println("Ovo su datumi: " + appointmentsWeekly);
			}
		}
		return appointmentsWeekly;
	}
}