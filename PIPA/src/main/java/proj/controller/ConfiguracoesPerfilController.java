package proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/configuracoesPerfil")
public class ConfiguracoesPerfilController {
	
	@GetMapping
	public String mostraConfiguracaoPessoal() {
		return "configuracoesPerfil";
	}
}