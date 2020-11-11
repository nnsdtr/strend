import java.time.LocalDate;

public class AvlSeriesLancadas {

    public class ElementoListaNodo{
        public String nomeSerieLancada;
        public ElementoListaNodo proximo;

        public ElementoListaNodo (String nome){
            nomeSerieLancada = nome;
            proximo = null;
        }

        public boolean equals(Object obj){
            String comparacao = (String)obj;
            return nomeSerieLancada.equals(comparacao);
        }

    }

    public class ListaNodo{
        public ElementoListaNodo primeiro;
        public ElementoListaNodo ultimo;

        public ListaNodo(){
            primeiro = new ElementoListaNodo(null);
            ultimo = primeiro;
        }

        public void inserirListaNodo(String novaSerie){
            ElementoListaNodo novo = new ElementoListaNodo(novaSerie);
            ultimo.proximo = novo;
            ultimo = novo;
        }

        public boolean vaziaListaNodo(){
            return (ultimo==primeiro);
        }

        public String retirarListaNodo (String serieRetirar){

            ElementoListaNodo aux = primeiro;
            while (aux.proximo!=null){
                if ( aux.proximo.equals(serieRetirar) ){
                    ElementoListaNodo retirada = aux.proximo;
                    aux.proximo = retirada.proximo;

                    if (retirada==ultimo){
                        ultimo=aux;
                    }
                    else {
                        retirada.proximo = null;
                        return retirada.nomeSerieLancada;
                    }
                }
                else {
                    aux = aux.proximo;
                }
            }
            return null;
        }
    }

    public class Nodo {
        public int fatorBalanco, altura;
        public ListaNodo meuDado;
        public LocalDate dataNodo;
        public Nodo esquerda, direita;

        public Nodo(LocalDate dataLancamento) {
            this.meuDado = new ListaNodo();
            this.dataNodo = dataLancamento;
        }
    }

    public Nodo raiz;
    private int numNodos = 0;

    public int altura() {
        if (raiz == null) return 0;
        return raiz.altura;
    }

    public int tamanho() {
        return numNodos;
    }

    public boolean vazia() {
        return tamanho() == 0;
    }

    public int grau(Nodo nodo) {
        if (nodo.esquerda != null)
            return (nodo.direita != null) ? 2 : -1;
        else
            return (nodo.direita != null) ? 1 : 0;
    }


    public boolean contem(Series contemData) {
        return contem(raiz, contemData);
    }

    private boolean contem(Nodo nodo, Series contemData) {
        if (nodo == null) return false;

        int cmp = nodo.dataNodo.compareTo(contemData.dataDeLancamento);
        if (cmp > 0) return contem(nodo.esquerda, contemData);
        if (cmp < 0) return contem(nodo.direita, contemData);

        return true;
    }


    public boolean inserir(Series novo) {
        if (novo == null) return false;

        raiz = inserir(raiz, novo);
        numNodos++;
        return true;
    }

    private Nodo inserir(Nodo nodo, Series novaSerie) {
        if (nodo == null){
            Nodo novoNodo = new Nodo(novaSerie.dataDeLancamento);
            novoNodo.meuDado.inserirListaNodo(novaSerie.nome);
            return novoNodo;
        }

        int cmp = novaSerie.dataDeLancamento.compareTo(nodo.dataNodo);

        if (cmp < 0) {
            nodo.esquerda = inserir(nodo.esquerda, novaSerie);

        }
        else if (cmp > 0){
            nodo.direita = inserir(nodo.direita, novaSerie);
        }
        else {
            nodo.meuDado.inserirListaNodo(novaSerie.nome);
            return nodo;
        }

        atualizar(nodo);
        return balancear(nodo);
    }


    public boolean remover(Series removerSerie) {
        if (raiz == null) return false;

        if (contem(raiz, removerSerie)) {
            raiz = remover(raiz, removerSerie);
            numNodos--;
            return true;
        }
        return false;
    }

    private Nodo remover(Nodo nodo, Series removerSerie) {
        if (nodo == null) return null;

        int cmp = nodo.dataNodo.compareTo(removerSerie.dataDeLancamento);
        if (cmp > 0){
            nodo.esquerda.meuDado.retirarListaNodo(removerSerie.nome);
        }
        else if (cmp < 0) {
            nodo.direita.meuDado.retirarListaNodo(removerSerie.nome);
        }

        else {
            int grau = grau(nodo);
            switch (grau) {
                case 0:  return null;
                case -1: return nodo.esquerda;
                case 1:  return nodo.direita;
                case 2:
                    nodo.meuDado = antecessor(nodo);
                    nodo.esquerda = remover(nodo.esquerda, removerSerie);
                    return nodo;
            }
        }

        atualizar(nodo);
        return balancear(nodo);
    }

    private ListaNodo antecessor(Nodo nodo) {
        Nodo aux = nodo.esquerda;
        while (aux.direita != null)
            aux = aux.direita;

        return aux.meuDado;
    }


    private void atualizar(Nodo nodo) {
        int alturaEsq = (nodo.esquerda == null) ? -1 : nodo.esquerda.altura;
        int alturaDir = (nodo.direita == null) ? -1 : nodo.direita.altura;

        // Update this node's height.
        nodo.altura = 1 + Math.max(alturaEsq, alturaDir);

        // Update balance factor.
        nodo.fatorBalanco = alturaDir - alturaEsq;
    }

    private Nodo balancear(Nodo nodo) {
        // Left heavy subtree.
        if (nodo.fatorBalanco == -2) {

            // Left-Left case.
            if (nodo.esquerda.fatorBalanco <= 0) {
                return casoEsqEsq(nodo);

                // Left-Right case.
            } else {
                return casoEsqDir(nodo);
            }

            // Right heavy subtree needs balancing.
        } else if (nodo.fatorBalanco == +2) {

            // Right-Right case.
            if (nodo.direita.fatorBalanco >= 0) {
                return casoDirDir(nodo);

                // Right-Left case.
            } else {
                return casoDirEsq(nodo);
            }
        }

        // Node either has a balance factor of 0, +1 or -1 which is fine.
        return nodo;
    }

    private Nodo rotacaoEsq(Nodo nodo) {
        Nodo novoPai = nodo.direita;
        nodo.direita = novoPai.esquerda;
        novoPai.esquerda = nodo;

        atualizar(nodo);
        atualizar(novoPai);

        return novoPai;
    }

    private Nodo rotacaoDir(Nodo nodo) {
        Nodo novoPai = nodo.esquerda;
        nodo.esquerda = novoPai.direita;
        novoPai.direita = nodo;

        atualizar(nodo);
        atualizar(novoPai);

        return novoPai;
    }

    private Nodo casoEsqEsq(Nodo nodo) {
        return rotacaoDir(nodo);
    }

    private Nodo casoEsqDir(Nodo nodo) {
        nodo.esquerda = rotacaoEsq(nodo.esquerda);
        return casoEsqEsq(nodo);
    }

    private Nodo casoDirDir(Nodo nodo) {
        return rotacaoEsq(nodo);
    }

    private Nodo casoDirEsq(Nodo nodo) {
        nodo.direita = rotacaoDir(nodo.direita);
        return casoDirDir(nodo);
    }


    public String preOrdem() {
        return preOrdem(this.raiz);
    }

    private String preOrdem(Nodo nodo) {
        String aux = "";

        aux += nodo.meuDado;
        if (nodo.esquerda != null) aux += preOrdem(nodo.esquerda);
        if (nodo.direita != null) aux += preOrdem(nodo.direita);

        return aux;
    }
}
