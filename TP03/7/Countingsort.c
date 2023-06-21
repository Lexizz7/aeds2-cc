#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

#define MAX 100

// como não existe tipo Date em C, criei uma simples com ano, mês e dia
typedef struct Date
{
    int ano;
    int mes;
    int dia;
} Date;

// estrutura do filme de acordo com o enunciado
typedef struct Filme
{
    char nome[100];
    char tituloOriginal[100];
    Date dataLancamento;
    int duracao;
    char genero[100];
    char idiomaOriginal[50];
    char situacao[30];
    float orcamento;
    char palavrasChave[50][100];
    int qntPalavrasChave; // contador de quantas palavras chaves o filme tem
    // foi a forma que encontrei pra percorrer a matriz de palavras-chave
} Filme;

// método para retirar os espaços laterais da string
char *trim(char string[15000])
{
    char *res;
    res = malloc(sizeof(char) * 15000); // aloca espaço para os caracteres da string
    int count = 0, esq = 0, dir = 0;
    for (int i = 0; i < strlen(string); i++) // primeiro passo: encontrar pela esquerda quando deixa de ser espaço
    {
        if (string[i] != ' ')
        {
            esq = i;            // define a posição que começa pela esquerda
            i = strlen(string); // sai do loop
        }
    }
    for (int i = strlen(string) - 1; i > esq; i--) // depois, a partir do final, encontrar pela direita o primeiro char
    {
        if (string[i] != ' ')
        {
            dir = i;
            i = esq;
        }
    }
    for (int i = esq; i < dir; i++) // com os valores de esquerda e direita, percorrer partindo da esquerda até a direita
    {
        res[count] = string[i];
        count++;
    }

    strcpy(string, res); // copia para a string original mas também retorna para usar dentro de outros métodos
    return res;
}

char *removeTags(char string[15000])
{
    char *res;
    res = malloc(sizeof(char) * 15000);
    int count = 0;
    for (int i = 0; i < strlen(string); i++)
    {
        if (string[i] == '<') // ao abrir uma tag, percorrer pulando o "i" até fechar a tag
        {
            while (string[i] != '>')
            {
                i++;
            }
        }
        if (string[i] != '>') // como depois de retirar a tag o i == '>', o if serve para não aparecer ">string"
        {
            res[count] = string[i];
            count++;
        }
    }

    strcpy(string, res);
    return res;
}

// metodo para criar um tipo date a partir de uma string dd/mm/yyyy
Date setDate(char dataString[15000])
{
    Date newData;
    char *Sdia, *Smes, *Sano; // cria 3 strings para receber a forma texto de cada atributo
    Sdia = malloc(sizeof(char) * 2);
    Smes = malloc(sizeof(char) * 2);
    Sano = malloc(sizeof(char) * 4);

    int dia = 0, mes = 0, ano = 0, contador = 0; // cria 3 inteiros para serem setados realmente na struct
                                                 // o contador conta o número de barra
    for (int i = 0; i < strlen(dataString); i++)
    {
        if (dataString[i] == '/') // ao encontrar a primeira barra significa que os dois ultimos digitos serao o dia
        {
            contador++;
            switch (contador)
            {
            case 1: // executar na aparição da primeira barra
                Sdia[0] = dataString[i - 2];
                Sdia[1] = dataString[i - 1];

                break;
            case 2: // executar na aparição da segunda barra
                // dois digitos antes dela é o mês, 4 digitos após ela é o ano
                Smes[0] = dataString[i - 2];
                Smes[1] = dataString[i - 1];

                Sano[0] = dataString[i + 1];
                Sano[1] = dataString[i + 2];
                Sano[2] = dataString[i + 3];
                Sano[3] = dataString[i + 4];
                break;
            }
        }
    }
    // converte a string para inteiros
    dia = atoi(Sdia);
    mes = atoi(Smes);
    ano = atoi(Sano);
    newData.dia = dia;
    newData.mes = mes;
    newData.ano = ano;

    return newData;
}

