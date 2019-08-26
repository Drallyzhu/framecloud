package com.framecloud.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.framecloud.common.base.vo.SysUserVo;
import com.framecloud.user.model.dto.SysUserInfoDTO;
import com.framecloud.user.model.entity.SysUser;
import com.framecloud.user.model.query.SysUserVoQuery;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    SysUserVo loadUserByUsername(String username);

    /**
     * 通过mobile查找用户
     *
     * @param mobile
     * @return
     */
    SysUserVo loadUserByMobile(String mobile);

    /**
     * 根据userid 与角色信息返回用户详细信息
     *
     * @param userId
     * @param roles
     * @return
     */
    SysUserInfoDTO getUserInfo(Integer userId, List<String> roles);

    /**
     * 用户信息分页查询
     *
     * @param query
     * @return
     */
    SysUserVoQuery pageUserVoByQuery(SysUserVoQuery query);

    /**
     * 添加用户信息（带角色）
     *
     * @param sysUserVo
     * @return
     */
    Boolean save(SysUserVo sysUserVo);

    /**
     * 更新用户信息（带角色）
     *
     * @param sysUserVo
     * @return
     */
    Boolean update(SysUserVo sysUserVo);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    Boolean delete(Integer userId);
}
