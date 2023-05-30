package de.glowman554.lang.listeners;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.glowman554.lang.LanguageSystemMain;

public class PlayerLoginListener implements Listener
{
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
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
