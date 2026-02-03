package com.andrespr.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.services.IRoleService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/role")
@Secured("ROLE_ADMIN")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Role> roles = roleService.findAll(pageRequest);
		PageRender<Role> pageRender = new PageRender<>("/role/listAll", roles);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Roles");
		model.addAttribute("listRoles", roles);

		return "role/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		Role role = new Role();
		model.addAttribute("titulo", "Crear Role");
		model.addAttribute("role", role);
		return "role/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid Role role, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Rol");
			return "role/create";
		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (role.getId() != null) ? "Role editado con éxito" : "Role creado con éxito";

		roleService.save(role);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/role/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Role role = roleService.findOne(id);
		if (role == null) {
			flash.addFlashAttribute("error", "El rol no existe en la base de datos");
			return "redirect:/listAll";
		}
		model.addAttribute("role", role);
		model.addAttribute("titulo", "Detalle del rol: " + role.getRoleName());

		return "role/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Role role = null;
		if (id > 0) {
			role = roleService.findOne(id);
			if (role == null) {
				flash.addFlashAttribute("error", "El ID del rol no existe en la base de datos");
				return "redirect:/role/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
			return "redirect:/role/listAll";
		}

		model.addAttribute("titulo", "Editar Role");
		model.addAttribute("role", role);
		return "role/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			roleService.delete(id);
			flash.addFlashAttribute("success", "menu eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/role/listAll";
	}
}
