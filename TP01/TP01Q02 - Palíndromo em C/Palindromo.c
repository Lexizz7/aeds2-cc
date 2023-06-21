#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool isPalindromo(char word[])
{
    // Método que verifica se uma palavra é palíndroma
    bool resposta = true; // Por padrão, a palavra é palíndroma até que se encontre um caracter que não
                          // seja igual ao seu respectivo caracter na posição oposta
    for (int i = 0; i < strlen(word); i++)
    { // Para cada caracter da palavra
        if (word[i] != word[strlen(word) - 1 - i])
        { // Caso seja diferente do caracter da posição
          // oposta
            resposta = false;
        }
    }

    return resposta;
}

int main(void)
{
    char entrada[1000];

    while (strcmp(entrada, "FIM") != 0) // Enquanto a entrada não for FIM
    {
        scanf(" %[^\n]s", entrada);      // Ler a proxima string
        if (strcmp(entrada, "FIM") != 0) // verifica se é FIM
        {
            // Caso seja palíndroma, imprime SIM, caso contrário, imprime NAO
            if (isPalindromo(entrada))
            {
                printf("SIM\n");
            }
            else
            {
                printf("NAO\n");
            }
        }
    }

    return 0;
}
