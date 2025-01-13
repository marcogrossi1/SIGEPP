package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import proj.model.Empresa;

import java.sql.Connection;

@Controller
public class EmpresaEditarController {

    @Autowired
    private Connection connection;

    @GetMapping("/empresa/perfil-editar")
    public String exibirPerfil(Model model) {
        try {
            String query = "SELECT * FROM Empresa WHERE id = ?";
            var stmt = connection.prepareStatement(query);
            stmt.setLong(1, 1); // Substitua pelo ID da empresa
            var rs = stmt.executeQuery();

            if (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getLong("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setWebsite(rs.getString("website"));
                empresa.setArea(rs.getString("area"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setEmail(rs.getString("email"));

                // Adicione o objeto empresa ao modelo
                model.addAttribute("empresa", empresa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "PerfilEditar";
    }


    @PostMapping("/empresa/perfil-editar")
    public String editarPerfil(String nome, String cnpj, String endereco, String website, String area, String telefone, String email) {
        try {
            // Atualiza os dados no banco
            String query = "UPDATE Empresa SET nome = ?, cnpj = ?, endereco = ?, website = ?, area = ?, telefone = ?, email = ? WHERE id = ?";
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setString(2, cnpj);
            stmt.setString(3, endereco);
            stmt.setString(4, website);
            stmt.setString(5, area);
            stmt.setString(6, telefone);
            stmt.setString(7, email);
            stmt.setLong(8, 1); // Substitua pelo ID da empresa
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "PerfilEditar";
    }
}
