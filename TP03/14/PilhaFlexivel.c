#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

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
char *trim(char *string)
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
    for (int i = esq; i <= dir; i++) // com os valores de esquerda e direita, percorrer partindo da esquerda até a direita
    {
        if (i < dir || (i == dir && string[i] > 65 && string[i] < 122))
        {
            res[count] = string[i];
            count++;
        }
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
    printf("%s ", trim(filme.situacao));

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

typedef struct Celula
{
    Filme elemento;
    struct Celula *prox;
} Celula;

Celula *newCelula(char html[100])
{
    Celula *new = (Celula *)malloc(sizeof(Celula));
    new->elemento = ler(html);
    new->prox = NULL;
    return new;
}

// Pilha flexivel
Celula *topo;
int tamanho = 0;
void construir()
{
    // topo = null -> pilha vazia
    topo = NULL;
}

void inserirFim(char html[100])
{
    // celula a ser inserida no topo
    Celula *tmp = newCelula(html);

    // faz o proximo de tmp ser o topo atual
    tmp->prox = topo;
    // faz o topo apontar para o tmp (novo topo)
    topo = tmp;

    // não se pode dar free pois tmp é o novo topo
    tmp = NULL;
    tamanho++;
}

Filme removerFim()
{
    // se a pilha tiver vazia
    if (topo == NULL)
    {
        Filme x;
        return x;
    }

    // resposta -> filme do topo
    Filme res = topo->elemento;

    // passa o topo para frente e apaga o topo antigo (tmp)
    Celula *tmp = topo;
    topo = topo->prox;
    tmp->prox = NULL;

    free(tmp);
    tmp = NULL;

    tamanho--;
    return res;
}

void mostrarRec(Celula *celula, int index)
{
    if (celula != NULL)
    {
        mostrarRec(celula->prox, index - 1);
        printf("[%d] ", index);
        imprimir(celula->elemento);
    }
}

void mostrar()
{
    // como é preciso mostrar da base até o topo, chamar recursivamente pra começar de baixo
    mostrarRec(topo, tamanho - 1);
}

int main()
{
    char entrada[100];
    int qntComando = 0;
    // Primeira parte da entrada (nome dos html)
    do
    {
        scanf("%[^\n]%*c", entrada);
        if (strstr(entrada, "FIM") == NULL)
        {
            inserirFim(entrada);
        }
    } while (strstr(entrada, "FIM") == NULL);

    // Pegar a quantidade de comandos
    scanf("%d", &qntComando);

    for (int i = qntComando; i >= 0; i--)
    {
        fgets(entrada, 100, stdin);
        char *tipo;                      // o tipo (I, R)
        tipo = malloc(sizeof(char) * 2); // separa dois digitos pro tipo
        strncpy(tipo, entrada, 2);

        // como não consegui fazer switch case funcionar, criei um if pra cada método
        if (strstr(tipo, "I") != NULL)
        {
            inserirFim(trim(replaceAll(entrada, "I ", "")));
        }
        else if (strstr(tipo, "R") != NULL)
        {
            printf("(R) %s\n", removerFim().nome);
        }
    }

    // mostrar tudo no final
    mostrar();

    return 0;
}