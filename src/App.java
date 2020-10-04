import java.io.FileNotFoundException;

public class App {
   public static void main(String[] args) throws FileNotFoundException {
      EspecLista dbEspectadores = new EspecLista();
      dbEspectadores.carregar("fake_data.txt");

      Espectador espectador = dbEspectadores.localizar("123.755.737-22");

      System.out.println(espectador.nome + " <" + espectador.login + ">");
   }
}

