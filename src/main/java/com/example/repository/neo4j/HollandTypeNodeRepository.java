package com.example.repository.neo4j;

import com.example.entity.neo4j.HollandTypeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HollandTypeNodeRepository extends Neo4jRepository<HollandTypeNode, String> {
} 