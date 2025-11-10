# Z-Reader Auth Module API

本文档概述了 `z-reader-auth` 模块中可用的 API 端点。

## 登录

*   **端点:** `POST /api/auth/login`
*   **描述:** 对用户进行身份验证，并在成功登录后返回令牌。
*   **请求体:**
    ```json
    {
      "username": "your_username",
      "password": "your_password"
    }
    ```
*   **字段:**
    *   `username` (string, 必需): 用户名。
    *   `password` (string, 必需): 密码。
*   **成功响应:**
    *   **代码:** `200 OK`
    *   **内容:** 包含身份验证令牌的 JSON 对象。
        ```json
        {
          "code": 200,
          "message": "Success",
          "data": "your_auth_token"
        }
        ```
