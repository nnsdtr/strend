public class EspecAvalLista {

   public static class Serie {
      public String nome;
      public int nota;

      public Serie(String nome, int nota) {
         this.nome = nome;
         this.nota = nota;
      }

      public boolean equals(Object obj) {
         String comp = (String) obj;
         return this.nome.equals(comp);
      }
   }

   public static class Elemento {
      public Serie serie;
      public Elemento prox;

      public Elemento(Serie qual) {
         this.serie = qual;
         this.prox = null;
      }
   }

   public Elemento sentinela;
   public Elemento ultima;

   public EspecAvalLista() {
      this.sentinela = new Elemento(null);
      this.ultima = sentinela;
   }

   public boolean vazia() {
      return ultima == sentinela;
   }

   public void inserir(String nome, int nota) {
      Elemento novo = new Elemento( new Serie(nome, nota) );

      ultima.prox = novo;
      ultima = novo;
   }

   public Serie remover(String nome) {
      Elemento aux = sentinela;

      while (aux.prox != null) {
         if (aux.prox.equals(nome)) {
            Elemento retirada = aux.prox;
            aux.prox = retirada.prox;

            if (retirada == ultima) ultima = aux;
            else retirada.prox = null;

            return retirada.serie;
         }
         else aux = aux.prox;
      }
      return null;
   }

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
