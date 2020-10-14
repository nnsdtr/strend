import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
   /**
    * Carrega dados dos Espectadores na Lista
    * @param caminho caminho do arquivo fonte
    * @throws FileNotFoundException para erros de leitura de arquivo.
    */
   public static EspecLista carregar(String caminho) throws FileNotFoundException {
      Scanner leitorArq = new Scanner(new File(caminho));
      EspecLista lista = new EspecLista();

      while(leitorArq.hasNextLine()) {
         String[] aux = leitorArq.nextLine().split(";");

         Espectador novo = new Espectador();
         novo.cpf = aux[0];
         novo.nome = aux[1];
         novo.login = aux[2];

         lista.inserir(novo);
      }

      leitorArq.close();

      return lista;
   }


   /**
    * Imprimir no console o espectador
    * @param espectador Um objeto da classe Espectador.
    */
   public static void imprimirEspectador(Espectador espectador) {
      if(espectador != null)
         System.out.println(espectador.toString());
      else
         System.out.println("Espectador não localizado.");
   }


   /**
    * Método principal
    * @param args sem argumentos
    * @throws FileNotFoundException Exceção para falha de leitura.
    */
   public static void main(String[] args) throws FileNotFoundException {
      EspecLista dbEspectadores = carregar("fake_data.txt");

      Espectador espectador = dbEspectadores.localizar("123.745.737-22");

      imprimirEspectador(espectador);
   }
}