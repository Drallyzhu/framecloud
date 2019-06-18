package com.framecloud.gateway.controller;

import com.framecloud.common.base.vo.SysResourceVO;
import com.framecloud.common.util.ApiResult;
import com.framecloud.gateway.feign.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ResourceController {


    @Autowired
    private SysResourceService sysResourceService;


    @GetMapping("/res")
    public ApiResult<Set<SysResourceVO>> readAllToken() {
        Set<SysResourceVO> ss =  sysResourceService.listResourceByRole("ROLE_ADMIN");
        return new ApiResult<>(ss);
    }


}
