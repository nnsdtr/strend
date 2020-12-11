import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * AVL destinada ao armazenamento e organização das Séries por data de lançamento.
 */
public class AvlSeriesLancadas {

    /**
     * Elementos da lista de Series com mesma data de lançamento
     */
    public class ElementoListaNodo{
        public String nomeSerieLancada;
        public ElementoListaNodo proximo;

        /**
         * Método construtor
         * @param nome String. Título da Série.
         */
        public ElementoListaNodo (String nome){
            nomeSerieLancada = nome;
            proximo = null;
        }

        /**
         * Método de comparação de igualdade entre Séries.
         * @param obj String. Título da Série a ser comparada.
         * @return boolean. Verdadeiro se forem iguais.
         */
        public boolean equals(Object obj){
            String comparacao = (String)obj;
            return nomeSerieLancada.equals(comparacao);
        }

    }

    /**
     * Lista de Series com mesma data de lançamento
     */
    public class ListaNodo{
        public ElementoListaNodo primeiro;
        public ElementoListaNodo ultimo;

        /**
         * Método construtor
         */
        public ListaNodo(){
            primeiro = new ElementoListaNodo(null);
            ultimo = primeiro;
        }

        /**
         * Método de inserção de nova Série na lista.
         * @param novaSerie String. Título da série a ser adicionada.
         */
        public void inserirListaNodo(String novaSerie){
            ElementoListaNodo novo = new ElementoListaNodo(novaSerie);
            ultimo.proximo = novo;
            ultimo = novo;
        }

        /**
         * Método de verificação de lista vazia.
         * @return boolean. Verdadeiro se vazia.
         */
        public boolean vaziaListaNodo(){
            return (ultimo==primeiro);
        }

        /**
         * Método de remoção da lista.
         * @param serieRetirar String. Título da série a ser removida.
         * @return Objeto da Classe Serie removido. Null se não for encontrado.
         */
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

        /**
         * Método de impressão para as Séries lançadas em uma mesma data.
         */
        public void imprimir( ){
            if (this.vaziaListaNodo()){
                System.out.println("Não existem séries lançadas nessa data.");
            }
            else{
                ElementoListaNodo aux = this.primeiro.proximo;
                while (aux != null){
                    System.out.println(aux.nomeSerieLancada);
                    aux = aux.proximo;
                }
            }
        }
    }

    /**
     * Nodo da AVL. Contém as listas de séries lançadas em uma mesma data.
     */
    public class Nodo {
        public int fatorBalanco, altura;
        public ListaNodo meuDado;
        public LocalDate dataNodo;
        public Nodo esquerda, direita;

        /**
         * Método construtor
         * @param dataLancamento LocalDate. Data de lançamento.
         */
        public Nodo(LocalDate dataLancamento) {
            this.meuDado = new ListaNodo();
            this.dataNodo = dataLancamento;
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
     * @param contemData Objeto Serie a ser pesquisado.
     * @return boolean. Verdadeiro se a árvore contiver a Série.
     */
    public boolean contem(Series contemData) {
        return contem(raiz, contemData);
    }

    /**
     * Realiza busca na AVL e verifica se a Série existe.
     * @param nodo Objeto da Subclasse Nodo. Para a busca ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param contemData Objeto Serie a ser pesquisado.
     * @return boolean. Verdadeiro se a árvore contiver a Série.
     */
    private boolean contem(Nodo nodo, Series contemData) {
        if (nodo == null) return false;

        int cmp = nodo.dataNodo.compareTo(contemData.dataDeLancamento);
        if (cmp > 0) return contem(nodo.esquerda, contemData);
        if (cmp < 0) return contem(nodo.direita, contemData);

        return true;
    }

    /**
     * Abstração do método privado de inserção na AVL
     * @param novo Objeto Serie a ser inserido.
     * @return boolean. Verdadeiro se a inserção ocorrer com sucesso.
     */
    public boolean inserir(Series novo) {
        if (novo == null) return false;

        raiz = inserir(raiz, novo);
        numNodos++;
        return true;
    }

    /**
     * Método de inserção da Série na AVL
     * @param nodo Objeto da Subclasse Nodo. Para a inserção ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param novaSerie Objeto Serie a ser inserido.
     * @return Árvore adequadamente balanceada.
     */
    private Nodo inserir(Nodo nodo, Series novaSerie) {
        if (nodo == null){
            Nodo novoNodo = new Nodo(novaSerie.dataDeLancamento);

            // Inserção no nodo passa pela inserção na lista
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

    /**
     * Abstração do método privado de remoção na AVL.
     * @param removerSerie Objeto Serie a ser removido.
     * @return boolean. Verdadeiro se a Série for removida com sucesso.
     */
    public boolean remover(Series removerSerie) {
        if (raiz == null) return false;

        if (contem(raiz, removerSerie)) {
            raiz = remover(raiz, removerSerie);
            numNodos--;
            return true;
        }
        return false;
    }


    /**
     * Método de remoção na AVL
     * @param nodo Objeto da Subclasse Nodo. Para a remoção ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param removerSerie Objeto Serie a ser removido.
     * @return Árvore adequadamente balanceada.
     */
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
            // Aquisição do grau do nodo a ser removido.
            int grau = grau(nodo);

            // Aplica procedimentos diferentes de remoção para cada caso
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

        // Atualização de altura e fator de balanceamento
        atualizar(nodo);

        // Aplicação do balanceamento
        return balancear(nodo);
    }

    /**
     * Busca o antecessor para remoção de nodos de Grau 2.
     * @param nodo Objeto da Subclasse Nodo. Nodo a ser removido.
     * @return Lista de Series lançadas na data antecessora.
     */
    private ListaNodo antecessor(Nodo nodo) {
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

        // Atualização a altura do nodo
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
            if (nodo.esquerda.fatorBalanco <= 0) {
                return casoEsqEsq(nodo);

            // Caso Esquerda-Direita
            } else {
                return casoEsqDir(nodo);
            }

        // Árvore desbalanceada para a direita
        } else if (nodo.fatorBalanco == +2) {

            // Caso Direita-Direita
            if (nodo.direita.fatorBalanco >= 0) {
                return casoDirDir(nodo);

            // Caso Direita-Esquerda
            } else {
                return casoDirEsq(nodo);
            }
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
     * Formatador para datas em String.
     */
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

    /**
     * Método de localização de Séries Lançadas em uma Mesma Data
     * @param nodo Objeto da Subclasse Nodo. Para a remoção ser realizada adequadamente, deve ser a raiz principal da AVL.
     * @param dataPesquisada String. Data de Lançamento buscada, no formato 'dd/mm/yyyy'.
     * @return Lista de Séries com mesma data de lançamento.
     */
    public Nodo localizar(Nodo nodo, String dataPesquisada) {
        if (nodo == null) return null;

        int cmp = nodo.dataNodo.compareTo( LocalDate.parse(dataPesquisada, formatador) );
        if (cmp > 0) return localizar(nodo.esquerda, dataPesquisada);
        if (cmp < 0) return localizar(nodo.direita, dataPesquisada);

        return nodo;
    }
}
