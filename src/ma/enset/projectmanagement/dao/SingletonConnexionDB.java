package ma.enset.projectmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnexionDB {
    private static Connection connections;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connections = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_projets","root","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static Connection getConnections() {
        return connections;
    }
}