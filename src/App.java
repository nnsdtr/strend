import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;


public class App {
   private static HashSeries seriesHash = new HashSeries();

   private static AvlSeriesLancadas seriesLancadas = new AvlSeriesLancadas();

   private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

   public static AvlEspec carregarEspectadores(String caminho) throws FileNotFoundException {
      Scanner leitorArq = new Scanner(new File(caminho));
      AvlEspec arvore = new AvlEspec();

      while (leitorArq.hasNextLine()) {
         String[] aux = leitorArq.nextLine().split(";");

         Espectador novo = new Espectador();
         novo.cpf = aux[0];
         novo.nome = aux[1];
         novo.login = aux[2];
         novo.senha = aux[3];

         arvore.inserir(novo);
      }
      leitorArq.close();
      return arvore;
   }

   public static AvlSeriesLancadas carregarSeriesLancadas (String caminho) throws FileNotFoundException {
      Scanner leitorArqAVL = new Scanner(new File(caminho));

      while (leitorArqAVL.hasNextLine()) {
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
      Scanner leitorArqHash = new Scanner(new File(caminho));
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
      while (leitorArqHash.hasNextLine()) {
         String[] arr = leitorArqHash.nextLine().split(";");
         Series nova = new Series();

         nova.nome = arr[0];
         nova.dataDeLancamento = LocalDate.parse(arr[1], formatador);
         nova.numeroTotalEpisodios = Integer.parseInt(arr[2]);

         seriesHash.inserir(nova);
      }

      leitorArqHash.close();
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
      AvlEspec espectadores = carregarEspectadores("dados2Espectadores2020-2.txt");

      // Teste da AVL de Series
      AvlSeriesLancadas seriesLancadas = carregarSeriesLancadas("dados2SeriesTV2020-2.txt");

      // Teste da Hash de Series
      HashSeries seriesHash = carregarSeries("dados2SeriesTV2020-2.txt");

      //System.out.println("Séries Tabela Hash:");
      //seriesHash.imprimir();

      // Teste da localização de Espectador por CPF
      Espectador localizado = espectadores.localizar("369382373-63");
      System.out.println(localizado.toString());

      // Teste da localização de Série por nome
      Series localizada = seriesHash.localizar("The Walking Bubbles - Temporada 7");
      System.out.println("\nSérie localizada: \n" + localizada.toString());

      // Teste da remoção por nome
      //Series removida = seriesHash.remover("Breaking Bad");
      //System.out.println("\nSérie removida: \n" + removida.toString() + "\n");

      //System.out.println("Séries Tabela Hash (Atualizada):");
      //seriesHash.imprimir();
      //System.out.println("\n");


      //Teste da localização de Série por data
      buscaData("17/07/1997"); // Não existe
      buscaData("20/01/2018"); // Friends + Copia

   }
}