package com.example.entity.neo4j;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class HasHollandRelationship {
    @RelationshipId
    private Long id;
    
    private int weight;
    
    @TargetNode
    private HollandTypeNode hollandType;
    
    public HasHollandRelationship() {
    }
    
    public HasHollandRelationship(JobNode jobNode, HollandTypeNode hollandType, int weight) {
        this.hollandType = hollandType;
        this.weight = weight;
    }
    
    public Long getId() {
        return id;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public HollandTypeNode getHollandType() {
        return hollandType;
    }
    
    public void setHollandType(HollandTypeNode hollandType) {
        this.hollandType = hollandType;
    }
} 