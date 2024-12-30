
/*---------------------------------------------------------------------
 * Funções de validação
 *---------------------------------------------------------------------*/
function validarCamposLogin(frm) {
    var login = frm.login.value;
    var senha = frm.senha.value;
    var result = false;
    
    if (login === "") {
        alert("Preencha o campo usuário!");
        frm.login.focus();
    } else if (senha === "") {
        alert("Preencha o campo senha!");
        frm.senha.focus();
    } else {
        frm.action = "/sgb/main?acao=Logar";
        frm.submit();
        result = true;
    }
    return result;
}

function validarAutor(frm) {
    let result = false;
    if (frm.nomeAutor.value.trim() === "") {
        alert("Informar o nome!");
        frm.nomeAutor.focus();
    } else {
        result = true;
    }
    return result;
}

function validarUsuario(frm) {
    var result = false;
    
    if (frm.login.value === "") {
        alert("Informar o login!");
        frm.login.focus();
    } else if (frm.nome.value === "") {
        alert("Informar o nome!");
        frm.nome.focus();
    } else if (frm.senha.value === "") {
        alert("Informar a senha!");
        frm.senha.focus();
    } else if (frm.email.value === "") {
        alert("Informar o email!");
        frm.email.focus();
    } 
    else
        result = true;
        
    return result;
}

function validarLogin(frm){
    var result = false;

    if(frm.login === ""){
        alert("Informar o login!");
        frm.login.focus();
    }
    else
        result = true;

    return result;
}

function validarPessoa(frm) {
    var result = false;
    
    if (frm.cpf.value === "") {
        alert("Informar o cpf!");
        frm.cpf.focus();
    } else if (frm.login.value === "") {
        alert("Informar o Login!");
        frm.login.focus();
    } else if (frm.nome.value === "") {
        alert("Informar o nome!");
        frm.nome.focus();
    } else if (frm.senha.value === "") {
        alert("Informar a senha!");
        frm.senha.focus();
    } else if (frm.email.value === "") {
        alert("Informar o email!");
        frm.email.focus();
    } 
    else
        result = true;
        
    return result;
}

function validarCamposPesquisaObra(frm){
    let tipo = frm.tipo.value;
    let nome = frm.nome.value;

    if(tipo == "null"){
        alert("Escolha um tipo de pesquisa!");
        frm.tipo.focus();
    }
    else if (nome == ""){
        alert("Preencha o campo de nome!");
        frm.nome.focus();
    }
    else{
        frm.action = "/sgb/main?acao=ObraPesquisar";
        frm.submit();
    }
}

function validarReservaPesquisaExemplar(frm){
    let tipo = frm.tipo.value;
    let nome = frm.nome.value;

    if(tipo == "null"){
        alert("Escolha um tipo de pesquisa!");
        frm.tipo.focus();
    }
    else if (nome == ""){
        alert("Preencha o campo de nome!");
        frm.nome.focus();
    }
    else{
        frm.action = "/sgb/main?acao=ReservarPesquisarExemplar";
        frm.submit();
    }
}

function validarPesquisaReservas(frm){
    let tipo = frm.tipo.value;
    let nome = frm.nome.value;

    if(tipo == "null"){
        alert("Escolha um tipo de pesquisa!");
        frm.tipo.focus();
    }
    else if (nome == ""){
        alert("Preencha o campo de nome!");
        frm.nome.focus();
    }
    else{
        frm.action = "/sgb/main?acao=ReservarPesquisar";
        frm.submit();
    }
}

function validarObra(frm){
    let result = false;
    if (frm.titulo.value === "") {
        alert("Informar o título!");
        frm.titulo.focus();
    } else if (frm.autores.value === ""){
        alert ("Insira ao menos algum autor!");
    } else if (frm.assuntos.value === ""){
        alert ("Insira ao menos algum assunto!");
    } else if (frm.ano.value === "") {
        alert("Informar o ano de publicação!");
        frm.ano.focus();
    } else if (frm.editora.value === "") {
        alert("Informar a editora!");
        frm.editora.focus();
    } else if (frm.cidEditora.value === "") {
        alert("Informar a cidade da editora!");
        frm.cidEditora.focus();
    } else if (frm.edicao.value === "") {
        alert("Informar a edição!");
        frm.edicao.focus();
    } else if (frm.volume.value === "") {
        alert("Informar o volume!");
        frm.volume.focus();
    }
    else
        result = true;

    return result;
}

function validarAquisicaoLeitor(frm){
    let result = false;
    if (frm.titulo.value === "") {
        alert("Informar o título!");
        frm.titulo.focus();
    }
    else{
        result = true;
    }
    return result;
}

function validarBiblioteca(frm) {
    let result = false;
    
    if (frm.uOrgNome.value === "") {
        alert("Informar a unidade organizacional.");
        frm.uOrgNome.focus();
    }
    else if (frm.bibliotecaNome.value === "") {
        alert("Informar o nome da biblioteca.");
        frm.bibliotecaNome.focus();
    }
    else
        result = true;
    
    return result;
}

