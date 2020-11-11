public class EspecSeries {

   public static class Serie {
      public String nome;
      public int numEpAssistidos;
      public int numEpTotal;
      public boolean completou;

      public Serie(String nome, int numEpAss, int numTotalEp) {
         this.nome = nome;
         this.numEpAssistidos = numEpAss;
         this.numEpTotal = numTotalEp;
         this.completou = numEpAss == numTotalEp;
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

      public int numEpAssistidos() {
         return serie.numEpAssistidos;
      }
      public int numEpTotal() {
         return serie.numEpTotal;
      }
      public boolean completou() {
         return serie.numEpAssistidos == serie.numEpTotal;
      }
      private void incrementar() {
         if (numEpAssistidos() < numEpTotal()) {
            serie.numEpAssistidos++;
            if (completou()) serie.completou = true;
         }
      }
      private void decrementar() {
         if (numEpAssistidos() > 0) {
            serie.numEpAssistidos--;
            serie.completou = false;
         }
      }
   }

   public Elemento sentinela;
   public Elemento ultima;
   public int numSeriesCompletadas;

   public EspecSeries() {
      this.sentinela = new Elemento(null);
      this.ultima = sentinela;
   }

   public boolean vazia() {
      return ultima == sentinela;
   }

   public void inserir(String nome, int numEpAss, int numEpTotal) {
      Serie novaSerie = new Serie(nome, numEpAss, numEpTotal);
      Elemento novo = new Elemento(novaSerie);

      ultima.prox = novo;
      ultima = novo;
   }

   public Serie remover(String nome) {
      Elemento aux = sentinela;

      while (aux.prox != null) {
         if (aux.prox.equals(nome)){
            Elemento retirada = aux.prox;
            aux.prox = retirada.prox;

            if (retirada == ultima) ultima = aux;
            else retirada.prox = null;

            return retirada.serie;
         }
         else {
            aux = aux.prox;
         }
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

   public boolean incrementarEpisodio(String nome) {
      Elemento aux = sentinela.prox;

      while (aux != null) {
         if (aux.equals(nome)) {
            aux.incrementar();
            if(aux.completou()) numSeriesCompletadas++;
            return true;
         }
         else {
            aux = aux.prox;
         }
      }
      return false;
   }

   public boolean decrementarEpisodio(String nome) {
      Elemento aux = sentinela.prox;

      while (aux != null) {
         if (aux.equals(nome)) {
            if (aux.completou()) numSeriesCompletadas--;
            aux.decrementar();
            return true;
         }
         else
            aux = aux.prox;
      }
      return false;
   }

   public String[] seriesCompletas() {
      String[] series = new String[numSeriesCompletadas];

      Elemento aux = sentinela.prox;
      int index = 0;
      while (aux != null) {
         if (aux.completou()) {
            series[index] = aux.serie.nome;
            index++;
         }
         aux = aux.prox;
      }

      return series;
   }
}
