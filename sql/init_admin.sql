-- 初始化管理员账号
-- 注意：请在首次部署时执行此脚本

USE z_reader;

-- 插入管理员账号
-- 用户名: admin
-- 密码: admin123 (HexUtil 加密后的密码，需要根据实际加密结果修改,每次加密结果随机)

-- 临时创建管理员账号（密码：admin123）
INSERT INTO z_user (id, username, nickname, z_password, email, role)
VALUES (0, -- 管理员ID固定为0
        'admin', -- 用户名
        'z-reader',
        '8BDDryw#s3#e547fcede1e56bfbfe593946355c922d466463740a1be34ea9e1b0ea32c29dcc13ba2ff6ecb8ccdd972e8cceb9f49bc91fc49e69e329219da4d2be1aa208d1b2',
        'admin@z-reader.com',
        0 -- 角色：0=管理员
       )
ON DUPLICATE KEY UPDATE username = VALUES(username),
                        nickname = VALUES(nickname);

-- 查询验证
SELECT id, username, z_password, nickname, email, role, created_at
FROM z_user
WHERE role = 0;

-- 说明：
-- 1. 首次部署时，请执行此脚本创建管理员账号
-- 2. 默认管理员用户名：admin，密码：admin123
-- 3. 请在部署后立即修改管理员密码
-- 4. 如需重新生成密码哈希，可以在 com.zreader.common.util/HexUtil.java 执行：

