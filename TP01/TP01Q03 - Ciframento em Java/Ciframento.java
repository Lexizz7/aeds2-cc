class Ciframento {
    public static boolean isFim(String s) { // Método para verificar se a string é "FIM"
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String cifrar(String texto) { // Método para cifrar a string
        String cifrado = "";
        int chave = 3; // Chave de cifragem

        for (int i = 0; i < texto.length(); i++) { // Para cada caracter da string, somar a chave
            char letra = texto.charAt(i);
            letra = (char) (letra + chave);
            cifrado += letra; // Depois de somar, adicionar a letra cifrada na string cifrada
        }

        return cifrado;
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