package com.example.repository.neo4j;

import com.example.entity.neo4j.CareerAnchorNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerAnchorNodeRepository extends Neo4jRepository<CareerAnchorNode, String> {
} 