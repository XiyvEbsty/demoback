package com.example.entity.neo4j;

import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node("Job")
public class JobNode {
    @Id
    @GeneratedValue
    private Long neoId;
    
    @Property("id")
    private Long originalId;
    
    @Property("title")
    private String title;
    
    @Property("company")
    private String company;
    
    @Property("description")
    private String description;
    
    @Property("industry")
    private String industry;
    
    @Property("type")
    private String type;
    
    @Property("location")
    private String location;
    
    @Relationship(type = "HAS_HOLLAND", direction = Relationship.Direction.OUTGOING)
    private List<HasHollandRelationship> hollandRelationships = new ArrayList<>();
    
    @Relationship(type = "HAS_ANCHOR", direction = Relationship.Direction.OUTGOING)
    private List<HasAnchorRelationship> anchorRelationships = new ArrayList<>();
    
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private IndustryNode industryNode;
    
    public JobNode() {
    }
    
    public JobNode(Long originalId, String title, String company, String description, 
                  String industry, String type, String location) {
        this.originalId = originalId;
        this.title = title;
        this.company = company;
        this.description = description;
        this.industry = industry;
        this.type = type;
        this.location = location;
    }
    
    // Getters and Setters
    
    public Long getNeoId() {
        return neoId;
    }
    
    public Long getOriginalId() {
        return originalId;
    }
    
    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIndustry() {
        return industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<HasHollandRelationship> getHollandRelationships() {
        return hollandRelationships;
    }
    
    public void setHollandRelationships(List<HasHollandRelationship> hollandRelationships) {
        this.hollandRelationships = hollandRelationships;
    }
    
    public List<HasAnchorRelationship> getAnchorRelationships() {
        return anchorRelationships;
    }
    
    public void setAnchorRelationships(List<HasAnchorRelationship> anchorRelationships) {
        this.anchorRelationships = anchorRelationships;
    }
    
    public IndustryNode getIndustryNode() {
        return industryNode;
    }
    
    public void setIndustryNode(IndustryNode industryNode) {
        this.industryNode = industryNode;
    }
    
    public void addHollandRelationship(HollandTypeNode hollandTypeNode, int weight) {
        HasHollandRelationship relationship = new HasHollandRelationship(this, hollandTypeNode, weight);
        this.hollandRelationships.add(relationship);
    }
    
    public void addAnchorRelationship(CareerAnchorNode careerAnchorNode, int weight) {
        HasAnchorRelationship relationship = new HasAnchorRelationship(this, careerAnchorNode, weight);
        this.anchorRelationships.add(relationship);
    }
} 