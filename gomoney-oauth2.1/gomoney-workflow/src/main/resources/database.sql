create database gomoney_workflow;
use gomoney_workflow;
create table matter_info(
    mid varchar(50) primary key,
    matter_name varchar(50) comment '物料名',
    specs varchar(50) comment '规格',
    brand varchar(50) comment '品牌',
    price decimal(18,2) comment '单价',
    count decimal(18,2) comment '数量',
    url varchar(255) comment '图片路径',
    user_id varchar(255) comment '操作人',
    optrdate datetime comment '操作时间',
    state int comment '状态 0 销毁 1 正常'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='物料信息表';

DROP TABLE IF EXISTS `form_groups`;

CREATE TABLE `form_groups` (
       `group_id` varchar(50) COMMENT 'id',
       `group_name` varchar(50) DEFAULT NULL COMMENT '组名',
       `sort_num` int DEFAULT NULL COMMENT '排序号',
       `created` datetime DEFAULT NULL COMMENT '创建时间',
       `updated` datetime DEFAULT NULL COMMENT '更新时间',
       PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1293705220 DEFAULT CHARSET=utf8mb4 COMMENT='form_groups';

DROP TABLE IF EXISTS `process_templates`;

CREATE TABLE `process_templates` (
     `template_id` varchar(50) NOT NULL COMMENT '审批摸板ID',
     `template_name` varchar(50) DEFAULT NULL COMMENT '摸板名称',
     `settings` longtext COMMENT '基础设置',
     `form_items` longtext COMMENT '摸板表单',
     `process` longtext COMMENT 'process',
     `icon` varchar(50) DEFAULT NULL COMMENT '图标',
     `background` varchar(50) DEFAULT NULL COMMENT '图标背景色',
     `notify` varchar(1000) DEFAULT NULL COMMENT 'notify',
     `who_commit` longtext COMMENT '谁能提交',
     `who_edit` longtext COMMENT '谁能编辑',
     `who_export` longtext COMMENT '谁能导出数据',
     `remark` varchar(200) DEFAULT NULL COMMENT 'remark',
     `group_id` int DEFAULT NULL COMMENT '冗余分组id',
     `is_stop` tinyint(3) DEFAULT NULL COMMENT '是否已停用',
     `created` datetime DEFAULT NULL COMMENT '创建时间',
     `updated` datetime DEFAULT NULL COMMENT '更新时间',
     PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='process_templates';

DROP TABLE IF EXISTS `template_group`;

CREATE TABLE `template_group` (
      `id` varchar(50) NOT NULL COMMENT 'id',
      `template_id` varchar(50) DEFAULT NULL COMMENT 'templateId',
      `group_id` int DEFAULT NULL COMMENT 'groupId',
      `sort_num` int DEFAULT NULL COMMENT 'sortNum',
      `created` datetime DEFAULT NULL COMMENT 'created',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='template_group';
