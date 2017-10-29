package com.nextgen.controller;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextgen.model.LoginDetails;
import com.nextgen.service.LoginService;


@RestController
public class LoginDetailsController {

	@Autowired
	private LoginService loginService = null;
	
	
	@RequestMapping(value="/doLogin", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginUser(@RequestBody LoginDetails login) throws JSONException {
		Boolean result = false;
		long parent_id = loginService.allowEntryForUser(login);		
		if(parent_id != 0) {
			result=true;
		}
		if(result) {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "200");
		    map.put("message", "success");
		    map.put("parent_id", ""+parent_id);
		    return map;
		}else {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "201");
		    map.put("message", "Incorrect username or password");
		    return map;
		}
	}
}
