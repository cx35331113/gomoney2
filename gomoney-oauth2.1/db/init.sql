set time_zone ='+8:00';
use gomoney_auth;
INSERT INTO sys_user(user_id, user_name, user_pass, user_email, state, optrdate, mobile, realname,enabled) VALUES ('06afa981-46e9-11e9-b7af-3c2c30a6698a', 'admin', '$2a$10$O1UvJK2JsNOyHNNUamdZ2OlSfS0.gikbjcjb/incn9wg82Jau4zpG', 'chenxiang_1984@163.com', '1', now(), '13227653338', '管理员',1);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M001', NULL, '系统管理', NULL, NULL, 0, 'system', 0,1);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M002', 'M001', '管理员列表', 'sys/user', NULL, 1, 'admin', 1,2);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M003', 'M001', '角色管理', 'sys/role', NULL, 1, 'role', 2,2);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M004', 'M001', '菜单管理', 'sys/menu', NULL, 1, 'menu', 3,2);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M015', 'M002', '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M016', 'M002', '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M017', 'M002', '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M018', 'M002', '删除', NULL, 'sys:user:delete', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M019', 'M003', '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M020', 'M003', '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M021', 'M003', '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M022', 'M003', '删除', NULL, 'sys:role:delete', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M023', 'M004', '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M024', 'M004', '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M025', 'M004', '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M026', 'M004', '删除', NULL, 'sys:menu:delete', 2, NULL, 0,3);
INSERT INTO `sys_menu`(menu_id, `parent_menu_id`, `menu_name`, `url`, `perms`, `type`, `icon`, `order_num`,`menu_level`) VALUES ('M027', NULL, '业务管理', NULL, NULL, 0, 'system', 0,0);

INSERT INTO gomoney_auth.oauth2_client (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name) VALUES ('2c9c20818099c695018099cbca030000', 'e2fa7e64-249b-46f0-ae1d-797610e88615', '2022-05-06 22:35:11', '{bcrypt}$2a$10$uHWdt9Ackncw6s5BJlYO9OOdpD3Q44aan0SjttGRCZU2qvvk3fAZO', null, 'felord');
INSERT INTO gomoney_auth.client_auth_method (client_id, client_authentication_method) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'client_secret_basic');
INSERT INTO gomoney_auth.oauth2_client_settings (client_id, require_proof_key, require_authorization_consent, jwk_set_url, signing_algorithm) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', false, true, '', '');
INSERT INTO gomoney_auth.oauth2_grant_type (client_id, grant_type_name) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'authorization_code');
INSERT INTO gomoney_auth.oauth2_grant_type (client_id, grant_type_name) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'client_credentials');
INSERT INTO gomoney_auth.oauth2_grant_type (client_id, grant_type_name) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'refresh_token');
INSERT INTO gomoney_auth.oauth2_scope (client_id, scope, description) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'message.read', '读取信息');
INSERT INTO gomoney_auth.oauth2_scope (client_id, scope, description) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'message.write', '写入信息');
INSERT INTO gomoney_auth.oauth2_scope (client_id, scope, description) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'userinfo', '用户信息');
INSERT INTO gomoney_auth.oauth2_token_settings (client_id, access_token_time_to_live, token_format, reuse_refresh_tokens, refresh_token_time_to_live, id_token_signature_algorithm) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 300000000000, 'self-contained', true, 3600000000000, 'RS256');
INSERT INTO gomoney_auth.redirect_uri (client_id, redirect_uri) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'http://127.0.0.1:8082/foo/bar');
INSERT INTO gomoney_auth.redirect_uri (client_id, redirect_uri) VALUES ('e2fa7e64-249b-46f0-ae1d-797610e88615', 'http://127.0.0.1:8083/login/oauth2/code/felord');

INSERT INTO gomoney_auth.sys_oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, create_time, update_time, create_by, update_by) VALUES ('qq', null, 'qq-secret', 'message.read', 'password,mobile,refresh_token,authorization_code,client_credentials', 'https://www.baidu.com', null, 3600, 36000, null, 'true', null, null, null, null);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
VALUES ('D001', '总经办', 0, 1, null, now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',1);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
  VALUES ('D002', '行政中心', 0, 1, 'D001', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',2);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
 VALUES ('D003', '技术中心', 0, 1, 'D001', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',2);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
VALUES ('D004', '运营中心', 0, 1, 'D001', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',2);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
VALUES ('D005', '研发中心', 0, 1, 'D003', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',3);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
VALUES ('D006', '产品中心', 0, 1, 'D003', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',3);
INSERT INTO `sys_dept`(DEPT_ID, NAME, SORT_ORDER, STATE, PARENT_ID, CREATE_TIME, CREATE_BY_USER_ID, UPDATE_TIME, USER_ID,level)
VALUES ('D007', '测试中心', 0, 1, 'D003', now(), '6afa981-46e9-11e9-b7af-3c2c30a6698a', now(), ' ',3);
COMMIT;
