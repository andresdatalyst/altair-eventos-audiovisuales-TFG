package com.andrespr.springboot.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "hardworking_companys")
public class HardworkingCompany implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;
	@NotEmpty
	private String province;
	@NotEmpty
	@Email
	private String email;
	@Pattern(regexp = "[0-9]{5}$", message = "Deben ser 5 cifras numéricas")
	private String postalCode;
	@Pattern(regexp = "^([ABCDEFGHJKLMNPQRSUVW])(\\d{7})([0-9A-J])$")
	private String cif;
	@Pattern(regexp = "[0-9]{9}")
	private String phone;
	@NotEmpty
	private String direccion;
	private String photo;

	// Relaciones
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "hardworkingCompany")
	private List<Worker> workers;
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "hardworkingCompany")
	private List<Event> events;

	// Constructor
	public HardworkingCompany() {
		this.workers = new ArrayList<>();
		this.events = new ArrayList<>();
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkes(List<Worker> workers) {
		this.workers = workers;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
