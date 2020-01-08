package com.projects.databaseapi.model;

import javax.persistence.*;

@Entity
public class Connections {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    //custom name of the database instance
    private String name;
    //hostname of the database
    private String hostName;
    //port where the database runs
    private Integer port;
    //name of the database
    private String databaseName;
    //username to connect to the database
    private String username;
    //password to connect to the database
    private String password;

    public Connections() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
