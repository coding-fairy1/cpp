import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:registrations.db";

    public DBHelper() throws SQLException {
        createTableIfNotExists();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS registrations (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "student_id TEXT," +
                     "name TEXT NOT NULL," +
                     "mobile TEXT," +
                     "gender TEXT," +
                     "dob TEXT," +
                     "address TEXT," +
                     "contact TEXT" +
                     ")";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertRegistration(String studentId, String name, String mobile,
                                   String gender, String dob, String address, String contact) throws SQLException {
        String sql = "INSERT INTO registrations(student_id,name,mobile,gender,dob,address,contact) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, name);
            pstmt.setString(3, mobile);
            pstmt.setString(4, gender);
            pstmt.setString(5, dob);
            pstmt.setString(6, address);
            pstmt.setString(7, contact);
            pstmt.executeUpdate();
        }
    }

    public List<String[]> fetchAll() throws SQLException {
        List<String[]> rows = new ArrayList<>();
        String sql = "SELECT id, student_id, name, gender, address, contact FROM registrations ORDER BY id";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = rs.getString("student_id");
                row[2] = rs.getString("name");
                row[3] = rs.getString("gender");
                row[4] = rs.getString("address");
                row[5] = rs.getString("contact");
                rows.add(row);
            }
        }
        return rows;
    }
}