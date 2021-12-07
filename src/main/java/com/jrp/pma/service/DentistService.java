package com.jrp.pma.service;

import java.util.List;
import com.jrp.pma.model.Dentist;

public interface DentistService {

	List<Dentist> getAll();
	Dentist updateCancellationDeadline(Dentist dentist);
}
