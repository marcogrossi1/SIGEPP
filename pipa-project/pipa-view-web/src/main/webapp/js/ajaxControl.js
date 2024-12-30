let autoresResultEL = document.querySelector("#resultados-pesquisa-autores");
let assuntosResultEL = document.querySelector("#resultados-pesquisa-assuntos");
let bibliotecaResultEL = document.querySelector("#resultados-pesquisa-biblioteca");
let obrasResultEl = document.querySelector("#resultados-pesquisa-obras");

function ajaxAutor() {
  let xh;
  if (window.XMLHttpRequest) // código dos browsers modernos
    xh = new XMLHttpRequest();
  else
    xh = new ActiveXObject("Microsoft.XMLHTTP");

  xh.onreadystatechange = function (){
    if (this.readyState == 4 && this.status == 200) {
      autoresResultEL.innerHTML = this.responseText;
    };
  }

  let nomeAutor = encodeURIComponent(document.querySelector("#nomeAutor").value);
  let parameters = "nomeAutor=" + nomeAutor;
  xh.open("POST", "/sgb/PesquisaAutorAjax", true);
  xh.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xh.send(parameters);
}
  
function ajaxAssunto() {
  let xh;
  if (window.XMLHttpRequest) // código dos browsers modernos
    xh = new XMLHttpRequest();
  else
    xh = new ActiveXObject("Microsoft.XMLHTTP");

  xh.onreadystatechange = function (){
    if (this.readyState == 4 && this.status == 200) {
      assuntosResultEL.innerHTML = this.responseText;
    };
  }

  let assunto = encodeURIComponent(document.querySelector("#nomeAssunto").value);
  let parameters = "nomeAssunto=" + assunto;
  xh.open("POST", "/sgb/PesquisaAssuntoAjax", true);
  xh.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xh.send(parameters);
}

function ajaxBiblioteca(){
    let xh;
  if (window.XMLHttpRequest) // código dos browsers modernos
    xh = new XMLHttpRequest();
  else
    xh = new ActiveXObject("Microsoft.XMLHTTP");

  xh.onreadystatechange = function (){
    if (this.readyState == 4 && this.status == 200) {
      bibliotecaResultEL.innerHTML = this.responseText;
    };
  }

  let biblioteca = encodeURIComponent(document.querySelector("#nomeBiblioteca").value);
  let parameters = "nomeBiblioteca=" + biblioteca;
  xh.open("POST", "/sgb/PesquisaBibliotecaAjax", true);
  xh.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xh.send(parameters);
}

function ajaxObra() {
  let xh;
  if (window.XMLHttpRequest) // código dos browsers modernos
    xh = new XMLHttpRequest();
  else
    xh = new ActiveXObject("Microsoft.XMLHTTP");

  xh.onreadystatechange = function (){
    if (this.readyState == 4 && this.status == 200) {
      obrasResultEl.innerHTML = this.responseText;
    };
  }

  let nomeObra = encodeURIComponent(document.querySelector("#nomeObra").value);
  let parameters = "nomeObra=" + nomeObra;
  xh.open("POST", "/sgb/PesquisaObraAjax", true);
  xh.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xh.send(parameters);
}
  
function adicionaAutor(nome){

    let autoresEL = document.querySelector("#autores");
    let autoresInputEl = document.querySelector("#autores-input");
    
    let nomeFormatado = nome.split(" ");
    nomeFormatado = nomeFormatado.join("--");

    let novoAutorEl = document.createElement("div");
    novoAutorEl.innerHTML = "<span>" + nome + "</span><input type=\"button\" value=\"Excluir\" onclick=\"excluirAutor('" + nomeFormatado + "')\">";
    novoAutorEl.classList.add("acoes");
    novoAutorEl.id = nomeFormatado;

    autoresEL.appendChild(novoAutorEl);
    autoresInputEl.value = autoresInputEl.value + nome + "::";

}
  
