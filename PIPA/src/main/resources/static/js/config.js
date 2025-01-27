//PERFIL
let configAlterada = false;
let edicaoAtiva = false;

let verificador = 0;

let habilitacaoEdicao = document.getElementById('botao-edicao');
let concluirEdicao = document.getElementById('botao-concluir-edicao');
let campoDescricao = document.getElementById('campo-descricao');
let botaoSeccao = document.getElementById('botao-add-seccao');
let containerSeccoes = document.getElementById('container-seccoes');
let inputBanner = document.getElementById('input-banner');
let banner = document.getElementById('banner');
let inputFotoPerfil = document.getElementById('input-foto-perfil');
let fotoPerfil = document.getElementById('foto-perfil');

let formAtualizar = document.getElementById('form-atualizar');

function submeterFormulariosPerfil() {
    let descricaoMudou = campoDescricao && campoDescricao.value.trim() !== 'Sem descrição';
    let imagemMudou = inputFotoPerfil.files.length > 0 || inputBanner.files.length > 0;

    if (descricaoMudou || imagemMudou) {
        document.getElementById('form-atualizar').submit();
    }
}

function mostrarEditaveis() {
    campoDescricao.disabled = false;
    inputFotoPerfil.disabled = false;
    inputBanner.disabled = false;
	fotoPerfil.style.cursor = 'pointer';
	banner.style.cursor = 'pointer';

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
    botaoSeccao.style.display = 'none';
	fotoPerfil.style.cursor = 'none';
	banner.style.cursor = 'none';

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
	botaoSeccao.style.display = 'none';
    concluirEdicao.style.display = 'block';
    mostrarEditaveis();
});

concluirEdicao.addEventListener('click', function() {
	configAlterada = false;
	edicaoAtiva = false;
	verificador = 0;
	
    alert('Alterações salvas com sucesso!');
    this.style.display = 'none';
	botaoSeccao.style.display = 'block';
    habilitacaoEdicao.style.display = 'block';
    submeterFormulariosPerfil();
    esconderEditaveis();
});

// Contadores para limitar o número de seções

//TERMINAR preciso criar metodo no secao dao de contar numeros de seções de certo tipo! Depois adicionar no modelAtribute pra colocar aqui os valores
let contadorTextoLivre = 0;
let contadorProjetosConcluidos = 0;
let contadorLicencasCertificados = 0;
//TERMINAR

let cont = true;

botaoSeccao.addEventListener('click', function() {
    if (cont) {
        cont = false;

        let catalogoSeccao = document.createElement('div');
        catalogoSeccao.id = 'catalogo-seccao';
        catalogoSeccao.innerHTML = `
            <button id="fecharCatalogo">X</button>
            <button id="adicionar-texto-livre">Texto Livre ${contadorTextoLivre} / 10</button>
            <button id="adicionar-projetos-concluidos">Projetos Concluídos ${contadorProjetosConcluidos} / 1</button>
            <button id="adicionar-licencas-certificados">Licenças e Certificados ${contadorLicencasCertificados} / 1</button>
            <button id="adicionar-desenho">Desenho</button>
            <button id="adicionar-foto">Foto</button>
            <button id="adicionar-video">Vídeo</button>
        `;
        document.body.appendChild(catalogoSeccao);

        document.getElementById('fecharCatalogo').addEventListener('click', function() {
            cont = true;
            catalogoSeccao.remove();
        });

        document.getElementById('adicionar-texto-livre').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Texto Livre');
        });

        document.getElementById('adicionar-projetos-concluidos').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Projetos Concluídos');
        });

        document.getElementById('adicionar-licencas-certificados').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Licenças e Certificados');
        });

        document.getElementById('adicionar-desenho').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Desenho');
        });

        document.getElementById('adicionar-foto').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Foto');
        });

        document.getElementById('adicionar-video').addEventListener('click', function() {
            catalogoSeccao.remove();
            cont = true;
            criarSecao('Vídeo');
        });
    }
});



let ordem = 0;

//ARRUMAR

