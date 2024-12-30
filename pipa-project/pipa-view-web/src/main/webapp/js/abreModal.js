function abreModal(id){
    let targetModalEl = document.querySelector("#" + id);
    let maskEl = document.querySelector("#mask");

    targetModalEl.style.display = "block";
    maskEl.style.display = "block";
}

function fechaModal(id){
    let targetModalEl = document.querySelector("#" + id);
    let maskEl = document.querySelector("#mask");

    targetModalEl.style.display = "none";
    maskEl.style.display = "none";
}

function fechaModalAll(){
    let modals = document.querySelectorAll(".form-popup[style=\"display: block;\"]");
    console.log("teste");
    modals.forEach(e => {
        e.style.display = "none";
    })
    document.querySelector("#mask").style.display = "none";
}