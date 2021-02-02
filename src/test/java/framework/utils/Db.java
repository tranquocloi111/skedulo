package framework.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Db {

    public static Connection createConnection(String url, String userName, String password) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, userName, password);
    }

    public ResultSet executeQuery(Connection connection, String sql) {
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            //conn.close();
        } catch (Exception ex) {
            Log.error(ex.getMessage());
        }
        return rs;
    }

    public int executeNonQuery(Connection conn, String sql) {
        int result = 0;
        try {
            Statement statement = conn.createStatement();
            result = statement.executeUpdate(sql);
            //conn.close();
        } catch (Exception ex) {
            Log.error(ex.getMessage());
        }
        return result;
    }



    public static List executeQuery(Connection conn, String query, int maxRowCount) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            ArrayList<HashMap<String, String>> list = new ArrayList<>();
            while (rs.next() && maxRowCount > 0) {
                HashMap<String, String> map = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnName(i), rs.getString(i));
                }
                list.add(map);
                maxRowCount--;
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            // conn.close();
        }
        return null;
    }

}