function criarSecao(tipo) {
    let overlay = document.createElement('div');
    overlay.id = 'overlay';
	
    let seccao = document.createElement('form');
    seccao.id = 'secao-edicao';
	seccao.action = "/perfil-aluno/atualizar-secoes"; 
	seccao.method = "POST"; 
	seccao.enctype = "multipart/form-data";

    let tituloPersonalizado = false;
    let criarTopico = false;
    let limitador = 0;
	
	// Id Aluno input
	let idInput = document.createElement('input');
	idInput.type = 'hidden';
	idInput.name = 'id';
	idInput.value = alunoId;
	seccao.appendChild(idInput);

    // Ordem input
    let ordemInput = document.createElement('input');
    ordemInput.type = 'hidden';
    ordemInput.name = 'ordem';
    ordemInput.value = ordem;
    seccao.appendChild(ordemInput);

    // Tipo input
    let tipoInput = document.createElement('input');
    tipoInput.type = 'hidden';
    tipoInput.name = 'tipo';
    tipoInput.value = tipo;
    seccao.appendChild(tipoInput);

    if (tipo === 'Texto Livre') {
        tituloPersonalizado = true;
        criarTopico = false;
    }
    if (tipo === 'Licenças e Certificados') {
        tituloPersonalizado = false;
        criarTopico = true;
    }
    if (tipo === 'Projetos Concluídos') {
        tituloPersonalizado = false;
        criarTopico = false;
    }

    // Título personalizado ou fixo
    if (tituloPersonalizado) {
        let texto = document.createElement('input');
        texto.className = 'titulo-seccao';
        texto.name = 'titulo';
        texto.maxLength = 36;
        texto.placeholder = 'Digite o título da seção ou deixe sem...';
        seccao.appendChild(texto);
    } else if (tipo !== 'Texto Livre') {
        let titulo = document.createElement('h3');
        titulo.innerText = tipo;
        titulo.className = 'titulo-seccao-texto';
        seccao.appendChild(titulo);
    }	
	
	//Caso Texto Livre
	let toolbarContainer;
    let colorPicker;
	let toolbar;
	
	if (tipo === 'Texto Livre') {
		let editorContainer = document.createElement('div');
	    editorContainer.className = 'editor-container';
		editorContainer.innerHTML = 'Escreva seu texto...';

		editorContainer.style.fontSize = '16px'; // Corrigido
		editorContainer.style.position = 'absolute';
		editorContainer.style.left = '20px';
		editorContainer.style.top = '70px';
		editorContainer.style.width = '400px';
		editorContainer.style.height = '100px';
		editorContainer.style.maxHeight = '100vh';
		editorContainer.style.maxWidth = '66vw';
		editorContainer.style.backgroundColor = '#fff';
		editorContainer.style.cursor = 'text';
		editorContainer.style.border = '1px solid #ccc';
		editorContainer.style.resize = 'both';
		editorContainer.style.overflow = 'auto';
		editorContainer.style.marginBottom = '20px';

	    seccao.appendChild(editorContainer);
		
	    let comprimentoConteudoTextoInput = document.createElement('input');
	    comprimentoConteudoTextoInput.type = 'hidden';
	    comprimentoConteudoTextoInput.name = 'comprimentoConteudoTexto';
	    seccao.appendChild(comprimentoConteudoTextoInput);

	    let alturaConteudoTextoInput = document.createElement('input');
	    alturaConteudoTextoInput.type = 'hidden';
	    alturaConteudoTextoInput.name = 'alturaConteudoTexto';
	    seccao.appendChild(alturaConteudoTextoInput);

		let atualizarDimensoes = () => {
	        comprimentoConteudoTextoInput.value = editorContainer.offsetWidth;
	        alturaConteudoTextoInput.value = editorContainer.offsetHeight;
	    };

	    editorContainer.addEventListener('input', atualizarDimensoes);
	    editorContainer.addEventListener('mouseup', atualizarDimensoes);
	    atualizarDimensoes();

	    // Lógica de arraste
	    let isDragging = false;
	    let startX, startY, startLeft, startTop;

	    editorContainer.addEventListener('mousedown', (e) => {	
			let isResizeArea = e.offsetX > editorContainer.offsetWidth - 20 && e.offsetY > editorContainer.offsetHeight - 20;
	        if (isResizeArea)
	            return

			let selection = window.getSelection();
		    if (selection && selection.anchorNode && editorContainer.contains(selection.anchorNode)) {
		        return;
		    }
			
			quill.disable();

	        isDragging = true;
	        startX = e.clientX;
	        startY = e.clientY;
	        startLeft = parseInt(editorContainer.style.left, 10) || 0;
	        startTop = parseInt(editorContainer.style.top, 10) || 0;

	    });

	    document.addEventListener('mousemove', (e) => {
	        if (isDragging) {
				editorContainer.style.border = '1px solid #ff0000';
				editorContainer.style.cursor = 'grabbing';
				
	            let deltaX = e.clientX - startX;
	            let deltaY = e.clientY - startY;
	            let newLeft = startLeft + deltaX;
	            let newTop = startTop + deltaY;

	            let espacoDelimitado = {
	                left: 0,
	                top: 70,
	                right: seccao.offsetWidth - editorContainer.offsetWidth,
	                bottom: seccao.offsetHeight - editorContainer.offsetHeight
	            };
				
				let maxWidth = seccao.offsetWidth - newLeft;
				
	            newLeft = Math.max(espacoDelimitado.left, Math.min(newLeft, espacoDelimitado.right));
	            newTop = Math.max(espacoDelimitado.top, Math.min(newTop, espacoDelimitado.bottom));

	            editorContainer.style.left = newLeft + "px";
	            editorContainer.style.top = newTop + "px";
	        }
	    });

	    document.addEventListener('mouseup', () => {
	        isDragging = false;
			
			let maxWidth = seccao.offsetWidth - editorContainer.offsetLeft;
		   	editorContainer.style.maxWidth = `${Math.max(maxWidth, 0)}px`;
			quill.enable();
			
			editorContainer.style.border = '1px solid #ccc';
			editorContainer.style.cursor = 'pointer';
	    });
		
		let quill = new Quill(editorContainer, {
		    theme: 'snow',
		    modules: {
		        toolbar: [
		            [{ 'header': '1' }, { 'header': '2' }, { 'header': [3, 4, 5, 6, false] }, { 'font': [] }],
		            [{ 'size': ['small', false, 'large', 'huge'] }],
		            [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'list': 'check' }],
		            [{ 'align': [] }],
		            ['bold', 'italic', 'underline', 'strike', 'blockquote'],
		            [{ 'color': [] }, { 'background': [] }],
		            ['link', 'image', 'video', 'formula'],
		            [{ 'script': 'sub' }, { 'script': 'super' }],
		            [{ 'indent': '-1' }, { 'indent': '+1' }],
		            [{ 'direction': 'rtl' }],
		            ['code-block'],
		            ['clean']
		        ]
		    }
		});
		
		let conteudoTexto = document.createElement('input');
	    conteudoTexto.type = 'hidden';
	    conteudoTexto.name = 'conteudoTexto';
	    conteudoTexto.value = '';
	    seccao.appendChild(conteudoTexto);

		quill.on('text-change', function () {
	        let htmlConteudo = quill.root.innerHTML;

	        let textoComDuasQuebras = htmlConteudo.replace(/<br>/g, '<br><br>');
			
			let textoComQuebras = htmlConteudo.replace(/<\/br>\s*<br>/g, '\n\n');
			textoComQuebras = textoComQuebras.replace(/<\/?br>/g, '');

	        textoComDuasQuebras = textoComDuasQuebras.replace(/\n/g, '<br><br>');
			
	        conteudoTexto.value = textoComDuasQuebras;
	    });
		
		toolbar = quill.getModule('toolbar');
	    toolbarContainer = toolbar.container;

	    toolbarContainer.style.position = 'fixed';
	    toolbarContainer.style.top = '2%';
	    toolbarContainer.style.left = '50%';
		toolbarContainer.style.transform = 'translateX(-50%)';
		toolbarContainer.style.backgroundColor = '#ffffff';
	    toolbarContainer.style.zIndex = '1099';
	    toolbarContainer.style.width = '66vw';

	    botaoColorPicker = document.createElement('button');
	    botaoColorPicker.style.backgroundImage = "url('../img/icon-color-picker.png')";
	    botaoColorPicker.style.backgroundSize = 'cover';
	    botaoColorPicker.style.backgroundRepeat = 'no-repeat';
	    botaoColorPicker.style.backgroundPosition = 'center';
	    botaoColorPicker.type = "button";
	    botaoColorPicker.classList.add('ql-custom-color');

	    toolbarContainer.appendChild(botaoColorPicker);

    	botaoColorPicker.style.width = '18px';
    	botaoColorPicker.style.height = '18px';
     	botaoColorPicker.style.padding = '0';
     	botaoColorPicker.style.border = 'none';
     	botaoColorPicker.style.cursor = 'pointer';
     	botaoColorPicker.style.position = 'fixed';
     	botaoColorPicker.style.bottom = '28%';
     	botaoColorPicker.style.left = '63.5%';

	    // Criar o seletor de cor
	    colorPicker = document.createElement('input');
	    colorPicker.type = 'color';
	    colorPicker.style.display = 'none';

	    document.body.appendChild(colorPicker);
		document.body.appendChild(toolbarContainer);

	    botaoColorPicker.addEventListener('click', function () {
	        colorPicker.click();
	    });

	    colorPicker.addEventListener('input', function (e) {
	        let color = e.target.value;
	        quill.format('color', color);
	    });
	}

	
    // Caso Projetos Concluídos
    if (tipo === 'Projetos Concluídos') {
		let tabela = document.createElement('table');
	    tabela.className = 'table';
	    
	    let thead = document.createElement('thead');
	    let th1 = document.createElement('th');
	    th1.innerText = 'Natureza';
	    let th2 = document.createElement('th');
	    th2.innerText = 'Empresa/Título';
	    let th3 = document.createElement('th');
	    th3.innerText = 'Descrição';
	    let th4 = document.createElement('th');
	    th4.innerText = 'Requisitos';
	    let th5 = document.createElement('th');
	    th5.innerText = 'Carga Horária';
	    let th6 = document.createElement('th');
	    th6.innerText = 'Ver certificado';

	    let trHeader = document.createElement('tr');
	    trHeader.appendChild(th1);
	    trHeader.appendChild(th2);
	    trHeader.appendChild(th3);
	    trHeader.appendChild(th4);
	    trHeader.appendChild(th5);
	    trHeader.appendChild(th6);
	    thead.appendChild(trHeader);
	    tabela.appendChild(thead);

	    let tbody = document.createElement('tbody');
		
	    projetos.forEach(projeto => {
	        let tr = document.createElement('tr');
	        
	        let td1 = document.createElement('td');
	        td1.innerText = 'Pesquisa/extensão';
	        let td2 = document.createElement('td');
	        td2.innerText = projeto.nome;
	        let td3 = document.createElement('td');
	        td3.innerText = projeto.descricao;
	        let td4 = document.createElement('td');
	        td4.innerText = projeto.requisito;
	        let td5 = document.createElement('td');
	        td5.innerText = projeto.cargaHoraria;
	        let td6 = document.createElement('td');
	        let a = document.createElement('a');
	        a.href = `/aluno/emite?id=${projeto.id}&tipo=projeto&aluno=${aluno.id}`;
	        let button = document.createElement('button');
	        button.id = 'botao-certificado';
			button.type = 'button';
	        button.innerText = 'Ver certificado';
	        a.appendChild(button);
	        td6.appendChild(a);

	        tr.appendChild(td1);
	        tr.appendChild(td2);
	        tr.appendChild(td3);
	        tr.appendChild(td4);
	        tr.appendChild(td5);
	        tr.appendChild(td6);
	        tbody.appendChild(tr);
	    });

	    estagios.forEach(estagio => {
	        let tr = document.createElement('tr');
	        
	        let td1 = document.createElement('td');
	        td1.innerText = 'Pesquisa/extensão';
	        let td2 = document.createElement('td');
	        td2.innerText = estagio.empresa;
	        let td3 = document.createElement('td');
	        td3.innerText = estagio.descricao;
	        let td4 = document.createElement('td');
	        td4.innerText = estagio.requisito;
	        let td5 = document.createElement('td');
	        td5.innerText = estagio.cargaHoraria;
	        let td6 = document.createElement('td');
	        let a = document.createElement('a');
	        a.href = `/aluno/emite?id=${estagio.id}&tipo=estagio&aluno=${aluno.id}`;
	        let button = document.createElement('button');
	        button.id = 'botao-certificado';
	        button.innerText = 'Ver certificado';
	        a.appendChild(button);
	        td6.appendChild(a);

	        tr.appendChild(td1);
	        tr.appendChild(td2);
	        tr.appendChild(td3);
	        tr.appendChild(td4);
	        tr.appendChild(td5);
	        tr.appendChild(td6);
	        tbody.appendChild(tr);
	    });

	    tabela.appendChild(tbody);
	    seccao.appendChild(tabela);
    }

    // Botões para Fechar e Salvar
    let botoesContainer = document.createElement('div');
    botoesContainer.id = 'botoes-container';

	let fecharBotao = document.createElement('button');
	fecharBotao.innerText = 'Fechar';
	fecharBotao.style.marginRight = '10px';
	fecharBotao.addEventListener('click', function () {
	    seccao.remove();
		overlay.remove();
	    botoesContainer.remove();
		if (toolbarContainer) toolbarContainer.remove();
	    if (colorPicker) colorPicker.remove();
		if (toolbar) toolbar.remove();
	});

	let salvarBotao = document.createElement('button');
	salvarBotao.innerText = 'Salvar';
	salvarBotao.type = 'submit';
	salvarBotao.addEventListener('click', function () {
	    seccao.submit();
		seccao.remove();
		overlay.remove();
	    botoesContainer.remove();
	    if (toolbarContainer) toolbarContainer.remove();
	    if (colorPicker) colorPicker.remove();
		if (toolbar) toolbar.remove();
	});

    botoesContainer.appendChild(fecharBotao);
    botoesContainer.appendChild(salvarBotao);
    document.body.appendChild(botoesContainer);

    // Adicionando Tópicos caso seja necessário
    if (criarTopico) {
		let botaoCriarTopico = document.createElement('button');
		botaoCriarTopico.className = 'seccao-botao-criar-topico';
		botaoCriarTopico.type = "button";
        seccao.appendChild(botaoCriarTopico);

        botaoCriarTopico.addEventListener('click', function () {
            if (tipo === 'Licenças e Certificados') {
                let caixaTexto = document.createElement('textarea');
                caixaTexto.className = 'caixa-texto-topico2';
                caixaTexto.placeholder = "Escreva seu texto...";
                caixaTexto.maxLength = 108;
                caixaTexto.rows = "3";
                caixaTexto.cols = "50";

                let inputLogo = document.createElement('input');
                inputLogo.type = 'file';
                inputLogo.accept = 'image/*';
                let logoPreview = document.createElement('img');
                logoPreview.className = 'logo-preview';
                logoPreview.style.width = '50px';
                logoPreview.style.height = '50px';

                let containerLogoTexto = document.createElement('div');
                containerLogoTexto.appendChild(inputLogo);
                containerLogoTexto.appendChild(logoPreview);
                containerLogoTexto.appendChild(caixaTexto);

                let botaoApagarTopico = document.createElement('button');
                botaoApagarTopico.innerText = 'Apagar Tópico';
                containerLogoTexto.appendChild(botaoApagarTopico);

                seccao.appendChild(containerLogoTexto);

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
                    containerLogoTexto.remove();
                    limitador--;
                });
            }
        });
    }
	
	seccao.style.display = 'block';
	document.body.appendChild(seccao);
	document.body.appendChild(overlay);
}

