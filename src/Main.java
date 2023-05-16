import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
public class Main {
    static MysqlDataSource dataSource;
    static String url = "localhost";
    static int port = 3306;
    static String database = "DB-Slutprojekt";
    static String username = "root";
    static String password = "admin123";


    public static void main(String[] args) throws SQLException {

        Integer[] intArr = new Integer[]{};
        InitializeDatabase();

        Connection connection = GetConnection();
        Statement statement = connection.createStatement();
        createTables(statement);

        connection.close();
    }

    //drops the tables : users , posts , comments
    public static void dropTables(Statement statement) throws SQLException {
        QueryMaker dropUsersQuery = new QueryMaker();
        dropUsersQuery.setTypeOfQuery("DROP TABLE");
        dropUsersQuery.setTableName("users");
        dropUsersQuery.makeQuery();

        QueryMaker dropPostsQuery = new QueryMaker();
        dropPostsQuery.setTypeOfQuery("DROP TABLE");
        dropPostsQuery.setTableName("posts");
        dropPostsQuery.makeQuery();

        QueryMaker dropCommentsQuery = new QueryMaker();
        dropCommentsQuery.setTypeOfQuery("DROP TABLE");
        dropCommentsQuery.setTableName("comments");
        dropCommentsQuery.makeQuery();

        statement.execute(dropUsersQuery.getQuery());
        statement.execute(dropPostsQuery.getQuery());
        statement.execute(dropCommentsQuery.getQuery());
    }

    //creates the tables if they don't already exist
    public static void createTables(Statement statement) throws SQLException {
        QueryMaker usersQuery = new QueryMaker();
        usersQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
        usersQuery.setTableName("users");
        usersQuery.setColumnNames(new String[]{"id", "real_name", "user_name", "online"});
        usersQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "varchar(100)", "varchar(100)", "BOOLEAN NOT NULL"});
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

        statement.execute(usersQuery.getQuery());
        statement.execute(postsQuery.getQuery());
        statement.execute(commentsQuery.getQuery());
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
            System.out.printf("Fetching connection to database...");
            Connection connection = dataSource.getConnection();
            System.out.printf("done!\n");
            return connection;
        }
        catch(SQLException e){
            System.out.printf("failed!\n");
            //PrintSQLException(e);
            System.exit(0);
            return null;
        }
    }
    //adds 10 users to the database
    public static void addUsers(Statement statement) throws SQLException {

        QueryMaker user1  = makeUser("Jack", "shit",0);
        QueryMaker user2  = makeUser("bobby", "bobbinsson",1);
        QueryMaker user3  = makeUser("nippon", "caps",0);
        QueryMaker user4  = makeUser("javascript", "anarchy",1);
        QueryMaker user5  = makeUser("computer", "fucking work",0);
        QueryMaker user6  = makeUser("water", "bottle",1);
        QueryMaker user7  = makeUser("power", "drink",0);
        QueryMaker user8  = makeUser("homer", "simpson",1);
        QueryMaker user9  = makeUser("bag", "inbox",0);
        QueryMaker user10 = makeUser("gas", "oline",1);

        statement.executeUpdate(user1.getQuery());
        statement.executeUpdate(user2.getQuery());
        statement.executeUpdate(user3.getQuery());
        statement.executeUpdate(user4.getQuery());
        statement.executeUpdate(user5.getQuery());
        statement.executeUpdate(user6.getQuery());
        statement.executeUpdate(user7.getQuery());
        statement.executeUpdate(user8.getQuery());
        statement.executeUpdate(user9.getQuery());
        statement.executeUpdate(user10.getQuery());
    }
    public static QueryMaker makeUser(String realName, String userName, int online){
        QueryMaker user = new QueryMaker();
        user.setTypeOfQuery("INSERT INTO");
        user.setTableName("users");
        user.setColumnNames(new String[]{"real_name", "user_name", "online"});
        user.setColumnValues(new String[]{realName, userName, String.valueOf(online) });
        user.makeQuery();
        return user;
    }
}