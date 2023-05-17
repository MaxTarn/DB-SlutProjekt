public class QueryMaker {
    private String query;

    private String queryType = null;
    private String tableName = null;
    private Integer numberOfColumns = null;
    private String[] columnNames = null;

    //used when adding new data to already made tables
    private String[] columnValues = null;

    //used when creating new tables
    private String[] columnParameters = null;

    private String whereParameters = null;


    //runs when adding new data to a table, checks if given amount of columnsNames and columnsValues match
    private void setNumberOfColumns(String[] columnNamesOrValuesOrParameters){

        //when the number of columns don't match with already specified columnNames[] or columnValues[]
        if(numberOfColumns != null && columnNamesOrValuesOrParameters.length != numberOfColumns){
            throw new RuntimeException("ERROR: the length of the colum arrays are not the same");
        }
        if(numberOfColumns == null) numberOfColumns = columnNamesOrValuesOrParameters.length;
    }
    public void setTypeOfQuery(String type){
        switch (type) {
            case "INSERT INTO",
                    "CREATE TABLE",
                    "CREATE TABLE IF NOT EXISTS",
                    "DROP TABLE",
                    "SELECT"-> queryType = type;
            default -> throw new RuntimeException("ERROR: given query type is not allowed");
        }
    }
    public void setTableName(String nameOfTable){
        tableName = nameOfTable + " ";
    }
    public void setColumnNames(String[] columnNames){
        setNumberOfColumns(columnNames);
        this.columnNames = columnNames;
    }
    public void setColumnValues(String[] columnValues){
        setNumberOfColumns(columnValues);
        this.columnValues = columnValues;
    }
    public void setColumnParameters(String[] columnParameters){
        setNumberOfColumns(columnParameters);
        this.columnParameters = columnParameters;
    }
    public void setWhereParameters(String parameters){
        this.whereParameters = parameters;
    }


    public void makeQuery(){
        StringBuilder queryBuilder = new StringBuilder();

        switch (queryType) {
            case "INSERT INTO" -> {
                queryBuilder.append(queryType);
                queryBuilder.append(" ");
                queryBuilder.append(tableName);
                queryBuilder.append("(");
                //adds the names of the diffrent colums into the query
                for (int i = 0; i < columnNames.length; i++) {
                    queryBuilder.append(columnNames[i]);
                    if (i != (columnNames.length - 1)) queryBuilder.append(", ");
                }
                queryBuilder.append(") ");
                queryBuilder.append("Values (");

                //adds the values for each column in the order specified in columnNames[]
                for (int i = 0; i < columnValues.length; i++) {
                    queryBuilder.append("\"");
                    queryBuilder.append(columnValues[i]);
                    queryBuilder.append("\"");
                    if (i != (columnValues.length - 1)) queryBuilder.append(", ");
                }
                queryBuilder.append(")");
            }
            case "CREATE TABLE", "CREATE TABLE IF NOT EXISTS" -> {
                queryBuilder.append(queryType);
                queryBuilder.append(" ");
                queryBuilder.append(tableName);
                queryBuilder.append("(");
                for (int i = 0; i < columnNames.length; i++) {
                    queryBuilder.append(columnNames[i]);
                    queryBuilder.append(" ");
                    queryBuilder.append(columnParameters[i]);
                    if (i != (columnNames.length - 1)) queryBuilder.append(", ");
                }
                queryBuilder.append(")");
            }

            case "DROP TABLE" -> {
                queryBuilder.append(queryType);
                for (int i = 0; i < columnNames.length; i++) {

                }

            }
            case "SELECT" ->{
                System.out.println("dgfin");
                queryBuilder.append("WHERE ");
                queryBuilder.append(whereParameters);
            }
        }

        //adds a semicolon at end, signifying a complete sql query
        queryBuilder.append(";");

        query = queryBuilder.toString();
    }


    @Override
    public String toString(){
        return query;
    }
    public String getQuery(){
        return query;
    }
}