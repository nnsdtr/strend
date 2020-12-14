/**
 * AVL destinada ao armazenamento e organização dos Espectadores.
 */
public class AvlEspec {
    /**
     * Subclasse Nodo
     */
    public class Nodo {
        public int fatorBalanco, altura;
        public Espectador meuDado;
        public Nodo esquerda, direita;

        /**
         * Método construtor
         * @param novo Objeto Espectador a ser inserido no nodo.
         */
        public Nodo(Espectador novo) {
            this.meuDado = novo;
        }
    }

    public Nodo raiz;
    private int numNodos = 0;

    /**
     * Método get para altura da AVL
     * @return integer. Altura da árvore a partir da raiz principal.
     */
    public int altura() {
        if (raiz == null) return 0;
        return raiz.altura;
    }

    /**
     * Método get para número total de nodos na AVL.
     * @return integer. Número total de nodos inseridos na AVL.
     */
    public int tamanho() {
        return numNodos;
    }

    /**
     * Verifica se AVL está vazia.
     * @return boolean. Verdadeiro se árvore não possuir nodos.
     */
    public boolean vazia() {
        return tamanho() == 0;
    }

    /**
     * Cálculo do grau de um nodo.
     * @param nodo Objeto da classe Nodo.
     * @return Grau do nodo, retornando -1 se houver apenas um filho à esquerda.
     */
    public int grau(Nodo nodo) {
        if (nodo.esquerda != null)
            return (nodo.direita != null) ? 2 : -1;
        else
            return (nodo.direita != null) ? 1 : 0;
    }

    /**
     * Abstração do método contem privado.
     * @param cpf String. CPF do Espectador a ser pesquisado.
     * @return boolean. Verdadeiro se a árvore contiver o Espectador pesquisado.
     */
    public boolean contem(String cpf) {
        return contem(raiz, cpf);
    }

    /**
     * Realiza busca na AVL e verifica se o Espectador existe.
     * @param nodo Objeto da Subclasse Nodo. Para a busca ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param cpf String. CPF do Espectador buscado.
     * @return boolean. Verdadeiro se a árvore contiver o Espectador pesquisado.
     */
    private boolean contem(Nodo nodo, String cpf) {
        if (nodo == null) return false;

        int cmp = nodo.meuDado.compareTo(cpf);
        if (cmp > 0) return contem(nodo.esquerda, cpf);
        if (cmp < 0) return contem(nodo.direita, cpf);

        return true;
    }

    /**
     * Abstração do método localizar privado.
     * @param cpf String. CPF do Espectador buscado.
     * @return Objeto da Classe Espectador encontrado. Null se não for encontrado.
     */
    public Espectador localizar(String cpf) {
        return localizar(raiz, cpf);
    }

    /**
     *
     * @param nodo Objeto da Subclasse Nodo. Para a busca ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param cpf String. CPF do Espectador buscado.
     * @return Objeto da Classe Espectador encontrado. Null se não for encontrado.
     */
    private Espectador localizar(Nodo nodo, String cpf) {
        if (nodo == null) return null;

        int cmp = nodo.meuDado.compareTo(cpf);
        if (cmp > 0) return localizar(nodo.esquerda, cpf);
        if (cmp < 0) return localizar(nodo.direita, cpf);

        return nodo.meuDado;
    }

    /**
     * Abstração do método inserir privado. Verifica se o espectador já existe antes de inserí-lo.
     * @param novo Objeto da Classe Espectador a ser inserido na AVL.
     * @return boolean. Verdadeiro se a inserção obtiver sucesso.
     */
    public boolean inserir(Espectador novo) {
        if (novo == null) return false;
        if (!contem(raiz, novo.cpf)) {
            raiz = inserir(raiz, novo);
            numNodos++;
            return true;
        }
        return false;
    }

    /**
     * Método de inserção de Espectadores na AVL.
     * @param nodo Objeto da Subclasse Nodo. Para a inserção ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param novo Objeto da Classe Espectador a ser inserido na AVL.
     * @return Árvore adequadamente balanceada.
     */
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

    /**
     * Abstração do método remoção privado. Verifica se o espectador existe antes de removê-lo.
     * @param cpf String. CPF do Espectador a ser removido.
     * @return boolean. Verdadeiro se a remoção obtiver sucesso.
     */
    public boolean remover(String cpf) {
        if (raiz == null) return false;

        if (contem(raiz, cpf)) {
            raiz = remover(raiz, cpf);
            numNodos--;
            return true;
        }
        return false;
    }

