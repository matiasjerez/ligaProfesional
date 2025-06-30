package conexionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	private static final String URL = "jdbc:mysql://localhost:3306/liga_profesional";
    private static final String USER = "root";
    private static final String PASSWORD = "123053";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
