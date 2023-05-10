import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
public class Main {
    static MysqlDataSource dataSource;
    static String url = "localhost";
    static int port = 3306;
    static String database = "DB-slutprojekt";
    static String username = "root";
    static String password = "admin123";


    public static void main(String[] args) throws SQLException {

        //----------inits the different queries that creates the three tables----------
        QueryMaker usersQuery = new QueryMaker();
        usersQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
        usersQuery.setTableName("users");
        usersQuery.setColumnNames(new String[]{"id", "real_name", "user_name"});
        usersQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "varchar(100)", "varchar(100)"});
        usersQuery.makeQuery();

        QueryMaker postsQuery = new QueryMaker();
        postsQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
        postsQuery.setTableName("posts");
        postsQuery.setColumnNames(new String[]{"id", "name", "content"});
        postsQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "varchar(100)", "varchar(255)"});
        postsQuery.makeQuery();

        QueryMaker commentsQuery = new QueryMaker();
        commentsQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
        commentsQuery.setTableName("comments");
        commentsQuery.setColumnNames(new String[]{"id", "name", "content"});
        commentsQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "varchar(100)", "varchar(255)"});
        commentsQuery.makeQuery();
        //--------inits the different queries that creates the three tables END--------


        InitializeDatabase();

        Connection connection = GetConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate(usersQuery.getQuery());
        statement.executeUpdate(postsQuery.getQuery());
        statement.executeUpdate(commentsQuery.getQuery());

        connection.close();
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