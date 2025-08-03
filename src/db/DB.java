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
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        }catch (SQLException e){
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public static Properties getProperties(){
        try (FileInputStream fis = new FileInputStream("database/db.properties")){
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        }catch (IOException e){
            throw new RuntimeException("Erro ao carregar o arquivo de propriedades", e);
        }
    }

}
