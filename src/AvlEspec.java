public class AvlEspec {
    public class Nodo {
        public int fatorBalanco, altura;
        public Espectador meuDado;
        public Nodo esquerda, direita;

        public Nodo(Espectador novo) {
            this.meuDado = novo;
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


    public boolean contem(String cpf) {
        return contem(raiz, cpf);
    }

    private boolean contem(Nodo nodo, String cpf) {
        if (nodo == null) return false;

        int cmp = nodo.meuDado.compareTo(cpf);
        if (cmp > 0) return contem(nodo.esquerda, cpf);
        if (cmp < 0) return contem(nodo.direita, cpf);

        return true;
    }


    public boolean inserir(Espectador novo) {
        if (novo == null) return false;
        if (!contem(raiz, novo.cpf)) {
            raiz = inserir(raiz, novo);
            numNodos++;
            return true;
        }
        return false;
    }

    private Nodo inserir(Nodo nodo, Espectador novo) {
        if (nodo == null) return new Nodo(novo);

        int cmp = novo.compareTo(nodo.meuDado);

        if (cmp < 0) {
            nodo.esquerda = inserir(nodo.esquerda, novo);

        } else {
            nodo.direita = inserir(nodo.direita, novo);
        }

        atualizar(nodo);
        return balancear(nodo);
    }


    public boolean remover(String cpf) {
        if (raiz == null) return false;

        if (contem(raiz, cpf)) {
            raiz = remover(raiz, cpf);
            numNodos--;
            return true;
        }
        return false;
    }

    private Nodo remover(Nodo nodo, String cpf) {
        if (nodo == null) return null;

        int cmp = nodo.meuDado.compareTo(cpf);
        if (cmp > 0) nodo.esquerda = remover(nodo.esquerda, cpf);
        else if (cmp < 0) nodo.direita = remover(nodo.direita, cpf);

        else {
            int grau = grau(nodo);
            switch (grau) {
                case 0:  return null;
                case -1: return nodo.esquerda;
                case 1:  return nodo.direita;
                case 2:
                    nodo.meuDado = antecessor(nodo);
                    nodo.esquerda = remover(nodo.esquerda, cpf);
                    return nodo;
            }
        }

        atualizar(nodo);
        return balancear(nodo);
    }

    private Espectador antecessor(Nodo nodo) {
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
