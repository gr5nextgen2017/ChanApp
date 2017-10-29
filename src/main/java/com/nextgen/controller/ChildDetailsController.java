package com.nextgen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextgen.model.ChildDetails;
import com.nextgen.service.ChildService;

@RestController
public class ChildDetailsController {

	@Autowired
	private ChildService childService;
	
	@RequestMapping(value="/addChildren", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> registerChildren(@RequestBody ChildDetails[] child) throws JSONException {
		System.out.println("inside register children");
		Boolean result = childService.registerChildren(child);		
		if(result) {
			System.out.println("enterd if");
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "300");
		    map.put("message", "success");
		    return map;
		}else {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "301");
		    map.put("message", "Child Save Failed");
		    return map;
		}
	}
	
	@RequestMapping(value="/getChildIDNames", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> getChildIDNames(@RequestBody ChildDetails child) {
		List <ChildDetails> cList = childService.getChildDetails(child.getParent_id());		
		if(!cList.isEmpty()) {
			HashMap<String, String> map = new HashMap<String, String>();
			for (ChildDetails row : cList) {
				map.put(""+row.getChild_id(),""+row.getName());
			}
		    return map;
		}else {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "501");
		    map.put("message", "No Child Available for Parent");
		    return map;
		}
	}
}
