class Is {
    // Chama o método isVogal
    public static boolean isVogais(String expressao) {
        return isVogais(expressao, 0); // Passa o index
    }

    // Método recursivo que verifica se a string é somente vogal
    // Caso não seja a,e,i,o,u,A,E,I,O,U, retorna false
    // Caso seja, chama novamente a função para testar o proximo index
    public static boolean isVogais(String expressao, int i) {
        boolean resultado = true; // Por padrão é vogal

        if (i < expressao.length()) {
            if (((int) expressao.charAt(i) >= 97 && (int) expressao.charAt(i) <= 122) // 97 a 122 = a a z
                    || ((int) expressao.charAt(i) >= 65 && (int) expressao.charAt(i) <= 90)) {// 65 a 90 = A a Z
                if (expressao.charAt(i) == 'a' || expressao.charAt(i) == 'e' || expressao.charAt(i) == 'i'
                        || expressao.charAt(i) == 'o' || expressao.charAt(i) == 'u' || expressao.charAt(i) == 'A'
                        || expressao.charAt(i) == 'E' || expressao.charAt(i) == 'I' || expressao.charAt(i) == 'O'
                        || expressao.charAt(i) == 'U') {
                    resultado = isVogais(expressao, i + 1);
                } else {
                    resultado = false;
                }
            } else {
                resultado = false;
            }
        }

        return resultado;
    }

    // Método para chamar isConsoantes
    public static boolean isConsoantes(String expressao) {
        return isConsoantes(expressao, 0);
    }

    // Método recursivo que verifica se a string é somente consoante
    // Caso seja a,e,i,o,u,A,E,I,O,U, retorna false
    // Caso não seja, chama novamente a função para testar o proximo index
    public static boolean isConsoantes(String expressao, int i) {
        boolean resultado = true;

        if (i < expressao.length()) {
            if (((int) expressao.charAt(i) >= 97 && (int) expressao.charAt(i) <= 122) // 97 a 122 = a a z
                    || ((int) expressao.charAt(i) >= 65 && (int) expressao.charAt(i) <= 90)) {// 65 a 90 = A a Z
                if (expressao.charAt(i) == 'a' || expressao.charAt(i) == 'e' || expressao.charAt(i) == 'i'
                        || expressao.charAt(i) == 'o' || expressao.charAt(i) == 'u' || expressao.charAt(i) == 'A'
                        || expressao.charAt(i) == 'E' || expressao.charAt(i) == 'I' || expressao.charAt(i) == 'O'
                        || expressao.charAt(i) == 'U') {
                    resultado = false;
                } else {
                    resultado = isConsoantes(expressao, i + 1);
                }
            } else {
                resultado = false;
            }
        }

        return resultado;
    }

    // Método para chamar isInteiro
    public static boolean isInteiro(String expressao) {
        return isInteiro(expressao, 0);
    }

    // Caso o caractere seja número (0 a 9), retorna a função para testar o proximo
    // index
    public static boolean isInteiro(String expressao, int i) {
        boolean resultado = true;

        if (i < expressao.length()) {
            if (((int) expressao.charAt(i) >= 48 && (int) expressao.charAt(i) <= 57)) {// 48 a 57 = 0 a 9
                resultado = isInteiro(expressao, i + 1);
            } else {
                resultado = false;
            }
        }

        return resultado;
    }

    // Método para chamar isReal
    public static boolean isReal(String expressao) {
        return isReal(expressao, 0, 0, 0);
    }

    // Método verifica se é um número, ponto ou virgula, caso false, retorna false
    // Caso seja, chama novamente a função para testar o proximo index
    public static boolean isReal(String expressao, int i, int virgulas, int pontos) {
        boolean resultado = true;
        int numeroDePonto = pontos; // Contador de pontos
        int numeroDeVirgula = virgulas; // Contador de virgulas
        // É preciso contar pontos e vírgulas pois não pode existe mais de 1 para fração
        if (i < expressao.length()) {
            if (((int) expressao.charAt(i) >= 48 && (int) expressao.charAt(i) <= 57) || expressao.charAt(i) == '.'
                    || expressao.charAt(i) == ',') { // 48 a 57 = 0 a 9 + . + ,
                if (expressao.charAt(i) == '.') {
                    numeroDePonto++;
                } else if (expressao.charAt(i) == ',') {
                    numeroDeVirgula++;
                }
                if (numeroDePonto > 1 || numeroDeVirgula > 1) { // Caso tenha mais de um ponto ou mais de uma vírgula,
                                                                // retornar false
                    resultado = false;
                } else {
                    resultado = isReal(expressao, i + 1, numeroDeVirgula, numeroDePonto);
                }
            } else {
                resultado = false;
            }
        }

        return resultado;
    }

    // Verifica se a string é FIM
    public static boolean isFIM(String expressao) {
        return (expressao.length() == 3 && expressao.charAt(0) == 'F' && expressao.charAt(1) == 'I'
                && expressao.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        String entrada = "";

        while (!isFIM(entrada)) {
            entrada = MyIO.readLine();
            if (!isFIM(entrada)) {
                // Printar na mesma linha todos os resultados
                MyIO.println((isVogais(entrada) ? "SIM"
                        : "NAO") + " "
                        + (isConsoantes(entrada) ? "SIM"
                                : "NAO")
                        + " " + (isInteiro(entrada) ? "SIM"
                                : "NAO")
                        + " " + (isReal(entrada) ? "SIM" : "NAO"));
            }
        }
    }
}
