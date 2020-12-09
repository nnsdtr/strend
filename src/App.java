import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;


public class App {

   // Grupo de Globais
   private static HashSeries seriesHash = new HashSeries();

   private static AvlSeriesLancadas seriesLancadas = new AvlSeriesLancadas();

   private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

   // Grupo de carregamento
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


   // Busca para data
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


   // Métodos de Login
   public  static Espectador login(String cpf, String senha, AvlEspec avl) {
      Espectador esp;
      esp = avl.localizar(cpf);
      if (esp == null)
         return null;
      else {
         if(esp.senha.equals(senha)) return esp;
         else return null;
      }
   }

   public static String [] MenuLogin() {

      String[] dados = new String[2];
      Scanner ler = new Scanner(System.in);
      String cpf = "";
      String senha = "";

      System.out.println("STREND");
      System.out.print("Informe o CPF: ");cpf = ler.next();
      System.out.print("Informe a Senha: ");senha = ler.next();
      dados[0] = cpf;
      dados[1] = senha;
      return dados;
   }

   public static int MenuPrincipal(Espectador esp, AvlEspec espectadores, Espectador localizado) {

      int opcao = 0;
      Scanner ler = new Scanner(System.in);

      System.out.println("Olá, " + localizado.nome +"!\n");
      System.out.println("Opções:");
      System.out.println("1- Mostrar meus Dados");
      System.out.println("2- Pesquisar meu histórico de avaliações");
      System.out.println("3- Pesquisas");
      System.out.print("Digite a opção: ");
      opcao = ler.nextInt();

      MenuPrincipalCase(opcao,espectadores,localizado);
      return opcao;
   }

   public static  void MenuPrincipalCase(int opcao, AvlEspec espectadores, Espectador localizado){
      if (opcao == 1) { //opcao de mostrar os dados do espectador
         System.out.println("\nMeus dados:\n"+localizado.toString()+"\n\n\n");
         MenuPrincipal(localizado,espectadores,localizado);
      }
      if (opcao == 2) { //opcao de mostrar historico de avaliacoes do espectador
         String historicoAval = localizado.toStringAval();
         System.out.println("\n"+historicoAval+"\n\n\n");
         MenuPrincipal(localizado,espectadores,localizado);
      }
      if(opcao==3){
         System.out.println("\n\n\n");
         MenuPesquisas(espectadores, localizado, localizado);
      }
   }

   public static void MenuSeries(AvlEspec espectadores, Espectador esp, Espectador logado){
      int escolhaSeries;
      Scanner ler2 = new Scanner(System.in);
      System.out.println("Pesquisa por series");
      System.out.println("1- Séries Lançadas em uma data");
      System.out.println("2- Nota Média de uma Séries");
      System.out.println("3- Voltar");
      System.out.print("Digite a opção: ");

      escolhaSeries = ler2.nextInt();

      if (escolhaSeries == 1) {
         Scanner lerData = new Scanner(System.in);
         System.out.println("Digite uma data para pesquisar no formato dia/mes/ano");
         String data = lerData.nextLine();
         System.out.println(data);
         buscaData(data);
         MenuSeries(espectadores,esp,logado);
      }

      else if (escolhaSeries == 2) {
         Scanner lerSerie = new Scanner(System.in);
         System.out.println("Digite o nome da série: ");
         String serie = lerSerie.nextLine();
         Series localizada = seriesHash.localizar(serie);
         if (localizada == null) {
            System.out.println("Série não encontrada");
         } else {
            System.out.println("\nSérie localizada: \n" + localizada.toString());
            System.out.println("Média das notas: " +localizada.notaMedia());
            System.out.println(localizada.qtdNotasValidas + "Usuários avaliaram") ;
         }

         MenuSeries(espectadores,esp,logado);
      }

      else if (escolhaSeries == 3){
         MenuPesquisas(espectadores,esp, logado);
      }

      else {
         System.out.println("Opção inválida! Tente novamente.\n\n\n");
         MenuSeries(espectadores,esp,logado);
      }
   }

