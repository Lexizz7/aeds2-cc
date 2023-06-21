class Palindromo {

	public static boolean isPalindromo(String word) { // Método que verifica se uma palavra é palíndroma
		boolean resposta = true; // Por padrão, a palavra é palíndroma até que se encontre um caracter que não
									// seja igual ao seu respectivo caracter na posição oposta

		for (int i = 0; i < word.length(); i++) { // Para cada caracter da palavra
			if (word.charAt(i) != word.charAt(word.length() - 1 - i)) { // Caso seja diferente do caracter da posição
																		// oposta
				resposta = false;
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
