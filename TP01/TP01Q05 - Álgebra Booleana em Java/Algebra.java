class Algebra {
    public static boolean calcular(String expressao) { // Método para calcular uma expressão única
        boolean resultado = false;
        int parentesesPos = 0;
        int qntVariavel = 1;
        for (int i = 0; i < expressao.length(); i++) {
            if (expressao.charAt(i) == '(') {// Encontra onde começa a expressão
                parentesesPos = i;
            }
            if (expressao.charAt(i) == ',') { // Através da quantidade de virgulas, descobre a quantidade de variaveis
                qntVariavel++;
            }
        }
        boolean[] variaveis = new boolean[qntVariavel];
        int convertido = 0;
        for (int i = parentesesPos + 1; i < expressao.length(); i += 2) {
            variaveis[convertido] = expressao.charAt(i) == '1'; // A cada valor, converte para boolean
            convertido++;
        }

        // Descobre a operação pela última letra, sendo r "or", d "and" e t "not"
        if (expressao.charAt(parentesesPos - 1) == 'r') {
            resultado = variaveis[0];
            for (int i = 1; i < qntVariavel; i++) {
                resultado = resultado || variaveis[i];
            }
        }
        if (expressao.charAt(parentesesPos - 1) == 't') {
            resultado = !variaveis[0];
        }
        if (expressao.charAt(parentesesPos - 1) == 'd') {
            resultado = variaveis[0];
            for (int i = 1; i < qntVariavel; i++) {
                resultado = resultado && variaveis[i];
            }
        }

        return resultado;
    }

    public static String replace(String s, String a, String b) {
        String resultado = "";
        boolean iguais = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a.charAt(0)) {
                for (int j = 0; j < a.length(); j++) {
                    if (s.charAt(i + j) != a.charAt(j)) {
                        j = a.length();
                        iguais = false;
                    } else {
                        iguais = true;
                    }
                }
                if (iguais) {
                    for (int j = 0; j < b.length(); j++) {
                        resultado += b;
                        i += a.length() - 1;
                    }
                } else {
                    resultado += s.charAt(i);
                }

            } else {
                resultado += s.charAt(i);
            }
        }
        return resultado;
    }

    public static boolean algebra(String expressao) {
        int parentesesPos = 0; // Guardar posição da abertura de parenteses

        for (int i = 2; i <= 6; i++) {// 2 é o valor de A e 6 é o valor de C
            if (expressao.charAt(i) == '0' || expressao.charAt(i) == '1') { // Para não contabilizar os espaços, apenas
                                                                            // os valores 0 e 1
                String valor = "";
                String substituir = i == 2 ? "A" : i == 4 ? "B" : i == 6 ? "C" : ""; // Cria uma string com o valor da
                                                                                     // variavel
                valor += expressao.charAt(i);
                expressao = replace(expressao, substituir, valor); // Chama o método replace para substituir as letras
                                                                   // pelos valores
            }
        }

        expressao = replace(expressao, " ", ""); // Retira os espaços

        // Salvar a posição do último parenteses aberto até encontrar um fechamento
        // Quando encontrar o fechamento, percorre negativamente até o último aberto
        // Salva em uma string e calcula em outro método, retornando o resultado e
        // reiniciando o for
        for (int i = 0; i < expressao.length(); i++) {
            if (expressao.charAt(i) == '(') {
                parentesesPos = i;
            }
            if (expressao.charAt(i) == ')') {
                String valorInverso = "";
                String valor = "";
                int operador = 0;
                if (expressao.charAt(parentesesPos - 1) == 'r') {
                    operador = 2;
                } else {
                    operador = 3;
                }
                for (int j = i; j >= parentesesPos - operador; j--) {
                    valorInverso += expressao.charAt(j);
                }
                for (int k = valorInverso.length() - 1; k >= 0; k--) {
                    valor += valorInverso.charAt(k);
                }
                expressao = replace(expressao, valor, calcular(valor) ? "1" : "0"); // Substitui o valor pelo resultado
                i = 0;
            }
        }

        return expressao.charAt(expressao.length() - 1) == '1' ? true : false;
    }

    public static boolean stringIs0(String s) {
        if (s.length() == 1) { // Verificar se a string contém unicamente "0"
            if (s.charAt(0) == '0') {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String entrada = ""; // Recebe uma string com a expressão

        while (!stringIs0(entrada)) { // Ler enquanto não for 0
            entrada = MyIO.readLine();
            if (!stringIs0(entrada)) {
                MyIO.println(algebra(entrada) ? "1" : "0");
            }
        }
    }
}
