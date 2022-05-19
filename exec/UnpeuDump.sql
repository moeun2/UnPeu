-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: k6b201.p.ssafy.io    Database: unpeu
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` varchar(10000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `title` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`board_id`),
  KEY `cate_index` (`category`),
  KEY `FKe8bj5yd6jrd9e5326rm8yk2ls` (`user_id`),
  CONSTRAINT `FKe8bj5yd6jrd9e5326rm8yk2ls` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (5,'2022_어른이날','등록 중.. 정윤 페이지 (0원 펀딩)','2022-05-04 16:28:04.856739','메세지',1),(6,'2022_어른이날','메세지 (0원 펀딩)','2022-05-04 16:28:51.566026','김정윤',1),(7,'Default','ㅇㅇㅇㅇ','2022-05-04 19:00:00.450121','ㅇㅇㅇㅇ',7),(8,'Default','많메부~','2022-05-04 19:59:47.536681','이벤트룸 개설했어요!',8),(9,'Default','ㄷㄹㄴㄷㄹㄴㄹㄴㄷㄹㄴㄷㄹㄴㄷㄹㄹㄷㅈㄴㄷㄻㄷㄹ','2022-05-04 20:02:44.253018','ㄹㄷㄴㄷㄹㄴㄷㄹㄷ',9),(10,'Default','테스트!','2022-05-04 21:10:55.275550','테스트입니다',13),(12,'Default','뭔가 단단히 잘못됬다.','2022-05-06 09:26:17.342621','아무도 나에게 선물을 주지않았다.',20),(13,'Default','zz','2022-05-06 11:36:13.764448','zz',22),(14,'Default','adfadff','2022-05-06 11:36:43.516119','adf',23),(15,'Default','하이이이잉','2022-05-06 14:59:36.515325','하잉',36),(16,'Default','도망치고싶다','2022-05-06 21:41:51.733439','아 ㅋㅋㅋㅋㅋ',40),(30,'2022_어른이날','Hello (0원 펀딩)','2022-05-09 15:09:24.185100','Hello',1),(79,'2022_어른이날','ss (0원 펀딩)','2022-05-09 15:11:58.473627','aa',4),(80,'2022_어른이날','asd (0원 펀딩)','2022-05-09 15:12:02.901756','asd',4),(85,'2022_어른이날','dasdasdaa (0원 펀딩)','2022-05-09 15:12:34.377763','aa',4),(87,'2022_어른이날','sss (0원 펀딩)','2022-05-09 15:16:37.127822','aaa',4),(88,'2022_어른이날','dd (0원 펀딩)','2022-05-09 15:16:41.425061','ss',4),(90,'2022_어른이날','ㅣㅣㅣ (0원 펀딩)','2022-05-09 15:21:48.041941',';;;',4),(91,'2022_어른이날','ㅇㅇㅇㅇ (0원 펀딩)','2022-05-09 15:22:21.605375','ㄹㄹㄹㄹㄹ',4),(92,'2022_어른이날','ㅇ (0원 펀딩)','2022-05-09 15:13:14.858960','ㅇ',2),(93,'2022_어른이날','ㅎㅎㅎ (0원 펀딩)','2022-05-09 15:18:42.249531','ㅎㅎㅎ',2),(95,'2022_어른이날','hello (0원 펀딩)','2022-05-09 15:46:55.502153','hello',1),(98,'Default','d','2022-05-09 16:53:57.865386','d',2),(99,'Default','동물의 숲 같이 하자~~','2022-05-09 17:00:06.513448','선물 줘 포로리야..',4),(100,'Default','<p>이것이야말로 지혜는 바로 꽃 가슴이 없으면, 그림자는 이것이다. 돋고, 되려니와, 그러므로 영락과 것이다.보라, 뜨고, 충분히 주는 있다. 관현악이며, 피에 같이, 투명하되 봄바람을 사는가 그리하였는가? 이상을 싸인 노년에게서 이상은 인도하겠다는 물방아 따뜻한 운다. 가장 이상을 끓는 황금시대의 역사를 때까지 눈이 오아이스도 쓸쓸하랴? 수 미묘한 피부가 몸이 이상이 속에서 대중을 보이는 노래하며 있는가? 뼈 그들은 몸이 피다. 간에 하는 위하여 충분히 그러므로 그것을 싸인 아니더면, 보는 듣는다. 원대하고, 있는 수 아니다. 뜨거운지라, 밝은 생명을 청춘이 이상의 부패를 인생의 것이다.\n\n온갖 내려온 그들은 내는 장식하는 가치를 열매를 때문이다. 우리는 우는 이상의 부패를 날카로우나 길을 더운지라 약동하다. 어디 웅대한 든 하는 것이다. 인간은 미묘한 군영과 있을 타오르고 뭇 용감하고 봄바람이다. 무엇을 이것을 피는 위하여서 충분히 그들의 부패를 너의 청춘의 끓는다. 긴지라 역사를 청춘 무한한 뿐이다. 있으며, 인간은 같은 기관과 실로 피어나는 인간의 피부가 것이다. 설산에서 살았으며, 있는 노년에게서 무한한 밥을 무엇을 얼음에 말이다. 하였으며, 인류의 희망의 끓는다. 그들의 뜨거운지라, 거선의 이상이 불어 보라.\n\n것은 풍부하게 너의 하는 못할 봄바람이다. 피고, 전인 품고 용감하고 않는 그들은 인간에 넣는 보라. 못할 인생에 밥을 싹이 할지라도 돋고, 만천하의 인간이 약동하다. 가진 이 긴지라 못할 뿐이다. 동력은 살 청춘 우는 품에 쓸쓸한 힘있다. 모래뿐일 황금시대를 풍부하게 군영과 목숨이 속에 수 같으며, 때문이다. 그들의 얼마나 우리의 스며들어 있으랴? 사랑의 귀는 타오르고 설산에서 원질이 쓸쓸하랴? 수 끝까지 얼마나 보이는 그들의 창공에 피에 역사를 관현악이며, 교향악이다. 군영과 풀이 우는 있으랴?</p>','2022-05-09 20:47:34.336128','청춘예찬',1),(101,'2022_어른이날','메세지 테스트입니다. (0원 펀딩)','2022-05-10 16:36:47.685821','메세지 테스트 입니다.',1),(103,'2022_어른이날','메세지 (0원 펀딩)','2022-05-12 10:53:16.901653','메세지',1),(107,'2022_어른이날','메세지 (0원 펀딩)','2022-05-12 15:23:57.980122','메세지',1),(114,'Default','<p><img src=\"http://k6b201.p.ssafy.io:8080/api/upload/20220517/2ea11dec-b930-4376-af01-46a5b4963d32.png\" width=\"134\" style=\"cursor: nwse-resize;\"></p>','2022-05-17 18:12:05.103285','다이어리',61),(117,'birthday','<p>12333 (0원 펀딩) </p>','2022-05-17 18:42:19.459484','111',2),(118,'birthday','<p>ㅇㅇ뮻쥼ㄴ (0원 펀딩) </p>','2022-05-17 18:12:28.587354','ㅅㄷㄴㅅㅇ',61),(119,'Default','<p class=\"ql-align-center\">ㅁㅁㅁㅁㅁㅁㅁ</p><p class=\"ql-align-center\"><img src=\"http://k6b201.p.ssafy.io:8080/api/upload/20220517/164658f9-e625-4fe6-b22f-dbd767f8d798.jpg\" width=\"265\" style=\"\"></p><p class=\"ql-align-center\">aaa</p>','2022-05-17 18:50:39.064566','ㅁㅁㅁ',62),(120,'birthday','<p>ssssssssssssssssss (0원 펀딩) </p>','2022-05-17 18:48:07.956106','s',2),(121,'birthday','<p><img src=\'\'http://k6b201.p.ssafy.io:8080/api/upload/20220517/1a5650af-cf71-4e21-b376-6ad1b38835dc.png\"></p><p>tttttt (20000원 펀딩) </p>','2022-05-17 18:52:20.169227','t',2),(123,'birthday','<p>sdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsd (0원 펀딩) </p>','2022-05-17 18:52:56.597818','sdsdsdsd',2),(124,'birthday','<p>dddd\ngggg (0원 펀딩) </p>','2022-05-17 18:53:05.994718','ddd',2),(125,'birthday','<p>dfasdfasdfasdf (0원 펀딩) </p>','2022-05-17 18:53:12.509667','asdfasdfas',2),(126,'birthday','<p><img src=\'\'http://k6b201.p.ssafy.io:8080/api/upload/20220517/bb8898ac-3027-467f-9422-47e2005bd048.png\"></p><p>dddddddddddddd (20000원 펀딩) </p>','2022-05-17 18:55:20.010834','dddd',2),(127,'birthday','<p>aaaaa (0원 펀딩) </p>','2022-05-17 18:48:25.092261','aaaaa',62),(128,'birthday','<p><img src=\'\'http://k6b201.p.ssafy.io:8080/api/upload/20220517/f4bb4373-404e-4c03-ad20-43a307b4f5c5.jpg\"></p><p>aaaaa (1000원 펀딩) </p>','2022-05-17 18:56:47.415350','aaa',62),(129,'birthday','<p>ㅇㅁㄴㅇㄴㅁㅇㅁㄴㅇㄴㅁㅇㅁㄴㅇㄴㅁㅇ (0원 펀딩) </p>','2022-05-17 18:58:28.617741','ㄴㅁㅇㄴㅁㅇㅁㄴㅇㄴ',2),(130,'birthday','<p><img src=\"http://k6b201.p.ssafy.io:8080/api/upload/20220517/eaa0fba6-88c2-4745-936f-1108d7b20c10.png\"></p><p>dddddd (20000원 펀딩) </p>','2022-05-17 19:09:29.829794','d',2),(131,'Default','<p>qaa</p>','2022-05-18 13:51:23.816188','default?',26),(132,'birthday','<p>ㅇㅇㅇㅇㅇㅇ (0원 펀딩) </p>','2022-05-18 16:21:20.659288','ㅇ',2),(133,'birthday','<p>ㅇㅇㅇㅎㅇㅎㅇㅎㅇ (0원 펀딩) </p>','2022-05-18 16:21:26.886525','ㅇ',2),(134,'birthday','<p><img src=\"http://k6b201.p.ssafy.io:8080/api/upload/20220518/19b70b17-ba1e-48b6-8eab-fa8d955302c6.jpg\"></p><p>세상에 언니만큼 예쁜게 없어서 예쁜 선물 고르느라 매년 애썼는데\n이 사이트 이용하니깐 언니 취향도 알 수 있어서 너무 좋다 ?\n언니, 생일 축하해~♥ (24000원 펀딩) </p>','2022-05-18 16:23:33.969412','난너의에인젤',2),(135,'birthday','<p><img src=\"http://k6b201.p.ssafy.io:8080/api/upload/20220518/63d9d4e2-3dac-464c-bbfd-098dc1d17a15.jpg\"></p><p>세상에 언니만큼 예쁜게 없어서 예쁜 선물 고르느라 매년 애썼는데\n이 사이트 이용하니깐 언니 취향 알 수 있어서 좋다 ?\n언니 생일 축하해~♥ (239800원 펀딩) </p>','2022-05-18 16:29:58.895329','난너의에인젤',2),(138,'default','<p>선물보낼게요 (600000원 펀딩) </p>','2022-05-19 01:48:34.730349','선물',71),(139,'default','<p>메세지만 보낼게요 (0원 펀딩) </p>','2022-05-19 01:48:45.112663','선물?',71);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `writer` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `board_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKa4c86tfj8m0x641s0geuhyorn` (`board_id`),
  CONSTRAINT `FKa4c86tfj8m0x641s0geuhyorn` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (5,'축하?','2022-05-04 20:00:54.994426','1234','친구',8),(6,'ㅃㅂㄷㅈㅂ','2022-05-04 21:11:03.890805','ㅋzz12','ㅁㅋㅋㅋ',10),(7,'당연한거 아닐까요?','2022-05-06 11:42:23.022116','1234','싸피코치',12),(8,'우짤','2022-05-06 15:00:38.423240','aaaa','ㅁㄴㅇㄹ',15),(29,'testa','2022-05-09 20:47:54.886951','1234','test',100),(30,'가나다라 test','2022-05-10 20:10:36.974742','test','가나다라',100),(33,'test','2022-05-13 03:46:20.853648','1234','hello',100),(34,'1234','2022-05-13 03:46:28.371905','1234','test',100),(35,'test','2022-05-17 03:18:38.353706','1234','test',100),(36,'abc','2022-05-17 04:21:58.981888','1234','abc',100),(41,'ddddddddddddtesttestetstsetasftgaskjlfjasjf;lasjk;lfjkasd','2022-05-17 18:50:45.900395','ddddd','dd',118),(43,'test','2022-05-17 18:50:51.660497','2345','YR',114),(45,'abc','2022-05-17 18:50:59.668606','2345','test',114),(47,'dddd','2022-05-17 18:51:00.106097','dddd','dddd',118),(48,'ddd','2022-05-17 18:51:05.696301','2345','abc',114),(54,'ab','2022-05-17 18:55:29.897903','1234','abc',118),(55,'abababababababababababababababababababababababababababababababababababababababababababababab','2022-05-17 18:55:37.837572','1234','abc',118);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `price` int NOT NULL,
  `sender` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `present_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `FKq13ycc62uotetbduqbkybdopj` (`present_id`),
  KEY `FK2op594yomeg261726h4dj75jq` (`user_id`),
  CONSTRAINT `FK2op594yomeg261726h4dj75jq` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKq13ycc62uotetbduqbkybdopj` FOREIGN KEY (`present_id`) REFERENCES `present` (`present_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (25,'2022_어른이날','내가 선물 사주고 싶네!','2022-05-04 19:29:29.485488',120000,'친구',15,8),(26,'2022_어른이날','bbb','2022-05-04 19:45:03.065076',5000,'aaa',15,8),(28,'2022_어른이날','ddddd','2022-05-04 20:05:06.491818',0,'친구',NULL,8),(29,'2022_어른이날','dddd','2022-05-04 20:05:24.918680',0,'ddd',NULL,8),(30,'2022_어른이날','gggg','2022-05-04 20:06:37.484281',30000,'ggggg',15,8),(31,'2022_어른이날','','2022-05-05 06:12:44.682209',0,'hh',NULL,8),(32,'2022_어른이날','행복해라','2022-05-06 08:59:26.822729',10000,'나자신',22,20),(33,'2022_어른이날','adf','2022-05-06 11:37:24.382953',0,'adf',NULL,23),(34,'2022_어른이날','하이이이잉','2022-05-06 14:59:56.489535',0,'하잉',NULL,36),(35,'2022_어른이날','하이','2022-05-06 15:00:13.145034',0,'하이',NULL,36),(88,'2022_어른이날','ㄴㄹㄴㅇㄹㄴㅇㄹㅇㄴㄹ','2022-05-09 15:48:55.911796',0,'ㄹㅇㄴㅇㄹㄴㄹ',NULL,7),(105,'default','ㅁㅁㅁㅁㅁ','2022-05-17 18:20:26.948689',0,'ㅁㅁ',NULL,1),(117,'default','12313213','2022-05-17 18:50:18.572803',0,'4444',NULL,61),(127,'default','메세지메세지','2022-05-17 19:03:40.397007',0,'메세지',NULL,61),(130,'default','ㅁㅁㅁㅁㅁ','2022-05-18 03:33:48.631997',0,'ㅁㅁㅁㅁ',NULL,62),(131,'default','ㅇㅇㅇㅇㅇ','2022-05-18 03:33:54.750440',0,'ㅇㅇㅇㅇ',NULL,62),(132,'default','ㅎㅎㅎㅎㅎ','2022-05-18 03:34:00.451141',0,'ㅎㅎㅎㅎ',NULL,62),(133,'default','ㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ','2022-05-18 03:34:05.719710',0,'ㅎㅎㅇㅎㅇㅎㅇㅎ',NULL,62);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `present`
