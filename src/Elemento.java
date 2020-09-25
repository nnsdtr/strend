public class Elemento {
   public Espectador espectador;
   public Elemento proximo;

   /**
    * Método construtor (sem verificação)
    * @param espectador Um objeto Espectador.
    */
   public Elemento(Espectador espectador) {
      this.espectador = espectador;
      this.proximo = null;
   }

   public boolean equals(Object obj) {
      String comparacao = (String) obj;

      return this.espectador.equals(comparacao);
   }
}
