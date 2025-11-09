package com.zreader.auth.service.impl;


import com.zreader.auth.dto.LoginDTO;
import com.zreader.auth.mapper.ZUserMapper;
import com.zreader.auth.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @author zy
 * @date 2025/11/9
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final ZUserMapper zUserMapper;

    public AuthServiceImpl(ZUserMapper zUserMapper) {
        this.zUserMapper = zUserMapper;
    }

    @Override
    public String login(LoginDTO loginDto) {
        return "abc123";
    }
}
