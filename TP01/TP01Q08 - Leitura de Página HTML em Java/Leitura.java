import java.io.*;
import java.net.*;

class Leitura {

    public static String getHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here

        }

        return resp;
    }

    // Método para testar se a string é FIM
    public static boolean isFIM(String expressao) {
        return (expressao.length() == 3 && expressao.charAt(0) == 'F' && expressao.charAt(1) == 'I'
                && expressao.charAt(2) == 'M');
    }

    public static int qntVogal(String expressao, String vogal) { // Recebe a vogal como string, pois vogais acentuadas
                                                                 // representam 2 caracteres
        int qnt = 0;
        int vogalInt = (int) vogal.charAt(0); // Converte para inteiro
        boolean tag = false; // Para caso esteja em <br> ou <table>
        if (vogalInt == 195) { // 195 é o código ASCII do primeiro char de uma vogal acentuada
            int vogalIntS = (int) vogal.charAt(1);
            for (int i = 0; i < expressao.length(); i++) {
                if ((int) expressao.charAt(i) == 195) {
                    if ((int) expressao.charAt(i + 1) == vogalIntS) { // i+1 deve ser o mesmo que vogalIntS
                        qnt++;
                    }
                }
            }
        } else {
            for (int i = 0; i < expressao.length(); i++) {
                // Verifica se está em <br> ou <table> e mudar o valor de tag
                if (expressao.charAt(i) == '<') {
                    if (expressao.charAt(i + 1) == 't' && expressao.charAt(i + 2) == 'a'
                            && expressao.charAt(i + 3) == 'b'
                            && expressao.charAt(i + 4) == 'l' && expressao.charAt(i + 5) == 'e'
                            && expressao.charAt(i + 6) == '>') {
                        tag = true;
                    }
                    if (expressao.charAt(i + 1) == 'b' && expressao.charAt(i + 2) == 'r'
                            && expressao.charAt(i + 3) == '>') {
                        tag = true;
                    }
                }
                if (tag && expressao.charAt(i) == '>') {
                    tag = false;
                }
                if (!tag && (int) expressao.charAt(i) == vogalInt) {
                    qnt++;
                }
            }
        }
        return qnt;
    }

    public static int qntConsoante(String expressao) {
        int qnt = 0;
        boolean tag = false; // Mesmo caso do qntVogal
        for (int i = 0; i < expressao.length(); i++) {
            if (expressao.charAt(i) == '<') {
                if (expressao.charAt(i + 1) == 't' && expressao.charAt(i + 2) == 'a'
                        && expressao.charAt(i + 3) == 'b'
                        && expressao.charAt(i + 4) == 'l' && expressao.charAt(i + 5) == 'e'
                        && expressao.charAt(i + 6) == '>') {
                    tag = true;
                }
                if (expressao.charAt(i + 1) == 'b' && expressao.charAt(i + 2) == 'r'
                        && expressao.charAt(i + 3) == '>') {
                    tag = true;
                }
            }
            if (tag && expressao.charAt(i) == '>') {
                tag = false;
            }
            // Não contabiliza as consoantes maiusculas, por isso 97 a 122
            if (!tag && ((int) expressao.charAt(i) >= 97 && (int) expressao.charAt(i) <= 122)) {
                if (!(expressao.charAt(i) == 'a' || expressao.charAt(i) == 'e' || expressao.charAt(i) == 'i'
                        || expressao.charAt(i) == 'o' || expressao.charAt(i) == 'u')) {
                    qnt++;
                }
            }
        }
        return qnt;
    }

    public static int qntBr(String expressao) {
        int qnt = 0;
        for (int i = 0; i < expressao.length(); i++) {
            // Espera um < e depois verifica as proximas letras
            if (expressao.charAt(i) == '<') {
                if (expressao.charAt(i + 1) == 'b' && expressao.charAt(i + 2) == 'r'
                        && expressao.charAt(i + 3) == '>') {
                    qnt++;
                }
            }
        }
        return qnt;
    }

    public static int qntTable(String expressao) {
        int qnt = 0;
        for (int i = 0; i < expressao.length(); i++) {
            // Espera um < e depois verifica as proximas letras
            if (expressao.charAt(i) == '<') {
                if (expressao.charAt(i + 1) == 't' && expressao.charAt(i + 2) == 'a' && expressao.charAt(i + 3) == 'b'
                        && expressao.charAt(i + 4) == 'l' && expressao.charAt(i + 5) == 'e'
                        && expressao.charAt(i + 6) == '>') {
                    qnt++;
                }
            }
        }
        return qnt;
    }

    public static void main(String[] args) {
        // Para printar acentos
        MyIO.setCharset("UTF-8");

        String nomeWeb = "";
        String linkWeb = "";
        String conteudoWeb = "";

        while (!isFIM(nomeWeb)) {
            nomeWeb = MyIO.readLine();
            if (!isFIM(nomeWeb)) {
                linkWeb = MyIO.readLine();
                conteudoWeb = getHtml(linkWeb);
                // Printar na ordem do enunciado
                MyIO.println("a(" + qntVogal(conteudoWeb, "a") + ") "
                        + "e(" + qntVogal(conteudoWeb, "e") + ") "
                        + "i(" + qntVogal(conteudoWeb, "i") + ") "
                        + "o(" + qntVogal(conteudoWeb, "o") + ") "
                        + "u(" + qntVogal(conteudoWeb, "u") + ") "
                        + "á(" + qntVogal(conteudoWeb, "á") + ") "
                        + "é(" + qntVogal(conteudoWeb, "é") + ") "
                        + "í(" + qntVogal(conteudoWeb, "í") + ") "
                        + "ó(" + qntVogal(conteudoWeb, "ó") + ") "
                        + "ú(" + qntVogal(conteudoWeb, "ú") + ") "
                        + "à(" + qntVogal(conteudoWeb, "à") + ") "
                        + "è(" + qntVogal(conteudoWeb, "è") + ") "
                        + "ì(" + qntVogal(conteudoWeb, "ì") + ") "
                        + "ò(" + qntVogal(conteudoWeb, "ò") + ") "
                        + "ù(" + qntVogal(conteudoWeb, "ù") + ") "
                        + "ã(" + qntVogal(conteudoWeb, "ã") + ") "
                        + "õ(" + qntVogal(conteudoWeb, "õ") + ") "
                        + "â(" + qntVogal(conteudoWeb, "â") + ") "
                        + "ê(" + qntVogal(conteudoWeb, "ê") + ") "
                        + "î(" + qntVogal(conteudoWeb, "î") + ") "
                        + "ô(" + qntVogal(conteudoWeb, "ô") + ") "
                        + "û(" + qntVogal(conteudoWeb, "û") + ") "
                        + "consoante(" + qntConsoante(conteudoWeb) + ") "
                        + "<br>(" + qntBr(conteudoWeb) + ") "
                        + "<table>(" + qntTable(conteudoWeb) + ") "
                        + nomeWeb);
            }
        }
    }
}
