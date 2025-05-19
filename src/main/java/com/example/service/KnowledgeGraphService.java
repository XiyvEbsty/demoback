package com.example.service;

import com.example.entity.Job;
import com.example.entity.neo4j.*;
import com.example.repository.JobRepository;
import com.example.repository.neo4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class KnowledgeGraphService {

    private final JobRepository jobRepository;
    private final JobNodeRepository jobNodeRepository;
    private final HollandTypeNodeRepository hollandTypeNodeRepository;
    private final CareerAnchorNodeRepository careerAnchorNodeRepository;
    private final IndustryNodeRepository industryNodeRepository;

    // 前端的职位数据缓存（后续可考虑从文件或数据库读取）
    private final Map<String, String> hollandTypeDescriptions = Map.of(
        "R", "现实型，喜欢操作工具、机器，处理具体问题",
        "I", "研究型，喜欢研究、分析、探索和理解事物",
        "A", "艺术型，喜欢创造性活动，表达自己的想法",
        "S", "社会型，喜欢与人交往，教育、培训或帮助他人",
        "E", "企业型，喜欢领导、说服和管理他人以达成目标",
        "C", "常规型，喜欢按规则工作，注重细节和准确性"
    );

    private final Map<String, String> anchorTypeDescriptions = Map.of(
        "TF", "技术/功能型，通过发挥专业技能获得满足感",
        "GM", "管理型，通过领导他人、做决策和承担责任获得满足感",
        "AU", "自主/独立型，追求自由和独立的工作环境",
        "SE", "安全/稳定型，注重工作稳定和可预测性",
        "EC", "创业型，通过创造自己的产品或服务获得满足感",
        "SV", "服务型，致力于通过工作帮助他人和社会",
        "CH", "挑战型，不断寻求并克服困难挑战",
        "LS", "生活型，追求工作与个人生活的平衡"
    );

    private final Map<String, String> hollandTypeNames = Map.of(
        "R", "Realistic",
        "I", "Investigative",
        "A", "Artistic",
        "S", "Social",
        "E", "Enterprising",
        "C", "Conventional"
    );

    private final Map<String, String> anchorTypeNames = Map.of(
        "TF", "Technical/Functional",
        "GM", "General Managerial",
        "AU", "Autonomy/Independence",
        "SE", "Security/Stability",
        "EC", "Entrepreneurial Creativity",
        "SV", "Service/Dedication",
        "CH", "Pure Challenge",
        "LS", "Lifestyle"
    );

    @Autowired
    public KnowledgeGraphService(JobRepository jobRepository,
                               JobNodeRepository jobNodeRepository,
                               HollandTypeNodeRepository hollandTypeNodeRepository,
                               CareerAnchorNodeRepository careerAnchorNodeRepository,
                               IndustryNodeRepository industryNodeRepository) {
        this.jobRepository = jobRepository;
        this.jobNodeRepository = jobNodeRepository;
        this.hollandTypeNodeRepository = hollandTypeNodeRepository;
        this.careerAnchorNodeRepository = careerAnchorNodeRepository;
        this.industryNodeRepository = industryNodeRepository;
    }

    @Transactional
    public void initializeKnowledgeGraph() {
        // 首先创建Holland类型节点
        for (Map.Entry<String, String> entry : hollandTypeNames.entrySet()) {
            String type = entry.getKey();
            String name = entry.getValue();
            String description = hollandTypeDescriptions.get(type);
            if (!hollandTypeNodeRepository.existsById(type)) {
                hollandTypeNodeRepository.save(new HollandTypeNode(type, name, description));
            }
        }

        // 创建职业锚点类型节点
        for (Map.Entry<String, String> entry : anchorTypeNames.entrySet()) {
            String type = entry.getKey();
            String name = entry.getValue();
            String description = anchorTypeDescriptions.get(type);
            if (!careerAnchorNodeRepository.existsById(type)) {
                careerAnchorNodeRepository.save(new CareerAnchorNode(type, name, description));
            }
        }

        // 从MySQL中读取所有职位数据，转换为Neo4j节点
        List<Job> jobs = jobRepository.findAll();
        for (Job job : jobs) {
            createOrUpdateJobNode(job);
        }
    }

    @Transactional
    public void createOrUpdateJobNode(Job job) {
        // 检查是否已存在该Job节点
        Optional<JobNode> existingJob = jobNodeRepository.findByOriginalId(job.getId());
        
        JobNode jobNode;
        if (existingJob.isPresent()) {
            jobNode = existingJob.get();
            // 更新基本属性
            jobNode.setTitle(job.getTitle());
            jobNode.setCompany(job.getCompany());
            jobNode.setDescription(job.getDescription());
            jobNode.setIndustry(job.getIndustry());
            jobNode.setType(job.getType());
            jobNode.setLocation(job.getLocation());
        } else {
            // 创建新节点
            jobNode = new JobNode(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getDescription(),
                job.getIndustry(),
                job.getType(),
                job.getLocation()
            );
        }

        // 确保行业节点存在
        if (job.getIndustry() != null) {
            IndustryNode industryNode = industryNodeRepository.findById(job.getIndustry())
                .orElseGet(() -> {
                    IndustryNode newIndustry = new IndustryNode(job.getIndustry());
                    return industryNodeRepository.save(newIndustry);
                });
            jobNode.setIndustryNode(industryNode);
        }

        // 处理HOLLAND类型关系
        if (job.getRealisticScore() != null && job.getRealisticScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("R").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getRealisticScore());
        }
        
        if (job.getInvestigativeScore() != null && job.getInvestigativeScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("I").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getInvestigativeScore());
        }
        
        if (job.getArtisticScore() != null && job.getArtisticScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("A").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getArtisticScore());
        }
        
        if (job.getSocialScore() != null && job.getSocialScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("S").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getSocialScore());
        }
        
        if (job.getEnterprisingScore() != null && job.getEnterprisingScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("E").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getEnterprisingScore());
        }
        
        if (job.getConventionalScore() != null && job.getConventionalScore() > 0) {
            HollandTypeNode hollandNode = hollandTypeNodeRepository.findById("C").orElseThrow();
            jobNode.addHollandRelationship(hollandNode, job.getConventionalScore());
        }

        // 处理职业锚点关系
        if (job.getTechnicalFunctionalScore() != null && job.getTechnicalFunctionalScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("TF").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getTechnicalFunctionalScore());
        }
        
        if (job.getGeneralManagerialScore() != null && job.getGeneralManagerialScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("GM").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getGeneralManagerialScore());
        }
        
        if (job.getAutonomyIndependenceScore() != null && job.getAutonomyIndependenceScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("AU").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getAutonomyIndependenceScore());
        }
        
        if (job.getSecurityStabilityScore() != null && job.getSecurityStabilityScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("SE").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getSecurityStabilityScore());
        }
        
        if (job.getEntrepreneurialCreativityScore() != null && job.getEntrepreneurialCreativityScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("EC").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getEntrepreneurialCreativityScore());
        }
        
        if (job.getServiceDedicationScore() != null && job.getServiceDedicationScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("SV").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getServiceDedicationScore());
        }
        
        if (job.getPureChallengeScore() != null && job.getPureChallengeScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("CH").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getPureChallengeScore());
        }
        
        if (job.getLifestyleScore() != null && job.getLifestyleScore() > 0) {
            CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById("LS").orElseThrow();
            jobNode.addAnchorRelationship(anchorNode, job.getLifestyleScore());
        }

        // 保存节点及其关系
        jobNodeRepository.save(jobNode);
    }
    
    public List<Map<String, Object>> getRecommendedJobsByHollandProfile(Map<String, Integer> hollandScores) {
        return jobNodeRepository.getRecommendedJobsByHollandProfile(hollandScores);
    }
    
    public List<Map<String, Object>> getRecommendedJobsByAnchorProfile(Map<String, Integer> anchorScores) {
        return jobNodeRepository.getRecommendedJobsByAnchorProfile(anchorScores);
    }
    
    public List<Map<String, Object>> getRecommendedJobsByCombinedProfile(
            Map<String, Integer> hollandScores, 
            Map<String, Integer> anchorScores) {
        return jobNodeRepository.getRecommendedJobsByCombinedProfile(hollandScores, anchorScores);
    }
    
    // 从前端JS数据导入职位数据
    @Transactional
    public void importJobsFromJsData(List<Map<String, Object>> jobsData) {
        for (Map<String, Object> jobData : jobsData) {
            JobNode jobNode = new JobNode(
                Long.parseLong(jobData.get("id").toString()),
                (String) jobData.get("title"),
                "",  // company可能在前端数据中没有
                (String) jobData.get("description"),
                (String) jobData.get("industry"),
                "",  // type可能在前端数据中没有
                ""   // location可能在前端数据中没有
            );
            
            // 确保行业节点存在
            String industry = (String) jobData.get("industry");
            if (industry != null) {
                IndustryNode industryNode = industryNodeRepository.findById(industry)
                    .orElseGet(() -> {
                        IndustryNode newIndustry = new IndustryNode(industry);
                        return industryNodeRepository.save(newIndustry);
                    });
                jobNode.setIndustryNode(industryNode);
            }
            
            // 处理霍兰德类型
            @SuppressWarnings("unchecked")
            List<String> hollandTypes = (List<String>) jobData.get("hollandTypes");
            if (hollandTypes != null) {
                for (String type : hollandTypes) {
                    HollandTypeNode hollandNode = hollandTypeNodeRepository.findById(type).orElseThrow();
                    jobNode.addHollandRelationship(hollandNode, 8); // 默认权重为8
                }
            }
            
            // 处理职业锚点类型
            @SuppressWarnings("unchecked")
            List<String> anchorTypes = (List<String>) jobData.get("anchorTypes");
            if (anchorTypes != null) {
                for (String type : anchorTypes) {
                    CareerAnchorNode anchorNode = careerAnchorNodeRepository.findById(type).orElseThrow();
                    jobNode.addAnchorRelationship(anchorNode, 8); // 默认权重为8
                }
            }
            
            // 保存节点及其关系
            jobNodeRepository.save(jobNode);
        }
    }
    
    /**
     * 检查Neo4j知识图谱连接状态
     * @return 连接状态信息
     */
    public Map<String, Object> checkStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // 尝试获取节点数量，验证连接
            long hollandTypeCount = hollandTypeNodeRepository.count();
            long careerAnchorCount = careerAnchorNodeRepository.count();
            long jobNodeCount = jobNodeRepository.count();
            
            status.put("connected", true);
            status.put("hollandTypeCount", hollandTypeCount);
            status.put("careerAnchorCount", careerAnchorCount);
            status.put("jobNodeCount", jobNodeCount);
            status.put("message", "成功连接到Neo4j知识图谱");
            status.put("initialized", hollandTypeCount > 0 && careerAnchorCount > 0);
        } catch (Exception e) {
            status.put("connected", false);
            status.put("message", "Neo4j连接失败: " + e.getMessage());
            status.put("error", e.getClass().getSimpleName());
            status.put("initialized", false);
        }
        
        return status;
    }
} 