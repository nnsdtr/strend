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
      this.avaliacao = null;
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

}
