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
import com.nextgen.model.ParentDetails;
import com.nextgen.model.VaccinationHistory;
import com.nextgen.service.ChildService;
import com.nextgen.service.VaccineService;

@RestController
public class VaccinationController {

	@Autowired
	private VaccineService vaccineService;
	
	@Autowired
	private ChildService childService;
	
	@RequestMapping(value="/addVaccination", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addVaccination(@RequestBody VaccinationHistory vaccine) throws JSONException {
		
		boolean result = vaccineService.addVaccination(vaccine);
		if(result) {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "600");
		    map.put("message", "success");
		    return map;
		}else {
			HashMap<String, String> map = new HashMap<String, String>();
		    map.put("status", "601");
		    map.put("message", "Vaccination Save Failed");
		    return map;
		}
	}
	
	@RequestMapping(value="/getVaccinationHistory", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, HashMap<String, HashMap<String, String>>> getVaccinationHistory(@RequestBody ParentDetails parent) {
		HashMap<String, HashMap<String,HashMap<String,String>>> profile = new HashMap<String, HashMap<String,HashMap<String,String>>>();
		List <ChildDetails> cList = childService.getChildDetails(parent.getParent_id());
			if(!cList.isEmpty()) {
				int count=0;
				
				for (ChildDetails row : cList) {
					HashMap<String,HashMap<String,String>> map1= new HashMap<String,HashMap<String,String>>();
					count++;
					int vaccineCount = 0;
					List <VaccinationHistory> vList = vaccineService.getVaccineDetails(row.getChild_id());
					if(!vList.isEmpty()) {
						for (VaccinationHistory vrow : vList) {
							vaccineCount++;
							HashMap<String, String> vmap = new HashMap<String,String>(); 
							vmap.put("name",vrow.getName());
							vmap.put("vaccination_date",vrow.getVaccination_date());
							vmap.put("doctor",vrow.getDoctor());
							vmap.put("doctor_phone",vrow.getDoctor_phone());
							vmap.put("hospital_name",vrow.getHospital_name());
							vmap.put("sideeffects",vrow.getSideeffects());
							
							String newVarName = "Vaccine"+vaccineCount+"details";
							map1.put(newVarName,vmap);							
						}
					}					
					String newVarName = "child"+count+"details";
					profile.put(newVarName,map1);
				}		
			return profile;
		}else {
			HashMap<String, HashMap<String,HashMap<String,String>>> map1 = new HashMap<String, HashMap<String,HashMap<String,String>>>();
			HashMap<String, HashMap<String,String>> map2 = new HashMap<String,HashMap<String,String>>();
			HashMap<String,String> map3 = new HashMap<String,String>();
			map3.put("status", "701");
		    map3.put("message", "No Vaccination is Available");
		    map2.put("status",map3);
			map1.put("status", map2);
		    return map1;
		}
	}
}
