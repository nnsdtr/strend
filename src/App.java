import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;


public class App {

   // GRUPO DE GLOBAIS
   private static HashSeries seriesHash = new HashSeries();
   private static AvlSeriesLancadas seriesLancadas = new AvlSeriesLancadas();
   private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);


   // GRUPO DE CARREGAMENTO
   /**
    * Carregamento da AVL de Espectadores
    * @param caminho String. Caminho do arquivo.
    * @return AVL carregada.
    * @throws FileNotFoundException
    */
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

   /**
    * Carregamento Da AVL de Séries Lançadas em mesma data
    * @param caminho String. Caminho do arquivo.
    * @return AVL carregada.
    * @throws FileNotFoundException
    */
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

   /**
    * Carregamento da Tabela Hash de Séries
    * @param caminho String. Caminho do arquivo.
    * @return Tabela Hash carregada.
    * @throws FileNotFoundException
    */
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

   /**
    * Carregamento de dados de avaliações na AVL de Espectadores e Tabela Hash das Séries
    * @param caminho String. Caminho do arquivo.
    * @param avlEspec AVL de Espectadores.
    * @param hashSeries Tabela Hash das Séries.
    * @throws FileNotFoundException
    */
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


   // GRUPO DE MÉTODOS DA INTERFACE DA PLATAFORMA
   /**
    * Método de login do usuário espectador
    * @param cpf String. CPF do usuário.
    * @param senha String. Senha do usuário.
    * @param avl AVL dos Espectadores.
    * @return Objeto Espectador encontrado. Null se não for encontrado.
    */
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

   /**
    * Menu de Login
    * @return Vetor contendo CPF e Senha digitados.
    */
   public static String [] MenuLogin() {
      String[] dados = new String[2];
      Scanner ler = new Scanner(System.in);
      String cpf = "";
      String senha = "";

      System.out.println("___________________________________\n\nSTREND - Login");
      System.out.print("Informe o CPF: ");
      cpf = ler.next();
      System.out.print("Informe a Senha: ");
      senha = ler.next();

      dados[0] = cpf;
      dados[1] = senha;

      ler.close();

      return dados;
   }

   /**
    * Menu principal pós-login (Página do usuário)
    * @param esp Espectador logado.
    * @param espectadores AVL de Espectadores carregada.
    * @param localizado
    * @return Opção selecionada.
    */
   public static int MenuPrincipal(Espectador esp, AvlEspec espectadores, Espectador localizado) {
      int opcao;
      Scanner ler = new Scanner(System.in);

      System.out.println("___________________________________\n\nOlá, " + localizado.nome +"!\n");
      System.out.println("Opções:");
      System.out.println("1- Mostrar meus Dados");
      System.out.println("2- Pesquisar meu histórico de avaliações");
      System.out.println("3- Pesquisas");
      System.out.print("\nDigite a opção: ");
      opcao = ler.nextInt();

      MenuPrincipalCase(opcao, espectadores, localizado);
      return opcao;
   }

   /**
    * Encaminha opção selecionada no Menu Principal.
    * @param opcao Opção selecionada
    * @param espectadores AVL de Espectadores
    * @param localizado
    */
   public static  void MenuPrincipalCase(int opcao, AvlEspec espectadores, Espectador localizado){
      if (opcao == 1) { //opcao de mostrar os dados do espectador
         System.out.println("\nMeus dados:\n\n"+localizado.toString());
         MenuPrincipal(localizado,espectadores,localizado);
      }
      else if (opcao == 2) { //opcao de mostrar historico de avaliacoes do espectador
         String historicoAval = localizado.toStringAval();
         System.out.println("\n"+historicoAval+"\n");
         MenuPrincipal(localizado,espectadores,localizado);
      }
      else if(opcao==3) {
         System.out.println("\n");
         MenuPesquisas(espectadores, localizado, localizado);
      }
      else {
         System.out.println("\nOpção inválida! Tente novamente.\n");
         MenuPrincipal(localizado,espectadores,localizado);
      }
   }

   /**
    * Menu de Pesquisas
    * @param espectadores AVL de Espectadores
    * @param esp
    * @param localizado
    */
   public static void  MenuPesquisas(AvlEspec espectadores, Espectador esp, Espectador localizado) {
      int opcao2;
      Scanner ler = new Scanner(System.in);

      System.out.println("___________________________________\n\nPesquisas disponíveis:");
      System.out.println("1- Espectador");
      System.out.println("2- Série");
      System.out.println("3- Voltar");
      System.out.print("\nDigite a opção: ");
      opcao2 = ler.nextInt();

      if (opcao2 == 1) {
         Scanner ler2 = new Scanner(System.in);
         System.out.print("\nInsira o CPF do Espectador: ");
         String cpf = ler2.nextLine();
         Espectador esp1 = espectadores.localizar(cpf);

         if (esp1 == null) {
            System.out.println("\nEspectador não localizado! Tente novamente.\n");
            MenuPesquisas(espectadores, esp, localizado);
         }
         else
            MenuEspec(esp1, espectadores, localizado);
      }

      else if (opcao2 == 2)
         MenuSeries(espectadores, esp, localizado);

      else if (opcao2 == 3)
         MenuPrincipal(esp,espectadores,localizado);

      else {
         System.out.println("\nOpção inválida! Tente novamente.\n");
         MenuPesquisas(espectadores, esp, localizado);
      }
   }

   /**
    * Menu do Espectador Pesquisado
    * @param esp
    * @param espectadores
    * @param localizado
    */
   public static void MenuEspec(Espectador esp, AvlEspec espectadores, Espectador localizado) {
      int opcao;
      Scanner ler = new Scanner(System.in);
      System.out.println("___________________________________\n\nVocê está na página de " + esp.nome + "!\n");
      System.out.println("1- Dados do Espectador");
      System.out.println("2- Histórico de avaliações");
      System.out.println("3- Voltar");
      System.out.print("\nDigite a opção: ");
      opcao = ler.nextInt();

      if (opcao==1) {
         ImprimirEspectador(esp);
         MenuEspec(esp, espectadores, localizado);
      }

      else if (opcao==2) {
         ImprimirHistorioAvaliacao(esp);
         MenuEspec(esp, espectadores, localizado);
      }

      else if (opcao==3)
         MenuPesquisas(espectadores, esp, localizado);

      else {
         System.out.println("\nOpção inválida! Tente novamente.\n");
         MenuEspec(esp, espectadores, localizado);
      }
   }


   /**
    * Menu de Séries
    * @param espectadores AVL de Espectadores.
    * @param esp
    * @param logado Espectador logado.
    */
   public static void MenuSeries(AvlEspec espectadores, Espectador esp, Espectador logado){
      int escolhaSeries;
      Scanner ler2 = new Scanner(System.in);
      System.out.println("___________________________________\n\nPesquisa por series:\n");
      System.out.println("1- Séries Lançadas em uma data");
      System.out.println("2- Nota Média de uma Séries");
      System.out.println("3- Voltar");
      System.out.print("\nDigite a opção: ");

      escolhaSeries = ler2.nextInt();

      if (escolhaSeries == 1) {
         Scanner lerData = new Scanner(System.in);
         System.out.print("\nDigite uma data (dd/mm/aaaa): ");
         String data = lerData.nextLine();
         if (!data.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
            System.out.println("\nFormato de data inválido.\n");
            MenuSeries(espectadores, esp, logado);
         }
         else {
            buscaData(data);
            MenuSeries(espectadores, esp, logado);
         }
      }

      else if (escolhaSeries == 2) {
         Scanner lerSerie = new Scanner(System.in);
         System.out.print("\nDigite o nome da série: ");
         String serie = lerSerie.nextLine();
         Series localizada = seriesHash.localizar(serie);

         if (localizada == null) {
            System.out.println("Série não encontrada");
         } else {
            System.out.println("\nSérie localizada: \n" + localizada.toString());
            System.out.println("Média das notas: " + localizada.notaMedia());
            System.out.println(localizada.qtdNotasValidas + " usuários avaliaram") ;
         }

         MenuSeries(espectadores,esp,logado);
      }

      else if (escolhaSeries == 3) {
         MenuPesquisas(espectadores,esp, logado);
      }

      else {
         System.out.println("\nOpção inválida! Tente novamente.\n");
         MenuSeries(espectadores,esp,logado);
      }
   }

   // GRUPO DE MÉTODOS UTILITÁRIOS
   /**
    * Realiza busca na AVL de Séries Lançadas na Mesma Data
    * @param dataPesquisada String. Data a ser pesquisada.
    */
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

   /**
    * Impressão do espectador
    * @param localizado Objeto Espectador.
    */
   public static void ImprimirEspectador(Espectador localizado) {
      System.out.println(localizado.toString());
   }

   /**
    * Impressão do Histórico de Avaliações
    * @param esp Objeto Espectador.
    */
   public static void ImprimirHistorioAvaliacao(Espectador esp) {
      String historicoAval = esp.toStringAval();
      System.out.println(historicoAval);
   }


   // Método principal
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
      System.out.println("\nTempo de carregamento = " + (toc - tic) / 1000000000 + " segundos\n");


      // LOGIN
      boolean menu;
      do {
         String[] dados = new String[2];
         dados = MenuLogin();
         System.out.println(" ");
         Espectador usuario_logado = login(dados[0], dados[1], espectadores);
         if (usuario_logado == null){
            menu = false;
            System.out.println("Usuário ou senha incorretos!\n");
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
   }
}