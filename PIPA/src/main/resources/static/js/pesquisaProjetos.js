function filtrarProjetos() {
    var input = document.getElementById('pesquisa').value.toLowerCase();
    var filtroRemunerado = document.getElementById('filtroRemunerado').checked;
    var filtroCurso = document.getElementById('filtroCurso').value.toLowerCase();
    var projetos = document.querySelectorAll('.projeto');

    projetos.forEach(function(projeto) {
        var nomeProjeto = projeto.querySelector('h2').textContent.toLowerCase();
        var vagasRemuneradas = parseInt(projeto.querySelector('.tag')?.textContent.includes("Remunerado") ? 1 : 0);
        var cursos = Array.from(projeto.querySelectorAll('.tag')).map(tag => tag.textContent.toLowerCase());

        var correspondeNome = nomeProjeto.includes(input);
        var correspondeRemuneracao = !filtroRemunerado || (vagasRemuneradas > 0);
        var correspondeCurso = filtroCurso === "" || cursos.includes(filtroCurso);

        if (correspondeNome && correspondeRemuneracao && correspondeCurso) {
            projeto.style.display = '';
        } else {
            projeto.style.display = 'none';
        }
    });
}