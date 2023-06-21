import java.util.*;

// Celula com 4 direções
class Celula {
    public int elemento;
    public Celula esq, dir, sup, inf;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = 0;
        this.esq = null;
        this.dir = null;
        this.sup = null;
        this.inf = null;
    }
}

class Matriz {
    // Percorre iniciando sempre da célula inicio (0,0)
    private Celula inicio;
    private int linhas, colunas;

    public Matriz() {
        this(3, 3);
    }

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;

        // Cria a célula (0,0)
        this.inicio = new Celula();

        // Preencher primeira linha
        Celula tmp = this.inicio;
        // Percorre somente para a direita
        // Linkando as respectivas células
        for (int c = 1; c < this.colunas; c++) {
            tmp.dir = new Celula();
            tmp.dir.esq = tmp;
            tmp = tmp.dir;
        }

        // Preencher outras linhas
        Celula tmp2; // Referencia para linha de cima da atual sendo preenchida
        for (int l = 1; l < this.linhas; l++) {
            // Inicializa as duas referencias para o inicio
            tmp = this.inicio;
            tmp2 = this.inicio;
            // Percorre para baixo até a linha que será preenchida
            for (int j = 0; j < l; j++) {
                tmp = tmp.inf;
            }
            // Percorre para baixo até a linha acima da que será preenchida
            for (int j = 1; j < l; j++) {
                tmp2 = tmp2.inf;
            }
            // Cria uma nova célula na posição 0 da linha
            // Linka superior e inferior, passa referencia 2 para a direita
            tmp = new Celula();
            tmp.sup = tmp2;
            tmp2.inf = tmp;
            tmp2 = tmp2.dir;
            // Percorre o restante das colunas
            for (int c = 1; c < this.colunas; c++) {
                tmp.dir = new Celula();
                tmp.dir.esq = tmp;
                tmp = tmp.dir;

                tmp.sup = tmp2;
                tmp2.inf = tmp;
                tmp2 = tmp2.dir;
            }
        }
    }

    public String diagonalPrincipal() {
        String res = "";

        // Percorre a matriz e retorna o elemento quando Linha = Coluna
        for (int l = 0; l < this.linhas; l++) {
            Celula tmp = this.inicio;
            for (int j = 0; j < l; tmp = tmp.inf, j++)
                ;
            for (int c = 0; c < this.colunas; c++) {
                if (l == c)
                    res += tmp.elemento + " ";

                tmp = tmp.dir;
            }
        }

        return res;
    }

    public String diagonalSecundaria() {
        String res = "";

        // Percorre a matriz e retorna o elemento quando Linha + Coluna = Total - 1
        // Total pode ser de colunas ou de linhas, visto que só vale matrizes quadradas
        // (Como o pub.in não tem matrizes sem ser quadrada, não verifiquei)
        for (int l = 0; l < this.linhas; l++) {
            Celula tmp = this.inicio;
            for (int j = 0; j < l; tmp = tmp.inf, j++)
                ;
            for (int c = 0; c < this.colunas; c++) {
                if (l + c == this.colunas - 1)
                    res += tmp.elemento + " ";

                tmp = tmp.dir;
            }
        }

        return res;
    }

    public Matriz somarCom(Matriz m2) throws Exception {
        Matriz res = new Matriz(this.linhas, this.colunas);

        // Percorre a matriz criada (de mesmo tamanho da matriz 1)
        // E troca seu valor para a soma entre matriz 1 e 2
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                res.alterarValor(c, l, (valorAt(c, l) + m2.valorAt(c, l)));
            }
        }

        return res;
    }

    public Matriz multiplicarCom(Matriz m2) throws Exception {
        Matriz res = new Matriz(this.linhas, this.colunas);
        int valor = 0;

        // Praticamente igual a soma, porém percorre as colunas da matriz 2
        // E para cada coluna, percorre as linhas da matriz 2
        for (int l = 0; l < this.linhas; l++) {
            for (int c2 = 0; c2 < m2.colunas; c2++) {
                for (int l2 = 0; l2 < m2.linhas; l2++) {
                    valor += valorAt(l2, l) * m2.valorAt(c2, l2);
                }

                res.alterarValor(c2, l, valor);

                valor = 0;
            }
        }

        return res;
    }

    // Recebe uma coordenada e um valor, para atualizar a matriz
    public void alterarValor(int x, int y, int valor) throws Exception {
        if (x >= this.colunas || y >= this.linhas || x < 0 || y < 0)
            throw new Exception("Posicao nao existe");

        // Percorre até a coordenada
        Celula tmp = this.inicio;
        for (int l = 0; l < y; l++, tmp = tmp.inf)
            ;
        for (int c = 0; c < x; c++, tmp = tmp.dir)
            ;

        tmp.elemento = valor; // Atualiza seu elemento
    }

    // Retorna o elemento na posição passada pelo parâmetro
    public int valorAt(int x, int y) throws Exception {
        if (x >= this.colunas || y >= this.linhas || x < 0 || y < 0)
            throw new Exception("Posicao nao existe");

        // Mesmo raciocínio de percorrer a matriz
        Celula tmp = this.inicio;
        for (int l = 0; l < y; l++, tmp = tmp.inf)
            ;
        for (int c = 0; c < x; c++, tmp = tmp.dir)
            ;

        return tmp.elemento; // Ao invés de atualizar o elemento, retorna ele
    }

    public void mostrar() {
        // Percorre a matriz dando uma quebra de linha a cada linha
        for (int l = 0; l < this.linhas; l++) {
            Celula tmp = this.inicio;
            for (int j = 0; j < l; j++) {
                tmp = tmp.inf;
            }
            for (int c = 0; c < this.colunas; c++) {
                System.out.print(tmp.elemento + " ");
                tmp = tmp.dir;
            }
            System.out.println("");
        }
    }
}

public class MatrizDinamica {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int linhas, colunas, valor;
        int nTestes;

        // Primeiro numero do pub.in indica a quantidade de testes (cada teste com duas
        // matrizes)
        nTestes = sc.nextInt();

        for (int i = 0; i < nTestes; i++) { // Para cada teste
            // Preencher a primeira matriz
            linhas = sc.nextInt();
            colunas = sc.nextInt();
            Matriz matriz1 = new Matriz(linhas, colunas);

            // Percorre para cada linha, o número de colunas
            // Alterando o valor padrão para o valor do pub.in
            for (int l = 0; l < linhas; l++) {
                for (int c = 0; c < colunas; c++) {
                    valor = sc.nextInt();
                    try {
                        matriz1.alterarValor(c, l, valor);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            // Preencher a segunda matriz
            linhas = sc.nextInt();
            colunas = sc.nextInt();
            Matriz matriz2 = new Matriz(linhas, colunas);

            for (int l = 0; l < linhas; l++) {
                for (int c = 0; c < colunas; c++) {
                    valor = sc.nextInt();
                    try {
                        matriz2.alterarValor(c, l, valor);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            // Imprime os dados
            try {
                System.out.println(matriz1.diagonalPrincipal());
                System.out.println(matriz1.diagonalSecundaria());
                matriz1.somarCom(matriz2).mostrar();
                matriz1.multiplicarCom(matriz2).mostrar();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
