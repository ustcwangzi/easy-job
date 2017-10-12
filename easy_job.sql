CREATE DATABASE IF NOT EXISTS easy_job DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use easy_job;

DROP TABLE IF EXISTS `easy_job_task`;
CREATE TABLE `easy_job_task` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) NOT NULL,
  `describes` varchar(100) DEFAULT NULL,
  `job_type` varchar(10) NOT NULL,
  `cron` varchar(45) DEFAULT NULL,
  `select_method` varchar(100) DEFAULT NULL,
  `execute_method` varchar(100) DEFAULT NULL,
  `params` varchar(100) DEFAULT NULL,
  `zk_address` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `owner_id` varchar(45) DEFAULT 'admin',
  `is_dispatch` char(1) DEFAULT '0',
  `dubbo_app_protocol` varchar(30) DEFAULT NULL,
  `dubbo_app_name` varchar(30) DEFAULT NULL,
  `dubbo_app_version` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `easy_job_log`;
CREATE TABLE `easy_job_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL,
  `execute_date` datetime DEFAULT NULL,
  `execute_result` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;