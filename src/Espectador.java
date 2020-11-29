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








   /**
    * APENAS PARA TESTE [DELETAR DEPOIS]
    * @return string. Todas as avaliações realizadas pelo espectador.
    */
   public String toStringAvaliacoes() {
      if (avaliacao.vazia())
         return "Não há avaliações para esse usuário\n";
      else {
         String impressao = "";
         EspecAvalLista.Elemento aux = avaliacao.sentinela.prox;
         while (aux != null) {
            impressao += aux.serie.nome + " [Nota: " + aux.serie.nota + "]\n";
            aux = aux.prox;
         }
         return impressao;
      }
   }
}
