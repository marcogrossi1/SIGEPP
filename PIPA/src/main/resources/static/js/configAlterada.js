document.addEventListener('DOMContentLoaded', function() {
	let configAlterada = false;
	
	//perfil
	
    let concluirEdicao = document.getElementById('botao-concluir-edicao');
    let campoDescricao = document.getElementById('campo-descricao');
    let adicionarSeccao = document.getElementById('botao-add-seccao');
	
	//configurações
	
	let concluirConfig = document.getElementById('botao-concluir-config');
	let elementos = document.querySelectorAll('conta-config');

	//verificações
	
	for(let i = 0; i < elementos.length; i++)
		elementos[i].addEventListener('input', function() {
		    configAlterada = true;
		});
	
    campoDescricao.addEventListener('input', function() {
        configAlterada = true;
    });

    adicionarSeccao.addEventListener('click', function() {
        configAlterada = true;
    });
	
	adicionarSeccao.addEventListener('click', function() {
        configAlterada = true;
    });

    concluirEdicao.addEventListener('click', function() {
        configAlterada = false;
        alert('Alterações salvas com sucesso!');
    });
	
	concluirConfig.addEventListener('click', function() {
        configAlterada = false;
        alert('Alterações salvas com sucesso!');
    });

    window.addEventListener('beforeunload', function(e) {
        if (configAlterada) {
            let mensagem = "Tem certeza que deseja sair? Configurações não foram salvas.";
            e.preventDefault();
            e.returnValue = mensagem;
            return mensagem;
        }
    });
});