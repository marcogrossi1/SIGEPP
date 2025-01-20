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

import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Professor;
import proj.model.Seguidores;
import proj.model.Usuario;

import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.SeguidoresDao;
import proj.dao.UsuarioDao;
import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;


@Controller
@RequestMapping("/seguidores")
public class SeguidoresController {
	
    @Autowired
    HDataSource ds;

	@GetMapping
	public String mostraSeguidores(Model model, Principal principal) {
            
            Seguidores s;
            
            try (Connection conn = ds.getConnection()) {

                //retorna o usuário que faz a requisição
                Usuario  usuarioLogged = UsuarioDao.getByNome( conn, principal.getName() );
                
                s = SeguidoresDao.listSeguidores( conn, usuarioLogged.getId() );
                
                if (s.getNumeroSeguidores() > 0){

                    ArrayList<Aluno> alunos = new ArrayList<>();
                    ArrayList<Empresa> empresas = new ArrayList<>();
                    ArrayList<Professor> professores = new ArrayList<>();

                    for (Usuario seguidor : s.getSeguidores()){
                        
                        if (seguidor.getRole().equals("Aluno")) 
                           alunos.add(AlunoDao.getByUsuario_id(conn, seguidor.getId()));
                        
                        else if(seguidor.getRole().equals("Empresa")){
                            Empresa e = EmpresaDao.getByCnpj(conn, seguidor.getNome());
                            empresas.add(e);
                        }

                        else if(seguidor.getRole().equals("Professor")){
                            professores.add( ProfessorDao.getByUsuario_id(conn, seguidor.getId()) );
                        }
                    }
                    model.addAttribute("content", "Lista de Seguidos");
                    model.addAttribute("Alunos", alunos);
                    model.addAttribute("Empresas", empresas);
                    model.addAttribute("Professores", professores);
                    return "verSeguidores";
                }

                else{
                    model.addAttribute("message", "Nenhum seguidor encontrado");
                    return "followingMessage";
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                model.addAttribute("message", e.getMessage());
                return "erro";
            }
        }

        @GetMapping("/seguidos")
        public String MostrarSeguindo(Model model, Principal principal) {
        
        Seguidores s;
        
        try (Connection conn = ds.getConnection()) {
            //retorna o usuário que faz a requisição
            Usuario  usuarioLogged = UsuarioDao.getByNome( conn, principal.getName() );
            
            s = SeguidoresDao.listSeguidos( conn, usuarioLogged.getId() );
                
            if (s.getNumeroSeguidores() > 0){
                //recupera o nome do usuário através do seu Role ()
                ArrayList<Aluno> alunos = new ArrayList<>();
                ArrayList<Empresa> empresas = new ArrayList<>();
                ArrayList<Professor> professores = new ArrayList<>();

                for (Usuario seguidor : s.getSeguidores()){
                    
                    if (seguidor.getRole().equals("Aluno")) 
                       alunos.add( AlunoDao.getByUsuario_id(conn, seguidor.getId()) );
                    
                    else if(seguidor.getRole().equals("Empresa")){
                        Empresa e = EmpresaDao.getByCnpj(conn, seguidor.getNome());
                        empresas.add(e);
                    }

                    else if(seguidor.getRole().equals("Professor")){
                        professores.add( ProfessorDao.getByUsuario_id(conn, seguidor.getId()) );
                    }
                }
                model.addAttribute("content", "Lista de Seguidos");
                model.addAttribute("Alunos", alunos);
                model.addAttribute("Empresas", empresas);
                model.addAttribute("Professores", professores);
                return "verSeguidores";
            }

            else{
                model.addAttribute("message", "Nenhum seguidor encontrado");
                return "followingMessage";
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }

    @GetMapping("/toggle")
    public String toogleSeguidor(Model model, @RequestParam("id") long seguindo_id, Principal principal){
        /*  Se estiver seguindo
         * 
         */
        try (Connection conn = ds.getConnection()) {

            //retorna o usuário que faz a requisição
            Usuario  usuarioLogged = UsuarioDao.getByNome( conn, principal.getName());

            //testa se o usuário que será seguido existe
            Usuario uSeguido = UsuarioDao.get(conn, seguindo_id);//retorna uma excessão se não existir

            long seguidor_id = usuarioLogged.getId();

            String nome = null;

            if (uSeguido.getRole().equals("Aluno")) 
                nome = (AlunoDao.getByUsuario_id(conn, uSeguido.getId()))
                .getNome();
                    
            else if(uSeguido.getRole().equals("Empresa")){
                nome = EmpresaDao.getByCnpj(conn, uSeguido.getNome())
                    .getNome();
                }

            else if(uSeguido.getRole().equals("Professor")){
                nome = ProfessorDao.getByUsuario_id(conn, uSeguido.getId())
                    .getNome();
            }

            if(SeguidoresDao.isFollowing(conn, seguidor_id, seguindo_id)){

                SeguidoresDao.deleteFollowRelation(conn, seguidor_id, seguindo_id);
                model.addAttribute("message", "Deixou de seguir "+nome);
            }

            else{
                SeguidoresDao.insertFollowRelation( conn, seguidor_id, seguindo_id );
                model.addAttribute("message", "Seguiu "+nome);
            }

            return "followingMessage";
        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }
}