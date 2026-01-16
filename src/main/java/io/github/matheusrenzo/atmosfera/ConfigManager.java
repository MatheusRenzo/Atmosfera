package io.github.matheusrenzo.atmosfera;

import io.github.matheusrenzo.atmosfera.requests.WeatherRequestObject;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.json.simple.parser.ParseException;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;
import java.util.HashSet;
import java.util.Objects;
import java.util.TimeZone;

public class ConfigManager {
    private final Atmosfera plugin;
    private final FileConfiguration configFile;
    private TimeZone timeZone;
    private boolean debug, timeEnabled, weatherEnabled, timeSyncAllWorlds, weatherSyncAllWorlds, blockTimeSetCommand,
            blockWeatherCommand, disableBedsAtNight, disableBedsDuringThunder;
    private long updateCheckInterval, timeSyncInterval, weatherSyncInterval;
    private String sunriseSunset, sunriseSunsetLatitude, sunriseSunsetLongitude, apiKey, weatherLatitude,
            weatherLongitude, disableBedsAtNightMessage, disableBedsDuringThunderMessage, sunriseCustomTime,
            sunsetCustomTime;
    private HashSet<World> timeSyncWorlds, weatherSyncWorlds;

    public ConfigManager(Atmosfera plugin) {
        this.plugin = plugin;
        configFile = plugin.getConfig();
    }

    public void refreshValues() {
        setDebugEnabled(configFile.getBoolean("Debug"));

        setTimeEnabled(configFile.getBoolean("SyncTime"));
        if (isTimeEnabled())
            try {
                timeSyncWorlds = new HashSet<>();
                setTimeSyncAllWorlds(configFile.getBoolean("TimeSyncAllWorlds"));
                if (getTimeSyncAllWorlds()) {
                    for (World world : plugin.getServer().getWorlds())
                        if (world.getEnvironment() == World.Environment.NORMAL)
                            addTimeSyncWorld(world.getName());
                } else {
                    for (String worldName : configFile.getStringList("TimeSyncWorlds"))
                        addTimeSyncWorld(worldName);
                }
                setBlockTimeSetCommand(configFile.getBoolean("BlockTimeSetCommand"));
                setDisableBedsAtNight(configFile.getBoolean("DisableBedsAtNight"));
                setDisableBedsAtNightMessage(configFile.getString("DisableBedsAtNightMessage"));
                setTimeSyncInterval(configFile.getLong("TimeSyncInterval"));
                setTimeZone(configFile.getString("Timezone"));
                setSunriseSunset(configFile.getString("SunriseSunset"));
                if (getSunriseSunset().equals("real")) {
                    setSunriseSunsetLatitude(configFile.getString("SunriseSunsetLatitude"));
                    setSunriseSunsetLongitude(configFile.getString("SunriseSunsetLongitude"));
                } else if (getSunriseSunset().equals("custom")) {
                    setSunriseCustomTime(configFile.getString("SunriseCustomTime"));
                    setSunsetCustomTime(configFile.getString("SunsetCustomTime"));
                }
            } catch (ConfigurationException e) {
                plugin.getLogger().severe((e.getMessage()));
                plugin.getLogger()
                        .severe("Erro ao carregar configuração de tempo. Verifique os valores no config.yml.");
                plugin.getLogger().severe("Desativando sincronização de tempo...");

                setTimeEnabled(false);
            }

        setWeatherEnabled(configFile.getBoolean("SyncWeather"));
        if (isWeatherEnabled())
            try {
                weatherSyncWorlds = new HashSet<>();
                setWeatherSyncAllWorlds(configFile.getBoolean("WeatherSyncAllWorlds"));
                if (getWeatherSyncAllWorlds()) {
                    for (World world : plugin.getServer().getWorlds())
                        if (world.getEnvironment() == World.Environment.NORMAL)
                            addWeatherSyncWorld(world.getName());
                } else {
                    for (String worldName : configFile.getStringList("WeatherSyncWorlds"))
                        addWeatherSyncWorld(worldName);
                }
                setBlockWeatherCommand(configFile.getBoolean("BlockWeatherCommand"));
                setDisableBedsDuringThunder(configFile.getBoolean("DisableBedsDuringThunder"));
                setDisableBedsDuringThunderMessage(configFile.getString("DisableBedsDuringThunderMessage"));
                setWeatherSyncInterval(configFile.getLong("WeatherSyncInterval"));
                setAPIKey(configFile.getString("APIKey"));
                setWeatherLatitude(configFile.getString("WeatherLatitude"));
                setWeatherLongitude(configFile.getString("WeatherLongitude"));
            } catch (ConfigurationException e) {
                plugin.getLogger().severe(e.getMessage());
                plugin.getLogger()
                        .severe("Erro ao carregar configuração de clima. Verifique os valores no config.yml.");
                plugin.getLogger().severe("Desativando sincronização de clima...");

                setWeatherEnabled(false);
            }

        setUpdateCheckInterval(configFile.getLong("UpdateCheckInterval"));
    }

