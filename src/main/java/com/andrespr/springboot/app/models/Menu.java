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

@Entity
@Table(name = "menus")
public class Menu implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String label;
	@NotEmpty
	private String action;

	// Relaciones
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
	private List<RoleHasMenu> roleHasMenus;

	// Contructor
	public Menu() {
		this.roleHasMenus = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<RoleHasMenu> getRoleHasMenus() {
		return roleHasMenus;
	}

	public void setRoleHasMenus(List<RoleHasMenu> roleHasMenus) {
		this.roleHasMenus = roleHasMenus;
	}

}
