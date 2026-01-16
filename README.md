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

[📥 Download](#-instalação) • [📚 Documentação](#-documentação-completa) • [⚙️ Configuração](#%EF%B8%8F-configuração) • [🤝 Contribuir](#-contribuindo)

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

1. Baixe o arquivo `Atmosfera-1.0-all.jar` da [página de Releases](https://github.com/MatheusRenzo/Atmosfera/releases).
2. Coloque o JAR na pasta `plugins` do seu servidor.
3. Inicie o servidor para gerar o `config.yml`.
4. Configure o plugin conforme suas preferências.

| Plugin | Obrigatório | Descrição |
|--------|-------------|-----------|
| [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) | Não | Para usar os placeholders em outros plugins |

---

## 📚 Documentação Completa

Preparamos uma **Wiki** detalhada para te ajudar em cada passo:

*   **[🏠 Página Inicial](wiki/Home.md)**: Visão geral do projeto.
*   **[🚀 Instalação](wiki/Instalacao.md)**: Guia passo a passo.
*   **[⚙️ Configuração](wiki/Configuracao.md)**: Explicação de cada linha do `config.yml`.
*   **[📋 Placeholders](wiki/Placeholders.md)**: Lista de variáveis para usar em Scoreboards/TAB.
*   **[💻 Comandos](wiki/Comandos-e-Permissoes.md)**: Lista de comandos e permissões.
*   **[❓ FAQ](wiki/FAQ.md)**: Perguntas frequentes e solução de problemas.

---

## ⚙️ Configuração Rápida

O arquivo `config.yml` permite configurar o essencial:

```yaml
# Fuso horário (exemplo: America/Sao_Paulo)
Timezone: 'America/Sao_Paulo'

# Ativar sincronização de tempo
SyncTime: true

# Ativar sincronização de clima
SyncWeather: true

# Coordenadas e API Key
WeatherLatitude: '-23.5505'
WeatherLongitude: '-46.6333'
APIKey: 'SUA_CHAVE_AQUI'
```

---

## 🤝 Contribuindo

Quer ajudar a melhorar o Atmosfera? Ficamos felizes com sua ajuda!

### 🐛 Encontrou um Bug? (Issues)
Se você achou um erro ou tem uma sugestão, abra uma **Issue**.
1. Vá na aba [Issues](https://github.com/MatheusRenzo/Atmosfera/issues).
2. Clique em **New Issue**.
3. Descreva o problema ou sua ideia.

### 💻 Quer enviar código? (Fork & Pull Request)
1.  Faça um **Fork** do projeto (cria uma cópia no seu perfil).
2.  Crie uma **Branch** para sua modificação (`git checkout -b feature/MinhaMelhoria`).
3.  Faça o **Commit** das suas alterações (`git commit -m 'Adiciona MinhaMelhoria'`).
4.  Faça o **Push** para a Branch (`git push origin feature/MinhaMelhoria`).
5.  Abra um **Pull Request** aqui no repositório original.

---

## 📝 Licença

Este projeto está sob a licença **GPL-3.0**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

**Desenvolvido com ❤️ por [MatheusRenzo](https://github.com/MatheusRenzo)**

[![GitHub](https://img.shields.io/badge/GitHub-MatheusRenzo-black?style=for-the-badge&logo=github)](https://github.com/MatheusRenzo)

</div>