function validarUnidadeOrganizacional(frm){
    let result = false;
    if (frm.nome.value === "") {
        alert("Informar o nome!");
        frm.nome.focus();
    } 
    else if (frm.endereco.value === ""){
        alert ("Insira o endereço!");
        frm.endereco.focus();
    }
    else
        result = true;

    return result;
}

function validarAssunto(frm){
    if (frm.nome.value === "") {
        alert("Informar o nome!");
        frm.nome.focus();
        return false;
    } 
    else
        return true;
}

function validarFornecedor(frm) {
    var result = false;
    if (frm.cnpj.value === "") {
        alert("Informar o cnpj!");
        frm.cnpj.focus();
    } else if (frm.nome.value === "") {
        alert("Informar o nome!");
        frm.nome.focus();
    } else if (frm.email.value === "") {
        alert("Informar o email!");
        frm.email.focus();
    } else if (frm.telefone.value === "") {
        alert("Informar o telefone!");
        frm.telefone.focus();
    } else if (frm.endereco.value === "") {
        alert("Informar o endereco!");
        frm.endereco.focus();
    } else if (frm.cep.value === "") {
        alert("Informar o cep!");
        frm.cep.focus();
    } 
    else
        result = true;

    return result;
}

function validarLogin(frm){
    var result = false;

    if(frm.login.value === ""){
        alert("Informar o login!");
        frm.login.focus();
    }
    else
        result = true;

    return result;
}

function validarAquisicao(frm){
    let result = false;
    if(frm.fornecedor.value === ""){
        alert("Insira um fornecedor.");
        frm.fornecedor.focus();
    }
    else if(frm.quantidade.value === ""){
        alert("Insira uma quantidade.");
        frm.quantidade.focus();
    }
    else{
        result = true;
    }
    return result;
}

function validarDevolucao(frm) {
    let result = false;
    
    if(frm.id.value === ""){
        alert("Selecione um exemplar para a devolução.");
        frm.id.focus();
    } 
    else
        result = true;

    return result;
}
    
function validarEmprestimo(frm){
    let result = false;

    if(frm.idEmprestimo.value === ""){
        alert("Selecione um exemplar para o empréstimo.");
        frm.id.focus();
    }else if(frm.loginLeitor.value === ""){
        alert("Digite um login do leitor.");
        frm.loginLeitor.focus();
    }else{
        result = true;
    }
    console.log(frm.loginLeitor.value);

    return result;
}

/*---------------------------------------------------------------------
 * Funções de pesquisa
 *---------------------------------------------------------------------*/


function pesquisar(frm){
    var table = frm.table.value;
    
    if (table === "Pessoa") {
        if (frm.acao.value === "pesquisarPorLogin") {
            if (frm.login.value === "") {
                alert("Informar o login!");
                frm.login.focus();
            } else {
                frm.action = "/sgb/main?acao=PessoaPesquisar&PessoaLogin=" + frm.login.value;            
                frm.submit();
            }
        }
    }
    else if (table === "Autor") {
        frm.action = "/sgb/main?acao=AutorPesquisar";
        frm.submit();
    }

    else if (table === "Atendente"){
        frm.action = "/sgb/main?acao=AtendenteListar";            
        frm.submit();
    }

    else if(table === "Bibliotecario"){
        frm.action = "/sgb/main?acao=BibliotecarioListar";            
        frm.submit();
    }

    else if(table === "Emprestimo"){
        frm.action = "/sgb/main?acao=EmprestimoListar";
        frm.submit();
    }

}

/*---------------------------------------------------------------------
 * Funções de gravação de alterações
 *---------------------------------------------------------------------*/

function inserirUsuario(frm) {

    if (frm.pessoaId.value === "") {
        alert("Informar o id da pessoa!");
        frm.pessoaId.focus();
    }
    else {
        frm.action = "/sgb/main?acao=UsuarioInserir";
        frm.submit();
    }
}

