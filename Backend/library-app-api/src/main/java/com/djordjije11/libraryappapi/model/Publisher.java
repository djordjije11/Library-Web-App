package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;

@Entity
public class Publisher {
    @Version
    private long rowVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(60)")
    private String name;

    public Publisher() {
    }

    public Publisher(String name){
        this.name = name;
    }

    public long getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(long rowVersion) {
        this.rowVersion = rowVersion;
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
}
