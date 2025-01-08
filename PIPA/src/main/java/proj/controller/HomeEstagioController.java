package proj.controller;

import java.util.ArrayList;
import java.sql.Connection;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;

import proj.model.Aluno;
import proj.model.Estagio;

@Controller
@RequestMapping("/estagios")
public class HomeEstagioController {
        @Autowired
        private HDataSource ds;
        
        @GetMapping
	public String mostraEstagios(Model model, Principal principal)
	throws Exception{
		
		try(Connection conn = ds.getConnection())
		{
                        Aluno a = AlunoDao.getByCfp(conn, principal.getName());
			ArrayList<Estagio> estagioList = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
                        ArrayList<Estagio> estagioDispList = EstagioDao.list(conn);
                        deslistarEstagioJaInscrito(estagioList, estagioDispList);
                        model.addAttribute("estagioDispList", estagioDispList);
			model.addAttribute("estagioList", estagioList);
		}
		catch(Exception e) {
			
			return mostraPaginaDeErro(model, e.toString());
		}
		return "homeEstagios";
	}
        public String mostraPaginaDeErro(Model model, String st){
            model.addAttribute("err", st);
		return "erro";
	}
        private void deslistarEstagioJaInscrito(ArrayList<Estagio> el, ArrayList<Estagio> edl){
            for(int i = 0; i < edl.size(); i++)
                for(int j = 0; j <el.size(); j++)
                    if(estagioIsEqual(edl.get(i), el.get(j)))
                        edl.remove(i);
                        
                    
        }
        private boolean estagioIsEqual(Estagio e1, Estagio e2){
            if(e1.getId() == e2.getId())
                return true;
            return false;
        }
}
