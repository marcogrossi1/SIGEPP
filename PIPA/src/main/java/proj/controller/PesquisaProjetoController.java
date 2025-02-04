package proj.controller;
/* 
import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import proj.dao.HDataSource;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Projeto;
import proj.model.Usuario;
import proj.ui.model.PesquisaFiltrosProjeto;
import proj.ui.model.PesquisaNomeProjeto;

@Controller
@RequestMapping("/pesquisa")
public class PesquisaProjetoController {
    
    @Autowired
    private HDataSource ds;

    @GetMapping("/listaprojetos")
    public String mostraPainelProjetos(Model model, Principal principal) 
    throws SQLException {
        
        try (Connection conn = ds.getConnection()) {
            ArrayList<Projeto> listaProjetos = ProjetoDao.list(conn);
            ArrayList<String> listaAreas = ProjetoDao.listAreas(conn);

            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            model.addAttribute("usuario", u);
            model.addAttribute("listaProjetos", listaProjetos);
            model.addAttribute("listaAreas", listaAreas);
        }
        
        model.addAttribute("pesqNome", new PesquisaNomeProjeto());
        model.addAttribute("pesqFiltro", new PesquisaFiltrosProjeto());
        return "pesquisa/pesquisaDeProjetos";
    }

    @PostMapping("/pesquisaNomeProjeto")
    public String pesquisaNomeProjeto(Model model, Principal principal, 
            @ModelAttribute("pesqNome") PesquisaNomeProjeto pesqNome,
            BindingResult result) 
    throws SQLException {
        
        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            model.addAttribute("usuario", u);
            ArrayList<String> listaAreas = ProjetoDao.listAreas(conn);
            model.addAttribute("listaAreas", listaAreas);
            model.addAttribute("pesqFiltro", new PesquisaFiltrosProjeto());

            ArrayList<Projeto> listaProjetos;
            if (pesqNome == null || pesqNome.getNome() == null || pesqNome.getNome().trim().isEmpty()) {
                listaProjetos = ProjetoDao.list(conn);
                model.addAttribute("pesqNome", new PesquisaNomeProjeto());
            } else {
                listaProjetos = ProjetoDao.listByNome(conn, pesqNome.getNome());
                if (listaProjetos.isEmpty()) {
                    result.rejectValue("nome", "nome", "Nenhum resultado encontrado para [" + pesqNome.getNome() + "]");
                }
            }
            model.addAttribute("listaProjetos", listaProjetos);
        }
        
        return "pesquisa/pesquisaDeProjetos";
    }

    @PostMapping("/pesquisaFiltroProjeto")
    public String pesquisaProjetoComFiltro(Model model, Principal principal,  
            @Valid @ModelAttribute("pesqFiltro") PesquisaFiltrosProjeto filtro,
            BindingResult result) 
    throws SQLException {
        
        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            model.addAttribute("usuario", u);
            ArrayList<String> listaAreas = ProjetoDao.listAreas(conn);
            model.addAttribute("listaAreas", listaAreas);

            ArrayList<Projeto> listaProjetos = ProjetoDao.listByArea(conn, filtro.getArea());
            if (listaProjetos.isEmpty()) {
                result.reject("lista-vazia", "Nenhum resultado encontrado para a pesquisa.");
            }
            model.addAttribute("listaProjetos", listaProjetos);
        }
        
        model.addAttribute("pesqFiltro", filtro);
        model.addAttribute("pesqNome", new PesquisaNomeProjeto());
        return "pesquisa/pesquisaDeProjetos";
    }
}
*/