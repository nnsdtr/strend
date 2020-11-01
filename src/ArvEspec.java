public class ArvEspec {
    public Espectador raiz;
    public ArvEspec subArvEsq, subArvDir;
    public int altura;


    /**
     * Método gerador da árvore
     */
    public ArvEspec() {
        this.raiz = null;
        this.subArvDir = this.subArvEsq = null;
        this.altura = 1;
    }

    /**
     * @param arv Árvore que se deseja calcular a altura
     * @return altura da árvore, definida como 1 por padrão, será incrementada/decrementeada posteriormente.
     */
    public int Altura (ArvEspec arv) {
        if (arv == null){
            return 0;
        }
        else{
            return arv.altura;
        }
    }

    /**
     * @param arvoreFator Árvore que se deseja calcular o Fator de Balanceamento
     * @return Definido como a altura da sub-árvore esquerda menos a altura da sub-árvore direita (alturaEsquerda - alturaDireita). IMPORTANTE: o valor resultante não segue a ordem da reta real matemática, é invertido, ou seja, -1 para direita e +1 para esquerda.
     */
    public int FatorBalanco (ArvEspec arvoreFator){
        if (arvoreFator == null){
            return 0;
        }
        return Altura(arvoreFator.subArvEsq) - Altura(arvoreFator.subArvDir);
    }

    /**
     * Inserção na árvore balanceada
     * @param novo O Espectador a ser inserido.
     */
    public ArvEspec Inserir (Espectador novo) {
        if (raiz == null) {
            raiz = novo;
            return this.balancear();
        }
        else {
            if (novo.compareTo(raiz) < 0) {
                if (subArvEsq == null) {
                    subArvEsq = new ArvEspec();
                }
                subArvEsq.Inserir(novo);
            } else {
                if (subArvDir == null) {
                    subArvDir = new ArvEspec();
                }
                subArvDir.Inserir(novo);
            }
        }
        altura = Math.max(Altura(subArvEsq), Altura(subArvDir)) + 1; // Incremento da altura da árvore que foi inserida

        return this;
    }

    public ArvEspec balancear ( ){
        int meuBalanco = FatorBalanco(this); // Cálculo do balanceamento da árvore

        //Rotação Esquerda
        if (meuBalanco == 2){
            //Rotação Esquerda novo.compareTo(subArvEsq.raiz)
            if (FatorBalanco(this.subArvEsq) < 0) {
                this.subArvEsq = rotacaoEsq(this.subArvEsq);
            }
            return rotacaoDir(this);
        }

        //Rotação Direita
        if (meuBalanco == -2){
            //Rotação Direita novo.compareTo(subArvEsq.raiz)
            if (FatorBalanco(this.subArvDir) > 0) {
                this.subArvDir = rotacaoDir(this.subArvDir);
            }
            return rotacaoEsq(this);
        }
        return this;
    }

    /** Rotação Esquerda
     * @param arvRodar Árvore a ser balanceada
     * @return Árvore balanceada
     */
    public ArvEspec rotacaoEsq(ArvEspec arvRodar){

        ArvEspec arvFinal = this.subArvDir; // Auxiliar para guardar a sub-árvore que causa o desbalanceamento.
        this.subArvDir = arvFinal.subArvEsq; // Atribuição no lado direito da árvore principal com os menores elementos do antigo lado direito

        arvFinal.subArvEsq = this; // Reorganização da árvore final

        return arvFinal;
    }

    /**Rotação Direita
     * @param arvRodar Árvore a ser balanceada
     * @return Árvore Balanceada
     */
    public ArvEspec rotacaoDir(ArvEspec arvRodar){

        ArvEspec arvFinal = this.subArvEsq; // Auxiliar para guardar a sub-árvore que causa o desbalanceamento.
        this.subArvEsq = arvFinal.subArvDir; // Atribuição no lado esquerdo da árvore principal com os maiores elementos do antigo lado esquerdo

        arvFinal.subArvDir = this; // Reorganização da árvore final

        return arvFinal;
    }

    public String emOrdem(){
        StringBuilder aux = new StringBuilder();            //StringBuilder: melhor desempenho que String
        if(subArvEsq!=null) aux.append(subArvEsq.emOrdem());  //anexamos a string dos elementos à esquerda (menores)
        aux.append(raiz.toString());    //anexamos a string do elemento-raiz
        if(subArvDir!=null) aux.append(subArvDir.emOrdem()); //anexamos a string dos elementos à direita (maiores)

        return aux.toString();  //retornamos a String
    }

    public String preOrdem(){
        String aux = "";

        aux += raiz.toString();
        if (subArvEsq != null) aux += subArvEsq.preOrdem();
        if (subArvDir != null) aux += subArvDir.preOrdem();

        return aux;
    }

}