function adicionaAssunto(nome){

    let assuntosEL = document.querySelector("#assuntos");
    let assuntosInputEl = document.querySelector("#assuntos-input");
    
    let nomeFormatado = nome.split(" ");
    nomeFormatado = nomeFormatado.join("--");

    let novoAssuntoEl = document.createElement("div");
    novoAssuntoEl.innerHTML = "<span>" + nome + "</span><input type=\"button\" value=\"Excluir\" onclick=\"excluirAssunto('" + nomeFormatado + "')\">";
    novoAssuntoEl.classList.add("acoes");
    novoAssuntoEl.id = nomeFormatado;

    assuntosEL.appendChild(novoAssuntoEl);
    assuntosInputEl.value = assuntosInputEl.value + nome + "::";

}

function adicionaBiblioteca(nome){

    let bibliotecaEL = document.querySelector("#biblioteca");
    let bibliotecaInputEl = document.querySelector("#biblioteca-input");
    
    let nomeFormatado = nome.split(" ");
    nomeFormatado = nomeFormatado.join("--");
    
    let novaBibliotecaEl = document.createElement("div");
    novaBibliotecaEl.innerHTML = "<span>" + nome + "</span><input type=\"button\" value=\"Excluir\" onclick=\"excluirBiblioteca('" + nomeFormatado + "')\">";
    novaBibliotecaEl.classList.add("acoes");
    novaBibliotecaEl.id = nomeFormatado;

    bibliotecaEL.appendChild(novaBibliotecaEl);
    bibliotecaInputEl.value = bibliotecaInputEl.value + nome + "::";
    
    let botaoAdicionaBibliotecaEl = document.querySelector("#adiciona-biblioteca");
    botaoAdicionaBibliotecaEl.style.display = "none";

}

function adicionaObra(nome){

  let obrasEL = document.querySelector("#obras");
  let obrasInputEl = document.querySelector("#obras-input");
  
  let nomeFormatado = nome.split(" ");
  nomeFormatado = nomeFormatado.join("--");

  let novaObraEl = document.createElement("div");
  novaObraEl.innerHTML = "<span>" + nome + "</span><input type=\"button\" value=\"Excluir\" onclick=\"excluirObra('" + nomeFormatado + "')\">";
  novaObraEl.classList.add("acoes");
  novaObraEl.id = nomeFormatado;

  obrasEL.appendChild(novaObraEl);
  obrasInputEl.value = obrasInputEl.value + nome + "::";

}

function excluirAutor(nome){
    let descricao = nome + "::";
    let regex = new RegExp(descricao, "gm");
    let autoresInputEl = document.querySelector("#autores-input");

    let autorAlvoEl = document.querySelector("#" + nome);
    autorAlvoEl.parentNode.removeChild(autorAlvoEl);
    autoresInputEl.value = autoresInputEl.value.replace(regex, "");
}
  
function excluirAssunto(nome){
    let descricao = nome + "::";
    let regex = new RegExp(descricao, "gm");
    let assuntosInputEl = document.querySelector("#assuntos-input");

    let assuntoAlvoEl = document.querySelector("#" + nome);
    assuntoAlvoEl.parentNode.removeChild(assuntoAlvoEl);
    assuntosInputEl.value = assuntosInputEl.value.replace(regex, "");
}

function excluirBiblioteca(nome){
    let descricao = nome + "::";
    let regex = new RegExp(descricao, "gm");
    let bibliotecaInputEl = document.querySelector("#biblioteca-input");

    let bibliotecaAlvoEl = document.querySelector("#" + nome);
    bibliotecaAlvoEl.parentNode.removeChild(bibliotecaAlvoEl);
    bibliotecaInputEl.value = bibliotecaInputEl.value.replace(regex, "");
    
    let botaoAdicionaBibliotecaEl = document.querySelector("#adiciona-biblioteca");
    botaoAdicionaBibliotecaEl.style.display = "inline";
}

function excluirObra(nome){
  let descricao = nome + "::";
  let regex = new RegExp(descricao, "gm");
  let obrasInputEl = document.querySelector("#obras-input");

  let obrasAlvoEl = document.querySelector("#" + nome);
  obrasAlvoEl.parentNode.removeChild(obrasAlvoEl);
  obrasInputEl.value = obrasInputEl.value.replace(regex, "");
}


