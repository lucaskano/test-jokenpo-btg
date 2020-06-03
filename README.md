# Project name: btg-jokenpo-project
Desafio: Desenvolver o jogo Jokenpo - API Rest em Java utilizando Spring

#### Como funciona o jogo:

O programa analisa o resultado de múltiplos jogadores em um jogo de Jokenpo.

A solução é resiliente para possíveis mudanças, como por exemplo adicionar, remover jogadas e inserir novos jogadores.

#### Requisitos:

Os jogadores deverão informar as entradas através das jogadas e o sistema deverá indicar qual o jogador ganhador.

As entradas das jogadas deverão ser disponibilizadas através de APIs REST, além da aplicação disponibilizar APIs para realização do cadastro dos jogadores e das jogadas também ter a possibilidade de consulta-los e exclui-los.

#### Fluxo do jogo:

![Fluxo do jogo](https://i.imgur.com/4lAyDPi.jpg)

**Exemplos (passo-a-passo):**
1. 	*Entrada 1* – Jogador 1 e Jogada Pedra
2.	*Entrada 2* – Jogador 2 e Jogada Tesoura
3.	*Entrada 3* – Jogador 3 e Jogada Tesoura
4.	*Jogar* 
5.	*Resultado* - Jogador 1 Vitória

#### Stack de desenvolvimento (Detalhes técnicos):

- Linguagem de desenvolvimento: Java 8;
  
- Framework utilizado: Spring Boot 2.2;

- Gerenciamento de dependências: Gradle; 

- Realização de testes unitários: JUnit;

- Não há utilização de banco de dados para esse projeto;

- Não há utilização de bibliotecas utilitárias externas;

- Para esse projeto foi utilizado boas práticas de desenvolvimento (Clean Code): Príncipios SOLID, comentários apenas quando necessários, nomes de classes, métodos e variáveis precisos para melhor entendimento do código, DRY (Don't repeat yourself), entre outros padrões e boas práticas.

