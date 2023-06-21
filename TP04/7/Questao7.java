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
        // Utilizando o m�todo set para inicializar os atributos
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

    // M�todos get e set
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
        return idiomaOriginal.trim(); // Retorna o idioma original sem espa�os em branco
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

    public Filme clone() { // M�todo clone
        return new Filme(this.nome, this.tituloOriginal, this.dataLancamento, this.duracao, this.genero,
                this.idiomaOriginal, this.situacao, this.orcamento, this.palavrasChave); // Retorna um novo filmes com
                                                                                         // os mesmos dados
    }

    public void imprimir() throws Exception { // Mostrar os dados do filme no formato especificado no enunciado
        PrintStream out = new PrintStream(System.out, true, "UTF-8"); // Imprime no formato UTF-8 para evitar problemas
                                                                      // de acentua��o
        try {
            // Printa utilizando os m�todos get
            out.print(this.getNome() + " ");
            out.print(this.getTituloOriginal() + " ");
            out.print(this.getDataLancamento() + " ");
            out.print(this.getDuracao() + " ");
            out.print(this.getGenero() + " ");
            out.print(this.getIdiomaOriginal() + " ");
            out.print(this.getSituacao() + " ");
            out.print(this.getOrcamento() + " ");
            out.print("[");
            out.print(String.join(", ", this.palavrasChave)); // Separa as palavras chave por v�rgula e transforma em
                                                              // uma string
            out.print("]\n");
        } catch (Exception e) {
            throw new Exception("Erro ao imprimir o filme");
        }
    }

    private String removeTags(String text) { // Al�m de tags, remove os caracteres estranhos que estavam
                                             // no HTML
        String res = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') { // ao abrir uma tag, percorrer pulando o "i" at� fechar a tag
                while (text.charAt(i) != '>') {
                    i++;
                }
            }
            if (text.charAt(i) != '>') {// como depois de retirar a tag o i == '>', o if serve para n�o aparecer
                                        // ">string"
                res += text.charAt(i);
            }
        }
        return res.replaceAll("&nbsp;", "");
    }

    private int getDuracao(String text) {
        text = text.trim(); // Garatir que n�o h� espa�os em branco
        String[] parts = text.split(" "); // Divide a string em partes (horas e minutos)
        int duracao = 0;
        if (parts.length == 2) { // Se for dividido por 2, � porque tem horas e minutos
            int horas = Integer.parseInt(parts[0].replace("h", "")); // Remove o h da string e converte para int
            int minutos = Integer.parseInt(parts[1].replace("m", ""));
            duracao = horas * 60 + minutos;
        } else if (parts.length == 1) { // Se for dividido por 1, � porque s� tem minutos
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

    public void ler(String html) throws Exception { // L� um arquivo HTML e inicializa os atributos do filme
        FileReader arq = new FileReader("/tmp/filmes/" + html);
        BufferedReader br = new BufferedReader(arq);

        // Percorrer as linhas encontrando os atributos e atribuindo ao filme
        try {
            String linha = "";

            // Primeiro atributo: Nome, procura pelo primeiro h2 class
            while (!linha.contains("<h2 class"))
                linha = br.readLine();
            linha = br.readLine(); // Pula uma linha pois o conte�do fica na pr�xima linha
            this.nome = removeTags(linha.trim()); // Remove as tags e espa�os em branco

            // Segundo atributo: Data de lan�amento, procura pelo pr�ximo span class release
            while (!linha.contains("<span class=\"release\">"))
                linha = br.readLine();
            linha = br.readLine();
            String tempDate = linha.trim().split(" ")[0]; // Pega a primeira parte da string, que � apenas a data
            // Transforma a data em um objeto Date e atribui ao filme
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            this.dataLancamento = sdf.parse(tempDate);

            // Terceiro atributo: Genero, procura pelo pr�ximo span class genre
            while (!linha.contains("<span class=\"genres\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine(); // Linha em branco
            this.genero = removeTags(linha.trim());

            // Quarto atributo: Dura��o, procura pelo pr�ximo span class runtime
            while (!linha.contains("<span class=\"runtime\">"))
                linha = br.readLine();
            linha = br.readLine();
            linha = br.readLine();
            this.duracao = getDuracao(linha);

            // Quinto atributo: Titulo original, procura pelo pr�ximo a conter "T�tulo
            // original"
            while (!linha.contains("<p class=\"wrap\"><strong>T�tulo original</strong>")) {
                linha = br.readLine();
                // Caso n�o encontre e chegue ao pr�ximo atributo, � porque n�o tem t�tulo
                // original
                if (linha.contains("<strong><bdi>Situa��o</bdi></strong>")) {
                    break; // Saia do loop
                }
            }
            // Se n�o tiver t�tulo original, atribui o nome do filme como t�tulo original
            if (linha.contains("<strong><bdi>Situa��o</bdi></strong>")) {
                this.tituloOriginal = this.nome;
            } else {
                this.tituloOriginal = removeTags(linha.replaceAll(
                        "<p class=\"wrap\"><strong>T�tulo original</strong>", "").trim());
            }

            // Sexto atributo: Situa��o, procura pelo pr�ximo a conter "Situa��o"
            while (!linha.contains("<strong><bdi>Situa��o</bdi></strong>"))
                linha = br.readLine();
            this.situacao = removeTags(linha.replaceAll(
                    "<strong><bdi>Situa��o</bdi></strong>", "").trim());

            // S�timo atributo: Idioma original, procura pelo pr�ximo a conter "Idioma
            // original"
            while (!linha.contains("<p><strong><bdi>Idioma original</bdi></strong>"))
                linha = br.readLine();
            this.idiomaOriginal = removeTags(linha.replaceAll(
                    "<strong><bdi>Idioma original</bdi></strong>", "").trim());

            // Oitavo atributo: Or�amento, procura pelo pr�ximo a conter "Or�amento"
            while (!linha.contains("<p><strong><bdi>Or�amento</bdi></strong>"))
                linha = br.readLine();
            if (linha.contains("-")) { // Se n�o tiver or�amento, atribui 0
                this.orcamento = 0;
            } else {
                // Remove $ e v�rgulas
                this.orcamento = Float.parseFloat(removeTags(linha.replaceAll(
                        "<strong><bdi>Or�amento</bdi></strong>", "").replace("$", "").replaceAll(",", "")
                        .replaceAll(" ", "").trim()));
            }

            // Nono atributo: Palavras-chave, procura os pr�ximos <li> at� </ul>
            String[] palavrasChaveTemp = new String[1000]; // Cria um array tempor�rio para armazenar as palavras-chave
            int i = 0;
            while (!linha.contains("</ul>")) {
                linha = br.readLine();
                if (linha.contains("<li>")) { // Se a linha contiver <li>, � porque � uma palavra-chave
                    palavrasChaveTemp[i] = removeTags(linha.trim());
                    i++;
                }
                if (linha.contains("Nenhuma palavra-chave foi adicionada")) { // Se n�o tiver palavras-chave, saia do
                                                                              // loop
                    break;
                }
            }
            this.palavrasChave = new String[i]; // Cria um array com o tamanho do array tempor�rio
            for (int j = 0; j < i; j++) {
                this.palavrasChave[j] = palavrasChaveTemp[j]; // Copia o array tempor�rio para o array do filme
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

class Celula {
    Filme elemento;
    Celula prox;

    Celula() {
        // Cria um filme vazio como inicial
        this.elemento = new Filme();
        this.prox = null;
    }

    Celula(String html) throws Exception {
        // Cria um filme e l� os dados do html
        this.elemento = new Filme();
        this.elemento.ler(html);
        this.prox = null;
    }
}

// Lista din�mica
class Lista {
    private Celula primeiro, ultimo;
    private int tamanho;

    Lista() {
        // Por padr�o, o primeiro � uma c�lula vazia que n�o ser� mostrada
        // Lista vazia -> primeiro = ultimo
        primeiro = new Celula();
        ultimo = primeiro;
        tamanho = 0;
    }

    public void inserirFim(String html) throws Exception {
        // Cria a c�lula com o filme desejado
        Celula tmp = new Celula(html);

        ultimo.prox = tmp; // Faz o ultimo apontar pro "novo ultimo"
        ultimo = tmp; // move a referencia do ultimo para esse criado

        tmp = null;
        tamanho++;
    }

    public boolean pesquisar(String chave) {
        boolean res = false;

        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            if (i.elemento.getTituloOriginal().equals(chave)) {
                res = true;
            }
        }

        return res;
    }
}

class Hash {
    // Tabela com lista din�mica
    private Lista[] tabela;

    Hash() {
        this(21);
    }

    Hash(int tamanho) {
        this.tabela = new Lista[tamanho];
        // Inicializar tabela
        for (int i = 0; i < tabela.length; tabela[i] = new Lista(), i++)
            ;
    }

    // Soma todos os caracteres e atribui a uma posi��o dentro do intervalo
    // 0-tamanho
    public int hash(String string) {
        int res = 0;
        for (int i = 0; i < string.length(); res += string.charAt(i), i++)
            ;
        return res % tabela.length;
    }

    // Recebe o html, cria o filme e insere na lista da posi��o hash
    public void inserir(String html) throws Exception {
        Filme tmp = new Filme();
        tmp.ler(html);

        int pos = hash(tmp.getTituloOriginal());

        tabela[pos].inserirFim(html);
    }

    // Vai direto na posi��o retornada pela fun��o hash
    // E procura na lista
    public int indexOf(String chave) {
        int pos = hash(chave);
        int res = -1;

        if (tabela[pos].pesquisar(chave) == true) {
            res = pos;
        }

        return res;
    }
}

public class Questao7 {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Hash tabela = new Hash();
        String entrada;

        // Primeira parte: inser��es
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                try {
                    tabela.inserir(entrada);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!entrada.equals("FIM"));

        // Segunda parte: pesquisa
        do {
            entrada = sc.nextLine();
            if (!entrada.equals("FIM")) {
                System.out.println("=> " + entrada);
                System.out.println(tabela.indexOf(entrada) >= 0 ? "Posicao: " + tabela.indexOf(entrada) : "NAO");
            }
        } while (!entrada.equals("FIM"));
    }
}
