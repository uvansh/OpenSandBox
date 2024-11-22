import java.sql.*;
import java.util.Scanner;


public class Main{
    private static final String url = "jdbc:postgresql://localhost:5432/library_db";
    private static final String user = "postgres";
    private static final String password = "mysql123";


    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection(url,user,password)){
            System.out.println("Connected to PostgreSQL database!");
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("\nLibrary Management System:");
                System.out.println("1.Add Book");
                System.out.println("2. Search Book");
                System.out.println("3.View All Books");
                System.out.println("4.Update Book");
                System.out.println("5.Delete Book");
                System.out.println("6.Exit");
                System.out.println("Choose an Option: ");
                int choice = sc.nextInt();

                switch(choice){
                    case 1 -> addBook(connection,sc);
                    case 2 -> searchBook(connection,sc);
                    case 3 -> viewBooks(connection);
                    case 4 -> updateBooks(connection,sc);
                    case 5 -> deleteBook(connection,sc);
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.Please try again...");
                }
            }
        } catch(SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
    private static void addBook(Connection connection,Scanner sc) {
        try{
            System.out.println("Enter book title: ");
            String title = sc.next();
            System.out.println("Enter book author: ");
            String author = sc.next();
            System.out.println("Enter published year: ");
            int year = sc.nextInt();

            System.out.println("Enter genre: ");
            String genre = sc.next();

            String sql = "INSERT INTO books (title,author,published_year,genre) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setString(2,author);
            statement.setInt(3,year);
            statement.setString(4,genre);
            statement.executeUpdate();

            System.out.println("Book added successfully!");
        } catch(SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
    private static void viewBooks(Connection connection){
        try{
            String sql = "SELECT * FROM books";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("\nBooks in the Library: ");
            while(resultSet.next()){
                System.out.printf("ID: %d,Title: %s, Author: %s, Year: %d, Genre: %s%n",
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getInt("published_year"),
                    resultSet.getString("genre"));
            }
        } catch (SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
    private static void updateBooks(Connection connection,Scanner sc){
        try{
            System.out.print("Enter the ID of the book to update: ");
            int id = sc.nextInt();

            System.out.println("Enter new title: ");
            String title = sc.next();

            System.out.println("Enter new author: ");
            String author = sc.next();

            System.out.println("Enter new published year: ");
            int year = sc.nextInt();

            System.out.println("Enter new genre: ");
            String genre = sc.next();

            String sql = "UPDATE books SET title=?,author=?,published_year=?,genre=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setString(2,author);
            statement.setInt(3,year);
            statement.setString(4,genre);
            statement.setInt(5,id);

            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated>0){
                System.out.println("Books updated successfully!");
            }
            else{
                System.out.println("Book with the given ID not found.");
            }
        } catch (SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
    private static void deleteBook(Connection connection,Scanner sc){
        try{
            System.out.println("Enter the ID of the book to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM books WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted>0){
                System.out.println("Rows deleted successfully!");
            }
            else{
                System.out.println("Book with the given ID is not found.");
            }
        } catch(SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
    private static void searchBook(Connection connection,Scanner sc){
        try{
            System.out.println("Enter keyword to search (title/author): ");
            String keyword = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title ILIKE ? OR author ILIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+keyword+"%");
            statement.setString(2,"%"+keyword+"%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Search Result: ");
            while (resultSet.next()){
                System.out.println("ID: "+resultSet.getInt("id"));
                System.out.println("Title: "+resultSet.getString("title"));
                System.out.println("Author: "+resultSet.getString("author"));
                System.out.println("----------------------------------------");
            }
        } catch(SQLException e){
            System.out.println("Error connecting to database: "+e.getMessage());
        }
    }
}