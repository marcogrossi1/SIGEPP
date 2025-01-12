package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String mostraPortal(Model model, Principal principal)
	throws Exception{
		
		//try(
			Connection conn = ds.getConnection();
		//	)
		//{
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (u.getRole().equals("Aluno") == false)
			{
				return mostraPaginaDeErro(model , "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);
		//}
		//catch(Exception e) {
		//	e.printStackTrace();
		//	return mostraPaginaDeErro(model , "Erro interno na aplicação!.");
		//}

		return "aluno/home";
	}	
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}

	
	@GetMapping("/estagios")
	public String mostraEstagios(Model model, Principal principal)
	throws Exception{
		
		Connection conn = ds.getConnection();
		Usuario u = UsuarioDao.getByNome(conn, principal.getName());
		if (u.getRole().equals("Aluno") == false){
			return mostraPaginaDeErro(model , "Usuário não é um Aluno!.");
		}
                
                Aluno a = AlunoDao.getByCpf(conn, principal.getName());
                ArrayList<Estagio> estagioList = AlunoDao.listEstagiosByAlunoId(conn, a.getId());                        
                ArrayList<Estagio> estagioDispList = EstagioDao.list(conn);
                System.out.println("TESTE");
                System.out.println(estagioList);
                System.out.println(estagioDispList);
                deslistarEstagioJaInscrito(estagioList, estagioDispList);
                model.addAttribute("estagioDispList", estagioDispList);
                model.addAttribute("estagioList", estagioList);			
		return "aluno/estagios";
	}
        @GetMapping("/detalhes-estagio")
        public String getDetalhesEstagio(Model model, Principal principal, @RequestParam("n") long id) throws Exception{
        try(Connection conn = ds.getConnection()){
            Estagio es = EstagioDao.get(conn, id);
            model.addAttribute("estagio", es);
            model.addAttribute("empresa", es.getEmpresa());
            model.addAttribute("descricao", es.getDescricao());
            model.addAttribute("cargaHoraria", es.getCargaHoraria());
            model.addAttribute("vagas", es.getVagas());
            model.addAttribute("requisito", es.getRequisito());
            model.addAttribute("salario", es.getSalario());
            return "aluno/detalhesEstagio";
        }catch(Exception e) {
                return mostraPaginaDeErro(model, e.getMessage());
        }
    }
	
	@GetMapping("/projetos")
	public String mostraHomeProjetos() {
		return "aluno/projetos";
	}
	
	@GetMapping("/perfil")
	public String mostraPerfilPessoal() {
		return "aluno/perfil";
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