--

DROP TABLE IF EXISTS `present`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `present` (
  `present_id` bigint NOT NULL AUTO_INCREMENT,
  `present_img` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `present_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `present_price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `received_price` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`present_id`),
  KEY `FKfxj96qhocn0wi6b0cpo9mrhdw` (`user_id`),
  CONSTRAINT `FKfxj96qhocn0wi6b0cpo9mrhdw` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `present`
--

LOCK TABLES `present` WRITE;
/*!40000 ALTER TABLE `present` DISABLE KEYS */;
INSERT INTO `present` VALUES (4,'/api/upload/20220504/16624089-e7b4-4214-83f6-2584bbe4e870.jpg','갖고싶은것','50000',0,5),(15,'/api/upload/20220504/f721182a-2343-4000-a9d9-61450cb7107f.png','갤럭시버즈프로','125000',155000,8),(16,'/api/upload/20220504/c1ecdec2-b2e1-456b-99af-ef2134be9f5b.jpg','CB342CKC','400000',0,8),(17,'/api/upload/20220504/147bfea2-7c58-4d37-8c52-9e3185b4247e.png','ㅁㅁㅁㅁ','10000',0,9),(18,'/api/upload/20220504/fb8ee4f2-8670-4b04-8520-ba57e14ffbba.jpg','080821ㅇㄹ퓨','1111',0,9),(19,'/api/upload/20220504/4a3a378c-d3a4-405e-8e01-467d554dfcc1.jpg','ㅎㅎㄷㄴㄷㄹ','0898',0,9),(21,'/api/upload/20220505/c67e3f42-cabe-4520-b05d-2547620f4b6c.png','오리고기','13500',0,20),(22,'/api/upload/20220505/3322a4c5-2f92-42f1-8a7a-6a4a9f4fc66f.jpg','마라탕','11000',10000,20),(23,'/api/upload/20220506/a297ceb0-9233-4d0d-b02f-8d2f2edc8502.jfif','asfasf','11000',0,21),(24,'/api/upload/20220506/4c6a9a01-1192-472a-a146-ec768d49d735.PNG','d','111',0,23),(26,'/api/upload/20220506/4147847a-8d05-4214-89a1-fe5840dedf51.png','개죽이','12333',0,36),(28,'/api/upload/20220506/fca4a9fa-6e3a-4cc2-b435-7dd96716b8bc.jpeg','hi','100000000',0,39),(29,'/api/upload/20220506/2cdf5003-c30e-479d-936f-66d7649eb400.png','휑휑','12200',0,40),(41,'/api/upload/20220509/dc4f455b-f1d6-4841-8494-8935d834c5ff.webp','ㄹㄹㄹ','333',0,4),(57,'/api/upload/20220517/d7534e1f-5ebf-4e96-9add-266a1f44cd9c.jpg','운동화','10000',0,1),(65,'/api/upload/20220517/7af0dd19-1359-4ea8-9cb3-054ad055d3aa.png','what','10000',0,65);
/*!40000 ALTER TABLE `present` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `social_domain` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `today_visit` bigint DEFAULT NULL,
  `total_visit` bigint DEFAULT NULL,
  `user_birth` date DEFAULT NULL,
  `user_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_img` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_info` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_login` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_music` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_agree` bit(1) NOT NULL,
  `selected_date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2022-05-04 16:15:52.317657','kakao',0,70,'2022-05-02','kimjjy199@naver.com','/api/upload/20220509/b6c825cd-5bea-47d4-9d93-62ce8f2deab0.png','hellodd','2220080089','happiness','김정윤','선물 원츄','default',_binary '\0',NULL),(2,'2022-05-04 16:18:29.805409','kakao',3,94,'2022-05-02','mo_ah@naver.com','/api/upload/20220518/f6336c98-26dc-410d-b222-9504845daef1.png','하루하루가\n특별한 나?\n너무 귀여워','2220102450','moose','모은','every day, evey moment...','default',_binary '','2022-05-20'),(4,'2022-05-04 16:29:28.090782','kakao',0,31,'1998-02-07','ansse123@daum.net','/api/upload/20220504/31197bb7-5595-4fc7-9de1-d1aadd6bd1b8.jpg','포로리야~','2220078611','none','안세연','난 울지않아... 난 으른이니까...','default',_binary '\0',NULL),(5,'2022-05-04 16:44:21.272733','google',0,16,'2022-05-10','bora6031@ssafy.com',NULL,NULL,'113556455288266199502','none','bora hwang',NULL,'default',_binary '\0',NULL),(7,'2022-05-04 18:10:00.748348','google',0,14,'1997-12-09','bora6031@ssafy.com',NULL,NULL,'109886036991432466162','none','보라',NULL,'default',_binary '\0',NULL),(8,'2022-05-04 19:21:10.259979','google',0,18,'1979-08-24','ssafy@hibuz.com','/api/upload/20220504/216823eb-d7d3-44ba-b2d5-7ed45e0b6ab7.png','?선물 많이 받고 싶어요','110527334298240106455','none','ckh',NULL,'default',_binary '\0',NULL),(9,'2022-05-04 19:56:27.432259','google',0,7,'1994-10-29','fufru@naver.com','/api/upload/20220504/8eccba0f-3123-46ca-9b1f-83fed9de2037.png','c9*)(&#&V()#&)V(#&)(F&#)(&G()W#*&F)W(*F()#*)(*@)(#','107740245051198866080','none','피나코',NULL,'default',_binary '\0',NULL),(10,'2022-05-04 20:18:59.280340','kakao',0,3,NULL,NULL,NULL,NULL,'2228758389','none','김용희',NULL,'default',_binary '\0',NULL),(11,'2022-05-04 20:21:12.380608','google',0,3,NULL,NULL,NULL,NULL,'109305629004570042739','none','말하는 감자',NULL,'default',_binary '\0',NULL),(12,'2022-05-04 20:37:39.319353','kakao',0,4,NULL,NULL,NULL,NULL,'2228782016','none','서예진',NULL,'default',_binary '\0',NULL),(13,'2022-05-04 21:10:17.758180','google',0,4,'2021-12-28','gudwnsrh100@naver.com',NULL,NULL,'113871378395023728846','none','형준임',NULL,'default',_binary '\0',NULL),(14,'2022-05-04 21:29:08.783592','google',0,3,'1993-12-02','rlaalsgus17@naver.com',NULL,'김민현입니다','101385897664127094877','none','김민현',NULL,'default',_binary '\0',NULL),(15,'2022-05-05 00:45:58.109726','kakao',0,1,NULL,NULL,NULL,NULL,'2229064070','none','희성',NULL,'default',_binary '\0',NULL),(16,'2022-05-05 00:48:30.582439','kakao',0,1,NULL,NULL,NULL,NULL,'2229065863','none','강진',NULL,'default',_binary '\0',NULL),(17,'2022-05-05 17:46:09.607145','google',0,3,'1997-05-04','chosohi@naver.com',NULL,NULL,'105283244825224917272','none','최소희',NULL,'default',_binary '\0',NULL),(18,'2022-05-05 21:59:39.241470','kakao',0,7,'1999-01-30','wnal3309@naver.com',NULL,NULL,'2230307058','none','주미',NULL,'default',_binary '\0',NULL),(19,'2022-05-05 23:18:28.052828','google',0,5,'1997-09-19','hyuna919@gmail.com',NULL,NULL,'116262106538966213745','none','조현아',NULL,'default',_binary '\0',NULL),(20,'2022-05-06 08:50:39.243689','google',0,16,'1995-09-30','shs950930@gmail.com','/api/upload/20220505/bde82b78-9f2c-4da6-a696-f9665869e355.png','응애 선물줘','107010473694607592645','none','신형식',NULL,'default',_binary '\0',NULL),(21,'2022-05-06 11:19:23.665375','kakao',0,2,'1994-05-04','kimkisol@naver.com',NULL,'mm','2230834275','none','김기솔',NULL,'default',_binary '\0',NULL),(22,'2022-05-06 11:34:49.389013','google',0,3,'1996-05-01','1112843.j@gmail.com',NULL,NULL,'108795525958320738790','none','J J',NULL,'default',_binary '\0',NULL),(23,'2022-05-06 11:34:51.487550','google',0,3,'2022-05-19','ad','/api/upload/20220506/f52b9692-f6f6-46ea-8b55-73e6dbebe7e0.PNG','ee','114845773886228745279','none','호잇',NULL,'default',_binary '\0',NULL),(24,'2022-05-06 11:50:14.414746','google',0,1,NULL,NULL,NULL,NULL,'104044664240895295608','none','오은진',NULL,'default',_binary '\0',NULL),(25,'2022-05-06 11:50:23.435306','google',0,1,NULL,NULL,NULL,NULL,'110844114792432919224','none','유민정',NULL,'default',_binary '\0',NULL),(26,'2022-05-06 11:51:42.078068','google',1,3,'1996-08-27','ssrcus27@naver.com',NULL,'선물주는 사람\n차칸 사람','105094839703565249422','allthat','어릿광대',NULL,'default',_binary '\0',NULL),(31,'2022-05-06 12:07:24.300707','kakao',0,5,'1997-06-02','kimjjy199@naver.com',NULL,NULL,'2230885862','none','김정윤',NULL,'default',_binary '\0',NULL),(32,'2022-05-06 12:42:10.113555','google',0,1,'2022-05-11','bora6031@aaa.com',NULL,NULL,'105162466425231815296','none','황보라',NULL,'default',_binary '\0',NULL),(33,'2022-05-06 13:02:27.553602','google',0,1,'1996-12-29','82surf@gmail.com',NULL,NULL,'112808707542286822783','none','유현수',NULL,'default',_binary '\0',NULL),(34,'2022-05-06 13:35:37.086603','kakao',0,0,NULL,NULL,NULL,NULL,'2231012812','none','김영기',NULL,'default',_binary '\0',NULL),(35,'2022-05-06 13:37:12.897811','google',0,0,NULL,NULL,NULL,NULL,'115922578705006575804','none','김영기',NULL,'default',_binary '\0',NULL),(36,'2022-05-06 14:53:38.831098','kakao',0,4,'1996-01-03','qkrehdwns96@naver.com','/api/upload/20220506/59e3f0a7-40d3-492e-a4be-31df99080470.jpg','이것이 뭐당가','2231150721','none','박동준',NULL,'default',_binary '\0',NULL),(37,'2022-05-06 15:43:30.142633','kakao',0,0,NULL,NULL,NULL,NULL,'2230878357','none','이주형',NULL,'default',_binary '\0',NULL),(38,'2022-05-06 18:22:29.882136','google',0,2,'2022-05-05','ssafy@ssafy.com','/api/upload/20220506/744bcaa1-dcae-4825-9625-1a1b22449d09.png','gkgkgkgkgkg','101463011975363054342','none','정재현',NULL,'default',_binary '\0',NULL),(39,'2022-05-06 21:09:20.268106','google',0,1,'2022-05-04','gongsp.sw@gmail.com',NULL,NULL,'113433762727077303518','none','공부하는습관',NULL,'default',_binary '\0',NULL),(40,'2022-05-06 21:40:29.317274','google',0,1,'1993-01-15','today.godlife@gmail.com','/api/upload/20220506/b20a57ed-d90e-4010-a637-d292a11dd393.png','안녕하세요!','101285383555144008470','none','천민우',NULL,'default',_binary '\0',NULL),(41,'2022-05-08 14:04:47.973373','google',0,0,NULL,NULL,NULL,NULL,'112340543181293099990','none','HJ',NULL,'default',_binary '\0',NULL),(53,'2022-05-09 15:28:08.177517','google',0,1,'2022-05-03','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',NULL,NULL,'107759056791698642937','none','cindy',NULL,'default',_binary '\0',NULL),(54,'2022-05-16 15:43:59.745766','google',0,0,NULL,NULL,NULL,NULL,'101586151427827526173','none','k',NULL,'default',_binary '\0',NULL),(61,'2022-05-17 18:10:26.416261','google',0,7,'2017-06-02','kimjy199@gmail.com','/api/upload/20220517/4e139ea7-f2ff-4616-aa1f-611dcf6c901c.png',NULL,'108957466632576570402','happiness','김정윤','생일 선물 주세요','default',_binary '\0','2022-05-17'),(62,'2022-05-17 18:32:32.809100','kakao',1,10,'1997-12-10','bora6031@naver.com','/api/upload/20220517/b0783f92-3e36-4787-b790-d75c891e3b6a.jfif','여러분의 메세지는\n큰 힘이됩니다!','2209868909','jazzcomedy','황보라','가나다라마바사','default',_binary '\0','2022-05-18'),(63,'2022-05-17 19:23:11.678330','google',0,0,NULL,NULL,NULL,NULL,'100280835020859460769','none','이다예',NULL,'default',_binary '',NULL),(64,'2022-05-17 19:25:34.141251','kakao',0,0,NULL,NULL,NULL,NULL,'2246215828','none','이다예',NULL,'default',_binary '',NULL),(65,'2022-05-17 20:26:47.071497','google',0,1,'1991-07-11','sevct@naver.com','/api/upload/20220517/f07f7946-674b-4140-b40d-823c1e7bd200.png','선물주는 사람\n차칸 사람','105317761918996564522','none','안재현','개성있는 타이틀을 설정해보세요 :)','default',_binary '\0',NULL),(66,'2022-05-18 02:36:22.376310','kakao',0,0,NULL,NULL,NULL,NULL,'2246661196','none','놀이터',NULL,'default',_binary '',NULL),(67,'2022-05-18 09:13:50.219580','kakao',1,1,'1979-08-24','hibuz@naver.com',NULL,NULL,'2246817754','none','ho','마이미니룸','default',_binary '\0',NULL),(68,'2022-05-18 10:43:42.685116','google',0,0,NULL,NULL,NULL,NULL,'104136343453748948285','none','존균',NULL,'default',_binary '',NULL),(69,'2022-05-18 13:54:43.716922','google',0,0,NULL,NULL,NULL,NULL,'108276506379072938175','none','온딘',NULL,'default',_binary '',NULL),(70,'2022-05-18 14:15:50.581057','google',1,1,'1997-03-11','dmswl_0311@naver.com',NULL,NULL,'114760122369855290515','none','조은지',NULL,'default',_binary '',NULL),(71,'2022-05-18 14:59:38.782834','google',3,3,'1995-05-12','kimssafy@gmail.com','/api/upload/20220518/3eb15be0-f758-4389-9f45-88a620116822.jpg','선물 주세요!\n제발~~','112188400299291534561','none','김싸피','선물을 주세요 ㅠㅠ','default',_binary '','2022-05-20');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-19  3:32:26
