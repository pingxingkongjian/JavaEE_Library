package demo.servlet;

import demo.util.Db;
import demo.util.Error;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ${刘晋勇}
 * on 2017/6/16.
 */
@WebServlet(urlPatterns = "/userBook")
public class UserBookAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            add(req, resp);
            return;
        }

        Error.showError(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String[] bookIds = req.getParameterValues("bookIds");

        for (String bookIdString : bookIds) {
            int bookId = Integer.parseInt(bookIdString);
            if (canBorrow(bookId, req, resp)) {
                // DML TRANSACTION
                insert(req, resp, userId, bookId); // INSERT INTO db.table VALUES (?, ?, ...);
                // TODO: 6/16/17 UPDATE db.table SET amount = amount - 1 WHERE id = bookId;
            } else {
                // TODO: 6/16/17 通知用户那些书不能借
            }
        }
        resp.sendRedirect("index.jsp");
    }

    private boolean canBorrow(int bookId, HttpServletRequest req, HttpServletResponse resp) {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM javaee_library.book WHERE id = ?";

        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                Error.showError(req, resp);
                return false;
            }
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int amount = resultSet.getInt("amount");
            if (amount > 0) {
                return true;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp, int userId, int bookId) throws IOException {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO javaee_library.user_book(userId, bookId) VALUE (?, ?)";

        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                Error.showError(req, resp);
                return;
            }
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, preparedStatement, connection);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}