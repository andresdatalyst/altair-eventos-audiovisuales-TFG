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

import com.andrespr.springboot.app.models.AudiovisualMaterial;
import com.andrespr.springboot.app.services.IAudiovisualMaterialService;
import com.andrespr.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/audiovisualMaterial")
@Secured({ "ROLE_ADMIN", "ROLE_PRODUCER" })
public class AudiovisualMaterialController {

	@Autowired
	private IAudiovisualMaterialService audiovisualMaterialService;

	// Listar
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page, String busqueda,
			RedirectAttributes flash) {

		if (busqueda != null) {
			List<AudiovisualMaterial> listMaterials = audiovisualMaterialService.findBymaterialName(busqueda);

			if (listMaterials.size() == 0) {
				String mensaje = "No se ha encontrado el material con ese nombre";
				flash.addFlashAttribute("error", mensaje);
				return "redirect:/audiovisualMaterial/listAll";
			}

			model.addAttribute("titulo", "Busqueda realizada de materiales");
			model.addAttribute("listMaterials", listMaterials);

			return "audiovisualMaterial/search";
		}

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<AudiovisualMaterial> audiovisualMaterials = audiovisualMaterialService.findAll(pageRequest);
		PageRender<AudiovisualMaterial> pageRender = new PageRender<>("/audiovisualMaterial/listAll",
				audiovisualMaterials);
		model.addAttribute("page", pageRender);

		model.addAttribute("titulo", "Listado de Materiales Audiovisuales");
		model.addAttribute("listMaterials", audiovisualMaterials);

		return "audiovisualMaterial/listAll";
	}

	// Crear
	@GetMapping("/create")
	public String create(Model model) {

		AudiovisualMaterial audiovisualMaterial = new AudiovisualMaterial();
		model.addAttribute("titulo", "Añadir Material");
		model.addAttribute("audiovisualMaterial", audiovisualMaterial);
		return "audiovisualMaterial/create";
	}

	// Guardar
	@RequestMapping(value = "/save", method = RequestMethod.POST) // Para crear los flash, es como un model
	public String save(@Valid AudiovisualMaterial audiovisualMaterial, BindingResult binding, Model model,
			RedirectAttributes flash, SessionStatus status, @RequestParam("file") MultipartFile photo) {

		if (binding.hasErrors()) {
			model.addAttribute("titulo", "Formulario de materiales audiovisuales");
			return "audiovisualMaterial/create";
		}

		if (!photo.isEmpty()) {

			String rootPath = "/opt/tomcat/webapps/uploads/audiovisualMaterials";
			

			try {
				byte[] bytes = photo.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente " + photo.getOriginalFilename());
				audiovisualMaterial.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// 2 mensajes segun si la id del menu es null
		String mensajeFlash = (audiovisualMaterial.getId() != null) ? "Material editado con éxito"
				: "Material creado con éxito";

		audiovisualMaterialService.save(audiovisualMaterial);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/audiovisualMaterial/listAll";
	}

	// Detalle
	@GetMapping("/detail/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		AudiovisualMaterial audiovisualMaterial = audiovisualMaterialService.findOne(id);
		if (audiovisualMaterial == null) {
			flash.addFlashAttribute("error", "El material audiovisual no existe en la base de datos");
			return "redirect:/listall";
		}
		model.addAttribute("material", audiovisualMaterial);
		model.addAttribute("titulo", "Detalle del material: " + audiovisualMaterial.getMaterialName());

		return "audiovisualMaterial/detail";
	}

	// Editar

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		AudiovisualMaterial audiovisualMaterial = null;
		if (id > 0) {
			audiovisualMaterial = audiovisualMaterialService.findOne(id);
			if (audiovisualMaterial == null) {
				flash.addFlashAttribute("error", "El ID del material no existe en la base de datos");
				return "redirect:/audiovisualMaterial/listAll";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del material no puede ser cero");
			return "redirect:/audiovisualMaterial/listAll";
		}

		model.addAttribute("titulo", "Editar Material audiovisual");
		model.addAttribute("audiovisualMaterial", audiovisualMaterial);
		return "audiovisualMaterial/create";
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			audiovisualMaterialService.delete(id);
			flash.addFlashAttribute("success", "material audiovisual eliminado con éxito");
			// Pasos para poder eliminar una foto cuando borramos un cliente.
			// resolve("nombre del archivo")

		}
		return "redirect:/audiovisualMaterial/listAll";
	}
}
