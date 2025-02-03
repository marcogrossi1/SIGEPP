package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.AdministradorDao;
import proj.model.Administrador;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Empresa;
import proj.model.Usuario;

@Controller
public class HomeCadastroController {
	
	@Autowired
	private HDataSource ds;

    @GetMapping("/cadastro")
    public String showHomeCadastro() {
        return "homeCadastro"; 
    }

    @GetMapping("/cadastroAluno")
    public String showCadastroAluno() {
        return "cadastroAluno";
    }
    @GetMapping("/cadastroProfessor")
    public String showCadastroProfessor() {
        return "cadastroProfessor"; 
    }

    @GetMapping("/cadastroEmpresa")
    public String showCadastroEmpresa() {
        return "cadastroEmpresa"; 
    }
    @GetMapping("/cadastroAdministrador")
    public String showCadastroAdministrador() {
        return "cadastroAdministrador"; 
    }
    
    @PostMapping("/criarAluno")
	public String criaNovoAluno(
	    @RequestParam(value = "nome", required = false) String nome,
	    @RequestParam(value = "cpf", required = false) String cpf,
	    @RequestParam(value = "curso", required = false) String curso,
	    @RequestParam(value = "campus", required = false) String campus,
	    @RequestParam(value = "email", required = false) String email,
	    @RequestParam(value = "periodo", required = false) String periodo,
	    @RequestParam(value = "telefone", required = false) String telefone,
	    @RequestParam(value = "senha", required = false) String senha,
	    Principal principal, Model model) throws Exception {
	    try (Connection conn = ds.getConnection()) {
	        Aluno a = new Aluno();
	        Usuario u = new Usuario();
	        u.setRole("Aluno");

	        if (senha != null && !senha.isEmpty()) {
	            String senhaCriptografada = DigestUtils.sha512Hex(senha);
	            u.setSenha(senhaCriptografada);
	        }

	        if (u.getRole().equals("Aluno")) {
	            if (nome != null && !nome.isEmpty()) {
	                a.setNome(nome);
	            }
	            if (curso != null && !curso.isEmpty()) {
	                a.setCurso(curso);
	            }
	            if (campus != null && !campus.isEmpty()) {
	                a.setCampus(campus);
	            }
	            if (email != null && !email.isEmpty()) {
	                a.setEmail(email);
	            }
	            if (periodo != null && !periodo.isEmpty()) {
	                a.setPeriodo(periodo);
	            }
	            if (telefone != null && !telefone.isEmpty()) {
	                a.setTelefone(telefone);
	            }
	            
	            a.setDescricaoPerfil("Sem descrição.");
	            /*a.setFotoPerfil(null);
	            a.setBannerPerfil(null);*/
	            
	            if (cpf != null && !cpf.isEmpty()) {
	                a.setCpf(cpf);
	                u.setNome(cpf);
	            }

	            UsuarioDao.insert(conn, u);
	            
	            a.setUsuario_id(u.getId());

	            AlunoDao.insert(conn, a);
	            
	            conn.commit();

	            System.out.println("Valores recebidos:");
	            System.out.println("Nome: " + nome);
	            System.out.println("CPF: " + cpf);
	            System.out.println("Curso: " + curso);
	            System.out.println("Campus: " + campus);
	            System.out.println("Email: " + email);
	            System.out.println("Período: " + periodo);
	            System.out.println("Telefone: " + telefone);
	            System.out.println("Senha: " + senha);
	        }
	    }
	    return "redirect:/aluno";

	    //AQUI Q TÁ DANDO ERRO DO REDIRECT
	}
	
	//public String mostraPaginaDeErro(Model model, String message) {
	//	model.addAttribute("message",message);
		//return "erro";
	//}	

////////////////////////////////////////////////////////////////////////////////////////////////////////////
@PostMapping("/criarProfessor")
public String criarProfessor(
	@RequestParam(value = "nome", required = false) String nome,
	@RequestParam(value = "cpf", required = false) String cpf,
	@RequestParam(value = "departamento", required = false) String departamento,
	@RequestParam(value = "email", required = false) String email,
	@RequestParam(value = "telefone", required = false) String telefone,
	@RequestParam(value = "senha", required = false) String senha,
	Principal principal, Model model) throws Exception {
	try (Connection conn = ds.getConnection()) {
		Professor p = new Professor();
		Usuario u = new Usuario();
		u.setRole("Professor");

		if (senha != null && !senha.isEmpty()) {
			String senhaCriptografada = DigestUtils.sha512Hex(senha);
			u.setSenha(senhaCriptografada);
		}

		if (u.getRole().equals("Professor")) {
			if (nome != null && !nome.isEmpty()) {
				p.setNome(nome);
			}
			if (departamento != null && !departamento.isEmpty()) {
				p.setDepartamento(departamento);
			}
			if (email != null && !email.isEmpty()) {
				p.setEmail(email);
			}
			if (telefone != null && !telefone.isEmpty()) {
				p.setTelefone(telefone);
			}
			
			p.setDescricaoPerfil("Sem descrição.");
			/*p.setFotoPerfil(null);
			p.setBannerPerfil(null);*/
			
			if (cpf != null && !cpf.isEmpty()) {
				p.setCpf(cpf);
				u.setNome(cpf);
			}

			UsuarioDao.insert(conn, u);
			
			p.setUsuario_id(u.getId());

			ProfessorDao.insert(conn, p);
			
			conn.commit();

			System.out.println("Valores recebidos:");
			System.out.println("Nome: " + nome);
			System.out.println("CPF: " + cpf);
			System.out.println("Departamento: " + departamento);
			System.out.println("Email: " + email);
			System.out.println("Telefone: " + telefone);
			System.out.println("Senha: " + senha);
		}
	}
	return "professor";
	//AQUI Q TÁ DANDO ERRO DO REDIRECT
}

