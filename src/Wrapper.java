import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.Statement;

//used as a wrapper so that DB can edit values

public class Wrapper {
   Session currSession = new Session();
   MysqlDataSource dataSource = new MysqlDataSource();
   Connection connection;
   Statement statement;
}
