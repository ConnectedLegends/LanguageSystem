package de.glowman554.lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageSystem
{
	public static void sendMessage(Player player, String message, Object... placeholders)
	{
		LanguageSystemMain.getInstance().getTranslations().sendMessage(player, message, placeholders);
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
}
