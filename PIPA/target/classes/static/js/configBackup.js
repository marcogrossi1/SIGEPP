let configAlterada = false;
let edicaoAtiva = false;

let concluirConfig = document.getElementById('botao-concluir-config');
let elementos = document.querySelectorAll('.conta-config');
let formularioContaConfig = document.getElementById('form-conta-config');

let habilitacaoEdicao = document.getElementById('botao-edicao');
let concluirEdicao = document.getElementById('botao-concluir-edicao');
let campoDescricao = document.getElementById('campo-descricao');
let adicionarSeccao = document.getElementById('botao-add-seccao');
let containerSeccoes = document.getElementById('container-seccoes');
let inputBanner = document.getElementById('input-banner');
let banner = document.getElementById('banner');
let inputFotoPerfil = document.getElementById('input-foto-perfil');
let fotoPerfil = document.getElementById('foto-perfil');

let formularioDescricao = document.getElementById('form-descricao');
let formularioBanner = document.getElementById('form-banner');
let formularioFotoPerfil = document.getElementById('form-foto-perfil');

// Contadores para limitar o número de seções
let contadorTextoLivre = 0;
let contadorProjetosConcluidos = 0;
let contadorCompetencias = 0;
let contadorLicencasCertificados = 0;

// Perfil
function submeterFormulariosPerfil() {
    formularioDescricao.submit();
    formularioBanner.submit();
    formularioFotoPerfil.submit();
    document.getElementById('form-titulo-texto-livre').submit();
    document.getElementById('form-logo-texto').submit();
}

function mostrarEditaveis() {
    campoDescricao.disabled = false;
    inputFotoPerfil.disabled = false;
    inputBanner.disabled = false;
    adicionarSeccao.style.display = 'block';

    let botaoApagar = document.querySelectorAll('.seccao-botao-apagar');
    let botaoCriarTopico = document.querySelectorAll('.seccao-botao-criar-topico');
    let botaoApagarTopico = document.querySelectorAll('.seccao-botao-apagar-topico');
	let inputsLogos = document.querySelectorAll('.input-logo');
    let tituloEditavel = document.querySelectorAll('.titulo-seccao');
    let caixasTexto1 = document.querySelectorAll('.caixa-texto-topico');
    let caixasTexto2 = document.querySelectorAll('.caixa-texto-topico2');

	for (let i = 0; i < inputsLogos.length; i++) {
        inputsLogos[i].disabled = false;
    }
	
    for (let i = 0; i < botaoApagar.length; i++) {
        botaoApagar[i].style.display = 'block';
    }

    for (let i = 0; i < botaoCriarTopico.length; i++) {
        botaoCriarTopico[i].style.display = 'block';
    }

    for (let i = 0; i < botaoApagarTopico.length; i++) {
        botaoApagarTopico[i].style.display = 'block';
    }

    for (let i = 0; i < tituloEditavel.length; i++) {
        tituloEditavel[i].disabled = false;
    }

    for (let i = 0; i < caixasTexto1.length; i++) {
        caixasTexto1[i].disabled = false;
    }

    for (let i = 0; i < caixasTexto2.length; i++) {
        caixasTexto2[i].disabled = false;
    }
}


