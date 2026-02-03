package com.andrespr.springboot.app.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.andrespr.springboot.app.utilities.Category;

@Entity
@Table(name = "workers")
public class Worker extends Person {

	// Campos
	private static final long serialVersionUID = 1L;

	private Category category;

	// Relaciones
	@ManyToOne(fetch = FetchType.LAZY)
	private HardworkingCompany hardworkingCompany;

	// Constructor

	public Worker() {
		super();
	}

	public Worker(Category category) {
		super();
		this.category = category;
	}

	// Getters Setters

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public HardworkingCompany getHardworkingCompany() {
		return hardworkingCompany;
	}

	public void setHardworkingCompany(HardworkingCompany hardworkingCompany) {
		this.hardworkingCompany = hardworkingCompany;
	}

}
