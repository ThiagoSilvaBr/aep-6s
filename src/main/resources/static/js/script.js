const botaoNovaEspecie = document.getElementById("botaoNovaEspecie");
const modalNovaEspecie = document.getElementById("modalNovaEspecie");
const botaoFecharModal = document.getElementById("botaoFecharModal");
const formNovaEspecie = document.getElementById("formNovaEspecie");

const tabelaEspecies = document.getElementById("tabelaEspecies");
const campoBusca = document.getElementById("campoBusca");

const modalVerEspecie = document.getElementById("modalVerEspecie");
const botaoFecharVerEspecie = document.getElementById("botaoFecharVerEspecie");
const botaoAtualizar = document.getElementById("botaoAtualizar");
const botaoDeletar = document.getElementById("botaoDeletar");

let especieSelecionada = null;

botaoNovaEspecie.addEventListener("click", function () {
    modalNovaEspecie.style.display = "flex";
});

botaoFecharModal.addEventListener("click", function () {
    modalNovaEspecie.style.display = "none";
});

formNovaEspecie.addEventListener("submit", async function (event) {

    event.preventDefault();

    const especie = {
        nomePopular: document.getElementById("nomePopular").value,
        nomeCientifico: document.getElementById("nomeCientifico").value,
        grupo: document.getElementById("grupo").value,
        bioma: document.getElementById("bioma").value,
        nivelRisco: document.getElementById("nivelRisco").value,
        populacaoEstimada: Number(
            document.getElementById("populacaoEstimada").value
        )
    };

    try {

        const resposta = await fetch("/especies", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(especie)
        });

        if (!resposta.ok) {
            throw new Error("Não foi possível cadastrar a espécie.");
        }

        alert("Espécie cadastrada com sucesso!");

        formNovaEspecie.reset();
        modalNovaEspecie.style.display = "none";

        carregarEspecies();

    } catch (erro) {

        console.error(erro);
        alert("Erro ao cadastrar a espécie.");

    }

});

async function carregarEspecies(nomePopular = "") {

    try {

        let url = "/especies";

        if (nomePopular.trim() !== "") {
            url += `?nomePopular=${encodeURIComponent(nomePopular)}`;
        }

        const resposta = await fetch(url);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar as espécies.");
        }

        const especies = await resposta.json();

        tabelaEspecies.innerHTML = "";

        especies.forEach(function (especie) {

            const linha = document.createElement("tr");

            linha.innerHTML = `
                <td>${especie.nomePopular}</td>

                <td>${formatarBioma(especie.bioma)}</td>

                <td>${formatarGrupo(especie.grupo)}</td>

                <td>
                    <span class="risco">
                        ${formatarRisco(especie.nivelRisco)}
                    </span>
                </td>

                <td>
                    <button
                        class="botao-ver-mais"
                        data-id="${especie.id}">
                        Ver mais
                    </button>
                </td>
            `;

            tabelaEspecies.appendChild(linha);

        });

    } catch (erro) {

        console.error(erro);

        tabelaEspecies.innerHTML = `
            <tr>
                <td colspan="5">
                    Erro ao carregar as espécies.
                </td>
            </tr>
        `;

    }

}

campoBusca.addEventListener("input", function () {
    carregarEspecies(campoBusca.value);
});

tabelaEspecies.addEventListener("click", function (event) {
    if (!event.target.classList.contains("botao-ver-mais")) {
        return;
    }
    const id = event.target.dataset.id;
    abrirVerEspecie(id);
});

async function abrirVerEspecie(id) {

    try {

        const resposta = await fetch(`/especies/${id}`);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar a espécie.");
        }

        const especie = await resposta.json();

        especieSelecionada = especie;

        document.getElementById("verNomePopular").value = especie.nomePopular;
        document.getElementById("verNomeCientifico").value = especie.nomeCientifico;
        document.getElementById("verGrupo").value = especie.grupo;
        document.getElementById("verBioma").value = especie.bioma;
        document.getElementById("verNivelRisco").value = especie.nivelRisco;
        document.getElementById("verPopulacaoEstimada").value =
            especie.populacaoEstimada;

        sairDoModoEdicao();

        modalVerEspecie.style.display = "flex";

    } catch (erro) {

        console.error(erro);
        alert("Erro ao carregar os dados da espécie.");
    }
}

botaoFecharVerEspecie.addEventListener("click", function () {
    modalVerEspecie.style.display = "none";
    especieSelecionada = null;
});

