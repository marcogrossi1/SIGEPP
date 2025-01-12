let concluirConfig = document.getElementById('botao-concluir-config');
let elementos = document.querySelectorAll('conta-config');
let formularioContaConfig = document.querySelectorAll('form-conta-config');

function submeterFormularios() {
	formularioContaConfig.submit();
}

for(let i = 0; i < elementos.length; i++)
	elementos[i].addEventListener('input', function() {
		concluirConfig.style.display = 'block';
	});

concluirConfig.addEventListener('click', function() {
	this.style.display = 'none';
	
	submeterFormularios();
});