public class Lista {
   public Elemento sentinela;
   public Elemento ultimo;


   /**
    * Método construtor (sem verificação)
    */
   public Lista() {
      sentinela = new Elemento(null);
      ultimo = sentinela;
   }


   /**
    * Verifica se lista está vazia
    * @return boolean.
    */
   public boolean vazia() {
      return (ultimo == sentinela);
   }


   /**
    * Insere Espectador na Lista
    * @param quem Um objeto Espectador.
    */
   public void inserir(Espectador quem) {
      Elemento novo = new Elemento(quem);
      ultimo.proximo = novo;
      ultimo = novo;
   }


   /**
    * Localiza Espectador via CPF
    * @param cpf Identificador CPF do espectador.
    * @return O objeto Espectador localizado.
    */
   public Espectador localizar(String cpf) {
      Elemento aux = sentinela.proximo;

      while (aux != null) {
         if (aux.equals(cpf))
            return aux.espectador;
         else
            aux = aux.proximo;
      }

      return null;
   }



   public Espectador remover(String cpf) {
      Elemento aux = sentinela;

      while (aux.proximo != null) {
         if (aux.proximo.equals(cpf)) {
            Elemento saida = aux.proximo;
            aux.proximo = saida.proximo;

            if (saida == ultimo)
               ultimo = aux;
            else
               saida.proximo = null;

            return saida.espectador;
         }
         else
            aux = aux.proximo;
      }

      return null;
   }
}
