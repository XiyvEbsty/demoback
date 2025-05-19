-- Create jobs table
CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    company VARCHAR(255),
    description TEXT,
    industry VARCHAR(100),
    type VARCHAR(100),
    location VARCHAR(255),
    job_url VARCHAR(255),
    
    -- Holland code dimensions
    realistic_score INT,
    investigative_score INT,
    artistic_score INT,
    social_score INT,
    enterprising_score INT,
    conventional_score INT,
    
    -- Career anchor dimensions
    technical_functional_score INT,
    general_managerial_score INT,
    autonomy_independence_score INT,
    security_stability_score INT,
    entrepreneurial_creativity_score INT,
    service_dedication_score INT,
    pure_challenge_score INT,
    lifestyle_score INT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index on frequently queried fields
-- 先删除索引(如果存在)，然后再创建
DROP INDEX IF EXISTS idx_job_industry ON jobs;
DROP INDEX IF EXISTS idx_job_type ON jobs;
DROP INDEX IF EXISTS idx_job_location ON jobs;
CREATE INDEX idx_job_industry ON jobs(industry);
CREATE INDEX idx_job_type ON jobs(type);
CREATE INDEX idx_job_location ON jobs(location);

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    real_name VARCHAR(50),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'user',
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建Holland职业兴趣问题表
CREATE TABLE IF NOT EXISTS holland_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    type VARCHAR(20) NOT NULL, -- R, I, A, S, E, C
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建职业锚问题表
CREATE TABLE IF NOT EXISTS career_anchor_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    type VARCHAR(20) NOT NULL, -- TF, GM, AU, SE, EC, SV, CH, LS
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建测评记录表
CREATE TABLE IF NOT EXISTS test_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    test_type VARCHAR(20) NOT NULL, -- 'holland' or 'career_anchor'
    
    -- Holland职业兴趣分数
    realistic_score INT,
    investigative_score INT,
    artistic_score INT,
    social_score INT,
    enterprising_score INT,
    conventional_score INT,
    
    -- 职业锚分数
    technical_functional_score INT,
    general_managerial_score INT,
    autonomy_independence_score INT,
    security_stability_score INT,
    entrepreneurial_creativity_score INT,
    service_dedication_score INT,
    pure_challenge_score INT,
    lifestyle_score INT,
    
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES user(id)
); 