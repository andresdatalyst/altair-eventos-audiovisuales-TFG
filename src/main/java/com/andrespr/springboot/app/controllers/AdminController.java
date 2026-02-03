package com.andrespr.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andrespr.springboot.app.dtos.AdminDTO;
import com.andrespr.springboot.app.models.Admin;
import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.services.IAdminService;
import com.andrespr.springboot.app.services.IRoleService;
import com.andrespr.springboot.app.services.IUserAccountService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserAccountRepository userRepo;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page, String busqueda,
			RedirectAttributes flash) {

		if (busqueda != null) {
			List<Admin> admins = adminService.findByname(busqueda);

			if (admins.size() == 0) {
				String mensaje = "No se ha encontrado el Admin con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/admin/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de Admins");
			model.addAttribute("admins", admins);

			return "admin/search";
		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Admin> admins = adminService.findAll(pageRequest);
		PageRender<Admin> pageRender = new PageRender<>("/admin/listAll", admins);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Administradores");
		model.addAttribute("admins", admins);

		return "admin/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		AdminDTO adminDto = new AdminDTO();
		model.addAttribute("titulo", "Añadir Administrador");
		model.addAttribute("adminDTO", adminDto);
		return "admin/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid AdminDTO adminDto, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("file") MultipartFile photo) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de administradores");
			return "admin/create";
		}

		// Buscamos el rol
		Role role = roleService.findAll().stream()
				.filter(roleProducer -> roleProducer.getRoleName().equals("ROLE_ADMIN")).collect(Collectors.toList())
				.get(0);

		/* Creamos los objetos y lo seteamos */
		Admin admin = new Admin();
		UserAccount userAccount = new UserAccount();

		userAccount.setActive(true);
		userAccount.setEmail(adminDto.getEmail());
		userAccount.setPassword(passwordEncoder.encode(adminDto.getPassword()));
		userAccount.setRole(role);
		userAccount.setUsername(adminDto.getUsername());

		UserAccount existe = userRepo.findByUsername(adminDto.getUsername());

		if (existe != null) {
			String mensajeFlash = "El nombre de usuario ya existe";
			flash.addFlashAttribute("success", mensajeFlash);
			return "redirect:/admin/create";
		}

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/userAccounts";
			
			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				adminDto.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		userAccount.setPhoto(adminDto.getPhoto());

		// Guardamos la userAccount antes en la base de datos
		userAccountService.save(userAccount);

		admin.setBirthday(adminDto.getBirthday());
		admin.setDni(adminDto.getDni());
		admin.setName(adminDto.getName());
		admin.setSurname(adminDto.getSurname());
		admin.setPhone(adminDto.getPhone());
		admin.setUserAccount(userAccount);

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (admin.getId() != null) ? "Admin editado con éxito" : "Admin creado con éxito";

		adminService.save(admin);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/admin/listAll";
	}

	// Guardar del edit
	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String saveEdit(@Valid Admin admin, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de administradores");
			return "admin/edit";
		}
		UserAccount userAccount = userAccountService.findAll().stream()
				.filter(userAccounts -> userAccounts.getId().equals(admin.getUserAccount().getId()))
				.collect(Collectors.toList()).get(0);

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (admin.getId() != null) ? "Admin editado con éxito" : "Admin creado con éxito";

		admin.setUserAccount(userAccount);
		adminService.save(admin);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/admin/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Admin admin = adminService.findOne(id);
		if (admin == null) {
			flash.addFlashAttribute("error", "El admin no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("admin", admin);
		model.addAttribute("titulo", "Detalle del administrador: " + admin.getName());

		return "admin/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Admin admin = null;

		if (id > 0) {
			admin = adminService.findOne(id);

			if (admin == null) {
				flash.addFlashAttribute("error", "El ID del administrador no existe en la base de datos");
				return "redirect:/producer/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del administrador no puede ser cero");
			return "redirect:/producer/listAll";
		}

		model.addAttribute("titulo", "Editar Admin");
		model.addAttribute("admin", admin);
		return "admin/edit";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			adminService.delete(id);
			flash.addFlashAttribute("success", "Admin eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/admin/listAll";
	}

}
