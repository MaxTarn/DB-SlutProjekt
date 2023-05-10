import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
public class Main {
    static MysqlDataSource dataSource;
    static String url = "localhost";
    static int port = 3306;
    static String database = "myfirstdatabase";
    static String username = "root";
    static String password = "";


    public static void main(String[] args) throws SQLException {
        /*InitializeDatabase();
        Connection connection = GetConnection();
        Statement statement = connection.createStatement();

        String queryName = Terminal.Ask("Your Name");
        String queryEmail = Terminal.Ask(" Your Email");
        String queryCreated = Terminal.Ask("Created at this Time");
        String queryOnline = Terminal.Ask("Is Online");
        String queryPhone = Terminal.Ask("Your phone number");
        String queryAddress = Terminal.Ask("Your address");


        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO newusers (name, email, created, online, phone, address) VALUES ( ")
                .append("\"").append(queryName).append("\"").append(",")
                .append("\"").append(queryEmail).append("\"").append(",")
                .append("\"").append(queryCreated).append("\"").append(",")
                .append("\"").append(queryOnline).append("\"").append(",")
                .append("\"").append(queryPhone).append("\"").append(",")
                .append("\"").append(queryAddress).append("\"").append(");");
        System.out.println(query);

        int result = statement. executeUpdate(query.toString());
        connection.close();*/

        QueryMaker addNewDataQuery = new QueryMaker();
        addNewDataQuery.setTypeOfQuery("INSERT INTO");
        addNewDataQuery.setTableName("users");
        addNewDataQuery.setColumnNames(new String[]{"name", "age", "phoneNumber"});
        addNewDataQuery.setColumnValues(new String[]{"bobby", "68", "0902975", });


        System.out.println(addNewDataQuery.toString());

        QueryMaker makeNewTableQuery = new QueryMaker();
        makeNewTableQuery.setTypeOfQuery("CREATE TABLE");
        makeNewTableQuery.setTableName("users");
        makeNewTableQuery.setColumnNames(new String[]{"id", "name", "password"});
        makeNewTableQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "varchar(100) NOT NULL", "varchar(100)"});

        System.out.print("make new table:   ");
        System.out.println(makeNewTableQuery);



    }


    public static void InitializeDatabase(){
        try {
            System.out.print("Configuring data source...");
            dataSource = new MysqlDataSource();
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setUrl("jdbc:mysql://" + url + ":" + port + "/" + database +
                    "?serverTimezone=UTC");
            dataSource.setUseSSL(false);

            System.out.print("done!\n");
        } catch(SQLException e){
            System.out.print("failed!\n");
            //PrintSQLException(e);
            System.exit(0);
        }
    }
    public static Connection GetConnection(){
        try{
            //System.out.printf("Fetching connection to database...");
            Connection connection = dataSource.getConnection();
            //System.out.printf("done!\n");
            return connection;
        }
        catch(SQLException e){
            //System.out.printf("failed!\n");
            //PrintSQLException(e);
            System.exit(0);
            return null;
        }
    }
}