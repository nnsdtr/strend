import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

   public static void main(String[] args) throws IOException {
      ArrayList<Spectator> clients = readSpectators("fake_data.txt");

      for(Spectator client : clients) {
         System.out.println(client.cpf + " | " + client.name + " | " + client.login);
      }
   }

   /**
    * Lê arquivo e preenche vetor com dados dos espectadores
    * @param filepath Caminho do arquivo de espectadores.
    * @return Vetor com objetos Spectator.
    * @throws FileNotFoundException Exceção para arquivos não encontrados.
    */
   public static ArrayList<Spectator> readSpectators(String filepath) throws FileNotFoundException {
      ArrayList<Spectator> clients = new ArrayList<>();
      Scanner reader = new Scanner(new File(filepath));

      String[] attributes;
      while (reader.hasNextLine()) {
         attributes = reader.nextLine().split(";");

         Spectator client = new Spectator();
         client.cpf = attributes[0];
         client.name = attributes[1];
         client.login = attributes[2];

         clients.add(client);
      }

      reader.close();
      return clients;
   }
}

