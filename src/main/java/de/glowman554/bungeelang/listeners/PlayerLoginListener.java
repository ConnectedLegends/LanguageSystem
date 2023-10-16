package de.glowman554.bungeelang.listeners;

import java.sql.SQLException;

import de.glowman554.bungeelang.LanguageSystemMain;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener
{
	@EventHandler
	public void onPlayerLogin(PostLoginEvent event)
	{
		try
		{
			LanguageSystemMain.getInstance().getDatabase().getLanguage(event.getPlayer().getUniqueId());
		}
		catch (Exception e)
		{
			try
			{
				LanguageSystemMain.getInstance().getDatabase().insertPlayer(event.getPlayer().getUniqueId());
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}
}
