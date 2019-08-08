-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 17, 2019 at 05:58 PM
-- Server version: 10.3.15-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laundry`
--

-- --------------------------------------------------------

--
-- Table structure for table `detail_penerimaan_cucian`
--

CREATE TABLE `detail_penerimaan_cucian` (
  `id_order` int(5) NOT NULL,
  `id_cucian` int(3) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `subtotal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detail_penerimaan_cucian`
--

INSERT INTO `detail_penerimaan_cucian` (`id_order`, `id_cucian`, `jumlah`, `subtotal`) VALUES
(10, 3, 3, 15000),
(10, 4, 2, 40000);

-- --------------------------------------------------------

--
-- Table structure for table `jenis_cucian`
--

CREATE TABLE `jenis_cucian` (
  `id_cucian` int(3) NOT NULL,
  `nama_cucian` varchar(55) NOT NULL,
  `satuan` varchar(10) NOT NULL,
  `biaya_cucian` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jenis_cucian`
--

INSERT INTO `jenis_cucian` (`id_cucian`, `nama_cucian`, `satuan`, `biaya_cucian`) VALUES
(1, 'Cuci dan Setrika', 'kiloan', 6000),
(2, 'Karpet', 'pcs', 35000),
(3, 'Cucian', 'Kiloan', 5000),
(4, 'Selimut Tipis', 'pcs', 20000),
(6, 'Handuk Tebal', 'pcs', 22000);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(5) NOT NULL,
  `kode_pelanggan` varchar(8) NOT NULL,
  `nama_pelanggan` varchar(25) NOT NULL,
  `alamat` varchar(35) NOT NULL,
  `no_telepon_pelanggan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `kode_pelanggan`, `nama_pelanggan`, `alamat`, `no_telepon_pelanggan`) VALUES
(1, 'P1907001', 'Fatimah', 'Jalan Beji no.3', '081978281812'),
(2, 'P1907002', 'Qudsiah', 'Jalan Juanda no 23', '087812328932'),
(3, 'P1907003', 'Aini', 'Jalan Sawangan gang belong no 3', '081978393232'),
(8, 'P1907004', 'Lia', 'Jalan Kampung Sawah', '081932114321');

-- --------------------------------------------------------

--
-- Table structure for table `penerimaan_cucian`
--

CREATE TABLE `penerimaan_cucian` (
  `id_order` int(5) NOT NULL,
  `no_order` varchar(10) NOT NULL,
  `id_pelanggan` int(5) NOT NULL,
  `tgl_penerimaan` date NOT NULL,
  `tgl_pengambilan` date DEFAULT NULL,
  `total` double NOT NULL,
  `status_cucian` enum('belum','sudah','') NOT NULL,
  `estimasi_pengerjaan` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penerimaan_cucian`
--

INSERT INTO `penerimaan_cucian` (`id_order`, `no_order`, `id_pelanggan`, `tgl_penerimaan`, `tgl_pengambilan`, `total`, `status_cucian`, `estimasi_pengerjaan`) VALUES
(1, 'O-19070001', 1, '2019-07-14', NULL, 90000, 'belum', 3),
(2, 'O-19070002', 1, '2019-07-15', NULL, 190000, 'belum', 4),
(6, 'O-19070003', 2, '2019-07-14', NULL, 80000, 'belum', 4),
(7, 'O-19070004', 2, '2019-07-15', NULL, 20000, 'belum', 2),
(8, 'O-19070005', 8, '2019-07-15', '2019-07-24', 60000, 'belum', 3),
(9, 'O-19070006', 1, '2019-07-15', '2019-07-19', 15000, 'belum', 3),
(10, 'O-19070007', 2, '2019-07-15', '2019-07-17', 55000, 'belum', 4);

-- --------------------------------------------------------

--
-- Table structure for table `sementara`
--

CREATE TABLE `sementara` (
  `id_cucian` int(3) NOT NULL,
  `nama_cucian` varchar(55) NOT NULL,
  `satuan` varchar(10) NOT NULL,
  `harga` double NOT NULL,
  `jumlah` int(3) NOT NULL,
  `subtotal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(5) NOT NULL,
  `no_transaksi` varchar(15) NOT NULL,
  `id_order` int(5) NOT NULL,
  `status` varchar(10) NOT NULL,
  `tanggal_transaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `no_transaksi`, `id_order`, `status`, `tanggal_transaksi`) VALUES
(1, 'T-19070001', 10, 'Lunas', '2019-07-17'),
(2, 'T-19070002', 10, 'Lunas', '2019-07-17'),
(3, 'T-19070003', 10, 'Lunas', '2019-07-17'),
(4, 'T-19070004', 10, 'Lunas', '2019-07-17'),
(5, 'T-19070005', 10, 'Lunas', '2019-07-17');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(5) NOT NULL,
  `nama_user` varchar(25) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(30) NOT NULL,
  `level` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama_user`, `username`, `password`, `level`) VALUES
(1, 'Sefti', 'qwe', 'wer', 'admin'),
(2, 'Fita', 'asd', 'sdf', 'kasir'),
(6, 'aasa', 'zxc', 'xcv', 'Kasir');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_penerimaan_cucian`
--
ALTER TABLE `detail_penerimaan_cucian`
  ADD KEY `id_order` (`id_order`),
  ADD KEY `id_cucian` (`id_cucian`);

--
-- Indexes for table `jenis_cucian`
--
ALTER TABLE `jenis_cucian`
  ADD PRIMARY KEY (`id_cucian`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `penerimaan_cucian`
--
ALTER TABLE `penerimaan_cucian`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `id_pelanggan` (`id_pelanggan`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_order` (`id_order`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jenis_cucian`
--
ALTER TABLE `jenis_cucian`
  MODIFY `id_cucian` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `penerimaan_cucian`
--
ALTER TABLE `penerimaan_cucian`
  MODIFY `id_order` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_penerimaan_cucian`
--
ALTER TABLE `detail_penerimaan_cucian`
  ADD CONSTRAINT `fk_detail_penerimaan_jenis_cucian` FOREIGN KEY (`id_cucian`) REFERENCES `jenis_cucian` (`id_cucian`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detail_penerimaan_penerimaan` FOREIGN KEY (`id_order`) REFERENCES `penerimaan_cucian` (`id_order`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penerimaan_cucian`
--
ALTER TABLE `penerimaan_cucian`
  ADD CONSTRAINT `fk_penerimaan_cucian_pelanggan` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_transaksi_penerimaan_cucian` FOREIGN KEY (`id_order`) REFERENCES `penerimaan_cucian` (`id_order`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
