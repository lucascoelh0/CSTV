# CSTV
Este aplicativo traz informações de partidas de CS GO.

Para rodar o projeto, é necessário criar uma variável API_KEY="SUA CHAVE AQUI" no local.properties

# O aplicativo é dividido em 4 módulos:

**Core**: possui lógica comum entre módulos.

**Data**: contém arquivos de serviço de API, utilitários e dtos.

**Domain**: contém a lógica de negócios do aplicativo. Possui os use cases e repositórios.

**App**: contém arquivos relacionados à UI, como composables e viewmodels.

# Bibliotecas utilizadas

Jetpack Compose

Hilt para injeção de dependência.

JUnit e MockK para testes unitários.

Retrofit e OkHttp para lidar com chamadas de api.

Moshi para deserialização.

Coils para carregamento de imagens.

NetworkResponseAdapter para tratamento de chamadas de api.

Turbine para testar Flows