/*LITERALMENTE CRIAR SEÇÃO Só QUE COM OS DADOS da SECAO
function editarSecao(tipo, secao) {
    let overlay = document.createElement('div');
    overlay.id = 'overlay';
	
    let seccao = document.createElement('form');
    seccao.id = 'secao-edicao';
	seccao.action = "/perfil-aluno/atualizar-secoes"; 
	seccao.method = "POST"; 
	seccao.enctype = "multipart/form-data";

    let tituloPersonalizado = false;
    let criarTopico = false;
    let limitador = 0;
	
	// Id input
	let idInput = document.createElement('input');
	idInput.type = 'hidden';
	idInput.name = 'id';
	idInput.value = alunoId;
	seccao.appendChild(idInput);

    // Ordem input
    let ordemInput = document.createElement('input');
    ordemInput.type = 'hidden';
    ordemInput.name = 'ordem';
    ordemInput.value = ordem;
    seccao.appendChild(ordemInput);

    // Tipo input
    let tipoInput = document.createElement('input');
    tipoInput.type = 'hidden';
    tipoInput.name = 'tipo';
    tipoInput.value = tipo;
    seccao.appendChild(tipoInput);

    if (tipo === 'Texto Livre') {
        tituloPersonalizado = true;
        criarTopico = true;
    }
    if (tipo === 'Licenças e Certificados') {
        tituloPersonalizado = false;
        criarTopico = true;
    }
    if (tipo === 'Projetos Concluídos') {
        tituloPersonalizado = false;
        criarTopico = false;
    }

    // Título personalizado ou fixo
    if (tituloPersonalizado) {
        let texto = document.createElement('input');
        texto.className = 'titulo-seccao';
        texto.name = 'titulo';
        texto.maxLength = 36;
        texto.placeholder = 'Digite o título...';
        seccao.appendChild(texto);
    } else {
        let titulo = document.createElement('h3');
        titulo.innerText = tipo;
        titulo.className = 'titulo-seccao-texto';
        seccao.appendChild(titulo);
    }

    // Caso Projetos Concluídos
    if (tipo === 'Projetos Concluídos') {
        let tabela = document.createElement('table');
        tabela.className = 'table';

        let thead = document.createElement('thead');
        let headers = ['Natureza', 'Empresa/Título', 'Descrição', 'Requisitos', 'Carga Horária', 'Ver certificado'];
        let trHeader = document.createElement('tr');

        headers.forEach(header => {
            let th = document.createElement('th');
            th.innerText = header;
            trHeader.appendChild(th);
        });

        thead.appendChild(trHeader);
        tabela.appendChild(thead);

        let tbody = document.createElement('tbody');

        projetos.forEach(projeto => {
            let tr = document.createElement('tr');
            let td1 = document.createElement('td');
            td1.innerText = 'Pesquisa/extensão';
            let td2 = document.createElement('td');
            td2.innerText = projeto.nome;
            let td3 = document.createElement('td');
            td3.innerText = projeto.descricao;
            let td4 = document.createElement('td');
            td4.innerText = projeto.requisito;
            let td5 = document.createElement('td');
            td5.innerText = projeto.cargaHoraria;
            let td6 = document.createElement('td');
            let a = document.createElement('a');
            a.href = `/aluno/emite?id=${projeto.id}&tipo=projeto&aluno=${aluno.id}`;
            let button = document.createElement('button');
            button.innerText = 'Ver certificado';
            a.appendChild(button);
            td6.appendChild(a);

            [td1, td2, td3, td4, td5, td6].forEach(td => tr.appendChild(td));
            tbody.appendChild(tr);
        });

        tabela.appendChild(tbody);
        seccao.appendChild(tabela);
    }

    // Botões para Fechar e Salvar
    let botoesContainer = document.createElement('div');
    botoesContainer.id = 'botoes-container';

    let fecharBotao = document.createElement('button');
    fecharBotao.innerText = 'Fechar';
    fecharBotao.style.marginRight = '10px';
    fecharBotao.addEventListener('click', function () {
        seccao.remove();
        overlay.remove();
    });

    let salvarBotao = document.createElement('button');
    salvarBotao.innerText = 'Salvar';
    salvarBotao.type = 'submit';
    salvarBotao.addEventListener('click', function () {
        seccao.submit();
        seccao.remove();
        overlay.remove();
    });

    botoesContainer.appendChild(fecharBotao);
    botoesContainer.appendChild(salvarBotao);
    seccao.appendChild(botoesContainer);

    // Adicionando Tópicos caso seja necessário
    if (criarTopico) {
		let botaoCriarTopico = document.createElement('button');
		botaoCriarTopico.className = 'seccao-botao-criar-topico';
		botaoCriarTopico.type = "button";
        seccao.appendChild(botaoCriarTopico);

        botaoCriarTopico.addEventListener('click', function () {
            if (tipo === 'Texto Livre') {
                if (limitador < 1) {
					botaoCriarTopico.disabled = false;
					let botaoApagarTopico = document.createElement('button');
					botaoApagarTopico.className = 'seccao-botao-apagar-topico';
					let containerTopico = document.createElement('div');
					
					let caixaTexto = document.createElement('textarea');
                	containerTopico.className = "form-caixa-texto";
                    caixaTexto.className = 'caixa-texto-topico';
                    caixaTexto.placeholder = "Escreva seu texto...";
                    caixaTexto.maxLength = 288;
                    caixaTexto.rows = "3";
                    caixaTexto.cols = "50";
					
                	containerTopico.appendChild(caixaTexto);
                	containerTopico.appendChild(botaoApagarTopico);
                    seccao.appendChild(containerTopico);
                    limitador++;

                    botaoApagarTopico.addEventListener('click', function () {
                        containerTopico.remove();
                        if (limitador > 0) limitador--;
                    });
					
                } else {
                    alert('Limite de criação de tópicos atingido!');
                }
            } else if (tipo === 'Licenças e Certificados') {
                let caixaTexto = document.createElement('textarea');
                caixaTexto.className = 'caixa-texto-topico2';
                caixaTexto.placeholder = "Escreva seu texto...";
                caixaTexto.maxLength = 108;
                caixaTexto.rows = "3";
                caixaTexto.cols = "50";

                let inputLogo = document.createElement('input');
                inputLogo.type = 'file';
                inputLogo.accept = 'image/*';
                let logoPreview = document.createElement('img');
                logoPreview.className = 'logo-preview';
                logoPreview.style.width = '50px';
                logoPreview.style.height = '50px';

                let containerLogoTexto = document.createElement('div');
                containerLogoTexto.appendChild(inputLogo);
                containerLogoTexto.appendChild(logoPreview);
                containerLogoTexto.appendChild(caixaTexto);

                let botaoApagarTopico = document.createElement('button');
                botaoApagarTopico.innerText = 'Apagar Tópico';
                containerLogoTexto.appendChild(botaoApagarTopico);

                seccao.appendChild(containerLogoTexto);

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
                    containerLogoTexto.remove();
                    limitador--;
                });
            }
        });
    }
	
	document.body.appendChild(seccao);
    seccao.style.display = 'block';
}*/


