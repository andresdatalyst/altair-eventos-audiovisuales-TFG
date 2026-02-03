package com.andrespr.springboot.app.controllers;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.HardworkingCompany;
import com.andrespr.springboot.app.models.Location;
import com.andrespr.springboot.app.models.Producer;
import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.Worker;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.services.IEventService;
import com.andrespr.springboot.app.services.IHardworkingCompanyService;
import com.andrespr.springboot.app.services.ILocationService;
import com.andrespr.springboot.app.services.IProducerService;
import com.andrespr.springboot.app.services.IWorkerService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private IEventService eventService;

	@Autowired
	private IProducerService producerService;

	@Autowired
	private IHardworkingCompanyService hardworkingCompanyService;

	@Autowired
	private ILocationService locationService;

	@Autowired
	private IUserAccountRepository userRepo;

	@Autowired
	private IWorkerService workerService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, Authentication authentication,
			@RequestParam(name = "page", defaultValue = "0") int page, String busqueda, RedirectAttributes flash,
			String fecha1, String fecha2) {

		String rol = authentication.getAuthorities().toString();
		String username = authentication.getName();
		UserAccount usuario = this.userRepo.findByUsername(username);

		if (busqueda != null) {
			List<Event> events = eventService.findByeventName(busqueda);

			if (events.size() == 0) {
				String mensaje = "No Se ha encontrado evento con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/event/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de eventos");
			model.addAttribute("events", events);

			return "event/search";
		}

		if (fecha1 != null && fecha2 != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {

				Date date1 = sdf.parse(fecha1);
				Date date2 = sdf.parse(fecha2);

				List<Event> events = eventService.findByEventDateBetween(date1, date2);

				if (events.size() == 0) {
					String mensaje = "No Se ha encontrado evento en esa fecha";
					flash.addFlashAttribute("error", mensaje);
					return "redirect:/event/listAll";
				}

				model.addAttribute("titulo", "Busqueda realizada de eventos");
				model.addAttribute("events", events);

				return "event/search";

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (rol.equals("[ROLE_WORKER]")) {
			// Tengo que recuperar la id del trabajador

			Worker workers = workerService.findAll().stream()
					.filter(ad -> ad.getUserAccount().getId() == usuario.getId()).toList().get(0);

			List<Event> events = eventService.findAll().stream()
					.filter(e -> e.getHardworkingCompany().getName() == workers.getHardworkingCompany().getName())
					.toList();

			model.addAttribute("titulo", "Listado de eventos");
			model.addAttribute("events", events);

			return "event/listAll";

		}

		if (rol.equals("[ROLE_BOSS]")) {
			// Tengo que recuperar la id del trabajador

			Worker workers = workerService.findAll().stream()
					.filter(ad -> ad.getUserAccount().getId() == usuario.getId()).toList().get(0);

			List<Event> events = eventService.findAll().stream()
					.filter(e -> e.getHardworkingCompany().getName() == workers.getHardworkingCompany().getName())
					.toList();

			model.addAttribute("titulo", "Listado de eventos");
			model.addAttribute("events", events);

			return "event/listAll";

		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Event> events = eventService.findAll(pageRequest);
		PageRender<Event> pageRender = new PageRender<>("/event/listAll", events);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de eventos");
		model.addAttribute("events", events);

		return "event/listAll";

	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		List<Producer> producers = producerService.findAll();
		model.addAttribute("producers", producers);
		List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
		model.addAttribute("hardworkingCompanys", hardworkingCompanys);
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);

		Event event = new Event();
		model.addAttribute("titulo", "Crear Evento");
		model.addAttribute("event", event);
		return "event/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid Event event, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de eventos");
			List<Producer> producers = producerService.findAll();
			model.addAttribute("producers", producers);
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			return "event/create";
		}
		
		/*UserAccount existe = userRepo.findByUsername(adminDto.getUsername());

		if (existe != null) {
			String mensajeFlash = "El nombre de usuario ya existe";
			flash.addFlashAttribute("success", mensajeFlash);
			return "redirect:/admin/create";
		}*/
		
		List<Event> existe = eventService.findByeventName(event.getEventName());
		
		if(existe.size()>0) {
			model.addAttribute("titulo", "Formulario de eventos");
			List<Producer> producers = producerService.findAll();
			model.addAttribute("producers", producers);
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			String mensaje = "Ya existe el nombre de ese evento en la base de datos";
			flash.addFlashAttribute("error", mensaje);
			return "redirect:/event/create";
		}
		
		// Funcionalidad:Que no se puede crear un evento si la fecha de montaje es
		// despues de la del evento y que la fecha de desmontaje sea antes del evento
		if (event.getEventDate().before(event.getStartDate())) {

			model.addAttribute("titulo", "Formulario de eventos");
			List<Producer> producers = producerService.findAll();
			model.addAttribute("producers", producers);
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			String mensaje = "No puedes crear un evento si la fecha del montaje es posterior a la fecha del evento";
			flash.addFlashAttribute("error", mensaje);
			return "redirect:/event/create";
		}

		if (event.getEventDate().after(event.getEndDate())) {

			model.addAttribute("titulo", "Formulario de eventos");
			List<Producer> producers = producerService.findAll();
			model.addAttribute("producers", producers);
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			String mensaje = "No puedes crear un evento si la fecha del Desmontaje es anterior a la fecha del evento";
			flash.addFlashAttribute("error", mensaje);
			return "redirect:/event/create";
		}

		/*
		 * funcionalidad 7:No puede haber más de un Evento en una Localizacion el mismo
		 * día.
		 */

		List<Event> events = eventService.findAll().stream().filter(e -> e.getLocation() == event.getLocation()
				&& e.getEventDate().getDate() == event.getEventDate().getDate()).toList();

		if (events.size() > 0) {
			flash.addFlashAttribute("error", "La localizacion está ocupada para ese día");
			return "redirect:/event/create";
		}

		/*
		 * funcionalidad 8:No puede haber más de un evento el mismo día con la misma
		 * empresa.
		 */

		events = eventService.findAll().stream().filter(e -> e.getHardworkingCompany() == event.getHardworkingCompany()
				&& e.getEventDate().getDate() == event.getEventDate().getDate()).toList();

		if (events.size() > 0) {
			flash.addFlashAttribute("error", "La Empresa ya tiene un evento ese día");
			return "redirect:/event/create";
		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (event.getId() != null) ? "evento editado con éxito" : "evento creado con éxito";

		eventService.save(event);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/event/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Event event = eventService.findOne(id);
		if (event == null) {
			flash.addFlashAttribute("error", "El evento no existe en la base de datos");
			return "redirect:/event/listall";
		}
		model.addAttribute("event", event);
		model.addAttribute("titulo", "Detalle del evento: " + event.getId());

		return "event/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Event event = null;
		if (id > 0) {
			event = eventService.findOne(id);
			if (event == null) {
				flash.addFlashAttribute("error", "El ID del evento no existe en la base de datos");
				return "redirect:/event/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del evento no puede ser cero");
			return "redirect:/event/listAll";
		}

		List<Producer> producers = producerService.findAll();
		model.addAttribute("producers", producers);
		List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll();
		model.addAttribute("hardworkingCompanys", hardworkingCompanys);
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		model.addAttribute("titulo", "Editar evento");
		model.addAttribute("event", event);
		return "event/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			eventService.delete(id);
			flash.addFlashAttribute("success", "evento eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/event/listAll";
	}
}
