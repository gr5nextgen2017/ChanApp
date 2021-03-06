package com.nextgen.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextgen.model.ChildDetails;

@Service
public class ChildService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Boolean registerChildren(ChildDetails[] child) {
	
		try {
			
			if(child.length > 1) {
				for(int i=0; i < child.length; i++) {
					em.persist(child[i]);
					em.flush();
				}
			}else {
			em.persist(child);
			em.flush();
			}
			return true;		
		 } catch (Exception ex) {
	         ex.printStackTrace();
	         return false;
	     }
	}

	@SuppressWarnings("unchecked")
	public List<ChildDetails> getChildDetails(long l) {
		Query query = em.createQuery("Select c from ChildDetails c where c.parent_id = "+l);
		List <ChildDetails> childList = query.getResultList();
		return childList;
	}
}
