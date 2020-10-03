import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
   public static ListaEspectadores lerEspectadores(String caminho) throws FileNotFoundException {
      Scanner leitorArq = new Scanner(new File(caminho));

      ListaEspectadores espectadores = new ListaEspectadores();

      while(leitorArq.hasNextLine()) {
         String[] aux = leitorArq.nextLine().split(";");

         Espectador novo = new Espectador();
         novo.cpf = aux[0];
         novo.nome = aux[1];
         novo.login = aux[2];

         espectadores.inserir(novo);
      }

      return espectadores;
   }

   public static void main(String[] args) {

   }
}