//renderizar minhas secoes
secoes.forEach(secao => {
	let tituloPersonalizado = false;

    let seccao = document.createElement('section');
    seccao.className = 'seccao';
    seccao.style.userSelect = 'text';
	
	if (secao.tipo === "Texto Livre") {
        tituloPersonalizado = true;
		criarTopico = true;
	}
	if (secao.tipo === "Licenças e Certificados") {
	   	tituloPersonalizado = false;
		criarTopico = true;
	}
	if (secao.tipo === "Projetos Concluídos") {
		tituloPersonalizado = false;
		criarTopico = false;
	}

	let titulo = document.createElement('h3');
	if (tituloPersonalizado)
		titulo.innerText = secao.titulo;
	else
		titulo.innerText = secao.tipo;
	titulo.className = 'titulo-seccao-texto';
	seccao.appendChild(titulo);
	
	//criações

	if (secao.tipo === "Projetos Concluídos") {
	    let tabela = document.createElement('table');
	    tabela.className = 'table';
	    
	    let thead = document.createElement('thead');
	    let th1 = document.createElement('th');
	    th1.innerText = 'Natureza';
	    let th2 = document.createElement('th');
	    th2.innerText = 'Empresa/Título';
	    let th3 = document.createElement('th');
	    th3.innerText = 'Descrição';
	    let th4 = document.createElement('th');
	    th4.innerText = 'Requisitos';
	    let th5 = document.createElement('th');
	    th5.innerText = 'Carga Horária';
	    let th6 = document.createElement('th');
	    th6.innerText = 'Ver certificado';

	    let trHeader = document.createElement('tr');
	    trHeader.appendChild(th1);
	    trHeader.appendChild(th2);
	    trHeader.appendChild(th3);
	    trHeader.appendChild(th4);
	    trHeader.appendChild(th5);
	    trHeader.appendChild(th6);
	    thead.appendChild(trHeader);
	    tabela.appendChild(thead);

	    let tbody = document.createElement('tbody');
		
	    projetos.forEach(projeto => {
	        let tr = document.createElement('tr');
	        
	        let td1 = document.createElement('td');
	        td1.innerText = 'Pesquisa/extensão';
	        let td2 = document.createElement('td');
	        td2.innerText = projeto.nome;
	        let td3 = document.createElement('td');
	        td3.innerText = projeto.descricao;
	        let td4 = document.createElement('td');
	        td4.innerText = projeto.requisito;
	        let td5 = document.createElement('td');
	        td5.innerText = projeto.cargaHoraria;
	        let td6 = document.createElement('td');
	        let a = document.createElement('a');
	        a.href = `/aluno/emite?id=${projeto.id}&tipo=projeto&aluno=${aluno.id}`;
	        let button = document.createElement('button');
	        button.id = 'botao-certificado';
	        button.innerText = 'Ver certificado';
	        a.appendChild(button);
	        td6.appendChild(a);

	        tr.appendChild(td1);
	        tr.appendChild(td2);
	        tr.appendChild(td3);
	        tr.appendChild(td4);
	        tr.appendChild(td5);
	        tr.appendChild(td6);
	        tbody.appendChild(tr);
	    });

	    estagios.forEach(estagio => {
	        let tr = document.createElement('tr');
	        
	        let td1 = document.createElement('td');
	        td1.innerText = 'Pesquisa/extensão';
	        let td2 = document.createElement('td');
	        td2.innerText = estagio.empresa;
	        let td3 = document.createElement('td');
	        td3.innerText = estagio.descricao;
	        let td4 = document.createElement('td');
	        td4.innerText = estagio.requisito;
	        let td5 = document.createElement('td');
	        td5.innerText = estagio.cargaHoraria;
	        let td6 = document.createElement('td');
	        let a = document.createElement('a');
	        a.href = `/aluno/emite?id=${estagio.id}&tipo=estagio&aluno=${aluno.id}`;
	        let button = document.createElement('button');
	        button.id = 'botao-certificado';
	        button.innerText = 'Ver certificado';
	        a.appendChild(button);
	        td6.appendChild(a);

	        tr.appendChild(td1);
	        tr.appendChild(td2);
	        tr.appendChild(td3);
	        tr.appendChild(td4);
	        tr.appendChild(td5);
	        tr.appendChild(td6);
	        tbody.appendChild(tr);
	    });

	    tabela.appendChild(tbody);
	    seccao.appendChild(tabela);
	}
	
	let botaoApagar = document.createElement('button');
    botaoApagar.className = 'seccao-botao-apagar';
	
	let formApagarSecao = document.createElement('form');
	formApagarSecao.id = 'form-apagar-secao';
	formApagarSecao.action = "/perfil-aluno/apagar-secao"; 
	formApagarSecao.method = "POST"; 
	formApagarSecao.enctype = "multipart/form-data";
	formApagarSecao.type = 'hidden';
	formApagarSecao.style.display = 'none';
	
	seccao.appendChild(formApagarSecao);
	
	let idSecaoInput = document.createElement('input');
	idSecaoInput.type = 'hidden';
	idSecaoInput.name = 'idSecao';
	idSecaoInput.value = secao.id;
	
	let idUsuarioInput = document.createElement('input');
	idUsuarioInput.type = 'hidden';
	idUsuarioInput.name = 'idUsuario';
	idUsuarioInput.value = secao.usuarioId;
	
	formApagarSecao.appendChild(idUsuarioInput);
	formApagarSecao.appendChild(idSecaoInput);
	
    seccao.appendChild(botaoApagar);
	
	botaoApagar.addEventListener('click', function () {
		
		/*
	    if (contadorTextoLivre != 0 && tipo === "Texto Livre") {
	        contadorTextoLivre--;
	    }
	    
	    if (contadorProjetosConcluidos != 0 && tipo === "Projetos Concluídos") {
	        contadorProjetosConcluidos = 0;
	    }
	
	    if (contadorLicencasCertificados != 0 && tipo === "Licenças e Certificados") {
	        contadorLicencasCertificados = 0;
	    }*/
	
	    if (confirm('Você tem certeza que deseja excluir esta seção?')) {
			formApagarSecao.submit();
	        seccao.remove();
	    }
	});
		
	if(secao.tipo === "Texto Livre") {
	    let editorContainer = document.createElement('div');
	    editorContainer.className = 'caixa-texto-topico'; 
	    editorContainer.style.resize = 'none';
	    editorContainer.style.width = secao.comprimentoConteudoTexto + 'px';
	    editorContainer.style.height = secao.alturaConteudoTexto + 'px';
		editorContainer.style.fontSize = '16px';

	    seccao.appendChild(editorContainer);

	    const quill = new Quill(editorContainer, {
	        theme: 'bubble', // Use o tema de leitura simples
	        readOnly: true, // Torna o conteúdo apenas leitura
	        modules: {
	            toolbar: false, // Sem ferramentas de edição
	        },
	    });

	    quill.root.innerHTML = secao.conteudoTexto;
    }
					
	else if(secao.tipo === "Licenças e Certificados") {
	    let conteinerLogoTexto = document.createElement('div');
	    conteinerLogoTexto.className = "form-logo-texto-container";
	
	    let logoPreview = document.createElement('img');
	    logoPreview.className = "logo-preview";
	    logoPreview.style.width = '50px';
	    logoPreview.style.height = '50px';
	
	    let caixaTexto = document.createElement('p');
	    caixaTexto.className = 'caixa-texto-topico2';
		caixaTexto.innerText = secao.conteudoTexto;
		
		conteinerLogoTexto.appendChild(logoPreview);
		conteinerLogoTexto.appendChild(caixaTexto);
	    seccao.appendChild(conteinerLogoTexto);
	
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
	}
	containerSeccoes.appendChild(seccao);
});

