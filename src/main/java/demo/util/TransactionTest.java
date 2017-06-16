package demo.util;

import java.sql.*;

/**
 * Created by ${刘晋勇}
 * on 2017/6/16.
 */
public class TransactionTest {
    public static void main(String[] args) {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "INSERT INTO javaee_library.user VALUE (NULL, 'u', 'p', 'r')";

        try {
            if (connection == null) {
                return;
            }
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate(); // DML 1 : INSERT

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            System.out.println("id: " + id);

            sql = "DELETE FROM javaee_library.user WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); // DML 2 : DELETE


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(resultSet, preparedStatement, connection);
        }
    }
}