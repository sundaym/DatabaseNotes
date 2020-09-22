import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 查询
 */
public class QueryTest {

    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            // 查询执行，返回结果集
            resultSet = ps.executeQuery();
            // 处理结果集
            if (resultSet.next()) { // 判断结果集的下一条是否有数据，如果有返回true，指针下移
                // 获取当前这条数据的各个字段
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
