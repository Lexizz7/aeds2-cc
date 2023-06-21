import java.util.*;

class No {
    public char elemento;
    public No esq, dir;

    No(char x) {
        this.elemento = x;

        this.esq = null;
        this.dir = null;
    }
}

class Arvore {
    // Arvore simples:
    // Método para inserir, para pesquisar e os "caminhar"
    private No raiz;

    Arvore() {
        raiz = null;
    }

    public void inserir(char x) throws Exception {
        raiz = inserir(x, raiz);
    }

    // Cria nó na folha (quando for null)
    // Enquanto não for folha, seguir pela direita ou esquerda
    private No inserir(char x, No no) throws Exception {
        if (no == null) {
            no = new No(x);
        } else if (x < no.elemento) {
            no.esq = inserir(x, no.esq);
        } else if (x > no.elemento) {
            no.dir = inserir(x, no.dir);
        } else {
            throw new Exception("Erro: elemento já existe.");
        }

        return no;
    }

    public boolean pesquisar(char x) {
        return pesquisar(x, raiz);
    }

    // Caso chegue na folha (no == null) significa que não existe (false)
    // Caso seja igual ao elemento, é pq existe
    // Se ainda tem o que caminhar, vai pra esquerda ou direita
    private boolean pesquisar(char x, No no) {
        boolean res;

        if (no == null) {
            res = false;
        } else if (x == no.elemento) {
            res = true;
        } else if (x < no.elemento) {
            res = pesquisar(x, no.esq);
        } else {
            res = pesquisar(x, no.dir);
        }

        return res;
    }

    public void caminharPre() {
        caminharPre(raiz);
        System.out.println("");
    }

    private void caminharPre(No no) {
        if (no != null) {
            System.out.print(no.elemento + " ");
            caminharPre(no.esq);
            caminharPre(no.dir);
        }
    }

    public void caminharCentral() {
        caminharCentral(raiz);
        System.out.println("");
    }

    private void caminharCentral(No no) {
        if (no != null) {
            caminharCentral(no.esq);
            System.out.print(no.elemento + " ");
            caminharCentral(no.dir);
        }
    }

    public void caminharPos() {
        caminharPos(raiz);
        System.out.println("");
    }

    private void caminharPos(No no) {
        if (no != null) {
            caminharPos(no.esq);
            caminharPos(no.dir);
            System.out.print(no.elemento + " ");
        }
    }
}

public class ArvoreBinaria {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        String entrada;

        // Enquanto existe próxima linha (enquanto não for EOF)
        while (sc.hasNextLine()) {
            entrada = sc.nextLine();

            if (entrada.length() > 2) { // Consistência
                // Compara a entrada, caso comece com I e não contenha "FIXA" insere
                // Caso comece com P e não contenha "FIXA" pesquisa
                // Caso contrário, INFIXA, PREFIXA ou POSFIXA
                if (entrada.charAt(0) == 'I' && !entrada.contains("FIXA")) {
                    // Letra a ser inserida: corta 2 caracteres do inicio
                    char elemento = entrada.substring(2, 3).charAt(0);
                    try {
                        arvore.inserir(elemento);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (entrada.charAt(0) == 'P' && !entrada.contains("FIXA")) {
                    char elemento = entrada.substring(2, 3).charAt(0);
                    System.out.println(arvore.pesquisar(elemento) ? elemento + " existe" : elemento + " nao existe");
                } else {
                    switch (entrada) {
                        case "INFIXA":
                            arvore.caminharCentral();
                            break;
                        case "PREFIXA":
                            arvore.caminharPre();
                            break;
                        case "POSFIXA":
                            arvore.caminharPos();
                            break;
                    }
                }
            }
        }
    }
}
