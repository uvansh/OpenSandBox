import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String sql = "select first_name from actor where actor_id = 8";
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgres";
        String password = "mysql123";

        Connection con = DriverManager.getConnection(url,username,password);
        Statement st = con.createStatement();
        st.executeQuery(sql);
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        String name =rs.getString(1);
        System.out.println(name);
        con.close();
    }
}