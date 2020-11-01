public class Espectador {
   public String cpf;
   public String nome;
   public String login;

   /**
    * Método construtor
    */
   public Espectador() {
      this.cpf = "";
      this.nome = "";
      this.login = "";
   }

   public String toString(){
      return "CPF: " + cpf + " - Nome: " + nome + " - Login: " + login + "\n";
   }

   public boolean equals(Object obj) {
      String comparacao = (String) obj;

      return this.cpf.equals(comparacao);
   }

   public int compareTo (Espectador outroEspec){
      return this.cpf.compareTo(outroEspec.cpf);
   }

}
