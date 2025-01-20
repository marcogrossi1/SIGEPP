let botaoAvancarNotificacaoEl = document.querySelector('#avancar-notificacao');
let botaoVoltarNotificacaoEl = document.querySelector('#voltar-notificacao');
let notificacaoUmEl = document.querySelector('#not-um');
let notificacaoDoisEl = document.querySelector('#not-dois');
let notificacaoTresEl = document.querySelector('#not-tres');
let notificacaoUmImagemEl = document.querySelector('#not-um-select');
let notificacaoDoisImagemEl = document.querySelector('#not-dois-select');
let notificacaoTresImagemEl = document.querySelector('#not-tres-select');

botaoAvancarNotificacaoEl.addEventListener('click', function () {
    if(notificacaoUmEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'none';
        notificacaoUmImagemEl.style.display = 'none';
        notificacaoDoisEl.style.display = 'inline';
        notificacaoDoisImagemEl.style.display = 'inline';
        notificacaoTresEl.style.display = 'none';
        notificacaoTresImagemEl.style.display = 'none';
    }
    else if(notificacaoDoisEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'none';
        notificacaoUmImagemEl.style.display = 'none';
        notificacaoDoisEl.style.display = 'none';
        notificacaoDoisImagemEl.style.display = 'none';
        notificacaoTresEl.style.display = 'inline';
        notificacaoTresImagemEl.style.display = 'inline';
    }
    else if(notificacaoTresEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'inline';
        notificacaoUmImagemEl.style.display = 'inline';
        notificacaoDoisEl.style.display = 'none';
        notificacaoDoisImagemEl.style.display = 'none';
        notificacaoTresEl.style.display = 'none';
        notificacaoTresImagemEl.style.display = 'none';
    }
})

botaoVoltarNotificacaoEl.addEventListener('click', function () {
    if(notificacaoUmEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'none';
        notificacaoUmImagemEl.style.display = 'none';
        notificacaoDoisEl.style.display = 'none';
        notificacaoDoisImagemEl.style.display = 'none';
        notificacaoTresEl.style.display = 'inline';
        notificacaoTresImagemEl.style.display = 'inline';
    }
    else if(notificacaoDoisEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'inline';
        notificacaoUmImagemEl.style.display = 'inline';
        notificacaoDoisEl.style.display = 'none';
        notificacaoDoisImagemEl.style.display = 'none';
        notificacaoTresEl.style.display = 'none';
        notificacaoTresImagemEl.style.display = 'none';
    }
    else if(notificacaoTresEl.style.display !== 'none'){
        notificacaoUmEl.style.display = 'none';
        notificacaoUmImagemEl.style.display = 'none';
        notificacaoDoisEl.style.display = 'inline';
        notificacaoDoisImagemEl.style.display = 'inline';
        notificacaoTresEl.style.display = 'none';
        notificacaoTresImagemEl.style.display = 'none';
    }
})