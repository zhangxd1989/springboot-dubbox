/* System Tables */
DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_menu`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_role_menu`;
DROP TABLE IF EXISTS `sys_user_role`;
DROP TABLE IF EXISTS `trip_user`;

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `login_name` VARCHAR(100) NOT NULL COMMENT '登录名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `name` VARCHAR(100) NOT NULL COMMENT '姓名',
  `email` VARCHAR(200) NULL COMMENT '邮件',
  `phone` VARCHAR(200) NULL COMMENT '电话',
  `mobile` VARCHAR(200) NULL COMMENT '手机',
  `enabled` CHAR(1) NULL DEFAULT '1' COMMENT '是否可用\n1：可用\n0：停用',
  `remarks` VARCHAR(255) NULL COMMENT '备注',
  `create_date` DATETIME NULL COMMENT '创建时间',
  `update_date` DATETIME NULL COMMENT '更新时间',
  `del_flag` CHAR(1) NULL DEFAULT 0 COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_name_UNIQUE` (`login_name` ASC))
  ENGINE = InnoDB
  COMMENT = '系统用户';

CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `parent_id` VARCHAR(64) NOT NULL COMMENT '父ID',
  `parent_ids` VARCHAR(2000) NOT NULL COMMENT '树ID',
  `name` VARCHAR(100) NOT NULL COMMENT '菜单名称',
  `sort` DECIMAL(10,0) NOT NULL COMMENT '排序',
  `href` VARCHAR(2000) NULL COMMENT '链接',
  `icon` VARCHAR(100) NULL COMMENT '图标',
  `is_show` CHAR(1) NULL DEFAULT '1' COMMENT '是否显示\n1：显示\n0：隐藏',
  `permission` VARCHAR(200) NULL COMMENT '权限标识',
  `remarks` VARCHAR(255) NULL COMMENT '备注',
  `create_date` DATETIME NULL COMMENT '创建时间',
  `update_date` DATETIME NULL COMMENT '更新时间',
  `del_flag` CHAR(1) NULL DEFAULT 0 COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  COMMENT = '系统菜单';

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(100) NOT NULL COMMENT '角色名称',
  `enabled` CHAR(1) NULL DEFAULT '1' COMMENT '是否可用\n1：可用\n0：停用',
  `remarks` VARCHAR(255) NULL COMMENT '备注',
  `create_date` DATETIME NULL COMMENT '创建时间',
  `update_date` DATETIME NULL COMMENT '更新时间',
  `del_flag` CHAR(1) NULL DEFAULT 0 COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB
  COMMENT = '系统角色';

CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` VARCHAR(36) NOT NULL COMMENT '角色ID',
  `menu_id` VARCHAR(36) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`))
  ENGINE = InnoDB
  COMMENT = '角色菜单';

CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
  `role_id` VARCHAR(36) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`))
  ENGINE = InnoDB
  COMMENT = '用户角色';

CREATE TABLE IF NOT EXISTS `trip_user` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `mobile` VARCHAR(64) NOT NULL COMMENT '手机',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(255) NULL COMMENT '昵称',
  `gender` CHAR(1) NULL COMMENT '性别\n0：未知\n1：男\n2：女',
  `age` VARCHAR(10) NULL COMMENT '年龄',
  `photo` VARCHAR(100) NULL COMMENT '头像',
  `enabled` CHAR(1) NULL DEFAULT '1' COMMENT '是否可用\n0：冻结\n1：可用',
  `remarks` VARCHAR(255) NULL COMMENT '备注信息',
  `create_date` DATETIME NULL COMMENT '创建时间',
  `update_date` DATETIME NULL COMMENT '更新时间',
  `del_flag` CHAR(1) NULL DEFAULT 0 COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC))
  ENGINE = InnoDB
  COMMENT = '用户';

