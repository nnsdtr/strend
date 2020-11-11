import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;


public class App {

   public static AvlEspec carregarEspectadores(String caminho) throws FileNotFoundException {
      Scanner leitorArq = new Scanner(new File(caminho));
      AvlEspec arvore = new AvlEspec();

      while(leitorArq.hasNextLine()) {
         String[] aux = leitorArq.nextLine().split(";");

         Espectador novo = new Espectador();
         novo.cpf = aux[0];
         novo.nome = aux[1];
         novo.login = aux[2];

         arvore.inserir(novo);
      }
      leitorArq.close();
      return arvore;
   }

   public static AvlSeriesLancadas carregarSeriesLancadas (String caminho) throws FileNotFoundException {
      Scanner leitorArq = new Scanner(new File(caminho));
      AvlSeriesLancadas seriesLancadas = new AvlSeriesLancadas();
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

      while (leitorArq.hasNextLine()){
         String [] aux = leitorArq.nextLine().split(";");

         Series nova = new Series();
         nova.nome = aux[0];
         nova.dataDeLancamento = LocalDate.parse(aux[1], formatador);
         nova.numeroTotalEpisodios = Integer.parseInt(aux[2]);

         seriesLancadas.inserir(nova);
      }
      leitorArq.close();
      return seriesLancadas;
   }

   public static void main(String[] args) throws FileNotFoundException {
      // Teste da AVL de Espectadores
      AvlEspec espectadores = carregarEspectadores("strend/fake_data.txt");

      // Teste da AVL de Series
      AvlSeriesLancadas seriesLancadas = carregarSeriesLancadas("strend/series_FakeData.txt");
   }
}