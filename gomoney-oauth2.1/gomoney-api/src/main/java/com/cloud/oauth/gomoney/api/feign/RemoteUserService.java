/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.oauth.gomoney.api.feign;

import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteUserService", value = "gomoney-biz")
public interface RemoteUserService {

    @GetMapping(value="/sys/user/infoByUserId/{userId}", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    SysUser infoByUserId(@PathVariable String userId);

    @PostMapping(value="/sys/role/add", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    void add(@RequestParam Map<String,Object> params);
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return R
     */
    @NoToken
    @GetMapping(value = "/sys/user/info/{username}", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    SysUser info(@PathVariable("username") String username);

    @NoToken
    @GetMapping(value = "/sys/user/findByMobile/{mobile}", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    SysUser findByMobile(@PathVariable String mobile);

    @NoToken
    @GetMapping(value = "/sys/user/authorized/{username}/{password}", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    SysUser authorized(@PathVariable("username") String username,@PathVariable("password") String password);

    @NoToken
    @GetMapping(value = "/sys/client/getClientDetailsById/{clientId}", headers = SecurityConstants.FROM + "=" + SecurityConstants.FROM_IN)
    R getClientDetailsById(@PathVariable("clientId") String clientId);
}
