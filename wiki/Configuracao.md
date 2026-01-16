# Configurando o Atmosfera

O arquivo `config.yml` controla todo o comportamento do plugin. Abaixo explicamos cada seção detalhadamente.

## 📂 Localização do Arquivo
O arquivo fica em: `plugins/Atmosfera/config.yml`.

## 🌍 Sincronização de Tempo (TimeSync)

Esta seção define como o relógio do jogo se comporta.

```yaml
SyncTime: true              # Ativa a sincronização de tempo
TimeSyncAllWorlds: true     # Se true, afeta todos os mundos normais
Timezone: 'America/Sao_Paulo' # O fuso horário que o servidor seguirá
```

### Como escolher o Timezone?
Você deve usar o formato `Continente/Cidade`.
*   [Clique aqui para ver a lista de Timezones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones#List)

### Nascer e Pôr do Sol
O plugin pode calcular exatamente quando o sol nasce e se põe na sua cidade.

```yaml
SunriseSunset: real               # Use 'real' para cálculo preciso
SunriseSunsetLatitude: '-23.5505' # Latitude da sua cidade
SunriseSunsetLongitude: '-46.6333' # Longitude da sua cidade
```

> **💡 Onde pegar as coordenadas?**
> Acesse [LatLong.net](https://www.latlong.net/), digite o nome da sua cidade e copie a Latitude e Longitude.

---

## 🌧️ Sincronização de Clima (WeatherSync)

Se chover na vida real, choverá no jogo.

```yaml
SyncWeather: true
APIKey: 'SUA_CHAVE_AQUI'
WeatherLatitude: '-23.5505'
WeatherLongitude: '-46.6333'
```

### 🔑 Como pegar a API Key (Gratuito)
O clima depende do OpenWeatherMap. Siga os passos:

1.  Crie uma conta em [OpenWeatherMap.org](https://home.openweathermap.org/users/sign_up).
2.  Após confirmar o e-mail, vá para a aba **[API Keys](https://home.openweathermap.org/api_keys)**.
3.  Copie a chave (é um código longo de letras e números).
4.  Cole no `config.yml` onde diz `APIKey`.
5.  *Nota: Pode levar de 10 a 30 minutos para a chave ativar após ser criada.*

---

## ⚔️ Eventos e Regras

```yaml
# Bloqueia comandos que podem "quebrar" a sincronização
BlockTimeSetCommand: true
BlockWeatherCommand: true

# Impede pular a noite, já que ela deve durar o tempo real
DisableBedsAtNight: true

# Aviso quando o jogador tenta dormir
DisableBedsAtNightMessage: 'Você não pode pular a noite! Ela é sincronizada com o mundo real.'
```