    public long getUpdateCheckInterval() {
        return updateCheckInterval;
    }

    public void setUpdateCheckInterval(long value) {
        updateCheckInterval = value;
        plugin.debug("updateCheckInterval definido para " + value);
    }

    public boolean debugEnabled() {
        return debug;
    }

    public void setDebugEnabled(boolean value) {
        debug = value;
        plugin.getLogger().warning("Debug definido para " + value);
    }

    public boolean isTimeEnabled() {
        return timeEnabled;
    }

    public void setTimeEnabled(boolean value) {
        timeEnabled = value;
        plugin.debug("SyncTime definido para " + value);
    }

    public boolean getTimeSyncAllWorlds() {
        return timeSyncAllWorlds;
    }

    public void setTimeSyncAllWorlds(boolean value) {
        timeSyncAllWorlds = value;
        plugin.debug("TimeSyncAllWorlds definido para " + value);
    }

    public HashSet<World> getTimeSyncWorlds() {
        return timeSyncWorlds;
    }

    public void addTimeSyncWorld(String worldName) throws ConfigurationException {
        World world = plugin.getServer().getWorld(worldName);

        if (world == null)
            throw new ConfigurationException("Mundo \"" + worldName + "\" não encontrado");

        timeSyncWorlds.add(world);
        plugin.debug("Mundo \"" + worldName + "\" adicionado ao TimeSyncWorlds");
    }

    public boolean getBlockTimeSetCommand() {
        return blockTimeSetCommand;
    }

    public void setBlockTimeSetCommand(boolean value) {
        blockTimeSetCommand = value;
        plugin.debug("BlockTimeSetCommand definido para " + value);
    }

    public boolean getDisableBedsAtNight() {
        return disableBedsAtNight;
    }

    public void setDisableBedsAtNight(boolean value) {
        disableBedsAtNight = value;
        plugin.debug("DisableBedsAtNight definido para " + value);
    }

    public String getDisableBedsAtNightMessage() {
        return disableBedsAtNightMessage;
    }

    public void setDisableBedsAtNightMessage(String value) {
        disableBedsAtNightMessage = value;
        plugin.debug("NightDisabledBedMessage definido para " + value);
    }

    public long getTimeSyncInterval() {
        return timeSyncInterval;
    }

    public void setTimeSyncInterval(long value) throws ConfigurationException {
        if (value < 0)
            throw new ConfigurationException("Intervalo de sincronização de tempo não pode ser menor que 0");

        timeSyncInterval = value;
        plugin.debug("TimeSyncInterval definido para " + value);
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String value) throws ConfigurationException {
        try {
            timeZone = TimeZone.getTimeZone(ZoneId.of(Objects.requireNonNull(value)));
        } catch (ZoneRulesException | NullPointerException e) {
            throw new ConfigurationException("Timezone inválido");
        }

        plugin.debug("TimeZone definido para " + value);
    }

    public String getSunriseSunset() {
        return sunriseSunset;
    }

    public void setSunriseSunset(String value) throws ConfigurationException {
        value = value.toLowerCase();
        if (value.equals("default") || value.equals("real") || value.equals("custom")) {
            sunriseSunset = value;
            plugin.debug("SunriseSunset definido para " + value);
        } else {
            throw new ConfigurationException("Valor de SunriseSunset inválido (deve ser default, real ou custom)");
        }
    }

    public String getSunriseSunsetLatitude() {
        return sunriseSunsetLatitude;
    }

    public void setSunriseSunsetLatitude(String value) {
        sunriseSunsetLatitude = value;
        plugin.debug("SunriseSunsetLatitude definido para " + value);
    }

    public String getSunriseSunsetLongitude() {
        return sunriseSunsetLongitude;
    }

    public void setSunriseSunsetLongitude(String value) {
        sunriseSunsetLongitude = value;
        plugin.debug("SunriseSunsetLongitude definido para " + value);
    }

    public String getSunriseCustomTime() {
        return sunriseCustomTime;
    }

