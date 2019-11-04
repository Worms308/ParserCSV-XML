import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        try {
            Connection conn = DriverManager.getConnection ("jdbc:h2:~/rekrutacja", "sa","");
            System.err.println(conn);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Hello world");
    }
}
