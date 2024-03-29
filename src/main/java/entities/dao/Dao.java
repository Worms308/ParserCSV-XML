package entities.dao;

import java.sql.*;

public abstract class Dao implements AutoCloseable{

    protected static Connection connection;

    public Dao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/rekrutacja", "sa", "");
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastInsertedId() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT LAST_INSERT_ID() id");
        if (set.next())
            return set.getInt(1);
        return -1;
    }
}
