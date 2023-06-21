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

class Lista {
    Filme[] array;
    int n;

    Lista() {
        this(30);
    }

    Lista(int tamanho) {
        array = new Filme[tamanho];
        n = 0;
    }

    void inserirInicio(String html) throws Exception {
        // Erro caso não tenha mais onde inserir
        if (n >= array.length)
            throw new Exception("Sem espaco no array");

        // Deslocar todos os existentes pra frente
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        // Criar filme e ler os dados
        Filme x = new Filme();
        x.ler(html);

        // Adicionar na primeira posição
        array[0] = x;
        n++;
    }

    void inserirFim(String html) throws Exception {
        // Erro caso não tenha mais onde inserir
        if (n >= array.length)
            throw new Exception("Sem espaco no array");

        // Criar filme e ler os dados
        Filme x = new Filme();
        x.ler(html);

        // Adicionar na última posição (n)
        array[n] = x;
        n++;
    }

    void inserir(String html, int pos) throws Exception {
        // Erro caso a posição não exista e não tenha onde inserir
        if (pos > n || pos < 0 || n >= array.length)
            throw new Exception("Posicao fora do range ou sem espaco no array");

        // Deslocar pra frente só até a posição desejada
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }

        // Criar filme e ler os dados
        Filme x = new Filme();
        x.ler(html);

        // Adicionar na posição
        array[pos] = x;
        n++;
    }

    Filme removerInicio() throws Exception {
        // Erro caso não tenha nenhum elemento
        if (n <= 0)
            throw new Exception("Nada para remover");

        // Salvar o filme do inicio, que será removido
        Filme tmp = array[0];
        n--;

        // Desloca todos para a esquerda, sobrescrevendo o inicio
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }

        // Retorna o filme salvo antes de deslocar
        return tmp;
    }

    Filme removerFim() throws Exception {
        if (n <= 0)
            throw new Exception("Nada para remover");

        // Retornar o elemento na posição final (n-1)
        Filme tmp = array[n - 1];
        n--;

        return tmp;
    }

    Filme remover(int pos) throws Exception {
        if (pos >= n || pos < 0 || n <= 0)
            throw new Exception("Posicao fora do range ou nada para remover");

        // Salvar o filme da posição
        Filme tmp = array[pos];
        n--;

        // Deslocar a partir da posição dada para sobrescrever o removido
        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
        }

        return tmp;
    }

    void mostrar() throws Exception {
        for (int i = 0; i < n; i++) {
            array[i].imprimir();
        }
    }

    void swap(int pos1, int pos2) {
        Filme tmp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = tmp;
    }

    void ordenar() throws Exception {
        File log = new File("756293_bolha.txt");
        log.createNewFile();
        int comparacoes = 0;
        int movimentacoes = 0;

        // Ordenar por bubblesort
        long tempoInicial = System.currentTimeMillis();
        // i = tamanho do vetor ordenado
        // j = posição da bolha
        // funcionamento: percorre da ultima posição com a bolha
        // passando ela pro menor valor encontrado no caminho
        // até chegar em i.
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j > i; j--) {
                comparacoes++;
                if (array[j].getDuracao() < array[j - 1].getDuracao() ||
                        (array[j].getDuracao() == array[j - 1].getDuracao()
                                && array[j].getNome().compareTo(array[j - 1].getNome()) < 0)) {
                    movimentacoes++;
                    swap(j, j - 1);
                }
            }
        }
        long tempoGasto = System.currentTimeMillis() - tempoInicial;

        FileWriter writer = new FileWriter(log);
        writer.write("756293\t" + comparacoes + "\t" + movimentacoes + "\t" + tempoGasto);
        writer.close();
    }
}

class Bubble {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Lista filmes = new Lista(100);
        String entrada = "";

        try {
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

            filmes.ordenar();
            filmes.mostrar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
