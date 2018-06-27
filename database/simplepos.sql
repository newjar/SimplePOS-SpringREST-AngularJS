# Host: localhost  (Version 5.5.5-10.1.21-MariaDB)
# Date: 2018-06-27 17:57:52
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "barang"
#

DROP TABLE IF EXISTS `barang`;
CREATE TABLE `barang` (
  `kode` varchar(15) NOT NULL DEFAULT '',
  `nama` varchar(50) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `satuan` varchar(10) DEFAULT NULL,
  `harga_beli` int(11) DEFAULT NULL,
  `harga_jual` int(11) DEFAULT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "barang"
#

INSERT INTO `barang` VALUES ('KB001','Liquid',2,'buah',10000,12000),('KB002','Vaporizer',15,'unit',5000,7500),('KB003','MOD',12,'unit',20000,22000),('KB004','TOOL SET',3,'box',7500,10000);

#
# Structure for table "customer"
#

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `kode` varchar(15) NOT NULL DEFAULT '',
  `nama` varchar(25) DEFAULT NULL,
  `alamat` varchar(150) DEFAULT NULL,
  `no_telp` varchar(12) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "customer"
#

INSERT INTO `customer` VALUES ('C01','Fajar','Jl. Ciledug Timur','085215334219','qwew@gmail.com'),('C02','Luna','Jl. Cipete Utara','085123352312','lunamagic@gmail.com'),('C03','Meepo','Jl. Ciledug Utara','089658456666','meepos@gmail.com');

#
# Structure for table "penjualan"
#

DROP TABLE IF EXISTS `penjualan`;
CREATE TABLE `penjualan` (
  `kode` varchar(25) NOT NULL DEFAULT '',
  `kode_customer` varchar(15) DEFAULT NULL,
  `kode_barang` varchar(15) DEFAULT NULL,
  `tgl_penjualan` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga_barang` int(11) DEFAULT NULL,
  `sub_total_harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`kode`),
  KEY `kode_customer.ibfk1` (`kode_customer`),
  KEY `kode_barang.ibfk3` (`kode_barang`),
  CONSTRAINT `kode_barang.ibfk3` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `kode_customer.ibfk1` FOREIGN KEY (`kode_customer`) REFERENCES `customer` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "penjualan"
#

INSERT INTO `penjualan` VALUES ('NP20180602014239','C01','KB002','2018-06-06',2,7500,15000),('NP20180605002006','C02','KB002','2018-06-04',2,7500,15000),('NP20180605005850','C03','KB003','2018-06-05',2,22000,22000);

#
# Structure for table "supplier"
#

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `kode` varchar(15) NOT NULL DEFAULT '',
  `nama` varchar(25) DEFAULT NULL,
  `alamat` varchar(150) DEFAULT NULL,
  `no_telp` varchar(12) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "supplier"
#

INSERT INTO `supplier` VALUES ('S01','RCKS JUICE','Jl. Bintaro Sektor 3','085777775551','rcks@gmail.com'),('S02','HERO 57','Jl. Ciputat Raya','085723232321','hero57@gmail.com'),('S03','GeekVape','Jl. Duren 3 Utara','085696956854','geekvape@gmail.com');

#
# Structure for table "pembelian"
#

DROP TABLE IF EXISTS `pembelian`;
CREATE TABLE `pembelian` (
  `kode` varchar(25) NOT NULL DEFAULT '',
  `kode_supplier` varchar(15) DEFAULT NULL,
  `kode_barang` varchar(15) DEFAULT NULL,
  `tgl_pembelian` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga_barang` int(11) DEFAULT NULL,
  `sub_total_harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`kode`),
  KEY `kode_supplier.ibfk1` (`kode_supplier`),
  KEY `kode_barang.ibfk2` (`kode_barang`),
  CONSTRAINT `kode_barang.ibfk2` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `kode_supplier.ibfk1` FOREIGN KEY (`kode_supplier`) REFERENCES `supplier` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

#
# Data for table "pembelian"
#

INSERT INTO `pembelian` VALUES ('NB20180605230812','S01','KB001','2018-05-27',1,10000,10000),('NB20180605234335','S02','KB001','2018-06-01',12,12000,120000),('NB20180606144505','S02','KB001','2018-06-05',10,12000,120000);

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(15) NOT NULL DEFAULT '',
  `password` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "user"
#

