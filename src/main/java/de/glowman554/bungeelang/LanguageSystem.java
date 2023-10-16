package de.glowman554.bungeelang;

import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LanguageSystem
{
	public static void sendMessage(ProxiedPlayer player, String message, Object... placeholders)
	{
		LanguageSystemMain.getInstance().getTranslations().sendMessage(player, message, placeholders);
	}

	public static void sendMessage(UUID uuid, String message, Object... placeholders)
	{
		LanguageSystemMain.getInstance().getTranslations().sendMessage(LanguageSystemMain.getInstance().getProxy().getPlayer(uuid), message, placeholders);
	}

	public static void sendMessage(CommandSender sender, String message, Object... placeholders)
	{
		if (sender instanceof ProxiedPlayer)
		{
			LanguageSystemMain.getInstance().getTranslations().sendMessage((ProxiedPlayer) sender, message, placeholders);
		}
		else
		{
			LanguageSystemMain.getInstance().getTranslations().sendMessage(sender, message, placeholders);
		}
	}

	public static String getString(ProxiedPlayer player, String message)
	{
		return LanguageSystemMain.getInstance().getTranslations().loadMessage(player.getUniqueId(), message);
	}

	public static String getString(UUID uuid, String message)
	{
		return LanguageSystemMain.getInstance().getTranslations().loadMessage(uuid, message);
	}

	public static String getString(CommandSender sender, String message)
	{
		if (sender instanceof ProxiedPlayer)
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			return LanguageSystemMain.getInstance().getTranslations().loadMessage(player.getUniqueId(), message);
		}
		else
		{
			return LanguageSystemMain.getInstance().getTranslations().loadMessage("de", message);
		}
	}

	/*
	 * public static String getString(OfflinePlayer player, String message) {
	 * return
	 * LanguageSystemMain.getInstance().getTranslations().loadMessage(player.
	 * getUniqueId(), message); }
	 */
}
