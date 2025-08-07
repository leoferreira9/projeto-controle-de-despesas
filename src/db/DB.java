package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    public static Connection getConnection(){
        try {
            Properties prop = getProperties();
            return DriverManager.getConnection(
                    prop.getProperty("db.url"),
                    prop.getProperty("db.user"),
                    prop.getProperty("db.password")
            );
        }catch (SQLException e){
            System.err.println("❌ Erro ao conectar ao banco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Properties getProperties(){
        try (FileInputStream fis = new FileInputStream("database/db.properties")){
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        }catch (IOException e){
            System.err.println("❌ Arquivo db.properties não encontrado ou inválido.");
            throw new RuntimeException(e);
        }
    }

}
