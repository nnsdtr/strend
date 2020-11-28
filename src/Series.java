import java.time.LocalDate;

public class Series {
   public String nome;
   public LocalDate dataDeLancamento;
   public int numeroTotalEpisodios;
   public float somaNotasValidas;
   public int qtdNotasValidas;

   /**
    * Método construtor
    */
   public Series() {
      this.nome = "unknown";
      this.dataDeLancamento = LocalDate.MIN;
      this.numeroTotalEpisodios = -1;
      this.somaNotasValidas = 0;
      this.qtdNotasValidas = 0;
   }

   public int compareTo(LocalDate outraData){
      return this.dataDeLancamento.compareTo(outraData);
   }

   public int compareTo(Series outraSerie){
      return this.dataDeLancamento.compareTo(outraSerie.dataDeLancamento);
   }

   public boolean equals(String comp) {
      return this.nome.equals(comp);
   }

   public String toString(){
      return "Nome: " + nome + " - Data de Lançamento: " + dataDeLancamento + " - Nº de episódios: " + numeroTotalEpisodios;
   }
}
