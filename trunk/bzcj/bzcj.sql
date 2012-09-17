-- phpMyAdmin SQL Dump
-- version 3.3.3
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2010 年 11 月 15 日 10:13
-- 服务器版本: 5.1.38
-- PHP 版本: 5.2.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `bzcj`
--
CREATE DATABASE `bzcj` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bzcj`;

-- --------------------------------------------------------

--
-- 表的结构 `banmian`
--

CREATE TABLE IF NOT EXISTS `banmian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL COMMENT '内容',
  `url` varchar(255) NOT NULL,
  `lx` varchar(255) NOT NULL COMMENT '版面类别',
  `bmdate` date NOT NULL COMMENT '日期',
  `getdone` tinyint(4) DEFAULT '0' COMMENT '是否采集完成',
  `createdone` tinyint(4) DEFAULT '0' COMMENT '是否生成完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版面表' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `banmian`
--


-- --------------------------------------------------------

--
-- 表的结构 `cjzt`
--

CREATE TABLE IF NOT EXISTS `cjzt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cjrq` date NOT NULL,
  `indexcjzt` tinyint(4) DEFAULT '0',
  `bmcjzt` tinyint(4) DEFAULT '0' COMMENT '版面是否采集完',
  `cjlx` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

--
-- 转存表中的数据 `cjzt`
--

INSERT INTO `cjzt` (`id`, `cjrq`, `indexcjzt`, `bmcjzt`, `cjlx`) VALUES
(1, '2010-11-08', 1, 1, 'fzwb'),
(3, '2010-11-09', 1, 1, 'fzwb'),
(7, '2010-11-10', 1, 1, 'fzwb'),
(13, '2010-11-10', 1, 1, 'mrjj'),
(15, '2010-11-11', 1, 1, 'mrjj'),
(17, '2010-11-11', 1, 1, 'fzwb'),
(19, '2010-11-12', 1, 1, 'mrjj'),
(21, '2010-11-12', 1, 1, 'fzwb'),
(23, '2010-11-13', 1, 1, 'fzwb'),
(25, '2010-11-14', 1, 1, 'mrjj');

-- --------------------------------------------------------

--
-- 表的结构 `detaillist`
--

CREATE TABLE IF NOT EXISTS `detaillist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `newsid` int(11) NOT NULL,
  `content` text,
  `imgurl` varchar(255) DEFAULT NULL,
  `createdone` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `detaillist`
--


-- --------------------------------------------------------

--
-- 表的结构 `newslist`
--

CREATE TABLE IF NOT EXISTS `newslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bmid` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `getdone` tinyint(4) DEFAULT '0',
  `createdone` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `newslist`
--

