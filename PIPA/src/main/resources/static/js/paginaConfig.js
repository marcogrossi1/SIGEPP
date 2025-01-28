let configAlterada = false;

let concluirConfig = document.getElementById('botao-concluir-config-conta');
let elementos = document.querySelectorAll('.conta-config');
let formularioContaConfig = document.getElementById('form-conta-config');

function submeterFormulariosConfig() {
    formularioContaConfig.submit();
}

concluirConfig.addEventListener('click', function() {
	configAlterada = false;
    alert('Alterações salvas com sucesso!');
    submeterFormulariosConfig();
    esconderEditaveis();
});


window.addEventListener('beforeunload', function (event) {
    if (configAlterada) {
        event.preventDefault();
        event.returnValue = 'Você tem alterações não salvas. Tem certeza de que deseja sair?';
    }
});