package proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
	
	@GetMapping
	public String mostraPerfilPessoal() {
		try {
			return "professor/home";
		}

		catch(Exception e) {
			return "erro";
		}
	}
}
