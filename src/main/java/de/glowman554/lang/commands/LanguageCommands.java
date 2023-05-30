package de.glowman554.lang.commands;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.glowman554.lang.LanguageSystem;
import de.glowman554.lang.LanguageSystemMain;

public class LanguageCommands implements TabCompleter, CommandExecutor
{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 1)
		{
			return LanguageSystemMain.getInstance().getTranslations().getLanguages();
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			LanguageSystem.sendMessage(sender, "language.invalid-sender");
			return false;
		}

		Player player = (Player) sender;

		if (args.length != 1)
		{
			LanguageSystem.sendMessage(player, "language.usage", "/language <language>");
			return false;
		}
		
		try
		{
			LanguageSystemMain.getInstance().getDatabase().updatePlayer(player.getUniqueId(), args[0]);
			LanguageSystem.sendMessage(sender, "language.update-success");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			LanguageSystem.sendMessage(sender, "language.internal-error");
		}

		return true;
	}

}
