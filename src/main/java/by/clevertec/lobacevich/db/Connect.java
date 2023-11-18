package by.clevertec.lobacevich.db;

import by.clevertec.lobacevich.exception.ConnectionException;
import by.clevertec.lobacevich.util.YamlReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Connect {

    private static final String url;
    private static final String username;
    private static final String password;
    private static final Connection CONNECTION;

    static {
        Map<String, String> data = YamlReader.getData();
        url = data.get("Connect.url");
        username = data.get("Connect.username");
        password = data.get("Connect.password");
        try {
            CONNECTION = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new ConnectionException("Connection failed");
        }
    }

    private Connect() {
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}