botaoAtualizar.addEventListener("click", async function () {
    if (!especieSelecionada) {
        return;
    }

    if (botaoAtualizar.textContent === "Salvar") {
        await salvarAlteracoes();
        return;
    }

    entrarNoModoEdicao();
});

function entrarNoModoEdicao() {
    document.getElementById("verNomePopular").readOnly = false;
    document.getElementById("verNomeCientifico").readOnly = false;
    document.getElementById("verGrupo").disabled = false;
    document.getElementById("verBioma").disabled = false;
    document.getElementById("verNivelRisco").disabled = false;
    document.getElementById("verPopulacaoEstimada").readOnly = false;

    botaoAtualizar.textContent = "Salvar";
}

function sairDoModoEdicao() {
    document.getElementById("verNomePopular").readOnly = true;
    document.getElementById("verNomeCientifico").readOnly = true;
    document.getElementById("verGrupo").disabled = true;
    document.getElementById("verBioma").disabled = true;
    document.getElementById("verNivelRisco").disabled = true;
    document.getElementById("verPopulacaoEstimada").readOnly = true;

    botaoAtualizar.textContent = "Atualizar";
}

async function salvarAlteracoes() {

    const especieAtualizada = {

        nomePopular: document.getElementById("verNomePopular").value,

        nomeCientifico: document.getElementById("verNomeCientifico").value,

        grupo: document.getElementById("verGrupo").value,

        bioma: document.getElementById("verBioma").value,

        nivelRisco: document.getElementById("verNivelRisco").value,

        populacaoEstimada: Number(
            document.getElementById("verPopulacaoEstimada").value
        )
    };

    try {

        const resposta = await fetch(
            `/especies/${especieSelecionada.id}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(especieAtualizada)
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível atualizar a espécie.");
        }

        const especie = await resposta.json();

        especieSelecionada = especie;

        sairDoModoEdicao();

        alert("Espécie atualizada com sucesso!");

        carregarEspecies();

    } catch (erro) {

        console.error(erro);
        alert("Erro ao atualizar a espécie.");
    }
}

botaoDeletar.addEventListener("click", async function () {

    if (!especieSelecionada) {
        return;
    }

    const confirmou = confirm(
        `Deseja realmente deletar a espécie "${especieSelecionada.nomePopular}"?`
    );

    if (!confirmou) {
        return;
    }

    try {
        const resposta = await fetch(
            `/especies/${especieSelecionada.id}`,
            {
                method: "DELETE"
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível deletar a espécie.");
        }

        alert("Espécie deletada com sucesso!");

        modalVerEspecie.style.display = "none";

        especieSelecionada = null;

        carregarEspecies();

    } catch (erro) {

        console.error(erro);
        alert("Erro ao deletar a espécie.");

    }
});

function formatarBioma(bioma) {

    const biomas = {
        AMAZONIA: "Amazônia",
        CAATINGA: "Caatinga",
        CERRADO: "Cerrado",
        MATA_ATLANTICA: "Mata Atlântica",
        PAMPA: "Pampa",
        PANTANAL: "Pantanal"
    };

    return biomas[bioma] || bioma;
}

function formatarGrupo(grupo) {

    const grupos = {
        AVE: "Ave",
        MAMIFERO: "Mamífero",
        REPTIL: "Réptil",
        ANFIBIO: "Anfíbio",
        PEIXE: "Peixe"
    };

    return grupos[grupo] || grupo;
}

function formatarRisco(risco) {

    const riscos = {
        CRITICO: "Crítico",
        ALTO: "Alto",
        MODERADO: "Moderado",
        BAIXO: "Baixo"
    };

    return riscos[risco] || risco;
}

carregarEspecies();

const botaoNovaObservacao = document.getElementById("botaoNovaObservacao");
const modalNovaObservacao = document.getElementById("modalNovaObservacao");
const botaoFecharObservacao = document.getElementById("botaoFecharObservacao");
const formNovaObservacao = document.getElementById("formNovaObservacao");

const tabelaObservacoes = document.getElementById("tabelaObservacoes");
const selectEspecieObservacao = document.getElementById("observacaoEspecie");

const listaRegistros = document.getElementById("listaRegistros");
const botaoAdicionarRegistro = document.getElementById("botaoAdicionarRegistro");

const modalVerObservacao = document.getElementById("modalVerObservacao");
const botaoFecharVerObservacao = document.getElementById("botaoFecharVerObservacao");

let especiesDisponiveis = [];
let observacoesDisponiveis = [];
let observacaoSelecionada = null;

async function carregarDadosObservacoes() {

    try {

        const [respostaEspecies, respostaObservacoes] = await Promise.all([
            fetch("/especies"),
            fetch("/observacoes")
        ]);

        if (!respostaEspecies.ok || !respostaObservacoes.ok) {
            throw new Error("Não foi possível carregar os dados.");
        }

        especiesDisponiveis = await respostaEspecies.json();
        observacoesDisponiveis = await respostaObservacoes.json();

        carregarOpcoesEspecies();
        renderizarObservacoes();

    } catch (erro) {

        console.error(erro);

        tabelaObservacoes.innerHTML = `
            <tr>
                <td colspan="5">Erro ao carregar as observações.</td>
            </tr>
        `;
    }
}

function carregarOpcoesEspecies() {

    selectEspecieObservacao.innerHTML = `
        <option value="" selected disabled>Selecione a espécie</option>
    `;

    especiesDisponiveis.forEach(function (especie) {

        const opcao = document.createElement("option");

        opcao.value = especie.id;
        opcao.textContent = especie.nomePopular;

        selectEspecieObservacao.appendChild(opcao);

    });
}

function renderizarObservacoes() {

    tabelaObservacoes.innerHTML = "";

    if (observacoesDisponiveis.length === 0) {

        tabelaObservacoes.innerHTML = `
            <tr>
                <td colspan="5">Nenhuma observação cadastrada.</td>
            </tr>
        `;

        return;
    }

    observacoesDisponiveis.forEach(function (observacao) {

        const especie = especiesDisponiveis.find(
            item => item.id === observacao.especieId
        );

        const nomeEspecie = especie
            ? especie.nomePopular
            : "Espécie não encontrada";

        const linha = document.createElement("tr");

        const colunaEspecie = document.createElement("td");
        colunaEspecie.textContent = nomeEspecie;

        const colunaData = document.createElement("td");
        colunaData.textContent = formatarDataObservacao(
            observacao.dataObservacao
        );

        const colunaLocalizacao = document.createElement("td");
        colunaLocalizacao.textContent =
            `${observacao.localizacao.municipio} - ${observacao.localizacao.estado}`;

        const colunaQuantidade = document.createElement("td");
        colunaQuantidade.textContent =
            observacao.detalhes.quantidadeObservada;

        const colunaAcoes = document.createElement("td");

        const botaoVer = document.createElement("button");
        botaoVer.textContent = "Ver detalhes";
        botaoVer.classList.add("botao-ver-mais");
        botaoVer.dataset.id = observacao.id;

        const botaoExcluir = document.createElement("button");
        botaoExcluir.textContent = "Deletar";
        botaoExcluir.classList.add("botao-excluir-observacao");
        botaoExcluir.dataset.id = observacao.id;

        colunaAcoes.appendChild(botaoVer);
        colunaAcoes.appendChild(botaoExcluir);

        linha.appendChild(colunaEspecie);
        linha.appendChild(colunaData);
        linha.appendChild(colunaLocalizacao);
        linha.appendChild(colunaQuantidade);
        linha.appendChild(colunaAcoes);

        tabelaObservacoes.appendChild(linha);

    });
}

function formatarDataObservacao(data) {
    if (!data) {
        return "-";
    }
    return new Date(data).toLocaleDateString("pt-BR");
}


botaoNovaObservacao.addEventListener("click", function () {

    formNovaObservacao.reset();
    listaRegistros.innerHTML = "";

    modalNovaObservacao.style.display = "flex";

});


botaoFecharObservacao.addEventListener("click", function () {

    modalNovaObservacao.style.display = "none";

});


botaoAdicionarRegistro.addEventListener("click", function () {

    adicionarCampoRegistro();

});


function adicionarCampoRegistro() {

    const registro = document.createElement("div");

    registro.classList.add("registro-item");

    registro.innerHTML = `
        <div class="campo">
            <label>Tipo</label>
            <input type="text" class="registro-tipo"
                   placeholder="Ex.: Comportamento">
        </div>

        <div class="campo">
            <label>Descrição</label>
            <textarea class="registro-descricao"
                      rows="2"
                      placeholder="Descreva a informação"></textarea>
        </div>

        <button type="button" class="botao-remover-registro">
            Remover registro
        </button>
    `;

    listaRegistros.appendChild(registro);

}


listaRegistros.addEventListener("click", function (event) {

    if (event.target.classList.contains("botao-remover-registro")) {

        event.target.closest(".registro-item").remove();

    }

});


formNovaObservacao.addEventListener("submit", async function (event) {

    event.preventDefault();

    const camposRegistros = [
        ...listaRegistros.querySelectorAll(".registro-item")
    ];
    const registros = [];

    for (const campo of camposRegistros) {

        const tipo = campo.querySelector(".registro-tipo").value.trim();
        const descricao = campo.querySelector(".registro-descricao").value.trim();

        if ((tipo && !descricao) || (!tipo && descricao)) {
            alert("Preencha o tipo e a descrição de cada registro complementar.");
            return;
        }

        if (tipo && descricao) {
            registros.push({ tipo, descricao });
        }
    }

    const observacao = {
        especieId: selectEspecieObservacao.value,

        localizacao: {
            municipio: document.getElementById("observacaoMunicipio").value.trim(),
            estado: document.getElementById("observacaoEstado").value.trim().toUpperCase()
        },

        detalhes: {
            quantidadeObservada: Number(
                document.getElementById("observacaoQuantidade").value
            ),
            descricao: document.getElementById("observacaoDescricao").value.trim()
        },

        registros: registros
    };

    try {
        const resposta = await fetch("/observacoes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(observacao)
        });

        if (!resposta.ok) {
            throw new Error("Não foi possível cadastrar a observação.");
        }

        alert("Observação cadastrada com sucesso!");

        formNovaObservacao.reset();
        listaRegistros.innerHTML = "";

        modalNovaObservacao.style.display = "none";

        await carregarDadosObservacoes();

    } catch (erro) {
        console.error(erro);
        alert("Erro ao cadastrar a observação.");
    }
});

tabelaObservacoes.addEventListener("click", function (event) {

    const botao = event.target;

    if (botao.classList.contains("botao-ver-mais")) {
        const id = botao.dataset.id;
        abrirVerObservacao(id);
    }

    if (botao.classList.contains("botao-excluir-observacao")) {
        const id = botao.dataset.id;
        deletarObservacao(id);
    }
});

function abrirVerObservacao(id) {

    const observacao = observacoesDisponiveis.find(
        item => item.id === id
    );

    if (!observacao) {
        alert("Observação não encontrada.");
        return;
    }

    observacaoSelecionada = observacao;

    const especie = especiesDisponiveis.find(
        item => item.id === observacao.especieId
    );

    document.getElementById("verObservacaoEspecie").textContent =
        especie ? especie.nomePopular : "Espécie não encontrada";

    document.getElementById("verObservacaoData").textContent =
        formatarDataObservacao(observacao.dataObservacao);

    document.getElementById("verObservacaoLocalizacao").textContent =
        `${observacao.localizacao.municipio} - ${observacao.localizacao.estado}`;

    document.getElementById("verObservacaoQuantidade").textContent =
        observacao.detalhes.quantidadeObservada;

    document.getElementById("verObservacaoDescricao").textContent =
        observacao.detalhes.descricao;

    const lista = document.getElementById("verObservacaoRegistros");

    lista.innerHTML = "";

    if (!observacao.registros || observacao.registros.length === 0) {

        const item = document.createElement("li");
        item.textContent = "Nenhum registro complementar.";

        lista.appendChild(item);

    } else {
        observacao.registros.forEach(function (registro) {

            const item = document.createElement("li");

            item.textContent = `${registro.tipo}: ${registro.descricao}`;

            lista.appendChild(item);

        });
    }
    modalVerObservacao.style.display = "flex";
}

botaoFecharVerObservacao.addEventListener("click", function () {
    modalVerObservacao.style.display = "none";
    observacaoSelecionada = null;
});

async function deletarObservacao(id) {

    const confirmou = confirm(
        "Deseja realmente deletar esta observação?"
    );

    if (!confirmou) {
        return;
    }

    try {
        const resposta = await fetch(`/observacoes/${id}`, {
            method: "DELETE"
        });

        if (!resposta.ok) {
            throw new Error("Não foi possível deletar a observação.");
        }

        alert("Observação deletada com sucesso!");
        await carregarDadosObservacoes();

    } catch (erro) {
        console.error(erro);
        alert("Erro ao deletar a observação.");
    }
}

carregarDadosObservacoes();
