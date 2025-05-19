package com.example.repository.neo4j;

import com.example.entity.neo4j.JobNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface JobNodeRepository extends Neo4jRepository<JobNode, Long> {
    
    Optional<JobNode> findByOriginalId(Long originalId);
    
    @Query("MATCH (j:Job) RETURN j LIMIT $limit")
    List<JobNode> findLimitedJobs(@Param("limit") int limit);
    
    @Query("""
        MATCH (j:Job)-[r:HAS_HOLLAND]->(h:HollandType)
        WITH j, h.type AS type, r.weight AS jobWeight, $hollandScores AS userScores
        WITH j, SUM(CASE 
          WHEN type = 'R' THEN jobWeight * userScores.R
          WHEN type = 'I' THEN jobWeight * userScores.I
          WHEN type = 'A' THEN jobWeight * userScores.A
          WHEN type = 'S' THEN jobWeight * userScores.S
          WHEN type = 'E' THEN jobWeight * userScores.E
          WHEN type = 'C' THEN jobWeight * userScores.C
          ELSE 0
        END) / 10 AS matchScore
        RETURN j.originalId AS jobId, j.title AS title, j.industry AS industry, 
               j.description AS description, matchScore
        ORDER BY matchScore DESC
        LIMIT 10
    """)
    List<Map<String, Object>> getRecommendedJobsByHollandProfile(@Param("hollandScores") Map<String, Integer> hollandScores);
    
    @Query("""
        MATCH (j:Job)-[r:HAS_ANCHOR]->(c:CareerAnchor)
        WITH j, c.type AS type, r.weight AS jobWeight, $anchorScores AS userScores
        WITH j, SUM(CASE 
          WHEN type = 'TF' THEN jobWeight * userScores.TF
          WHEN type = 'GM' THEN jobWeight * userScores.GM
          WHEN type = 'AU' THEN jobWeight * userScores.AU
          WHEN type = 'SE' THEN jobWeight * userScores.SE
          WHEN type = 'EC' THEN jobWeight * userScores.EC
          WHEN type = 'SV' THEN jobWeight * userScores.SV
          WHEN type = 'CH' THEN jobWeight * userScores.CH
          WHEN type = 'LS' THEN jobWeight * userScores.LS
          ELSE 0
        END) / 10 AS matchScore
        RETURN j.originalId AS jobId, j.title AS title, j.industry AS industry, 
               j.description AS description, matchScore
        ORDER BY matchScore DESC
        LIMIT 10
    """)
    List<Map<String, Object>> getRecommendedJobsByAnchorProfile(@Param("anchorScores") Map<String, Integer> anchorScores);
    
    @Query("""
        MATCH (j:Job)
        OPTIONAL MATCH (j)-[rh:HAS_HOLLAND]->(h:HollandType)
        WITH j, h.type AS hType, rh.weight AS hWeight, $hollandScores AS hScores
        WITH j, SUM(CASE 
          WHEN hType = 'R' THEN hWeight * hScores.R
          WHEN hType = 'I' THEN hWeight * hScores.I
          WHEN hType = 'A' THEN hWeight * hScores.A
          WHEN hType = 'S' THEN hWeight * hScores.S
          WHEN hType = 'E' THEN hWeight * hScores.E
          WHEN hType = 'C' THEN hWeight * hScores.C
          ELSE 0
        END) / 10 AS hollandScore
        
        OPTIONAL MATCH (j)-[ra:HAS_ANCHOR]->(c:CareerAnchor)
        WITH j, hollandScore, c.type AS cType, ra.weight AS cWeight, $anchorScores AS cScores
        WITH j, hollandScore, SUM(CASE 
          WHEN cType = 'TF' THEN cWeight * cScores.TF
          WHEN cType = 'GM' THEN cWeight * cScores.GM
          WHEN cType = 'AU' THEN cWeight * cScores.AU
          WHEN cType = 'SE' THEN cWeight * cScores.SE
          WHEN cType = 'EC' THEN cWeight * cScores.EC
          WHEN cType = 'SV' THEN cWeight * cScores.SV
          WHEN cType = 'CH' THEN cWeight * cScores.CH
          WHEN cType = 'LS' THEN cWeight * cScores.LS
          ELSE 0
        END) / 10 AS anchorScore
        
        WITH j, hollandScore * 0.5 + anchorScore * 0.5 AS totalScore
        WHERE totalScore > 0
        RETURN j.originalId AS jobId, j.title AS title, j.industry AS industry, 
               j.description AS description, totalScore AS matchScore
        ORDER BY totalScore DESC
        LIMIT 10
    """)
    List<Map<String, Object>> getRecommendedJobsByCombinedProfile(
            @Param("hollandScores") Map<String, Integer> hollandScores,
            @Param("anchorScores") Map<String, Integer> anchorScores);
} 