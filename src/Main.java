import com.sun.source.tree.UsesTree;

import java.sql.*;
public class Main {
    static DataConnStateWrapper wrapper = new DataConnStateWrapper();

    public static void main(String[] args) throws SQLException {
        /*DB.init(wrapper);
        DB.dropTables(wrapper.statement);
        wrapper.connection.close();*/

        QueryMaker select = new QueryMaker();
        select.setTypeOfQuery("SELECT");
        select.setColumnNames(new String[]{"id"});
        select.setWhereParameters("id=0");

        System.out.println(select.toString());
    }


}