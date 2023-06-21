#include <stdio.h>
#include <stdbool.h>
#include <string.h>

// Método recursivo que verifica se uma string é palíndroma
bool isPalindromoRec(char word[], int i)
{
    bool resposta = true;

    if (i < strlen(word))
    {
        if (word[i] == word[strlen(word) - i - 1]) // Caso seja igual ao seu oposto, chama novamente com proximo index
        {
            resposta = isPalindromoRec(word, i + 1);
        }
        else // Caso contrario, retorna falso
        {
            resposta = false;
        }
    }

    return resposta;
}
// Chamar a função palindromo
bool isPalindromo(char word[])
{
    return isPalindromoRec(word, 0); // Recebe index 0 como começo
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
