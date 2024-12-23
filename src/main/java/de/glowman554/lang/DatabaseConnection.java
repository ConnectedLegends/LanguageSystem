package de.glowman554.lang;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import de.glowman554.lang.utils.Migration;
import org.yaml.snakeyaml.util.UriEncoder;

import de.glowman554.lang.utils.FileUtils;

public class DatabaseConnection
{
    private Connection connection = null;

    public DatabaseConnection(String url, String database, String username, String password) throws ClassNotFoundException, SQLException, IOException
    {
        connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&autoReconnect=true", url, database, username, UriEncoder.encode(password)));

        new Migration("initial", (connection, statement) -> {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `players` (
                      `uuid` varchar(100) NOT NULL,
                      `language` varchar(100) NOT NULL,
                      PRIMARY KEY (`uuid`)
                    )
                    """);
        }, connection);
    }

    public void close()
    {
        try
        {
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String getLanguage(UUID uuid) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("select language from players where uuid = ?");
        st.setString(1, uuid.toString());

        ResultSet rs = st.executeQuery();
        if (rs.next())
        {
            String res = rs.getString("language");
            rs.close();
            st.close();
            return res;
        } else
        {
            rs.close();
            st.close();
            throw new IllegalArgumentException(uuid.toString() + " could not be found in the database!");
        }
    }

    public void insertPlayer(UUID uuid) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("insert into players (uuid, language) values (?, \"de\")");
        st.setString(1, uuid.toString());
        st.execute();
        st.close();
    }

    public void updatePlayer(UUID uuid, String lang) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("update players set language = ? where uuid = ?");
        st.setString(1, lang);
        st.setString(2, uuid.toString());
        st.execute();
        st.close();
    }
}