//CROPPEP

let cropper;
let currentInput = '';

let borderBottom = 2; // Borda inferior de 2px do banner

function openCropModal(file, inputId) {
    let reader = new FileReader();
    reader.onload = (e) => {
        let imageToCrop = document.getElementById('imageToCrop');
        imageToCrop.src = e.target.result;
        currentInput = inputId;

        // Mostrar o modal
        document.getElementById('cropModal').style.display = 'flex';

        if (cropper) cropper.destroy();
        cropper = new Cropper(imageToCrop, {
            aspectRatio: inputId === 'input-banner' ? 100 / 35 : 1, // Aspecto para banner ou foto de perfil
            viewMode: 1,
            autoCropArea: 1,
            movable: true,
            zoomable: true,
            scalable: true,
            background: false,
            ready() {
                if (inputId === 'input-foto-perfil') {
                    let cropBoxData = cropper.getCropBoxData();
                    // Para foto de perfil, subtrair a borda e garantir formato circular
                    cropper.setCropBoxData({
                        left: cropBoxData.left + borderRadius,
                        top: cropBoxData.top + borderRadius,
                        width: Math.min(cropBoxData.width, cropBoxData.height) - borderRadius * 2,
                        height: Math.min(cropBoxData.width, cropBoxData.height) - borderRadius * 2,
                    });
                } else if (inputId === 'input-banner') {
                    let cropBoxData = cropper.getCropBoxData();
                    // Para o banner, subtrair a borda inferior de 2px na altura
                    cropper.setCropBoxData({
                        left: cropBoxData.left, // Não é necessário ajustar a largura
                        top: cropBoxData.top, // Não é necessário ajustar o topo
                        width: cropBoxData.width,
                        height: cropBoxData.height - borderBottom, // Subtrai a borda inferior de 2px
                    });
                }
            },
        });
    };
    reader.readAsDataURL(file);
}