function gravarAlteracao(frm) {
    var table = frm.table.value;

    if (table === "Administrador") {
        if (validarLogin(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=AdministradorGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=AdministradorGravarInsercao";
            
        }
    }
    else if (table === "Assunto") {
        if (validarAssunto(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=AssuntoGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=AssuntoGravarInsercao";
        }
    }
    else if(table === "Atendente"){
        if(validarLogin(frm)){
            if(frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=AtendenteAlterar";
            else if(frm.acao.value === "gravarAlteracao")
                caminhourl = "/sgb/main?acao=AtendenteGravarAlteracao";
            else if(frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=AtendenteGravarInsercao";
        }
    }   
    else if (table === "Autor") {
        if (validarAutor(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=AutorAlterar";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=AutorCadastrar";
        }
    }        
    else if(table === "Biblioteca"){
        if(validarBiblioteca(frm)){
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=BibliotecaGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=BibliotecaGravarInsercao";
        }
    }
    else if(table === "Bibliotecario"){
        if(validarLogin(frm)){
            if(frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=BibliotecarioAlterar";
            else if(frm.acao.value === "gravarAlteracao")
                caminhourl = "/sgb/main?acao=BibliotecarioGravarAlteracao";
            else if(frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=BibliotecarioGravarInsercao";
        }
    }    
    else if (table === "Fornecedor") {
        if (validarFornecedor(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=FornecedorGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=FornecedorGravarInsercao";
        }
    }
    else if (table === "Gestor") {
        if (validarLogin(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=GestorGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=GestorGravarInsercao";
        }
    }   
    else if (table === "Leitor") {
        if (frm.login.value === "") {
            alert("Informar um login valido!");
            frm.login.focus();
        }else{
            caminhourl = "/sgb/main?acao=LeitorCadastrar";  
        }
    }    
    else if(table === "Obra") {
        if (validarObra(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=ObraGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=ObraGravarInsercao";
        }
    }    
    else if (table === "Pessoa") {
        if (validarPessoa(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=PessoaGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=PessoaGravarInsercao";
        }
    }
    else if (table === "UnidadeOrganizacional") {
        if (validarUnidadeOrganizacional(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=UnidadeOrganizacionalGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=UnidadeOrganizacionalGravarInsercao";
        }
    }
    else if(table === "AquisicaoLeitor") {
        if (validarAquisicaoLeitor(frm)) {
            caminhourl = "/sgb/main?acao=AquisicaoPedidoCriarObra";
        }
    }
    else if(table === "Aquisicao"){
        if(validarAquisicao(frm)){
            caminhourl = "/sgb/main?acao=AquisicaoCriar";
        }
    }
    else if(table === "AquisicaoObra"){
        if(validarObra(frm)){
            caminhourl = "/sgb/main?acao=GravarObraAquisicao";
        }
    }
    
    else if (table === "Usuario") {
        if (validarUsuario(frm)) {
            if (frm.acao.value === "alterar")
                caminhourl = "/sgb/main?acao=UsuarioGravarAlteracao";
            else if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=UsuarioGravarInsercao";
        }
    }

    else if (table === "Devolucao")
        if(validarDevolucao(frm))
            if (frm.acao.value === "gravar")
                caminhourl = "/sgb/main?acao=GravaDevolucao";
    else if (table === "Emprestimo"){
        if(validarEmprestimo(frm)){
            if(frm.acao.value === "solicita"){
                caminhourl = "/sgb/main?acao=EmprestimoSolicitar";
            }
        }
    }
    
    frm.action = caminhourl;
    frm.submit();    
}

/*---------------------------------------------------------------------
 * Funções de exclusão
 *---------------------------------------------------------------------*/
function excluirUsuario(id, tipo, frm) {

    if (confirm('Deseja excluir o ' + tipo  + ' com Id = ' + id + '?')) {
        frm.pessoaId.value = id;
        frm.usuarioTipo.value = tipo;
        frm.action = "/sgb/main?acao=UsuarioExcluir";            
        frm.submit();
    }
} 


function excluir(id, frm) {
    var table = frm.table.value;

    if (table === "Administrador") {
        if (confirm('Deseja excluir o Administrador com Id = ' + id + '?')) {
            frm.pessoaId.value = id;
            frm.action = "/sgb/main?acao=AdministradorExcluir";            
        }
    } 
    else if (table === "Assunto") {
        if (confirm('Deseja excluir o Assunto com Id = ' + id + '?')) {
            frm.assuntoId.value = id;
            frm.action = "/sgb/main?acao=AssuntoExcluir";            
        }
    } 
    else if (table === "Autor") {
        if (confirm('Deseja excluir o Autor com Id = ' + frm.idAutor.value + '?')) {
            frm.action = "/sgb/main?acao=AutorExcluir";
        }
    }
    else if (table === "Biblioteca") {
        if (confirm('Deseja excluir a Biblioteca com Id = ' + id + '?')) {
            frm.bibliotecaId.value = id;
            frm.action = "/sgb/main?acao=BibliotecaExcluir";
        }
    }    
    else if(table === "Fornecedor") {
        if (confirm('Deseja excluir o Fornecedor com o id = ' + id + '?')) {
            frm.fornecedorCNPJ.value = id;
            frm.action = "/sgb/main?acao=FornecedorExcluir";            
        }
    } 
    else if(table === "Obra") {
        if (confirm('Deseja excluir a Obra com Id = ' + id + '?')) {
            frm.obraId.value = id;
            frm.action = "/sgb/main?acao=ObraExcluir";            
        }
    }    
    else if (table === "Pessoa") {
        if (confirm('Deseja excluir o Usuário com Id = ' + id + '?')) {
            frm.pessoaId.value = id;
            frm.action = "/sgb/main?acao=PessoaExcluir";            
        }
    }
    else if(table === "UnidadeOrganizacional") {
        if (confirm('Deseja excluir a Unidade Organizacional com Id = ' + id + '?')) {
            frm.uOrgId.value = id;
            frm.action = "/sgb/main?acao=UnidadeOrganizacionalExcluir";            
        }
    }
    else if (table === "Usuario") {
        if (confirm('Deseja excluir o Usuário com Id = ' + id + '?')) {
            frm.usuarioId.value = id;
            frm.action = "/sgb/main?acao=UsuarioExcluir";            
        }
    }
    frm.submit();    
}
