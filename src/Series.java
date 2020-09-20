import java.util.Date;

public class Series {
   public int id;
   public String name;
   public Date releaseDate;
   public int totalNumberEpisodes;

   /**
    * Método construtor
    */
   public Series() {
      this.id = -1;
      this.name = "unknown";
      this.releaseDate = new Date();
      this.totalNumberEpisodes = -1;
   }
}
