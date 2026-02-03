package com.andrespr.springboot.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Person {

	private static final long serialVersionUID = 1L;

	public Admin() {
		super();

	}

}
