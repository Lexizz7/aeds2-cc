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
    Filme filme;
    No esq, dir;
    boolean cor;

    No(String html) throws Exception {
        this.filme = new Filme();
        this.filme.ler(html);

        this.esq = null;
        this.dir = null;

        this.cor = false;
    }

    No(String html, boolean cor) throws Exception {
        this.filme = new Filme();
        this.filme.ler(html);

        this.esq = null;
        this.dir = null;

        this.cor = cor;
    }

    No(Filme filme) {
        this.filme = filme;

        this.esq = null;
        this.dir = null;

        this.cor = false;
    }
}

class Arvore {
    private No raiz;
    public int nComparacoes = 0;

    Arvore() {
        raiz = null;
    }

    // Metodos para facilitar a comparação na inserção, balanceamento e pesquisa
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

    // Compara titulo dos filmes entre dois nós
    private int compararPorNo(No a, No b) {
        return a.filme.getTituloOriginal().compareTo(b.filme.getTituloOriginal());
    }

    public void inserir(String html) throws Exception {
        if (raiz == null) {
            // Arvore vazia
            raiz = new No(html);
        } else if (raiz.esq == null && raiz.dir == null) {
            // Arvore somente com raiz
            if (compararPorHtml(html, raiz) > 0) {
                raiz.esq = new No(html);
            } else if (compararPorHtml(html, raiz) < 0) {
                raiz.dir = new No(html);
            } else {
                throw new Exception("Erro: valor repetido");
            }
        } else if (raiz.esq == null) {
            // Arvore com raiz e raiz.dir
            if (compararPorHtml(html, raiz) > 0) {
                raiz.esq = new No(html);
            } else if (compararPorHtml(html, raiz.dir) > 0) {
                raiz.esq = new No(raiz.filme);
                Filme tmp = new Filme();
                tmp.ler(html);
                raiz.filme = tmp;
            } else if (compararPorHtml(html, raiz.dir) < 0) {
                raiz.esq = new No(raiz.filme);
                raiz.filme = raiz.dir.filme;
                Filme tmp = new Filme();
                tmp.ler(html);
                raiz.dir.filme = tmp;
            } else {
                throw new Exception("Erro: valor repetido");
            }
        } else if (raiz.dir == null) {
            // Arvore com raiz e raiz.esq
            if (compararPorHtml(html, raiz) < 0) {
                raiz.dir = new No(html);
            } else if (compararPorHtml(html, raiz.esq) < 0) {
                raiz.dir = new No(raiz.filme);
                Filme tmp = new Filme();
                tmp.ler(html);
                raiz.filme = tmp;
            } else if (compararPorHtml(html, raiz.esq) > 0) {
                raiz.dir = new No(raiz.filme);
                raiz.filme = raiz.esq.filme;
                Filme tmp = new Filme();
                tmp.ler(html);
                raiz.esq.filme = tmp;
            } else {
                throw new Exception("Erro: valor repetido");
            }
        } else {
            // Arvore com tres filmes
            inserir(html, null, null, null, raiz);
        }
        raiz.cor = false; // Raiz nunca tem cor
    }

    private void inserir(String html, No bisavo, No avo, No pai, No no) throws Exception {
        if (no == null) { // Caso chegue em null = momento de inserir
            if (compararPorHtml(html, pai) > 0) {
                no = pai.esq = new No(html, true);
            } else if (compararPorHtml(html, pai) < 0) {
                no = pai.dir = new No(html, true);
            } else {
                throw new Exception("Erro: valor repetido");
            }

            if (pai.cor == true) { // Depois de inserir, verifica se precisa balancear
                balancear(bisavo, avo, pai, no);
            }
        } else {
            // Fragmentar 4-no (inverter as cores caso tenha)
            if (no.esq != null && no.dir != null && no.esq.cor == true && no.dir.cor == true) {
                no.cor = true;
                no.esq.cor = no.dir.cor = false;

                // Se o pai tem cor e o no não for raiz, está desbalanceado
                if (no == raiz) {
                    no.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, no);
                }
            }

            if (compararPorHtml(html, no) > 0) {
                inserir(html, avo, pai, no, no.esq);
            } else if (compararPorHtml(html, no) < 0) {
                inserir(html, avo, pai, no, no.dir);
            } else {
                throw new Exception("Erro: valor repetido");
            }
        }
    }

    private void balancear(No bisavo, No avo, No pai, No no) {
        if (pai.cor == true) {
            // Entrou no balanceamento

            if (compararPorNo(avo, pai) < 0) {
                // Avo e menor que pai
                if (compararPorNo(pai, no) < 0) {
                    // Pai e menor que no = RotacionarEsq
                    avo = rotacionarEsq(avo);
                } else {
                    // Pai e maior que no = RotacionarDirEsq
                    avo.dir = rotacionarDir(avo.dir);
                    avo = rotacionarEsq(avo);
                }
            } else {
                // Avo e maior que pai
                if (compararPorNo(pai, no) > 0) {
                    // Pai e maior que no = RotacionarDir
                    avo = rotacionarDir(avo);
                } else {
                    // Pai e menor que no = RotacionarEsqDir
                    avo.esq = rotacionarEsq(avo.esq);
                    avo = rotacionarDir(avo);
                }
            }
            // Saiu das rotacoes

            if (bisavo == null) {
                raiz = avo;
            } else if (compararPorNo(bisavo, avo) > 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }

            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        }
    }

    private No rotacionarEsq(No no) {
        // Guarda as referencias do no.dir e no.dir.esq
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        // noDir aponta pro no
        noDir.esq = no;
        // no.dir aponta pro noDirEsq
        no.dir = noDirEsq;

        return noDir;
    }

    private No rotacionarDir(No no) {
        // Mesmo raciocínio do rotacionarEsq
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    public boolean pesquisar(String chave) {
        System.out.print("raiz");
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

public class Alvinegra {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();

        Arvore arvore = new Arvore();
        String entrada = "";

        // Fazer inserções
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                try {
                    arvore.inserir(entrada);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!entrada.equals("FIM"));

        // Fazer pesquisas
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                System.out.println(entrada);
                System.out.print(arvore.pesquisar(entrada) ? " SIM\n" : " NAO\n");
            }
        } while (!entrada.equals("FIM"));

        long tempoGasto = System.currentTimeMillis() - tempoInicial;
        try { // criação do log
            File log = new File("756293_alvinegra.txt");
            log.createNewFile(); // cria o arquivo no caminho atual
            FileWriter writer = new FileWriter(log); // escreve no arquivo, depois de criado
            writer.write("756293" + "\t" + tempoGasto + "\t" + arvore.nComparacoes);
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao criar log");
        }
    }
}