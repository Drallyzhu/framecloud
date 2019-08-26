package com.framecloud.gateway.feign;

import com.framecloud.common.base.vo.SysResourceVO;
import com.framecloud.gateway.config.FeignConfig;
import com.framecloud.gateway.feign.fallback.SysResourceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;


@FeignClient(name = "framecloud-user-service", fallback = SysResourceFallback.class, configuration = FeignConfig.class)
public interface SysResourceService {

    /**
     * 根据角色查询资源信息
     *
     * @param roleCode
     * @return
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<SysResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode);


}
