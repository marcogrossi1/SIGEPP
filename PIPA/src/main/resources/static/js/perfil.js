let habilitacaoEdicao = document.getElementById('botao-edicao');
let concluirEdicao = document.getElementById('botao-concluir-edicao');
let campoDescricao = document.getElementById('campo-descricao');
let formularioDescricao = document.getElementById('form-descricao');
let adicionarSeccao = document.getElementById('botao-add-seccao');
let containerSeccoes = document.getElementById('container-seccoes');

function submeterFormularios() {
	formularioDescricao.submit();
}

function mostrarEditaveis() {
	campoDescricao.disabled = false;
	adicionarSeccao.style.display = 'block';
}

function esconderEditaveis() {
	campoDescricao.disabled = true;
	adicionarSeccao.style.display = 'none';
}


habilitacaoEdicao.addEventListener('click', function() {
	this.style.display = 'none';
	concluirEdicao.style.display = 'block';
	mostrarEditaveis();
});

concluirEdicao.addEventListener('click', function() {
	this.style.display = 'none';
	habilitacaoEdicao.style.display = 'block';
	
	submeterFormularios();
	esconderEditaveis();
});

adicionarSeccao.addEventListener('click', function() {
	let seccao = document.createElement('section');
	
	seccao.className = 'seccao';
	seccao.textContent = 'Teste!';
	
	containerSeccoes.appendChild(seccao);
});