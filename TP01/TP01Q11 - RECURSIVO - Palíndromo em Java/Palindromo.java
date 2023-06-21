class Palindromo {

	// Chamar a função palindromo
	public static boolean isPalindromo(String word) {
		return isPalindromo(word, 0);
	}

	// Método recursivo que verifica se uma string é palíndroma
	public static boolean isPalindromo(String word, int i) {// Recebe index 0 como começo
		boolean resposta = true;

		if (i < word.length()) {// Caso seja diferente do seu oposto retorna false
			if (word.charAt(i) != word.charAt(word.length() - i - 1)) {
				resposta = false;
			} else {// Se não, chama a função novamente com o proximo index
				resposta = isPalindromo(word, i + 1);
			}
		}

		return resposta;
	}

	public static boolean isFim(String s) {
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
			// Para cada linha da entrada, verifica se é palíndroma
			if (isPalindromo(entrada[j])) {
				MyIO.println("SIM");
			} else {
				MyIO.println("NAO");
			}
		}
	}
}
