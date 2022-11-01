package me.es359.Broadcast.Report;

import java.sql.*;

public class CreateSQLTables
{
    public void createTable(final SQL val, final boolean instance, final String sql) {
        if (instance) {
            try {
                val.c.prepareStatement(sql).executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createTable(final SQL value, final String sql) {
        try {
            value.c.prepareStatement(sql).executeUpdate();
        }
        catch (SQLException ex) {}
    }
}