// método para pegar a duração em minutos no formato Hh Mm
int getDuracao(char string[15000])
{
    int res = 0;
    char *horas;
    horas = malloc(sizeof(char) * 2);
    char *minutos;
    minutos = malloc(sizeof(char) * 2);

    for (int i = 0; i < strlen(string); i++)
    {
        if (string[i] == 'h') // ao encontrar h significa que os dois numeros antes são as horas
        {
            if (string[i - 2] != '0' && string[i - 2] != '1' && string[i - 2] != '2' && string[i - 2] != '3' && string[i - 2] != '4' && string[i - 2] != '5' && string[i - 2] != '6' && string[i - 2] != '7' && string[i - 2] != '8' && string[i - 2] != '9')
            {
                // esse if serve como prevenção para casos de não existir segundo digito. exemplo: 3h
                horas[0] = '0'; // nesse caso, o primeiro char é 0
            }
            else
            {
                // caso seja um filme com mais de 9 horas (meio improvável né mas tudo bem)
                horas[0] = string[i - 2];
            }
            horas[1] = string[i - 1]; // pegar o número imediatamente antes do h
            res += atoi(horas) * 60;  // converte em int e multiplica por 60
        }
        if (string[i] == 'm') // mesmo raciocinio
        {
            if (string[i - 2] != '0' && string[i - 2] != '1' && string[i - 2] != '2' && string[i - 2] != '3' && string[i - 2] != '4' && string[i - 2] != '5' && string[i - 2] != '6' && string[i - 2] != '7' && string[i - 2] != '8' && string[i - 2] != '9')
            {
                minutos[0] = '0';
            }
            else
            {
                minutos[0] = string[i - 2];
            }
            minutos[1] = string[i - 1];
            res += atoi(minutos); // mas aqui não precisa de multiplicação
        }
    }

    return res;
}

// corta uma string a partir do começo
char *cortar(char string[15000], int start, int end)
{
    char *res;
    res = malloc(sizeof(char) * 15000);
    int count = 0;
    for (int i = start - 1; i < end; i++)
    {
        res[count] = string[i];
        count++;
    }

    strcpy(string, res);
    return res;
}

// método para substituir um texto dentro da string
char *replaceAll(char string[15000], char deleteString[15000], char newString[15000])
{
    char *res;
    res = malloc(sizeof(char) * 15000);
    bool iguais = false;                     // serve para controlar quando foi encontrado exatamente a string que quer substituir
    int contador = 0;                        // tamanho da string resposta
    for (int i = 0; i < strlen(string); i++) // percorre por toda a string até encontrar o primeiro digito da substring que se quer substituir
    {
        if (string[i] == deleteString[0]) // caso encontre, é preciso verificar se os próximos digitos também conferem
        {
            for (int j = 0; j < strlen(deleteString); j++) // percorre pela substring verificando se ela existe dentro da string
            {
                if (string[i + j] != deleteString[j])
                {
                    iguais = false;           // caso algum char for diferente, quer dizer que não existe (não é igual)
                    j = strlen(deleteString); // sai do loop
                }
                else
                {
                    iguais = true;
                }
            }
            if (iguais) // se foi encontrada a substring, percorrer pela nova que irá substituir, aumentando o contador da resultante
            {
                for (int j = 0; j < strlen(newString); j++)
                {
                    res[contador] = newString[j];
                    contador++;
                }
                i += strlen(deleteString) - 1; // somar no tamanho da string original, já que substituiu o que havia no lugar
            }
            else
            {
                // não são iguais, continua do jeito normal
                res[contador] = string[i];
                contador++;
            }
        }
        else
        {
            // se não encontrar o primeiro digito, só continuar adicionando os char da original na resultante
            res[contador] = string[i];
            contador++;
        }
    }

    return res;
}

