# Project name: btg-jokenpo-project
Desafio: Desenvolver o jogo Jokenpo - API Rest em Java utilizando Spring

#### Como funciona o jogo:

O programa analisa o resultado de múltiplos jogadores em um jogo de Jokenpo.

A solução é resiliente para possíveis mudanças, como por exemplo adicionar, remover jogadas e inserir novos jogadores.

#### Requisitos:

Os jogadores deverão informar as entradas através das jogadas e o sistema deverá indicar qual o jogador ganhador.

As entradas das jogadas deverão ser disponibilizadas através de APIs REST, além da aplicação disponibilizar APIs para realização do cadastro dos jogadores e das jogadas também ter a possibilidade de consulta-los e exclui-los.

## Fluxo do jogo:

![Fluxo do jogo](https://i.imgur.com/4lAyDPi.jpg)

**Exemplos (passo-a-passo):**
1. 	*Entrada 1* – Jogador 1 e Jogada Pedra
2.	*Entrada 2* – Jogador 2 e Jogada Tesoura
3.	*Entrada 3* – Jogador 3 e Jogada Tesoura
4.	*Jogar* 
5.	*Resultado* - Jogador 1 Vitória

## Stack de desenvolvimento (Detalhes técnicos):

- Linguagem de desenvolvimento: Java 8;
  
- Framework utilizado: Spring Boot 2.2;

- Gerenciamento de dependências: Gradle; 

- Realização de testes unitários: JUnit;
  
- Para armazenamento em memória foi utilizado o padrão Singleton.

- Não há utilização de banco de dados para esse projeto;

- Não há utilização de bibliotecas utilitárias externas;

- Para esse projeto foi utilizado boas práticas de desenvolvimento (Clean Code): Príncipios SOLID, comentários apenas quando necessários, nomes de classes e métodos precisos para melhor entendimento do código, DRY (Don't repeat yourself), entre outros padrões e boas práticas.

#### Observações e mais regras sobre o jogo:

- Em uma partida há possibilidade de ter mais de um vencedor, pois os jogadores podem ter jogadas semelhantes como citado acima. Por exemplo: Dois jogadores escolheram ser "SPOCK" e ganharam de todos os demais.

- Para ser possível iniciar o jogo, é necessário cadastrar os jogadores e posteriormente será possível fazer as escolhas das jogadas. No final das jogadas pode solicitar o resultado da partida.

- O resultado final apresenta o ganhador(es) e o histórico das jogadas realizados de cada um.

- O resultado de cada partida as jogadas são apagadas para facilitar o início de um novo jogo com outras jogadas.

- Nomes repetidos (duplicados) não são permitidos, valendo também para discriminação entre letras maiúsculas e minúsculas. 

- A fim de validar o o campo "playerName" (Nome do jogador) foi determinado o mínimo de 1 caracteres e máximo de 120.
  
- Os jogadores podem utilizar das mesmas jogadas. Por exemplo: O "Jogador1" pode ser "LIZARD" e o "Jogador2" também.


## Exemplos das chamadas nos ENDPOINTS:

### 1. Jogador (Player)

##### 1.1 Inserção (Cadastro)

###### 1.1.1 Exemplo de chamada:

```
curl --location --request POST 'http://localhost:8080/v1/btg/jokenpo/players' \
--header 'Content-Type: application/json' \
--data-raw '{
	"playerName": "Player1"
}'
```

###### 1.1.2 Exemplo de retorno da chamada com Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:11:13.680+00:00"
    },
    "data": {
        "playerName": "Player1"
    }
}
```

##### 1.2 Exclusão (Delete)

###### 1.2.1 Exemplo de chamada:

```
curl --location --request DELETE 'http://localhost:8080/v1/btg/jokenpo/players?name=Player1'
```

##### 1.2.2 Exemplo de retorno da chamada com Sucesso - 200 OK

```json
[
    {
        "playerName": "Player2"
    },
    {
        "playerName": "Player3"
    },
    {
        "playerName": "Player4"
    }
]
```

##### 1.3 Listagem dos jogadores

###### 1.3.1 Exemplo de chamada:

```
curl --location --request GET 'http://localhost:8080/v1/btg/jokenpo/players'
```

##### 1.3.2 Exemplo de retorno da chamada com Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:26:22.624+00:00"
    },
    "data": [
        {
            "playerName": "Player2"
        },
        {
            "playerName": "Player3"
        },
        {
            "playerName": "Player4"
        }
    ]
}
```

### 2. Jogada

##### 2.1 Inserção (Cadastro)

###### 2.1.1 Exemplo de chamada

```
curl --location --request POST 'http://localhost:8080/v1/btg/jokenpo/moves' \
--header 'Content-Type: application/json' \
--data-raw '{
	"playerName": "Player1",
	"movement": "PAPER"
}'
```

##### 2.2 Exemplo de retorno de Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:30:15.501+00:00"
    },
    "data": {
        "player": {
            "playerName": "Player1"
        },
        "movement": "PAPER"
    }
}
```

##### 2.2 Exclusão

###### 2.2.1 Exemplo de chamada:

```
curl --location --request DELETE 'http://localhost:8080/v1/btg/jokenpo/moves?playerName=Player1'
```

##### 2.2.2 Exemplo de retorno de Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:35:24.590+00:00"
    },
    "data": [
        {
            "player": {
                "playerName": "Player2"
            },
            "movement": "STONE"
        },
        {
            "player": {
                "playerName": "Player3"
            },
            "movement": "PAPER"
        }
    ]
}
```

##### 2.3 Listagem

###### 2.3.1 Exemplo de chamada:

```
curl --location --request GET 'http://localhost:8080/v1/btg/jokenpo/moves'
```

##### 2.3.2 Exemplo de retorno de Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:37:23.473+00:00"
    },
    "data": [
        {
            "player": {
                "playerName": "Player2"
            },
            "movement": "STONE"
        },
        {
            "player": {
                "playerName": "Player3"
            },
            "movement": "PAPER"
        }
    ]
}
```

### 3. Resultado do jogo (Play)

##### 3.1 Obter resultado da partida

###### 3.1.1 Exemplo de chamada:

```
curl --location --request GET 'http://localhost:8080/v1/btg/jokenpo/play'
```

###### 3.1.2 Exemplo de retorno de Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:41:09.956+00:00"
    },
    "data": {
        "matchResult": "PLAYER3 IS THE WINNER!",
        "history": [
            "Player2 (STONE)",
            "Player3 (PAPER)"
        ]
    }
}
```


###### 3.1.3 Exemplo de retorno com erro - 404 NOT FOUND (Não encontrou jogadores cadastrados)

```json
{
    "timestamp": "2020-06-07T03:44:29.798+00:00",
    "status": 404,
    "error": "Object not found",
    "message": "There are no registered players",
    "path": "/v1/btg/jokenpo/play"
}
```

###### 3.1.4 Exemplo de retorno com erro - 404 NOT FOUND (Há jogadores que ainda não cadastraram jogadas)

```json
{
    "timestamp": "2020-06-07T03:49:01.118+00:00",
    "status": 404,
    "error": "Object not found",
    "message": "There are players who have not yet chosen",
    "path": "/v1/btg/jokenpo/play"
}
```

##### 3.2 Limpar todos os dados

###### 3.2.1 Exemplo de chamada:

```
curl --location --request DELETE 'http://localhost:8080/v1/btg/jokenpo/play'
```

###### 3.2.2 Exemplo de retorno de Sucesso - 200 OK

```json
{
    "meta": {
        "timestamp": "2020-06-07T03:50:42.080+00:00"
    },
    "data": []
}
```