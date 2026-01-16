<div align="center">

![Atmosfera Logo](media/Gemini_Generated_Image_xm65zhxm65zhxm65.png)

# Atmosfera

### 🌦️ Plugin de sincronização de tempo e clima em tempo real para Minecraft

[![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.13+-green?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![License](https://img.shields.io/badge/License-GPL--3.0-blue?style=for-the-badge)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0-purple?style=for-the-badge)](https://github.com/MatheusRenzo/Atmosfera/releases)

[![Spigot](https://img.shields.io/badge/Spigot-Compatible-yellow?style=flat-square&logo=spigotmc)](https://www.spigotmc.org/)
[![Paper](https://img.shields.io/badge/Paper-Compatible-lightblue?style=flat-square)](https://papermc.io/)
[![PlaceholderAPI](https://img.shields.io/badge/PlaceholderAPI-Supported-green?style=flat-square)](https://www.spigotmc.org/resources/placeholderapi.6245/)

---

**Sincronize o horário e clima do seu servidor Minecraft com o mundo real!**

[📥 Download](#-instalação) • [⚙️ Configuração](#%EF%B8%8F-configuração) • [📋 Placeholders](#-placeholders) • [🤝 Contribuir](#-contribuindo)

</div>

---

## ✨ Funcionalidades

- ⏰ **Sincronização de Horário** - Sincroniza o tempo do jogo com qualquer fuso horário do mundo real
- 🌧️ **Sincronização de Clima** - Clima do servidor baseado em dados reais via OpenWeatherMap API
- ⚔️ **Sistema de PVP Noturno** - PVP ativo automaticamente durante a noite (18h-6h)
- 📢 **Anúncios Automáticos** - Avisos de mudança de horário e clima no chat
- 🔌 **PlaceholderAPI** - Integração completa para usar em outros plugins
- 🌅 **Nascer/Pôr do Sol Real** - Horários de nascer e pôr do sol baseados na localização real

---

## 📥 Instalação

1. Baixe o arquivo `Atmosfera-1.0-all.jar` da [página de releases](https://github.com/MatheusRenzo/Atmosfera/releases)
2. Coloque o JAR na pasta `plugins` do seu servidor
3. Inicie o servidor para gerar o `config.yml`
4. Configure o plugin conforme suas preferências
5. Reinicie o servidor ou use `/atmosfera reload`

### Dependências

| Plugin | Obrigatório | Descrição |
|--------|-------------|-----------|
| [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) | Não | Para usar os placeholders em outros plugins |

---

## ⚙️ Configuração

O arquivo `config.yml` permite configurar:

```yaml
# Fuso horário (exemplo: America/Sao_Paulo)
Timezone: 'America/Sao_Paulo'

# Ativar sincronização de tempo
SyncTime: true

# Ativar sincronização de clima
SyncWeather: true

# Coordenadas para clima e nascer/pôr do sol
WeatherLatitude: '-23.5505'
WeatherLongitude: '-46.6333'

# Chave da API do OpenWeatherMap
APIKey: 'SUA_CHAVE_AQUI'
```

> 💡 **Dica:** Obtenha sua chave API gratuita em [openweathermap.org](https://openweathermap.org/appid)

---

## 🎮 Comandos

| Comando | Permissão | Descrição |
|---------|-----------|-----------|
| `/atmosfera` | `atmosfera.status` | Mostra o status da sincronização |

---

## 📋 Placeholders

Use estes placeholders com PlaceholderAPI em qualquer plugin compatível:

| Placeholder | Descrição | Exemplo |
|-------------|-----------|---------|
| `%atmosfera_time%` | Hora atual (HH:mm) | `14:30` |
| `%atmosfera_time_sec%` | Hora com segundos | `14:30:45` |
| `%atmosfera_time_12h%` | Hora formato 12h | `02:30 PM` |
| `%atmosfera_date%` | Data atual | `16/01/2026` |
| `%atmosfera_status%` | Status do clima | `Limpo ☀️` |
| `%atmosfera_weather_icon%` | Ícone do clima | `☀` `🌧` `⛈` |
| `%atmosfera_pvp%` | Status PVP | `ON` / `OFF` |
| `%atmosfera_pvp_status%` | PVP formatado | `&c&l⚔ PVP ATIVO` |
| `%atmosfera_is_night%` | É noite? | `true` / `false` |
| `%atmosfera_period%` | Período do dia | `Manhã` `Tarde` `Noite` |
| `%atmosfera_period_icon%` | Ícone do período | `☀` / `☾` |

---

## 🔨 Compilando

Requisitos:
- Java 17+
- Gradle

```bash
# Clone o repositório
git clone https://github.com/MatheusRenzo/Atmosfera.git

# Entre na pasta
cd Atmosfera

# Compile
./gradlew shadowJar
```

O JAR será gerado em `build/libs/Atmosfera-1.0-all.jar`

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/NovaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona NovaFeature'`)
4. Push para a branch (`git push origin feature/NovaFeature`)
5. Abrir um Pull Request

---

## 📝 Licença

Este projeto está sob a licença GPL-3.0. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

**Desenvolvido com ❤️ por [MatheusRenzo](https://github.com/MatheusRenzo)**

[![GitHub](https://img.shields.io/badge/GitHub-MatheusRenzo-black?style=for-the-badge&logo=github)](https://github.com/MatheusRenzo)

</div>
