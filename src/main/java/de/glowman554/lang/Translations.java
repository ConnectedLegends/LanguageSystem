package de.glowman554.lang;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.glowman554.lang.utils.HttpUtils;
import net.md_5.bungee.api.ChatColor;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Translations
{
	private HashMap<String, HashMap<String, String>> loadedTranslations = new HashMap<>();

	private String base = "https://cloud.glowman554.de/pdf/res/trans";

	public Translations() throws JsonSyntaxException, IOException
	{
		LanguageSystemMain.getInstance().getLogger().log(Level.INFO, "Loading translations...");

		for (JsonNode trans : Json.json().parse(HttpUtils.get(base + "/translations.json")))
		{
			LanguageSystemMain.getInstance().getLogger().log(Level.INFO, "Loading " + trans.asString());

			HashMap<String, String> translaltion_map = new HashMap<>();

			JsonNode translations = Json.json().parse(HttpUtils.get(base + "/" + trans.asString() + ".json"));
			for (String message : translations.keySet())
			{
				translaltion_map.put(message, translations.get(message).asString());
			}

			loadedTranslations.put(trans.asString(), translaltion_map);
		}
	}

	private String loadMessage(String language, String message)
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

	private String loadMessage(Player player, String message)
	{
		try
		{
			String language = LanguageSystemMain.getInstance().getDatabase().getLanguage(player.getUniqueId());

			return loadMessage(language, message);
		}
		catch (SQLException e)
		{
			return null;
		}
	}

	public void sendMessage(Player player, String message, Object... placeholders)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(loadMessage(player, message), placeholders)));
	}

	public void sendMessage(CommandSender sender, String message, Object... placeholders)
	{
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(loadMessage("de", message), placeholders)));
	}

	public List<String> getLanguages()
	{
		return Arrays.asList(loadedTranslations.keySet().toArray(String[]::new));
	}
}
