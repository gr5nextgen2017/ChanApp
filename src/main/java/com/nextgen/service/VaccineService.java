package com.nextgen.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextgen.model.VaccinationHistory;

@Service
public class VaccineService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public boolean addVaccination(VaccinationHistory vaccine) {
	
		try {
			em.persist(vaccine);
			em.flush();
			return true;	
		 } catch (Exception ex) {
	         ex.printStackTrace();
	         return false;
	     }
	}

	@SuppressWarnings("unchecked")
	public List<VaccinationHistory> getVaccineDetails(int child_id) {
		Query query = em.createQuery("Select v from VaccinationHistory v where v.child_id = "+child_id);
		List <VaccinationHistory> vaccineList = query.getResultList();
		return vaccineList;
	}
}