    /**
     * Método de remoção de Espectadores na AVL.
     * @param nodo Objeto da Subclasse Nodo. Para a remoção ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param cpf String. CPF do Espectador a ser removido.
     * @return Árvore adequadamente balanceada.
     */
    private Nodo remover(Nodo nodo, String cpf) {
        if (nodo == null) return null;

        int cmp = nodo.meuDado.compareTo(cpf);
        if (cmp > 0) nodo.esquerda = remover(nodo.esquerda, cpf);
        else if (cmp < 0) nodo.direita = remover(nodo.direita, cpf);

        else {
            // Aquisição do grau do nodo a ser removido.
            int grau = grau(nodo);

            // Aplica procedimentos diferentes de remoção para cada caso
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

        // Atualização de altura e fator de balanceamento
        atualizar(nodo);

        // Aplicação do balanceamento
        return balancear(nodo);
    }

    /**
     * Busca o antecessor para remoção de nodos de Grau 2.
     * @param nodo Objeto da Subclasse Nodo. Nodo a ser removido.
     * @return Objeto da Classe Espectador contido no nodo antecessor.
     */
    private Espectador antecessor(Nodo nodo) {
        Nodo aux = nodo.esquerda;
        while (aux.direita != null)
            aux = aux.direita;

        return aux.meuDado;
    }

    /**
     * Método de atualização de altura e fator de balanço para inserções e remoções na AVL
     * @param nodo Objeto da Subclasse Nodo a ser atualizado.
     */
    private void atualizar(Nodo nodo) {
        int alturaEsq = (nodo.esquerda == null) ? -1 : nodo.esquerda.altura;
        int alturaDir = (nodo.direita == null) ? -1 : nodo.direita.altura;

        // Atualização da altura do nodo
        nodo.altura = 1 + Math.max(alturaEsq, alturaDir);

        // Atualização do fator de balanceamento do nodo
        nodo.fatorBalanco = alturaDir - alturaEsq;
    }


    /**
     * Realiza balanceamento da árvore a partir de um determinado Nodo.
     * @param nodo Objeto da Subclasse Nodo cuja subárvore deve ser balanceada.
     * @return Nodo balanceado.
     */
    private Nodo balancear(Nodo nodo) {
        // Árvore desbalanceada para a esquerda
        if (nodo.fatorBalanco == -2) {

            // Caso Esquerda-Esquerda
            if (nodo.esquerda.fatorBalanco <= 0)
                return casoEsqEsq(nodo);

            // Caso Esquerda-Direita
            else return casoEsqDir(nodo);


        // Árvore desbalanceada para a direita
        } else if (nodo.fatorBalanco == +2) {

            // Caso Direita-Direita
            if (nodo.direita.fatorBalanco >= 0)
                return casoDirDir(nodo);

            // Caso Direita-Esquerda
            else return casoDirEsq(nodo);
        }

        // Retorna nodo se não houver desbalanceamento.
        return nodo;
    }

    /**
     * Método de Rotação para a Esquerda
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai.
     */
    private Nodo rotacaoEsq(Nodo nodo) {
        Nodo novoPai = nodo.direita;
        nodo.direita = novoPai.esquerda;
        novoPai.esquerda = nodo;

        atualizar(nodo);
        atualizar(novoPai);

        return novoPai;
    }

    /**
     * Método de Rotação para a Direita
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai.
     */
    private Nodo rotacaoDir(Nodo nodo) {
        Nodo novoPai = nodo.esquerda;
        nodo.esquerda = novoPai.direita;
        novoPai.direita = nodo;

        atualizar(nodo);
        atualizar(novoPai);

        return novoPai;
    }

    /**
     * Rotação para casos Esquerda-Esquerda
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai é retornado para a recursividade.
     */
    private Nodo casoEsqEsq(Nodo nodo) {
        return rotacaoDir(nodo);
    }

    /**
     * Rotação para casos Esquerda-Direita
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai é retornado para a recursividade.
     */
    private Nodo casoEsqDir(Nodo nodo) {
        nodo.esquerda = rotacaoEsq(nodo.esquerda);
        return casoEsqEsq(nodo);
    }

    /**
     * Rotação para casos Direita-Direita
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai é retornado para a recursividade.
     */
    private Nodo casoDirDir(Nodo nodo) {
        return rotacaoEsq(nodo);
    }

    /**
     * Rotação para casos Direita-Esquerda
     * @param nodo Objeto da Subclasse Nodo a partir do qual ocorrerá a rotação.
     * @return Novo elemento pai é retornado para a recursividade.
     */
    private Nodo casoDirEsq(Nodo nodo) {
        nodo.direita = rotacaoDir(nodo.direita);
        return casoDirDir(nodo);
    }

    /**
     * Abstração do método preOrdem de impressão da AVL
     * @return String. Espectadores ordenados segundo pré-ordem.
     */
    public String preOrdem() {
        return preOrdem(this.raiz);
    }

    /**
     * Método de impressão da AVL em pré-ordenação
     * @param nodo Objeto da Subclasse Nodo. Para a impressão ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @return String. Espectadores ordenados segundo pré-ordem.
     */
    private String preOrdem(Nodo nodo) {
        String aux = "";

        aux += nodo.meuDado;
        if (nodo.esquerda != null) aux += preOrdem(nodo.esquerda);
        if (nodo.direita != null) aux += preOrdem(nodo.direita);

        return aux;
    }
}
