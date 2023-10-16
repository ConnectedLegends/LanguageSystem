package de.glowman554.bungeelang;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import de.glowman554.bungeelang.listeners.PlayerLoginListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

// TODO: randomness to translations, make load url configurable

public class LanguageSystemMain extends Plugin
{
	private static LanguageSystemMain instance;

	public LanguageSystemMain()
	{
		instance = this;
	}

	public static LanguageSystemMain getInstance()
	{
		return instance;
	}

	private Configuration config;
	private DatabaseConnection database;
	private Translations translations;

	@Override
	public void onLoad()
	{
		try
		{
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}
		/*
		 * config.addDefault("database.url", "changeme");
		 * config.addDefault("database.database", "changeme");
		 * config.addDefault("database.username", "changeme");
		 * config.addDefault("database.password", "changeme");
		 * config.addDefault("base_url",
		 * "https://raw.githubusercontent.com/ConnectedLegends/languages/main");
		 * config.options().copyDefaults(true); saveConfig();
		 */

		try
		{
			getLogger().log(Level.INFO, "Opening database connection");
			database = new DatabaseConnection(config.getString("database.url"), config.getString("database.database"), config.getString("database.username"), config.getString("database.password"));
		}
		catch (ClassNotFoundException | SQLException | IOException e)
		{
			throw new IllegalStateException(e);
		}

		try
		{
			translations = new Translations(config.getString("base_url"));
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}

	}

	@Override
	public void onEnable()
	{
		getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());
	}

	@Override
	public void onDisable()
	{
		getLogger().log(Level.INFO, "Closing database connection");
		database.close();
	}

	public DatabaseConnection getDatabase()
	{
		return database;
	}

	public Translations getTranslations()
	{
		return translations;
	}
}
