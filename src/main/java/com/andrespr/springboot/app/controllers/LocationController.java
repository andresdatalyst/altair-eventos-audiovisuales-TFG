package com.andrespr.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.Location;
import com.andrespr.springboot.app.services.IEventService;
import com.andrespr.springboot.app.services.ILocationService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/location")
@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
public class LocationController {

	@Autowired
	private ILocationService locationService;

	@Autowired
	private IEventService eventService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page, String busqueda,
			RedirectAttributes flash, String city) {

		if (busqueda != null) {
			List<Location> listLocations = locationService.findBylocationName(busqueda);

			if (listLocations.size() == 0) {
				String mensaje = "No se ha encontrado La localizacion con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/location/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de Localizaciones");
			model.addAttribute("listLocations", listLocations);

			return "location/search";
		}

		if (city != null) {
			List<Location> listLocations = locationService.findBycity(city);

			if (listLocations.size() == 0) {
				String mensaje = "No se ha encontrado La localizacion con esa ciudad";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/location/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de Localizaciones");
			model.addAttribute("listLocations", listLocations);

			return "location/search";
		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Location> locations = locationService.findAll(pageRequest);
		PageRender<Location> pageRender = new PageRender<>("/location/listAll", locations);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Localizaciones");
		model.addAttribute("listLocations", locations);

		return "location/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		Location location = new Location();
		model.addAttribute("titulo", "Añadir Localización");
		model.addAttribute("location", location);
		return "location/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid Location location, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("file") MultipartFile photo) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de localizaciones");
			return "location/create";
		}

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/locations";
			
			

			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				location.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (location.getId() != null) ? "Localización editado con éxito"
				: "Localización creado con éxito";

		locationService.save(location);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/location/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Location location = locationService.findOne(id);
		if (location == null) {
			flash.addFlashAttribute("error", "La localización no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("location", location);
		model.addAttribute("titulo", "Detalle de la localización: " + location.getLocationName());

		return "location/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Location location = null;
		if (id > 0) {
			location = locationService.findOne(id);
			if (location == null) {
				flash.addFlashAttribute("error", "El ID de la localización no existe en la base de datos");
				return "redirect:/location/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
			return "redirect:/location/listAll";
		}

		model.addAttribute("titulo", "Editar Localización");
		model.addAttribute("location", location);
		return "location/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		// funcionalidad 4: No se podrá eliminar una localzacion si hay un evento
		// futuro.
		// Cogemos la fecha
		Date ahora = new Date();

		List<Event> events = eventService.findAll().stream()
				.filter(e -> e.getLocation().getId() == id && e.getEventDate().getDate() >= ahora.getDate()).toList();

		if (events.size() > 0) {
			flash.addFlashAttribute("error", "No puedes eliminar esta localizacion porque esta en un evento futuro");
			return "redirect:/location/listAll";
		}

		if (id > 0) {
			locationService.delete(id);
			flash.addFlashAttribute("success", "localización eliminada con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}

		return "redirect:/location/listAll";
	}

	private boolean hasRole(String role, Authentication authentication) {

		if (authentication == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		// Otra forma sin iterar
		// return authorities.contains(new SimpleGrantedAuthority(role));

		for (GrantedAuthority authority : authorities) {
			if (role.equals(authority.getAuthority())) {
				// logger.info("Hola " + authentication.getName() + "Tu rol es: "
				// +authority.getAuthority() + " tienes acceso");
				return true;
			}
		}
		return false;
	}
}
