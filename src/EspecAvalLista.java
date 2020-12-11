/**
 * Lista de séries avaliadas pelo espectador.
 */
public class EspecAvalLista {

   /**
    * Subclasse Serie contendo informações básicas
    */
   public static class Serie {
      public String nome;
      public int nota;

      /**
       * Método construtor
       * @param nome String. Título da Série.
       * @param nota integer. Nota atribuída à Série.
       */
      public Serie(String nome, int nota) {
         this.nome = nome;
         this.nota = nota;
      }

      /**
       * Método de comparação de igualdade entre Séries.
       * @param obj String. Título da série a ser comparada. Deve-se notar que o título está
       *            sendo tratado como a chave primária de uma Série.
       * @return boolean. Verdadeiro se forem iguais.
       */
      public boolean equals(Object obj) {
         String comp = (String) obj;
         return this.nome.equals(comp);
      }

      /**
       * Método para impressão das séries avaliadas.
       * @return String. Série avaliada com suas informações básicas (nota e título).
       */
      public String toString() {
         return "[Nota: " + this.nota + "] " + this.nome + "\n";
      }
   }

   /**
    * Subclasse Elemento. São os elementos da lista de avaliações.
    */
   public static class Elemento {
      public Serie serie;
      public Elemento prox;

      /**
       * Método construtor
       * @param qual Objeto da Subclasse Serie.
       */
      public Elemento(Serie qual) {
         this.serie = qual;
         this.prox = null;
      }
   }

   public Elemento sentinela;
   public Elemento ultima;
   public int numElemementos;

   /**
    * Método construtor
    */
   public EspecAvalLista() {
      this.sentinela = new Elemento(null);
      this.ultima = sentinela;
      this.numElemementos = 0;
   }

   /**
    * Verifica se lista está vazia
    * @return boolean. Verdadeiro se estiver vazia.
    */
   public boolean vazia() {
      return ultima == sentinela;
   }

   /**
    * Método get para obter o número de elementos contidos na lista.
    * @return integer. Número de elementos ou tamanho da lista.
    */
   public int getTamanho() {
      return this.numElemementos;
   }

   /**
    * Método de inserção de avaliações na lista.
    * @param nome String. Título da Série.
    * @param nota integer. Nota atribuída à Série.
    */
   public void inserir(String nome, int nota) {
      Elemento novo = new Elemento( new Serie(nome, nota) );

      ultima.prox = novo;
      ultima = novo;
      numElemementos++;
   }

   /**
    * Método de remoção de avaliação.
    * @param nome String. Título da Série a ter sua avaliação removida.
    * @return Objeto da Subclasse Serie removido. Null se não existir na lista
    */
   public Serie remover(String nome) {
      Elemento aux = sentinela;

      while (aux.prox != null) {
         if (aux.prox.equals(nome)) {
            Elemento retirada = aux.prox;
            aux.prox = retirada.prox;

            if (retirada == ultima) ultima = aux;
            else retirada.prox = null;

            numElemementos--;

            return retirada.serie;
         }
         else aux = aux.prox;
      }
      return null;
   }

   /**
    * Método de localização de uma série avaliada.
    * @param nome String. Título a série a ser buscada.
    * @return Objeto da Subclasse Serie localizado. Null se não tiver sido localizado.
    */
   public Serie localizar(String nome) {
      Elemento aux = sentinela.prox;

      while(aux != null) {
         if(aux.equals(nome))
            return aux.serie;
         else
            aux = aux.prox;
      }

      return null;
   }
}
