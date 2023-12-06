package org.example;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Connection connection = db.connect_to_db("ass2", "postgres", "B66Wz7c2");
        //db.createTable(connection, "Authors");
        //db.insert_row(connection, "Books", "aaa", "1", "12.9", "2002", "6");
        //db.read(connection, "Books");
        db.update(connection, "12", "1");
        db.read(connection, "Books");
        //db.insert_order(connection, "1", "2", "2", "3");
        //db.delete_rows(connection, "Books", "1");
        //db.order_book(connection, "5", "2", "2", "2");
        //System.out.println(db.checkInventory(connection, "2", 1));
        //db.displayKeyInformation(connection, "Customers");
        //db.TableNames_Structures(connection);



        //db.displayTableNamesAndStructures(connection);
        //db.displayForeignKey(connection);
    }
}
