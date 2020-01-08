package com.projects.databaseapi.controller;

import com.projects.databaseapi.model.Connections;
import com.projects.databaseapi.repository.ConnectionsRepository;
import com.projects.databaseapi.repository.DatabasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/databases/{connectionId}")
public class DatabasesController {
    private final DatabasesRepository databasesRepository;
    private final ConnectionsRepository connectionsRepository;

    @Autowired
    public DatabasesController(DatabasesRepository databasesRepository, ConnectionsRepository connectionsRepository) {
        this.databasesRepository = databasesRepository;
        this.connectionsRepository = connectionsRepository;
    }

    /**
     * Retrieves list of schemas in the specified database.
     * @param connectionId Connections entity id.
     * @return List of tables.
     * @throws SQLException
     */
    @GetMapping(value = "/schemas")
    public ResponseEntity<List<String>> getSchemaNames(@PathVariable("connectionId") Long connectionId) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, false);
        databasesRepository.doQuery("SHOW SCHEMAS");
        List<String> responseList = databasesRepository.getFirstColumn();
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Retrieves existing tables in the specified database.
     * @param connectionId Connections entity id.
     * @return List of tables.
     * @throws SQLException
     */
    @GetMapping(value = "/tables")
    public ResponseEntity<List<String>> getTableNames(@PathVariable("connectionId") Long connectionId) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, true);
        databasesRepository.doQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"
                + connectionsEntity.getDatabaseName() + "'");
        List<String> responseList = databasesRepository.getFirstColumn();
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Retrieves existing columns in the specified table of a database.
     * @param connectionId Connections entity id.
     * @param tableName Name of a required table.
     * @return List of columns.
     * @throws SQLException
     */
    @GetMapping(value = "/{tableName}/columns")
    public ResponseEntity<List<String>> getColumnNames(@PathVariable("connectionId") Long connectionId,
                                                       @PathVariable("tableName") String tableName) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, true);
        databasesRepository.doQuery("DESCRIBE " + tableName);
        List<String> responseList = databasesRepository.getFirstColumn();
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Retrieves up to five table records as a preview.
     * @param connectionId Connections entity id.
     * @param tableName Name of a required table.
     * @return List of records with respective columnNames.
     * @throws SQLException
     */
    @GetMapping(value = "/{tableName}/tablePreview")
    public ResponseEntity<List<String>> getTablePreview(@PathVariable("connectionId") Long connectionId,
                                                        @PathVariable("tableName") String tableName) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, true);
        databasesRepository.doQuery("SELECT * FROM " + tableName + " LIMIT 5");
        List<String> responseList = databasesRepository.getAllColumns();
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Shows number of records and attributes of the specified table.
     * @param connectionId Connections entity id.
     * @param tableName Table's name.
     * @return List containing information about the number of records and attributes of table.
     * @throws SQLException
     */
    @GetMapping(value = "/{tableName}/tableStatistics")
    public ResponseEntity<List<String>> getTableStatistics(@PathVariable("connectionId") Long connectionId,
                                                           @PathVariable("tableName") String tableName) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, true);
        databasesRepository.doQuery("SELECT COUNT(*) AS 'Number of records' FROM " + tableName);
        List<String> responseList = databasesRepository.getAllColumns();
        databasesRepository.doQuery("SELECT COUNT(*) AS 'Number of attributes' FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE table_schema = '"
                + connectionsEntity.getDatabaseName() + "' AND table_name = '" + tableName + "'");
        responseList.addAll(databasesRepository.getAllColumns());
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Shows the maximal, minimal and median values of the specified column.
     * @param connectionId Connections entity id.
     * @param tableName Name of a required table.
     * @param columnName Name of a required column.
     * @return List containing information about the maximal, minimal and median values of a column.
     * @throws SQLException
     */
    @GetMapping(value = "/{tableName}/{columnName}/columnStatistics")
    public ResponseEntity<List<String>> getColumnStatistics(@PathVariable("connectionId") Long connectionId,
                                                            @PathVariable("tableName") String tableName,
                                                            @PathVariable("columnName") String columnName) throws SQLException {
        Connections connectionsEntity = connectionsRepository.getOne(connectionId);
        databasesRepository.setUpEnvironment(connectionsEntity, true);
        databasesRepository.doQuery("SELECT MAX(" + columnName + ") AS 'Maximal Value', MIN(" + columnName
                + ") AS 'Minimal Value', AVG(" + columnName + ") AS 'Average Value' FROM " + tableName);
        List<String> responseList = databasesRepository.getAllColumns();
        databasesRepository.doQuery("SET @rowindex := -1");
        databasesRepository.doQuery(
                "SELECT AVG(i.value) AS 'Median Value' FROM (SELECT @rowindex:=@rowindex + 1 AS rowindex,"
                        + columnName + " AS value FROM " + tableName + " ORDER BY " + columnName
                        + ") AS i WHERE i.rowindex IN (FLOOR(@rowindex / 2) , CEIL(@rowindex / 2))");
        responseList.addAll(databasesRepository.getAllColumns());
        databasesRepository.closeObjects();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
