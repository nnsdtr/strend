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

   public String toString(){
      return "CPF: " + cpf + " - Nome: " + nome + " - Login: " + login + "\n";
   }

   public boolean equals(Object obj) {
      String comparacao = (String) obj;
      return this.cpf.equals(comparacao);
   }

   public int compareTo(Espectador outroEspec){
      return this.cpf.compareTo(outroEspec.cpf);
   }

   public int compareTo(String cpf){
      return this.cpf.compareTo(cpf);
   }


   public String toStringAval() {
      if (avaliacao.vazia())
         return "Histórico de avaliações:\nNão há avaliações ainda! =(\n";
      else {
         EspecAvalLista.Serie[] vetor = new EspecAvalLista.Serie[ avaliacao.getTamanho() ];
         EspecAvalLista.Elemento aux = avaliacao.sentinela.prox;

         int pos = 0;
         while (aux != null) {
            vetor[pos++] = aux.serie;
            aux = aux.prox;
         }

         MergeSort.ordenacao(vetor);

         String imprimir = "Histórico de avaliações:\n";
         for (int i=0; i < vetor.length; i++) {
            imprimir += vetor[i].toString();
         }

         return imprimir;
      }
   }
}
