package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.model.Seguidores;
import proj.model.Usuario;

import proj.dao.HDataSource;
import proj.dao.SeguidoresDao;
import proj.dao.UsuarioDao;


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
                    
                model.addAttribute("Seguidores", s);

            }
            catch(Exception e) {
                e.printStackTrace();
                return "erro";
            }

                return "verSeguidores";
        }

        @GetMapping("/seguidos")
        public String MostrarSeguindo(Model model, Principal principal) {
        
        Seguidores s;
        
        try (Connection conn = ds.getConnection()) {
            //retorna o usuário que faz a requisição
            Usuario  usuarioLogged = UsuarioDao.getByNome( conn, principal.getName() );
            
            s = SeguidoresDao.listSeguidos( conn, usuarioLogged.getId() );
                
            model.addAttribute("Seguidores", s);
        }
        catch(Exception e) {
            e.printStackTrace();
            return "erro";
        }
            return "verSeguidores";
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

            if(SeguidoresDao.isFollowing(conn, seguidor_id, seguindo_id)){

                SeguidoresDao.deleteFollowRelation(conn, seguidor_id, seguindo_id);
                model.addAttribute("message", "Deixou de seguir "+uSeguido.getNome());
            }

            else{
                SeguidoresDao.insertFollowRelation( conn, seguidor_id, seguindo_id );
                model.addAttribute("message", "Seguiu "+uSeguido.getNome());
            }

            return "followingMessage";
        }
        catch(Exception e){
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }
}