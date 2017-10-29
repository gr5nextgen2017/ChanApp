package com.nextgen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextgen.model.ChildDetails;
import com.nextgen.model.ParentDetails;
import com.nextgen.service.ChildService;
import com.nextgen.service.LoginService;
import com.nextgen.service.ParentService;

@RestController
public class ParentDetailsController {

	@Autowired
	private ParentService parentService = null;
	
	@Autowired
	private LoginService loginService = null;
	
	@Autowired
	private ChildService childService = null;
	
	
	@RequestMapping(value="/doRegister", method=RequestMethod.POST)
	@ResponseBody
	
	public HashMap<String, String> registerUser(@RequestBody ParentDetails parent) throws JSONException {
		
		long newparentID = parentService.registerUser(parent);
		boolean result = false;
		if(newparentID > 0) {
			String email = parent.getEmail();
			String password = parent.getPassword();
			boolean loginresult = loginService.createLogin(newparentID,email,password);
			if(loginresult){
				result = true;
			}
		}
		if(result) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("status", "100");
		    map.put("message", "success");
		    return map;
		}else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("status", "101");
		    map.put("message", "Register Parent Incomplete");
		    return map;
		}
	}
	
	@RequestMapping(value="/getParentChildDetails", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, ArrayList<HashMap<String, String>>> getParentChildDetails(@RequestBody ParentDetails parent) {
		List <ParentDetails> pList = parentService.getParentChildDetails(parent.getParent_id());		
		if(!pList.isEmpty()) {
			HashMap<String, ArrayList<HashMap<String,String>>> profile = new HashMap<String, ArrayList<HashMap<String,String>>>();
			ArrayList<HashMap<String,String>> parray = new ArrayList<HashMap<String,String>>();
			HashMap<String, String> map = new HashMap<String,String>();
			for (ParentDetails row : pList) {				
				map.put("parent_name",row.getParent_name());
				map.put("age",row.getAge());
				map.put("email",row.getEmail());
				map.put("mobile",row.getMobile());
				map.put("address",row.getAddress());
				
				parray.add(map);
			}
			profile.put("parent", parray);
			List <ChildDetails> cList = childService.getChildDetails(parent.getParent_id());
			ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();
			if(!cList.isEmpty()) {
				int count=0;
				for (ChildDetails row : cList) {
					count++;
					HashMap<String, String> map1 = new HashMap<String,String>();
					map1.put("name",row.getName());
					map1.put("gender",row.getGender());
					map1.put("dob",row.getDob());
					array.add(map1);
				}
				profile.put("children", array);
			}			
			return profile;
		}else {
			HashMap<String, ArrayList<HashMap<String, String>>> map = new HashMap<String, ArrayList<HashMap<String,String>>>();
			HashMap<String, String> map1 = new HashMap<String,String>();
			ArrayList<HashMap<String,String>> earray = new ArrayList<HashMap<String,String>>();
			map1.put("status", "401");
		    map1.put("message", "No Parent is Available");
		    earray.add(map1);
		    map.put("status",earray);
		    return map;
		}
	}
}
