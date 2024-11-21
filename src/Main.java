import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        /*
        Setup-ing connection: Every Connection needs username,password,query
        url which has connector like jdbc here, host,port and database-name
        */
        String sql = "select first_name from actor where actor_id = 8";
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgres";
        String password = "mysql123";
        //Connection is a session between a Java application and a database. It helps to establish a connection with the database.
        //The DriverManager class acts as an interface between users and drivers. It keeps track of the drivers that are available and handles establishing a connection between a database and the appropriate driver.
        //The Statement interface provides methods to execute queries with the database.
        //
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        st.executeQuery(sql);
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        String name = rs.getString(1);
        System.out.println(name);
        con.close();
    }
}