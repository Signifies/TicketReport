package me.es359.Broadcast.Report;

import java.sql.*;

public class CreateSQLTables
{
    public void createTable(SQL val, boolean instance, String sql) {
        if (instance) {
            try {
                val.c.prepareStatement(sql).executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createTable(SQL value, String sql) {
        try {
            value.c.prepareStatement(sql).executeUpdate();
        }
        catch (SQLException ex) {}
    }
}
