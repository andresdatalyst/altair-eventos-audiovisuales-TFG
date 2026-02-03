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
@Table(name = "roles_has_menus")
public class RoleHasMenu implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Relaciones
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Role role;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menu;

	// Constructor
	public RoleHasMenu() {

	}

	// Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
