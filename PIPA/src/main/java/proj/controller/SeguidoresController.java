package proj.controller;


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


@Controller
@RequestMapping("/seguidores")
public class SeguidoresController {
	
    @Autowired
    HDataSource ds;

	@GetMapping
	public String mostraSeguidores(Model model, @RequestParam("id") long id) {
            
            Seguidores s;
            
            try{
                
                s = SeguidoresDao.listSeguidores( ds.getConnection(), id);
                
                for(Usuario u : s.getSeguidores()){
                    
                    System.out.println(u.getNome());
                }
                
            
            }
                catch(Exception e) {
                    e.printStackTrace();
                    return "erro";
            }
                    return "verSeguidores";
            }
}

