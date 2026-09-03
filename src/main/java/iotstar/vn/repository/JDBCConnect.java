package iotstar.vn.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnect{
    private final String serverName = "localhost\\SQLEXPRESS";
    private final String dbName = "BaiTap";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "namtrung8312";
    
    public Connection getConnection() throws Exception{
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";encrypt=true;trustServerCertificate=true;databaseName=" + dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
    
    public static void main(String[] args){
        try{
            System.out.println(new JDBCConnect().getConnection());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}