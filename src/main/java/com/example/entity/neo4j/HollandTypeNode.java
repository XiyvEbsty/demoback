package com.example.entity.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("HollandType")
public class HollandTypeNode {
    @Id
    private String type;
    
    @Property("name")
    private String name;
    
    @Property("description")
    private String description;
    
    public HollandTypeNode() {
    }
    
    public HollandTypeNode(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
} 