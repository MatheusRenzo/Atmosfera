package io.github.matheusrenzo.atmosfera;

import io.github.matheusrenzo.atmosfera.commands.StatusCommand;
import io.github.matheusrenzo.atmosfera.requests.*;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public final class Atmosfera extends JavaPlugin {
    private Logger logger;
    private ConfigManager config;
    private int lastAnnouncedHour = -1;
    private boolean wasRaining = false;
    private boolean wasThundering = false;
    private boolean pvpNightActive = false;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("Iniciando Atmosfera...");

        logger.info("Carregando configuração...");
        saveDefaultConfig();
        config = new ConfigManager(this);
        config.refreshValues();

        debug("TimeSync: " + config.isTimeEnabled());
        if (config.isTimeEnabled())
            setupTime();

        debug("WeatherSync: " + config.isWeatherEnabled());
        if (config.isWeatherEnabled())
            setupWeather();

        getServer().getPluginManager().registerEvents(new EventHandlers(this), this);
        getCommand("atmosfera").setExecutor(new StatusCommand(this));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIExpansion(this).register();
            logger.info("PlaceholderAPI encontrada! Expansão registrada.");
        } else {
            logger.warning("PlaceholderAPI não encontrada! Placeholders não funcionarão.");
        }

        debug("Ativando métricas...");
        Metrics metrics = new Metrics(this, 16709);
        metrics.addCustomChart(new SimplePie("weather_sync_enabled", () -> String.valueOf(config.isWeatherEnabled())));
        metrics.addCustomChart(new SimplePie("sunrise_sunset_source", () -> String.valueOf(config.getSunriseSunset())));
        metrics.addCustomChart(new SimplePie("time_sync_enabled", () -> String.valueOf(config.isTimeEnabled())));

        logger.info("Atmosfera iniciado com sucesso!");

        logger.info("Verificando atualizações...");
        logger.info(getUpdateCheck());

        long updateCheckInterval = config.getUpdateCheckInterval();
        if (config.getUpdateCheckInterval() > 0)
            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> logger.info(getUpdateCheck()),
                    updateCheckInterval, updateCheckInterval);
    }

    @Override
    public void onDisable() {
        for (World world : getServer().getWorlds())
            if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                debug("Reativando ciclos normais de luz e clima...");

                if (config.isTimeEnabled())
                    world.setGameRuleValue("doDaylightCycle", "true");
                if (config.isWeatherEnabled())
                    world.setGameRuleValue("doWeatherCycle", "true");
            }

        logger.info("Atmosfera desativado.");
    }

    private void setupTime() {
        debug("Ativando sincronização de timezone...");
        debug("Usando timezone: " + config.getTimeZone().getID());
        debug("Usando coordenadas: " + config.getSunriseSunsetLatitude() + ", " + config.getSunriseSunsetLongitude());

        TimeZone timezone = config.getTimeZone();

        for (World world : config.getTimeSyncWorlds())
            world.setGameRuleValue("doDaylightCycle", "false");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (config.isTimeEnabled()) {
                Calendar cal = Calendar.getInstance(timezone);
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);

                boolean isNightTime = (currentHour >= 18 || currentHour < 6);

                if (currentMinute < 1 && currentHour != lastAnnouncedHour) {
                    lastAnnouncedHour = currentHour;

                    if (isNightTime) {
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(
                                ChatColor.DARK_RED + "" + ChatColor.BOLD + "  ⚔ ═══════════════════════════════ ⚔");
                        Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "       ☠ PVP NOTURNO ATIVO ☠");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.GOLD + "  ⏰ " + ChatColor.YELLOW + "Horário: "
                                + ChatColor.WHITE + String.format("%02d:00", currentHour));
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "  ☾ " + ChatColor.LIGHT_PURPLE
                                + "A noite caiu... " + ChatColor.GRAY + "Cuidado ao explorar!");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RED + "  ⚠ " + ChatColor.WHITE + "PVP está " + ChatColor.RED
                                + ChatColor.BOLD + "LIBERADO" + ChatColor.WHITE + " em todo o servidor!");
                        Bukkit.broadcastMessage(
                                ChatColor.DARK_RED + "" + ChatColor.BOLD + "  ⚔ ═══════════════════════════════ ⚔");
                        Bukkit.broadcastMessage("");

                        if (!pvpNightActive) {
                            pvpNightActive = true;
                            if (currentHour == 18) {
                                Bukkit.getOnlinePlayers().forEach(player -> {
                                    player.sendTitle(
                                            ChatColor.DARK_RED + "" + ChatColor.BOLD + "☠ PVP ATIVO ☠",
                                            ChatColor.GOLD + "A noite chegou... Prepare-se para batalha!",
                                            10, 70, 20);
                                });
                            }
                        }
                    } else {
                        Bukkit.broadcastMessage(ChatColor.GOLD + "☀ " + ChatColor.YELLOW + "Horário: " +
                                ChatColor.WHITE + String.format("%02d:00", currentHour) +
                                ChatColor.GREEN + " - Dia tranquilo!");

                        if (pvpNightActive && currentHour == 6) {
                            pvpNightActive = false;
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage(
                                    ChatColor.GREEN + "" + ChatColor.BOLD + "  ☀ ═══════════════════════════════ ☀");
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "       ✦ O DIA NASCEU ✦");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage(ChatColor.GREEN + "  ✔ " + ChatColor.WHITE + "PVP está "
                                    + ChatColor.GREEN + ChatColor.BOLD + "DESATIVADO" + ChatColor.WHITE + "!");
                            Bukkit.broadcastMessage(ChatColor.AQUA + "  ☮ " + ChatColor.WHITE
                                    + "Aproveite o dia para farmar e construir!");
                            Bukkit.broadcastMessage(
                                    ChatColor.GREEN + "" + ChatColor.BOLD + "  ☀ ═══════════════════════════════ ☀");
                            Bukkit.broadcastMessage("");

                            Bukkit.getOnlinePlayers().forEach(player -> {
                                player.sendTitle(
                                        ChatColor.GREEN + "" + ChatColor.BOLD + "☀ DIA NASCEU ☀",
                                        ChatColor.YELLOW + "PVP desativado! Hora de farmar!",
                                        10, 70, 20);
                            });
                        }
                    }
                }

                for (World world : config.getTimeSyncWorlds()) {
                    try {
                        String sunriseSunsetMode = config.getSunriseSunset();
                        String sunriseTime, sunsetTime;

                        if (sunriseSunsetMode.equals("real")) {
                            SunriseSunsetRequestObject sunriseSunset = new SunriseSunsetRequestObject(
                                    timezone,
                                    config.getSunriseSunsetLatitude(),
                                    config.getSunriseSunsetLongitude());
                            sunriseTime = sunriseSunset.getSunriseTime();
                            sunsetTime = sunriseSunset.getSunsetTime();
                        } else if (sunriseSunsetMode.equals("custom")) {
                            sunriseTime = config.getSunriseCustomTime();
                            sunsetTime = config.getSunsetCustomTime();
                        } else {
                            sunriseTime = "5:02:27 AM";
                            sunsetTime = "6:36:36 PM";
                        }

                        world.setTime(calculateWorldTime(cal, sunriseTime, sunsetTime));
                    } catch (Exception e) {
                        logger.severe("Erro ao buscar sunrise/sunset: " + e.getMessage());
                        world.setTime(calculateWorldTime(cal, "6:00:00 AM", "6:00:00 PM"));
                    }
                }
            }
        }, 0L, config.getTimeSyncInterval());

        debug("Sincronização de tempo ativada");
    }

    private void setupWeather() {
        debug("Ativando sincronização de clima...");

        try {
            new WeatherRequestObject(config.getAPIKey(), config.getWeatherLatitude(), config.getWeatherLongitude());
        } catch (Exception e) {
            logger.severe(e.getMessage());
            logger.severe("Desativando sincronização de clima...");

            config.setWeatherEnabled(false);
            return;
        }

        for (World world : config.getWeatherSyncWorlds())
            world.setGameRuleValue("doWeatherCycle", "false");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            debug("Sincronizando clima...");

            try {
                WeatherRequestObject request = new WeatherRequestObject(
                        config.getAPIKey(),
                        config.getWeatherLatitude(),
                        config.getWeatherLongitude());

                boolean isRaining = request.isRaining();
                boolean isThundering = request.isThundering();

                debug("Configurando clima (Chuva: " + isRaining + ", Trovão: " + isThundering + ")...");

                if (isThundering && !wasThundering) {
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "⛈ " + ChatColor.LIGHT_PURPLE
                            + "Tempestade com trovões se aproximando!");
                } else if (isRaining && !wasRaining && !isThundering) {
                    Bukkit.broadcastMessage(ChatColor.BLUE + "🌧 " + ChatColor.AQUA + "Começou a chover no servidor!");
                } else if (!isRaining && wasRaining) {
                    Bukkit.broadcastMessage(
                            ChatColor.YELLOW + "☀ " + ChatColor.WHITE + "O céu está limpando... Sol à vista!");
                }

                wasRaining = isRaining;
                wasThundering = isThundering;

                for (World world : config.getWeatherSyncWorlds()) {
                    world.setStorm(isRaining);
                    world.setThundering(isThundering);
                }
            } catch (Exception e) {
                logger.severe("Ocorreu um erro ao obter informações do clima");
                debug(e.getMessage());
            }
        }, 0L, config.getWeatherSyncInterval());

        debug("Sincronização de clima ativada");
    }

    private long calculateWorldTime(Calendar cal, String sunriseTime, String sunsetTime) {
        double currentHour = cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) / 60.0
                + cal.get(Calendar.SECOND) / 3600.0;

        long ticks = (long) ((currentHour - 6) * 1000);

        if (ticks < 0) {
            ticks += 24000;
        }
        ticks = ticks % 24000;

        debug(String.format("Hora real: %.2f (%.0f:%02.0f) -> Ticks: %d",
                currentHour,
                Math.floor(currentHour),
                (currentHour - Math.floor(currentHour)) * 60,
                ticks));

        return ticks;
    }

    public String getUpdateCheck() {
        String currentVersion = this.getDescription().getVersion();
        return String.format("Atmosfera v%s carregado!", currentVersion);
    }

    public ConfigManager getConfigManager() {
        return config;
    }

    public void debug(String message) {
        if (config.debugEnabled()) {
            logger.info("[DEBUG] " + message);
        }
    }

    public boolean isRaining() {
        return wasRaining;
    }

    public boolean isThundering() {
        return wasThundering;
    }

    public boolean isPvpNight() {
        return pvpNightActive;
    }
}
