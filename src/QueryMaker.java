public class QueryMaker {
    private StringBuilder query = new StringBuilder();

    private String queryType = null;
    private String tableName = null;
    private Integer numberOfColumns = null;


    private String[] columnNames = null;

    //used when adding new data to already made tables
    private String[] columnValues = null;

    //used when creating new tables
    private String[] columnParameters = null;


    //runs when adding new data to a table, checks if given amount of columsNames and columsValues match
    private void setNumberOfColumns(String[] columnNamesOrValuesOrParameters){

        //when the number of columns dont match with alredy specified columnNames[] or columnValues[]
        if(numberOfColumns != null && columnNamesOrValuesOrParameters.length != numberOfColumns){
            throw new RuntimeException("ERROR: the length of the colum arrays are not the same");
        }
        if(numberOfColumns == null) numberOfColumns = columnNamesOrValuesOrParameters.length;

    }
    public void setTypeOfQuery(String type){
        switch (type) {
            case "INSERT INTO", "CREATE TABLE" -> queryType = type;
            default -> throw new RuntimeException("ERROR: given query type is not allowd");
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

    @Override
    public String toString(){

        //when you try to call toString() before the global variables are set
        if(queryType == null || tableName == null || columnNames == null){
            throw new RuntimeException("ERROR: query lacks enough data");
        }


        query.append(queryType);
        query.append(" ");
        query.append(tableName);
        query.append("(");

        switch (queryType) {

            case "INSERT INTO" -> {

                //adds the names of the diffrent colums into the query
                for (int i = 0; i < columnNames.length; i++) {
                    query.append(columnNames[i]);
                    if (i != (columnNames.length - 1)) query.append(", ");
                }
                query.append(") ");
                query.append("Values (");

                //adds the values for each column in the order specified in columnNames[]
                for (int i = 0; i < columnValues.length; i++) {
                    query.append("\"");
                    query.append(columnValues[i]);
                    query.append("\"");
                    if (i != (columnValues.length - 1)) query.append(", ");
                }

            }
            case "CREATE TABLE" -> {
                for (int i = 0; i < columnNames.length; i++) {
                    query.append(columnNames[i]);
                    query.append(" ");
                    query.append(columnParameters[i]);
                    if (i != (columnNames.length - 1)) query.append(", ");
                }
            }
        }
        query.append(");");
        return query.toString();
    }


}
