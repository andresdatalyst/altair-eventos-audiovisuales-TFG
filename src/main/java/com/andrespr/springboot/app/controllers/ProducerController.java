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
import org.springframework.security.core.Authentication;
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

import com.andrespr.springboot.app.dtos.ProducerDTO;
import com.andrespr.springboot.app.models.Producer;
import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.services.IProducerService;
import com.andrespr.springboot.app.services.IRoleService;
import com.andrespr.springboot.app.services.IUserAccountService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/producer")
public class ProducerController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IProducerService producerService;

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserAccountRepository userRepo;

	// Listar
	@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			Authentication authentication, String busqueda, RedirectAttributes flash) {

		String rol = authentication.getAuthorities().toString();

		String username = authentication.getName();
		UserAccount usuario = this.userRepo.findByUsername(username);

		if (busqueda != null) {
			List<Producer> producers = producerService.findByname(busqueda);

			if (producers.size() == 0) {
				String mensaje = "No se ha encontrado el Productor con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/producer/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de productores");
			model.addAttribute("producers", producers);

			return "producer/search";
		}

		if (rol.equals("[ROLE_PRODUCER]")) {
			// Tengo que recuperar la id del trabajador

			Producer producer = producerService.findAll().stream()
					.filter(ad -> ad.getUserAccount().getId() == usuario.getId()).toList().get(0);
			model.addAttribute("titulo", "Tus datos");
			model.addAttribute("producers", producer);
			return "producer/listAll";
		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Producer> producers = producerService.findAll(pageRequest);
		PageRender<Producer> pageRender = new PageRender<>("/producer/listAll", producers);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Productores");
		model.addAttribute("producers", producers);

		return "producer/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		ProducerDTO producerDto = new ProducerDTO();
		model.addAttribute("titulo", "Añadir Productor");
		model.addAttribute("producerDTO", producerDto);
		return "producer/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid ProducerDTO producerDto, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("file") MultipartFile photo) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de productores");
			return "producer/create";
		}

		// Buscamos el rol
		Role role = roleService.findAll().stream()
				.filter(roleProducer -> roleProducer.getRoleName().equals("ROLE_PRODUCER")).collect(Collectors.toList())
				.get(0);

		/* Creamos los objetos y lo seteamos */
		Producer producer = new Producer();
		UserAccount userAccount = new UserAccount();

		userAccount.setUsername(producerDto.getUsername());

		UserAccount existe = userRepo.findByUsername(producerDto.getUsername());

		if (existe != null) {
			String mensajeFlash = "El nombre de usuario ya existe";
			flash.addFlashAttribute("success", mensajeFlash);
			return "redirect:/producer/create";
		}
		userAccount.setActive(true);
		userAccount.setEmail(producerDto.getEmail());
		userAccount.setPassword(passwordEncoder.encode(producerDto.getPassword()));
		userAccount.setRole(role);

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/userAccounts";
			

			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				producerDto.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		userAccount.setPhoto(producerDto.getPhoto());

		// Guardamos la userAccount antes en la base de datos
		userAccountService.save(userAccount);

		producer.setBirthday(producerDto.getBirthday());
		producer.setDni(producerDto.getDni());
		producer.setName(producerDto.getName());
		producer.setSurname(producerDto.getSurname());
		producer.setPhone(producerDto.getPhone());
		producer.setUserAccount(userAccount);

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (producer.getId() != null) ? "Productor editado con éxito" : "Productor creado con éxito";

		producerService.save(producer);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/altairAudiovisuales/home";
	}

	// Guardar del edit
	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String saveEdit(@Valid Producer producer, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de productores");
			return "producer/edit";
		}
		UserAccount userAccount = userAccountService.findAll().stream()
				.filter(userAccounts -> userAccounts.getId().equals(producer.getUserAccount().getId()))
				.collect(Collectors.toList()).get(0);

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (producer.getId() != null) ? "Productor editado con éxito" : "Productor creado con éxito";

		producer.setUserAccount(userAccount);
		producerService.save(producer);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/producer/listAll";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Producer producer = producerService.findOne(id);
		if (producer == null) {
			flash.addFlashAttribute("error", "El productor no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("producer", producer);
		model.addAttribute("titulo", "Detalle del productor: " + producer.getName());

		return "producer/detail";
	}

	// Editar

	@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Producer producer = null;

		if (id > 0) {
			producer = producerService.findOne(id);

			if (producer == null) {
				flash.addFlashAttribute("error", "El ID del productor no existe en la base de datos");
				return "redirect:/producer/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del productor no puede ser cero");
			return "redirect:/producer/listAll";
		}

		model.addAttribute("titulo", "Editar Productor");
		model.addAttribute("producer", producer);
		return "producer/edit";
	}

	// Delete
	@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			producerService.delete(id);
			flash.addFlashAttribute("success", "Productor eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/producer/listAll";
	}

}
