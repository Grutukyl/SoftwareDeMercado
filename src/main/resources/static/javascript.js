function enviarDelete(id) {
    fetch(`/deletar/${id}`, {
        method: 'POST',
    }).then(response => {
        if (response.ok) {
            location.reload(); // atualiza a página
        }
    });
}

function redirectCadastro() {
    fetch(`/cadastro`, {
        method: 'GET',
    });
}

document.addEventListener('DOMContentLoaded', function () {

    const form = document.getElementById("cadastroProduto");
    const att = document.getElementById("ID");
    let rota = '/cadastrar'
    if(att != null){

        rota = "/atualizarProduto";
        alert(rota);
    }


    form.addEventListener('submit', (event) => {
        event.preventDefault(); // previne o comportamento padrão do envio do formulário

        const formData = new FormData(form);


        fetch(rota, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.status === 200) {
                    setTimeout(() => {
                        alert("Produto cadastrado com sucesso!");
                    }, 500);
                    adicionarProdutoLista(formData.get("nome"));
                } else if (response.status === 208) {
                    alert("Este produto já existe no banco de dados!");
                } else {
                    alert("Erro ao cadastrar produto!");
                }
            })

            .catch(error => alert(error.message));
    });
});

function adicionarProdutoLista(nome){
    var tabela = document.getElementById("produtosAdicionados");
    var novaLinha = document.createElement("tr");
    var novaCelula = document.createElement("td");
    novaCelula.textContent ="Produto " + nome + " foi adiocionado!";
    novaLinha.appendChild(novaCelula);
    var corpoTabela = tabela.querySelector("tbody");
    corpoTabela.appendChild(novaLinha);
}