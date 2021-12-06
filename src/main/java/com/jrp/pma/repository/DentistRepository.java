package com.jrp.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrp.pma.model.Dentist;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {
	Dentist findByJmbg(String jmbg);
}
