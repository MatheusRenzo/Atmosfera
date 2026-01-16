# Placeholders

O Atmosfera possui integração nativa com o **PlaceholderAPI**. Isso significa que você pode exibir informações do plugin em:
*   Scoreboards (TAB, QuickBoard, etc)
*   Tablists
*   Hologramas
*   Menus (DeluxeMenus, ChestCommands)
*   Chat (Essentials, ChatColor)
*   Mensagens de boas-vindas

## Lista Completa

| Placeholder | Descrição | Exemplo de Saída |
| :--- | :--- | :--- |
| `%atmosfera_time%` | Horário atual (HH:mm) | `14:35` |
| `%atmosfera_time_sec%` | Horário com segundos | `14:35:12` |
| `%atmosfera_time_12h%` | Horário em formato AM/PM | `02:35 PM` |
| `%atmosfera_date%` | Data atual (Dia/Mês/Ano) | `16/01/2026` |
| `%atmosfera_status%` | Status atual do clima (Texto) | `Limpo ☀️`, `Chovendo 🌧️` |
| `%atmosfera_weather_icon%` | Apenas o ícone do clima | `☀`, `🌧`, `⛈` |
| `%atmosfera_pvp%` | Status do PVP (Curto) | `ON`, `OFF` |
| `%atmosfera_pvp_status%` | Status do PVP (Formatado) | `&c&l⚔ PVP ATIVO` |
| `%atmosfera_is_night%` | Retorna se é noite (Boolean) | `true`, `false` |
| `%atmosfera_period%` | Período do dia | `Manhã`, `Tarde`, `Noite` |
| `%atmosfera_period_icon%` | Ícone do período (Sol/Lua) | `☀`, `☾` |

## Como usar

Basta colocar o placeholder no arquivo de configuração do outro plugin.

**Exemplo no TAB (config.yml):**
```yaml
custom-tabs:
  default:
    footer:
      - "&7Horário Real: &f%atmosfera_time%"
      - "&7Clima: &f%atmosfera_status%"
```

**Exemplo no HolographicDisplays:**
```
/hd create relogio &eHorário de Brasília: &f%atmosfera_time%
```
