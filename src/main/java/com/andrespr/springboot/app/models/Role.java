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
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	// Campos
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String roleName;
	@NotEmpty
	@Size(max = 100)
	private String roleDescription;

	// Relaciones
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "role")
	private List<UserAccount> userAccounts;
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "role")
	private List<RoleHasMenu> rolesHasMenus;

	// Constructor
	public Role() {
		this.userAccounts = new ArrayList<>();
		this.rolesHasMenus = new ArrayList<>();
	}

	// Getter Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public List<RoleHasMenu> getRolesHasMenus() {
		return rolesHasMenus;
	}

	public void setRolesHasMenus(List<RoleHasMenu> rolesHasMenus) {
		this.rolesHasMenus = rolesHasMenus;
	}

}
