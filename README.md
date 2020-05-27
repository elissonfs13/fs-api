## Bem vindo ao simulador da Copa do Mundo de Futebol!

Este projeto foi desenvolvido com o objetivo de servir como laboratório para aprendizado e testes de tecnologias utilizadas no mercado de desenvolvimento de sistemas web atualmente, além de unir duas paixões de seu desenvolvedor: futebol e desenvolvimento de sistemas.

### Regras:
Consiste em simular uma copa do mundo de futebol nos moldes dos disputados a partir da Copa do Mundo de 1998. 8 grupos com 4 seleções cada, onde os 2 primeiros se classificam para as oitavas-de-finais. A partir desse momento os vencedores avançam para as quartas-de-finais, semi-finais e finais, onde teremos o campeão, vice-campeão, terceiro e quarto lugares definidos para cada edição gerada do campeonato.

As seleções mais famosas podem ser cadastradas automaticamente, assim como outras seleções podem ser cadastradas a qualquer tempo. Também há a opção de exclusão de seleções, desde que a seleção ainda não tenha participado de nenhum campeonato.

É definido um nível para cada seleção, de acordo com sua expressão na vida real. Este nível varia de 2 a 6 e é responsável pela definição da probabilidade da geração de gols da seleção em cada jogo. Assim, uma seleção mais forte (nível mais alto) tem maior probabilidade de vencer uma partida do que uma seleção mais fraca (nível mais baixo). Este nível pode ser alterado para cada seleção a qualquer momento.

Em cada jogo é gerado aleatoriamente o número de gols de cada seleção, com um valor entre 0 e o valor de seu nível. Por exemplo, a seleção brasileira (nível 6) em cada jogo pode ter entre 0 e 5 gols. Já uma seleção com menor expressão (nível 2) poderá ter apenas 0 ou 1 gol em cada jogo. Esse mecanismo se justifica com o objetivo de deixar a simulação mais real, fazendo com que uma seleção mais forte tenha maior probabilidade de vencer uma seleção mais fraca, mas não significa que o contrário não possa ocorrer (zebras acontecem assim como na vida real).

O resultado de um jogo, bem como as classificações e progressões das seleções em cada campeonato são gerados e administrados automaticamente mediante as regras definidas no sistema. Assim, ao gerar um novo campeonato, todos os jogos e classificações são gerados automaticamente e apenas exibidos para conferência e salvos no banco de dados para posterior uso dos dados em estatísticas.

Para iniciar um novo campeonato é necessário selecionar 32 seleções (manualmente ou aleatoriamente), sendo que a primeira seleção selecionada se torna a sede do campeonato gerado.

### Tecnologias Utilizadas:
Na primeira versão deste projeto foi desenvolvido uma API REST com as tecnologias de back-end e front-end em um mesmo projeto, onde eram executadas em conjunto. As tecnologias utilizadas na primeira versão deste projeto foram:
- Java versão 8 para desenvolvimento da API e regras de negócio do projeto;
- Framework AngularJS para o desenvolvimento front-end da aplicação web;
- Spring Boot e Spring Data para configuração e execução do projeto;
- Banco de dados PostgreSQL como solução para armazenar os dados gerados;
- Framework Bootstrap versão 3 para template de sistema web responsivo;

Na segunda versão do projeto algumas alterações foram realizadas:
- Refatoração da API do back-end para atender os princípios SOLID de desenvolvimento de sistemas e a metodologia Domain-Driven Design (DDD);
- Cobertura de testes do código legado e de novos códigos gerados no back-end;
- Front-end atualizado para Angular versão 9. Manteve-se o layout desenvolvido na primeira versão, fazendo os ajustes necessários;
- Front-end e back-end separados em projetos individuais para garantir sua independência, conforme padrão de mercado usualmente utilizado;
- Utilização da API Google Charts para geração e exibição de gráficos com informações dos campeonatos gerados;
- Utilização da API amCharts versão 4 para geração e exibição de mapas com informações dos campeonatos gerados;
- Utlização do Projeto Lombok para ganho de produtividade no desenvolvimento da aplicação;

### Autor:
Elisson Francisco da Silva

email: elissonfs@gmail.com

cel: (12)997477873

linkedIn: elissonfs
