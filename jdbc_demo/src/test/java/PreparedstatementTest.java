import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * preparedstatement curd
 * 增删改，查(增删改 看成是一类，查询单独作为一类)
 */
public class PreparedstatementTest {

    /**
     * 单条插入
     */
    @Test
    public void testInsert() throws Exception {
        // 获取连接
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "12345678";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);

        // 返回PreparedStatement实例, ?是占位符
        String sql = "INSERT INTO customers (name, age, email) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 填充占位符
        preparedStatement.setString(1, "root");
        preparedStatement.setInt(2, 18);
        preparedStatement.setString(3, "xxx@gmail.com");

        // 执行SQL
        preparedStatement.execute();

        // 资源关闭
        preparedStatement.close();
        connection.close();
    }

    /**
     * 方式1
     * 使用Preparedstatement实现批量插入操作
     * @throws Exception
     */
    @Test
    public void testBatchInsert() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "INSERT INTO goods (name) values (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            ps.setObject(1, "name_"+i);
            ps.execute();
        }
    }

    /**
     * 方式2
     * @throws Exception
     */
    @Test
    public void testBatchInsert2() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "INSERT INTO goods (name) values (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            ps.setObject(1, "name_"+i);
            //ps.execute();
            //1.攒SQL
            ps.addBatch();
            if (i % 500 == 0) {
                //2.攒够500执行一次
                ps.executeBatch();
                //3.清空batch
                ps.clearBatch();
            }
        }

        conn.commit();
    }
}
