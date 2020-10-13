import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EspecLista {
   public EspecElemento sentinela;
   public EspecElemento ultimo;


   /**
    * Método construtor (sem verificação)
    */
   public EspecLista() {
      sentinela = new EspecElemento(null);
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
    * @param dado Um objeto Espectador.
    */
   public void inserir(Espectador dado) {
      EspecElemento novo = new EspecElemento(dado);
      ultimo.proximo = novo;
      ultimo = novo;
   }


   /**
    * Localiza Espectador via CPF
    * @param cpf Identificador CPF do meuDado.
    * @return O objeto Espectador localizado.
    */
   public Espectador localizar(String cpf) {
      EspecElemento aux = sentinela.proximo;

      while (aux != null) {
         if (aux.equals(cpf))
            return aux.dado;
         else
            aux = aux.proximo;
      }

      return null;
   }


   /**
    * Remove elemento da lista via CPF
    * @param cpf Código cpf do espectador.
    * @return Retorna o espectador removido ou null se não for encontrado.
    */
   public Espectador remover(String cpf) {
      EspecElemento aux = sentinela;

      while (aux.proximo != null) {
         if (aux.proximo.equals(cpf)) {
            EspecElemento saida = aux.proximo;
            aux.proximo = saida.proximo;

            if (saida == ultimo)
               ultimo = aux;
            else
               saida.proximo = null;

            return saida.dado;
         }
         else
            aux = aux.proximo;
      }

      return null;
   }
}
