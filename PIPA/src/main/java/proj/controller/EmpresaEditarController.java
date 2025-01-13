package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import proj.dao.EmpresaRepository;
import proj.model.Empresa;

@Controller
public class EmpresaEditarController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/perfil-editar/{id}")
    public String editarPerfil(@PathVariable Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            model.addAttribute("empresa", empresa);
            return "PerfilEditar"; 
        } else {
            return "redirect:/erro";
        }
    }

    @PostMapping("/perfil-editar")
    public String salvarPerfil(@ModelAttribute Empresa empresa) {
        empresaRepository.save(empresa); 
        return "redirect:/perfil"; 
    }
}
