import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * 操作数据库的工具类
 */
public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        // 读取配置文件信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭资源
     * @param conn
     * @param ps
     */
    public void closeResource(Connection conn, PreparedStatement ps) {

    }
}
