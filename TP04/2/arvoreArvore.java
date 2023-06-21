import java.util.*;
import java.io.BufferedReader;
// import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;

class Filme {
    // Atributos especificados no enunciado
    private String nome;
    private String tituloOriginal;
    private Date dataLancamento;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    public Filme() {
        // Construtor vazio com dados vazios/nulos
        this.nome = "";
        this.tituloOriginal = "";
        this.dataLancamento = new Date();
        this.duracao = 0;
        this.genero = "";
        this.idiomaOriginal = "";
        this.situacao = "";
        this.orcamento = 0;
        this.palavrasChave = new String[0];
    }

    public Filme(String nome, String tituloOriginal, Date dataLancamento, int duracao, String genero,
            String idiomaOriginal, String situacao, float orcamento, String[] palavrasChave) {
        // Utilizando o método set para inicializar os atributos
        this.setNome(nome);
        this.setTituloOriginal(tituloOriginal);
        this.setDataLancamento(dataLancamento);
        this.setDuracao(duracao);
        this.setGenero(genero);
        this.setIdiomaOriginal(idiomaOriginal);
        this.setSituacao(situacao);
        this.setOrcamento(orcamento);
        this.setPalavrasChave(palavrasChave);
    }

    // Métodos get e set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getDataLancamento() { // Retorna a data no formato dd/mm/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy"); // Formato da data
        return sdf.format(dataLancamento); // Retorna a data formatada
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal.trim(); // Retorna o idioma original sem espaços em branco
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Filme clone() { // Método clone
        return new Filme(this.nome, this.tituloOriginal, this.dataLancamento, this.duracao, this.genero,
                this.idiomaOriginal, this.situacao, this.orcamento, this.palavrasChave); // Retorna um novo filmes com
                                                                                         // os mesmos dados
    }

    public void imprimir() throws Exception { // Mostrar os dados do filme no formato especificado no enunciado
        PrintStream out = new PrintStream(System.out, true, "UTF-8"); // Imprime no formato UTF-8 para evitar problemas
                                                                      // de acentuação
        try {
            // Printa utilizando os métodos get
            out.print(this.getNome() + " ");
            out.print(this.getTituloOriginal() + " ");
            out.print(this.getDataLancamento() + " ");
            out.print(this.getDuracao() + " ");
            out.print(this.getGenero() + " ");
            out.print(this.getIdiomaOriginal() + " ");
            out.print(this.getSituacao() + " ");
            out.print(this.getOrcamento() + " ");
            out.print("[");
            out.print(String.join(", ", this.palavrasChave)); // Separa as palavras chave por vírgula e transforma em
                                                              // uma string
            out.print("]\n");
        } catch (Exception e) {
            throw new Exception("Erro ao imprimir o filme");
        }
    }

