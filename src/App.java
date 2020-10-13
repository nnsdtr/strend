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
    * Método principal
    * @param args sem argumentos
    * @throws FileNotFoundException Exceção para falha de leitura.
    */
   public static void main(String[] args) throws FileNotFoundException {
      EspecLista dbEspectadores = carregar("fake_data.txt");

      Espectador espectador = dbEspectadores.localizar("123.755.737-22");

      System.out.println(espectador.toString());
   }
}