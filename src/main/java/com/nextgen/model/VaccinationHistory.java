package com.nextgen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VaccinationHistory {

	@Id
	@GeneratedValue
	private String vaccine_id;
	
	private int child_id;
	private String name;
	private String vaccination_date;
	private String doctor;
	private String doctor_phone;
	private String hospital_name;
	private String sideeffects;
	
	
	public int getChild_id() {
		return child_id;
	}
	
	public String getDoctor() {
		return doctor;
	}
	public String getDoctor_phone() {
		return doctor_phone;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	
	public String getVaccine_id() {
		return vaccine_id;
	}
	public void setChild_id(int child_id) {
		this.child_id = child_id;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public void setDoctor_phone(String doctor_phone) {
		this.doctor_phone = doctor_phone;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	public void setVaccine_id(String vaccine_id) {
		this.vaccine_id = vaccine_id;
	}
	public String getVaccination_date() {
		return vaccination_date;
	}
	public void setVaccination_date(String vaccination_date) {
		this.vaccination_date = vaccination_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSideeffects() {
		return sideeffects;
	}

	public void setSideeffects(String sideeffects) {
		this.sideeffects = sideeffects;
	}

}
