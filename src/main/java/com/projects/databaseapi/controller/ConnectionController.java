package com.projects.databaseapi.controller;

import com.projects.databaseapi.model.Connections;
import com.projects.databaseapi.repository.ConnectionsRepository;
import com.projects.databaseapi.helper.PathVariableParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
public class ConnectionController {
    private final ConnectionsRepository connectionsRepository;

    @Autowired
    public ConnectionController(ConnectionsRepository connectionsRepository) {
        this.connectionsRepository = connectionsRepository;
    }

    /**
     * Retrieves all Connections entities.
     * @return List of entities.
     */
    @GetMapping
    public ResponseEntity<List<Connections>> getConnection() {
        return new ResponseEntity<>(connectionsRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves specified Connections entities.
     * @param id String supposed to represent a set and/or ranges of ids.
     * @return List of entities.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Connections>> getConnectionById(@PathVariable String id) {
        return new ResponseEntity<>(connectionsRepository.findAllById(PathVariableParser.getRange(id)), HttpStatus.OK);
    }

    /**
     * Creates new entity.
     * @param connection New Connections entity to be created.
     * @return Response message with brief result description.
     */
    @PostMapping
    public ResponseEntity<Connections> addConnection(@RequestBody Connections connection) {
        return new ResponseEntity<>(connectionsRepository.save(connection), HttpStatus.CREATED);
    }

    /**
     * Updates specified entity.
     * @param connection New entity values.
     * @return Response message with brief result description.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateConnectionById(@RequestBody Connections connection, @PathVariable(required = true) String id) {
        if (connectionsRepository.existsById(Long.parseLong(id))) {
            connection.setId(Long.parseLong(id));

            return new ResponseEntity<>(connectionsRepository.save(connection), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could not update the entity: The specified entity does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes specified Connections entities by their id.
     * @param id Connection entities' id values.
     * @return Response message with brief result description.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteConnectionById(@PathVariable String id){
        connectionsRepository.deleteConnectionsById(PathVariableParser.getRange(id));

        return ResponseEntity.noContent().build();
    }
}
