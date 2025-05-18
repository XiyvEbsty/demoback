-- Insert sample job data
INSERT INTO jobs (
    title, company, description, industry, type, location, job_url,
    realistic_score, investigative_score, artistic_score, social_score, enterprising_score, conventional_score,
    technical_functional_score, general_managerial_score, autonomy_independence_score, security_stability_score,
    entrepreneurial_creativity_score, service_dedication_score, pure_challenge_score, lifestyle_score
) VALUES
-- 产品经理
(
    '产品经理', 'BOSS直聘', '负责产品规划和需求分析', 'IT/互联网', '产品/策划', '北京', 'https://www.zhipin.com/job_detail/',
    20, 60, 30, 80, 90, 70,
    50, 90, 80, 60, 70, 50, 75, 40
),
-- 游戏策划 
(
    '游戏策划', 'BOSS直聘', '负责游戏玩法、关卡、剧情设计', 'IT/互联网', '产品/策划', '上海', 'https://www.zhipin.com/job_detail/',
    30, 50, 90, 60, 80, 60,
    60, 80, 70, 50, 90, 40, 85, 50
),
-- 新媒体运营
(
    '新媒体运营', 'BOSS直聘', '负责公司社交媒体内容运营', 'IT/互联网', '运营/数据', '广州', 'https://www.zhipin.com/job_detail/',
    20, 40, 80, 90, 70, 80,
    40, 60, 70, 75, 60, 85, 50, 65
),
-- 数据分析师
(
    '数据分析师', 'BOSS直聘', '负责业务数据分析与洞察', 'IT/互联网', '运营/数据', '深圳', 'https://www.zhipin.com/job_detail/',
    30, 90, 20, 50, 60, 80,
    85, 50, 60, 70, 40, 50, 90, 40
),
-- 前端开发工程师
(
    '前端开发工程师', 'BOSS直聘', '负责公司产品的前端开发工作', 'IT/互联网', '开发/技术', '杭州', 'https://www.zhipin.com/job_detail/',
    80, 90, 60, 40, 30, 70,
    90, 30, 75, 60, 50, 30, 85, 50
),
-- 后端开发工程师
(
    '后端开发工程师', 'BOSS直聘', '负责系统服务端开发与维护', 'IT/互联网', '开发/技术', '北京', 'https://www.zhipin.com/job_detail/',
    85, 90, 30, 40, 50, 70,
    95, 40, 70, 60, 50, 30, 90, 40
),
-- 通信技术工程师
(
    '通信技术工程师', 'BOSS直聘', '负责通信网络的建设与维护', '电子/通信/半导体', '技术', '深圳', 'https://www.zhipin.com/job_detail/',
    90, 70, 20, 40, 30, 60,
    90, 40, 50, 80, 30, 60, 75, 30
),
-- 半导体/芯片工程师
(
    '半导体/芯片工程师', 'BOSS直聘', '负责芯片设计与开发', '电子/通信/半导体', '技术', '上海', 'https://www.zhipin.com/job_detail/',
    70, 95, 20, 30, 40, 60,
    95, 30, 40, 70, 50, 30, 90, 20
),
-- 硬件工程师
(
    '硬件工程师', 'BOSS直聘', '负责电子硬件电路设计', '电子/通信/半导体', '技术', '成都', 'https://www.zhipin.com/job_detail/',
    85, 90, 30, 40, 50, 60,
    90, 40, 50, 70, 60, 30, 80, 40
),
-- 人力资源专员
(
    '人力资源专员', 'BOSS直聘', '负责公司招聘、培训和人事管理', '职能支持', '人力/行政', '北京', 'https://www.zhipin.com/job_detail/',
    20, 50, 40, 90, 70, 80,
    60, 70, 50, 85, 40, 90, 30, 70
); 