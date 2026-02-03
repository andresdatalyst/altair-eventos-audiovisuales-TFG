package com.andrespr.springboot.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "events")
public class Event implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date eventDate;
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	@NotEmpty
	private String eventName;

	// Relaciones

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Producer producer;
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Location location;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private HardworkingCompany hardworkingCompany;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
	private List<EventHasMaterial> eventHasMaterials;

	// Constructor
	public Event() {
		this.eventHasMaterials = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public HardworkingCompany getHardworkingCompany() {
		return hardworkingCompany;
	}

	public void setHardworkingCompany(HardworkingCompany hardworkingCompany) {
		this.hardworkingCompany = hardworkingCompany;
	}

	public List<EventHasMaterial> getEventHasMaterials() {
		return eventHasMaterials;
	}

	public void setEventHasMaterials(List<EventHasMaterial> eventHasMaterials) {
		this.eventHasMaterials = eventHasMaterials;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
