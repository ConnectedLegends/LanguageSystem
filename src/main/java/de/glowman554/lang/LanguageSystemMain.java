package de.glowman554.lang;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.lang.commands.LanguageCommands;
import de.glowman554.lang.listeners.PlayerLoginListener;

public class LanguageSystemMain extends JavaPlugin
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

	private FileConfiguration config = getConfig();
	private DatabaseConnection database;
	private Translations translations;

	@Override
	public void onLoad()
	{
		config.addDefault("database.url", "changeme");
		config.addDefault("database.database", "changeme");
		config.addDefault("database.username", "changeme");
		config.addDefault("database.password", "changeme");
		config.options().copyDefaults(true);
		saveConfig();

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
			translations = new Translations();
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}

	}

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
	
		LanguageCommands language = new LanguageCommands();
		getCommand("language").setExecutor(language);
		getCommand("language").setTabCompleter(language);
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
