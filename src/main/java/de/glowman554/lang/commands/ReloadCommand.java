package de.glowman554.lang.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.glowman554.lang.LanguageSystem;
import de.glowman554.lang.LanguageSystemMain;

public class ReloadCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length != 0)
		{
			LanguageSystem.sendMessage(sender, "language.usage", "/reload");
			return false;
		}

		LanguageSystem.sendMessage(sender, "language.reloading");
		
		LanguageSystemMain.getInstance().reloadConfig();
		LanguageSystemMain.getInstance().getTranslations().reload();

		LanguageSystem.sendMessage(sender, "language.reloading.ok");
		return true;
	}

}
