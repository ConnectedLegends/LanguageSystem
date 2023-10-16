package de.glowman554.bungeelang;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import de.glowman554.bungeelang.utils.FileUtils;
import de.glowman554.bungeelang.utils.HttpUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Translations
{
	private HashMap<String, HashMap<String, String>> loadedTranslations;

	private String base;

	public Translations(String base) throws JsonSyntaxException, IOException
	{
		this.base = base;
		reload();
	}

	public String loadMessage(String language, String message)
	{
		if (!loadedTranslations.containsKey(language))
		{
			return "language " + language + " not found!";
		}

		if (!loadedTranslations.get(language).containsKey(message))
		{
			return message;
		}

		return loadedTranslations.get(language).get(message);
	}

	public String loadMessage(UUID uuid, String message)
	{
		try
		{
			String language = LanguageSystemMain.getInstance().getDatabase().getLanguage(uuid);

			return loadMessage(language, message);
		}
		catch (SQLException e)
		{
			return null;
		}
	}

	public void sendMessage(ProxiedPlayer player, String message, Object... placeholders)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(loadMessage(player.getUniqueId(), message), placeholders)));
	}

	public void sendMessage(CommandSender sender, String message, Object... placeholders)
	{
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(loadMessage("de", message), placeholders)));
	}

	public List<String> getLanguages()
	{
		return Arrays.asList(loadedTranslations.keySet().toArray(String[]::new));
	}

	public String loadFromFileOrDownload(String path) throws IOException
	{
		if (path.startsWith("http"))
		{
			return HttpUtils.get(path);
		}
		else
		{
			return FileUtils.readFile(new FileInputStream(path));
		}
	}

	public void reload()
	{
		loadedTranslations = new HashMap<>();

		LanguageSystemMain.getInstance().getLogger().log(Level.INFO, "Loading translations...");

		try
		{
			for (JsonNode trans : Json.json().parse(loadFromFileOrDownload(base + "/translations.json")))
			{
				LanguageSystemMain.getInstance().getLogger().log(Level.INFO, "Loading " + trans.asString());

				HashMap<String, String> translaltion_map = new HashMap<>();

				JsonNode translations = Json.json().parse(loadFromFileOrDownload(base + "/" + trans.asString() + ".json"));
				for (String message : translations.keySet())
				{
					translaltion_map.put(message, translations.get(message).asString());
				}

				loadedTranslations.put(trans.asString(), translaltion_map);
			}
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
