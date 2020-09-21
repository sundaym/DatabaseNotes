import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * jdbc连接示例
 */
public class ConnectionTest {

    /**
     * 方式1
     * @throws Exception
     */
    @Test
    public void testConnection1() throws Exception {

        // jdbc是接口, 具体的实现有对应的数据库驱动完成
        Driver driver = new com.mysql.jdbc.Driver();
        // jdbc URL标准由3部分组成，协议子,协议,子名称
        // jdbc 协议
        // mysql 子协议
        // 地址 子名称
        String url = "jdbc:mysql://localhost:3306/";
        //用户名和密码分装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connect = driver.connect(url, info);
        System.out.println("print connection object:" + connect);
    }

    /**
     * 方式2 对方式1的迭代，程序中不出现第3方API，提高可移植性
     * @throws Exception
     */
    @Test
    public void testConnection2() throws Exception {
        // 使用反射获取Driver实现类对象
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 提供要连接的数据库，同方式1
        String url = "jdbc:mysql://localhost:3306/";
        //用户名和密码分装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connect = driver.connect(url, info);
        System.out.println("print connection object:" + connect);
    }

    /**
     * 方式3 使用DriverManager 替代 Driver
     * @throws Exception
     */
    @Test
    public void testConnection3() throws Exception {
        // 1
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 2
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "123456";

        // 3.注册Driver
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    /**
     * 方式4
     * @throws Exception
     */
    @Test
    public void testConnection4() throws Exception {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "123456";

        // 加载Driver，MySQl的Driver中静态代码块有注册操作
        // Driver实现类在加载到内存中时，就会执行注册操作(详细看com.mysql.jdbc.Driver实现类源码)
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    /**
     * 方式5 将数据库配置信息写到jdbc.properties中
     * @throws Exception
     */
    @Test
    public void testConnection5() throws Exception {

    }
}
