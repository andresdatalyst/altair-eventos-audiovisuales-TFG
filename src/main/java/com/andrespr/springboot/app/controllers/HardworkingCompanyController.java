package com.andrespr.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.andrespr.springboot.app.models.HardworkingCompany;
import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.Worker;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.services.IEventService;
import com.andrespr.springboot.app.services.IHardworkingCompanyService;
import com.andrespr.springboot.app.services.IWorkerService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/hardworkingCompany")
@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER", "ROLE_BOSS" })
public class HardworkingCompanyController {

	@Autowired
	private IHardworkingCompanyService hardworkingCompanyService;

	@Autowired
	private IUserAccountRepository userRepo;

	@Autowired
	private IWorkerService workerService;

	@Autowired
	private IEventService eventService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, Authentication authentication,
			@RequestParam(name = "page", defaultValue = "0") int page, String busqueda, RedirectAttributes flash,
			String province) {

		String rol = authentication.getAuthorities().toString();
		String username = authentication.getName();
		UserAccount usuario = this.userRepo.findByUsername(username);

		if (busqueda != null) {
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findByname(busqueda);

			if (hardworkingCompanys.size() == 0) {
				String mensaje = "No se ha encontrado La Empresa Trabajadora con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/hardworkingCompany/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de Empresas Trabajadoras");
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);

			return "hardworkingCompany/search";
		}

		if (province != null) {
			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findByprovince(province);

			if (hardworkingCompanys.size() == 0) {
				String mensaje = "No se ha encontrado La Empresa Trabajadora con esa provincia";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/hardworkingCompany/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de Localizaciones");
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);

			return "hardworkingCompany/search";
		}

		if (rol.equals("[ROLE_BOSS]")) {
			// Tengo que recuperar la id del trabajador

			Worker workers = workerService.findAll().stream()
					.filter(ad -> ad.getUserAccount().getId() == usuario.getId()).toList().get(0);

			List<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll().stream()
					.filter(h -> h.getName() == workers.getHardworkingCompany().getName()).toList();

			model.addAttribute("titulo", "Listado de Empresas Trabajadoras");
			model.addAttribute("hardworkingCompanys", hardworkingCompanys);

			return "hardworkingCompany/listAll";

		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<HardworkingCompany> hardworkingCompanys = hardworkingCompanyService.findAll(pageRequest);
		PageRender<HardworkingCompany> pageRender = new PageRender<>("/hardworkingCompany/listAll",
				hardworkingCompanys);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de compañías trabajadoras");
		model.addAttribute("hardworkingCompanys", hardworkingCompanys);

		return "hardworkingCompany/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		HardworkingCompany hardworkingCompany = new HardworkingCompany();
		model.addAttribute("titulo", "Añadir Empresa Trabajadora");
		model.addAttribute("hardworkingCompany", hardworkingCompany);
		return "hardworkingCompany/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid HardworkingCompany hardworkingCompany, BindingResult binding, Model model,
			RedirectAttributes flash, SessionStatus status, @RequestParam("file") MultipartFile photo) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Empresas Trabajadoras");
			return "hardworkingCompany/create";
		}

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/hardworkingCompanys";
			

			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				hardworkingCompany.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (hardworkingCompany.getId() != null) ? "Empresa Trabajadora editada con éxito"
				: "Empresa Trabajadora creada con éxito";

		hardworkingCompanyService.save(hardworkingCompany);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/hardworkingCompany/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		HardworkingCompany hardworkingCompany = hardworkingCompanyService.findOne(id);
		if (hardworkingCompany == null) {
			flash.addFlashAttribute("error", "La Empresa Trabajadora no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("hardworkingCompany", hardworkingCompany);
		model.addAttribute("titulo", "Detalle de la Empresa Trabajadora: " + hardworkingCompany.getName());

		return "hardworkingCompany/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		HardworkingCompany hardworkingCompany = null;
		if (id > 0) {
			hardworkingCompany = hardworkingCompanyService.findOne(id);
			if (hardworkingCompany == null) {
				flash.addFlashAttribute("error", "El ID de la Empresa Trabajadora no existe en la base de datos");
				return "redirect:/hardworkingCompany/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la Empresa Trabajadora no puede ser cero");
			return "redirect:/hardworkingCompany/listAll";
		}

		model.addAttribute("titulo", "Editar Empresa Trabajadora");
		model.addAttribute("hardworkingCompany", hardworkingCompany);
		return "hardworkingCompany/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		// funcionalidad 5: No se podrá eliminar una Empresa Trabajadora si hay un
		// evento futuro.
		// Cogemos la fecha
		Date ahora = new Date();

		List<Event> events = eventService.findAll().stream()
				.filter(e -> e.getHardworkingCompany().getId() == id && e.getEventDate().getDate() >= ahora.getDate())
				.toList();

		if (events.size() > 0) {
			flash.addFlashAttribute("error", "No puedes eliminar esta Empresa porque esta en un evento futuro");
			return "redirect:/hardworkingCompany/listAll";
		}

		if (id > 0) {

			hardworkingCompanyService.delete(id);
			flash.addFlashAttribute("success", "Empresa Trabajadora eliminada con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/hardworkingCompany/listAll";
	}

	@RequestMapping(value = "/worker/{id}")
	public String trabajadoresEmpresa(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model,
			@RequestParam(name = "page", defaultValue = "0") int page) {

		final HardworkingCompany hardworkingCompany = hardworkingCompanyService.findOne(id);

		List<Worker> workers = (List<Worker>) workerService.findAll().stream()
				.filter(ad -> ad.getHardworkingCompany().getId() == hardworkingCompany.getId()).toList();

		model.addAttribute("titulo", "Trabajadores de la empresa " + hardworkingCompany.getName());
		model.addAttribute("workers", workers);

		return "worker/workerList";
	}

}
