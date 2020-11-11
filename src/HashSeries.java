import java.util.Arrays;
import java.util.Collections;

public class HashSeries {

   public static class ElemSerie {
      public Series meuDado;
      public boolean removido;

      public ElemSerie(Series dado) {
         this.meuDado = dado;
         this.removido = false;
      }
   }

   private final int tamanho;
   private final ElemSerie[] vetorSeries;
   private final Integer[] vetorPesos;

   public HashSeries() {
      this.tamanho = 1511;
      this.vetorSeries = new ElemSerie[tamanho];
      this.vetorPesos = vetorPesos(150);
   }

   private Integer[] vetorPesos(int numMaxChar) {
      Integer[] pesos = new Integer[numMaxChar];
      for(int i=0; i < numMaxChar; i++)
         pesos[i] = i+1;

      Collections.shuffle(Arrays.asList(pesos));

      return pesos;
   }

   public int funcaoHash(String chave) {
      int soma = 0;
      for (int i=0; i < chave.length(); i++) {
         int charNum = chave.charAt(i);
         soma += charNum * this.vetorPesos[i];
      }

      return soma % this.tamanho;
   }

   public void inserir(Series novaSerie) {
      int pos = funcaoHash(novaSerie.nome);

      while (this.vetorSeries[pos] != null && !this.vetorSeries[pos].removido)
         pos++;

      ElemSerie novo = new ElemSerie(novaSerie);
      this.vetorSeries[pos] = novo;
   }

   public Series localizar(String nome) {
      int pos = funcaoHash(nome);

      while (!this.vetorSeries[pos].meuDado.equals(nome)) {
         pos++;
         if (pos > this.tamanho || this.vetorSeries[pos] == null)
            return null;
      }
      return this.vetorSeries[pos].meuDado;
   }

   public Series remover(String nome) {
      int pos = funcaoHash(nome);

      while (!this.vetorSeries[pos].meuDado.equals(nome)) {
         pos++;
         if (pos > this.tamanho || this.vetorSeries[pos] == null)
            return null;
      }

      this.vetorSeries[pos].removido = true;
      return this.vetorSeries[pos].meuDado;
   }

   public void imprimir() {
      for (int i=0; i < this.vetorSeries.length; i++) {
         if (this.vetorSeries[i] != null)
            System.out.println(this.vetorSeries[i].meuDado.toString());
      }
   }
}
