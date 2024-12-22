package proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalController {
	
	@GetMapping("/")
	public String mostraPortal() {
		return "portal";
	}
	
	@GetMapping("/teste")
	public String mostraTeste() {
		return "teste";
	}
	
	@GetMapping("/estagios")
	public String mostraHomeEstagios() {
		return "homeEstagios";
	}
	
	@GetMapping("/projetos")
	public String mostraHomeProjetos() {
		return "homeProjetosDePesquisa";
	}
}