// método para retornar um novo filme com os atributos preenchidos
Filme ler(char html[100])
{
    Filme filme;
    char path[110] = "/tmp/filmes/";
    strcat(path, html);           // concatena o caminho com o nome do html
    FILE *arq = fopen(path, "r"); // tenta abrir esse arquivo
    char linha[15000];            // receberá cada uma das linhas do html
    if (arq)                      // se o arquivo foi aberto
    {
        // Primeiro atributo: Nome, procura pelo primeiro h2 class
        while (strstr(linha, "<h2 class") == NULL)
        {
            fgets(linha, 15000, arq); // pula pra próxima linha, armazenando na variavel
        }
        fgets(linha, 15000, arq);                    // pula linha em branco
        strcpy(filme.nome, removeTags(trim(linha))); // atribui a nova string, sem tags e espaços, ao nome do filme

        // Segundo atributo: Data de lançamento, procura pelo próximo span class release
        while (strstr(linha, "<span class=\"release\">") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        fgets(linha, 15000, arq);
        filme.dataLancamento = setDate(strtok(linha, " ")); // strtok é parecido com o split em java, mas só retorna a primeira parte, que é o necessário

        // Terceiro atributo: Genero, procura pelo próximo span class genre
        while (strstr(linha, "<span class=\"genres\">") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        fgets(linha, 15000, arq);
        fgets(linha, 15000, arq);
        strcpy(filme.genero, removeTags(trim(replaceAll(linha, "&nbsp;", ""))));

        // Quarto atributo: Duração, procura pelo próximo span class runtime
        while (strstr(linha, "<span class=\"runtime\">") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        fgets(linha, 15000, arq);
        fgets(linha, 15000, arq);
        filme.duracao = getDuracao(trim(linha));

        // Quinto atributo: Titulo original, procura pelo próximo a conter "Título original"
        while (strstr(linha, "<p class=\"wrap\"><strong>Título original</strong>") == NULL)
        {
            fgets(linha, 15000, arq);

            // como alguns html não tem titulo original, se encontrar o próximo atributo (situação), sai do loop
            if (strstr(linha, "<strong><bdi>Situação</bdi></strong>") != NULL)
            {
                break;
            }
        }
        if (strstr(linha, "<strong><bdi>Situação</bdi></strong>") != NULL) // se a linha for o proximo atributo, o titulo original é igual ao nome
        {
            strcpy(filme.tituloOriginal, filme.nome);
        }
        else
        {
            // se não, só remover tags, espaços e substituir o que não precisa
            strcpy(filme.tituloOriginal, trim(removeTags(replaceAll(linha, "<p class=\"wrap\"><strong>Título original</strong>", ""))));
        }

        // Sexto atributo: Situação, procura pelo próximo a conter "Situação"
        while (strstr(linha, "<strong><bdi>Situação</bdi></strong>") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        strcpy(filme.situacao, trim(removeTags(replaceAll(linha, "<strong><bdi>Situação</bdi></strong>", ""))));

        // Sétimo atributo: Idioma original, procura pelo próximo a conter "Idioma original"
        while (strstr(linha, "<p><strong><bdi>Idioma original</bdi></strong>") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        strcpy(filme.idiomaOriginal, trim(removeTags(replaceAll(linha, "<p><strong><bdi>Idioma original</bdi></strong>", ""))));

        // Oitavo atributo: Orçamento, procura pelo próximo a conter "Orçamento"
        while (strstr(linha, "<p><strong><bdi>Orçamento</bdi></strong>") == NULL)
        {
            fgets(linha, 15000, arq);
        }
        if (strstr(linha, "-") != NULL) // alguns têm "-" ao inves do valor, significa que é 0
        {
            filme.orcamento = 0;
        }
        else
        {
            // vários replaces para remover '$' ',' ' '
            // atof converte a string final para um float
            filme.orcamento = atof(
                trim(
                    removeTags(
                        replaceAll(
                            replaceAll(
                                replaceAll(
                                    replaceAll(linha, "<p><strong><bdi>Orçamento</bdi></strong>", ""), "$", ""),
                                ",", ""),
                            " ", ""))));
        }

        int qntPChave = 0;                     // contador de palavras chave
        while (strstr(linha, "</ul>") == NULL) // percorre até o final de uma lista
        {
            fgets(linha, 15000, arq);

            if (strstr(linha, "<li>") != NULL) // cada item da lista é uma palavra chave
            {
                strcpy(filme.palavrasChave[qntPChave], trim(removeTags(linha))); // adiciona a string de acordo com o contador
                qntPChave++;
            }
            if (strstr(linha, "Nenhuma palavra-chave foi adicionada") != NULL) // se não houver palavra chave, sai do loop
            {
                break;
            }
        }
        filme.qntPalavrasChave = qntPChave; // seta a quantidade na struct
    }
    else
    {
        printf("Arquivo %s nao encontrado\n", html);
    }

    fclose(arq);
    return filme;
}

char *getDataLancamento(Date data) // retorna uma string com a data no formato dd/mm/yyyy
{
    char *res;
    res = malloc(sizeof(char) * 10);

    // escreve na string resposta cada int com duas casas no minimo
    sprintf(res, "%02d/%02d/%d", data.dia, data.mes, data.ano);

    return res;
}

void imprimir(Filme filme) // imprime os dados do filme
{
    printf("%s ", filme.nome);
    printf("%s ", filme.tituloOriginal);
    printf("%s ", getDataLancamento(filme.dataLancamento));
    printf("%d ", filme.duracao);
    printf("%s ", filme.genero);
    printf("%s ", filme.idiomaOriginal);
    printf("%s ", filme.situacao);
    if (filme.orcamento > 0)
    {
        printf("%.1e ", filme.orcamento);
    }
    else
    {
        printf("%g ", filme.orcamento);
    }
    printf("[");
    for (int i = 0; i < filme.qntPalavrasChave; i++) // percorre pelas palavras chaves
    {
        if (i == filme.qntPalavrasChave - 1) // para o ultimo não ficar com ", "
        {
            printf("%s", filme.palavrasChave[i]);
        }
        else
        {
            printf("%s, ", filme.palavrasChave[i]);
        }
    }
    printf("]\n");
}

// Cria uma lista de acordo com o maximo definido
Filme array[MAX];
int n;

// Inicializa com 0 elementos
void construir()
{
    n = 0;
}

// Todos os métodos seguem a mesma lógica do Exercício 1 em java, comentei apenas as diferenças
void inserirInicio(char html[100])
{
    if (n >= MAX)
        exit(1);
    // Deslocar elementos para a direita
    for (int i = n; i > 0; i--)
    {
        array[i] = array[i - 1];
    }

    array[0] = ler(html); // método ler retorna um filme, então não é necessário criar um filme temporario
    n++;
}

void inserirFim(char html[100])
{
    if (n >= MAX)
        exit(1);

    array[n] = ler(html);
    n++;
}

void inserir(char html[100], int pos)
{
    if (n >= MAX || pos < 0 || pos > n)
        exit(1);

    for (int i = n; i > pos; i--)
    {
        array[i] = array[i - 1];
    }

    array[pos] = ler(html);
    n++;
}

Filme removerInicio()
{
    if (n == 0)
        exit(1);

    Filme tmp = array[0];
    n--;

    for (int i = 0; i < n; i++)
    {
        array[i] = array[i + 1];
    }

    return tmp;
}

Filme removerFim()
{
    if (n == 0)
        exit(1);

    Filme tmp = array[n - 1];
    n--;

    return tmp;
}

Filme remover(int pos)
{
    if (n == 0 || pos < 0 || pos >= n)
        exit(1);

    Filme tmp = array[pos];
    n--;

    for (int i = pos; i < n; i++)
    {
        array[i] = array[i + 1];
    }

    return tmp;
}

void mostrar()
{
    for (int i = 0; i < n; i++)
    {
        imprimir(array[i]);
    }
}

void swap(int pos1, int pos2)
{
    Filme tmp = array[pos1];
    array[pos1] = array[pos2];
    array[pos2] = tmp;
}

// variaveis para log
int comparacoes = 0;
int movimentacoes = 0;

// Método de inserção geral, onde H é o espaçamento e Distancia é a distancia do 0
void insercao(int distancia, int h)
{
    // Percorre começando da distancia passada e pulando de h em h
    for (int i = h + distancia; i < n; i += h)
    {
        // Mesmo raciocinio de inserção, salva o temporário, compara com o de trás até encontrar o lugar correto
        Filme tmp = array[i];
        int j = i - h;
        while ((j >= 0) && array[j].duracao == tmp.duracao && strcmp(array[j].nome, tmp.nome) < 0)
        {
            array[j + h] = array[j];
            j -= h;
        }
        array[j + h] = tmp;
    }
}

void ordenar()
{
    // Counting sort
    // - Encontrar o maior valor (getMaior)
    int maior = 0;
    for (int i = 0; i < n; i++)
    {
        if (array[i].duracao > maior)
            maior = array[i].duracao;
    }
    maior++; // Para criar o número correto de posições

    int *contagem;
    contagem = malloc(maior * sizeof(int));
    Filme *ordenado;
    ordenado = malloc(n * sizeof(Filme));

    // Inicializar contagem
    for (int i = 0; i < maior; contagem[i] = 0, i++)
        ;

    // Preencher contagem
    for (int i = 0; i < n; contagem[array[i].duracao]++, i++)
        ;

    // Acumular valores
    for (int i = 1; i < maior; contagem[i] += contagem[i - 1], i++)
        ;

    // Ordenar o array
    for (int i = n - 1; i >= 0; ordenado[contagem[array[i].duracao] - 1] = array[i], movimentacoes++, contagem[array[i].duracao]--, i--)
        ;

    // A questão do desempate por nome: não sei como fazer usando counting sort
    // Então usei insertion sort
    // Mas mesmo assim, o pub.out está errado (Homem aranha vem antes de Matrix)
    insercao(0, 1);

    for (int i = 0; i < n; array[i] = ordenado[i], i++)
        ; // Armazena os valores ordenados

    free(contagem);
    free(ordenado);
}

int main()
{
    FILE *log = fopen("756293_countingsort.txt", "w"); // arquivo para log
    char entrada[100];
    construir();
    // Primeira parte da entrada (nome dos html)
    do
    {
        scanf("%[^\n]%*c", entrada);
        if (strstr(entrada, "FIM") == NULL)
        {
            inserirFim(entrada);
        }
    } while (strstr(entrada, "FIM") == NULL);

    // ordenar e mostrar tudo no final
    clock_t comeco = clock();
    ordenar();
    clock_t final = clock();
    double tempoGasto = (double)(final - comeco) / CLOCKS_PER_SEC;
    mostrar();

    fprintf(log, "756293\t%d\t%d\t%lf", comparacoes, movimentacoes, tempoGasto);

    fclose(log);
    return 0;
}