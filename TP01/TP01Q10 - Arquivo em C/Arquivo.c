#include <stdio.h>

// Método que recebe a posição do último caracter
// percorre inversamente até encontrar a quebra de linha
// e retorna a posição da linha anterior
long posicaoAnterior(FILE *arquivo, long posicao)
{
    long posicaoAnterior;
    fseek(arquivo, posicao, SEEK_SET); // posiciona o ponteiro no arquivo

    for (int i = posicao; i > 0; i--) // Percorrer inversamente, até 0
    {
        fseek(arquivo, i, SEEK_SET);
        if (fgetc(arquivo) == '\n' || fgetc(arquivo) == ' ') // Caso encontre uma quebra de linha, é um novo valor
        {
            posicaoAnterior = i;
            i = 0;
        }
    }

    if (posicaoAnterior > posicao)
    {
        posicaoAnterior = 0;
    }

    return posicaoAnterior;
}

int main()
{
    int n = 0;
    float numero = 0;
    FILE *arquivo = fopen("arquivo.txt", "w");

    scanf("%d", &n); // Receber o numero de valores (primeira linha)

    for (int i = 0; i < n; i++) // Printa cada valor em um arquivo de texto
    {
        scanf("%f", &numero);
        fprintf(arquivo, "%.6g\n", numero);
    }

    fclose(arquivo);

    arquivo = fopen("arquivo.txt", "r"); // Abre o mesmo arquivo feito

    fseek(arquivo, 0, SEEK_END); // Posiciona o ponteiro no final do arquivo
    long posicao = ftell(arquivo);

    while (fgetc(arquivo) == '\n' || fgetc(arquivo) == EOF || fgetc(arquivo) == ' ') // Percorre até encontrar o primeiro valor
    {
        posicao--;
        fseek(arquivo, posicao, SEEK_SET); // Necessário para que o final do arquivo não seja um espaço ou quebra de linha
    }

    for (int i = 0; i < n; i++)
    {
        // Atualiza a posição para o começo da linha
        posicao = posicaoAnterior(arquivo, posicao);
        // Posiciona nela
        fseek(arquivo, posicao, SEEK_SET);
        // Lê o valor daquela determinada linha
        fscanf(arquivo, "%f", &numero);
        printf("%.6g\n", numero);
        posicao--;
    }

    fclose(arquivo);

    return 0;
}
