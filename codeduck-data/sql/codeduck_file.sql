/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18-log : Database - codeduck_file
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`codeduck_file` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `codeduck_file`;

/*Table structure for table `tb_contract` */

DROP TABLE IF EXISTS `tb_contract`;

CREATE TABLE `tb_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '合同文件id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `content` text COMMENT '文件内容',
  `file_level` varchar(25) DEFAULT NULL COMMENT '文件级别',
  `region` varchar(50) DEFAULT NULL COMMENT '文件所属地区',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '文件上传时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '文件修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '文件是否被删除：0未被删除，1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_contract` */

/*Table structure for table `tb_picture` */

DROP TABLE IF EXISTS `tb_picture`;

CREATE TABLE `tb_picture` (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `alias` varchar(255) DEFAULT NULL COMMENT '图片别名',
  `original_name` varchar(255) DEFAULT NULL COMMENT '原图片名',
  `pic_name` varchar(255) DEFAULT NULL COMMENT '现图片名',
  `local_url` varchar(255) DEFAULT NULL COMMENT '服务器图片地址',
  `qi_niu_url` varchar(255) DEFAULT NULL COMMENT '七牛云图片地址',
  `md_url` varchar(255) DEFAULT NULL COMMENT 'markdown图片地址',
  `suffix_name` varchar(255) DEFAULT NULL COMMENT '文件后缀名',
  `pic_size` int(20) DEFAULT '0' COMMENT '图片大小',
  `resolution` varchar(36) DEFAULT NULL COMMENT '图片分辨率',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

/*Data for the table `tb_picture` */

insert  into `tb_picture`(`id`,`alias`,`original_name`,`pic_name`,`local_url`,`qi_niu_url`,`md_url`,`suffix_name`,`pic_size`,`resolution`,`deleted`,`creation_time`,`update_time`) values ('004775028a2f275c50620305ed7ae35e','202011974','actor.jpg','2020119-69f59c0f-077c-4bf4-b4d6-aa0ec20a6007.jpg','http://localhost:8600/upload/images/2020/11/2020119-69f59c0f-077c-4bf4-b4d6-aa0ec20a6007.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020119-69f59c0f-077c-4bf4-b4d6-aa0ec20a6007.jpg)','jpg',83288,'960x960',1,'2020-11-09 15:50:40','2020-11-09 15:50:40'),('0d1b90da1032e7cb2002ba012cfcc71c','202011943','2000698.jpg','2020119-c7f34f8d-a17b-4dfb-8ba4-f09722f6d61c.jpg','http://localhost:8600/upload/images/2020/11/2020119-c7f34f8d-a17b-4dfb-8ba4-f09722f6d61c.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020119-c7f34f8d-a17b-4dfb-8ba4-f09722f6d61c.jpg)','jpg',270760,'1080x1920',0,'2020-11-09 15:52:40','2020-11-09 15:52:40'),('0f72c4b133e17b7b307e2a568b4771a1','202011789','2033155.jpg','2020117-157ae157-4804-4159-a713-1f7dc97da654.jpg','http://localhost:8600/upload/images/2020/11/2020117-157ae157-4804-4159-a713-1f7dc97da654.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-157ae157-4804-4159-a713-1f7dc97da654.jpg)','jpg',825755,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('196155dbbdbab76ad0dd54847060704d','202011785','2030196.jpg','2020117-73a2067e-d01f-4618-9a68-372a3bdb1d27.jpg','http://localhost:8600/upload/images/2020/11/2020117-73a2067e-d01f-4618-9a68-372a3bdb1d27.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-73a2067e-d01f-4618-9a68-372a3bdb1d27.jpg)','jpg',730981,'1440x2560',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('23df656e06732cd32b61bfdbadb40fd5','202011776','2034268.jpg','2020117-faa41821-d9e0-4663-90b8-361be069b9e1.jpg','http://localhost:8600/upload/images/2020/11/2020117-faa41821-d9e0-4663-90b8-361be069b9e1.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-faa41821-d9e0-4663-90b8-361be069b9e1.jpg)','jpg',750437,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('24abb69fc18ef5ab4b69c3c19877a0b7','20201265','2031995.jpg','2020126-d1ddd8b8-8a44-41ea-b4b6-6becf2448520.jpg','http://codeduck.top/2020126-d1ddd8b8-8a44-41ea-b4b6-6becf2448520.jpg','http://codeduck.top/2020126-d1ddd8b8-8a44-41ea-b4b6-6becf2448520.jpg','![202012](http://codeduck.top/2020126-d1ddd8b8-8a44-41ea-b4b6-6becf2448520.jpg)','jpg',647359,'1080x1920',1,'2020-12-06 21:43:24',NULL),('281196a0ecb21865585491bdf813bcf6','202011748','2034608.jpg','2020117-cd88a378-5b26-4eed-9a5a-83513c356b20.jpg','http://localhost:8600/upload/images/2020/11/2020117-cd88a378-5b26-4eed-9a5a-83513c356b20.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-cd88a378-5b26-4eed-9a5a-83513c356b20.jpg)','jpg',722262,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('2cda2fe4273cc042e9d765ae58e02e75','202011783','2034169.jpg','2020117-9cfc209c-c898-423f-99dc-9ab71693c71e.jpg','http://localhost:8600/upload/images/2020/11/2020117-9cfc209c-c898-423f-99dc-9ab71693c71e.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-9cfc209c-c898-423f-99dc-9ab71693c71e.jpg)','jpg',748301,'1440x2560',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('2fe1dc8b2b7460c2cfbc4069862150c5','202011712','2030195.jpg','2020117-111dce65-5f83-4335-90a1-afffe55a24f2.jpg','http://localhost:8600/upload/images/2020/11/2020117-111dce65-5f83-4335-90a1-afffe55a24f2.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-111dce65-5f83-4335-90a1-afffe55a24f2.jpg)','jpg',693109,'1440x2560',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('37668f2d4ec5b007c8759779009aa122','202011762','2034606.jpg','2020117-819898da-bdce-4923-8e16-8a9a606e775a.jpg','http://localhost:8600/upload/images/2020/11/2020117-819898da-bdce-4923-8e16-8a9a606e775a.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-819898da-bdce-4923-8e16-8a9a606e775a.jpg)','jpg',838760,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('3b1369fec910a6f4e6f57cb782e1f5c1','202011788','2030828.jpg','2020117-bb88a80f-c54d-4fec-9984-4fdddf6a5994.jpg','http://localhost:8600/upload/images/2020/11/2020117-bb88a80f-c54d-4fec-9984-4fdddf6a5994.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-bb88a80f-c54d-4fec-9984-4fdddf6a5994.jpg)','jpg',902047,'2160x3840',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('3e1930d26a6205094110bde0ef504e03','202011759','1001558.jpg','2020117-7d54d40d-5a7c-4c4a-9f9c-841eeb0759fe.jpg','http://localhost:8600/upload/images/2020/11/2020117-7d54d40d-5a7c-4c4a-9f9c-841eeb0759fe.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-7d54d40d-5a7c-4c4a-9f9c-841eeb0759fe.jpg)','jpg',839752,'1080x1920',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('3e950acec31a60f8393097f9281784da','202012642','2019247.jpg','2020126-1ddf1ec2-4b27-4250-8ece-3911df716c72.jpg','http://codeduck.top/2020126-1ddf1ec2-4b27-4250-8ece-3911df716c72.jpg','http://codeduck.top/2020126-1ddf1ec2-4b27-4250-8ece-3911df716c72.jpg','![202012](http://codeduck.top/2020126-1ddf1ec2-4b27-4250-8ece-3911df716c72.jpg)','jpg',418077,'1080x1920',0,'2020-12-06 21:41:42',NULL),('43bbd47ed48846c2e44e3bcd46ce903e','202012660','2001521.jpg','2020126-3ea9eba2-7728-4a7f-bf99-87a3891137e6.jpg','http://codeduck.top/2020126-3ea9eba2-7728-4a7f-bf99-87a3891137e6.jpg','http://codeduck.top/2020126-3ea9eba2-7728-4a7f-bf99-87a3891137e6.jpg','![202012](http://codeduck.top/2020126-3ea9eba2-7728-4a7f-bf99-87a3891137e6.jpg)','jpg',227841,'1080x1920',1,'2020-12-06 20:49:03',NULL),('4d19c6f4395c8a2b3ca658edb85eb658','20201269','2034608.jpg','2020126-a0cc6a1c-f89c-4f43-b502-a057224d968a.jpg','http://localhost:8600/upload/images/2020/12/2020126-a0cc6a1c-f89c-4f43-b502-a057224d968a.jpg',NULL,'![202012](http://localhost:8600/upload/images/2020/12/2020126-a0cc6a1c-f89c-4f43-b502-a057224d968a.jpg)','jpg',722262,'1080x1920',0,'2020-12-06 21:42:13',NULL),('548bfb8117451e2f31a56abbf82b1690','202012678','2019419.jpg','2020126-fc696f68-e4b1-4cd4-824a-c0920887293d.jpg','http://codeduck.top/2020126-fc696f68-e4b1-4cd4-824a-c0920887293d.jpg','http://codeduck.top/2020126-fc696f68-e4b1-4cd4-824a-c0920887293d.jpg','![202012](http://codeduck.top/2020126-fc696f68-e4b1-4cd4-824a-c0920887293d.jpg)','jpg',446326,'1080x1920',0,'2020-12-06 21:42:03',NULL),('549692e619ad362e1b8f6a3576181e87','202011737','2032866.jpg','2020117-0eb5121e-b6e4-40c2-81d2-16833663cafe.jpg','http://localhost:8600/upload/images/2020/11/2020117-0eb5121e-b6e4-40c2-81d2-16833663cafe.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-0eb5121e-b6e4-40c2-81d2-16833663cafe.jpg)','jpg',882320,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('5811813ae1b80253ac78012613697c86','202012649','2025519.jpg','2020126-a0c6d425-cd14-4699-aa03-ac89fc02aed6.jpg','http://codeduck.top/2020126-a0c6d425-cd14-4699-aa03-ac89fc02aed6.jpg','http://codeduck.top/2020126-a0c6d425-cd14-4699-aa03-ac89fc02aed6.jpg','![202012](http://codeduck.top/2020126-a0c6d425-cd14-4699-aa03-ac89fc02aed6.jpg)','jpg',336005,'1080x1920',0,'2020-12-06 21:41:25',NULL),('5c23319ce63d8c0807ab2d779590fd7b','202011793','2031995.jpg','2020117-960b1921-a6e9-43bd-b694-abd949d5cf68.jpg','http://localhost:8600/upload/images/2020/11/2020117-960b1921-a6e9-43bd-b694-abd949d5cf68.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-960b1921-a6e9-43bd-b694-abd949d5cf68.jpg)','jpg',647359,'1080x1920',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('60767330e37b329ba707a84574efde29','202012669','b387.jpg','2020126-0816d476-33fc-4970-8186-1dc609ac268c.jpg','http://localhost:8600/upload/images/2020/12/2020126-0816d476-33fc-4970-8186-1dc609ac268c.jpg',NULL,'![202012](http://localhost:8600/upload/images/2020/12/2020126-0816d476-33fc-4970-8186-1dc609ac268c.jpg)','jpg',63089,'448x448',1,'2020-12-06 20:22:39',NULL),('6090506cbb3bc4ec856c20262f8476d5','202011728','2034845.jpg','2020117-e99fbee8-9271-409c-ad06-33cfdae1d316.jpg','http://localhost:8600/upload/images/2020/11/2020117-e99fbee8-9271-409c-ad06-33cfdae1d316.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-e99fbee8-9271-409c-ad06-33cfdae1d316.jpg)','jpg',341207,'1080x1920',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('7375f2c9e512ce883adbaa2e69326fe7','202012637','2034166.jpg','2020126-eb842b3d-cacf-4600-b137-5d221381c335.jpg','http://codeduck.top/2020126-eb842b3d-cacf-4600-b137-5d221381c335.jpg','http://codeduck.top/2020126-eb842b3d-cacf-4600-b137-5d221381c335.jpg','![202012](http://codeduck.top/2020126-eb842b3d-cacf-4600-b137-5d221381c335.jpg)','jpg',831257,'1080x1920',0,'2020-12-06 20:46:13',NULL),('7cbd86d1840a130167eb230b588a7946','202011731','2034041.jpg','2020117-c1c7b0fb-932a-4c60-b0cc-6708ebfb5c65.jpg','http://localhost:8600/upload/images/2020/11/2020117-c1c7b0fb-932a-4c60-b0cc-6708ebfb5c65.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-c1c7b0fb-932a-4c60-b0cc-6708ebfb5c65.jpg)','jpg',604828,'2160x3840',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('83d624ff5e88398d43489875a59e87e3','202012682','2000698.jpg','2020126-3a5a9aba-8324-4b8d-b777-52de3dd95850.jpg','http://codeduck.top/2020126-3a5a9aba-8324-4b8d-b777-52de3dd95850.jpg','http://codeduck.top/2020126-3a5a9aba-8324-4b8d-b777-52de3dd95850.jpg','![202012](http://codeduck.top/2020126-3a5a9aba-8324-4b8d-b777-52de3dd95850.jpg)','jpg',270760,'1080x1920',0,'2020-12-06 21:47:01',NULL),('851ffe3f8578c7902f61efd48497e698','202011765','2034107.jpg','2020117-e56dcc44-97f8-474d-83e9-b01475b4dc82.jpg','http://localhost:8600/upload/images/2020/11/2020117-e56dcc44-97f8-474d-83e9-b01475b4dc82.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-e56dcc44-97f8-474d-83e9-b01475b4dc82.jpg)','jpg',829927,'1440x2560',0,'2020-11-07 16:45:50','2020-11-07 16:45:50'),('8aee1f5d8ca5124ec91892ecebf8be9e','202011729','2000782.jpg','2020117-4a8b9740-d0f0-4c26-8c61-e7cb954130df.jpg','http://localhost:8600/upload/images/2020/11/2020117-4a8b9740-d0f0-4c26-8c61-e7cb954130df.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-4a8b9740-d0f0-4c26-8c61-e7cb954130df.jpg)','jpg',702703,'1080x1920',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('8e8a5333d5d0fb72a722376c1e820507','202012622','324724.jpg','2020126-1dc6f9d7-745c-4272-b3e7-9199384208dc.jpg','http://codeduck.top/2020126-1dc6f9d7-745c-4272-b3e7-9199384208dc.jpg','http://codeduck.top/2020126-1dc6f9d7-745c-4272-b3e7-9199384208dc.jpg','![202012](http://codeduck.top/2020126-1dc6f9d7-745c-4272-b3e7-9199384208dc.jpg)','jpg',169955,'1080x1920',1,'2020-12-06 20:47:12',NULL),('99e3c63a9c40761aa585d865b43f24f9','2020112411','微信图片_20201124142410.png','20201124-d86a5335-3d49-46d4-9b44-e535576876a7.png','http://localhost:8600/upload/images/2020/11/20201124-d86a5335-3d49-46d4-9b44-e535576876a7.png',NULL,'![202011](http://localhost:8600/upload/images/2020/11/20201124-d86a5335-3d49-46d4-9b44-e535576876a7.png)','png',3280,'433x653',1,'2020-11-24 14:24:45','2020-11-24 14:24:45'),('9a785d445265b0b5f9bf90e6b89c9a9a','202011776','2031992.jpg','2020117-da332f6e-b980-4593-aa0c-68fdb684d225.jpg','http://localhost:8600/upload/images/2020/11/2020117-da332f6e-b980-4593-aa0c-68fdb684d225.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-da332f6e-b980-4593-aa0c-68fdb684d225.jpg)','jpg',612792,'1080x1920',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('9c0a5df0f1421888d01fa3eafcbb36a1','202012686','2000698.jpg','2020126-2b0292fa-750a-425d-a445-0d8186e97a83.jpg','http://codeduck.top/2020126-2b0292fa-750a-425d-a445-0d8186e97a83.jpg','http://codeduck.top/2020126-2b0292fa-750a-425d-a445-0d8186e97a83.jpg','![202012](http://codeduck.top/2020126-2b0292fa-750a-425d-a445-0d8186e97a83.jpg)','jpg',270760,'1080x1920',0,'2020-12-06 21:39:41',NULL),('a845771e5c1d81efa9ee05416134a75b','202011780','1001062.jpg','2020117-0ecc80cf-c1d3-4397-b02d-86d78e3e44e7.jpg','http://localhost:8600/upload/images/2020/11/2020117-0ecc80cf-c1d3-4397-b02d-86d78e3e44e7.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-0ecc80cf-c1d3-4397-b02d-86d78e3e44e7.jpg)','jpg',1857204,'1440x2560',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('aa713612041c88583a9158748a13e75d','202011970','favicon.jpg','2020119-8e1ceb2a-4c95-4ee3-85ac-730721308d25.jpg','http://localhost:8600/upload/images/2020/11/2020119-8e1ceb2a-4c95-4ee3-85ac-730721308d25.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020119-8e1ceb2a-4c95-4ee3-85ac-730721308d25.jpg)','jpg',38505,'449x449',0,'2020-11-09 15:02:46','2020-11-09 15:02:46'),('af77e33563ca7ec70337a968d42f5531','20201268','2000782.jpg','2020126-a3ce00c2-0b53-48c4-8f07-b4701cdc3593.jpg','http://codeduck.top/2020126-a3ce00c2-0b53-48c4-8f07-b4701cdc3593.jpg','http://codeduck.top/2020126-a3ce00c2-0b53-48c4-8f07-b4701cdc3593.jpg','![202012](http://codeduck.top/2020126-a3ce00c2-0b53-48c4-8f07-b4701cdc3593.jpg)','jpg',702703,'1080x1920',1,'2020-12-06 21:43:35',NULL),('ba715fd294d6906d61f345676431140f','202012651','2000698.jpg','2020126-5f6c4968-db3e-4800-aae8-e77c606e0bd4.jpg','http://localhost:8600/upload/images/2020/12/2020126-5f6c4968-db3e-4800-aae8-e77c606e0bd4.jpg',NULL,'![202012](http://localhost:8600/upload/images/2020/12/2020126-5f6c4968-db3e-4800-aae8-e77c606e0bd4.jpg)','jpg',270760,'1080x1920',1,'2020-12-06 20:48:57',NULL),('bedc4a70fef1c1a660d78184b4b6f345','20201176','2031005.jpg','2020117-3f47f5be-9605-43b0-a815-2c9d49d5d516.jpg','http://localhost:8600/upload/images/2020/11/2020117-3f47f5be-9605-43b0-a815-2c9d49d5d516.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-3f47f5be-9605-43b0-a815-2c9d49d5d516.jpg)','jpg',687684,'1080x1920',0,'2020-11-07 16:45:49','2020-11-07 16:45:49'),('d4da18359c554a0e0d3b21f53948bd31','202012691','2000782.jpg','2020126-a140612e-de13-44f4-ab55-bdd5703b408b.jpg','http://codeduck.top/2020126-a140612e-de13-44f4-ab55-bdd5703b408b.jpg','http://codeduck.top/2020126-a140612e-de13-44f4-ab55-bdd5703b408b.jpg','![202012](http://codeduck.top/2020126-a140612e-de13-44f4-ab55-bdd5703b408b.jpg)','jpg',702703,'1080x1920',0,'2020-12-06 21:45:53',NULL),('d9c6f07e4a51ab4eaa01faa919f33166','202012617','2003222.jpg','2020126-a042cc22-2156-4413-9428-3486d831ae39.jpg','http://codeduck.top/2020126-a042cc22-2156-4413-9428-3486d831ae39.jpg','http://codeduck.top/2020126-a042cc22-2156-4413-9428-3486d831ae39.jpg','![202012](http://codeduck.top/2020126-a042cc22-2156-4413-9428-3486d831ae39.jpg)','jpg',1115067,'1080x1920',1,'2020-12-06 21:42:31',NULL),('e9cbc964c7e1254353513a83d38dbd6e','202012671','324250.jpg','2020126-655965fc-d30d-4c19-ba6b-2cc9b8b197a4.jpg','http://localhost:8600/upload/images/2020/12/2020126-655965fc-d30d-4c19-ba6b-2cc9b8b197a4.jpg',NULL,'![202012](http://localhost:8600/upload/images/2020/12/2020126-655965fc-d30d-4c19-ba6b-2cc9b8b197a4.jpg)','jpg',267454,'1200x1920',0,'2020-12-06 21:39:47',NULL),('f15fc44e95efc5b8ae18d9a488868e51','2020112495','placeholder.jpg','20201124-c9368084-9af8-4bb5-879e-70d28c1ebe92.jpg','http://localhost:8600/upload/images/2020/11/20201124-c9368084-9af8-4bb5-879e-70d28c1ebe92.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/20201124-c9368084-9af8-4bb5-879e-70d28c1ebe92.jpg)','jpg',5474,'390x585',0,'2020-11-24 14:37:47','2020-11-24 14:37:47'),('ff73f9703b23f6ba90ab8be4199a1542','202012683','325702.jpg','2020126-44de6001-99d9-43ac-acd5-7fdb05b418e7.jpg','http://localhost:8600/upload/images/2020/12/2020126-44de6001-99d9-43ac-acd5-7fdb05b418e7.jpg',NULL,'![202012](http://localhost:8600/upload/images/2020/12/2020126-44de6001-99d9-43ac-acd5-7fdb05b418e7.jpg)','jpg',182277,'1080x1920',0,'2020-12-06 20:31:41',NULL),('fffd9d4fef200ea7a4df36143743db25','20201170','2030199.jpg','2020117-972d7749-3c0e-420f-9b42-a0625eb8bf7d.jpg','http://localhost:8600/upload/images/2020/11/2020117-972d7749-3c0e-420f-9b42-a0625eb8bf7d.jpg',NULL,'![202011](http://localhost:8600/upload/images/2020/11/2020117-972d7749-3c0e-420f-9b42-a0625eb8bf7d.jpg)','jpg',816112,'1440x2560',0,'2020-11-07 16:45:49','2020-11-07 16:45:49');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