  // public String mostraPaginaDeErro(Model model, String message) {
    //     model.addAttribute("message", message);
    //     return "erro";
    // }
//////////////////////////////////////////////////////////////////////////////////////
@PostMapping("/criarEmpresa")
public String criaNovaEmpresa(
	@RequestParam(value = "nome", required = false) String nome,
	@RequestParam(value = "cnpj", required = false) String cnpj,
	@RequestParam(value = "area", required = false) String area,
	@RequestParam(value = "endereco", required = false) String endereco,
	@RequestParam(value = "website", required = false) String website,
	@RequestParam(value = "email", required = false) String email,
	@RequestParam(value = "telefone", required = false) String telefone,
	@RequestParam(value = "senha", required = false) String senha,
	Principal principal, Model model) throws Exception {
	try (Connection conn = ds.getConnection()) {
		Empresa e = new Empresa();
		Usuario u = new Usuario();
		u.setRole("Empresa");

		if (senha != null && !senha.isEmpty()) {
			String senhaCriptografada = DigestUtils.sha512Hex(senha);
			u.setSenha(senhaCriptografada);
		}

		if (u.getRole().equals("Empresa")) {
			if (nome != null && !nome.isEmpty()) {
				e.setNome(nome);
			}
			if (area != null && !area.isEmpty()) {
				e.setArea(area);
			}
			if (endereco != null && !endereco.isEmpty()) {
				e.setEndereco(endereco);
			}
			if (website != null && !website.isEmpty()) {
				e.setWebsite(website);
			}
			if (telefone != null && !telefone.isEmpty()) {
				e.setTelefone(telefone);
			}
			if (email != null && !email.isEmpty()) {
				e.setEmail(email);
			}
			
			
			//e.setDescricaoPerfil("Sem descrição.");
			/*e.setFotoPerfil(null);
			e.setBannerPerfil(null);*/
			
			if (cnpj != null && !cnpj.isEmpty()) {
				e.setCnpj(cnpj);
				u.setNome(cnpj);
			}

			UsuarioDao.insert(conn, u);
			
			e.setUsuario_id(u.getId());

			EmpresaDao.insert(conn, e);
			
			conn.commit();

			System.out.println("Valores recebidos:");
			System.out.println("Nome: " + nome);
			System.out.println("CNPJ: " + cnpj);
			System.out.println("Area: " + area);
			System.out.println("Endereço: " + endereco);
			System.out.println("Website: " + website);
			System.out.println("Email: " + email);
			System.out.println("Telefone: " + telefone);
			System.out.println("Senha: " + senha);
		}
	}
	return "empresa";
	//AQUI Q TÁ DANDO ERRO DO REDIRECT
}

//public String mostraPaginaDeErro(Model model, String message) {
//	model.addAttribute("message",message);
//	return "erro";
	//////////////////////////////////////////////////////////////////////////////////////
  @PostMapping("/criarAdministrador")
	public String criaNovoAdministrador(
	    @RequestParam(value = "nome", required = false) String nome,
	    @RequestParam(value = "cpf", required = false) String cpf,
	    @RequestParam(value = "campus", required = false) String campus,
	    @RequestParam(value = "email", required = false) String email,
	    @RequestParam(value = "senha", required = false) String senha,
	    Principal principal, Model model) throws Exception {
	    try (Connection conn = ds.getConnection()) {
	        Administrador a = new Administrador();
	        Usuario u = new Usuario();
	        u.setRole("Administrador");

	        if (senha != null && !senha.isEmpty()) {
	            String senhaCriptografada = DigestUtils.sha512Hex(senha);
	            u.setSenha(senhaCriptografada);
	        }

	        if (u.getRole().equals("Administrador")) {
	            if (nome != null && !nome.isEmpty()) {
	                a.setNome(nome);
	            }
	            if (campus != null && !campus.isEmpty()) {
	                a.setCampus(campus);
	            }
	            if (email != null && !email.isEmpty()) {
	                a.setEmail(email);
	            }
	            
	            if (cpf != null && !cpf.isEmpty()) {
	                a.setCpf(cpf);
	                u.setNome(cpf);
	            }

	            UsuarioDao.insert(conn, u);
	            
	            a.setUsuario_id(u.getId());

	            AdministradorDao.insert(conn, a);
	            
	            conn.commit();

	            System.out.println("Valores recebidos:");
	            System.out.println("Nome: " + nome);
	            System.out.println("CPF: " + cpf);
	            System.out.println("Campus: " + campus);
	            System.out.println("Email: " + email);
	            System.out.println("Senha: " + senha);
	        }
	    }
	    return "administrador";
	}
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "administrador";
	}
}


