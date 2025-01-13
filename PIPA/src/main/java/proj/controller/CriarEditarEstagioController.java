package proj.controller;

import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.model.Estagio;

@Controller
@RequestMapping("empresa/estagio")
//@RequestMapping("/editar-estagio")
public class CriarEditarEstagioController {

    @Autowired
    private HDataSource ds;

    @GetMapping("/criar")
    public String showForm(Model model) {
        model.addAttribute("estagio", new Estagio());
        return "empresa/criar-editar-estagio";
    }
    @GetMapping("/editar/{id}")
    public String showFormEdit(@PathVariable Long id, Model model){
        try(Connection conn = ds.getConnection()){
            model.addAttribute("estagio", EstagioDao.get(conn, id));
        }catch(SQLException ex){
            ex.printStackTrace();
            return mostraPaginaDeErro();
        }
        return "empresa/criar-editar-estagio";
    }

    @PostMapping
    public String submitForm(Estagio estagio) {
         try(Connection conn = ds.getConnection()){
            if(estagio.getId() == 0)
                EstagioDao.insert(conn, estagio);
            else EstagioDao.update(conn, estagio);
         }catch(SQLException ex){
            ex.printStackTrace();
            return mostraPaginaDeErro();
         }
            // EstagioDao.update(ds.getConnection(), e);
        
            //Logger.getLogger(criarEditarEstagioController.class.getName()).log(Level.SEVERE, null, ex);
        
        return "redirect:/estagios";
    }
    public String mostraPaginaDeErro() {
		return "erro";
	}
}

