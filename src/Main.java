import java.sql.*;


public class Main {
    static ConnetionToDB wrapper = new ConnetionToDB();

    public static void main(String[] args) throws SQLException {
        DB.init(wrapper);
        //DB.dropTables(wrapper);
        //DB.createTables(wrapper);
        //DB.addUsers(wrapper);

        Terminal.printActions(wrapper);
        wrapper.connection.close();
    }


}








