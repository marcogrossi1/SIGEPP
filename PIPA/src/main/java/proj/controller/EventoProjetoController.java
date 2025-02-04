package proj.controller;

import proj.dao.EventoProjetoDao;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.dao.HDataSource;

import proj.model.EventoProjeto;
import proj.model.Projeto;
import proj.model.Usuario;

import java.security.Principal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/eventos")
public class EventoProjetoController {

    @Autowired
    private HDataSource ds;
    @GetMapping
    public String getEventos(Model model, @RequestParam("id") long projeto_id, Principal principal){

        HashMap<Long, String> imagens = new HashMap<>();


        try(Connection conn = ds.getConnection()) {

            Usuario u = UsuarioDao.getByNome(conn, principal.getName());

            if (!u.getRole().equals("Professor")) {
                throw new Exception("Acesso inválido"); 
            }

            Projeto p = ProjetoDao.get(conn, projeto_id);

            ArrayList<EventoProjeto> eventos = EventoProjetoDao.listByProjeto_id(conn, p.getId());

            for (EventoProjeto e : eventos) {
                if (e.getImagem() != null) {
			    String fotoPerfilBase64 = Base64.getEncoder().encodeToString(e.getImagem());
			    imagens.put(e.getId(), fotoPerfilBase64);
			    }
            }

            model.addAttribute("projeto", p);
            model.addAttribute("imagens", imagens);
            model.addAttribute("Eventos", eventos);
        }
        catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return "erro";
		}

        return "professor/eventosProjeto";
    }
    @GetMapping("/criar")
    public String setEvento(Model model, @RequestParam("id") long projeto_id, Principal principal){

        model.addAttribute("eventoProjeto", new EventoProjeto());

        try(Connection conn = ds.getConnection()){

            Usuario u = UsuarioDao.getByNome(conn, principal.getName());

            if (!u.getRole().equals("Professor")) {
                throw new Exception("Acesso inválido"); 
            }

            Projeto p = ProjetoDao.get(conn, projeto_id);  
            
            model.addAttribute("projeto", p);
        }
        catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return "erro";
		}

        return "professor/criarEvento";

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "dateExpiracao", new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/criar")
    public String setEvento(Model model, @ModelAttribute EventoProjeto eventoProjeto){

        eventoProjeto.setDatePublicacao(new Date()) ;

        eventoProjeto.setImagem(null); //placeholder (tempo indeterminado)

        try(Connection conn = ds.getConnection()){
            EventoProjetoDao.insert(conn, eventoProjeto);

            return "redirect:../eventos?id=" + eventoProjeto.getProjeto_id() ;
        }
        catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/deletar")
    public String deletarEvento(Model model, @RequestParam("id") long evento_id){

        try(Connection conn = ds.getConnection()){
            EventoProjetoDao.delete(conn, evento_id);

            return "redirect:../";
        }
        catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }
}
