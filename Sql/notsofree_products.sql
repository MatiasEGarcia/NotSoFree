CREATE DATABASE  IF NOT EXISTS `notsofree` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `notsofree`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: notsofree
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id_product` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `stock` int NOT NULL,
  `brand` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `image` mediumblob,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Wine - Ej Gallo Sierra Valley',37,'PHILIPS',22676,NULL),(2,'Star Fruit',16,'ADIDAS',27877,NULL),(3,'Compound - Rum',3,'PHILIPS',46101,NULL),(4,'Pastry - Key Limepoppy Seed Tea',5,'SONY',38166,NULL),(5,'Halibut - Steaks',23,'SONY',21378,NULL),(6,'Oats Large Flake',6,'SAMSUNG',6335,NULL),(7,'Trout - Rainbow, Fresh',36,'PHILIPS',43272,NULL),(8,'Paper Towel Touchless',49,'TOPPER',32009,NULL),(9,'Chicken - Base',13,'SAMSUNG',33033,NULL),(10,'Marjoram - Dried, Rubbed',29,'ADIDAS',17844,NULL),(11,'Potatoes - Instant, Mashed',28,'SAMSUNG',32915,NULL),(12,'Beef - Tenderloin Tails',25,'HP',14591,NULL),(13,'Lamb - Pieces, Diced',50,'HP',8836,NULL),(14,'Pepper - White, Ground',1,'GENIUS',49197,NULL),(15,'Barley - Pearl',1,'TOPPER',37602,NULL),(16,'Lamb - Shoulder, Boneless',20,'PHILIPS',36739,NULL),(17,'Beer - Camerons Cream Ale',23,'ADIDAS',17867,NULL),(18,'Mustard - Seed',16,'SONY',31496,NULL),(19,'Nantucket - Pomegranate Pear',50,'SONY',42911,NULL),(20,'Juice - Apple, 341 Ml',5,'SONY',27067,NULL),(21,'Cake - Box Window 10x10x2.5',29,'SAMSUNG',41769,NULL),(22,'Truffle Shells - White Chocolate',17,'TOPPER',25672,NULL),(23,'Fond - Chocolate',8,'SAMSUNG',18274,NULL),(24,'Rice Wine - Aji Mirin',10,'TOPPER',36467,NULL),(25,'Ocean Spray - Ruby Red',6,'SAMSUNG',10162,NULL),(26,'Wine - Saint - Bris 2002, Sauv',44,'TOPPER',40304,NULL),(27,'Pasta - Spaghetti, Dry',32,'SAMSUNG',36576,NULL),(28,'Nantucket Apple Juice',8,'SONY',46681,NULL),(29,'Wine - Black Tower Qr',9,'HP',17187,NULL),(30,'Soup - Knorr, Ministrone',35,'NIKE',19573,NULL),(31,'Aromat Spice / Seasoning',12,'SONY',46726,NULL),(32,'Juice - V8, Tomato',42,'ADIDAS',49411,NULL),(33,'Kellogs All Bran Bars',20,'SONY',44916,NULL),(34,'Oil - Sesame',18,'GENIUS',13929,NULL),(35,'Chicken - Wings, Tip Off',20,'PHILIPS',36908,NULL),(36,'Spinach - Spinach Leaf',50,'ADIDAS',44805,NULL),(37,'Basil - Primerba, Paste',5,'HP',22978,NULL),(38,'Cheese - Cream Cheese',34,'TOPPER',24965,NULL),(39,'Shrimp - 150 - 250',23,'ADIDAS',7176,NULL),(40,'Cheese - Wine',17,'NIKE',18845,NULL),(41,'Fish - Atlantic Salmon, Cold',37,'TOPPER',2737,NULL),(42,'Wine - Cave Springs Dry Riesling',12,'PHILIPS',19968,NULL),(43,'Cumin - Ground',33,'HP',25196,NULL),(44,'Transfer Sheets',25,'GENIUS',15386,NULL),(45,'Ezy Change Mophandle',6,'PHILIPS',11876,NULL),(46,'Cake - Cheese Cake 9 Inch',22,'HP',47871,NULL),(47,'Olive - Spread Tapenade',14,'SONY',6130,NULL),(48,'Nougat - Paste / Cream',28,'GENIUS',9351,NULL),(49,'Pepper - Red, Finger Hot',18,'SONY',5134,NULL),(50,'Lobak',46,'ADIDAS',40456,NULL),(51,'Cherries - Maraschino,jar',25,'TOPPER',41725,NULL),(52,'Milk - Homo',7,'HP',34292,NULL),(53,'Bread - Corn Muffaletta',15,'TOPPER',16101,NULL),(54,'Beef - Bones, Cut - Up',31,'NIKE',21438,NULL),(55,'Shrimp - Baby, Warm Water',46,'SAMSUNG',10802,NULL),(56,'Pepper - White, Whole',22,'NIKE',42607,NULL),(57,'Apple - Custard',23,'SAMSUNG',28943,NULL),(58,'Frangelico',42,'PHILIPS',42554,NULL),(59,'Water - Perrier',22,'TOPPER',24821,NULL),(60,'Longos - Grilled Veg Sandwiches',13,'PHILIPS',39168,NULL),(61,'Beef - Salted',50,'NIKE',24944,NULL),(62,'Swiss Chard',36,'HP',45940,NULL),(63,'Soup Campbells',33,'ADIDAS',32039,NULL),(64,'Apples - Sliced / Wedge',6,'GENIUS',34700,NULL),(65,'Tart - Pecan Butter Squares',23,'ADIDAS',10569,NULL),(66,'Ice - Clear, 300 Lb For Carving',14,'PHILIPS',32662,NULL),(67,'Goat - Whole Cut',31,'HP',33461,NULL),(68,'Food Colouring - Red',11,'NIKE',34644,NULL),(69,'Turkey - Whole, Fresh',19,'GENIUS',6130,NULL),(70,'Chips - Assorted',2,'SAMSUNG',11337,NULL),(71,'Soho Lychee Liqueur',34,'HP',27874,NULL),(72,'Calypso - Strawberry Lemonade',21,'GENIUS',21262,NULL),(73,'Puree - Raspberry',11,'SAMSUNG',16674,NULL),(74,'Wine - Sake',10,'ADIDAS',29717,NULL),(75,'Pepsi, 355 Ml',18,'GENIUS',9373,NULL),(76,'Foil - 4oz Custard Cup',48,'GENIUS',48734,NULL),(77,'Bread - 10 Grain Parisian',11,'SONY',44390,NULL),(78,'Pail With Metal Handle 16l White',17,'SONY',35346,NULL),(79,'Bar Special K',15,'SAMSUNG',2681,NULL),(80,'Wine - Blue Nun Qualitatswein',4,'SONY',35775,NULL),(81,'Roe - Lump Fish, Red',8,'HP',15959,NULL),(82,'Food Colouring - Orange',36,'HP',10309,NULL),(83,'Peach - Fresh',50,'SONY',28268,NULL),(84,'Wine - Mondavi Coastal Private',3,'NIKE',34220,NULL),(85,'Pheasants - Whole',1,'PHILIPS',13600,NULL),(86,'Appetizer - Seafood Assortment',22,'NIKE',49215,NULL),(87,'Cheese - Wine',1,'PHILIPS',18185,NULL),(88,'Soy Protein',27,'GENIUS',29079,NULL),(89,'Spoon - Soup, Plastic',12,'NIKE',3759,NULL),(90,'Cheese - Manchego, Spanish',22,'PHILIPS',5119,NULL),(91,'White Fish - Filets',24,'PHILIPS',36900,NULL),(92,'Sauce - Marinara',28,'SONY',14262,NULL),(93,'Lemon Grass',42,'HP',31016,NULL),(94,'Sauce - Ranch Dressing',1,'GENIUS',43715,NULL),(95,'Beans - Kidney White',48,'ADIDAS',29455,NULL),(96,'Lady Fingers',48,'HP',4326,NULL),(97,'Onions Granulated',22,'NIKE',34635,NULL),(98,'Shrimp - Prawn',31,'GENIUS',40711,NULL),(99,'Tea - Earl Grey',24,'NIKE',48120,NULL),(100,'Marjoram - Fresh',44,'SONY',43537,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-18 11:26:27
