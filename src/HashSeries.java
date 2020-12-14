import java.util.Arrays;
import java.util.Collections;

/**
 * Tabela Hash destinada ao armazenamento das informações de
 * Séries cadastradas na plataforma Strend.
 */
public class HashSeries {

   /**
    * Elementos contidos no vetor da Hash
    */
   public static class ElemSerie {
      public Series meuDado;
      public boolean removido;

      /**
       * Método construtor
       * @param dado Objeto de Classe Serie.
       */
      public ElemSerie(Series dado) {
         this.meuDado = dado;
         this.removido = false;
      }
   }

   // Atributos da Classe HashSeries
   private final int capacidade;
   private final ElemSerie[] vetorSeries;
   private final Integer[] vetorPesos;

   /**
    * Método construtor
    */
   public HashSeries() {
      this.capacidade = 1511;
      this.vetorSeries = new ElemSerie[capacidade];
      this.vetorPesos = vetorPesos(150);
   }

   /**
    * Contrutor do vetor de pesos para uso na funcaoHash
    * @param numMaxChar integer. Tamanho do vetor de pesos. Deverá refletir o número
    *                   máximo de caracteres no título de uma série.
    * @return Vetor de pesos.
    */
   private Integer[] vetorPesos(int numMaxChar) {
      Integer[] pesos = new Integer[numMaxChar];

      // Preenchimento do vetor de pesos
      for(int i=0; i < numMaxChar; i++)
         pesos[i] = i+1;

      // Desordenação aleatória dos pesos no vetor
      Collections.shuffle(Arrays.asList(pesos));

      return pesos;
   }

   /**
    * Função Hash para cálculo do índice de inserção na tabela
    * @param chave String. Título da série.
    * @return integer. Índice para inserção na Tabela Hash.
    */
   public int funcaoHash(String chave) {
      int soma = 0;

      for (int i=0; i < chave.length(); i++) {
         int charNum = chave.charAt(i);
         soma += charNum * this.vetorPesos[i];
      }
      return soma % this.capacidade;
   }

   /**
    * Método de inserção na Tabela Hash.
    * @param novaSerie Objeto da Classe Serie a ser inserido.
    */
   public void inserir(Series novaSerie) {
      int pos = funcaoHash(novaSerie.nome);

      // Tratamento de colisão: endereçamento aberto por sondagem linear
      while (this.vetorSeries[pos] != null && !this.vetorSeries[pos].removido)
         pos = (pos + 1) % this.capacidade;

      this.vetorSeries[pos] = new ElemSerie(novaSerie);
   }

   /**
    * Método de remoção na Tabela Hash.
    * @param nome String. Título da série a ser removida.
    * @return Objeto da Classe Serie, o qual foi removido. Null se não for encontrado.
    */
   public Series remover(String nome) {
      int pos = funcaoHash(nome);

      while (!this.vetorSeries[pos].meuDado.equals(nome)) {
         pos = (pos + 1) % this.capacidade;
         if (this.vetorSeries[pos] == null) return null;
      }

      this.vetorSeries[pos].removido = true;
      return this.vetorSeries[pos].meuDado;
   }

   /**
    * Método de localização da Série na Tabela Hash.
    * @param nome String. Título da Série a ser procurada. Deve ser exato
    * @return Objeto da Classe Serie. Null se não for encontrado.
    */
   public Series localizar(String nome) {
      int pos = funcaoHash(nome);

      while (!this.vetorSeries[pos].meuDado.equals(nome)) {
         pos = (pos + 1) % this.capacidade;
         if (this.vetorSeries[pos] == null) return null;
      }
      return this.vetorSeries[pos].meuDado;
   }

   /**
    * Método de impressão de todas as Séries contidas na Tabela Hash.
    */
   public void imprimir() {
      for (ElemSerie elem : this.vetorSeries) {
         if (elem != null && !elem.removido)
            System.out.println(elem.meuDado.toString());
      }
   }
}
