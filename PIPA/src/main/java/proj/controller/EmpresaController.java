package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import proj.dao.EstagioDao;
import proj.model.Estagio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proj.dao.EmpresaDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Empresa;
import proj.model.Usuario;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	@Autowired
        private HDataSource ds;
	
        @GetMapping
	public String mostraHomeEmpresa(Model model, Principal principal) {
		try (Connection conn = ds.getConnection()){
                    Usuario u = UsuarioDao.getByNome(conn, principal.getName());
                    if (u.getRole().equals("Empresa") == false)
			return mostraPaginaDeErro(model , "Usuário não é uma Empresa!.");
			
                    Empresa empresa = EmpresaDao.getByCnpj(conn, principal.getName());
                    ArrayList<Estagio> e = EmpresaDao.listEstagiosByEmpresaId(conn, empresa.getId());
                    model.addAttribute("empresa", empresa);
                    model.addAttribute("estagioList", e);
			return "empresa/home";
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
                if(es.getDocumentos() != null){
                    docArr = es.getDocumentos().split(",");
                    if(docArr.length == 1 && docArr[0].trim().equals("")) docArr = null;
                }
                model.addAttribute("documentos", docArr);
                model.addAttribute("salario", es.getSalario());
            return "empresa/detalhesEstagio";
            }catch(Exception e) {
                return mostraPaginaDeErro(model, e.getMessage());
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
                if(estagio.getDescricao().equals("")) estagio.setDescricao(null);
                if(estagio.getId() == 0)
                    EstagioDao.insert(conn, estagio);
                else EstagioDao.update(conn, estagio);
                conn.commit();
                
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa";  
        }
        @PostMapping("/criar")
        public String criarEstagio(Principal principal, Model model, Estagio estagio){
            try(Connection conn = ds.getConnection()){
                Empresa emp = EmpresaDao.getByCnpj(conn, principal.getName());
                estagio.setEmpresa(emp.getNome());
                System.out.println(estagio.getEmpresa());
                if(estagio.getDescricao().equals("")) estagio.setDescricao(null);
                if(estagio.getId() == 0)
                    EstagioDao.insert(conn, estagio);
                else EstagioDao.update(conn, estagio);
                conn.commit();
                
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa"; 
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
                conn.commit();
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
            return "redirect:/empresa"; 
        }
        
        public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}
        
}

