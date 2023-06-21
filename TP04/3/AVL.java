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
            linha = br.readLine(); // Pula uma linha pois o conteudo fica na proxima linha
            this.nome = removeTags(linha.trim()); // Remove as tags e espacos em branco

            // Segundo atributo: Data de lancamento, procura pelo proximo span class release
            while (!linha.contains("<span class=\"release\">"))
                linha = br.readLine();
            linha = br.readLine();
            String tempDate = linha.trim().split(" ")[0]; // Pega a primeira parte da string, que e apenas a data
            // Transforma a data em um objeto Date e atribui ao filme
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            this.dataLancamento = sdf.parse(tempDate);

            // Terceiro atributo: Genero, procura pelo proximo span class genre
            while (!linha.contains("<span class=\"genres\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine(); // Linha em branco
            this.genero = removeTags(linha.trim());

            // Quarto atributo: Duracao, procura pelo proximo span class runtime
            while (!linha.contains("<span class=\"runtime\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine();
            this.duracao = getDuracao(linha);

            // Quinto atributo: Titulo original, procura pelo proximo a conter "Título
            // original"
            while (!linha.contains("<p class=\"wrap\"><strong>Título original</strong>")) {
                linha = br.readLine();
                // Caso não encontre e chegue ao proximo atributo, é porque não tem título
                // original
                if (linha.contains("<strong><bdi>Situação</bdi></strong>")) {
                    break; // Saia do loop
                }
            }
            // Se nao tiver titulo original, atribui o nome do filme como titulo original
            if (linha.contains("<strong><bdi>Situação</bdi></strong>")) {
                this.tituloOriginal = this.nome;
            } else {
                this.tituloOriginal = removeTags(linha.replaceAll(
                        "<p class=\"wrap\"><strong>Título original</strong>", "").trim());
            }

            // Sexto atributo: Situação, procura pelo proximo a conter "Situação"
            while (!linha.contains("<strong><bdi>Situação</bdi></strong>"))
                linha = br.readLine();
            this.situacao = removeTags(linha.replaceAll(
                    "<strong><bdi>Situação</bdi></strong>", "").trim());

            // Setimo atributo: Idioma original, procura pelo proximo a conter "Idioma
            // original"
            while (!linha.contains("<p><strong><bdi>Idioma original</bdi></strong>"))
                linha = br.readLine();
            this.idiomaOriginal = removeTags(linha.replaceAll(
                    "<strong><bdi>Idioma original</bdi></strong>", "").trim());

            // Oitavo atributo: Orcamento, procura pelo proximo a conter "Orçamento"
            while (!linha.contains("<p><strong><bdi>Orçamento</bdi></strong>"))
                linha = br.readLine();
            if (linha.contains("-")) { // Se nao tiver orçamento, atribui 0
                this.orcamento = 0;
            } else {
                // Remove $ e virgulas
                this.orcamento = Float.parseFloat(removeTags(linha.replaceAll(
                        "<strong><bdi>Orçamento</bdi></strong>", "").replace("$", "").replaceAll(",", "")
                        .replaceAll(" ", "").trim()));
            }

            // Nono atributo: Palavras-chave, procura os proximos <li> ate </ul>
            String[] palavrasChaveTemp = new String[1000]; // Cria um array tempororio para armazenar as palavras-chave
            int i = 0;
            while (!linha.contains("</ul>")) {
                linha = br.readLine();
                if (linha.contains("<li>")) { // Se a linha contiver <li>, e porque e uma palavra-chave
                    palavrasChaveTemp[i] = removeTags(linha.trim());
                    i++;
                }
                if (linha.contains("Nenhuma palavra-chave foi adicionada")) { // Se nao tiver palavras-chave, saia do
                                                                              // loop
                    break;
                }
            }
            this.palavrasChave = new String[i]; // Cria um array com o tamanho do array temporario
            for (int j = 0; j < i; j++) {
                this.palavrasChave[j] = palavrasChaveTemp[j]; // Copia o array temporario para o array do filme
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
    // No da arvore AVL, possui direita, esquerda, seu fator
    // E o conteudo dela (filme)
    public Filme filme;
    public No esq, dir;
    public int fator;

    No(String html) throws Exception {
        this.filme = new Filme();
        this.filme.ler(html);

        this.esq = null;
        this.dir = null;

        this.fator = 1;
    }

    public void setFator() {
        this.fator = 1 + Math.max(getFator(esq), getFator(dir));
    }

    public static int getFator(No no) {
        return (no == null) ? 0 : no.fator;
    }
}

class Arvore {
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
        if (no == null) {
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

        return balancear(no); // Retorna balanceado
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

        return balancear(no);
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

    private No balancear(No no) throws Exception {
        if (no != null) {
            int fator = No.getFator(no.dir) - No.getFator(no.esq);

            // Se estiver balanceada, seta o fator e retorna
            if (Math.abs(fator) <= 1) {
                nComparacoes++;
                no.setFator();
                // Se estiver desbalanceada para direita
            } else if (fator == 2) {
                nComparacoes += 2;
                // Calcular o fator do filho a direita
                // Para saber se é rotação simples ou dupla
                int fatorDir = No.getFator(no.dir.dir) - No.getFator(no.dir.esq);
                if (fatorDir == -1)
                    no.dir = rotacionarDir(no.dir);
                no = rotacionarEsq(no);
                // Se estiver desbalanceado para esquerda
            } else if (fator == -2) {
                // Mesmo raciocínio
                nComparacoes += 3;
                int fatorEsq = No.getFator(no.esq.dir) - No.getFator(no.esq.esq);
                if (fatorEsq == 1)
                    no.esq = rotacionarEsq(no.esq);
                no = rotacionarDir(no);
            } else {
                nComparacoes += 3;
                throw new Exception("Erro no balanceamento");
            }
        }
        return no;
    }

    private No rotacionarEsq(No no) {
        // Guarda as referencias do no.dir e no.dir.esq
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        // noDir aponta pro no
        noDir.esq = no;
        // no.dir aponta pro noDirEsq
        no.dir = noDirEsq;

        no.setFator();
        noDir.setFator();

        return noDir;
    }

    private No rotacionarDir(No no) {
        // Mesmo raciocínio do rotacionarEsq
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;

        no.esq = noEsqDir;
        no.setFator();
        noEsq.setFator();

        return noEsq;
    }
}

public class AVL {
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
                    e.printStackTrace();
                }
            }
        } while (!entrada.equals("FIM"));

        // Inserir ou remover
        for (int i = sc.nextInt(); i > 0; i--) {
            entrada = sc.nextLine();
            if (entrada.length() < 1)
                entrada = sc.nextLine();

            if (entrada.charAt(0) == 'I') {
                try {
                    arvore.inserir(entrada.substring(2, entrada.length()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (entrada.charAt(0) == 'R') {
                try {
                    arvore.remover(entrada.substring(2, entrada.length()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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
            File log = new File("756293_avl.txt");
            log.createNewFile(); // cria o arquivo no caminho atual
            FileWriter writer = new FileWriter(log); // escreve no arquivo, depois de criado
            writer.write("756293" + "\t" + tempoGasto + "\t" + arvore.nComparacoes);
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao criar log");
        }
    }
}