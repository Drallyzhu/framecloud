package com.framecloud.user.model.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framecloud.common.base.vo.SysUserVo;
import lombok.Data;

@Data
public class SysUserVoQuery extends Page<SysUserVo> {

    /**
     * 用户名
     */
    private String username;

}
