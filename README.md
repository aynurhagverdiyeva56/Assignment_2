# Assignment_2

# Database Management Java Application

This Java application is designed to interact with a PostgreSQL database using JDBC. It includes functionalities for creating database tables, performing CRUD operations, managing transactions, and accessing metadata.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Part One: Database Schema](#part-one-database-schema)
- [Part Two: Database Connection](#part-two-database-connection)
- [Part Three: CRUD Operations](#part-three-crud-operations)
- [Part Four: Transaction Management](#part-four-transaction-management)
- [Part Five: Accessing Metadata](#part-five-accessing-metadata)

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK)
- PostgreSQL Database
- pgAdmin
- IntelliJ IDEA

## Part One: Database Schema

This section describes the database schema with four tables: Books, Authors, Orders, and Customers. It includes an ER Diagram illustrating the relationships between these tables.

## Part Two: Database Connection

The `DbFunctions` class provides a method to establish a connection to the PostgreSQL database. Ensure you provide the correct database name, username, and password when calling the `connect_to_db` method.

## Part Three: CRUD Operations

This section explains how to perform CRUD operations on the database. It includes methods for inserting new records, retrieving book information, updating details, and deleting existing books.

## Part Four: Transaction Management

The `order_book` method demonstrates how to manage transactions when placing an order. It checks inventory, inserts an order, updates stock quantity, and commits the transaction.

## Part Five: Accessing Metadata

Methods are provided to access metadata such as table names, column details, primary keys, and foreign keys. These can be useful for understanding the database structure and optimizing queries.

## Usage

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure the database connection in the `DbFunctions` class.
4. Run the Java application.

