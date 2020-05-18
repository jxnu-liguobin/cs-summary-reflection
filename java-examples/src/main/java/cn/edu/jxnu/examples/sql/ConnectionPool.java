package cn.edu.jxnu.examples.sql;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 使用超时等待，构造一个数据库连接池
 *
 * @author 梦境迷离
 * @time. 2018年4月10日 下午7:16:24.
 * @version V1.0
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        for (int i = 0; i < initialSize; i++) {
            pool.addLast(ConnectionDriver.createConnection());
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 在mills内无法获取到连接则返回null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 未超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills; // 超时时间
                long remaining = mills; // 等待持续时间
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
