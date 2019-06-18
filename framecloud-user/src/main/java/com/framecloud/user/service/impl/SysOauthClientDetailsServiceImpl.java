package com.framecloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framecloud.user.mapper.SysOauthClientDetailsMapper;
import com.framecloud.user.model.entity.SysOauthClientDetails;
import com.framecloud.user.service.SysOauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * oauth2客户端资源认证表 服务实现类
 * </p>
 *
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

}
