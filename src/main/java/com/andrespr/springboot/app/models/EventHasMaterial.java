package com.andrespr.springboot.app.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event_has_materials")
public class EventHasMaterial implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Relaciones

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Event event;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private AudiovisualMaterial audiovisualMaterial;

	// Constructor
	public EventHasMaterial() {

	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public AudiovisualMaterial getAudiovisualMaterial() {
		return audiovisualMaterial;
	}

	public void setAudiovisualMaterial(AudiovisualMaterial audiovisualMaterial) {
		this.audiovisualMaterial = audiovisualMaterial;
	}

}
