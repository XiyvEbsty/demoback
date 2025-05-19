package com.example.repository.neo4j;

import com.example.entity.neo4j.IndustryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryNodeRepository extends Neo4jRepository<IndustryNode, String> {
} 