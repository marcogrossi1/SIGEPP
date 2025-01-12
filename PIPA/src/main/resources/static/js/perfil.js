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

//Banner e foto de perfil

let inputBanner = document.getElementById('input-banner');
let banner = document.getElementById('banner');

let inputFotoPerfil = document.getElementById('input-foto-perfil');
let fotoPerfil = document.getElementById('foto-perfil');

banner.addEventListener('click', function() {
    inputBanner.click();
});

fotoPerfil.addEventListener('click', function() {
    inputFotoPerfil.click();
});

inputBanner.addEventListener('change', function(e) {
    let file = e.target.files[0];
    if (file) {
        let reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('banner').src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});

inputFotoPerfil.addEventListener('change', function(event) {
    let file = event.target.files[0];
    if (file) {
        let reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('foto-perfil').src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});