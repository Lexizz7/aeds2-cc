import java.util.Random;

class Aleatorio {

    public static String alterar(String texto, int pos) { // Recebe uma string para alterar e uma posição para continuar
                                                          // a sequencia
        String saida = "";
        Random gerador = new Random();
        char letra1 = 'a', letra2 = 'a'; // Define as duas letras a serem trocadas

        gerador.setSeed(4);

        for (int i = 0; i <= pos; i++) { // Para cada nova entrada, gerar uma letra seguindo a sequência de letras da
                                         // seed
            letra1 = ((char) ('a' + (Math.abs(gerador.nextInt()) % 26))); // Isso é necessário pois sempre que o método
                                                                          // é chamado a seed volta a posição 0
            letra2 = ((char) ('a' + (Math.abs(gerador.nextInt()) % 26)));// Gerando as mesmas letras sempre
        }

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i); // Para cada caracter da string, trocar as letras definidas pelo gerador
            if (letra == letra1) {
                letra = letra2;
            }
            saida += letra;
        }

        return saida;
    }

    public static boolean isFim(String s) { // Método para verificar se a string é "FIM"
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int i = 0; // Index da entrada

        // Leitura da entrada padrao
        do {
            entrada[i] = MyIO.readLine();
        } while (isFim(entrada[i++]) == false);
        i--; // Desconsiderar ultima linha contendo a palavra FIM

        for (int j = 0; j < i; j++) {
            // Para cada linha da entrada, alterar e imprimir
            // É preciso usar a posição como parâmetro para continuar a sequência de letras
            MyIO.println(alterar(entrada[j], j));
        }
    }
}
