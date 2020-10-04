public class EspecElemento {
   public Espectador dado;
   public EspecElemento proximo;

   /**
    * Método construtor (sem verificação)
    * @param dado Um objeto Espectador.
    */
   public EspecElemento(Espectador dado) {
      this.dado = dado;
      this.proximo = null;
   }

   public boolean equals(Object obj) {
      String comparacao = (String) obj;

      return this.dado.equals(comparacao);
   }
}
