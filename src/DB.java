import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
   static String host = "localhost";
   static int port = 3306;
   static String database = "DB-Slutprojekt";
   static String username = "root";
   static String password = "admin123";
   static String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC";


   //drops the tables : users , posts , comments
   public static void dropTables(DataConnStateWrapper wrapper) throws SQLException {
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

      System.out.println(dropUsersQuery);
      wrapper.statement.execute(dropUsersQuery.getQuery());
      wrapper.statement.execute(dropPostsQuery.getQuery());
      wrapper.statement.execute(dropCommentsQuery.getQuery());
   }
   private static void sleep(int miliSec){
      try {
         Thread.sleep(miliSec);
      } catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   //creates the tables if they don't already exist
   public static void createTables(DataConnStateWrapper wrapper) throws SQLException {
      QueryMaker usersQuery = new QueryMaker();
      usersQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
      usersQuery.setTableName("users");
      usersQuery.setColumnNames(new String[]{"id", "real_name", "user_name", "online", "email", "created"});
      usersQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT",
              "varchar(100)", "varchar(100) NOT NULL", "BOOLEAN NOT NULL", "varchar(100)", "DATETIME NOT NULL"});
      usersQuery.makeQuery();

      QueryMaker postsQuery = new QueryMaker();
      postsQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
      postsQuery.setTableName("posts");
      postsQuery.setColumnNames(new String[]{"id", "user_id", "name", "content"});
      postsQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "int NOT NULL", "varchar(100)", "varchar(255)"});
      postsQuery.makeQuery();

      QueryMaker commentsQuery = new QueryMaker();
      commentsQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
      commentsQuery.setTableName("comments");
      commentsQuery.setColumnNames(new String[]{"id", "user_id", "name", "content"});
      commentsQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "int NOT NULL", "varchar(100)", "varchar(255)"});
      commentsQuery.makeQuery();

      wrapper.statement.execute(usersQuery.getQuery());
      wrapper.statement.execute(postsQuery.getQuery());
      wrapper.statement.execute(commentsQuery.getQuery());
   }

   //adds 10 users to the database
   public static void addUsers(DataConnStateWrapper wrapper) throws SQLException {

      QueryMaker user1  = makeUser("Jack", "shit",0, "JackShit@email.com", "GETDATE()");
      QueryMaker user2  = makeUser("bobby", "bobbinsson",1, "BobbyBobbinsson@email.com", "GETDATE()" );
      QueryMaker user3  = makeUser("nippon", "caps",0, "nipponCaps@email.com", "GETDATE()");
      QueryMaker user4  = makeUser("javascript", "anarchy",1, "javascriptAnarchy@email.com", "GETDATE()");
      QueryMaker user5  = makeUser("computer", "fucking work",0, "computerFuckingWork@email.com", "GETDATE()");
      QueryMaker user6  = makeUser("water", "bottle",1, "waterBottle@email.com", "GETDATE()");
      QueryMaker user7  = makeUser("power", "drink",0, "powerDrink@email.com", "GETDATE()");
      QueryMaker user8  = makeUser("homer", "simpson",1, "homerSimpson@email.com", "GETDATE()");
      QueryMaker user9  = makeUser("bag", "inbox",0, "bagInbox@email.com", "GETDATE()");
      QueryMaker user10 = makeUser("gas", "oline",1, "gasoline", "GETDATE()");

      wrapper.statement.executeUpdate(user1.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user2.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user3.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user4.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user5.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user6.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user7.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user8.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user9.getQuery());
      sleep(1000);
      wrapper.statement.executeUpdate(user10.getQuery());
      sleep(1000);
   }
   public static QueryMaker makeUser(String realName, String userName, int online, String email, String created){
      QueryMaker user = new QueryMaker();
      user.setTypeOfQuery("INSERT INTO");
      user.setTableName("users");
      user.setColumnNames(new String[]{"real_name", "user_name", "online", "email", "created"});
      user.setColumnValues(new String[]{realName, userName, String.valueOf(online), email, created});
      user.makeQuery();
      return user;
   }



   public static void init(DataConnStateWrapper dataConnStateWrapper) throws SQLException {
      initializeDatabase(dataConnStateWrapper.dataSource);
      dataConnStateWrapper.connection = getConnection(dataConnStateWrapper.dataSource);
      dataConnStateWrapper.statement = dataConnStateWrapper.connection.createStatement();
   }

   public static void initializeDatabase(MysqlDataSource dataSource){
      try {
         System.out.print("Configuring data source...");
         dataSource.setUser(username);
         dataSource.setPassword(password);
         dataSource.setUrl(url);
         dataSource.setUseSSL(false);

         System.out.print("done!\n");
      } catch(SQLException e){
         System.out.print("failed!\n");
         //PrintSQLException(e);
         System.exit(0);
      }
   }
   public static Connection getConnection(MysqlDataSource dataSource){
      try{
         System.out.print("Fetching connection to database...");
         Connection connection = dataSource.getConnection();
         System.out.print("done!\n");
         return connection;
      }
      catch(SQLException e){
         System.out.print("failed!\n");
         //PrintSQLException(e);
         System.exit(0);
         return null;
      }
   }




}
