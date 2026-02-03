package com.andrespr.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.services.IRoleService;
import com.andrespr.springboot.app.services.IUserAccountService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/userAccount")
public class UserAccountController {

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserAccountRepository userRepo;

	// Listar
	@Secured("ROLE_ADMIN")
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page, String busqueda,
			RedirectAttributes flash, String email) {

		if (busqueda != null) {
			List<UserAccount> userAccounts = userAccountService.findByusername(busqueda);

			if (userAccounts.size() == 0) {
				String mensaje = "No se ha encontrado el UserAccount con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/userAccount/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de UserAccounts");
			model.addAttribute("userAccounts", userAccounts);

			return "userAccount/search";
		}

		if (email != null) {
			List<UserAccount> userAccounts = userAccountService.findByEmail(email);

			if (userAccounts.size() == 0) {
				String mensaje = "No se ha encontrado el UserAccount con ese email";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/userAccount/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de UserAccounts");
			model.addAttribute("userAccounts", userAccounts);

			return "userAccount/search";
		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<UserAccount> userAccounts = userAccountService.findAll(pageRequest);
		PageRender<UserAccount> pageRender = new PageRender<>("/userAccount/listAll", userAccounts);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de cuentas de usuarios");
		model.addAttribute("userAccounts", userAccounts);

		return "userAccount/listAll";
	}

	// Crear
	@GetMapping("/create")
	@Secured("ROLE_ADMIN")
	public String create(Model model) {

		UserAccount userAccount = new UserAccount();
		List<Role> roles = roleService.findAll();
		model.addAttribute("titulo", "Crear Cuenta");
		model.addAttribute("userAccount", userAccount);
		model.addAttribute("roles", roles);
		return "userAccount/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid UserAccount userAccount, BindingResult binding, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("file") MultipartFile photo, Authentication authentication) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de UserAccount");
			List<Role> roles = roleService.findAll();
			model.addAttribute("roles", roles);
			return "userAccount/create";
		}

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/userAccounts";
		

			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				userAccount.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (userAccount.getId() != null) ? "UserAccount editado con éxito"
				: "UserAccount creado con éxito";

		//userAccount.setActive(true);
		userAccountService.save(userAccount);
		flash.addFlashAttribute("success", mensajeFlash);
		String rol = authentication.getAuthorities().toString();

		if (rol.equals("ROLE_ADMIN")) {
			return "redirect:/userAccount/listAll";
		}
		return "redirect:/altairAudiovisuales/home";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		UserAccount userAccount = userAccountService.findOne(id);
		if (userAccount == null) {
			flash.addFlashAttribute("error", "El userAccount no existe en la base de datos");
			return "redirect:/listAll";
		}
		model.addAttribute("userAccount", userAccount);
		model.addAttribute("titulo", "Detalle del userAccount: " + userAccount.getUsername());

		return "userAccount/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		UserAccount userAccount = null;
		if (id > 0) {
			userAccount = userAccountService.findOne(id);
			if (userAccount == null) {
				flash.addFlashAttribute("error", "El ID del userAccount no existe en la base de datos");
				return "redirect:/userAccount/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del userAccount no puede ser cero");
			return "redirect:/userAccount/listAll";
		}

		List<Role> roles = roleService.findAll();

		model.addAttribute("roles", roles);
		model.addAttribute("titulo", "Editar userAccount");
		model.addAttribute("userAccount", userAccount);
		return "userAccount/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			userAccountService.delete(id);
			flash.addFlashAttribute("success", "menu eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/userAccount/listAll";
	}

	@GetMapping("/perfil")
	public String perfil(Model model, Authentication authentication) {

		String username = authentication.getName();
		UserAccount usuario = this.userRepo.findByUsername(username);

		return "redirect:/userAccount/detail/" + usuario.getId();
	}

	@RequestMapping(value = "/verCuenta/{id}")
	public String verCuenta(@PathVariable(value = "id") Long id, Model model, Authentication authentication) {

		UserAccount usuario = userAccountService.findOne(id);

		List<UserAccount> userAccounts = userAccountService.findAll().stream()
				.filter(ad -> ad.getId() == usuario.getId()).toList();

		model.addAttribute("titulo", "Datos de tu UserAccount");
		model.addAttribute("userAccounts", userAccounts);
		return "userAccount/verCuenta";
	}
}