function esconderEditaveis() {
    campoDescricao.disabled = true;
    inputFotoPerfil.disabled = true;
    inputBanner.disabled = true;
    adicionarSeccao.style.display = 'none';

	let botaoApagar = document.querySelectorAll('.seccao-botao-apagar');
    let botaoCriarTopico = document.querySelectorAll('.seccao-botao-criar-topico');
    let botaoApagarTopico = document.querySelectorAll('.seccao-botao-apagar-topico');
	let inputsLogos = document.querySelectorAll('.input-logo');
    let tituloEditavel = document.querySelectorAll('.titulo-seccao');
    let caixasTexto1 = document.querySelectorAll('.caixa-texto-topico');
    let caixasTexto2 = document.querySelectorAll('.caixa-texto-topico2');

	for (let i = 0; i < inputsLogos.length; i++) {
        inputsLogos[i].disabled = true;
    }

    for (let i = 0; i < botaoApagar.length; i++) {
        botaoApagar[i].style.display = 'none';
    }

    for (let i = 0; i < botaoCriarTopico.length; i++) {
        botaoCriarTopico[i].style.display = 'none';
    }

    for (let i = 0; i < botaoApagarTopico.length; i++) {
        botaoApagarTopico[i].style.display = 'none';
    }

    for (let i = 0; i < tituloEditavel.length; i++) {
        tituloEditavel[i].disabled = true;
    }

    for (let i = 0; i < caixasTexto1.length; i++) {
        caixasTexto1[i].disabled = true;
    }

    for (let i = 0; i < caixasTexto2.length; i++) {
        caixasTexto2[i].disabled = true;
    }
}


banner.addEventListener('click', function() {
    inputBanner.click();
});

fotoPerfil.addEventListener('click', function() {
    inputFotoPerfil.click();
});

habilitacaoEdicao.addEventListener('click', function() {
	edicaoAtiva = true;
    this.style.display = 'none';
    concluirEdicao.style.display = 'block';
    mostrarEditaveis();
});

concluirEdicao.addEventListener('click', function() {
    configAlterada = false;
	edicaoAtiva = false;
    //alert('Alterações salvas com sucesso!'); código pra manter!!!
    this.style.display = 'none';
    habilitacaoEdicao.style.display = 'block';
    //submeterFormulariosPerfil(); código pra manter!!!
    esconderEditaveis();
});

adicionarSeccao.addEventListener('click', function() {
    let catalogoSeccao = document.createElement('div');
    catalogoSeccao.id = 'catalogo-seccao';
    catalogoSeccao.innerHTML = `
        <button id="adicionar-texto-livre">Texto Livre</button>
        <button id="adicionar-projetos-concluidos">Projetos Concluídos</button>
        <button id="adicionar-competencias">Competências</button>
        <button id="adicionar-licencas-certificados">Licenças e Certificados</button>
    `;
    document.body.appendChild(catalogoSeccao);

    document.getElementById('adicionar-texto-livre').addEventListener('click', function() {
        if (contadorTextoLivre < 10) {
            adicionarSecao("Texto Livre");
            contadorTextoLivre++;
        } else {
            alert('Limite de 10 seções de Texto Livre atingido!');
        }
        catalogoSeccao.remove();
    });

    document.getElementById('adicionar-projetos-concluidos').addEventListener('click', function() {
        if (contadorProjetosConcluidos < 1) {
            adicionarSecao("Projetos Concluídos");
            contadorProjetosConcluidos++;
        } else {
            alert('Limite de 1 seção de Projetos Concluídos atingido!');
        }
        catalogoSeccao.remove();
    });

    document.getElementById('adicionar-competencias').addEventListener('click', function() {
        if (contadorCompetencias < 1) {
            adicionarSecao("Competências");
            contadorCompetencias++;
        } else {
            alert('Limite de 1 seção de Competências atingido!');
        }
        catalogoSeccao.remove();
    });

    document.getElementById('adicionar-licencas-certificados').addEventListener('click', function() {
        if (contadorLicencasCertificados < 1) {
            adicionarSecao("Licenças e Certificados");
            contadorLicencasCertificados++;
        } else {
            alert('Limite de 1 seção de Licenças e Certificados atingido!');
        }
        catalogoSeccao.remove();
    });
});

containerSeccoes.addEventListener('dragstart', (e) => {
    if (!edicaoAtiva || !e.target.classList.contains('seccao')) {
        e.preventDefault();
        return;
    }
    e.target.classList.add('dragging');
});

containerSeccoes.addEventListener('dragend', (e) => {
    if (e.target.classList.contains('seccao')) {
        e.target.classList.remove('dragging');
    }
});

