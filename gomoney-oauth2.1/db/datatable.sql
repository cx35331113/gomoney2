-- create database gomoney_auth;
use gomoney_auth;
drop table if exists sys_captcha;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';
drop table if exists sys_user;
create table `sys_user`(
  `user_id` varchar(50) primary key ,
  `user_name` varchar(32),
  `user_pass` varchar(100),
  `user_email` varchar(32),
  `mobile` varchar(32),
  `realname` varchar(50),
  `state` int,
  `optrdate` datetime,
  `enabled` int,
  `dept_id` varchar(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理员';
#alter table sys_user add dept_id varchar(50) comment '部门编号';
#alter table sys_user add enabled int;
drop table if exists sys_role;
CREATE TABLE sys_role (
  role_id varchar(50) primary key,
  role_name varchar(100),
  remark varchar(100),
  user_id varchar(50),
  create_time datetime
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
drop table if exists sys_user_role;
CREATE TABLE sys_user_role (
  id varchar(50) primary key,
  user_id varchar(50),
  role_id varchar(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';
drop table if exists sys_menu;
create table sys_menu(
  menu_id varchar(50) primary key ,
  menu_name varchar(32),
  url varchar(100),
  perms varchar(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  type int COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon varchar(50) COMMENT '菜单图标',
  menu_level int COMMENT '菜单级别',
  parent_menu_id varchar(50) COMMENT '父级菜单编号',
  `order_num` int COMMENT '排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';
drop table if exists sys_role_menu;
CREATE TABLE sys_role_menu (
  id varchar(50),
  role_id varchar(50) COMMENT '角色ID',
  menu_id varchar(50) COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';


######oauth2.1######
CREATE TABLE oauth2_registered_client (
      id varchar(100) NOT NULL,
      client_id varchar(100) NOT NULL,
      client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
      client_secret varchar(200) DEFAULT NULL,
      client_secret_expires_at timestamp DEFAULT NULL,
      client_name varchar(200) NOT NULL,
      client_authentication_methods varchar(1000) NOT NULL,
      authorization_grant_types varchar(1000) NOT NULL,
      redirect_uris varchar(1000) DEFAULT NULL,
      scopes varchar(1000) NOT NULL,
      client_settings varchar(2000) NOT NULL,
      token_settings varchar(2000) NOT NULL,
      PRIMARY KEY (id)
);
CREATE TABLE oauth2_authorization_consent (
      registered_client_id varchar(100) NOT NULL,
      principal_name varchar(200) NOT NULL,
      authorities varchar(1000) NOT NULL,
      PRIMARY KEY (registered_client_id, principal_name)
);
CREATE TABLE oauth2_authorization (
      id varchar(100) NOT NULL,
      registered_client_id varchar(100) NOT NULL,
      principal_name varchar(200) NOT NULL,
      authorization_grant_type varchar(100) NOT NULL,
      attributes blob DEFAULT NULL,
      state varchar(500) DEFAULT NULL,
      authorization_code_value blob DEFAULT NULL,
      authorization_code_issued_at timestamp DEFAULT NULL,
      authorization_code_expires_at timestamp DEFAULT NULL,
      authorization_code_metadata blob DEFAULT NULL,
      access_token_value blob DEFAULT NULL,
      access_token_issued_at timestamp DEFAULT NULL,
      access_token_expires_at timestamp DEFAULT NULL,
      access_token_metadata blob DEFAULT NULL,
      access_token_type varchar(100) DEFAULT NULL,
      access_token_scopes varchar(1000) DEFAULT NULL,
      oidc_id_token_value blob DEFAULT NULL,
      oidc_id_token_issued_at timestamp DEFAULT NULL,
      oidc_id_token_expires_at timestamp DEFAULT NULL,
      oidc_id_token_metadata blob DEFAULT NULL,
      refresh_token_value blob DEFAULT NULL,
      refresh_token_issued_at timestamp DEFAULT NULL,
      refresh_token_expires_at timestamp DEFAULT NULL,
      refresh_token_metadata blob DEFAULT NULL,
      PRIMARY KEY (id)
);


create table authorization
(
    id                            varchar(100)                            not null
        primary key,
    registered_client_id          varchar(100)                            not null,
    principal_name                varchar(200)                            not null,
    authorization_grant_type      varchar(100)                            not null,
    attributes                    text                                    null,
    state                         varchar(500)                            null,
    authorization_code_value      text                                    null,
    authorization_code_issued_at  datetime default CURRENT_TIMESTAMP     null,
    authorization_code_expires_at datetime null,
    authorization_code_metadata   text                                    null,
    access_token_value            text                                    null,
    access_token_issued_at        datetime default CURRENT_TIMESTAMP     null,
    access_token_expires_at       datetime null,
    access_token_metadata         text                                    null,
    access_token_type             varchar(100)                            null,
    access_token_scopes           varchar(1000)                           null,
    oidc_id_token_value           text                                    null,
    oidc_id_token_issued_at       timestamp default CURRENT_TIMESTAMP     null,
    oidc_id_token_expires_at      timestamp default CURRENT_TIMESTAMP     null,
    oidc_id_token_metadata        text                                    null,
    refresh_token_value           text                                    null,
    refresh_token_issued_at       timestamp default CURRENT_TIMESTAMP     null,
    refresh_token_expires_at      timestamp default CURRENT_TIMESTAMP     null,
    refresh_token_metadata        text                                    null,
    oidc_id_token_claims          varchar(2000)                           null
);

create table authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

create table client_auth_method
(
    client_id                    varchar(255) not null comment 'oauth2客户端id',
    client_authentication_method varchar(255) not null comment '客户端认证方式',
    primary key (client_id, client_authentication_method)
)
    comment 'oauth2客户端认证方式表';

create table menu
(
    id        varchar(255) not null
        primary key,
    parent_id varchar(255) not null,
    title     varchar(255) not null,
    type      varchar(255) null comment '是否可跳转，0 不可跳转  1 可跳转',
    open_type varchar(255) null,
    icon      varchar(255) null,
    href      varchar(255) null
);

create table oauth2_client_settings
(
    client_id                     varchar(255)         not null comment 'oauth2客户端id'
        primary key,
    require_proof_key             tinyint(1) default 0 null comment '客户端是否需要证明密钥',
    require_authorization_consent tinyint(1) default 0 null comment '客户端是否需要授权确认页面',
    jwk_set_url                   varchar(255)         null comment 'jwkSet url',
    signing_algorithm             varchar(255)         null comment '支持的签名算法'
)
    comment 'oauth2客户端配置';

create table oauth2_client
(
    id                       varchar(100)                        not null comment 'ID'
        primary key,
    client_id                varchar(100)                        not null comment 'oauth2客户端id',
    client_id_issued_at      timestamp default CURRENT_TIMESTAMP not null comment '客户端创建时间',
    client_secret            varchar(200)                        not null comment '客户端密码',
    client_secret_expires_at timestamp                           null comment '客户端密码过期时间',
    client_name              varchar(200)                        not null comment '客户端名称',
    constraint UK_drwlno5wbex09l0acnnwecp7r
        unique (client_id)
)
    comment 'oauth2客户端基础信息表';

create table oauth2_grant_type
(
    client_id       varchar(255) not null comment 'oauth2客户端id',
    grant_type_name varchar(255) not null comment '客户端授权方式',
    primary key (client_id, grant_type_name)
)
    comment 'oauth2客户端授权方式表';

create table oauth2_scope
(
    client_id   varchar(255) not null comment 'oauth2客户端id',
    scope       varchar(255) not null comment '客户端允许的scope 来自role表',
    description varchar(255) null,
    primary key (client_id, scope)
)
    comment 'oauth2客户端授权范围';

create table oauth2_token_settings
(
    client_id                    varchar(255)         not null comment 'oauth2客户端id'
        primary key,
    access_token_time_to_live    bigint               null comment 'access_token 有效时间',
    token_format                 varchar(255)         null comment 'token 格式  jwt、opaque',
    reuse_refresh_tokens         tinyint(1) default 1 null comment '是否重用 refresh_token',
    refresh_token_time_to_live   bigint               null comment 'refresh_token 有效时间',
    id_token_signature_algorithm varchar(255)         null comment 'oidc id_token 签名算法'
)
    comment 'oauth2客户端的token配置项';

create table redirect_uri
(
    client_id    varchar(255) not null comment 'oauth2客户端id',
    redirect_uri varchar(255) not null comment '客户端允许重定向的uri',
    primary key (client_id, redirect_uri)
)
    comment 'oauth2客户端重定向uri表';

drop table if exists sys_user_dept;
create table sys_user_dept(
    user_dept_id varchar(50) primary key,
    user_id  varchar(50),
    dept_id varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户部门管理';

DROP TABLE IF EXISTS `sys_dept` ;
CREATE TABLE `sys_dept` (
    `dept_id` varchar(50) NOT NULL,
    `name` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称',
    `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序',
    `state` int COMMENT '是否删除  -1：已删除  0：正常',
    `parent_id` varchar(50) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_by_user_id` varchar(64) COMMENT '创建人',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `user_id` varchar(64) COMMENT '更新人',
    `level` int COMMENT '部门级别',
    PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门管理';
create table sys_oauth_client_details
(
    client_id               varchar(32)   not null comment '客户端ID'
        primary key,
    resource_ids            varchar(256)  null comment '资源列表',
    client_secret           varchar(256)  null comment '客户端密钥',
    scope                   varchar(256)  null comment '域',
    authorized_grant_types  varchar(256)  null comment '认证类型',
    web_server_redirect_uri varchar(256)  null comment '重定向地址',
    authorities             varchar(256)  null comment '角色列表',
    access_token_validity   int           null comment 'token 有效期',
    refresh_token_validity  int           null comment '刷新令牌有效期',
    additional_information  varchar(4096) null comment '令牌扩展字段JSON',
    autoapprove             varchar(256)  null comment '是否自动放行',
    create_time             datetime      null comment '创建时间',
    update_time             datetime      null comment '更新时间',
    create_by               varchar(64)   null comment '创建人',
    update_by               varchar(64)   null comment '更新人'
)
    comment '终端信息表' charset = utf8;



drop table parchase_orders;
create table parchase_orders(
        pid varchar(50) primary key comment '采购单id',
        parchase_user_id varchar(50) comment '采购员id',
        supplier_company_name varchar(50) comment '供应商公司',
        supplier_phone varchar(50) comment '供应商电话',
        supplier_address varchar(50) comment '供应商地址',
        supplier_contacter varchar(50) comment '供应商联系人',
        order_number varchar(50) comment '订单编号',
        create_time datetime comment '创建时间',
        payment varchar(50) comment '支付方式',
        user_id varchar(50) comment '销售员'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购单';
drop table parchase_order_details;
create table parchase_order_details(
               did varchar(50) primary key comment '采购单明细id',
               pid varchar(50) comment '采购单id',
               product_id varchar(50) comment '产品id',
               product_name varchar(50) comment '产品名称',
               product_price decimal(18,2) comment '产品单价',
               specifications varchar(50) comment '规格',
               unit varchar(50) comment '单位',
               quantity decimal(18,2) comment '数量',
               tax_rate decimal(18,2) comment '税率',
               Delivery_Time datetime comment '交货时间',
               bak varchar(50) comment '备注'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购单明细';