    private String removeTags(String text) { // Além de tags, remove os caracteres estranhos que estavam
                                             // no HTML
        String res = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') { // ao abrir uma tag, percorrer pulando o "i" até fechar a tag
                while (text.charAt(i) != '>') {
                    i++;
                }
            }
            if (text.charAt(i) != '>') {// como depois de retirar a tag o i == '>', o if serve para não aparecer
                                        // ">string"
                res += text.charAt(i);
            }
        }
        return res.replaceAll("&nbsp;", "");
    }

    private int getDuracao(String text) {
        text = text.trim(); // Garatir que não há espaços em branco
        String[] parts = text.split(" "); // Divide a string em partes (horas e minutos)
        int duracao = 0;
        if (parts.length == 2) { // Se for dividido por 2, é porque tem horas e minutos
            int horas = Integer.parseInt(parts[0].replace("h", "")); // Remove o h da string e converte para int
            int minutos = Integer.parseInt(parts[1].replace("m", ""));
            duracao = horas * 60 + minutos;
        } else if (parts.length == 1) { // Se for dividido por 1, é porque só tem minutos
            if (parts[0].contains("m")) {
                int minutos = Integer.parseInt(parts[0].replace("m", ""));
                duracao = minutos;
            } else if (parts[0].contains("h")) {
                int horas = Integer.parseInt(parts[0].replace("h", ""));
                duracao = horas * 60;
            }
        }

        return duracao;
    }

    public void ler(String html) throws Exception { // Lê um arquivo HTML e inicializa os atributos do filme
        FileReader arq = new FileReader("/tmp/filmes/" + html);
        BufferedReader br = new BufferedReader(arq);

        // Percorrer as linhas encontrando os atributos e atribuindo ao filme
        try {
            String linha = "";

            // Primeiro atributo: Nome, procura pelo primeiro h2 class
            while (!linha.contains("<h2 class"))
                linha = br.readLine();
            linha = br.readLine(); // Pula uma linha pois o conteúdo fica na próxima linha
            this.nome = removeTags(linha.trim()); // Remove as tags e espaços em branco

            // Segundo atributo: Data de lançamento, procura pelo próximo span class release
            while (!linha.contains("<span class=\"release\">"))
                linha = br.readLine();
            linha = br.readLine();
            String tempDate = linha.trim().split(" ")[0]; // Pega a primeira parte da string, que é apenas a data
            // Transforma a data em um objeto Date e atribui ao filme
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            this.dataLancamento = sdf.parse(tempDate);

            // Terceiro atributo: Genero, procura pelo próximo span class genre
            while (!linha.contains("<span class=\"genres\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine(); // Linha em branco
            this.genero = removeTags(linha.trim());

            // Quarto atributo: Duração, procura pelo próximo span class runtime
            while (!linha.contains("<span class=\"runtime\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine();
            this.duracao = getDuracao(linha);

            // Quinto atributo: Titulo original, procura pelo próximo a conter "Título
            // original"
            while (!linha.contains("<p class=\"wrap\"><strong>Título original</strong>")) {
                linha = br.readLine();
                // Caso não encontre e chegue ao próximo atributo, é porque não tem título
                // original
                if (linha.contains("<strong><bdi>Situação</bdi></strong>")) {
                    break; // Saia do loop
                }
            }
            // Se não tiver título original, atribui o nome do filme como título original
            if (linha.contains("<strong><bdi>Situação</bdi></strong>")) {
                this.tituloOriginal = this.nome;
            } else {
                this.tituloOriginal = removeTags(linha.replaceAll(
                        "<p class=\"wrap\"><strong>Título original</strong>", "").trim());
            }

            // Sexto atributo: Situação, procura pelo próximo a conter "Situação"
            while (!linha.contains("<strong><bdi>Situação</bdi></strong>"))
                linha = br.readLine();
            this.situacao = removeTags(linha.replaceAll(
                    "<strong><bdi>Situação</bdi></strong>", "").trim());

            // Sétimo atributo: Idioma original, procura pelo próximo a conter "Idioma
            // original"
            while (!linha.contains("<p><strong><bdi>Idioma original</bdi></strong>"))
                linha = br.readLine();
            this.idiomaOriginal = removeTags(linha.replaceAll(
                    "<strong><bdi>Idioma original</bdi></strong>", "").trim());

            // Oitavo atributo: Orçamento, procura pelo próximo a conter "Orçamento"
            while (!linha.contains("<p><strong><bdi>Orçamento</bdi></strong>"))
                linha = br.readLine();
            if (linha.contains("-")) { // Se não tiver orçamento, atribui 0
                this.orcamento = 0;
            } else {
                // Remove $ e vírgulas
                this.orcamento = Float.parseFloat(removeTags(linha.replaceAll(
                        "<strong><bdi>Orçamento</bdi></strong>", "").replace("$", "").replaceAll(",", "")
                        .replaceAll(" ", "").trim()));
            }

            // Nono atributo: Palavras-chave, procura os próximos <li> até </ul>
            String[] palavrasChaveTemp = new String[1000]; // Cria um array temporário para armazenar as palavras-chave
            int i = 0;
            while (!linha.contains("</ul>")) {
                linha = br.readLine();
                if (linha.contains("<li>")) { // Se a linha contiver <li>, é porque é uma palavra-chave
                    palavrasChaveTemp[i] = removeTags(linha.trim());
                    i++;
                }
                if (linha.contains("Nenhuma palavra-chave foi adicionada")) { // Se não tiver palavras-chave, saia do
                                                                              // loop
                    break;
                }
            }
            this.palavrasChave = new String[i]; // Cria um array com o tamanho do array temporário
            for (int j = 0; j < i; j++) {
                this.palavrasChave[j] = palavrasChaveTemp[j]; // Copia o array temporário para o array do filme
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
            throw new Exception("Erro ao ler arquivo " + html);
        } finally {
            arq.close(); // Fecha o arquivo
        }
    }
}

class No {
    // No da arvore comum, apenas direita e esquerda
    // E o conteudo dela (no caso, filme)
    Filme filme;
    No esq, dir;

    No(String html) throws Exception {
        this.filme = new Filme();
        this.filme.ler(html);

        this.esq = null;
        this.dir = null;
    }
}

class Arvore {
    // Arvore normal, mesma do Ex.1
    private No raiz;
    public int nComparacoes = 0;

    Arvore() {
        raiz = null;
    }

    // Metodos para facilitar a comparação na inserção, remoção e pesquisa
    // Compara titulo do filme de um html e o titulo de um nó
    private int compararPorHtml(String html, No no) throws Exception {
        Filme tmp = new Filme();
        tmp.ler(html);

        return no.filme.getTituloOriginal().compareTo(tmp.getTituloOriginal());
    }

    // Compara titulo do filme e o titulo de um nó
    private int compararPorChave(String tituloOriginal, No no) {
        return no.filme.getTituloOriginal().compareTo(tituloOriginal);
    }

    public void inserir(String html) throws Exception {
        raiz = inserir(html, raiz);
    }

    private No inserir(String html, No no) throws Exception {
        if (no == null) { // Caso seja raiz
            nComparacoes += 1;
            no = new No(html);
            // A partir daí, caso seja menor chama inserção pra esquerda
            // Caso seja maior, pra direita
        } else if (compararPorHtml(html, no) > 0) {
            nComparacoes += 2;
            no.esq = inserir(html, no.esq);
        } else if (compararPorHtml(html, no) < 0) {
            nComparacoes += 3;
            no.dir = inserir(html, no.dir);
        } else {
            nComparacoes += 3;
            throw new Exception("Erro ao inserir");
        }

        return no;
    }

    public void remover(String chave) throws Exception {
        raiz = remover(chave, raiz);
    }

    private No remover(String chave, No no) throws Exception {
        if (no == null) { // Se não existir o no
            nComparacoes += 1;
            throw new Exception("Erro ao remover");
            // Percorre assim como na inserção
        } else if (compararPorChave(chave, no) > 0) {
            nComparacoes += 2;
            no.esq = remover(chave, no.esq);
        } else if (compararPorChave(chave, no) < 0) {
            nComparacoes += 3;
            no.dir = remover(chave, no.dir);
        } else if (no.dir == null) { // Caso não tenha filho na direita
            nComparacoes += 4;
            no = no.esq; // Substitui pelo filho à esquerda, sobrescrevendo o antigo
        } else if (no.esq == null) {// Caso não tenha filho na esquerda
            nComparacoes += 5;
            no = no.dir;// Substitui pelo filho à direita, sobrescrevendo o antigo
        } else {
            nComparacoes += 5;
            no.esq = maiorEsq(no, no.esq); // Caso tenha os dois filhos, substitui pelo maior
            // na esquerda, para manter a ordem
        }

        return no;
    }

    private No maiorEsq(No i, No j) {
        // Percorre pela direita até a folha, onde será o maior
        nComparacoes += 1;
        if (j.dir == null) {
            i.filme = j.filme;
            j = j.esq;
        } else
            j.dir = maiorEsq(i, j.dir);

        return j;
    }

    public boolean pesquisar(String chave) {
        return pesquisar(chave, raiz);
    }

    private boolean pesquisar(String chave, No no) {
        boolean res;

        // Mesmo caminho de execução do inserir, porém, caso seja igual retorna true
        if (no == null) {
            nComparacoes += 1;
            res = false;
        } else if (compararPorChave(chave, no) == 0) {
            nComparacoes += 2;
            res = true;
        } else if (compararPorChave(chave, no) > 0) {
            nComparacoes += 3;
            System.out.print(" esq");
            res = pesquisar(chave, no.esq);
        } else {
            nComparacoes += 3;
            System.out.print(" dir");
            res = pesquisar(chave, no.dir);
        }

        return res;
    }

    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(No no) {
        if (no != null) {
            caminharCentral(no.esq);
            System.out.print(no.filme.getTituloOriginal() + " --- ");
            caminharCentral(no.dir);
        }
    }

}

// Nó da árvore de char
class NoChar {
    // Cada nó possui sua letra e a referencia para uma arvore
    char letra;
    NoChar esq, dir;
    Arvore arvore;

    NoChar(char letra) {
        this.letra = letra;

        this.esq = null;
        this.dir = null;

        this.arvore = new Arvore();
    }
}

class ArvoreChar {
    // Construtor, inserir e pesquisar iguais a da primeira árvore
    private NoChar raiz;
    public int nComparacoes = 0;

    ArvoreChar() {
        raiz = null;
    }

    public void inserir(char letra) throws Exception {
        raiz = inserir(letra, raiz);
    }

    private NoChar inserir(char letra, NoChar no) throws Exception {
        if (no == null) {
            nComparacoes += 1;
            no = new NoChar(letra);
        } else if (letra < no.letra) {
            nComparacoes += 2;
            no.esq = inserir(letra, no.esq);
        } else if (letra > no.letra) {
            nComparacoes += 3;
            no.dir = inserir(letra, no.dir);
        } else {
            nComparacoes += 3;
            throw new Exception("Erro ao inserir");
        }

        return no;
    }

    public boolean pesquisar(char letra) {
        return pesquisar(letra, raiz);
    }

    private boolean pesquisar(char letra, NoChar no) {
        boolean res;

        // Mesmo caminho de execução do inserir, porém, caso seja igual retorna true
        if (no == null) {
            nComparacoes += 1;
            res = false;
        } else if (letra == no.letra) {
            nComparacoes += 2;
            res = true;
        } else if (letra < no.letra) {
            nComparacoes += 3;
            res = pesquisar(letra, no.esq);
        } else {
            nComparacoes += 3;
            res = pesquisar(letra, no.dir);
        }

        return res;
    }

    // Para inserir um filme na árvore do nó de sua letra
    public void inserirNaArvore(String html) throws Exception {
        // Criar um filme para descobrir seu título
        Filme tmp = new Filme();
        tmp.ler(html);
        char letra = tmp.getTituloOriginal().charAt(0); // Primeira letra do título

        inserirNaArvore(letra, raiz, html); // Procura pela letra, insere na arvore dessa letra
    }

    private void inserirNaArvore(char letra, NoChar no, String html) throws Exception {
        // Igualzinho ao método de pesquisar, porém, quando for igual, ao invés de
        // retornar true
        // Ele acessa a árvore do nó e insere o filme nela
        if (no == null) {
            nComparacoes += 1;
            throw new Exception("Erro ao inserir");
        } else if (letra == no.letra) {
            nComparacoes += 2;
            no.arvore.inserir(html); // Diferença do pesquisa
        } else if (letra < no.letra) {
            nComparacoes += 3;
            inserirNaArvore(letra, no.esq, html);
        } else {
            nComparacoes += 3;
            inserirNaArvore(letra, no.dir, html);
        }
    }

    public boolean procurarNaArvore(String chave) throws Exception {
        System.out.print("raiz");
        return procurarNaArvore(raiz, chave);
    }

    private boolean procurarNaArvore(NoChar no, String chave) throws Exception {
        boolean res = false;

        if (no != null) { // Assim como o caminhar pré
            // Começando pela raiz, acessa a arvore dela, faz a pesquisa binária
            // E retorna a resposta, caso seja false, continuar a caminhar
            // E pesquisar em cada nó
            res = no.arvore.pesquisar(chave);
            if (!res) {
                System.out.print("  ESQ");
                res = procurarNaArvore(no.esq, chave);
                if (!res) {
                    System.out.print("  DIR");
                    res = procurarNaArvore(no.dir, chave);
                }
            }
        }

        return res;
    }

    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(NoChar no) {
        if (no != null) {
            caminharCentral(no.esq);
            System.out.print(no.letra + " ");
            caminharCentral(no.dir);
        }
    }

    // Método para colocar o total de comparações no log
    // Passa por cada árvore de cada nó para totalizar as comparações
    public int getComparacoes() {
        int res = this.nComparacoes;

        res += getComparacoes(raiz);

        return res;
    }

    private int getComparacoes(NoChar no) {
        int res = 0;
        if (no != null) {
            res += getComparacoes(no.esq);
            res += no.arvore.nComparacoes;
            res += getComparacoes(no.dir);
        }
        return res;
    }
}

public class arvoreArvore {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();

        // Inicializar a primeira arvore com as letras
        ArvoreChar arvoreChar = new ArvoreChar();
        char[] letras = { 'D', 'R',
                'Z', 'X', 'V', 'B', 'F', 'P', 'U', 'I', 'G', 'E', 'J', 'L', 'H', 'T', 'A', 'W', 'S', 'O', 'M', 'N', 'K',
                'C', 'Y', 'Q' };

        for (int i = 0; i < letras.length; i++) {
            try {
                arvoreChar.inserir(letras[i]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String entrada = "";

        // Fazer inserções da primeira parte
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                try {
                    arvoreChar.inserirNaArvore(entrada);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!entrada.equals("FIM"));

        // Inserções da segunda parte (com comando I)
        for (int i = sc.nextInt(); i > 0; i--) {
            entrada = sc.nextLine();
            if (entrada.length() < 1)
                entrada = sc.nextLine();

            if (entrada.charAt(0) == 'I') {
                try {
                    arvoreChar.inserirNaArvore(entrada.substring(2, entrada.length()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        // Fazer pesquisas
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                System.out.println("=> " + entrada);
                try {
                    System.out.print(arvoreChar.procurarNaArvore(entrada) ? "  SIM\n" : "  NAO\n");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!entrada.equals("FIM"));

        long tempoGasto = System.currentTimeMillis() - tempoInicial;
        try { // criação do log
            File log = new File("756293_arvoreArvore.txt");
            log.createNewFile(); // cria o arquivo no caminho atual
            FileWriter writer = new FileWriter(log); // escreve no arquivo, depois de criado
            writer.write("756293" + "\t" + tempoGasto + "\t" + arvoreChar.getComparacoes());
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao criar log");
        }
    }
}