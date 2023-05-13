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
    }


    form.addEventListener('submit', (event) => {
        event.preventDefault(); // previne o comportamento padrão do envio do formulário
        const valor = document.getElementById("valor");
        valor.value = valor.value.replace(/,/g, '.');
        const formData = new FormData(form);



        fetch(rota, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.status === 200) {
                    setTimeout(() => {
                        adicionarProdutoLista(formData.get("nome"), "green");
                        form.reset();
                    }, 500);
                } else if (response.status === 208) {
                    alert("Este produto já existe no banco de dados!");
                }else if (response.status === 204) {
                    adicionarProdutoLista(formData.get("nome"), "yellow");
                } else {
                    alert("Erro ao cadastrar produto!");
                }
            })

            .catch(error => alert(error.message));
    });
});

function adicionarProdutoLista(nome, cor){
    var tabela = document.getElementById("produtosAdicionados");
    var novaLinha = document.createElement("tr");
    var novaCelula = document.createElement("td");
    novaCelula.textContent ="Produto " + nome + " foi adiocionado!";
    novaCelula.style.color = cor;
    novaLinha.appendChild(novaCelula);
    var corpoTabela = tabela.querySelector("tbody");
    corpoTabela.appendChild(novaLinha);
}