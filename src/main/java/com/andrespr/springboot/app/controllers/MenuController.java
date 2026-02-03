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

import com.andrespr.springboot.app.models.Menu;
import com.andrespr.springboot.app.services.IMenuService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/menu")
@Secured("ROLE_ADMIN")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Menu> menus = menuService.findAll(pageRequest);
		PageRender<Menu> pageRender = new PageRender<>("/menu/listAll", menus);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Menus");
		model.addAttribute("listMenus", menus);

		return "menu/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		Menu menu = new Menu();
		model.addAttribute("titulo", "Crear Menu");
		model.addAttribute("menu", menu);
		return "menu/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid Menu menu, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de menu");
			return "menu/create";
		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (menu.getId() != null) ? "Menu editado con éxito" : "Menu creado con éxito";

		menuService.save(menu);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/menu/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Menu menu = menuService.findOne(id);
		if (menu == null) {
			flash.addFlashAttribute("error", "El menu no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("menu", menu);
		model.addAttribute("titulo", "Detalle menu: " + menu.getLabel());

		return "menu/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Menu menu = null;
		if (id > 0) {
			menu = menuService.findOne(id);
			if (menu == null) {
				flash.addFlashAttribute("error", "El ID del menu no existe en la base de datos");
				return "redirect:/menu/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
			return "redirect:/menu/listAll";
		}

		model.addAttribute("titulo", "Editar Menu");
		model.addAttribute("menu", menu);
		return "menu/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			menuService.delete(id);
			flash.addFlashAttribute("success", "menu eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/menu/listAll";
	}
}
