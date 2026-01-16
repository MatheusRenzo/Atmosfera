# Guia de Configuração

O arquivo `config.yml` é o coração do Atmosfera. Aqui você define como a sincronização deve funcionar.

## Exemplo Completo

```yaml
# Arquivo de Configuração do Atmosfera v1.0

################################ CONFIGURAÇÕES GERAIS ########################################################
UpdateCheckInterval: 1734000 # Verifica atualizações a cada 24h (em ticks)
Debug: false # Ative apenas se estiver tendo problemas
###############################################################################################################

################################# TEMPO (HORÁRIO) ############################################################
SyncTime: true # Ativa/Desativa sincronização de horário

# Define se sincroniza TODOS os mundos ou apenas os listados
TimeSyncAllWorlds: true 
TimeSyncWorlds:
  - world
  - survival

# Bloqueia o comando /time set para evitar desincronização
BlockTimeSetCommand: true

# Impede jogadores de dormir para pular a noite (já que a noite é real)
DisableBedsAtNight: true
DisableBedsAtNightMessage: 'Você não pode pular a noite! Ela é sincronizada com o mundo real.'

# Fuso Horário (Timezone)
# Importante: Use o formato "Continente/Cidade"
Timezone: 'America/Sao_Paulo'

# Configuração de Nascer/Pôr do Sol
# 'default': Padrão do Minecraft
# 'real': Baseado na latitude/longitude (Recomendado)
# 'custom': Horários fixos
SunriseSunset: real

# Coordenadas (Usado se SunriseSunset: real)
SunriseSunsetLatitude: '-23.5505'
SunriseSunsetLongitude: '-46.6333'
###############################################################################################################

################################# CLIMA (CHUVA/TEMPESTADE) ###################################################
SyncWeather: true # Ativa/Desativa sincronização de clima

WeatherSyncAllWorlds: true
WeatherSyncWorlds:
  - world

BlockWeatherCommand: true

# Impede dormir durante tempestades
DisableBedsDuringThunder: true
DisableBedsDuringThunderMessage: 'Você não pode pular a tempestade!'

# Intervalo de verificação (em ticks). 6000 ticks = 5 minutos.
WeatherSyncInterval: 6000

# SUA CHAVE API (Obrigatória para funcionar o clima)
# Pegue em: https://openweathermap.org/api
APIKey: 'SUA_CHAVE_AQUI'

# Coordenadas para verificação do clima
WeatherLatitude: '-23.5505'
WeatherLongitude: '-46.6333'
###############################################################################################################
```

## Explicação Detalhada

### Timezone
Define qual relógio o servidor vai seguir.
*   **Exemplos**: `America/Sao_Paulo`, `Europe/London`, `America/New_York`, `Asia/Tokyo`.
*   [Lista completa de Timezones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones#List)

### API Key (OpenWeatherMap)
Para o clima funcionar, você precisa de uma chave gratuita:
1.  Crie uma conta em [OpenWeatherMap](https://openweathermap.org/).
2.  Vá em "My API Keys".
3.  Copie a chave e cole em `APIKey` no arquivo `config.yml`.
4.  **Atenção**: Pode levar alguns minutos para a chave ativar após criar a conta.

### Coordenadas (Latitude/Longitude)
Para saber se está chovendo ou qual a hora exata do pôr do sol, o plugin precisa saber "onde" ele está.
*   Use sites como [LatLong.net](https://www.latlong.net/) para pegar as coordenadas da sua cidade.
