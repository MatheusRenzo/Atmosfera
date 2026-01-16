# Placeholders

O Atmosfera se integra nativamente ao **PlaceholderAPI**. Você pode usar essas variáveis para mostrar o tempo real em Scoreboards, TAB, chat e muito mais.

## 📋 Lista de Variáveis

| Placeholder | Retorno (Exemplo) | Descrição |
| :--- | :--- | :--- |
| `%atmosfera_time%` | `14:30` | Horário (HH:mm) do fuso configurado. |
| `%atmosfera_time_sec%` | `14:30:15` | Horário com segundos. |
| `%atmosfera_time_12h%` | `02:30 PM` | Horário no formato AM/PM. |
| `%atmosfera_date%` | `16/01/2026` | Data atual (DD/MM/AAAA). |
| `%atmosfera_status%` | `Limpo ☀️` | Estado do clima traduzido. |
| `%atmosfera_weather_icon%` | `☀️` | Apenas o ícone do clima. |
| `%atmosfera_pvp%` | `ON` / `OFF` | Se o PVP noturno está ativo. |
| `%atmosfera_pvp_status%` | `⚔ PVP ATIVO` | Texto formatado e colorido do estatus do PVP. |
| `%atmosfera_period%` | `Tarde` | Período do dia (Manhã, Tarde, Noite, Madrugada). |

---

## 💡 Exemplos de Uso

### No TAB (Plugin TAB Reborn)
```yaml
custom-tabs:
  default:
    footer:
      - ""
      - "&bHorário: &f%atmosfera_time%"
      - "&bClima: &f%atmosfera_status%"
```

### No Scoreboard (TitleManager / AnimatedScoreboard)
```yaml
lines:
  - "&7Data: &f%atmosfera_date%"
  - "&7Hora: &f%atmosfera_time_sec%"
  - "&7Mundo: &f%atmosfera_period%"
```

### No Holograma (HolographicDisplays)
Comando:
`/hd create relogio &eHorário Real: &f%atmosfera_time%`
