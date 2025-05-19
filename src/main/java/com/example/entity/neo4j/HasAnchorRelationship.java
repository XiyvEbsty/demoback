package com.example.entity.neo4j;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class HasAnchorRelationship {
    @RelationshipId
    private Long id;
    
    private int weight;
    
    @TargetNode
    private CareerAnchorNode careerAnchor;
    
    public HasAnchorRelationship() {
    }
    
    public HasAnchorRelationship(JobNode jobNode, CareerAnchorNode careerAnchor, int weight) {
        this.careerAnchor = careerAnchor;
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
    
    public CareerAnchorNode getCareerAnchor() {
        return careerAnchor;
    }
    
    public void setCareerAnchor(CareerAnchorNode careerAnchor) {
        this.careerAnchor = careerAnchor;
    }
} 