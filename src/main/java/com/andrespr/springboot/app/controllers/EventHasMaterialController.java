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

import com.andrespr.springboot.app.models.AudiovisualMaterial;
import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.EventHasMaterial;
import com.andrespr.springboot.app.services.IAudiovisualMaterialService;
import com.andrespr.springboot.app.services.IEventHasMaterialService;
import com.andrespr.springboot.app.services.IEventService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/eventHasMaterial")
@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
public class EventHasMaterialController {

	@Autowired
	private IEventHasMaterialService eventHasMaterialService;

	@Autowired
	private IEventService eventService;

	@Autowired
	private IAudiovisualMaterialService audiovisualMaterialService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<EventHasMaterial> eventHasMaterials = eventHasMaterialService.findAll(pageRequest);
		PageRender<EventHasMaterial> pageRender = new PageRender<>("/eventHasMaterial/listAll", eventHasMaterials);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de event_Has_Materials");
		model.addAttribute("eventHasMaterials", eventHasMaterials);

		return "eventHasMaterial/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		EventHasMaterial eventHasMaterial = new EventHasMaterial();
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		List<AudiovisualMaterial> audioVisualMaterials = audiovisualMaterialService.findAll();
		model.addAttribute("audioVisualMaterials", audioVisualMaterials);
		model.addAttribute("titulo", "Crear eventHasMaterial");
		model.addAttribute("eventHasMaterial", eventHasMaterial);

		return "eventHasMaterial/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid EventHasMaterial eventHasMaterial, BindingResult binding, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de eventHasMaterial");
			return "eventHasMaterial/create";
		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (eventHasMaterial.getId() != null) ? "eventHasMaterial editado con éxito"
				: "eventHasMaterial creado con éxito";

		eventHasMaterialService.save(eventHasMaterial);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/eventHasMaterial/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		EventHasMaterial eventHasMaterial = eventHasMaterialService.findOne(id);
		if (eventHasMaterial == null) {
			flash.addFlashAttribute("error", "El eventHasMaterial no existe en la base de datos");
			return "redirect:/listAll";
		}
		model.addAttribute("eventHasMaterial", eventHasMaterial);
		model.addAttribute("titulo", "Detalle del eventHasMaterial: " + eventHasMaterial.getId());

		return "eventHasMaterial/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		EventHasMaterial eventHasMaterial = null;

		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		List<AudiovisualMaterial> audioVisualMaterials = audiovisualMaterialService.findAll();
		model.addAttribute("audioVisualMaterials", audioVisualMaterials);
		if (id > 0) {
			eventHasMaterial = eventHasMaterialService.findOne(id);
			if (eventHasMaterial == null) {
				flash.addFlashAttribute("error", "El ID eventHasMaterial no existe en la base de datos");
				return "redirect:/eventHasMaterial/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID eventHasMaterial cliente no puede ser cero");
			return "redirect:/eventHasMaterial/listAll";
		}

		model.addAttribute("titulo", "Editar eventHasMaterial");
		model.addAttribute("eventHasMaterial", eventHasMaterial);

		return "eventHasMaterial/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			eventHasMaterialService.delete(id);
			flash.addFlashAttribute("success", "eventHasMaterial eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/eventHasMaterial/listAll";
	}
}
