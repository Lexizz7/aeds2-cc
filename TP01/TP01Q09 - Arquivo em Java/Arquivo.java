import java.io.RandomAccessFile;

class Arquivo {
    // Método lê o arquivo, e percorre caracter a caracter para encontrar a posição
    // onde ocorre uma nova linha
    public static long posUltimaLinha(RandomAccessFile arquivo, long posicao) throws Exception {
        arquivo.seek(posicao); // Começa na posição passada
        char caracter = (char) arquivo.read();
        while (caracter != '\n' && posicao != 0) { // E enquanto não houver uma quebra de linha, continuar voltando
            posicao--;
            arquivo.seek(posicao);
            caracter = (char) arquivo.read();
        }
        posicao--; // Diminui 1 para não cair exatamente na quebra de linha

        return posicao;
    }

    // Método para formatar a string e mostrar os números em Int ou Float
    public static String converterNumeros(String linha) {
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '.') { // Caso houver . é float
                return Float.toString(Float.parseFloat(linha));
            }
        }
        return Integer.toString(Integer.parseInt(linha));
    }

    public static void main(String args[]) throws Exception {
        int n = MyIO.readInt(); // Quantidade de números
        Arq.openWrite("arquivo.txt", "UTF-8");

        // Para cada linha da entrada além de N, escrever no arquivo
        for (int i = 0; i < n; i++) {
            Arq.println(MyIO.readLine());
        }
        Arq.close();

        RandomAccessFile arq = new RandomAccessFile("arquivo.txt", "r");

        // Pegar última posição
        long pos = arq.length() - 1;

        for (int i = 0; i <= n; i++) {
            // Chamar a função para encontrar o começo da linha
            pos = posUltimaLinha(arq, pos);
            if (i == n) { // Caso seja o último número, ir para a próxima posição para não ser uma posição
                          // negativa
                arq.seek(pos + 1);
            } else {
                arq.seek(pos + 2); // Somar 2 para não pegar o \n
            }
            if (i > 0) {
                MyIO.println(converterNumeros(arq.readLine()));
            }
        }

        arq.close();
    }
}
