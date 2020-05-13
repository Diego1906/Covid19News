# App Covid-19 News

O aplicativo consome uma API REST para buscar as estatísticas sobre a doença Covid-19 o [Coronavírus](https://pt.wikipedia.org/wiki/Coronav%C3%ADrus).
API utilizada [Covid-19 API](https://api-sports.io/documentation/covid-19) 

**O aplicativo possui quatro telas e elas são:**
  - 1º Tela de dados consolidados do mundo inteiro;
  - 2º Tela de dados por país;
  - 3º Tela que apresenta uma lista de países;
  - 4º Tela que detalha o país selecionado na lista.

**No desenvolvimento do projeto foram utilizadas as tecnologias descritas abaixo:**

- Linguagem de Programação
  - 
  - Kotlin

- Componentes de Arquitetura e Android Jetpack
  -
  - Documentação
    - 
    - [Navigation](https://developer.android.com/guide/navigation)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel#sharing)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - [DataBinding](https://developer.android.com/topic/libraries/data-binding)
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    - [Coroutines](https://developer.android.com/topic/libraries/architecture/coroutines)
    - [Room](https://developer.android.com/training/data-storage/room)
    - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
    - [Multidex](https://developer.android.com/studio/build/multidex)
    - [Fragments](https://developer.android.com/guide/components/fragments)
    - [Android KTX](https://developer.android.com/kotlin/ktx)
    
- Bibliotecas Externas
  - 
  - [Retrofit](https://square.github.io/retrofit/) é utilizada para fazer requisições HTTP a serviços Web
  - [Moshi](https://github.com/square/moshi) é uma moderna biblioteca JSON para Android, Kotlin e Java. Ela faz de forma fácil a conversão de um JSON para objetos Kotlin e Java
  - [Koin](https://insert-koin.io/) é utilizada para fazer injeção de depedência
  - [Timber](https://github.com/JakeWharton/timber) é um logger com uma API pequena e extensível que fornece utilidade sobre a classe Log normal do Android
  
- Bibliotecas Internas
  -
  - [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) é utilizada para criar listas de informações, objetos, imagens que serão apresentados na tela
  - [Material Design](https://material.io/) é a orientação de código e projeto oficial do Google
  
- Design de Arquitetura e Padrões
  - 
  - [MVVM](https://developer.android.com/jetpack/docs/guide) é o padrão de design de arquitetura de software que a Google indica para os novos desenvolvimento. Os novos componentes de arquitetura já são lançados com suporte a esse tipo de padrão
  - [Repository](https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754) é estratégia para abstrair o acesso aos dados. Ele é composto pelo código em uma aplicação que lida com o armazenamento e a recuperação de dados.
  

