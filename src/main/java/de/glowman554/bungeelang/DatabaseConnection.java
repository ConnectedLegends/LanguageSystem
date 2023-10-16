package de.glowman554.bungeelang;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.yaml.snakeyaml.util.UriEncoder;

import de.glowman554.bungeelang.utils.FileUtils;

public class DatabaseConnection
{
	private Connection connect = null;

	public DatabaseConnection(String url, String database, String username, String password) throws ClassNotFoundException, SQLException, IOException
	{
		connect = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&autoReconnect=true", url, database, username, UriEncoder.encode(password)));

		execute_script("database_setup");
	}

	public void close()
	{
		try
		{
			connect.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void execute_script(String script_name) throws SQLException, IOException
	{
		Statement s = connect.createStatement();
		String[] sql_commands = FileUtils.readFile(this.getClass().getResourceAsStream("/sql/" + script_name + ".sql")).split(";");

		for (String sql : sql_commands)
		{
			sql = sql.trim();
			if (sql.equals(""))
			{
				continue;
			}

			s.execute(sql);
		}

		s.close();
	}

	public String getLanguage(UUID uuid) throws SQLException
	{
		PreparedStatement st = connect.prepareStatement("select language from players where uuid = ?");
		st.setString(1, uuid.toString());

		ResultSet rs = st.executeQuery();
		if (rs.next())
		{
			String res = rs.getString("language");
			rs.close();
			st.close();
			return res;
		}
		else
		{
			rs.close();
			st.close();
			throw new IllegalArgumentException(uuid.toString() + " could not be found in the database!");
		}
	}

	public void insertPlayer(UUID uuid) throws SQLException
	{
		PreparedStatement st = connect.prepareStatement("insert into players (uuid, language) values (?, \"de\")");
		st.setString(1, uuid.toString());
		st.execute();
		st.close();
	}

	public void updatePlayer(UUID uuid, String lang) throws SQLException
	{
		PreparedStatement st = connect.prepareStatement("update players set language = ? where uuid = ?");
		st.setString(1, lang);
		st.setString(2, uuid.toString());
		st.execute();
		st.close();
	}
}