   public static void  MenuPesquisas(AvlEspec espectadores, Espectador esp, Espectador localizado) {
      int opcao2;
      Scanner ler = new Scanner(System.in);

      System.out.println("Pesquisas disponíveis");
      System.out.println("1- Espectador");
      System.out.println("2- Série");
      System.out.println("3- Voltar");
      System.out.print("Digite a opção: ");
      opcao2 = ler.nextInt();
      String cpf = "";

      if (opcao2 == 1) {
         Scanner ler2 = new Scanner(System.in);
         System.out.println("Pesquisa CPF: ");
         cpf = ler2.nextLine();
         Espectador esp1 = espectadores.localizar(cpf);
         MenuEspec(esp1, espectadores, localizado);
      }
      else if (opcao2 == 2) {
         MenuSeries(espectadores, esp, localizado);
      }
      else if (opcao2 == 3) {
         MenuPrincipal(esp,espectadores,localizado);
      }
      else {
         System.out.println("Opção inválida! Tente novamente.\n\n\n");
         MenuPesquisas(espectadores, esp, localizado);
      }
   }

   public static void MenuEspec(Espectador esp, AvlEspec espectadores, Espectador localizado) {
      int opcao;
      Scanner ler = new Scanner(System.in);
      System.out.println("Você está na página de " + esp.nome + "!");
      System.out.println("1- Dados do Espectador");
      System.out.println("2- Histórico de avaliações");
      System.out.println("3- Voltar");
      System.out.print("Digite a opção: ");
      opcao = ler.nextInt();

      if (opcao==1) {
         ImprimirEspectador(espectadores, esp);
         MenuEspec(esp, espectadores, localizado);
      }
      else if (opcao==2) {
         ImprimirHistorioAvaliacao(esp);
         MenuEspec(esp, espectadores, localizado);
      }
      else if (opcao==3) {
         MenuPesquisas(espectadores, esp, localizado);
      }
      else {
         System.out.println("Opção inválida! Tente novamente.\n\n\n");
         MenuEspec(esp, espectadores, localizado);
      }
   }

   public static void ImprimirEspectador(AvlEspec espectadores, Espectador localizado) {
      System.out.println(localizado.toString());
   }
   public static void ImprimirHistorioAvaliacao(Espectador esp) {
      String historicoAval = esp.toStringAval();
      System.out.println(historicoAval);
   }


   public static void main(String[] args) throws FileNotFoundException {
      // Marcação de tempo para carregamento
      long tic = System.nanoTime();

      // Teste da AVL de Espectadores
      AvlEspec espectadores = carregarEspectadores("dados2Espectadores2020-2.txt");

      // Teste da AVL de Series
      AvlSeriesLancadas seriesLancadas = carregarSeriesLancadas("dados2SeriesTV2020-2.txt");

      // Teste da Hash de Series
      HashSeries seriesHash = carregarSeries("dados2SeriesTV2020-2.txt");

      // Teste de carregamento das Avaliações
      carregarAvaliacoes("dados2AvaliacaoSeries2020-2.txt", espectadores, seriesHash);

      // Fim do cálculo de tempo para carregamento
      long toc = System.nanoTime();
      System.out.println("Tempo de carregamento = " + (toc - tic) / 1000000000 + " segundos\n");


      // Teste de login

      boolean menu;
      do {
         String [] dados = new String [2];
         dados = MenuLogin();
         System.out.println(" ");
         Espectador usuario_logado = login(dados[0], dados[1], espectadores);
         if (usuario_logado == null){
            menu = false;
            System.out.println("Usuário ou senha incorretos!");
         }
         else {
            menu = true;
            String cpf = null;
            Espectador esp;
            System.out.println(" ");
            int opcao = MenuPrincipal(usuario_logado,espectadores, usuario_logado);
            while (opcao >=1 && opcao <=3){
               MenuPesquisas(espectadores,usuario_logado, usuario_logado);

            }
         }
      } while (!menu);


      // Teste da localização de Espectador por CPF
      // Espectador localizado = espectadores.localizar("369382373-63");
      //System.out.println(localizado.toString());
      //System.out.println(localizado.toStringAval());

      // Teste da localização de Série por nome
      //Series localizada = seriesHash.localizar("The Walking Bubbles - Temporada 7");
      //System.out.println("\nSérie localizada: \n" + localizada.toString());
      //System.out.println("Soma das notas: " + localizada.somaNotasValidas);
      //System.out.println("Qtd de notas: " + localizada.qtdNotasValidas);
      //System.out.println("Média das notas: " + localizada.somaNotasValidas / (float) localizada.qtdNotasValidas );

      //Teste da localização de Série por data
      //buscaData("17/07/1997"); // Não existe
      //buscaData("20/01/2018"); // Friends + Copia

   }

   }
