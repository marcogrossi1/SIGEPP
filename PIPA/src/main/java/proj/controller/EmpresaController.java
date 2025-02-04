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

import proj.dao.DocumentoDao;
import proj.model.Documento;
import proj.model.DocumentoId;
import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Empresa;

import proj.model.Progresso;

import proj.model.Estagio;

import proj.model.Usuario;
import proj.service.NotificacaoService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	@Autowired
        private HDataSource ds;
	@Autowired
        private NotificacaoService notificacaoService;
	
	@Autowired
	private DocumentoDao documentoDao;
	
    @GetMapping
    public String mostraHomeEmpresa(Principal principal, Model model) {
    	
        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            if (!u.getRole().equals("Empresa")) {
                return mostraPaginaDeErro(model, "Usuário não é uma Empresa!");
            }

            Empresa e = EmpresaDao.getByUsuario_id(conn, u.getId());

            model.addAttribute("empresa", e);
            return "empresa/home";
        } catch (Exception e) {
            return "erro";
        }
    }	
    
    
        @GetMapping("/estagios")
        public String mostraEstagiosEmpresa(Model model, Principal principal) {
		try (Connection conn = ds.getConnection()){
                    Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                    if (u.getRole().equals("Empresa") == false)
			return mostraPaginaDeErro(model , "Usuário não é uma Empresa!.");
			
                    Empresa empresa = EmpresaDao.getByCnpj(conn, principal.getName());
                    ArrayList<Estagio> e = EmpresaDao.listEstagiosByEmpresaId(conn, empresa.getId());
                    model.addAttribute("empresa", empresa);
                    model.addAttribute("estagioList", e);
			return "empresa/estagios";
		}

		catch(Exception e) {
			return mostraPaginaDeErro(model, e.getMessage());
		}
	}
        @GetMapping("/detalhes-estagio")
        public String mostraDetalhesEstagio(Model model, Principal principal, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Estagio es = EstagioDao.get(conn, id);
                model.addAttribute("empresa", EmpresaDao.getByCnpj(conn, principal.getName()));
                model.addAttribute("estagio", es);
                model.addAttribute("empresaEst", es.getEmpresa());
                model.addAttribute("descricao", es.getDescricao());
                model.addAttribute("cargaHoraria", es.getCargaHoraria());
                model.addAttribute("vagas", es.getVagas());
                model.addAttribute("requisito", es.getRequisito());
                String docArr[] = null;
                if(es.getDocumentos() != null) docArr = es.getDocumentos().split(",");
                model.addAttribute("documentos", docArr);
                model.addAttribute("salario", es.getSalario());
            return "empresa/detalhesEstagio";
            }catch(Exception e) {
                return mostraPaginaDeErro(model, e.getMessage());
            } 
        }
        @GetMapping("/candidatos-estagio")
        public String mostraCandidatosEstagio(Model model, Principal principal, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, id);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                ArrayList<Aluno> aluno = new ArrayList<>();
                ArrayList<Progresso> progresso = new ArrayList<>();
                EstagioDao.listCandidatos(conn, id, aluno, progresso);
                model.addAttribute("empresa", emp);
                model.addAttribute("candidatos", aluno);
                model.addAttribute("progresso", progresso);
                model.addAttribute("estagio", est);
                return "empresa/candidaturas";
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
        }
        
        @GetMapping("/aprovar")
        public String aprovarInscricaoDeAluno(Model model, Principal principal, @RequestParam("a_id") long alunoId, @RequestParam("e_id") long estagioId){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, estagioId);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                if(AlunoDao.getProgressoEstagio(conn, alunoId, estagioId) != Progresso.PENDENTE)
                    return mostraPaginaDeErro(model, "Você não pode efetuar essa operação.");
                AlunoDao.setProgresso(conn, alunoId, estagioId, Progresso.APROVADO);
                conn.commit();
                notificacaoService.salvarNotificacao(AlunoDao.get(conn, alunoId).getUsuario_id(), "Inscrição aprovada no estágio da empresa " + est.getEmpresa());
                return "redirect:/empresa/candidatos-estagio?n=" + estagioId;
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
        }
        @GetMapping("/concluir")
        public String concluirEstagioDeAluno(Model model, Principal principal, @RequestParam("a_id") long alunoId, @RequestParam("e_id") long estagioId){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, estagioId);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                if(AlunoDao.getProgressoEstagio(conn, alunoId, estagioId) != Progresso.APROVADO)
                    return mostraPaginaDeErro(model, "Você não pode efetuar essa operação.");
                AlunoDao.setProgresso(conn, alunoId, estagioId, Progresso.CONCLUIDO);
                String str = EstagioDao.get(conn, estagioId).getDocumentos();
                if(str != null && !str.equals("")){
                    try{
                        DocumentoId documentoId = new DocumentoId(alunoId, estagioId);
                        documentoDao.deletarPorId(documentoId);
                        notificacaoService.salvarNotificacao(AlunoDao.get(conn, alunoId).getUsuario_id(), "Você foi desinscrito de um estágio pela empresa " + est.getEmpresa());
                    }catch(RuntimeException e){}
                }
                conn.commit();
                notificacaoService.salvarNotificacao(AlunoDao.get(conn, alunoId).getUsuario_id(), "Estágio da empresa " + est.getEmpresa() + " concluído.");

                return "redirect:/empresa/candidatos-estagio?n=" + estagioId;
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
        }
        
        @GetMapping("/remover")
        public String removerAlunoDoEstagio(Model model, Principal principal, @RequestParam("a_id") long alunoId, @RequestParam("e_id") long estagioId){
        	 
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
           
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, estagioId);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                if(AlunoDao.getProgressoEstagio(conn, alunoId, estagioId) == Progresso.CONCLUIDO)
                    return mostraPaginaDeErro(model, "Você não pode efetuar essa operação.");
                AlunoDao.desinscreverEstagio(conn, alunoId, estagioId);
                conn.commit();
                String str = EstagioDao.get(conn, estagioId).getDocumentos();
                if(str != null && !str.equals("")){
                    DocumentoId documentoId = new DocumentoId(alunoId, estagioId);
                    documentoDao.deletarPorId(documentoId);
                    notificacaoService.salvarNotificacao(AlunoDao.get(conn, alunoId).getUsuario_id(), "Você foi desinscrito de um estágio pela empresa " + est.getEmpresa());
                }
                conn.commit();
                return "redirect:/empresa/candidatos-estagio?n=" + estagioId;
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
        }
        @GetMapping("/criar")
        public String criarEstagio(Model model, Principal principal){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio e = new Estagio();
                e.setEmpresa(emp.getNome());
                model.addAttribute("estagio", new Estagio());
                model.addAttribute("empresa", emp);
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "empresa/criar-editar-estagio";
        }
        @GetMapping("/editar")
        public String editarEstagio(Model model, Principal principal, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, id);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                model.addAttribute("empresa", emp);
                model.addAttribute("estagio", est);
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "empresa/criar-editar-estagio";
        }
        @PostMapping("/editar")
        public String updateEstagio(Model model, Estagio estagio){
            try(Connection conn = ds.getConnection()){
                if(estagio.getDocumentos().equals(""))
                    estagio.setDocumentos(null);
                if(estagio.getId() == 0)
                    EstagioDao.insert(conn, estagio);
                else EstagioDao.update(conn, estagio);
                conn.commit();
                
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa/estagios";  
        }
        @PostMapping("/criar")
        public String criarEstagio(Principal principal, Model model, Estagio estagio){
            try(Connection conn = ds.getConnection()){
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                estagio.setEmpresa(emp.getNome());
                if(estagio.getDocumentos().equals(""))
                    estagio.setDocumentos(null);
                System.out.println(estagio.getEmpresa());
                if(estagio.getId() == 0)
                    EstagioDao.insert(conn, estagio);
                else EstagioDao.update(conn, estagio);
                conn.commit();
                
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa/estagios"; 
        }
        
        @GetMapping("/apagar")
        public String apagarEstagio(Principal principal, Model model, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                if (u.getRole().equals("Empresa") == false)
                    return mostraPaginaDeErro(model , "Usuário não é uma Empresa!");
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                Estagio est = EstagioDao.get(conn, id);
                if(!emp.getNome().equals(est.getEmpresa()))
                    return mostraPaginaDeErro(model, "Estágio selecionado não pertence à empresa.");
                EstagioDao.delete(conn, id);
                documentoDao.deletarTodosPorProjetoId(id);
                conn.commit();
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa/estagios"; 
        }
            
            
        public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}
        
}

