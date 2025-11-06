# 用户表
CREATE TABLE z_user
(
    id         TINYINT PRIMARY KEY COMMENT '用户唯一ID',
    username   VARCHAR(64)  NOT NULL UNIQUE COMMENT '用户账号',
    nickname   VARCHAR(20)           DEFAULT 'z-reader' COMMENT '昵称',
    z_password VARCHAR(255) NOT NULL COMMENT '哈希后的密码',
    email      VARCHAR(255)          DEFAULT NULL COMMENT '邮箱',
    avatar_url VARCHAR(512)          DEFAULT NULL COMMENT '头像URL',
    gender     TINYINT               DEFAULT NULL COMMENT '性别 0:女,1:男',
    bio        VARCHAR(255)          DEFAULT NULL COMMENT '个人简介',
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CHECK (gender = 0 or gender = 1)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

# 书籍信息表
CREATE TABLE z_book
(
    id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '书籍唯一ID',
    isbn          CHAR(13)              DEFAULT NULL UNIQUE COMMENT 'ISBN-13（国际标准书号）',
    book_name     VARCHAR(255) NOT NULL COMMENT '书名',
    author        VARCHAR(255) NOT NULL COMMENT '作者（多作者用逗号分隔）',
    score         TINYINT      NOT NULL DEFAULT 0 COMMENT '书籍评星（0-5）',
    translator    VARCHAR(255)          DEFAULT NULL COMMENT '译者（多译者用逗号分隔）',
    publisher     VARCHAR(128)          DEFAULT NULL COMMENT '出版社',
    publish_date  DATE                  DEFAULT NULL COMMENT '出版日期',
    pages         INT UNSIGNED          DEFAULT NULL COMMENT '总页数',
    cover_url     VARCHAR(512)          DEFAULT NULL COMMENT '封面图片URL',
    summary       TEXT                  DEFAULT NULL COMMENT '内容简介',
    book_language VARCHAR(10)  NOT NULL DEFAULT 'zh-CN' COMMENT '语言 zh-CN、en、jp等',
    category_id   INT UNSIGNED          DEFAULT NULL COMMENT '分类ID（关联z_book_category）',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CHECK (score IN (0, 1, 2, 3, 4, 5))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='书籍信息表';

# 书籍阅读进度表
CREATE TABLE z_book_progress
(
    book_id         INT UNSIGNED PRIMARY KEY COMMENT '书籍进度唯一ID',
    reading_status  VARCHAR(10)     NOT NULL DEFAULT 'not_started' COMMENT '阅读状态：not_started=未开始, reading=正在阅读, finished=已读完',
    reading_hours   BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '累计阅读时长（秒）',
    reading_percent INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '阅读比例（0-10000，代表0.00%-100.00%）',
    last_position   VARCHAR(255)             DEFAULT NULL COMMENT '最后位置：{"type":"txt","chapter":5,"page_in_chapter":3} 或 {"type":"pdf","page":5}',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='书籍阅读进度表';

# 书籍分类表
CREATE TABLE z_book_category
(
    id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '分类唯一ID',
    category_name VARCHAR(64)  NOT NULL UNIQUE COMMENT '分类名称（全局唯一）',
    book_count    INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '该分类下书籍数量',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='书籍分类表';

# 书籍存储表
CREATE TABLE z_book_storage
(
    -- 文件基础信息
    book_id             INT UNSIGNED PRIMARY KEY COMMENT '书籍ID',
    original_filename   VARCHAR(255)    NOT NULL COMMENT '用户上传的原始文件名（如：小说.txt）',
    file_ext            VARCHAR(8)      NOT NULL COMMENT '文件扩展名（txt/pdf/epub/mobi/azw3）',
    file_size           BIGINT UNSIGNED NOT NULL COMMENT '文件大小（字节）',
    file_hash           CHAR(64)        NOT NULL UNIQUE COMMENT 'SHA256 文件哈希（防重复上传）',
    -- 存储位置
    storage_provider_id VARCHAR(10)     NOT NULL COMMENT '存储服务提供ID',
    file_url            VARCHAR(1024)   NOT NULL COMMENT '文件完整访问URL',
    created_at          DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    updated_at          DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='书籍文件存储表';

# 存储提供商配置表
CREATE TABLE IF NOT EXISTS z_storage_provider
(
    -- 基本信息
    id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '存储提供商ID',
    provider_name   VARCHAR(32)                                 NOT NULL UNIQUE COMMENT '提供商名称（如：local, aws_s3, aliyun_oss）',
    storage_type    ENUM ('local', 's3', 'oss', 'cos', 'minio') NOT NULL COMMENT '存储类型',
    is_active       TINYINT(1)                                  NOT NULL DEFAULT 1 COMMENT '是否启用：1=启用，0=停用',
    -- 本地存储配置（仅 local 模式使用）
    local_root_path VARCHAR(512)                                         DEFAULT NULL COMMENT '本地绝对路径（如：/var/books/）',
    -- S3 兼容配置（local 模式可留空）
    endpoint        VARCHAR(255)                                         DEFAULT NULL COMMENT 'S3 兼容端点（如：https://s3.amazonaws.com）',
    region          VARCHAR(64)                                          DEFAULT NULL COMMENT '区域（如：us-west-2, cn-hangzhou）',
    access_key      VARCHAR(128)                                         DEFAULT NULL COMMENT '访问密钥',
    secret_key      VARCHAR(256)                                         DEFAULT NULL COMMENT '私密密钥',
    bucket_name     VARCHAR(128)                                         DEFAULT NULL COMMENT '存储桶',
    base_path       VARCHAR(255)                                         DEFAULT '/' COMMENT '路径前缀（如：/books/）',
    created_at      DATETIME(6)                                 NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at      DATETIME(6)                                 NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='存储提供商配置表';
