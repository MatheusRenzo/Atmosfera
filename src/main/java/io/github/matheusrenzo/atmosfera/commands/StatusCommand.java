package io.github.matheusrenzo.atmosfera.commands;

import io.github.matheusrenzo.atmosfera.Atmosfera;
import io.github.matheusrenzo.atmosfera.ConfigManager;
import io.github.matheusrenzo.atmosfera.requests.SunriseSunsetRequestObject;
import io.github.matheusrenzo.atmosfera.requests.WeatherRequestObject;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class StatusCommand implements CommandExecutor {
    private final Atmosfera plugin;
    private final ConfigManager config;

    public StatusCommand(Atmosfera plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "========== Atmosfera Status ==========");

        TimeZone timezone = config.getTimeZone();
        if (timezone == null) {
            timezone = TimeZone.getDefault();
        }

        sender.sendMessage(ChatColor.YELLOW + "--- Sincronização de Tempo ---");
        sender.sendMessage(ChatColor.WHITE + "Sincronização Ativada: "
                + (config.isTimeEnabled() ? ChatColor.GREEN + "Sim" : ChatColor.RED + "Não"));

        if (config.isTimeEnabled()) {
            sender.sendMessage(ChatColor.WHITE + "Timezone: " + ChatColor.AQUA + timezone.getID());

            Calendar cal = Calendar.getInstance(timezone);
            LocalTime currentTime = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND));
            sender.sendMessage(ChatColor.WHITE + "Hora Real: " + ChatColor.GREEN
                    + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            ZonedDateTime zdt = ZonedDateTime.now(timezone.toZoneId());
            sender.sendMessage(ChatColor.WHITE + "Hora (12h): " + ChatColor.GREEN
                    + zdt.format(DateTimeFormatter.ofPattern("h:mm:ss a")));

            String sunriseSunsetMode = config.getSunriseSunset();
            sender.sendMessage(ChatColor.WHITE + "Modo Sunrise/Sunset: " + ChatColor.AQUA + sunriseSunsetMode);

            if (sunriseSunsetMode.equals("real")) {
                String sunriseTime = "N/A";
                String sunsetTime = "N/A";

                try {
                    SunriseSunsetRequestObject sunriseSunset = new SunriseSunsetRequestObject(
                            timezone,
                            config.getSunriseSunsetLatitude(),
                            config.getSunriseSunsetLongitude());
                    sunriseTime = sunriseSunset.getSunriseTime();
                    sunsetTime = sunriseSunset.getSunsetTime();
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Erro ao buscar sunrise/sunset da API: " + e.getMessage());
                    sunriseTime = "6:00:00 AM (fallback)";
                    sunsetTime = "6:00:00 PM (fallback)";
                }

                sender.sendMessage(ChatColor.WHITE + "Nascer do Sol (API): " + ChatColor.GREEN + sunriseTime);
                sender.sendMessage(ChatColor.WHITE + "Pôr do Sol (API): " + ChatColor.GREEN + sunsetTime);
            }

            sender.sendMessage(ChatColor.YELLOW + "--- Tempos dos Mundos (Ticks) ---");
            for (World world : config.getTimeSyncWorlds()) {
                long worldTime = world.getTime();
                String dayPhase = getDayPhase(worldTime);
                sender.sendMessage(ChatColor.WHITE + world.getName() + ": " + ChatColor.AQUA + worldTime + " ticks "
                        + ChatColor.GRAY + "(" + dayPhase + ")");
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "--- Sincronização de Clima ---");
        sender.sendMessage(ChatColor.WHITE + "Sincronização Ativada: "
                + (config.isWeatherEnabled() ? ChatColor.GREEN + "Sim" : ChatColor.RED + "Não"));

        if (config.isWeatherEnabled()) {
            sender.sendMessage(ChatColor.WHITE + "Coordenadas: " + ChatColor.AQUA
                    + config.getWeatherLatitude() + ", " + config.getWeatherLongitude());

            try {
                WeatherRequestObject weather = new WeatherRequestObject(
                        config.getAPIKey(),
                        config.getWeatherLatitude(),
                        config.getWeatherLongitude());
                sender.sendMessage(ChatColor.WHITE + "API Weather - Chovendo: "
                        + (weather.isRaining() ? ChatColor.BLUE + "Sim" : ChatColor.GREEN + "Não"));
                sender.sendMessage(ChatColor.WHITE + "API Weather - Trovejando: "
                        + (weather.isThundering() ? ChatColor.DARK_PURPLE + "Sim" : ChatColor.GREEN + "Não"));
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Erro ao buscar clima da API: " + e.getMessage());
            }

            sender.sendMessage(ChatColor.YELLOW + "--- Clima dos Mundos ---");
            for (World world : config.getWeatherSyncWorlds()) {
                boolean storm = world.hasStorm();
                boolean thunder = world.isThundering();
                sender.sendMessage(ChatColor.WHITE + world.getName() + ": " +
                        "Tempestade=" + (storm ? ChatColor.BLUE + "Sim" : ChatColor.GREEN + "Não") + ChatColor.WHITE
                        + ", " +
                        "Trovão=" + (thunder ? ChatColor.DARK_PURPLE + "Sim" : ChatColor.GREEN + "Não"));
            }
        }

        sender.sendMessage(ChatColor.GOLD + "=======================================");
        return true;
    }

    private String getDayPhase(long ticks) {
        if (ticks >= 0 && ticks < 1000)
            return "Nascer do Sol";
        if (ticks >= 1000 && ticks < 6000)
            return "Manhã";
        if (ticks >= 6000 && ticks < 12000)
            return "Meio-dia/Tarde";
        if (ticks >= 12000 && ticks < 13000)
            return "Pôr do Sol";
        if (ticks >= 13000 && ticks < 18000)
            return "Noite";
        if (ticks >= 18000 && ticks < 23000)
            return "Meia-noite";
        if (ticks >= 23000 && ticks < 24000)
            return "Madrugada";

        return "Desconhecido";
    }
}
