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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "locations")
public class Location implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String city;
	@NotEmpty
	private String direction;
	@Pattern(regexp = "[0-9]{5}$", message = "Deben ser 5 cifras numéricas")
	private String postalCode;
	@NotEmpty
	private String locationName;

	private String photo;
	// Relaciones
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "location")
	private List<Event> events;

	// Constructores
	public Location() {
		this.events = new ArrayList<>();
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direccion) {
		this.direction = direccion;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
