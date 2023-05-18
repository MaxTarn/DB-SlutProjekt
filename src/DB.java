import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
   static String host = "localhost";
   static int port = 3306;
   static String database = "DB-Slutprojekt";
   static String username = "root";
   static String password = "admin123";
   static String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC";


   //--------------- methods that can 'reset' DB ---------------
   //drops the tables : users , posts , comments
   public static void dropTables(ConnetionToDB wrapper) throws SQLException {
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


   //creates the tables if they don't already exist
   public static void createTables(ConnetionToDB wrapper) throws SQLException {
      QueryMaker usersQuery = new QueryMaker();
      usersQuery.setTypeOfQuery("CREATE TABLE IF NOT EXISTS");
      usersQuery.setTableName("users");
      usersQuery.setColumnNames(new String[]{"id", "real_name", "user_name", "online", "email", "created"});
      usersQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT",
              "varchar(100)", "varchar(100) NOT NULL UNIQUE", "BOOLEAN NOT NULL", "varchar(100)", "DATETIME NOT NULL"});
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
   public static void addUsers(ConnetionToDB wrapper) throws SQLException {

      QueryMaker user1  = makeUser("Jack", "HeresJohny",0, "Jack@email.com", "NOW()");
      QueryMaker user2  = makeUser("Bobby", "bobbinsson",1, "Bobby@email.com", "NOW()" );
      QueryMaker user3  = makeUser("Noggle", "Wobble",0, "noggle@email.com", "NOW()");
      QueryMaker user4  = makeUser("Schtina", "Chaos",1, "Schtina@email.com", "NOW()");
      QueryMaker user5  = makeUser("Karen", "I_WANT_TO_SPEAK_WITH_YOUR_MANAGER",0, "KarensEmail@email.com", "NOW()");
      QueryMaker user6  = makeUser("Maximus", "TheGladiator",1, "Maximus@email.com", "NOW()");
      QueryMaker user7  = makeUser("Lisa", "Gamer",0, "LisaTheGamer@email.com", "NOW()");
      QueryMaker user8  = makeUser("Homer", "Simpson",1, "homerSimpson@email.com", "NOW()");
      QueryMaker user9  = makeUser("Eric", "Electric",0, "El@email.com", "NOW()");
      QueryMaker user10 = makeUser("Doggo", "The_Grand_Master_DOGGO",1, "Doggo@doggoMail.com", "NOW()");


      //staggers the sending of the queries, 1 second between each
      //done so that 'GETDATE()' is not the same for each query
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
   //--------------- methods that can 'reset' DB END---------------









   //--------------- methods that changes things in DB---------------
   public static void addNewUserToDB(ConnetionToDB wrapper) throws SQLException {
      String realName = Terminal.ask("Your legal Name");
      String userName = Terminal.ask("User Name");
      int online = Integer.parseInt(Terminal.ask("Are you online (0 -> no , 1 -> yes)"));
      String email = Terminal.ask("Your email");
      QueryMaker query = DB.makeUser(realName, userName, online, email, "NOW()");
      wrapper.statement.executeUpdate(query.getQuery());
   }


   //--------------- methods that changes things in DB END---------------


   //--------------- methods that collects data from DB ---------------
   public static void demandLogIn(ConnetionToDB wrapper) throws SQLException {
      Boolean userExists = false;
      int id = -1;

      while(!userExists){
         String userName = Terminal.ask("User Name");
         QueryMaker selectUser = new QueryMaker();
         selectUser.setTypeOfQuery("SELECT");
         selectUser.setTableName("users");
         selectUser.setColumnNames("id");
         selectUser.setWhereParameters("user_name=" + "'" + userName + "'");
         ResultSet response = wrapper.statement.executeQuery(selectUser.getQuery());
         while(response.next()){
            id = response.getInt("id");
         }

         //if id is still -1 there is no user with that username
         if(id != -1)userExists = true;else System.out.println("Invalid User name.");
      }
   }
   //--------------- methods that collects data from DB END---------------


   //--------------- inits ---------------
   public static void init(ConnetionToDB wrapper) throws SQLException {
      initializeDatabase(wrapper.dataSource);
      wrapper.connection = getConnection(wrapper.dataSource);
      wrapper.statement = wrapper.connection.createStatement();
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
   //--------------- inits END ---------------



   //--------------- Other misc. methods ---------------

   //pauses for specified number of miliseconds
   private static void sleep(int miliSec){
      try {
         Thread.sleep(miliSec);
      } catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   //builds a QueryMaker
   public static QueryMaker makeUser(String realName, String userName, int online, String email, String created){
      QueryMaker user = new QueryMaker();
      user.setTypeOfQuery("INSERT INTO");
      user.setTableName("users");
      user.setColumnNames(new String[]{"real_name", "user_name", "online", "email", "created"});
      user.setColumnValues(new String[]{realName, userName, String.valueOf(online), email, created});
      user.makeQuery();
      return user;
   }


}
