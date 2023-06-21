class Is {

    public static boolean isVogais(String expressao) {
        boolean resultado = false;
        // Percorre por toda a string e verifica se existe alguma vogal na posição
        // Se houver outro caracter que não seja uma vogal, fecha o for e retorna false
        for (int i = 0; i < expressao.length(); i++) {
            if (((int) expressao.charAt(i) >= 97 && (int) expressao.charAt(i) <= 122) // 97 a 122 = a a z
                    || ((int) expressao.charAt(i) >= 65 && (int) expressao.charAt(i) <= 90)) {// 65 a 90 = A a Z
                if (expressao.charAt(i) == 'a' || expressao.charAt(i) == 'e' || expressao.charAt(i) == 'i'
                        || expressao.charAt(i) == 'o' || expressao.charAt(i) == 'u' || expressao.charAt(i) == 'A'
                        || expressao.charAt(i) == 'E' || expressao.charAt(i) == 'I' || expressao.charAt(i) == 'O'
                        || expressao.charAt(i) == 'U') {
                    resultado = true;
                } else {
                    resultado = false;
                    i = expressao.length();
                }
            } else {
                resultado = false;
                i = expressao.length();
            }
        }

        return resultado;
    }

    public static boolean isConsoantes(String expressao) {
        boolean resultado = false;
        // Percorre por toda a string e verifica se existe alguma consoante na posição
        // Se houver outro caracter que não seja uma consoante (diferente de vogal),
        // fecha o for e retorna false
        for (int i = 0; i < expressao.length(); i++) {
            if (((int) expressao.charAt(i) >= 97 && (int) expressao.charAt(i) <= 122)// 97 a 122 = a a z
                    || ((int) expressao.charAt(i) >= 65 && (int) expressao.charAt(i) <= 90)) {// 65 a 90 = A a Z
                if (expressao.charAt(i) == 'a' || expressao.charAt(i) == 'e' || expressao.charAt(i) == 'i'
                        || expressao.charAt(i) == 'o' || expressao.charAt(i) == 'u' || expressao.charAt(i) == 'A'
                        || expressao.charAt(i) == 'E' || expressao.charAt(i) == 'I' || expressao.charAt(i) == 'O'
                        || expressao.charAt(i) == 'U') {
                    resultado = false;
                    i = expressao.length();
                } else {
                    resultado = true;
                }
            } else {
                resultado = false;
                i = expressao.length();
            }
        }

        return resultado;
    }

    public static boolean isInteiro(String expressao) {
        boolean resultado = false;
        // Percorre toda a string e verifica se existe algum caracter que não seja um
        // inteiro
        for (int i = 0; i < expressao.length(); i++) {
            if (((int) expressao.charAt(i) >= 48 && (int) expressao.charAt(i) <= 57)) {// 48 a 57 = 0 a 9
                resultado = true;
            } else {
                resultado = false;
                i = expressao.length();
            }
        }
        return resultado;
    }

    public static boolean isReal(String expressao) {
        boolean resultado = false;
        int numeroDePonto = 0; // Contador de pontos
        int numeroDeVirgula = 0; // Contador de virgulas
        // É preciso contar pontos e vírgulas pois não pode existe mais de 1 para fração
        for (int i = 0; i < expressao.length(); i++) {
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
                    i = expressao.length();
                } else {
                    resultado = true;
                }
            } else {
                resultado = false;
                i = expressao.length();
            }
        }
        return resultado;
    }

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
