package proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@GetMapping
	public String mostraHomeEmpresa() {
		try {
			return "empresa/home";
		}

		catch(Exception e) {
			return "erro";
		}
	}
}
