package com.projects.databaseapi.repository;

import com.projects.databaseapi.model.Connections;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SQLQueryResponse implements DatabasesRepository {
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Override
    public void setUpEnvironment(Connections connectionsEntity, boolean databaseProvided) throws SQLException {
        String database = "";

        if (databaseProvided) {
            database = connectionsEntity.getDatabaseName() + "?useSSL=false";
        }

        this.connection = DriverManager.getConnection("jdbc:mysql://" + connectionsEntity.getHostName() + ":"
                        + connectionsEntity.getPort() + "/" + database, connectionsEntity.getUsername(), connectionsEntity.getPassword());
        this.statement = this.connection.createStatement();
    }

    @Override
    public void doQuery(String sqlQuery) throws SQLException {
        this.resultSet = this.statement.executeQuery(sqlQuery);
    }

    //get only first column from ResultSet
    @Override
    public List<String> getFirstColumn() throws SQLException{
        List<String> responseList = new ArrayList<>();

        while (this.resultSet.next()) {
            responseList.add(this.resultSet.getString(1));
        }

        return responseList;
    }

    //get all columns from ResultSet
    @Override
    public List<String> getAllColumns() throws SQLException {
        List<String> responseList = new ArrayList<>();

        while (this.resultSet.next()) {
            ResultSetMetaData metaData = this.resultSet.getMetaData();
            StringBuilder rowData = new StringBuilder();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                rowData.append(" ").append(metaData.getColumnName(i)).append(" : ").append(this.resultSet.getString(i)).append(";");
            }
            responseList.add(rowData.toString());
        }

        return responseList;
    }

    @Override
    public void closeObjects() throws SQLException {
        this.connection.close();
        this.statement.close();
    }
}
