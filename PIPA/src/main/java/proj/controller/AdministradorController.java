package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Professor;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
	private HDataSource ds;
    
    @GetMapping
	public String mostraPortal(Model model, Principal principal) throws Exception {		
		try(Connection conn = ds.getConnection())
		{
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (u.getRole().equals("Administrador") == false)
			{
				return mostraPaginaDeErro(model , "Usuário não é um Administrador!.");
			}
			
            Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
			
            model.addAttribute("usuario", u);
            model.addAttribute("administrador", adm);
		}
		catch(Exception e) {
			e.printStackTrace();
			return mostraPaginaDeErro(model , "Erro interno na aplicação!.");
		}

		return "administrador/home";
	}	
	
    @GetMapping("/editar-estagio")
	public String editarEstagio(@RequestParam("n") Long estagioId, Model model, Principal principal) throws Exception {
        //try(
            Connection conn = ds.getConnection();//) {
	        
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Estagio e = EstagioDao.get(conn, estagioId);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("estagio", e);

            return "administrador/editarEstagioAdm";
        //}
//  
        //catch(Exception e) {
        //    return "erro";
        //}
    }

    @GetMapping("/editar-projeto")
	public String editarProjeto(@RequestParam("n") Long projetoId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
	        
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Projeto e = ProjetoDao.get(conn, projetoId);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projeto", e);

            return "administrador/editarProjetoAdm";
        }
  
        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/deletar-estagio")
    public String deletarEstagio(@RequestParam("n") Long estagioId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Estagio e = EstagioDao.get(conn, estagioId);

            EstagioDao.delete(conn, e.getId());

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("estagio", e);

            return "/administrador/estagios";
        }

        catch(Exception e) {
            return mostraPaginaDeErro(model, "Este estágio já pertence à uma empresa e, portanto, não pode mais ser deletado.");
        }
    }

    @RequestMapping("/projetos")
    public String listaProjetos(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/projetos";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/deletar-projeto")
    public String deletarProjeto(@RequestParam("n") Integer projetoId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Projeto e = ProjetoDao.get(conn, projetoId);

            ProjetoDao.delete(conn, projetoId);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("estagio", e);

            return "/administrador/estagios";
        }

        catch(Exception e) {
            return mostraPaginaDeErro(model, "Este estágio já pertence à uma empresa e, portanto, não pode mais ser deletado.");
        }
    }

    public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}

    @GetMapping("/perfil")
    public String mostraPerfilPessoal(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());
            
            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);
            
            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/perfil";
        }

        catch(Exception e) {
            return "erro";
        }
    }
    
    @RequestMapping("/estagios")
    public String listaEstagios(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/estagios";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/listar-empresas")
    public String listaEmpresas(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("listaEmpresas", empresas);

            return "administrador/empresas";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @GetMapping("/listar-alunos")
    public String listaAlunos(Model model, Principal principal) throws Exception {
        //try(
            Connection conn = ds.getConnection();//) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Aluno> alunos = AdministradorDao.listAlunos(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("listaAlunos", alunos);

            return "administrador/alunos";
        //}

        //catch(Exception e) {
        //    return "erro";
        //}
    }
    
    @GetMapping("/listar-professores")
    public String listaProfessores(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Professor> professores = ProfessorDao.list(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("listaProfessores", professores);

            return "administrador/professores";
        }

        catch(Exception e) {
            return "erro";
        }
    }
    
    
    @PostMapping("/deletar-aluno")
    public String deletarAluno(@RequestParam("n") Long alunoId, Model model, Principal principal) 
    throws Exception 
    {
        try(Connection conn = ds.getConnection())
        {
            Aluno al = AlunoDao.get(conn, alunoId);
            AlunoDao.delete(conn, al.getId());
            conn.commit();
            return "redirect:/administrador/listar-alunos";
        }
        catch(Exception e) {
            return mostraPaginaDeErro(model, "Não foi possível deletar.");
        }
    }
    
    @PostMapping("/deletar-empresa")
    public String deletarEmpresa(@RequestParam("n") Long empresaId, Model model, Principal principal) 
    throws Exception 
    {
        try(Connection conn = ds.getConnection())
        {
            Empresa e = EmpresaDao.get(conn, empresaId);
            EmpresaDao.delete(conn, e.getId());
            conn.commit();
            return "redirect:/administrador/listar-empresas";
        }
        catch(Exception e) {
        	e.printStackTrace();
            return mostraPaginaDeErro(model, "Não foi possível deletar.");
        }
    }
    
    
    @PostMapping("/deletar-professor")
    public String deletarProfessor(@RequestParam("n") Long professorId, Model model, Principal principal) 
    throws Exception 
    {
        try(Connection conn = ds.getConnection())
        {
            Professor p = ProfessorDao.get(conn, professorId);
            ProfessorDao.delete(conn, p.getId());
            conn.commit();
            return "redirect:/administrador/listar-professores";
        }
        catch(Exception e) {
        	e.printStackTrace();
            return mostraPaginaDeErro(model, "Não foi possível deletar.");
        }
    }
    
    @GetMapping("/listar-alunos/editar")
    public String mostraTelaDeEdicao(@RequestParam("id") Long id, Model model, Principal principal) {
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 Aluno a = AlunoDao.get(conn, id);
    		   		 
    		 
              model.addAttribute("aluno", a);
              
              System.out.println(a);

         }
    	 
    	 catch(Exception e) {
             return mostraPaginaDeErro(model, "Erro");
         }
    	    	
    	    	    	
    	 return "administrador/editarAluno";
    }
    
    @GetMapping("/listar-empresas/editar")
    public String mostraTelaDeEdicaoEmpresa(@RequestParam("id") Long id, Model model, Principal principal) {
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 Empresa e = EmpresaDao.get(conn, id);
    		   		 
    		 
              model.addAttribute("empresa", e);
              
              System.out.println(e);

         }
    	 
    	 catch(Exception e) {
             return mostraPaginaDeErro(model, "Erro");
         }
    	    	
    	    	    	
    	 return "administrador/editarEmpresa";
    }
    
    @GetMapping("/listar-professores/editar")
    public String mostraTelaDeEdicaoProfessor(@RequestParam("id") Long id, Model model, Principal principal) {
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 Professor p = ProfessorDao.get(conn, id);
    		   		 
    		 
              model.addAttribute("professor", p);
              
              System.out.println(p);

         }
    	 
    	 catch(Exception e) {
             return mostraPaginaDeErro(model, "Erro");
         }
    	    	
    	    	    	
    	 return "administrador/editarProfessor";
    }
    
    @PostMapping("/listar-alunos/editado")
    public String alteraAluno(
    		@RequestParam(value = "id", required = false) String id, 
    		@RequestParam(value = "nome", required = false) String nome,
    	    @RequestParam(value = "cpf", required = false) String cpf,
    	    @RequestParam(value = "curso", required = false) String curso,
    	    @RequestParam(value = "campus", required = false) String campus,
    	    @RequestParam(value = "periodo", required = false) String periodo,
    	    @RequestParam(value = "telefone", required = false) String telefone,
    	    @RequestParam(value = "email", required = false) String email,
    	    Model model, Principal principal) {
    	
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 
    		 
    		 long id_conv = Long.parseLong(id);
    		 
    		 Aluno a = AlunoDao.get(conn, id_conv);
    		 
    		 if (nome != null && !nome.isEmpty()) {
    			 a.setNome(nome);
    		 }
    		 
    		 if (cpf != null && !cpf.isEmpty()) {    		 
    			 a.setCpf(cpf);    	
    			 UsuarioDao.updateForNome(conn, a.getUsuario_id(), cpf);
    		 }
    		 
    		 if (curso != null && !curso.isEmpty()) {
    			 a.setCurso(curso);
    		 }
    		 
    		 if (campus != null && !campus.isEmpty()) {
    			 a.setCampus(campus);
    		 }
    		 
    		 if (periodo != null && !periodo.isEmpty()) {
    			 a.setPeriodo(periodo);
    		 }
    		 
    		 if (telefone != null && !telefone.isEmpty()) {
    			 a.setTelefone(telefone);
    		 }
    		 
    		 if (email != null && !email.isEmpty()) {
    			 a.setEmail(email);
    		 }

    		 
    		 System.out.println(a);
    		 
    		 AlunoDao.update(conn, a);
    		 conn.commit();  		 
    		 
    		 

              model.addAttribute("aluno", a);

         }
    	 
    	 catch(Exception e) {
    		 e.printStackTrace();
             return mostraPaginaDeErro(model, "Erro ao atualizar no banco");
         }
    	
    	
    	 return "redirect:/administrador/listar-alunos";
    }
    
    
    @PostMapping("/listar-empresas/editado")
    public String alteraEmpresa(
    		@RequestParam(value = "id", required = false) String id, 
    		@RequestParam(value = "nome", required = false) String nome,
    	    @RequestParam(value = "cnpj", required = false) String cnpj,
    	    @RequestParam(value = "endereco", required = false) String endereco,
    	    @RequestParam(value = "website", required = false) String website,
    	    @RequestParam(value = "area", required = false) String area,
    	    @RequestParam(value = "telefone", required = false) String telefone,
    	    @RequestParam(value = "email", required = false) String email,
    	    Model model, Principal principal) {
    	
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 
    		 
    		 long id_conv = Long.parseLong(id);
    		 
    		 Empresa e = EmpresaDao.get(conn, id_conv);
    		 
    		 if (nome != null && !nome.isEmpty()) {
    			 e.setNome(nome);
    		 }
    		 
    		 if (cnpj != null && !cnpj.isEmpty()) {    		 
    			 e.setCnpj(cnpj);    	
    			 UsuarioDao.updateForNome(conn, e.getUsuario_id(), cnpj);
    		 }
    		 
    		 if (endereco != null && !endereco.isEmpty()) {
    			 e.setEndereco(endereco);
    		 }
    		 
    		 if (website != null && !website.isEmpty()) {
    			 e.setWebsite(website);
    		 }
    		 
    		 if (area != null && !area.isEmpty()) {
    			 e.setArea(area);
    		 }
    		 
    		 if (telefone != null && !telefone.isEmpty()) {
    			 e.setTelefone(telefone);
    		 }
    		 
    		 if (email != null && !email.isEmpty()) {
    			 e.setEmail(email);
    		 }

    		 
    		 System.out.println(e);
    		 
    		 EmpresaDao.update(conn, e);
    		 conn.commit();  		 
    		 
    		 

              model.addAttribute("empresa", e);

         }
    	 
    	 catch(Exception e) {
    		 e.printStackTrace();
             return mostraPaginaDeErro(model, "Erro ao atualizar no banco");
         }
    	
    	
    	 return "redirect:/administrador/listar-empresas";
    }
    
    
    @PostMapping("/listar-professores/editado")
    public String alteraProfessor(
    		@RequestParam(value = "id", required = false) String id, 
    		@RequestParam(value = "nome", required = false) String nome,
    	    @RequestParam(value = "cpf", required = false) String cpf,
    	    @RequestParam(value = "telefone", required = false) String telefone,
    	    @RequestParam(value = "email", required = false) String email,
    	    Model model, Principal principal) {
    	
    	
    	 try(Connection conn = ds.getConnection())
         {
    		 
    		 
    		 long id_conv = Long.parseLong(id);
    		 
    		 Professor p = ProfessorDao.get(conn, id_conv);
    		 
    		 if (nome != null && !nome.isEmpty()) {
    			 p.setNome(nome);
    		 }
    		 
    		 if (cpf != null && !cpf.isEmpty()) {    		 
    			 p.setCpf(cpf);    	
    			 UsuarioDao.updateForNome(conn, p.getUsuario_id(), cpf);
    		 }
    		    		 
    		 if (telefone != null && !telefone.isEmpty()) {
    			 p.setTelefone(telefone);
    		 }
    		 
    		 if (email != null && !email.isEmpty()) {
    			 p.setEmail(email);
    		 }

    		 
    		 System.out.println(p);
    		 
    		 ProfessorDao.update(conn, p);
    		 conn.commit();  		 
    		 
    		 

              model.addAttribute("professor", p);

         }
    	 
    	 catch(Exception e) {
    		 e.printStackTrace();
             return mostraPaginaDeErro(model, "Erro ao atualizar no banco");
         }
    	
    	
    	 return "redirect:/administrador/listar-professores";
    }
    
}