// Botão de cortar
document.getElementById('cropButton').addEventListener('click', () => {
    if (cropper) {
        let bannerHeight = Math.round(window.innerHeight * 0.35) - borderBottom; // Subtrai a borda inferior de 2px
        let width = currentInput === 'input-banner' ? window.innerWidth : 170;
        let height = currentInput === 'input-banner' ? bannerHeight : 170;

        let croppedCanvas = cropper.getCroppedCanvas({
            width: width,
            height: height,
        });

        croppedCanvas.toBlob((blob) => {
            let croppedFile = new File([blob], `cropped-${currentInput}.png`, { type: blob.type });

            let dataTransfer = new DataTransfer();
            dataTransfer.items.add(croppedFile);

            let inputElement = document.getElementById(currentInput);
            inputElement.files = dataTransfer.files;

            let url = URL.createObjectURL(blob);
            if (currentInput === 'input-banner') {
                document.getElementById('banner').src = url;
            } else if (currentInput === 'input-foto-perfil') {
                document.getElementById('foto-perfil').src = url;
            }

            configAlterada = true;
            verificador = 1;
            document.getElementById('cropModal').style.display = 'none';
        });
    }
});

// Botão cancelar
document.getElementById('closeModalButton').addEventListener('click', () => {
    if (verificador !== 1) configAlterada = false;
    document.getElementById('cropModal').style.display = 'none';
    if (cropper) {
        cropper.destroy();
        cropper = null;
    }
});

