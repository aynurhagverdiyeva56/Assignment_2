package org.example;
import java.sql.*;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String password) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, password);
            if(connection != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }
    public void insert_row(Connection connection, String table_name,String Title, String Author_id, String Price,
                           String publication_year, String stock_quantity) {
        Statement statement;
        try {
            String query = String.format("INSERT INTO %s(Title, Author_id, Price, Publication_year, Stock_Quantity) VALUES('%s', %s, %s, %s, %s)",
                    table_name, Title, Author_id, Price, publication_year, stock_quantity);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Raw Inserted");
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    public void read(Connection connection, String table_name) {
        Statement statement; ResultSet resultSet;
        try {
            String query = String.format("SELECT * FROM %s", table_name);
            statement = connection.createStatement(); resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.print(resultSet.getString("Book_id") + " ");
                System.out.print(resultSet.getString("Title") + " ");
                System.out.print(resultSet.getString("Author_id") + " ");
                System.out.print(resultSet.getString("Price") + " ");
                System.out.print(resultSet.getString("Publication_year") + " ");
                System.out.print(resultSet.getString("Stock_Quantity") + " ");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void update(Connection connection, String new_quantity, String book_id) {
        Statement statement;
        try {
            String query = String.format("UPDATE Books SET stock_quantity=%s WHERE book_id = %s", new_quantity,  book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Updated");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void delete_rows(Connection connection, String table_name, String book_id) {
        Statement statement;
        try {
            String query = String.format("DELETE FROM %s WHERE book_id=%s", table_name, book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void insert_order(Connection connection, String order_id, String customer_id, String book_id,
                           String number_of_books) {
        Statement statement;
        try {
            String query = String.format("INSERT INTO Orders(Order_id, Customer_id, Book_id, Number_Of_Books) VALUES(%s, %s, %s, %s)",
                    order_id, customer_id, book_id, number_of_books);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Raw Inserted");
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public int checkInventory(Connection connection, String bookId, int quantity) {
        Statement statement;
        try  {
            String query = String.format("SELECT stock_quantity FROM Books WHERE book_id = %s", bookId);
            statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    if(Integer.valueOf(resultSet.getInt("stock_quantity")) >= quantity) {
                        return (Integer.valueOf(resultSet.getInt("stock_quantity")) - quantity);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public void order_book(Connection connection, String order_id, String customerId, String bookId, String quantity) {
        try {
            connection.setAutoCommit(false);
            if (checkInventory(connection, bookId, Integer.valueOf(quantity)) >= 0) {
                insert_order(connection, order_id, customerId, bookId, quantity);
                int new_quantity = checkInventory(connection, bookId, Integer.valueOf(quantity));
                update(connection, String.valueOf(new_quantity),  bookId);
                connection.commit();
                System.out.println("Order placed successfully.");
            } else {
                connection.rollback();
                System.out.println("Not enough books in the inventory. Order not placed.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void displayTableNamesAndStructures(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            System.out.println("Table Names and Structures:");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                displayColumnDetails(metaData, tableName);

                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void displayColumnDetails(DatabaseMetaData metaData, String tableName) {
        try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
            System.out.println("Column Details for Table " + tableName + ":");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                String isNullable = columns.getString("IS_NULLABLE");

                System.out.println("  Column Name: " + columnName);
                System.out.println("  Data Type: " + dataType);
                System.out.println("  Nullable: " + isNullable);
                System.out.println("  ---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void displayPrimaryKey(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] TABLE = new String[]{"TABLE"};
            ResultSet rs = metaData.getTables(null, null, null, TABLE);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                while (primaryKeys.next()) {
                    String columnName = primaryKeys.getString("COLUMN_NAME");
                    System.out.println("  Primary Key Column: " + columnName);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public static void displayForeignKey(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] TABLE = new String[]{"TABLE"};
            ResultSet rs = metaData.getTables(null, null, null, TABLE);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
                while (foreignKeys.next()) {
                    String columnName = foreignKeys.getString("FKCOLUMN_NAME");
                    String referenceTable = foreignKeys.getString("PKTABLE_NAME");
                    String referenceColumn = foreignKeys.getString("PKCOLUMN_NAME");
                    System.out.println("Foreign Key: " + columnName + ", Referenced Table: " + referenceTable +
                            ", Referenced Column: " + referenceColumn);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
