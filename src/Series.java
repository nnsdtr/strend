import java.util.Date;

public class Series {
   public String nome;
   public Date dataDeLancamento;
   public int numeroTotalEpisodios;

   /**
    * Método construtor
    */
   public Series() {
      this.nome = "unknown";
      this.dataDeLancamento = new Date();
      this.numeroTotalEpisodios = -1;
   }


   public int compareTo(Date outraData){
      return this.dataDeLancamento.compareTo(outraData);
   }

   public int compareTo(Series outraSerie){
      return this.dataDeLancamento.compareTo(outraSerie.dataDeLancamento);
   }

}
