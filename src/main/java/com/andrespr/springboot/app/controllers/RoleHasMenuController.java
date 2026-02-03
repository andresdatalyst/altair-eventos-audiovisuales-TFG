package com.andrespr.springboot.app.controllers;

import java.util.List;

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

import com.andrespr.springboot.app.models.Menu;
import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.models.RoleHasMenu;
import com.andrespr.springboot.app.services.IMenuService;
import com.andrespr.springboot.app.services.IRoleHasMenuService;
import com.andrespr.springboot.app.services.IRoleService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/roleHasMenu")
@Secured({"ROLE_ADMIN"})
public class RoleHasMenuController {

	@Autowired
	private IRoleHasMenuService roleHasMenuService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IMenuService menuService;
	
	    // Listar
		@GetMapping("/listAll")
		public String listAll(Model model,@RequestParam(name = "page", defaultValue = "0") int page) {

			Pageable pageRequest = PageRequest.of(page, 5);
			Page<RoleHasMenu> roleHasMenus = roleHasMenuService.findAll(pageRequest);
			PageRender<RoleHasMenu> pageRender = new PageRender<>("/roleHasMenu/listAll", roleHasMenus);
			model.addAttribute("page", pageRender);
			
			model.addAttribute("titulo", "Listado de RoleHasMenu");
			model.addAttribute("roleHasMenus", roleHasMenus);
			
			return "roleHasMenu/listAll";
		}
		
		// Crear
		@GetMapping("/create")
		public String create(Model model) {

			RoleHasMenu roleHasMenu= new RoleHasMenu();
			List<Menu> menus = menuService.findAll();
			List<Role> roles = roleService.findAll();
			model.addAttribute("titulo", "Crear roleHasMenu");
			model.addAttribute("roleHasMenu", roleHasMenu);
			model.addAttribute("menus", menus);
			model.addAttribute("roles", roles);
			return "roleHasMenu/create";
		}
		
		//Guardar
		@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
		public String save(@Valid RoleHasMenu roleHasMenu, BindingResult binding, Model model,
				 RedirectAttributes flash, SessionStatus status) {

			if (binding.hasErrors()) {
				model.addAttribute("titulo", "Formulario de RolHasMenu");
				List<Menu> menus = menuService.findAll();
				List<Role> roles = roleService.findAll();
				model.addAttribute("menus", menus);
				model.addAttribute("roles", roles);
				
				return "roleHasMenu/create";
			}
		
			// 2 mensajes segun si la id del menu es null
			String mensajeFlash = (roleHasMenu.getId() != null) ? "RoleHasMenu editado con éxito" : "RoleHasMenu creado con éxito";

			roleHasMenuService.save(roleHasMenu);		
			flash.addFlashAttribute("success", mensajeFlash);
			return "redirect:/roleHasMenu/listAll";
		}
		
		//Detalle
		@GetMapping("/detail/{id}")
		public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

			RoleHasMenu roleHasMenu = roleHasMenuService.findOne(id);
			if (roleHasMenu == null) {
				flash.addFlashAttribute("error", "El rol no existe en la base de datos");
				return "redirect:/listAll";
			}
			model.addAttribute("roleHasMenu", roleHasMenu);
			model.addAttribute("titulo", "Detalle del roleHasMenu: " + roleHasMenu.getId());

			return "roleHasMenu/detail";
		}
		
		// Editar
		
		@RequestMapping(value = "/edit/{id}")
		public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
			RoleHasMenu roleHasMenu= null;
			
			List<Menu> menus = menuService.findAll();
			List<Role> roles = roleService.findAll();
			if (id > 0) {
				roleHasMenu = roleHasMenuService.findOne(id);
				if (roleHasMenu == null) {
					flash.addFlashAttribute("error", "El ID del rol no existe en la base de datos");
					return "redirect:/roleHasMenu/listAll";
				}
			} else {
				flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
				return "redirect:/roleHasMenu/listAll";
			}

			model.addAttribute("titulo", "Editar Role");
			model.addAttribute("roleHasMenu", roleHasMenu);
			model.addAttribute("menus", menus);
			model.addAttribute("roles", roles);
			return "roleHasMenu/create";
		}
		//Delete
		@RequestMapping(value = "/delete/{id}")
		public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

			if (id > 0) {
				
				roleHasMenuService.delete(id);
				flash.addFlashAttribute("success", "roleHasMenu eliminado con éxito");
				// Pasos para poder eliminar una foto cuando borramos un cliente.
				// resolve("nombre del archivo")
			

			}
			return "redirect:/roleHasMenu/listAll";
		}
}
