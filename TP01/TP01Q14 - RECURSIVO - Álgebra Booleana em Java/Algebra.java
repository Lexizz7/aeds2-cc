class Algebra {

    // Dividi o exercicio em vários métodos, todos recursivos

    // Método que verifica se a string 1 é igual a string 2
    // Chamda
    public static boolean sameWord(String s1, String s2, int i) {
        return sameWord(s1, s2, i, 0);
    }

    public static boolean sameWord(String s1, String s2, int i, int j) {
        boolean resposta = true;

        if (i < s1.length()) { // Percorre a string 1 e a string 2
            if (j < s2.length()) {
                if (s1.charAt(i) == s2.charAt(j)) { // Se os caracteres forem iguais, chama o método novamente
                    resposta = sameWord(s1, s2, i + 1, j + 1);

                } else { // Se encontrar alguma letra diferente, significa que não é igual
                    resposta = false;
                }
            }
        }

        return resposta;
    }

    // Método para substituir uma parte da string por outra
    // Chamada
    public static String replace(String string, String old, String novo) {
        return replace(string, old, novo, 0, 0, 0);
    }

    // Foi necessário 3 index pois percorre 3 strings distintas, a entrada, o que
    // vai ser substituido e pelo que vai substituir
    public static String replace(String string, String old, String novo, int i, int j, int k) {
        String resultado = "";
        boolean iguais = false;

        if (i < string.length()) { // Percorre a string padrão
            if (string.charAt(i) == old.charAt(j)) { // Caso encontre um caracter igual ao que vai ser substituido
                iguais = sameWord(string, old, i); // Verifica se é igual ao que vai ser substituido
                if (iguais) { // Se for igual, adiciona ao resultado a string que vai substituir
                    resultado += novo;
                    i = i + old.length(); // E pula todas as posições da string substituida (concatena)
                    j = 0;
                    k = k + novo.length();
                    resultado += replace(string, old, novo, i, j, k);
                } else { // Se não for igual, adiciona o caracter normalmente
                    resultado += string.charAt(i);
                    resultado += replace(string, old, novo, i + 1, j, k);
                }
            } else { // Se não encontrar um caracter igual, adiciona o caracter normalmente
                resultado += string.charAt(i);
                resultado += replace(string, old, novo, i + 1, j, k);
            }
        }

        return resultado;
    }

    // Método para atribuir 1 ou 0 para as variaveis
    // Chamada
    public static String atribuirVariaveis(String string) {
        return atribuirVariaveis(string, 1);
    }

    public static String atribuirVariaveis(String string, int i) {
        String valor = "";
        String variavel = "";

        if (i <= 3) {// Percorre até a posicao 3 pois a expressao é limitada a 3 variaveis, sendo que
                     // a primeira é a entrada (posição 0)
            if (string.charAt(i) == '1' || string.charAt(i) == '0') {
                valor += string.charAt(i);
                variavel = i == 1 ? "A" : i == 2 ? "B" : i == 3 ? "C" : ""; // Se for a primeira variavel, A, se for a
                                                                            // segunda, B, se for a terceira, C

                string = replace(string, variavel, valor); // Substitui a variavel pelo valor

                string = atribuirVariaveis(string, i + 1); // Chama o método novamente para a próxima variavel
            }
        }

        return string;
    }

    // Método para inverter uma string qualquer
    // Chamada
    public static String inverter(String string) {
        return inverter(string, string.length());
    }

    public static String inverter(String string, int i) {
        String resultado = "";

        if (i > 0) { // Percorre a string do final para o inicio
            resultado += string.charAt(i - 1); // adiciona o caracter final no resultado
            resultado += inverter(string, i - 1); // chama o método novamente para a próxima posição (invertendo)
        }

        return resultado;
    }

    // Método para isolar uma operacao. Ex: and(not(A) -> not(A)
    public static String separarOperacao(String expressao, int i, int j) {
        String resultado = "";
        // Caso seja "or" separar 2 caracteres antes do "(", caso contrário separar 3
        // caracteres antes do "(" (and e not)
        int operacao = expressao.charAt(i - 1) == 'r' ? 2
                : expressao.charAt(i - 1) == 'd' ? 3 : expressao.charAt(i - 1) == 't' ? 3 : 0;

        if (j >= i - operacao) { // i -> posicao do "(" mais proximo e j -> posicao do ")"
            resultado += expressao.charAt(j);
            resultado += separarOperacao(expressao, i, j - 1);
        }

        return resultado;
    }

    // Método para calcular uma operação separada
    // Chamada
    public static boolean calcular(String expressao) {
        return calcular(expressao, 0);
    }

    public static boolean calcular(String expressao, int i) {
        boolean resultado = expressao.charAt(0) == 'a' ? true : false; // Se for "and", retorna true por padrão, caso
                                                                       // contrario, false

        if (i < expressao.length()) {
            if (expressao.charAt(0) == 'a') { // And
                if (expressao.charAt(i) == '0') { // Se houver um único 0 = false
                    resultado = false;
                } else {
                    resultado = calcular(expressao, i + 1); // Se não, verificar proxima posição
                }
            }
            if (expressao.charAt(0) == 'o') { // Or
                if (expressao.charAt(i) == '1') { // Se houver um único 1 = true
                    resultado = true;
                } else {
                    resultado = calcular(expressao, i + 1); // Se não, verificar proxima posição
                }
            }
            if (expressao.charAt(0) == 'n') { // Not
                if (expressao.charAt(i) == '1') { // Caso encontre 1 = false
                    resultado = false;
                } else if (expressao.charAt(i) == '0') { // Caso encontre 0 = true
                    resultado = true;
                } else {
                    resultado = calcular(expressao, i + 1); // Se não encontrar nenhum número, verificar proxima posição
                }
            }
        }
        return resultado;
    }

    // Método que liga todos os outros, fazendo a operação completa
    // Chamada
    public static boolean algebra(String expressao) {
        return algebra(expressao, 0, 0);
    }

    public static boolean algebra(String expressao, int i, int parenteses) {
        boolean resposta = false;
        int posParentesesAberto = parenteses; // Salva a posição do parenteses aberto mais próximo
        // Não encontrei um meio de fazer isso sem passar parâmetro, então fiz assim

        expressao = replace(expressao, " ", ""); // Remove os espaços

        expressao = atribuirVariaveis(expressao); // Atribui valores para as variaveis

        if (i < expressao.length()) {
            if (expressao.charAt(i) == '(') { // Caso abre parenteses, atualiza a posição do parenteses aberto
                posParentesesAberto = i;
            }
            if (expressao.charAt(i) == ')') { // Caso feche parenteses, significa que uma operação foi feita
                String expressaoInterna = inverter(separarOperacao(expressao, posParentesesAberto, i)); // Separa essa
                                                                                                        // operação e
                                                                                                        // inverte
                boolean respostaInterna = calcular(expressaoInterna); // Calcula a operação

                expressao = replace(expressao, expressaoInterna, respostaInterna ? "1" : "0"); // Substitui a operação
                                                                                               // pelo resultado
                resposta = algebra(expressao, 0, posParentesesAberto); // Chama o método novamente para a próxima
                                                                       // operação
            } else { // Caso não encontre nenhum parenteses, verifica a próxima posição
                resposta = algebra(expressao, i + 1, posParentesesAberto);
            }
        } else { // Caso chegue no final da string, verifica se é true ou false de acordo com o
                 // último caracter
            resposta = expressao.charAt(expressao.length() - 1) == '1' ? true : false;
        }

        return resposta;
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
