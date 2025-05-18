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
CREATE INDEX idx_job_industry ON jobs(industry);
CREATE INDEX idx_job_type ON jobs(type);
CREATE INDEX idx_job_location ON jobs(location); 