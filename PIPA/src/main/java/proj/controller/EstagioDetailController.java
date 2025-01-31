package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.model.Estagio;

@Controller
@RequestMapping("/detalhes-estagio")
public class EstagioDetailController {
    @Autowired
    private HDataSource ds;
    
    @GetMapping
    public String getDetalhes(Model model, Principal principal, @RequestParam("n") long id) throws Exception{
        try(Connection conn = ds.getConnection()){
            Estagio es = EstagioDao.get(conn, id);
            model.addAttribute("estagio", es);
            model.addAttribute("empresa", es.getEmpresa());
            model.addAttribute("descricao", es.getDescricao());
            model.addAttribute("cargaHoraria", es.getCargaHoraria());
            model.addAttribute("vagas", Integer.valueOf(es.getVagas()));
            model.addAttribute("requisito", es.getRequisito());
            model.addAttribute("salario", es.getSalario());
            return "detalhesEstagio";
        }catch(Exception e) {
                e.printStackTrace();
                return mostraPaginaDeErro();
        }
    }
    public String mostraPaginaDeErro() {
		return "erro";
	}
    
}
