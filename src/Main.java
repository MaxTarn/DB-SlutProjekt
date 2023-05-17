import com.sun.source.tree.UsesTree;

import java.sql.*;
public class Main {
    static DataConnStateWrapper wrapper = new DataConnStateWrapper();

    public static void main(String[] args) throws SQLException {
        DB.init(wrapper);
//        DB.dropTables(wrapper);
//        DB.createTables(wrapper);
        DB.addUsers(wrapper);

        QueryMaker sel = new QueryMaker();
        sel.setTableName("users");
        sel.setTypeOfQuery("SELECT");
        sel.setColumnNames("*");
        sel.makeQuery();
        ResultSet response = wrapper.statement.executeQuery(sel.getQuery());
        while(response.next()){
            int id = response.getInt("id");
            System.out.println(id);
        }

        wrapper.connection.close();
    }


}