// Eventos para inputs
document.getElementById('input-banner').addEventListener('change', (event) => {
    let file = event.target.files[0];
    if (file) openCropModal(file, 'input-banner');
});

document.getElementById('input-foto-perfil').addEventListener('change', (event) => {
    let file = event.target.files[0];
    if (file) openCropModal(file, 'input-foto-perfil');
});

// Verificação de alterações
window.addEventListener('beforeunload', function (event) {
    if (configAlterada) {
        event.preventDefault();
        event.returnValue = 'Você tem alterações não salvas. Tem certeza de que deseja sair?';
    }
});


//Seção de pintar

const canvas = document.getElementById('paintCanvas');
const ctx = canvas.getContext('2d');

let painting = false;
let color = 'black';

// Função para começar a desenhar
function startPosition(e) {
    painting = true;
    draw(e);
}

 // Função para parar de desenhar
function endPosition() {
     painting = false;
	   ctx.beginPath();
}

// Função para desenhar
function draw(e) {
    if (!painting) return;

    ctx.lineWidth = 5;
    ctx.lineCap = 'round';
    ctx.strokeStyle = color;

    ctx.lineTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
}

// Mudar cor
 function changeColor(newColor) {
    color = newColor;
}

// Limpar o canvas
function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

// Eventos de mouse
canvas.addEventListener('mousedown', startPosition);
canvas.addEventListener('mouseup', endPosition);
canvas.addEventListener('mousemove', draw);
canvas.addEventListener('mouseleave', endPosition);