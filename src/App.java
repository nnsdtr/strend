import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;


public class App {
   private static HashSeries seriesHash = new HashSeries();

   private static AvlSeriesLancadas seriesLancadas = new AvlSeriesLancadas();

   private static DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

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
      Scanner leitorArqAVL = new Scanner(new File(caminho));

      while (leitorArqAVL.hasNextLine()){
         String [] aux = leitorArqAVL.nextLine().split(";");

         Series nova = new Series();
         nova.nome = aux[0];
         nova.dataDeLancamento = LocalDate.parse(aux[1], formatador);
         nova.numeroTotalEpisodios = Integer.parseInt(aux[2]);

         seriesLancadas.inserir(nova);
      }
      leitorArqAVL.close();
      return seriesLancadas;
   }

   public static HashSeries carregarSeries(String caminho) throws FileNotFoundException {
      Scanner sc = new Scanner(new File(caminho));
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
      while(sc.hasNextLine()) {
         String[] arr = sc.nextLine().split(";");
         Series nova = new Series();

         nova.nome = arr[0];
         nova.dataDeLancamento = LocalDate.parse(arr[1], formatador);
         nova.numeroTotalEpisodios = Integer.parseInt(arr[2]);

         seriesHash.inserir(nova);
      }

      sc.close();
      return seriesHash;
   }

   public static void buscaData(String dataPesquisada){
      AvlSeriesLancadas.Nodo listaLocalizada = seriesLancadas.localizar(seriesLancadas.raiz, dataPesquisada);
      System.out.println("Busca realizada na data " + dataPesquisada + ": ");
      if (listaLocalizada == null){
         System.out.println("Não existem séries lançadas nessa data.\n");
      }
      else {
         listaLocalizada.meuDado.imprimir();
      }
   }

   public static void main(String[] args) throws FileNotFoundException {
      // Teste da AVL de Espectadores
      AvlEspec espectadores = carregarEspectadores("strend/fake_data.txt");

      Espectador localizado = espectadores.localizar("002.072.557-41");
      System.out.println(localizado.toString());

      // Teste da AVL de Series
      AvlSeriesLancadas seriesLancadas = carregarSeriesLancadas("strend/series_FakeData.txt");

      // Teste da Hash de Series
      HashSeries seriesHash = carregarSeries("strend/series_Fakedata.txt");

      // Teste da localização por nome
      Series localizada = seriesHash.localizar("Breaking Bad");
      System.out.println(localizada.toString());

      //Teste da localização por Data
      buscaData("17/07/1997"); // Não existe
      buscaData("22/09/1994"); // Friends + Copia

   }
}