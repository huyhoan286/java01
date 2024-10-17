package tivi;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static void main(String[] args) {
        try {
            Connection conn = connectToDatabase();
            if (conn != null) {
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Product name: " + dm.getDatabaseProductName());

                // Example: Add a Tivi object
                addTivi(conn, "Samsung", 55);

                // Load data from the Tivi table
                loadData(conn);

                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbURL = "jdbc:sqlserver://localhost:1434;DatabaseName=Java1;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String pass = "123456@";
        return DriverManager.getConnection(dbURL, user, pass);
    }

    public static void loadData(Connection conn) {
        try {
            Statement stm = conn.createStatement();
            String query = "SELECT * FROM Tivi";
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                System.out.println("Hãng sản xuất: " + result.getString("hangsanxuat") + ", Kích cỡ: " + result.getInt("kichco"));
            }
            result.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTivi(Connection conn, String hangsanxuat, int kichco) {
        String query = "INSERT INTO Tivi (hangsanxuat, kichco) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, hangsanxuat);
            pstmt.setInt(2, kichco);
            pstmt.executeUpdate();
            System.out.println("Tivi added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}