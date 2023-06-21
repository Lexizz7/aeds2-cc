#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool isVogais(char expressao[])
{
    bool resultado = false;
    // Percorre por toda a string e verifica se existe alguma vogal na posição
    // Se houver outro caracter que não seja uma vogal,
    // fecha o for e retorna false
    for (int i = 0; i < strlen(expressao); i++)
    {
        if (((int)expressao[i] >= 97 && (int)expressao[i] <= 122) || ((int)expressao[i] >= 65 && (int)expressao[i] <= 90)) // 97 a 122 = a a z // 65 a 90 = A a Z
        {
            if (expressao[i] == 'a' || expressao[i] == 'e' || expressao[i] == 'i' || expressao[i] == 'o' || expressao[i] == 'u' || expressao[i] == 'A' || expressao[i] == 'E' || expressao[i] == 'I' || expressao[i] == 'O' || expressao[i] == 'U')
            {
                resultado = true;
            }
            else
            {
                resultado = false;
                i = strlen(expressao);
            }
        }
        else
        {
            resultado = false;
            i = strlen(expressao);
        }
    }

    return resultado;
}

bool isConsoantes(char expressao[])
{
    bool resultado = false;
    // Percorre por toda a string e verifica se existe alguma consoante na posição
    // Se houver outro caracter que não seja uma consoante (diferente de vogal),
    // fecha o for e retorna false
    for (int i = 0; i < strlen(expressao); i++)
    {
        if (((int)expressao[i] >= 97 && (int)expressao[i] <= 122) || ((int)expressao[i] >= 65 && (int)expressao[i] <= 90)) // 97 a 122 = a a z // 65 a 90 = A a Z
        {
            if (expressao[i] == 'a' || expressao[i] == 'e' || expressao[i] == 'i' || expressao[i] == 'o' || expressao[i] == 'u' || expressao[i] == 'A' || expressao[i] == 'E' || expressao[i] == 'I' || expressao[i] == 'O' || expressao[i] == 'U')
            {
                resultado = false;
                i = strlen(expressao);
            }
            else
            {
                resultado = true;
            }
        }
        else
        {
            resultado = false;
            i = strlen(expressao);
        }
    }

    return resultado;
}

bool isInteiro(char expressao[])
{
    bool resultado = false;
    // Percorre toda a string e verifica se existe algum caracter que não seja um
    // inteiro
    for (int i = 0; i < strlen(expressao); i++)
    {
        if (((int)expressao[i] >= 48 && (int)expressao[i] <= 57)) // 48 a 57 = 0 a 9
        {
            resultado = true;
        }
        else
        {
            resultado = false;
            i = strlen(expressao);
        }
    }
    return resultado;
}

bool isReal(char expressao[])
{
    bool resultado = false;
    int numeroDePonto = 0;   // Contador de pontos
    int numeroDeVirgula = 0; // Contador de virgulas
    // É preciso contar pontos e vírgulas pois não pode existe mais de 1 para fração
    for (int i = 0; i < strlen(expressao); i++)
    {
        if (((int)expressao[i] >= 48 && (int)expressao[i] <= 57) || expressao[i] == '.' || expressao[i] == ',') // 48 a 57 = 0 a 9 //. = ponto //, = virgula
        {
            if (expressao[i] == '.')
            {
                numeroDePonto++;
            }
            else if (expressao[i] == ',')
            {
                numeroDeVirgula++;
            }
            if (numeroDePonto > 1 || numeroDeVirgula > 1) // Caso tenha mais de um ponto ou mais de uma vírgula,
                                                          // retornar false
            {
                resultado = false;
                i = strlen(expressao);
            }
            else
            {
                resultado = true;
            }
        }
        else
        {
            resultado = false;
            i = strlen(expressao);
        }
    }
    return resultado;
}

int main(void)
{
    char entrada[1000];

    while (strcmp(entrada, "FIM") != 0) // Enquanto a entrada não for FIM
    {
        scanf(" %[^\n]s", entrada);      // Ler a proxima string
        if (strcmp(entrada, "FIM") != 0) // verifica se é FIM
        {
            printf("%s ", isVogais(entrada) ? "SIM" : "NAO");
            printf("%s ", isConsoantes(entrada) ? "SIM" : "NAO");
            printf("%s ", isInteiro(entrada) ? "SIM" : "NAO");
            printf("%s\n", isReal(entrada) ? "SIM" : "NAO");
        }
    }

    return 0;
}