containerSeccoes.addEventListener('dragover', (e) => {
    if (!edicaoAtiva) {
        e.preventDefault();
        return;
    }
    e.preventDefault();
    let afterElement = getDragAfterElement(containerSeccoes, e.clientY);
    let dragging = document.querySelector('.dragging');
    if (afterElement == null) {
        containerSeccoes.appendChild(dragging);
    } else {
        containerSeccoes.insertBefore(dragging, afterElement);
    }
});


function getDragAfterElement(container, y) {
    if (!edicaoAtiva) return null;
    let draggableElements = [...container.querySelectorAll('.seccao:not(.dragging)')];

    return draggableElements.reduce(
        (closest, child) => {
            let box = child.getBoundingClientRect();
            let offset = y - box.top - box.height / 2;
            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        },
        { offset: Number.NEGATIVE_INFINITY }
    ).element;
}

function adicionarSecao(tipo) {
	configAlterada = true;
    let seccao = document.createElement('section');
    seccao.className = 'seccao';
	seccao.draggable = true;
    let limitador = 0;

    if (tipo !== "Texto Livre") {
        let titulo = document.createElement('h3');
        titulo.innerText = tipo;
        titulo.className = 'titulo-seccao-texto';
        seccao.appendChild(titulo);
    } else {
        let formTitulo = document.createElement('form');
        let texto = document.createElement('input');
        formTitulo.className = 'form-titulo-texto-livre';
        formTitulo.appendChild(texto);
        texto.className = 'titulo-seccao';
        texto.maxLength = 43;
        texto.placeholder = "Sem título";
        seccao.appendChild(formTitulo);
    }

    let botaoApagar = document.createElement('button');
    botaoApagar.className = 'seccao-botao-apagar';
    seccao.appendChild(botaoApagar);
	
	let botaoCriarTopico = document.createElement('button');
    botaoCriarTopico.className = 'seccao-botao-criar-topico';
    seccao.appendChild(botaoCriarTopico);

    botaoApagar.addEventListener('click', function () {
		if(contadorTextoLivre != 0 && tipo === "Texto Livre") {
			contadorTextoLivre--;			
					
		}
		
		if(contadorProjetosConcluidos != 0 && tipo === "Projetos Concluídos") {
			contadorProjetosConcluidos = 0;		
		}
		
		if(contadorCompetencias != 0 && tipo === "Competências") {
			contadorCompetencias = 0;		
		}
		
		if(contadorLicencasCertificados != 0 && tipo === "Licenças e Certificados") {
			contadorLicencasCertificados = 0;						
		}
		
        if (confirm('Você tem certeza que deseja excluir esta seção?')) {
            seccao.remove();
        }
    });

    botaoCriarTopico.addEventListener('click', function () {
        if (tipo !== "Licenças e Certificados") {
            if (limitador < 5) {
				botaoCriarTopico.disabled = false;
                let formCaixaTexto = document.createElement('form');
				let botaoApagarTopico = document.createElement('button');
				botaoApagarTopico.className = 'seccao-botao-apagar-topico';
				
                let caixaTexto = document.createElement('textarea');
                formCaixaTexto.className = "form-caixa-texto";
                caixaTexto.className = 'caixa-texto-topico';
                caixaTexto.placeholder = "Escreva seu texto...";
                caixaTexto.maxLength = 288;
                caixaTexto.rows = "3";
                caixaTexto.cols = "50";
                formCaixaTexto.appendChild(caixaTexto);
				formCaixaTexto.appendChild(botaoApagarTopico);
                seccao.appendChild(formCaixaTexto);
                limitador++;
				
				botaoApagarTopico.addEventListener('click', function () {
					formCaixaTexto.remove();
					if(limitador > 0)
						limitador--;
			    });
            } else {
                alert('Limite de criação de tópicos atingido!');
            }
        } else {
            let formLogoTexto = document.createElement('form');
			let botaoApagarTopico = document.createElement('button');
			botaoApagarTopico.className = 'seccao-botao-apagar-topico';
			formLogoTexto.className = "form-logo-texto";
			let conteinerLogoTexto = document.createElement('div');
			conteinerLogoTexto.className = "form-logo-texto-container"

            let inputLogo = document.createElement('input');
            inputLogo.className = "input-logo";
            inputLogo.type = 'file';
            inputLogo.accept = 'image/*';

            let logoPreview = document.createElement('img');
            logoPreview.className = "logo-preview";
            logoPreview.style.width = '50px';
            logoPreview.style.height = '50px';

            let caixaTexto = document.createElement('textarea');
            caixaTexto.className = 'caixa-texto-topico2';
            caixaTexto.placeholder = "Escreva seu texto...";
            caixaTexto.maxLength = 108;
            caixaTexto.rows = "3";
            caixaTexto.cols = "50";
			
			formLogoTexto.appendChild(inputLogo);
			formLogoTexto.appendChild(logoPreview);
            formLogoTexto.appendChild(caixaTexto);
			conteinerLogoTexto.appendChild(formLogoTexto);
			conteinerLogoTexto.appendChild(botaoApagarTopico);
            seccao.appendChild(conteinerLogoTexto);
            limitador++;

            logoPreview.addEventListener('click', function () {
                inputLogo.click();
            });

            inputLogo.addEventListener('change', function (e) {
                let file = e.target.files[0];
                if (file) {
                    let reader = new FileReader();
                    reader.onload = function (e) {
                        logoPreview.src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            });
			
			botaoApagarTopico.addEventListener('click', function () {
				conteinerLogoTexto.remove();
				if(limitador != 0)
					limitador--;
		    });
        }
    });

    containerSeccoes.appendChild(seccao);
}

document.addEventListener('dragover', (event) => {
    event.preventDefault(edicaoAtiva);

    const scrollSpeed = 6;
    const buffer = 50;

    const mouseY = event.clientY;
    const windowHeight = window.innerHeight;

    if (mouseY < buffer) {
        window.scrollBy(0, -scrollSpeed);
    }

    if (mouseY > windowHeight - buffer) {
        window.scrollBy(0, scrollSpeed);
    }
});

inputBanner.addEventListener('change', function(e) {
    let file = e.target.files[0];
    if (file) {
        let reader = new FileReader();
        reader.onload = function(e) {
			//TEMPORÁRIO PRA APRESENTAÇÃO//////////////////
			let result = e.target.result;
            document.getElementById('banner').src = result;
            localStorage.setItem('bannerImage', result);
			/////////////////////////////////////////////////
            //document.getElementById('banner').src = e.target.result; código pra manter!!!
            configAlterada = true;
        };
        reader.readAsDataURL(file);
    }
});

inputFotoPerfil.addEventListener('change', function(e) {
    let file = e.target.files[0];
    if (file) {
        let reader = new FileReader();
        reader.onload = function(e) {
			//TEMPORÁRIO PRA APRESENTAÇÃO//////////////////
			let result = e.target.result;
			fotoPerfil.src = result;
			localStorage.setItem('profileImage', result);
			/////////////////////////////////////////////////
            //fotoPerfil.src = e.target.result; código pra manter!!!
            configAlterada = true;
        };
        reader.readAsDataURL(file);
    }
});


//TEMPORÁRIO PRA APRESENTAÇÃO///////////////////////////////
window.addEventListener('load', function() {
    let storedBanner = localStorage.getItem('bannerImage');
    if (storedBanner) {
        document.getElementById('banner').src = storedBanner;
    }

    let storedProfileImage = localStorage.getItem('profileImage');
    if (storedProfileImage) {
        fotoPerfil.src = storedProfileImage;
    }
});
////////////////////////////////////////////////////////////////////

window.addEventListener('beforeunload', function (event) {
    if (configAlterada) {
        event.preventDefault();
        event.returnValue = 'Você tem alterações não salvas. Tem certeza de que deseja sair?';
    }
});