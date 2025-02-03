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

let cont = true;

botaoSeccao.addEventListener('click', function() {
    if (cont) {
        cont = false;

        let catalogoSeccao = document.createElement('div');
        catalogoSeccao.id = 'catalogo-seccao';
		
		let conteudoCatalogo = `
            <button id="fecharCatalogo">X</button>
            <button id="adicionar-texto-livre">Texto Livre ${qtdSecoesTextoLivre} / 10</button>
        `;

        if (usuario.role === "Aluno") {
            conteudoCatalogo += `<button id="adicionar-projetos-concluidos">Projetos Concluídos ${qtdSecoesProjetosConcluidos} / 1</button>`;
        }

        conteudoCatalogo += `
            <button id="adicionar-licencas-certificados">Licenças e Certificados ${qtdSecoesLicencasECertificados} / 1</button>
            <button id="adicionar-desenho">Desenho</button>
            <button id="adicionar-foto">Foto</button>
            <button id="adicionar-video">Vídeo</button>
        `;

        catalogoSeccao.innerHTML = conteudoCatalogo;
        document.body.appendChild(catalogoSeccao);

        document.getElementById('fecharCatalogo').addEventListener('click', function() {
            cont = true;
            catalogoSeccao.remove();
        });

	    document.getElementById('adicionar-texto-livre').addEventListener('click', function() {
	        cont = true;
			if(qtdSecoesTextoLivre < 10) {
        		criarSecao('Texto Livre');
				catalogoSeccao.remove();
			}
			else
				alert("Quantidade máxima atingida!");
	    });
	
	
		if (usuario.role === "Aluno") {
            document.getElementById('adicionar-projetos-concluidos').addEventListener('click', function() {
                cont = true;
                if(qtdSecoesProjetosConcluidos < 1) {
                    criarSecao('Projetos Concluídos');
                    catalogoSeccao.remove();
                } else {
                    alert("Quantidade máxima atingida!");
                }
            });
        }
		
        document.getElementById('adicionar-licencas-certificados').addEventListener('click', function() {
            cont = true;
			if(qtdSecoesLicencasECertificados < 1) {
        		criarSecao('Licenças e Certificados');
				catalogoSeccao.remove();
			}
			else
				alert("Quantidade máxima atingida!");
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

//FAZER UM GRID DE QUADRADINHOS PRA FOTO VIDEO E DESENHO COM A FUNÇÃO DE MOSTRAR MAIS!
//AO CLICA NO QUADRADINHO ABRE MODAL DA FOTO OU VÍDEO COM COMENTARIOS LÁ!
//OBS: NA SEÇÃO DE FOTO QUERO FAZER IGUAL O INSTAGRAM COM VARIAS FOTOS EM UMA PUBLICAÇÃO SÓ COM SETA PRO LADO E EDIÇÃO PERMITE MUDAR DESCRIÇÃO SÓ. VíDEo mESMA COISA SÓ DESCRIÇÃO


/*ARRUMAR SISTEMA DE ORDEM, CRIAR UM METODO NO SECAO DAO PRA LISTAR SEÇÕES DE ACORDO COM A ORDEM 
E NÃO ID DA SECAO PRA USAR NA RENDERIZAÇÃO!!! USAR SISTEMA DE DRAG QUE SÓ É HABILITADO AO CLICAR NO BOTAO EDITAR*/

//FAZER COM QUE BOTÔES DE EDITAR SECAO APARACE APÓS CLICAR EM BOTÃO DE EDITAR

function criarSecao(tipo) {
    let overlay = document.createElement('div');
    overlay.id = 'overlay';
	
    let seccao = document.createElement('form');
    seccao.id = 'secao-edicao';
	seccao.action = "/perfil/atualizar-secoes"; 
	seccao.method = "POST"; 
	seccao.enctype = "multipart/form-data";

    let tituloPersonalizado = false;
    let criarTopico = false;
	
	// Id Usuario input
	let idInput = document.createElement('input');
	idInput.type = 'hidden';
	idInput.name = 'id';
	idInput.value = usuarioId;
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
	
	if (tipo === 'Desenho') {
        tituloPersonalizado = true;
        criarTopico = false;
    }

    // Título personalizado ou fixo
    if (tituloPersonalizado) {
        let texto = document.createElement('input');
        texto.className = 'titulo-seccao';
        texto.name = 'titulo';
        texto.maxLength = 35;
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

		editorContainer.style.fontSize = '16px';
		editorContainer.style.position = 'absolute';
		editorContainer.style.left = '50px';
		editorContainer.style.top = '45px';
		editorContainer.style.width = '200px';
		editorContainer.style.height = '50px';
		editorContainer.style.maxHeight = '67.9vh';
		editorContainer.style.maxWidth = '58.9vw';
		editorContainer.style.backgroundColor = '#fff';
		editorContainer.style.cursor = 'text';
		editorContainer.style.border = '1px solid #ccc';
		editorContainer.style.resize = 'both';
		editorContainer.style.overflow = 'auto';
		editorContainer.style.marginBottom = '20px';

	    seccao.appendChild(editorContainer);
		
		let quill = new Quill(editorContainer, {
		    theme: 'snow',
		    modules: {
		        toolbar: [
		            [{ 'header': '1' }, { 'header': '2' }, { 'header': [3, 4, 5, 6, false] }, { 'font': [] },
		            { 'size': ['small', false, 'large', 'huge'] },
		            { 'list': 'ordered' }, { 'list': 'bullet' }, { 'list': 'check' },
		            { 'align': [] }, { 'indent': '-1' }, { 'indent': '+1' }, { 'direction': 'rtl' },
		            'bold', 'italic', 'underline', 'strike',
		            { 'color': [] }, { 'background': [] },
		            'link', 'video', 'image',
		            'clean']
		        ]
		    }
		});
		
		//BOTAR FUNDO NA DIV E TROCAR SIMBOLO DO FONT

		quill.getModule('toolbar').addHandler('video', function() {
		    let range = quill.getSelection();
		    let value = prompt('Digite a URL do vídeo');

		    if (value) {
		        let iframeSrc = '';
		        if (value.includes('youtube.com')) {
		            let videoId = value.split('v=')[1].split('&')[0];
		            iframeSrc = `https://www.youtube.com/embed/${videoId}`;
		        } else if (value.includes('vimeo.com')) {
		            let videoId = value.split('vimeo.com/')[1];
		            iframeSrc = `https://player.vimeo.com/video/${videoId}`;
		        } else if (value.includes('drive.google.com')) {
		            let fileId = value.match(/[-\w]{25,}/); // Extrai o ID do arquivo
		            if (fileId) {
		                iframeSrc = `https://drive.google.com/file/d/${fileId[0]}/preview`;
		            }
		        }

		        if (iframeSrc)
		            quill.insertEmbed(range.index, 'video', iframeSrc);
		        else
		            alert('URL de vídeo não suportada');
		    }
		});
		
		quill.getModule('toolbar').addHandler('link', function() {
		    let range = quill.getSelection();
		    let value = prompt('Digite a URL do link');
		    if (value) {
		        quill.format('link', value);
		    }
		});
		
	    let comprimentoConteudoTextoInput = document.createElement('input');
	    comprimentoConteudoTextoInput.type = 'hidden';
	    comprimentoConteudoTextoInput.name = 'comprimentoConteudoTexto';
	    seccao.appendChild(comprimentoConteudoTextoInput);

	    let alturaConteudoTextoInput = document.createElement('input');
	    alturaConteudoTextoInput.type = 'hidden';
	    alturaConteudoTextoInput.name = 'alturaConteudoTexto';
	    seccao.appendChild(alturaConteudoTextoInput);
		
		let leftConteudoTextoInput = document.createElement('input');
	    leftConteudoTextoInput.type = 'hidden';
	    leftConteudoTextoInput.name = 'leftConteudoTexto';
	    seccao.appendChild(leftConteudoTextoInput);

		let topConteudoTextoInput = document.createElement('input');
	    topConteudoTextoInput.type = 'hidden';
	    topConteudoTextoInput.name = 'topConteudoTexto';
	    seccao.appendChild(topConteudoTextoInput);

		let atualizar = () => {
		    comprimentoConteudoTextoInput.value = editorContainer.offsetWidth;
		    alturaConteudoTextoInput.value = editorContainer.offsetHeight;
		    leftConteudoTextoInput.value = editorContainer.offsetLeft;
		    topConteudoTextoInput.value = editorContainer.offsetTop;
		};

		editorContainer.addEventListener('input', atualizar);
		editorContainer.addEventListener('mouseup', atualizar);
		editorContainer.addEventListener('mousemove', atualizar);
		atualizar();

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
	                left: 50,
	                top: 45,
	                right: seccao.offsetWidth - editorContainer.offsetWidth - 50,
	                bottom: seccao.offsetHeight - editorContainer.offsetHeight - 20
	            };
				
				let maxWidth = seccao.offsetWidth - newLeft - 50;
				let maxHeight = seccao.offsetHeight - newTop - 20;
				
	            newLeft = Math.max(espacoDelimitado.left, Math.min(newLeft, espacoDelimitado.right));
	            newTop = Math.max(espacoDelimitado.top, Math.min(newTop, espacoDelimitado.bottom));

	            editorContainer.style.left = newLeft + "px";
	            editorContainer.style.top = newTop + "px";
	        }
	    });

	    document.addEventListener('mouseup', () => {
	        isDragging = false;
			
			let maxWidth = seccao.offsetWidth - editorContainer.offsetLeft - 50;
		    let maxHeight = seccao.offsetHeight - editorContainer.offsetTop - 20;

		    editorContainer.style.maxWidth = `${Math.max(maxWidth, 0)}px`;
		    editorContainer.style.maxHeight = `${Math.max(maxHeight, 0)}px`;
			
			quill.enable();
			
			editorContainer.style.border = '1px solid #ccc';
			editorContainer.style.cursor = 'pointer';
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

    	botaoColorPicker.style.width = '16px';
    	botaoColorPicker.style.height = '16px';
     	botaoColorPicker.style.padding = '0';
     	botaoColorPicker.style.border = 'none';
     	botaoColorPicker.style.cursor = 'pointer';
     	botaoColorPicker.style.position = 'fixed';
     	botaoColorPicker.style.bottom = '30%';
     	botaoColorPicker.style.left = '76.6%';

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
		if (toolbarDesenho) toolbarDesenho.remove();
	});
	
	let salvarBotao = document.createElement('button');
	salvarBotao.innerText = 'Salvar';
	salvarBotao.type = 'submit';
	salvarBotao.addEventListener('click', function () {
		if(tipo == "Desenho")
			salvarNoInput();
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
	
	//Tipo Desenho
	if (tipo === "Desenho") {
	    seccao.style.marginTop = '35px';

	    let desenhoContainer = document.createElement('div');

	    let toolbarDesenho = document.createElement('div');
	    toolbarDesenho.id = 'toolbarDesenho';
	    toolbarDesenho.style.display = 'flex';
	    toolbarDesenho.style.justifyContent = 'center';
	    toolbarDesenho.style.position = 'fixed';
	    toolbarDesenho.style.top = '2%';
	    toolbarDesenho.style.left = '50%';
	    toolbarDesenho.style.transform = 'translateX(-50%)';
	    toolbarDesenho.style.backgroundColor = '#ffffff';
	    toolbarDesenho.style.zIndex = '1099';
	    toolbarDesenho.style.width = '67vw';
		
		toolbarDesenho.style.display = 'flex';
		toolbarDesenho.style.justifyContent = 'space-around';
		toolbarDesenho.style.alignItems = 'center';
		toolbarDesenho.style.gap = '10px'; 

	    document.body.appendChild(toolbarDesenho);

	    let areaDesenho = document.createElement('canvas');
	    areaDesenho.id = 'paintCanvas';
	    areaDesenho.width = 900;
	    areaDesenho.height = 500;

	    desenhoContainer.appendChild(areaDesenho);
	    seccao.appendChild(desenhoContainer);

	    let ctx = areaDesenho.getContext('2d');
	    let painting = false;
	    let color = 'black';
	    let lineWidth = 5;
	    let erasing = false;
	    let lastColor = color;
	    let startX = null, startY = null;
	    let drawingLine = false;

		ctx.lineCap = 'round';
		
	    ctx.fillStyle = 'white';
	    ctx.fillRect(0, 0, areaDesenho.width, areaDesenho.height);

	    function startPosition(e) {
	        if (drawingLine) {
	            if (startX === null) {
	                const rect = areaDesenho.getBoundingClientRect();
	                startX = e.clientX - rect.left;
	                startY = e.clientY - rect.top;
	            } else {
	                const rect = areaDesenho.getBoundingClientRect();
	                let endX = e.clientX - rect.left;
	                let endY = e.clientY - rect.top;
	                drawLine(startX, startY, endX, endY);
	                startX = null;
	                startY = null;
	            }
	            return;
	        }

	        painting = true;
	        draw(e);
	    }

	    function endPosition() {
	        painting = false;
	        ctx.beginPath();
	    }
		
		function draw(e) {
		    if (!painting) return;
		    const rect = areaDesenho.getBoundingClientRect();
		    const x = e.clientX - rect.left;
		    const y = e.clientY - rect.top;

		    ctx.lineWidth = lineWidth;
		    ctx.strokeStyle = erasing ? 'white' : color;

		    if (squarePointerMode) {
		        // Desenha quadrados pequenos em vez de uma linha contínua
		        ctx.fillStyle = erasing ? 'white' : color;
		        ctx.fillRect(x, y, lineWidth, lineWidth);
		    } else {
		        // Modo normal (linha redonda)
		        ctx.lineTo(x, y);
		        ctx.stroke();
		        ctx.beginPath();
		        ctx.moveTo(x, y);
		    }
		}

	    function drawLine(x1, y1, x2, y2) {
	        ctx.strokeStyle = color;
	        ctx.lineWidth = lineWidth;
	        ctx.beginPath();
	        ctx.moveTo(x1, y1);
	        ctx.lineTo(x2, y2);
	        ctx.stroke();
	        ctx.closePath();
	    }

	    function changeColor(newColor) {
	        color = newColor;
	        erasing = false;
	        lastColor = newColor;
	    }

	    function changeThickness(thickness) {
	        lineWidth = Math.min(50, Math.max(1, thickness));
	        espessuraInput.value = lineWidth;
	        espessuraLabel.textContent = lineWidth;
	    }

	    function clearAreaDesenho() {
	        ctx.fillStyle = 'white';
	        ctx.fillRect(0, 0, areaDesenho.width, areaDesenho.height);
	    }

	    function fillCanvas() {
	        ctx.fillStyle = color;
	        ctx.fillRect(0, 0, areaDesenho.width, areaDesenho.height);
	    }

	    function saveDrawing() {
	        let link = document.createElement('a');
	        link.download = 'desenho.png';
	        link.href = areaDesenho.toDataURL();
	        link.click();
	    }

	    function enableEraser() {
	        erasing = true;
	        drawingLine = false;
	        color = 'white';
			squarePointerMode = false;
	    }

		let squarePointerMode = false;

		function setQuadradoPointer() {
		    erasing = false;
		    drawingLine = false;
		    color = lastColor;
		    squarePointerMode = true; 
		}

		function setNormalPointer() {
		    erasing = false;
		    drawingLine = false;
		    color = lastColor;
		    ctx.lineCap = 'round';
		    squarePointerMode = false;
		}

	    function enableLineDrawing() {
	        drawingLine = true;
	        ctx.lineCap = 'round';
			squarePointerMode = false;
	    }

	    function enableSquareLineDrawing() {
	        drawingLine = true;
	        ctx.lineCap = 'butt';
			squarePointerMode = false;
	    }

	    function criarBotao(imagem, onClick, nome) {
	        let button = document.createElement('button');
	        button.type = 'button';
	        button.onclick = onClick;
	        button.style.background = 'none';
	        button.style.border = 'none';
	        button.style.padding = '0';
	        button.style.cursor = 'pointer';

	        let img = document.createElement('img');
	        img.src = imagem;
	        img.alt = nome;
	        img.style.width = '30px';
	        img.style.height = '30px';
	        button.appendChild(img);
	        button.title = nome;

	        return button;
	    }

	    let colorPicker = document.createElement('input');
	    colorPicker.type = 'color';
	    colorPicker.style.display = 'none';
	    colorPicker.oninput = () => changeColor(colorPicker.value);
	    document.body.appendChild(colorPicker);

	    let botaoColorPicker = criarBotao('../img/icon-color-picker.png', () => colorPicker.click(), 'Selecionar Cor');

	    toolbarDesenho.appendChild(criarBotao('../img/borracha.png', enableEraser, 'Borracha'));
	    toolbarDesenho.appendChild(criarBotao('../img/limpar.png', clearAreaDesenho, 'Limpar'));
	    toolbarDesenho.appendChild(criarBotao('../img/balde.png', fillCanvas, 'Preencher'));
	    toolbarDesenho.appendChild(criarBotao('../img/baixar.png', saveDrawing, 'Salvar'));
	    toolbarDesenho.appendChild(criarBotao('../img/linha-circular.png', enableLineDrawing, 'Linha circular'));
	    toolbarDesenho.appendChild(criarBotao('../img/linha-quadrada.png', enableSquareLineDrawing, 'Linha quadrada'));
	    toolbarDesenho.appendChild(criarBotao('../img/ponteiro-circulo.png', setNormalPointer, 'Ponteiro circular'));
	    toolbarDesenho.appendChild(criarBotao('../img/ponteiro-quadrado.png', setQuadradoPointer, 'Ponteiro quadrado'));
	    toolbarDesenho.appendChild(botaoColorPicker);

	    let espessuraInput = document.createElement('input');
	    espessuraInput.type = 'range';
	    espessuraInput.min = 1;
	    espessuraInput.max = 50;
	    espessuraInput.value = lineWidth;
	    espessuraInput.oninput = () => changeThickness(espessuraInput.value);
	    toolbarDesenho.appendChild(espessuraInput);

	    let espessuraLabel = document.createElement('span');
	    espessuraLabel.textContent = lineWidth;
	    toolbarDesenho.appendChild(espessuraLabel);

	    areaDesenho.addEventListener('wheel', (e) => {
	        e.preventDefault();
	        changeThickness(lineWidth + (e.deltaY > 0 ? -1 : 1));
	    });

	    areaDesenho.addEventListener('mousedown', startPosition);
	    areaDesenho.addEventListener('mousemove', draw);
	    areaDesenho.addEventListener('mouseup', endPosition);
	    areaDesenho.addEventListener('mouseout', endPosition);
		
		let botoesContainerDesenho = document.createElement('div');
	    botoesContainerDesenho.id = 'botoes-container';

		let fecharBotaoDesenho = document.createElement('button');
		fecharBotaoDesenho.innerText = 'Fechar';
		fecharBotaoDesenho.style.marginRight = '10px';
		fecharBotaoDesenho.addEventListener('click', function () {
		    seccao.remove();
			overlay.remove();
		    botoesContainerDesenho.remove();
			if (toolbarDesenho) toolbarDesenho.remove();
		});
		
		
		let inputDesenho = document.createElement('input');
	    inputDesenho.type = "hidden"; // Mantemos o input oculto
	    inputDesenho.name = "conteudoDesenho";

	    seccao.appendChild(inputDesenho);
		
		salvarBotao.remove();
		fecharBotao.remove();
		
	    let salvarBotaoDesenho = document.createElement('button');
	    salvarBotaoDesenho.innerText = 'Salvar';
	    salvarBotaoDesenho.type = 'submit';
		
	    salvarBotaoDesenho.addEventListener('click', function () {
			let inputDesenho = document.createElement('input');
		    inputDesenho.type = "hidden";
		    inputDesenho.name = "conteudoDesenho";
			
			inputDesenho.value = areaDesenho.toDataURL();
			seccao.appendChild(inputDesenho);

	        seccao.submit();
	        seccao.remove();
	        overlay.remove();
	        botoesContainer.remove();
	        if (toolbarDesenho) toolbarDesenho.remove();
	    });

	    botoesContainerDesenho.appendChild(fecharBotaoDesenho);
	    botoesContainerDesenho.appendChild(salvarBotaoDesenho);
	    document.body.appendChild(botoesContainerDesenho);
	}


    // Caso Projetos Concluídos
    if (tipo === 'Projetos Concluídos' && usuario.role == "Aluno") {
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
	        a.href = `/aluno/emites?id=${projeto.id}&tipo=projeto&aluno=${u.id}`;
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
	        a.href = `/aluno/emites?id=${estagio.id}&tipo=estagio&aluno=${u.id}`;
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

    // Adicionando Tópicos caso seja necessário
	
	//ARRUMAR TAMANHO SENDO SALVO TODO ERRADO
    if (criarTopico) {
		let botaoCriarTopico = document.createElement('button');
		botaoCriarTopico.className = 'seccao-botao-criar-topico';
		botaoCriarTopico.type = "button";
        seccao.appendChild(botaoCriarTopico);
		
		let qtdTopicos = 0;
			
		let inputQtdTopicos = document.createElement('input');
		inputQtdTopicos.type = 'hidden';
		inputQtdTopicos.name = 'qtdTopicos'; 
		inputQtdTopicos.value = qtdTopicos;
		seccao.appendChild(inputQtdTopicos);
	
        botaoCriarTopico.addEventListener('click', function () {
			qtdTopicos++;
			inputQtdTopicos.value = qtdTopicos;
            if (tipo === 'Licenças e Certificados' && qtdTopicos <= 100) {
                let caixaTexto = document.createElement('textarea');
                caixaTexto.className = 'caixa-texto-topico2';
                caixaTexto.placeholder = "Escreva seu texto...";
                caixaTexto.maxLength = 122;
				caixaTexto.style.height = '35px';
				caixaTexto.style.maxWidth = '417px';
				
				let inputConteudoTextoTopico = document.createElement('input');
                inputConteudoTextoTopico.type = 'hidden';
				inputConteudoTextoTopico.name = 'conteudoTextoTopico';
				inputConteudoTextoTopico.value = '';
				
				let comprimentoConteudoTextoInput = document.createElement('input');
			    comprimentoConteudoTextoInput.type = 'hidden';
			    comprimentoConteudoTextoInput.name = 'comprimentoConteudoTextoTopico';
			    seccao.appendChild(comprimentoConteudoTextoInput);

			    let alturaConteudoTextoInput = document.createElement('input');
			    alturaConteudoTextoInput.type = 'hidden';
			    alturaConteudoTextoInput.name = 'alturaConteudoTextoTopico';
			    seccao.appendChild(alturaConteudoTextoInput);
				
                let inputLogo = document.createElement('input');
                inputLogo.type = 'file';
				inputLogo.name = 'conteudoImagem';
				inputLogo.hidden = true;
                inputLogo.accept = 'image/*';
				
                let logoPreview = document.createElement('img');
                logoPreview.className = 'logo-preview';
                logoPreview.style.width = '50px';
                logoPreview.style.height = '50px';

                let containerLogoTexto = document.createElement('div');
				containerLogoTexto.className = 'container-logo-texto';
				
				inputConteudoTextoTopico.value = caixaTexto.value;
				comprimentoConteudoTextoInput.value = caixaTexto.scrollWidth;
				alturaConteudoTextoInput.value = caixaTexto.scrollHeight;
				
				let atualizarDados = () => {
				    inputConteudoTextoTopico.value = caixaTexto.value;
				    comprimentoConteudoTextoInput.value = caixaTexto.scrollWidth;
				    alturaConteudoTextoInput.value = caixaTexto.scrollHeight;
				};
				
				caixaTexto.addEventListener('input', atualizarDados);
				caixaTexto.addEventListener('mouseup', atualizarDados);
				
                let botaoApagarTopico = document.createElement('button');
                botaoApagarTopico.className = 'seccao-botao-apagar-topico';
				
				containerLogoTexto.appendChild(inputConteudoTextoTopico);
				containerLogoTexto.appendChild(inputLogo);
				containerLogoTexto.appendChild(botaoApagarTopico);
		        containerLogoTexto.appendChild(logoPreview);
		        containerLogoTexto.appendChild(caixaTexto);

		        let inputArquivo = document.createElement('input');
		        inputArquivo.type = 'file';
		        inputArquivo.hidden = true; 
				inputArquivo.name = 'conteudoArquivo';
		        inputArquivo.accept = 'image/*,.pdf';

		        let labelUpload = document.createElement('label');
		        labelUpload.innerText = 'Anexar certificado/licença';
		        labelUpload.classList.add('upload-label');

		        containerLogoTexto.appendChild(inputArquivo);
		        containerLogoTexto.appendChild(labelUpload);

		        seccao.appendChild(containerLogoTexto);

				labelUpload.addEventListener('click', () => {
				    inputArquivo.click();
				});
				
				logoPreview.addEventListener('click', function() {
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
					qtdTopicos--;
					inputQtdTopicos.value = qtdTopicos;
                    containerLogoTexto.remove();
                });
            }
        });
    }
	
	seccao.style.display = 'block';
	document.body.appendChild(seccao);
	document.body.appendChild(overlay);
}

function editarSecao(secao, topicos) {
	let tipo = secao.tipo;
	let titulo = secao.titulo;
	let idSecao = secao.id;
	
    let overlay = document.createElement('div');
    overlay.id = 'overlay';
	
    let seccao = document.createElement('form');
    seccao.id = 'secao-edicao';
	seccao.action = "/perfil/atualizar-secoes-editadas"; 
	seccao.method = "POST"; 
	seccao.enctype = "multipart/form-data";

    let tituloPersonalizado = false;
    let criarTopico = false;
	
	// Id Usuario input
	let idInput = document.createElement('input');
	idInput.type = 'hidden';
	idInput.name = 'id';
	idInput.value = usuarioId;
	seccao.appendChild(idInput);
	
	let idSecaoInput = document.createElement('input');
	idSecaoInput.type = 'hidden';
	idSecaoInput.name = 'idSecao';
	idSecaoInput.value = idSecao;
	seccao.appendChild(idSecaoInput);

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
	
	if (tipo === 'Desenho') {
        tituloPersonalizado = true;
        criarTopico = false;
    }

    // Título personalizado ou fixo
    if (tituloPersonalizado) {
        let texto = document.createElement('input');
        texto.className = 'titulo-seccao';
        texto.name = 'titulo';
		texto.value = secao.titulo;
        texto.maxLength = 35;
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

		editorContainer.style.fontSize = '16px';
		editorContainer.style.position = 'absolute';
		editorContainer.style.left = secao.leftConteudoTexto + 'px';
		editorContainer.style.top = secao.topConteudoTexto + 'px';
		editorContainer.style.width = secao.comprimentoConteudoTexto + 'px';
		editorContainer.style.height = secao.alturaConteudoTexto + 'px';
		editorContainer.style.maxHeight = '67.9vh';
		editorContainer.style.maxWidth = '58.9vw';
		editorContainer.style.backgroundColor = '#fff';
		editorContainer.style.border = '1px solid #ccc';
		editorContainer.style.resize = 'both';
		editorContainer.style.overflow = 'auto';
		editorContainer.style.marginBottom = '20px';

	    seccao.appendChild(editorContainer);
		
		let quill = new Quill(editorContainer, {
		    theme: 'snow',
		    modules: {
		        toolbar: [
		            [{ 'header': '1' }, { 'header': '2' }, { 'header': [3, 4, 5, 6, false] }, { 'font': [] },
		            { 'size': ['small', false, 'large', 'huge'] },
		            { 'list': 'ordered' }, { 'list': 'bullet' }, { 'list': 'check' },
		            { 'align': [] }, { 'indent': '-1' }, { 'indent': '+1' }, { 'direction': 'rtl' },
		            'bold', 'italic', 'underline', 'strike',
		            { 'color': [] }, { 'background': [] },
		            'link', 'video', 'image',
		            'clean']
		        ]
		    }
		});
		
		
		if (secao.topConteudoTexto === 0) {
		    editorContainer.style.top = '45px';
		}

		if (secao.leftConteudoTexto === 0) {
		    editorContainer.style.left = '50px';
		}

		if (secao.comprimentoConteudoTexto === 0) {
		    editorContainer.style.width = '200px';
		}

		if (secao.alturaConteudoTexto === 0) {
		    editorContainer.style.height = '50px';
		}
		
		quill.root.innerHTML = secao.conteudoTexto;
		
		//BOTAR FUNDO NA DIV E TROCAR SIMBOLO DO FONT

		quill.getModule('toolbar').addHandler('video', function() {
		    let range = quill.getSelection();
		    let value = prompt('Digite a URL do vídeo');

		    if (value) {
		        let iframeSrc = '';
		        if (value.includes('youtube.com')) {
		            let videoId = value.split('v=')[1].split('&')[0];
		            iframeSrc = `https://www.youtube.com/embed/${videoId}`;
		        } else if (value.includes('vimeo.com')) {
		            let videoId = value.split('vimeo.com/')[1];
		            iframeSrc = `https://player.vimeo.com/video/${videoId}`;
		        } else if (value.includes('drive.google.com')) {
		            let fileId = value.match(/[-\w]{25,}/); // Extrai o ID do arquivo
		            if (fileId) {
		                iframeSrc = `https://drive.google.com/file/d/${fileId[0]}/preview`;
		            }
		        }

		        if (iframeSrc)
		            quill.insertEmbed(range.index, 'video', iframeSrc);
		        else
		            alert('URL de vídeo não suportada');
		    }
		});
		
		quill.getModule('toolbar').addHandler('link', function() {
		    let range = quill.getSelection();
		    let value = prompt('Digite a URL do link');
		    if (value) {
		        quill.format('link', value);
		    }
		});
		
	    let comprimentoConteudoTextoInput = document.createElement('input');
	    comprimentoConteudoTextoInput.type = 'hidden';
	    comprimentoConteudoTextoInput.name = 'comprimentoConteudoTexto';
	    seccao.appendChild(comprimentoConteudoTextoInput);

	    let alturaConteudoTextoInput = document.createElement('input');
	    alturaConteudoTextoInput.type = 'hidden';
	    alturaConteudoTextoInput.name = 'alturaConteudoTexto';
	    seccao.appendChild(alturaConteudoTextoInput);
		
		let leftConteudoTextoInput = document.createElement('input');
	    leftConteudoTextoInput.type = 'hidden';
	    leftConteudoTextoInput.name = 'leftConteudoTexto';
	    seccao.appendChild(leftConteudoTextoInput);

		let topConteudoTextoInput = document.createElement('input');
	    topConteudoTextoInput.type = 'hidden';
	    topConteudoTextoInput.name = 'topConteudoTexto';
	    seccao.appendChild(topConteudoTextoInput);

		let atualizar = () => {
		    comprimentoConteudoTextoInput.value = editorContainer.offsetWidth;
		    alturaConteudoTextoInput.value = editorContainer.offsetHeight;
		    leftConteudoTextoInput.value = editorContainer.offsetLeft;
		    topConteudoTextoInput.value = editorContainer.offsetTop;
		};

		editorContainer.addEventListener('input', atualizar);
		editorContainer.addEventListener('mouseup', atualizar);
		editorContainer.addEventListener('mousemove', atualizar);
		atualizar();

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
	                left: 50,
	                top: 45,
	                right: seccao.offsetWidth - editorContainer.offsetWidth - 50,
	                bottom: seccao.offsetHeight - editorContainer.offsetHeight - 20
	            };
				
				let maxWidth = seccao.offsetWidth - newLeft - 50;
				let maxHeight = seccao.offsetHeight - newTop - 20;
				
	            newLeft = Math.max(espacoDelimitado.left, Math.min(newLeft, espacoDelimitado.right));
	            newTop = Math.max(espacoDelimitado.top, Math.min(newTop, espacoDelimitado.bottom));

	            editorContainer.style.left = newLeft + "px";
	            editorContainer.style.top = newTop + "px";
	        }
	    });

	    document.addEventListener('mouseup', () => {
	        isDragging = false;
			
			let maxWidth = seccao.offsetWidth - editorContainer.offsetLeft - 50;
		    let maxHeight = seccao.offsetHeight - editorContainer.offsetTop - 20;

		    editorContainer.style.maxWidth = `${Math.max(maxWidth, 0)}px`;
		    editorContainer.style.maxHeight = `${Math.max(maxHeight, 0)}px`;
			
			quill.enable();
			
			editorContainer.style.border = '1px solid #ccc';
			editorContainer.style.cursor = 'pointer';
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

    	botaoColorPicker.style.width = '16px';
    	botaoColorPicker.style.height = '16px';
     	botaoColorPicker.style.padding = '0';
     	botaoColorPicker.style.border = 'none';
     	botaoColorPicker.style.cursor = 'pointer';
     	botaoColorPicker.style.position = 'fixed';
     	botaoColorPicker.style.bottom = '30%';
     	botaoColorPicker.style.left = '76.6%';

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
	
	//MUDAR ISSO AQUI!
	if (tipo === "Desenho") {
		
	}
	
    // Caso Projetos Concluídos
    if (tipo === 'Projetos Concluídos' && usuario.role == "Aluno") {
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
	        a.href = `/aluno/emites?id=${projeto.id}&tipo=projeto&aluno=${u.id}`;
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
	        a.href = `/aluno/emites?id=${estagio.id}&tipo=estagio&aluno=${u.id}`;
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

    // Adicionando Tópicos caso seja necessário
	
	//ARRUMAR TAMANHO SENDO SALVO TODO ERRADO
    if (criarTopico) {
		let botaoCriarTopico = document.createElement('button');
		botaoCriarTopico.className = 'seccao-botao-criar-topico';
		botaoCriarTopico.type = "button";
        seccao.appendChild(botaoCriarTopico);
		
		let qtdTopicosFront = qtdTopicos;
			
		let inputQtdTopicos = document.createElement('input');
		inputQtdTopicos.type = 'hidden';
		inputQtdTopicos.name = 'qtdTopicos'; 
		inputQtdTopicos.value = qtdTopicosFront;
		seccao.appendChild(inputQtdTopicos);
	
        botaoCriarTopico.addEventListener('click', function () {
			qtdTopicosFront++;
			inputQtdTopicos.value = qtdTopicosFront;
            if (tipo === 'Licenças e Certificados' && qtdTopicos <= 100) {
                let caixaTexto = document.createElement('textarea');
                caixaTexto.className = 'caixa-texto-topico2';
                caixaTexto.placeholder = "Escreva seu texto...";
				caixaTexto.value = topico.conteudoTexto;
                caixaTexto.maxLength = 122;
				caixaTexto.style.height = '35px';
				caixaTexto.style.width = topico.comprimentoConteudoTexto + 'px';
				caixaTexto.style.maxWidth = '417px';
				
				let inputEstado = document.createElement('input');
                inputEstado.type = 'hidden';
				inputEstado.name = 'estado';
				inputEstado.value = topico.estado;
				
				let inputConteudoTextoTopico = document.createElement('input');
                inputConteudoTextoTopico.type = 'hidden';
				inputConteudoTextoTopico.name = 'conteudoTextoTopico';
				inputConteudoTextoTopico.value = topico.conteudoTexto;
				
				let comprimentoConteudoTextoInput = document.createElement('input');
			    comprimentoConteudoTextoInput.type = 'hidden';
			    comprimentoConteudoTextoInput.name = 'comprimentoConteudoTextoTopico';
			    seccao.appendChild(comprimentoConteudoTextoInput);

			    let alturaConteudoTextoInput = document.createElement('input');
			    alturaConteudoTextoInput.type = 'hidden';
			    alturaConteudoTextoInput.name = 'alturaConteudoTextoTopico';
			    seccao.appendChild(alturaConteudoTextoInput);
				
                let inputLogo = document.createElement('input');
                inputLogo.type = 'file';
				inputLogo.name = 'conteudoImagem';
				inputLogo.hidden = true;
                inputLogo.accept = 'image/*';
				
                let logoPreview = document.createElement('img');
                logoPreview.className = 'logo-preview';
                logoPreview.style.width = '50px';
                logoPreview.style.height = '50px';

                let containerLogoTexto = document.createElement('div');
				containerLogoTexto.className = 'container-logo-texto';
				
				inputConteudoTextoTopico.value = caixaTexto.value;
				comprimentoConteudoTextoInput.value = caixaTexto.scrollWidth;
				alturaConteudoTextoInput.value = caixaTexto.scrollHeight;
				
				let atualizarDados = () => {
				    inputConteudoTextoTopico.value = caixaTexto.value;
				    comprimentoConteudoTextoInput.value = caixaTexto.scrollWidth;
				    alturaConteudoTextoInput.value = caixaTexto.scrollHeight;
				};
				
				caixaTexto.addEventListener('input', atualizarDados);
				caixaTexto.addEventListener('mouseup', atualizarDados);
				
                let botaoApagarTopico = document.createElement('button');
                botaoApagarTopico.className = 'seccao-botao-apagar-topico';
				
				containerLogoTexto.appendChild(inputConteudoTextoTopico);
				containerLogoTexto.appendChild(inputLogo);
				containerLogoTexto.appendChild(botaoApagarTopico);
		        containerLogoTexto.appendChild(logoPreview);
		        containerLogoTexto.appendChild(caixaTexto);

		        let inputArquivo = document.createElement('input');
		        inputArquivo.type = 'file';
		        inputArquivo.hidden = true; 
				inputArquivo.name = 'conteudoArquivo';
		        inputArquivo.accept = 'image/*,.pdf';

		        let labelUpload = document.createElement('label');
		        labelUpload.innerText = 'Anexar certificado/licença';
		        labelUpload.classList.add('upload-label');

		        containerLogoTexto.appendChild(inputArquivo);
		        containerLogoTexto.appendChild(labelUpload);

		        seccao.appendChild(containerLogoTexto);

				labelUpload.addEventListener('click', () => {
				    inputArquivo.click();
				});
				
				logoPreview.addEventListener('click', function() {
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
					qtdTopicosFront--;
					inputQtdTopicos.value = qtdTopicosFront;
                    containerLogoTexto.remove();
                });
            }
        });
    }
	
	seccao.style.display = 'block';
	document.body.appendChild(seccao);
	document.body.appendChild(overlay);
}

//USAR ISSO SÓ PRA RENDERIZAR PUBLICAÇÕES NO GRID QUE SERA FEITO. DEPOIS MUDO

//let limite = Math.min(indiceAtual + quantidadePorPagina, secoes.length);

/*for (let i = indiceAtual; i < limite; i++) {
	REPETIÇÃO
}*/

secoes.forEach(secao => {
	let tituloPersonalizado = false;

    let seccao = document.createElement('section');
    seccao.className = 'seccao';
    seccao.style.userSelect = 'text';
	
	let botaoApagar = document.createElement('button');
    botaoApagar.className = 'seccao-botao-apagar';
	
	let formApagarSecao = document.createElement('form');
	formApagarSecao.id = 'form-apagar-secao';
	formApagarSecao.action = "/perfil/apagar-secao"; 
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
	
	botaoApagar.addEventListener('click', function () {
	    if (confirm('Você tem certeza que deseja excluir esta seção?')) {
			formApagarSecao.submit();
	        seccao.remove();
	    }
	});
	
	seccao.appendChild(botaoApagar);
	
	let botaoEditar = document.createElement('button');
    botaoEditar.className = 'seccao-botao-editar';
	
	botaoEditar.addEventListener('click', function () {
		editarSecao(secao, topicos);
	});
	
	seccao.appendChild(botaoEditar);
	
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
	if (secao.tipo === "Desenho") {
		tituloPersonalizado = true;
		criarTopico = false;
	}

	if (tituloPersonalizado) {
		let titulo = document.createElement('h3');
		titulo.innerText = secao.titulo;
		titulo.className = 'titulo-seccao';
		seccao.appendChild(titulo);
	}
	else {
		let titulo2 = document.createElement('h3');
		titulo2.innerText = secao.tipo;
		titulo2.className = 'titulo-seccao-texto';
		seccao.appendChild(titulo2);
	}
	
	//criações

	if (secao.tipo === "Projetos Concluídos") {
		seccao.style.height = '72vh';
		
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
			a.href = `/perfil-aluno/emite?id=${projeto.id}&tipo=projeto&aluno=${u.id}`;
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
			a.href = `/perfil-aluno/emite?id=${estagio.id}&tipo=estagio&aluno=${u.id}`;
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
		
	if (secao.tipo === "Texto Livre") {
	    let editorContainer = document.createElement('div');
	    editorContainer.className = 'editor-container';
	    editorContainer.style.resize = 'none';
	    editorContainer.style.width = secao.comprimentoConteudoTexto + 'px';
	    editorContainer.style.height = secao.alturaConteudoTexto + 'px';
	    editorContainer.style.position = 'absolute'; 
	    editorContainer.style.left = secao.leftConteudoTexto + 'px'; 
	    editorContainer.style.top = secao.topConteudoTexto + 'px';
	    editorContainer.style.fontSize = '16px';
	    editorContainer.style.overflow = 'auto';
	    editorContainer.style.marginBottom = '20px';
		editorContainer.style.display = 'block';
		/*pra teste da posição
		editorContainer.style.border = '#ff0000 1px solid'*/
		
		if (secao.topConteudoTexto === 0) {
		    editorContainer.style.top = '45px';
		    setTimeout(() => {
		        let rectUpdated = editorContainer.getBoundingClientRect();
		        topConteudoTextoInput.value = rectUpdated.top;
		    }, 0);
		}

		if (secao.leftConteudoTexto === 0) {
		    editorContainer.style.left = '50px';
		    setTimeout(() => {
		        let rectUpdated = editorContainer.getBoundingClientRect();
		        leftConteudoTextoInput.value = rectUpdated.left;
		    }, 0);
		}

		if (secao.comprimentoConteudoTexto === 0) {
		    editorContainer.style.width = '200px';
		    setTimeout(() => {
		        let rectUpdated = editorContainer.getBoundingClientRect();
		        comprimentoConteudoTextoInput.value = rectUpdated.width;
		    }, 0);
		}

		if (secao.alturaConteudoTexto === 0) {
		    editorContainer.style.height = '50px';
		    setTimeout(() => {
		        let rectUpdated = editorContainer.getBoundingClientRect();
		        alturaConteudoTextoInput.value = rectUpdated.height;
		    }, 0);
		}
		
	    seccao.appendChild(editorContainer);
		
	    let alturaNecessaria = editorContainer.offsetTop + editorContainer.offsetHeight + 20;
	    seccao.style.height = Math.max(seccao.scrollHeight, alturaNecessaria) + 'px';

	    const quill = new Quill(editorContainer, {
	        theme: 'bubble',
	        readOnly: true,
	        modules: {
	            toolbar: false,
	        },
	    });

	    quill.root.innerHTML = secao.conteudoTexto;
	}
	
	if (secao.tipo === "Desenho") {
		let desenho = document.createElement('img');
		if (secao.conteudoImagem)
		    desenho.src = 'data:image/jpeg;base64,' + secao.conteudoImagem;
		else 
		    desenho.src = '../img/fundo-branco.png';
		seccao.appendChild(desenho);
	}
	
	if (secao.tipo === 'Licenças e Certificados') {
		let indiceAtualLC = 0;
		let quantidadePorPaginaLC = 4;
		let botaoCarregarMaisLC = document.createElement('button');
		botaoCarregarMaisLC.className = 'botao-carregar-mais-LC';
		botaoCarregarMaisLC.innerText = 'Carregar Mais';
		botaoCarregarMaisLC.style.display = 'none';
		
		let carregarSecaoLicencaCertificado = () => {
			seccao.style.height = '72vh';

			let limiteLC = Math.min(indiceAtualLC + quantidadePorPaginaLC, topicos.length);
			
		    for (let i = indiceAtualLC; i < limiteLC; i++) {
				topico = topicos[i];
				if(topico.secaoId == secao.id) {
		            let arquivoTopico = topico.conteudoArquivo;
		        
			        let caixaTexto = document.createElement('textarea');
			        caixaTexto.className = 'caixa-texto-topico2';
					caixaTexto.style.cursor = 'text';
			        caixaTexto.disabled = true;
					caixaTexto.style.resize = 'none';
			        caixaTexto.innerText = topico.conteudoTexto;
					caixaTexto.style.border = '#ccc 1px solid';
					caixaTexto.style.width = topico.comprimentoConteudoTexto + 'px';
					caixaTexto.style.height = '35px';
					
					if (topico.comprimentoConteudoTexto === 0) {
				        caixaTexto.style.width = '100px';
				    }
		
			        let logoPreview = document.createElement('img');
					if (topico.conteudoImagem)
					    logoPreview.src = 'data:image/jpeg;base64,' + topico.conteudoImagem;
					 else 
					    logoPreview.src = '../img/fundo-branco.png';
					    
			        logoPreview.className = 'logo-preview';
			        logoPreview.style.width = '50px';
			        logoPreview.style.height = '50px';
		
			        let containerLogoTexto = document.createElement('div');
			        containerLogoTexto.className = 'container-logo-texto';
		
			        containerLogoTexto.appendChild(logoPreview);
			        containerLogoTexto.appendChild(caixaTexto);
		
			        let buttonAbrirArquivo = document.createElement('button');
			        buttonAbrirArquivo.type = 'button';
			        buttonAbrirArquivo.innerText = 'Ver certificado / licença';
			        buttonAbrirArquivo.classList.add('upload-label');
		
			        containerLogoTexto.appendChild(buttonAbrirArquivo);
					
					let verificadoDiv = document.createElement('div');
					verificadoDiv.className = 'verificado-container';

					let verificadoImg = document.createElement('img');
					let verificadoTexto = document.createElement('p');

					verificadoImg.style.width = '30px';
					verificadoImg.style.height = '30px';
					verificadoImg.style.objectFit = 'cover';

					if (topico.estado) {
					    verificadoTexto.innerText = 'Verificado em ' + topico.data;
					    verificadoImg.src = '../img/verificado.png';
					} else {
					    verificadoTexto.innerText = 'Não verificado';
					    verificadoImg.src = '../img/nao-verificado.png';
					}

					verificadoDiv.appendChild(verificadoImg);
					verificadoDiv.appendChild(verificadoTexto);
					containerLogoTexto.appendChild(verificadoDiv);
					
					verificadoDiv.addEventListener('mouseover', function() {
					    verificadoTexto.style.display = 'block';
					});

					verificadoDiv.addEventListener('mouseout', function() {
						verificadoTexto.style.display = 'none';
					});

					containerLogoTexto.appendChild(verificadoDiv);
		
					buttonAbrirArquivo.addEventListener('click', function() {
					    if (arquivoTopico) {
					        let byteCharacters = atob(arquivoTopico);
					        let byteNumbers = new Array(byteCharacters.length);
					        for (let i = 0; i < byteCharacters.length; i++) {
					            byteNumbers[i] = byteCharacters.charCodeAt(i);
					        }
					        let byteArray = new Uint8Array(byteNumbers);

					        let mimeType = "application/pdf";
					        if (arquivoTopico.startsWith('/9j/')) {
					            mimeType = "image/jpeg";
					        } else if (arquivoTopico.startsWith('iVBOR')) {
					            mimeType = "image/png";
					        }

					        let blob = new Blob([byteArray], { type: mimeType });
					        let url = URL.createObjectURL(blob);

					        let novaAba = window.open(url);
					        if (!novaAba) {
					            alert("Pop-up bloqueado! Permita pop-ups para visualizar o arquivo.");
					        }
					    } else {
					        alert("Arquivo não disponível!");
					    }
					});
			        seccao.appendChild(containerLogoTexto);
					seccao.appendChild(botaoCarregarMaisLC);
				}
		    };
			
			indiceAtualLC = limiteLC;
			
			botaoCarregarMaisLC.style.display = indiceAtualLC < topicos.length ? 'block' : 'none';
			
			botaoCarregarMaisLC.addEventListener('click', carregarSecaoLicencaCertificado);
		};
		
		carregarSecaoLicencaCertificado();
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