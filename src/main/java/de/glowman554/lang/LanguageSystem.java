package de.glowman554.lang;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.Bukkit.getPlayer;

public class LanguageSystem
{
	public static void sendMessage(Player player, String message, Object... placeholders)
	{
		LanguageSystemMain.getInstance().getTranslations().sendMessage(player, message, placeholders);
	}
	public static void sendMessage(UUID uuid, String message, Object... placeholders)
	{
		LanguageSystemMain.getInstance().getTranslations().sendMessage(getPlayer(uuid), message, placeholders);
	}

	public static void sendMessage(CommandSender sender, String message, Object... placeholders)
	{
		if (sender instanceof Player)
		{
			LanguageSystemMain.getInstance().getTranslations().sendMessage((Player) sender, message, placeholders);
		}
		else
		{
			LanguageSystemMain.getInstance().getTranslations().sendMessage(sender, message, placeholders);
		}
	}

	public static String getString(Player player, String message)
	{
		return LanguageSystemMain.getInstance().getTranslations().loadMessage(player.getUniqueId(), message);
	}
	public static String getString(UUID uuid, String message)
	{
		return LanguageSystemMain.getInstance().getTranslations().loadMessage(uuid, message);
	}

	public static String getString(CommandSender sender, String message)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			return LanguageSystemMain.getInstance().getTranslations().loadMessage(player.getUniqueId(), message);
		}
		else
		{
			return LanguageSystemMain.getInstance().getTranslations().loadMessage("de", message);
		}
	}

	public static String getString(OfflinePlayer player, String message)
	{
		return LanguageSystemMain.getInstance().getTranslations().loadMessage(player.getUniqueId(), message);
	}
}
