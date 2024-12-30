<%-- 
    Document   : criar-oferta
    Created on : 27 de dez de 2024, 10:41:05
    Author     : berna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/core/header.jsp"%> <!--TODO: criar header-->
<!DOCTYPE html>
<head><title>Criar oferta</title></head>
<center><h3>Criar oferta</h3></center>
<section id="form">
    <div id="caixa-form">
        <form name="frmCriarOferta" method="post">
            <div class="oferta-container">
                <label for="createNomeEstagio">Nome: </label>
                <input type="text" id="createNomeEstagio" name="createNomeEstagio"/><br>
                <label>É uma oferta de:</label><br>
                <input type="radio" id="isEstagio" name="isEstagio" value="estagio" checked/>
                <label for="estagio">Estágio</label><br>
                <input type="radio" id="isEmprego" name="isEstagio" value="emprego"/>
                <label for="emprego">Emprego</label><br>
                <label for="createEnderecoEstagio">Endereço: </label>
                <input type="text" id="createEnderecoEstagio" name="createEnderecoEstagio"/><br>
                <label for="createValorEstagio">Salário: </label>
                <input type="number" id="createValorEstagio" name="createValorEstagio" placeholder="Em branco = A combinar"/><br>
                <label for="createDescricaoEstagio">Descrição: </label>
                <textarea rows="10" cols="100" type="text" id="createDescricaoEstagio" name="createDescricaoEstagio"></textarea><br>
                <div class="aviso" id="warn"></div>
                <br><input class="button" type="button" onclick="validarCamposCriarOferta(document.frmCriarOferta)" value="Enviar" />
            </div>
        </form>
    </div>
</section>
<script>
    
function validarCamposCriarOferta(frm){
    let camposNaoPreenchidos = [];
    if (frm.createNomeEstagio.value == "")
        camposNaoPreenchidos.push("Nome");
    
    if (frm.createEnderecoEstagio.value == "")
        camposNaoPreenchidos.push("Endereço");
        
    if (frm.createValorEstagio < 0)
        camposNaoPreenchidos.push("Salário");
        
    if (frm.createDescricaoEstagio.value == "")
        camposNaoPreenchidos.push("Descricao");
    
    
    if(camposNaoPreenchidos.length > 0){
        document.querySelector("#warn").innerHTML = 
            "Por favor, preencha os seguintes campos corretamente:<br>";
        camposNaoPreenchidos.forEach((el) => document.querySelector("#warn").innerHTML += el + "<br>");
    }
    else{
        frm.action = "/pipa/main?acao=ListarOferta"; //todo: terminar isso aqui
        frm.submit();
    }
}
</script>

<%@include file="/core/footer.jsp" %>