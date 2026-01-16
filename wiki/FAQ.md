# Perguntas Frequentes (FAQ)

### 1. O clima no jogo não está igual ao da minha cidade. Por que?
Existem algumas razões possíveis:
*   **Delay da API**: O OpenWeatherMap tem um pequeno atraso na atualização.
*   **Cache**: O plugin verifica o clima a cada X minutos (configurável em `WeatherSyncInterval`). Se começou a chover agora na vida real, pode levar alguns minutos para atualizar no jogo.
*   **Coordenadas Erradas**: Verifique se a Latitude e Longitude no `config.yml` estão corretas.

### 2. Configurei tudo mas o clima não muda.
Verifique se você colocou a **API Key** corretamente. Se a chave for inválida ou nova (leva uns 10-20 min para ativar), o plugin não conseguirá baixar os dados.
Use o comando `/atmosfera` para ver se há erros de conexão.

### 3. Os placeholders não funcionam (aparece %atmosfera_time%).
Você precisa instalar o plugin **PlaceholderAPI**.
1.  Instale o PlaceholderAPI.
2.  Use o comando `/papi ecloud download atmosfera` (se disponível) ou apenas reinicie o servidor com o Atmosfera instalado, pois ele registra automaticamente.
3.  Use `/papi reload`.

### 4. O comando /time set parou de funcionar.
Isso é proposital. O Atmosfera bloqueia comandos que mudam o tempo para evitar que a sincronização quebre. Você pode desativar isso no `config.yml` mudando `BlockTimeSetCommand` para `false`, mas não recomendamos.

### 5. O servidor fica lagado com a sincronização?
Não. Todas as chamadas para a API de clima são feitas de forma **assíncrona** (em segundo plano) e não travam o servidor (Main Thread). O impacto na performance é praticamente zero.
