import java.util.*;

class No {
    public int elemento;
    public No esq, dir;

    No(int x) {
        this.elemento = x;

        this.esq = null;
        this.dir = null;
    }
}

class Arvore {
    // Arvore simples:
    // Método para inserir e os "caminhar"
    private No raiz;

    Arvore() {
        raiz = null;
    }

    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    // Cria nó na folha (quando for null)
    // Enquanto não for folha, seguir pela direita ou esquerda
    private No inserir(int x, No no) throws Exception {
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

    public void caminharPre() {
        caminharPre(raiz);
        System.out.println("");
    }

    private void caminharPre(No no) {
        if (no != null) {
            System.out.print(" " + no.elemento);
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
            System.out.print(" " + no.elemento);
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
            System.out.print(" " + no.elemento);
        }
    }
}

public class ArvoreBinaria {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int nCasos = sc.nextInt();
        for (int i = 0; i < nCasos; i++) { // Para cada caso
            Arvore arvore = new Arvore();

            int nElementos = sc.nextInt();
            for (int j = 0; j < nElementos; j++) { // Para cada elemento
                int elemento = sc.nextInt();
                try {
                    arvore.inserir(elemento);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Mostrar
            System.out.println("Case " + (i + 1) + ":");
            System.out.print("Pre.:");
            arvore.caminharPre();
            System.out.print("In..:");
            arvore.caminharCentral();
            System.out.print("Post:");
            arvore.caminharPos();

            System.out.println("");
        }
    }
}
