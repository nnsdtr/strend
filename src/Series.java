import java.util.Date;

public class Series {
   public int id;
   public String nome;
   public Date dataLancamento;
   public int numEpisodios;

   /**
    * Método construtor
    */
   public Series() {
      this.id = -1;
      this.nome = "unknown";
      this.dataLancamento = new Date();
      this.numEpisodios = -1;
   }
}
