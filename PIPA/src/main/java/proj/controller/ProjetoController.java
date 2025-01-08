package proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
	
	@GetMapping
	public String mostraHomeProjetos() {
		return "homeProjetosDePesquisa";
	}
}