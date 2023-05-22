import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {
   static String host = "localhost";
   static int port = 3306;
   static String database = "DB-Slutprojekt";
   static String username = "root";
   static String password = "admin123";
   static String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC";
   static Wrapper wrapper;


   //--------------- methods that can 'reset' DB ---------------

   //drops all tables, makes new tables, adds some data to the users table
   public static void resetDB( ) throws SQLException {
      dropTables();
      createTables();
      addUsers();

   }
   //drops the tables : users , posts , comments
   public static void dropTables( ) throws SQLException {
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
   public static void createTables( ) throws SQLException {
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
      commentsQuery.setColumnNames(new String[]{"id", "user_id","post_id",  "content"});
      commentsQuery.setColumnParameters(new String[]{"int PRIMARY KEY NOT NULL AUTO_INCREMENT", "int NOT NULL", "varchar(100)", "varchar(255)"});
      commentsQuery.makeQuery();

      wrapper.statement.execute(usersQuery.getQuery());
      wrapper.statement.execute(postsQuery.getQuery());
      wrapper.statement.execute(commentsQuery.getQuery());
   }

   //adds 10 users to the database
   public static void addUsers( ) throws SQLException {

      QueryMaker user1  = makeUser("Jack", "jack",0, "Jack@email.com", "NOW()");
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









   //--------------- methods that adds things to DB---------------
   public static void addNewUserToDB( ) throws SQLException {
      String realName = Terminal.ask("Your legal Name");
      String userName = Terminal.ask("User Name");
      int online = Integer.parseInt(Terminal.ask("Are you online (0 -> no , 1 -> yes)"));
      String email = Terminal.ask("Your email");
      QueryMaker query = DB.makeUser(realName, userName, online, email, "NOW()");
      wrapper.statement.executeUpdate(query.getQuery());
   }

   public static void addNewPostToDB() throws SQLException {
      String nameOfPost = Terminal.ask("Headline of the post");
      String content = Terminal.ask("Content of post");
      QueryMaker newPostQuery = new QueryMaker();
      newPostQuery.setTypeOfQuery("INSERT INTO");
      newPostQuery.setTableName("posts");
      newPostQuery.setColumnNames(new String[]{"user_id", "name", "content"});
      newPostQuery.setColumnValues(new String[]{String.valueOf(wrapper.currSession.userId), nameOfPost, content});
      wrapper.statement.executeUpdate(newPostQuery.toString());

   }

   public static void addNewCommentToDB() throws SQLException {
      ResultSet response = DB.getAllPosts();
      ArrayList<Integer> allPostIDs = new ArrayList<>();
      while(response.next()){
         String postName = response.getString("name");
         String content = response.getString("content");
         int id = response.getInt("id");
         allPostIDs.add(id);

         System.out.println("---------- " + postName + " ----------");
         System.out.println("Post ID:" + id);
         System.out.println(content);

      }
      System.out.println("---------- ----------");
      int chosenPostId = Terminal.askForInt("What post do yo wish to comment on (enter Post ID)");
      boolean goodPostId = false;
      for (int id:allPostIDs){
         if (id == chosenPostId) goodPostId= true;
      }

      if(goodPostId == false) return;
      String commentContent = Terminal.ask("Enter your comment");


      QueryMaker newCommentQueryy = new QueryMaker();
      newCommentQueryy.setTypeOfQuery("INSERT INTO");
      newCommentQueryy.setTableName("comments");
      newCommentQueryy.setColumnNames(new String[]{"user_id", "post_id", "content"});
      newCommentQueryy.setColumnValues(new String[]{
              String.valueOf(wrapper.currSession.userId),
              String.valueOf(chosenPostId),
              commentContent});

      wrapper.statement.executeUpdate(newCommentQueryy.getQuery());
   }

   //--------------- methods that adds things to DB END---------------

   //--------------- methods that removes things rows DB ---------------

   public static void deleteUser() throws SQLException {
      ResultSet allUsers = getAllUsers();
      System.out.println("List of all users:");
      while(allUsers.next()){
         String userName = allUsers.getString("user_name");
         int id = allUsers.getInt("id");
         System.out.println("Unique id: " + id + "| User Name: " + userName);
      }
      Terminal.ask("What user do you want to delete(Enter the unique id)");
   }



   //--------------- methods that removes things rows DB END ---------------

   //--------------- methods that collects data from DB ---------------

   //user will input a
   public static void demandLogIn() throws SQLException {
      Boolean userExists = false;
      int id = -1;

      while(!userExists){
         String userName = Terminal.ask("Enter Your User name");
         QueryMaker selectUser = new QueryMaker();
         selectUser.setTypeOfQuery("SELECT");
         selectUser.setColumnNames("id");
         selectUser.setTableName("users");
         selectUser.setWhereParameters("user_name=" + "'" + userName + "'");
         ResultSet response = wrapper.statement.executeQuery(selectUser.getQuery());
         while(response.next()){
            id = response.getInt("id");
         }

         //if id is still -1 there is no user with that username
         if(id != -1){
            userExists = true;
            wrapper.currSession.loggedIn = true;
            wrapper.currSession.userId = id;
         }else System.out.println("Invalid User name.");
      }
   }

   public static ResultSet getAllPosts() throws SQLException {
      QueryMaker allPostsQuery = new QueryMaker();
      allPostsQuery.setTypeOfQuery("SELECT");
      allPostsQuery.setColumnNames("*");
      allPostsQuery.setTableName("posts");
      ResultSet response = wrapper.statement.executeQuery(allPostsQuery.getQuery());
      return response;

   }
   public static ResultSet getAllUsers() throws SQLException {
      QueryMaker allUsersQuery = new QueryMaker();
      allUsersQuery.setTypeOfQuery("SELECT");
      allUsersQuery.setColumnNames("*");
      allUsersQuery.setTableName("users");
      ResultSet response = wrapper.statement.executeQuery(allUsersQuery.getQuery());
      return response;
   }
   //--------------- methods that collects data from DB END---------------


   //--------------- inits ---------------
   public static void init(Wrapper wrapp) throws SQLException {
      wrapper = wrapp;
      initializeDatabase(wrapp.dataSource);
      wrapp.connection = getConnection(wrapp.dataSource);
      wrapp.statement = wrapp.connection.createStatement();
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
