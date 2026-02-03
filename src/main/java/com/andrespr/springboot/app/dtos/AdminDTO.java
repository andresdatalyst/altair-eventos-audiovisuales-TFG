package com.andrespr.springboot.app.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class AdminDTO {
	@NotEmpty
	@Size(max = 20)
	private String name;
	@NotEmpty
	@Size(max = 80)
	private String surname;
	@Pattern(regexp = "[0-9]{9}")
	private String phone;
	@Pattern(regexp = "[0-9]{8}[A-Z]", message = "tiene que ser formato DNI")
	private String dni;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private Date birthday;
	@NotEmpty
	private String username;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "La contraseña debe contener mínimo 8 caractéres, incluyendo 1 numbero,1 letra mayuscula, 1 letra minuscula y un caracter especial")
	private String password;
	@Email
	private String email;
	private Boolean active;
	private String photo;

	public AdminDTO() {

	}

	public AdminDTO(String name, String surname, String phone, String dni, Date birthday, String username,
			String password, String email, Boolean active) {
		super();
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.dni = dni;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
		this.email = email;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
