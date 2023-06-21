import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

class Celula {
    Filme elemento;
    Celula prox;

    Celula() {
        // Cria um filme vazio como inicial
        this.elemento = new Filme();
        this.prox = null;
    }

    Celula(String html) throws Exception {
        // Cria um filme e lê os dados do html
        this.elemento = new Filme();
        this.elemento.ler(html);
        this.prox = null;
    }
}

// Lista dinâmica
class Lista {
    private Celula primeiro, ultimo;
    private int tamanho;

    Lista() throws Exception {
        // Por padrão, o primeiro é uma célula vazia que não será mostrada
        // Lista vazia -> primeiro = ultimo
        primeiro = new Celula();
        ultimo = primeiro;
        tamanho = 0;
    }

    void inserirInicio(String html) throws Exception {
        // Cria a célula com o filme desejado
        Celula tmp = new Celula(html);

        tmp.prox = primeiro.prox; // Faz o próximo elemento ser o primeiro elemento antigo
        primeiro.prox = tmp; // Faz a sentinela apontar para a nova célula

        if (primeiro == ultimo) // Condição caso a lista esteja vazia, o inserido será o ultimo
            ultimo = tmp;

        tmp = null;
        tamanho++;
    }

    void inserirFim(String html) throws Exception {
        // Cria a célula com o filme desejado
        Celula tmp = new Celula(html);

        ultimo.prox = tmp; // Faz o ultimo apontar pro "novo ultimo"
        ultimo = tmp; // move a referencia do ultimo para esse criado

        tmp = null;
        tamanho++;
    }

    void inserir(String html, int pos) throws Exception {
        // Verificar caso a posição seja 0 ou a final, chamar os respectivos métodos
        if (pos < 0 || pos > tamanho)
            throw new Exception("Posicao invalida.");
        else if (pos == 0)
            inserirInicio(html);
        else if (pos == tamanho)
            inserirFim(html);
        else {
            // Caso não seja, percorrer até a celula de posição anterior
            Celula i = primeiro;
            Celula tmp = new Celula(html);

            for (int j = 0; j < pos; i = i.prox, j++)
                ;

            // tmp -> a ser inserido
            // i -> celula anterior
            tmp.prox = i.prox;
            i.prox = tmp;

            tmp = null;
            tamanho++;
        }
    }

    Filme removerInicio() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Nada para remover.");

        Celula tmp = primeiro; // Guarda referencia do primeiro

        // Como o primeiro é sentinela e nunca é usado, move para frente a referencia do
        // primeiro
        primeiro = primeiro.prox;
        Filme res = primeiro.elemento;

        // O "primeiro antigo" que ficou salvo, liberar da memória
        tmp.prox = null;
        tmp = null;

        return res;
    }

    Filme removerFim() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Nada para remover.");

        Filme res = ultimo.elemento; // Retornar o elemento da ultima celula

        // Percorrer até a celula anterior à última
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox)
            ;

        // Faz com que o último aponte para essa celula anterior
        ultimo = i;
        // Apaga referencia ao proximo, que era o ultimo de antes
        ultimo.prox = null;
        i = null;

        tamanho--;

        return res;
    }

    Filme remover(int pos) throws Exception {
        Filme res;
        // Verifica caso seja primeira ou ultima posição
        if (pos >= tamanho || pos < 0 || primeiro == ultimo)
            throw new Exception("Nada para remover.");
        else if (pos == 0)
            res = removerInicio();
        else if (pos == tamanho - 1)
            res = removerFim();
        else {
            // Percorre até a celular anterior a que será removida
            Celula i = primeiro;
            for (int j = 0; j < pos; i = i.prox, j++)
                ;

            // tmp -> será removido e retornado o elemento
            // i -> celula anterior
            Celula tmp = i.prox;
            res = tmp.elemento;

            // faz o proximo de i apontar para o proximo de tmp, pulando tmp
            i.prox = tmp.prox;
            tmp.prox = null;
            tmp = null;
            i = null;

            tamanho--;
        }
        return res;
    }

    void mostrar() throws Exception {
        int j = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, j++) {
            System.out.print("[" + j + "] ");
            i.elemento.imprimir();
        }
    }
}

class ListaDinamica {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Lista filmes = new Lista();
            String entrada = "";

            PrintStream out = new PrintStream(System.out, true, "UTF-8"); // Imprime no formato UTF-8 para evitar
                                                                          // problemas de acentuação
            // Primeira parte da entrada (nome dos html)
            do {
                entrada = sc.nextLine();
                if (!entrada.equals("FIM")) {
                    // Preencher a lista
                    filmes.inserirFim(entrada);
                }
            } while (!entrada.equals("FIM"));

            // Segunda parte, com um número dado de linhas
            for (int i = sc.nextInt(); i >= 0; i--) {
                entrada = sc.nextLine();

                // Separa a string e pegar o comando
                switch (entrada.split(" ")[0]) {
                    case "II":
                        // Divide a string em 2 partes, onde a primeira é o comando
                        filmes.inserirInicio(entrada.split(" ", 2)[1]);
                        break;
                    case "IF":
                        filmes.inserirFim(entrada.split(" ", 2)[1]);
                        break;
                    case "I*":
                        // Divide a string em 3 partes, onde a primeira é o comando e a segunda é a
                        // posição
                        filmes.inserir(entrada.split(" ", 3)[2], Integer.parseInt(entrada.split(" ", 3)[1]));
                        break;
                    case "RI":
                        // Retorna apenas o nome daquele que foi removido
                        out.println("(R) " + filmes.removerInicio().getNome());
                        break;
                    case "RF":
                        out.println("(R) " + filmes.removerFim().getNome());
                        break;
                    case "R*":
                        out.println("(R) " + filmes.remover(Integer.parseInt(entrada.split(" ")[1])).getNome());
                        break;
                }
            }

            // Depois das inserções e remoções, mostrar os elementos
            filmes.mostrar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