INSERT INTO `sys_menu` VALUES ('07fb63cf51d74e31b095d42b3dfb7686', '1caf32bb9f6f42e99b1d9413ebe84171', ',50223b0d0c014e848296f7580df517e7,1caf32bb9f6f42e99b1d9413ebe84171,', '修改', '20', null, null, '0', 'trip:user:edit', null, '2016-10-20 11:56:31', '2016-10-20 11:56:31', '0'), ('1caf32bb9f6f42e99b1d9413ebe84171', '50223b0d0c014e848296f7580df517e7', ',50223b0d0c014e848296f7580df517e7,', '用户信息', '10', 'app.trip.user.list', 'glyphicon glyphicon-qrcode', '1', '', '', '2016-10-17 10:40:33', '2016-10-20 11:55:45', '0'), ('50223b0d0c014e848296f7580df517e7', '', ',', '用户管理', '20', null, 'glyphicon glyphicon-user', '1', null, null, '2016-10-17 10:38:49', '2016-10-17 10:38:49', '0'), ('71', '', ',', '首页', '1', 'app.dashboard', 'glyphicon glyphicon-dashboard', '1', 'trip:dashboard:view', '', '2015-10-20 08:00:00', '2016-12-26 11:13:42', '0'), ('863b9d86e81f4e3599ce6e594c2e4932', '1caf32bb9f6f42e99b1d9413ebe84171', ',50223b0d0c014e848296f7580df517e7,1caf32bb9f6f42e99b1d9413ebe84171,', '查看', '10', null, null, '0', 'trip:user:view', null, '2016-10-20 11:56:08', '2016-10-20 11:56:08', '0'), ('92', '', '', '系统设置', '60', '', 'glyphicon glyphicon-book', '1', '', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('921', '92', '92,', '菜单管理', '30', 'app.sys.menu.list', 'glyphicon glyphicon-picture', '1', '', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('9211', '921', '92,921,', '查看', '30', '', '', '0', 'sys:menu:view', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('9212', '921', '92,921,', '修改', '40', '', '', '0', 'sys:menu:edit', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('923', '92', '92,', '用户管理', '10', 'app.sys.user.list', 'glyphicon glyphicon-tag', '1', '', '', '2015-10-20 08:00:00', '2016-10-09 16:11:37', '0'), ('9231', '923', '92,923,', '查看', '30', '', '', '0', 'sys:user:view', '', '2015-10-20 08:00:00', '2016-10-12 16:28:45', '0'), ('9232', '923', '92,923,', '修改', '40', '', '', '0', 'sys:user:edit', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('924', '92', '92,', '角色管理', '20', 'app.sys.role.list', 'glyphicon  glyphicon-list-alt', '1', '', '', '2015-10-20 08:00:00', '2016-10-09 16:11:44', '0'), ('9241', '924', '92,924,', '查看', '30', '', '', '0', 'sys:role:view', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0'), ('9242', '924', '92,924,', '修改', '40', '', '', '0', 'sys:role:edit', '', '2015-10-20 08:00:00', '2015-10-20 08:00:00', '0');
INSERT INTO `sys_role` VALUES ('1', 'ROLE_ADMIN', '1', '管理员角色', '2016-10-09 15:13:21', '2016-12-26 19:28:03', '0'), ('2', 'ROLE_USER', '1', '用户角色', '2016-10-09 15:13:24', '2016-10-14 10:44:16', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '07fb63cf51d74e31b095d42b3dfb7686'), ('1', '1caf32bb9f6f42e99b1d9413ebe84171'), ('1', '50223b0d0c014e848296f7580df517e7'), ('1', '71'), ('1', '863b9d86e81f4e3599ce6e594c2e4932'), ('1', '92'), ('1', '921'), ('1', '9211'), ('1', '9212'), ('1', '923'), ('1', '9231'), ('1', '9232'), ('1', '924'), ('1', '9241'), ('1', '9242'), ('2', '71');
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$08$UIbl948v1vaFLzwr3Hea7uJECTdYsEA8gkxWxNgBLBVXbIG1ODyLO', '系统管理员', '', '', '', '1', '超级管理员', '2016-10-08 19:53:46', '2017-02-10 17:21:30', '0');
INSERT INTO `sys_user_role` VALUES ('1', '1'), ('1', '2');