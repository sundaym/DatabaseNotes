import org.junit.Test;

/**
 * 数据库事务
 * 事务:一组逻辑操作单元，使数据从一种状态变换到另一种状态
 * 一组逻辑单元:一个或多个DML操作
 * 数据一旦提交，就不可回滚
 * 哪些操作导致数据自动提交?
 * DDL操作一旦执行都会自动提交。DML默认情况下自动提交，通过SET AUTOCOMMIT FALSE取消DML自动提交。
 * 默认在关闭连接时，会自动提交数据。
 */
public class TransactionTest {
    /**
     * A给B转账，未考虑数据库事务
     * UPDATE user_table SET balance = balance - 100 WHERE user = 'A'
     * UPDATE user_table SET balance = balance + 100 WHERE user = 'B'
     */
    @Test
    public void testTransaction1() {
        // 必须保证2个UPDATE都执行或都不执行
        String sql1 = "UPDATE user_table SET balance = balance-100 WHERE user = ?";
        String sql2 = "UPDATE user_table SET balance = balance+100 WHERE user = ?";
        PreparedstatementTest.update(sql1, 'A');
        PreparedstatementTest.update(sql2, 'B');
    }

}
