package com.nextgen.controller;

import java.util.ArrayList;
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
	public HashMap<String, ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>>> getVaccinationHistory(@RequestBody ParentDetails parent) {
		HashMap<String, ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>> profile = new HashMap<String, ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>>();
		List <ChildDetails> cList = childService.getChildDetails(parent.getParent_id());
			if(!cList.isEmpty()) {
				ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>> vaccinearray = new ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>();
				for (ChildDetails row : cList) {
					HashMap<String,ArrayList<HashMap<String,String>>> map1= new HashMap<String,ArrayList<HashMap<String,String>>>();
					ArrayList<HashMap<String,String>> carray = new ArrayList<HashMap<String,String>>();
					HashMap<String, String> cmap = new HashMap<String,String>(); 
					cmap.put("name",row.getName());
					carray.add(cmap);
					map1.put("name",carray);
					
					cmap = new HashMap<String,String>(); 
					cmap.put("dob",row.getDob());
					carray = new ArrayList<HashMap<String,String>>();
					carray.add(cmap);
					map1.put("dob",carray);
					
					cmap = new HashMap<String,String>();
					cmap.put("gender",row.getGender());
					carray = new ArrayList<HashMap<String,String>>();
					carray.add(cmap);
					map1.put("gender",carray);
				
					List <VaccinationHistory> vList = vaccineService.getVaccineDetails(row.getChild_id());
					ArrayList<HashMap<String,String>> varray = new ArrayList<HashMap<String,String>>();
					if(!vList.isEmpty()) {
						for (VaccinationHistory vrow : vList) {
							HashMap<String, String> vmap = new HashMap<String,String>(); 
							vmap.put("name",vrow.getName());
							vmap.put("vaccination_date",vrow.getVaccination_date());
							vmap.put("doctor",vrow.getDoctor());
							vmap.put("doctor_phone",vrow.getDoctor_phone());
							vmap.put("hospital_name",vrow.getHospital_name());
							vmap.put("sideeffects",vrow.getSideeffects());
							varray.add(vmap);														
						}
						map1.put("history",varray);
					}					
					vaccinearray.add(map1);
				}
				profile.put("vaccinationHistory", vaccinearray);
			return profile;
		}else {
			HashMap<String, ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>> map1 = new HashMap<String, ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>>();
			ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>> arr1 = new ArrayList<HashMap<String,ArrayList<HashMap<String,String>>>>();
			HashMap<String,ArrayList<HashMap<String,String>>> map2 = new HashMap<String,ArrayList<HashMap<String,String>>>();
			ArrayList<HashMap<String,String>> arr2 = new ArrayList<HashMap<String,String>>();
			HashMap<String,String> map3 = new HashMap<String,String>();
			map3.put("status", "701");
		    map3.put("message", "No Vaccination is Available");
		    arr2.add(map3);
		    map2.put("status",arr2);
		    arr1.add(map2);
			map1.put("status", arr1);
		    return map1;
		}
	}
}
