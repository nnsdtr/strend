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
   public  static Espectador login(String cpf, String senha, AvlEspec avl){
      Espectador esp = new Espectador();
      esp = avl.localizar(cpf);
      if (esp == null){
         return null;
      }else
      {
         if(esp.senha.equals(senha)){
            return esp;
         }
         else{
            return null;
         }
      }
   }

   public static void carregarAvaliacoes(String caminho, AvlEspec avlEspec, HashSeries hashSeries) throws FileNotFoundException {
      Scanner leitorArqAval = new Scanner(new File(caminho));

      while (leitorArqAval.hasNextLine()) {
         String[] aux = leitorArqAval.nextLine().split(";");

         String cpf = aux[0];
         String serie = aux[1];
         int numEpAssistidos = Integer.parseInt(aux[2]);
         int nota = Integer.parseInt(aux[3]);

         Espectador especUpdate = avlEspec.localizar(cpf);
         if (especUpdate != null) especUpdate.avaliacao.inserir(serie, nota);

         Series serieUpdate = hashSeries.localizar(serie);
         if (serieUpdate != null)
            if (numEpAssistidos == serieUpdate.numeroTotalEpisodios) {
               serieUpdate.somaNotasValidas += nota;
               serieUpdate.qtdNotasValidas++;
            }
      }
      leitorArqAval.close();
   }

   public static void buscaData(String dataPesquisada) {
      AvlSeriesLancadas.Nodo listaLocalizada = seriesLancadas.localizar(seriesLancadas.raiz, dataPesquisada);
      System.out.println("Busca realizada na data " + dataPesquisada + ": ");
      if (listaLocalizada == null){
         System.out.println("Não existem séries lançadas nessa data.\n");
      }
      else {
         listaLocalizada.meuDado.imprimir();
      }
   }

   public static void MenuLogin() {
      System.out.println("                           Strend ###");
      System.out.println("\n                  =========================");
      System.out.println("                  |         STREND        |");
      System.out.println("                  |                       |");
      System.out.println("                  |    Informe o CPF:     |");
      System.out.println("                  |    Informe a Senha:   |");
      System.out.println("                  |                       |");
      System.out.println("                  =========================\n");

   }


   public static void main(String[] args) throws FileNotFoundException {
      Scanner ler = new Scanner(System.in);

      long tic = System.nanoTime();

      // Teste da AVL de Espectadores
      AvlEspec espectadores = carregarEspectadores("dados2Espectadores2020-2.txt");

      // Teste da AVL de Series
      AvlSeriesLancadas seriesLancadas = carregarSeriesLancadas("dados2SeriesTV2020-2.txt");

      // Teste da Hash de Series
      HashSeries seriesHash = carregarSeries("dados2SeriesTV2020-2.txt");

      // Teste de carregamento das Avaliações
      carregarAvaliacoes("dados2AvaliacaoSeries2020-2.txt", espectadores, seriesHash);

      long toc = System.nanoTime();
      System.out.println("Tempo de carregamento = " + (toc - tic) / 1000000000 + " segundos");


      Boolean menu = false;
      do{
         MenuLogin();
         String cpf = "";
         String senha = "";
         cpf = ler.nextLine();
         senha = ler.nextLine();
         Espectador localizado = login(cpf, senha, espectadores);
         if(localizado == null){
            menu = false;
            System.out.println("Usuario ou Senha incorretos");
         }
         else{
            menu = true;
            System.out.println(localizado.toString());
            System.out.println(localizado.toStringAvaliacoes());

         }
      }while (menu == false);
      System.out.println("chegou");



      //System.out.println("Séries Tabela Hash:");
      //seriesHash.imprimir();

      // Teste da localização de Espectador por CPF
      Espectador localizado = espectadores.localizar("369382373-63");
      System.out.println(localizado.toString());
      System.out.println(localizado.toStringAvaliacoes());

      // Teste da localização de Série por nome
      Series localizada = seriesHash.localizar("The Walking Bubbles - Temporada 7");
      System.out.println("\nSérie localizada: \n" + localizada.toString());
      System.out.println("Soma das notas: " + localizada.somaNotasValidas);
      System.out.println("Qtd de notas: " + localizada.qtdNotasValidas);
      System.out.println("Média das notas: " + localizada.somaNotasValidas / (float) localizada.qtdNotasValidas );


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