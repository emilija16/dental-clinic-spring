package com.jrp.pma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jrp.pma.model.Dentist;
import com.jrp.pma.repository.DentistRepository;

@Service
public class DentistServiceImpl implements DentistService {
	
	@Autowired
	DentistRepository dentistRepository;

	@Override
	public List<Dentist> getAll() {
		// TODO Auto-generated method stub
		return dentistRepository.findAll();
	}
}
