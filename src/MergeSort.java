public class MergeSort {

   /**
    * Método de ordenação abstraído para uso.
    * @param vetor Vetor a ser ordenado.
    */
   public static void ordenacao(EspecAvalLista.Serie[] vetor) {
      merge(vetor, 0, vetor.length-1);
   }

   /**
    * Método de ordenação MergeSort (Recursivo)
    * @param vetor Vetor a ser ordenado.
    * @param inicio Posição inicial do (sub)vetor a ser ordenado.
    * @param fim Posição final do (sub))vetor a ser ordenado.
    */
   private static void merge(EspecAvalLista.Serie[] vetor, int inicio, int fim) {
      int meio;
      if (inicio < fim) {
         meio = (inicio + fim) / 2;
         merge(vetor, inicio, meio);
         merge(vetor, meio+1, fim);
         intercala(vetor, inicio, meio, fim);
      }
   }

   /**
    * Intercala subsequências
    * @param vetor (Sub)vetor a ser ordenado
    * @param inicio Posição de início do vetor a ser intercalado.
    * @param meio Posição do meio calculada.
    * @param fim Última posição do vetor a ser intercalado.
    */
   private static void intercala (EspecAvalLista.Serie[] vetor, int inicio, int meio, int fim) {
      int poslivre, inicioVet1, inicioVet2, i;

      EspecAvalLista.Serie[] aux = new EspecAvalLista.Serie[vetor.length];
      inicioVet1 = inicio;
      inicioVet2 = meio + 1;
      poslivre = inicio;

      while (inicioVet1 <= meio && inicioVet2 <= fim) {
         if (vetor[inicioVet1].nota >= vetor[inicioVet2].nota) {
            aux[poslivre] = vetor[inicioVet1];
            inicioVet1++;
         } else {
            aux[poslivre] = vetor[inicioVet2];
            inicioVet2++;
         }

         poslivre++;
      }

      for(i = inicioVet1; i <= meio; i++) {
         aux[poslivre] = vetor[i];
         poslivre++;
      }

      for (i = inicioVet2; i <= fim; i++) {
         aux[poslivre] = vetor[i];
         poslivre++;
      }

      for(i=inicio; i <= fim; i++)
         vetor[i] = aux[i];
   }
}
