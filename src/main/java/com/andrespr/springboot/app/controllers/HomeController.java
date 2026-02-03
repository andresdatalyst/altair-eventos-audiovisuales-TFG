package com.andrespr.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/altairAudiovisuales/home";
		// Otra forma
		// return "forward:/app/index"
		// Con el redirect reinicia la página perdiendo los parámetros de http
		// Con forward no los pierde, interesante para el proyecto, solo sirve para
		// rutas del controllador
	}

	@GetMapping("/altairAudiovisuales/home")
	public String index() {
		return "home/index";

	}
}
