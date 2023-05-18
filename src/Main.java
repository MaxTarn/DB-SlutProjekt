import java.sql.*;


public class Main {
    static Wrapper wrapper = new Wrapper();

    public static void main(String[] args) throws SQLException {
        DB.init(wrapper);
        Terminal.init(wrapper);

        Terminal.printActions();



        wrapper.connection.close();
    }


}








