let configAlterada = false;

let formApagar = document.createElement('form');
formApagar.id = 'secao-edicao';
formApagar.action = "/configPerfil/confirmar-exclusao-conta"; 
formApagar.method = "POST"; 
formApagar.enctype = "multipart/form-data";

// Criar o campo hidden para o id do usuário
let inputIdUsuario = document.createElement('input');
inputIdUsuario.type = "hidden";
inputIdUsuario.name = "id";
inputIdUsuario.value = usuario.id;

formApagar.appendChild(inputIdUsuario);

let concluirConfig = document.getElementById('botao-concluir-config-conta');
let elementos = document.querySelectorAll('.conta-config');
let formularioContaConfig = document.getElementById('form-conta-config');
let apagarContaBotao = document.getElementById('botao-apagar-conta');

function submeterFormulariosConfig() {
    formularioContaConfig.submit();
}

concluirConfig.addEventListener('click', function() {
	configAlterada = false;
    alert('Alterações salvas com sucesso!');
    submeterFormulariosConfig();
    esconderEditaveis();
});

apagarContaBotao.addEventListener('click', function() {
	let confirmar = confirm("Tem certeza que deseja excluir sua conta? Essa ação não pode ser desfeita.");

    if (confirmar) {
        document.body.appendChild(formApagar);
        formApagar.submit();
    }
});

window.addEventListener('beforeunload', function (event) {
    if (configAlterada) {
        event.preventDefault();
        event.returnValue = 'Você tem alterações não salvas. Tem certeza de que deseja sair?';
    }
});