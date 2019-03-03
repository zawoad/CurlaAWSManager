package curlaawsmanager.edu.cis.uab.dal;

import curlaawsmanager.edu.cis.uab.util.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class UrlDBHandler {

    private Connection db;
    
    public UrlDBHandler() {
        db = URLDbConnection.DB(Config.Instanc().URLDBHost(), Config.Instanc().URLDBPort(),
                        Config.Instanc().URLDBName(), Config.Instanc().URLDBUserName(), Config.Instanc().URLDBPassword());
    }

    public long totalUniqueURL() throws SQLException {
        Statement sql = db.createStatement();
        String query = "select count(*) as total from url";
        ResultSet rs = sql.executeQuery(query);
        while (rs.next()) {
            return rs.getLong("total");
        }
        return 0;
    }
    public long totalDuplicateURL() throws SQLException {
        Statement sql = db.createStatement();
        String query = "select count(*) as total from duplicate_url";
        ResultSet rs = sql.executeQuery(query);
        while (rs.next()) {
            return rs.getLong("total");
        }
        return 0;
    }
    
    public void clearUrlData() throws SQLException
    {
        Statement sql = db.createStatement();
        String q = "delete from url";
        sql.executeUpdate(q);
        q = "delete from duplicate_url";
        sql.executeUpdate(q);
    }

}
