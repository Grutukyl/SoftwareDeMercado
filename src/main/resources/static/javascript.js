





function enviarDelete(id) {
    fetch(`/deletar/${id}`,  {
        method: 'POST',
    }).then(response => {
        if (response.ok) {
            location.reload(); // atualiza a página
        }
    });
}

function redirectCadastro() {
    fetch(`/cadastro`,  {
        method: 'GET',
    });
}

function salvarCadastro(){

}