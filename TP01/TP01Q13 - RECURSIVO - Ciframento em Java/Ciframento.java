class Ciframento {

    // Chamar a função cifrar de forma recursiva
    public static String cifrar(String texto) {
        return cifrar(texto, 0);
    }

    // Método recursivo para cifrar a mensagem
    public static String cifrar(String texto, int i) {
        String cifrado = "";
        int chave = 3;

        if (i < texto.length()) {
            // Somar a chave a letra
            cifrado += (char) (texto.charAt(i) + chave);
            // Chamar a função com a próxima letra
            cifrado += cifrar(texto, i + 1);
        }

        return cifrado;
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
            // Para cada linha da entrada, cifrar e imprimir
            MyIO.println(cifrar(entrada[j]));
        }
    }
}