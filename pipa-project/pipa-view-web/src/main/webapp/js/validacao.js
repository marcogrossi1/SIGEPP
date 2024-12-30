function validacaoView(inputEl, regexEl, smallEl, labelEl, inputObrigatorioEl) {
    if(regexEl.test(inputEl.value)) {
        smallEl.classList.remove("invalido");
        labelEl.style.color = "#00a65a";
        inputEl.style.borderColor = "#00a65a";
        inputObrigatorioEl.style.visibility = "hidden";
    }
    else {
        smallEl.classList.add("invalido");
        labelEl.style.color = "#dd4b39";
        inputEl.style.borderColor = "#dd4b39";
        inputObrigatorioEl.style.visibility = "visible";
        inputEl.focus();
    }
}

let loginEl = document.querySelector("#login");
loginEl.addEventListener("change", () => {

    let smallEl = document.querySelector("#login ~ small");
    let labelEl = document.querySelector("label[for=login]");
    let inputObrigatorioEl = document.querySelector("label[for=login] .input-obrigatorio");
    
    if(loginEl == null){
        smallEl.classList.add("invalido");
        labelEl.style.color = "#dd4b39";
        loginEl.style.borderColor = "#dd4b39";
        inputObrigatorioEl.style.visibility = "visible";
    } else {
        smallEl.classList.remove("invalido");
        labelEl.style.color = "#00a65a";
        loginEl.style.borderColor = "#00a65a";
        inputObrigatorioEl.style.visibility = "hidden";
    }
});

let cpfEl = document.querySelector("#cpf");
cpfEl.addEventListener("change", () => {
    let regexEl = /^[0-9]{11}$/;
    let smallEl = document.querySelector("#cpf ~ small");
    let labelEl = document.querySelector("label[for=cpf]");
    let inputObrigatorioEl = document.querySelector("label[for=cpf] .input-obrigatorio");
    
    validacaoView(cpfEl, regexEl, smallEl, labelEl, inputObrigatorioEl);
});

let nomeEl = document.querySelector("#nome");
nomeEl.addEventListener("change", () => {
    let regexEl = /^[A-z]+([ ][A-z]+)+/;
    let smallEl = document.querySelector("#nome ~ small");
    let labelEl = document.querySelector("label[for=nome]");
    let inputObrigatorioEl = document.querySelector("label[for=nome] .input-obrigatorio");

    validacaoView(nomeEl, regexEl, smallEl, labelEl, inputObrigatorioEl);
});

let emailEl = document.querySelector("#email");
emailEl.addEventListener("change", () => {
    let regexEl = /^[^0-9][A-z0-9_]+([.][A-z0-9_]+)*[@][A-z0-9_]+([.][A-z0-9_]+)*[.][A-z]{2,4}$/;
    let smallEl = document.querySelector("#email ~ small");
    let labelEl = document.querySelector("label[for=email]");
    let inputObrigatorioEl = document.querySelector("label[for=email] .input-obrigatorio");

    validacaoView(emailEl, regexEl, smallEl, labelEl, inputObrigatorioEl);
});

let senhaEl = document.querySelector("#senha");
senhaEl.addEventListener("change", () => {
    let regexEl = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/;
    let smallEl = document.querySelector("#senha ~ small");
    let labelEl = document.querySelector("label[for=senha]");
    let inputObrigatorioEl = document.querySelector("label[for=senha] .input-obrigatorio");

    validacaoView(senhaEl, regexEl, smallEl, labelEl, inputObrigatorioEl);
});

let senha2El = document.querySelector("#senha2");
senha2El.addEventListener("change", () => {
    let smallEl = document.querySelector("#senha2 ~ small");
    let labelEl = document.querySelector("label[for=senha2]");
    let inputObrigatorioEl = document.querySelector("label[for=senha2] .input-obrigatorio");
    
    if(senha2El.value !== senhaEl.value){
        smallEl.classList.add("invalido");
        labelEl.style.color = "#dd4b39";
        senha2El.style.borderColor = "#dd4b39";
        inputObrigatorioEl.style.visibility = "visible";
        senha2El.focus();
    } else {
        smallEl.classList.remove("invalido");
        labelEl.style.color = "#00a65a";
        senha2El.style.borderColor = "#00a65a";
        inputObrigatorioEl.style.visibility = "hidden";
    }
});