    public void setSunriseCustomTime(String value) throws ConfigurationException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
            sunriseCustomTime = LocalTime.parse(value, formatter).format(formatter);
            plugin.debug("SunriseCustomTime definido para " + value);
        } catch (DateTimeParseException e) {
            throw new ConfigurationException("Valor de SunriseCustomTime inválido (verifique o formato)");
        }
    }

    public String getSunsetCustomTime() {
        return sunsetCustomTime;
    }

    public void setSunsetCustomTime(String value) throws ConfigurationException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
            sunsetCustomTime = LocalTime.parse(value, formatter).format(formatter);
            plugin.debug("SunsetCustomTime definido para " + value);
        } catch (DateTimeParseException e) {
            throw new ConfigurationException("Valor de SunsetCustomTime inválido (verifique o formato)");
        }
    }

    public boolean isWeatherEnabled() {
        return weatherEnabled;
    }

    public void setWeatherEnabled(boolean value) {
        weatherEnabled = value;
        plugin.debug("SyncWeather definido para " + value);
    }

    public boolean getWeatherSyncAllWorlds() {
        return weatherSyncAllWorlds;
    }

    public void setWeatherSyncAllWorlds(boolean value) {
        weatherSyncAllWorlds = value;
        plugin.debug("WeatherSyncAllWorlds definido para " + value);
    }

    public HashSet<World> getWeatherSyncWorlds() {
        return weatherSyncWorlds;
    }

    public void addWeatherSyncWorld(String worldName) throws ConfigurationException {
        World world = plugin.getServer().getWorld(worldName);

        if (world == null)
            throw new ConfigurationException("Mundo \"" + worldName + "\" não encontrado");

        weatherSyncWorlds.add(world);
        plugin.debug("Mundo \"" + worldName + "\" adicionado ao WeatherSyncWorlds");
    }

    public boolean getBlockWeatherCommand() {
        return blockWeatherCommand;
    }

    public void setBlockWeatherCommand(boolean value) {
        blockWeatherCommand = value;
        plugin.debug("BlockWeatherCommand definido para " + value);
    }

    public boolean getDisableBedsDuringThunder() {
        return disableBedsDuringThunder;
    }

    public void setDisableBedsDuringThunder(boolean value) {
        disableBedsDuringThunder = value;
        plugin.debug("DisableBedsDuringThunder definido para " + value);
    }

    public String getDisableBedsDuringThunderMessage() {
        return disableBedsDuringThunderMessage;
    }

    public void setDisableBedsDuringThunderMessage(String value) {
        disableBedsDuringThunderMessage = value;
        plugin.debug("ThunderDisabledBedMessage definido para " + value);
    }

    public long getWeatherSyncInterval() {
        return weatherSyncInterval;
    }

    public void setWeatherSyncInterval(long value) throws ConfigurationException {
        if (value < 0)
            throw new ConfigurationException("WeatherSyncInterval não pode ser menor que 0");

        weatherSyncInterval = value;
        plugin.debug("WeatherSyncInterval definido para " + value);
    }

    public String getAPIKey() {
        return apiKey;
    }

    public void setAPIKey(String value) throws ConfigurationException {
        try {
            new WeatherRequestObject(Objects.requireNonNull(value), "0", "0");
        } catch (NullPointerException e) {
            throw new ConfigurationException("A APIKey não pode estar em branco");
        } catch (IOException | ParseException e) {
            plugin.getLogger().severe(e.getMessage());
            throw new ConfigurationException(
                    "Ocorreu um erro ao validar esta APIKey (isso não significa que a chave é inválida)");
        }

        apiKey = value;
        plugin.debug("APIKey definida para " + value);
    }

    public String getWeatherLatitude() {
        return weatherLatitude;
    }

    public void setWeatherLatitude(String value) throws ConfigurationException {
        try {
            double doubleValue = Double.parseDouble(Objects.requireNonNull(value));
            if (doubleValue < -90 || doubleValue > 90)
                throw new ConfigurationException("A latitude não pode ser menor que -90 ou maior que 90");
        } catch (NullPointerException e) {
            throw new ConfigurationException("A latitude não pode estar em branco");
        } catch (NumberFormatException e) {
            throw new ConfigurationException("A latitude informada pode não ser um número (ou é muito longa)");
        }

        weatherLatitude = value;
        plugin.debug("Latitude definida para " + value);
    }

    public String getWeatherLongitude() {
        return weatherLongitude;
    }

    public void setWeatherLongitude(String value) throws ConfigurationException {
        try {
            double doubleValue = Double.parseDouble(Objects.requireNonNull(value));
            if (doubleValue < -180 || doubleValue > 180)
                throw new ConfigurationException("A longitude não pode ser menor que -180 ou maior que 180");
        } catch (NullPointerException e) {
            throw new ConfigurationException("A longitude não pode estar em branco");
        } catch (NumberFormatException e) {
            throw new ConfigurationException("A longitude informada pode não ser um número (ou é muito longa)");
        }

        weatherLongitude = value;
        plugin.debug("Longitude definida para " + value);
    }
}
