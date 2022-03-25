-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: watashi_house
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
-- Table structure for table `article`
--

use heroku_433d5faa3f64939;

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `images` varchar(500) NOT NULL,
  `couleur` varchar(30) NOT NULL,
  `prix` int NOT NULL,
  `nb_avis` int NOT NULL,
  `note` int NOT NULL,
  `stock` int NOT NULL,
  `id_collection_article` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Article_Collection_FK` (`id_collection_article`),
  CONSTRAINT `Article_Collection_FK` FOREIGN KEY (`id_collection_article`) REFERENCES `collection` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `article_categorie`
--

DROP TABLE IF EXISTS `article_categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `article_categorie` (
  `id_article` int NOT NULL,
  `id_categorie` int NOT NULL,
  PRIMARY KEY (`id_article`,`id_categorie`),
  KEY `Article_Categorie_Categorie0_FK` (`id_categorie`),
  CONSTRAINT `Article_Categorie_Article_FK` FOREIGN KEY (`id_article`) REFERENCES `article` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Article_Categorie_Categorie0_FK` FOREIGN KEY (`id_categorie`) REFERENCES `categorie` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `article_commande`
--

DROP TABLE IF EXISTS `article_commande`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `article_commande` (
  `id_article` int NOT NULL,
  `id_commande` int NOT NULL,
  PRIMARY KEY (`id_article`,`id_commande`),
  KEY `Article_Commande_Commande0_FK` (`id_commande`),
  CONSTRAINT `Article_Commande_Article_FK` FOREIGN KEY (`id_article`) REFERENCES `article` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Article_Commande_Commande0_FK` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `carte_de_paiement`
--

DROP TABLE IF EXISTS `carte_de_paiement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `carte_de_paiement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(16) NOT NULL,
  `cvc` int NOT NULL,
  `annee_expiration` int NOT NULL,
  `mois_expiration` int NOT NULL,
  `id_utilisateur_carte` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Carte_de_paiement_Utilisateur_FK` (`id_utilisateur_carte`),
  CONSTRAINT `Carte_de_paiement_Utilisateur_FK` FOREIGN KEY (`id_utilisateur_carte`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `categorie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `collection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `commande` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(10) NOT NULL,
  `date_achat` varchar(30) NOT NULL,
  `prix_tot` int NOT NULL,
  `id_utilisateur_commande` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Commandes_Utilisateur_FK` (`id_utilisateur_commande`),
  CONSTRAINT `Commandes_Utilisateur_FK` FOREIGN KEY (`id_utilisateur_commande`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `civilite` varchar(20) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mdp` varchar(50) NOT NULL,
  `tel` varchar(10) NOT NULL,
  `adresse_livraison` varchar(200) NOT NULL,
  `adresse_facturation` varchar(200) NOT NULL,
  `pays` varchar(20) NOT NULL,
  `type_user` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
