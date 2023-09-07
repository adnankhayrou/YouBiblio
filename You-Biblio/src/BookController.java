import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookController {
    Connection con;
    public void addBook(Book newBook){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "insert into book(title,author,isbn,quantity,status) values(?,?,?,?,?)";

            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, newBook.getTitle());
                pstm.setString(2, newBook.getAuthor());
                pstm.setString(3, newBook.getIsbn());
                pstm.setInt(4, newBook.getQuantity());
                pstm.setString(5, newBook.getStatus());
                int cnt = pstm.executeUpdate();
                if (cnt != 0)
                    System.out.println("Book Inserted successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //show all books
    public void showAllBooks(){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "select * from book";
            System.out.println("Books Details : ");
            System.out.format("%s\t%s\t%s\t%s\t%s\t%s\n", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
            try {
                Statement stmt = con.createStatement();
                ResultSet data = stmt.executeQuery(query);
                while (data.next()) {
                    System.out.format("%s\t%s\t%s\t%s\t%d\t%s\n",
                            data.getInt(1),
                            data.getString(2),
                            data.getString(3),
                            data.getString(4),
                            data.getInt(5),
                            data.getString(6));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //show book based on title or author
    public void bookSearchWithTitleOrAuthor(String word){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "select * from book where book.title LIKE ? OR book.author LIKE ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, "%"+word+"%");
                pstm.setString(2, "%"+word+"%");

                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()) {
                    System.out.println("No book found with this word : " + word + "!");
                }else {
                    System.out.println("Result : ");
                    System.out.format("%s\t%s\t%s\t%s\t%s\t%s\n", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
                    while (result.next()){
                        System.out.format("%s\t%s\t%s\t%s\t%d\t%s\n",
                                result.getString(1),
                                result.getString(2),
                                result.getString(3),
                                result.getString(4),
                                result.getInt(5),
                                result.getString(6));

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    //update book
    public void updateBook(){

    }
    // check if book exists
    public boolean checkBookExists(String isbn) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "select * from book where isbn = ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, isbn);
                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()){
                    return false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public void updateBook(Book updateBook) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "UPDATE `book` SET `title`=?, `author`=?, `status`=? WHERE `isbn`=? ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, updateBook.getTitle());
                preparedStatement.setString(2, updateBook.getAuthor());
                preparedStatement.setString(3, updateBook.getStatus());
                preparedStatement.setString(4, updateBook.getIsbn());


                int count = preparedStatement.executeUpdate();
                if (count != 0) {
                    System.out.println("Book Updated Successfully");
                } else {
                    System.out.println("Something went wrong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //delete book
    public void deleteBookWithISBN(String bookIsbn){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "delete from book where isbn = ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, bookIsbn);

                int cnt = pstm.executeUpdate();
                if (cnt != 0){
                    System.out.println("Book Deleted successfully.");
                }else {
                    System.out.println("No book found with this ISBN : " + bookIsbn + "!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
