# Chuck Norris Facts

O aplicativo permite realizar buscas de "fatos" sobre o Chuck Norris. Isto se dá através do consumo da API [chucknorris.io]( https://chucknorris.io/).

## Telas
![Tela de erro](/pictures/user_first_time_screen.jpg)
![Tela de erro](/pictures/search_screen.jpg)
![Tela de erro](/pictures/fact_catalog_loading_screen.jpg)
![Tela de erro](/pictures/fact_catalog_screen.jpg)
![Tela de erro](/pictures/without_result_screen.jpg)
![Tela de erro](/pictures/error_screen.jpg)

## Ambiente de instalação
* 1: Instale o Android Studio;
* 2: Abra a aplicação;
* 3: Sincronize o projeto;
* 4: Rode o aplicativo em um simulador ou em um device externo.

## Automação
Ktlint - a task valida se o padrão do código está de acordo com o lint. Utilize o comando `./gradlew ktlint` para que a validação seja feita.

KtlintFormat - esta tarefa modifica o código para que ele siga o padrão do lint. Utilize o comando `./gradlew ktlintFormat` para que o código seja ajustado.

Test - Utilize o comando `./gradlew test` para que todos os testes sejam executados.

## Integração Contínua
**GitHub CI**

Ferramenta de CI que permite a criação de workflows personalizados para repositórios no GitHub.

**Workflows**

Nesta aplicação foram criados dois workflows. O primeiro para branche `Master` e o segundo para branches de `featureas e fixes`. A seguir estão as descrições dos workflows.

Master - testes unitários, Ktlint e geração de APK.

Featureas e Fixes - testes unitários e Ktlint.

 ## Arquitetura
 A aplicação busca o desacoplamento e a escalabilidade em sua arquitetura, fazendo uso do Clean Architecture e do MVVM.

 ## Principais dependências
**Koin** - _injeção de dependências_
 <p>Biblioteca escolhida por sua simples implementação. Como ponto negativo, há alguma perda de performance quando comparada com outras concorrentes, como o Dagger. Não há uma perda significativa para esta aplicação.</p>

**Coroutines** - _lidando com threads e assincronismo_
 <p>Abordagem sugerida pela Google e com um bom funcionamento com o Live Data, faz bom uso das threads e da Thread Pool do dispositivo, melhorando a performance da aplicação. Quando comparada com a sua principal concorrente, o RxJava, tem como ponto positivo o seu menor tamanho e negativo o seu tratamento de erros, um pouco mais manual.</p>

**Room** - _persistência de dados_
 <p>Camada de abstração sobre o SQLite, o Room é um facilitador para persistir dados no banco do aparelho. Outro ponto a ser destacado é a disponibilização dos dados de forma reativa, que permite que a View observe diretamente os dados do banco.</p>

**MockK** - _testes unitários_ 
 <p>O MockK é uma lib de testes para a linguagem Kotlin. Sua utilização é simples e tem como ponto positivo perante aos concorrêntes, a possibilidade de mockar métodos de Objects e Companion Objects sem precisar criar interfaces. Por outro lado, é uma biblioteca exclusiva para Kotlin, não funcionando com a linguagem Java.</p>
 
 **Espresso** - _testes de ui_ 
 <p>Biblioteca da Google que é utilizada para testes de tela no ambiente Android. Como alternativas, temos o Robolectric e o Kakao (que faz uso interno do Espresso).</p>

**Retrofit** - _requisições HTTP_
 <p>Retrofit é a biblioteca mais difundida para lidar com requisições HTTP, além de possuir uma fácil implementação.</p>
 
 **Shimmer** - _animação_
 <p>A biblioteca do Facebook permite que você introduza animações com efeito de shimmer de maneira simples. Ela é muito utilizada para sinalizar o carregamento de algum conteúdo.</p>
 
## Testes

**Testes de UI** - _Espresso_
 <p>No módulo FactCatalog foram feitos testes na classe FactCatalogFragment.</p>
 
**Testes unitários** - _MockK_
 <p>No módulo Search foram feitos testes nas classes SearchViewModel, GetRandomCategoriesFromDatabase e SearchHistoricRepositoryImpl. No módulo FactCatalog, a classe FactCatalogAdapter foi testada.</p>
 
## O que eu gostaria de ter feito
 * _aumentado a cobertura de testes unitários;_
 
 * _aumentado a cobertura de testes de ui;_
    
 * _no módulo Search, ter feito um tratamendo caso a API entregue menos de 8 categorias para serem exibidas como sugestão;_
 
 * _implementado mais animações._
