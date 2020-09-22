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

    /**
     *
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        // 1.获取连接
        Connection conn = JDBCUtils.getConnection();
        // 2.预编译SQL语句，返回PreparedStatement实例
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        // 3.填充占位符
        ps.setObject(1, "abcd");
        ps.setObject(2, 20);
        // 4.执行
        ps.execute();
        // 5.资源关闭
        JDBCUtils.closeResource(conn, ps);
    }

    /**
     * 通用的增删改 操作
     * @param sql
     * @param args
     */
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= args.length; i++) {
                ps.setObject(i, args[i-1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, ps);
        }


    }
}
