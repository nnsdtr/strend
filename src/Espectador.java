/**
 * Espectador é o usuário da plataforma Strend.
 */
public class Espectador {
   public String cpf;
   public String nome;
   public String login;
   public String senha;
   public EspecAvalLista avaliacao;

   /**
    * Método construtor
    */
   public Espectador() {
      this.cpf = "";
      this.nome = "";
      this.login = "";
      this.senha = "";
      this.avaliacao = new EspecAvalLista();
   }

   /**
    * Método de impressão do Espectador
    * @return Atributos principais e não sensíveis do espectador organizados para melhor exibição.
    */
   public String toString(){
      return "\nCPF: " + cpf + "\nNome: " + nome + "\nLogin: " + login + "\n";
   }

   /**
    * Método de comparação de igualdade para espectadores via CPF.
    * @param obj String. CPF a ser comparado.
    * @return boolean. Verdadeiro se for igual.
    */
   public boolean equals(Object obj) {
      String comparacao = (String) obj;
      return this.cpf.equals(comparacao);
   }

   /**
    * Método de comparação para espectadores via Objeto Classe Espectador.
    * @param obj Objeto da Classe Espectador a ser comparado.
    * @return inteiro. Diferença lexicográfica entre os CPFs avaliados. Usado na AvlEspec.
    */
   public int compareTo(Espectador outroEspec){
      return this.cpf.compareTo(outroEspec.cpf);
   }

   /**
    * Método de comparação para espectadores via CPF.
    * @param obj String. CPF a ser comparado.
    * @return inteiro. Diferença lexicográfica entre os CPFs avaliados. Essencial para AvlEspec.
    */
   public int compareTo(String cpf){
      return this.cpf.compareTo(cpf);
   }

   /**
    * Impressão em ordem decrescente de nota do Histórico de Avaliações.
    * @return String. Séries avaliadas em ordem decrescente de nota.
    */
   public String toStringAval() {
      if (avaliacao.vazia())
         return "Histórico de avaliações:\n\nNão há avaliações ainda! =(\n";
      else {
         EspecAvalLista.Serie[] vetor = new EspecAvalLista.Serie[ avaliacao.getTamanho() ];
         EspecAvalLista.Elemento aux = avaliacao.sentinela.prox;

         int pos = 0;
         while (aux != null) {
            vetor[pos++] = aux.serie;
            aux = aux.prox;
         }

         MergeSort.ordenacao(vetor);

         StringBuilder imprimir = new StringBuilder("Histórico de avaliações:\n");
         for (EspecAvalLista.Serie serie : vetor) {
            imprimir.append( serie.toString() );
         }

         return imprimir.toString();
      }
   }

}
