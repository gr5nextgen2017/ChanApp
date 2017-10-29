package com.nextgen.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextgen.model.ParentDetails;

@Service
public class ParentService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public long registerUser(ParentDetails parent) {
	
		try {
			em.persist(parent);
			em.flush();
			return parent.getParent_id();		
		 } catch (Exception ex) {
	         ex.printStackTrace();
	         return 0;
	     }
	}

	@SuppressWarnings("unchecked")
	public List<ParentDetails> getParentChildDetails(long parent_id) {
		Query query = em.createQuery("Select p from ParentDetails p where p.parent_id = "+parent_id);
		List <ParentDetails> parentList = query.getResultList();
		return parentList;
	}

}
