package com.projects.databaseapi.repository;

import com.projects.databaseapi.model.Connections;

import java.sql.SQLException;
import java.util.List;

public interface DatabasesRepository {

    void setUpEnvironment(Connections connectionsEntity, boolean databaseProvided) throws SQLException;

    void doQuery(String sqlQuery) throws SQLException;

    List<String> getFirstColumn() throws SQLException;

    List<String> getAllColumns() throws SQLException;

    void closeObjects() throws SQLException;
}
