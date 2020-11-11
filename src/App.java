
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


public class App {

//   public static AvlEspec carregar(String caminho) throws FileNotFoundException {
//      Scanner leitorArq = new Scanner(new File(caminho));
//      AvlEspec arvore = new AvlEspec();
//
//      while(leitorArq.hasNextLine()) {
//         String[] aux = leitorArq.nextLine().split(";");
//
//         Espectador novo = new Espectador();
//         novo.cpf = aux[0];
//         novo.nome = aux[1];
//         novo.login = aux[2];
//
//         arvore.inserir(novo);
//      }
//
//      leitorArq.close();
//
//      return arvore;
//   }

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
      return seriesLancadas;
   }

   static Random sorteio = new Random(46);

   public static void main(String[] args) throws FileNotFoundException {
      // Teste da AVL de Espectadores
      AvlEspec arvEspectadores = new AvlEspec();

      for (int i=0; i<10; i++){
         int num = sorteio.nextInt(100);
         System.out.println(num);

         Espectador novo = new Espectador();

         novo.cpf = Integer.toString(num);
         novo.nome = "Espectador " + (i);
         novo.login = "Login " + (i);

         arvEspectadores.inserir(novo);
      }

      String arvoreToda = arvEspectadores.preOrdem();
      System.out.println(arvoreToda);

      // Teste da AVL de Series
      AvlSeriesLancadas testeAVL = carregarSeriesLancadas("strend/series_FakeData.txt");
   }
}