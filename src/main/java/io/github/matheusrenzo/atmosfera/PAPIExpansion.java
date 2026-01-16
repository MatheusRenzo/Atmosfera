package io.github.matheusrenzo.atmosfera;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class PAPIExpansion extends PlaceholderExpansion {

    private final Atmosfera plugin;

    public PAPIExpansion(Atmosfera plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "atmosfera";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MatheusRenzo";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        TimeZone timezone = plugin.getConfigManager().getTimeZone();
        if (timezone == null) {
            timezone = TimeZone.getDefault();
        }

        ZonedDateTime zdt = ZonedDateTime.now(timezone.toZoneId());

        if (params.equalsIgnoreCase("time")) {
            return zdt.format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        if (params.equalsIgnoreCase("time_sec")) {
            return zdt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        if (params.equalsIgnoreCase("time_12h")) {
            return zdt.format(DateTimeFormatter.ofPattern("hh:mm a"));
        }

        if (params.equalsIgnoreCase("date")) {
            return zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        if (params.equalsIgnoreCase("status")) {
            if (plugin.isThundering()) {
                return "Tempestade ⛈️";
            } else if (plugin.isRaining()) {
                return "Chovendo 🌧️";
            } else {
                return "Limpo ☀️";
            }
        }

        if (params.equalsIgnoreCase("weather_icon")) {
            if (plugin.isThundering()) {
                return "⛈";
            } else if (plugin.isRaining()) {
                return "🌧";
            } else {
                return "☀";
            }
        }

        if (params.equalsIgnoreCase("is_night") || params.equalsIgnoreCase("pvp_active")) {
            int hour = zdt.getHour();
            boolean isNight = (hour >= 18 || hour < 6);
            return String.valueOf(isNight);
        }

        if (params.equalsIgnoreCase("pvp_status")) {
            int hour = zdt.getHour();
            boolean isNight = (hour >= 18 || hour < 6);
            return isNight ? "&c&l⚔ PVP ATIVO" : "&a&l☮ PVP DESATIVADO";
        }

        if (params.equalsIgnoreCase("pvp")) {
            int hour = zdt.getHour();
            boolean isNight = (hour >= 18 || hour < 6);
            return isNight ? "ON" : "OFF";
        }

        if (params.equalsIgnoreCase("period") || params.equalsIgnoreCase("day_period")) {
            int hour = zdt.getHour();
            if (hour >= 6 && hour < 12) {
                return "Manhã";
            } else if (hour >= 12 && hour < 18) {
                return "Tarde";
            } else {
                return "Noite";
            }
        }

        if (params.equalsIgnoreCase("period_icon")) {
            int hour = zdt.getHour();
            if (hour >= 6 && hour < 18) {
                return "☀";
            } else {
                return "☾";
            }
        }

        return null;
    }
}
