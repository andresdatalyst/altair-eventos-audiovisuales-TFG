package com.andrespr.springboot.app.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "producers")
public class Producer extends Person {

	private static final long serialVersionUID = 1L;

	// Relaciones
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
	private List<Event> events;

	// Constructor
	public Producer() {

		this.events = new ArrayList<>();

	}

	// Getters and Setters

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
