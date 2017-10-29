package com.nextgen.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextgen.model.LoginDetails;

@Service
public class LoginService {

	@PersistenceContext
	EntityManager em=null;
	
	@SuppressWarnings("unchecked")
	public long allowEntryForUser(LoginDetails login) throws JSONException {
		Query query = em.createQuery("Select l from LoginDetails l");
		List <LoginDetails> userList = query.getResultList();
		String loggedInUser = login.getUsername();
		String passwordProvided = login.getPassword();
		long parent_id = 0;
		boolean result= false;
		for (LoginDetails row : userList) {
			String user = row.getUsername();
			String passwrod = row.getPassword();
			System.out.println("user : "+user);			
			if(user.equalsIgnoreCase(loggedInUser)) {				
				if(passwordProvided.equalsIgnoreCase(passwrod)) {
					result= true;
					parent_id = row.getParent_id();
				}
			}
		}
		if(result) {
			return parent_id;
		}
		return 0;
	}
	@Transactional
	public boolean createLogin(long parent_id,String email, String password) {
		try {
			LoginDetails lDetails = new LoginDetails();
			lDetails.setUsername(email);
			lDetails.setPassword(password);
			lDetails.setParent_id(parent_id);
			em.persist(lDetails);
			em.flush();
			return true;		
		 } catch (Exception ex) {
	         ex.printStackTrace();
	         return false;
	     }
	}
}
