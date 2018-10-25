-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 25, 2018 lúc 09:58 AM
-- Phiên bản máy phục vụ: 10.1.31-MariaDB
-- Phiên bản PHP: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `manager_restaurant`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `area`
--

CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `area` varchar(100) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `res_id` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `area`
--

INSERT INTO `area` (`id`, `area`, `status`, `res_id`) VALUES
(1, 'Khu A', 0, 1),
(2, 'Khu B', 0, 1),
(3, 'Khu C', 0, 1),
(4, 'Khu D', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `table_id` int(11) NOT NULL,
  `staff_id` int(11) NOT NULL,
  `customer` varchar(255) DEFAULT NULL,
  `time_order` datetime DEFAULT CURRENT_TIMESTAMP,
  `bill_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_price` bigint(20) NOT NULL DEFAULT '0',
  `guest_money` bigint(11) NOT NULL DEFAULT '0',
  `money_back` bigint(11) NOT NULL DEFAULT '0',
  `discount` int(11) NOT NULL DEFAULT '0',
  `surcharge` bigint(11) NOT NULL DEFAULT '0',
  `ship` int(11) NOT NULL DEFAULT '0',
  `bill_note` text,
  `status` int(1) NOT NULL DEFAULT '0',
  `res_id` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`id`, `table_id`, `staff_id`, `customer`, `time_order`, `bill_date`, `total_price`, `guest_money`, `money_back`, `discount`, `surcharge`, `ship`, `bill_note`, `status`, `res_id`) VALUES
(3, 2, 2, 'Không có thông tin', NULL, '2018-07-04 19:26:54', 87500, 77500, 0, 0, 0, 0, 'Không', 1, 1),
(4, 6, 2, 'Không có thông tin', NULL, '2018-07-06 18:39:26', 93000, 80000, 2000, 0, 0, 0, 'Không', 1, 1),
(5, 7, 2, 'Không có thông tin', NULL, '2018-07-06 18:40:20', 142500, 142500, 0, 0, 0, 0, 'Không', 1, 1),
(6, 6, 2, 'Không có thông tin', NULL, '2018-07-06 19:23:04', 36000, 313000, 282000, 0, 0, 0, 'Không', 1, 1),
(7, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:30:51', 35000, 30000, 0, 0, 0, 0, 'Không', 1, 1),
(8, 2, 2, 'Không có thông tin', NULL, '2018-07-06 19:33:28', 37000, 32000, 0, 0, 0, 0, 'Không', 1, 1),
(9, 2, 2, 'Không có thông tin', NULL, '2018-07-06 19:37:34', 15000, 15000, 0, 0, 0, 0, 'Không', 1, 1),
(10, 2, 2, 'Không có thông tin', NULL, '2018-07-06 19:37:51', 10000, 10000, 0, 0, 0, 0, 'Không', 1, 1),
(11, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:40:59', 25000, 200000, 180000, 0, 0, 0, 'Không', 1, 1),
(12, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:45:36', 25000, 20000, 0, 0, 0, 0, 'Không', 1, 1),
(13, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:48:59', 25000, 20000, 0, 0, 0, 0, 'Không', 1, 1),
(14, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:52:29', 25000, 20000, 0, 0, 0, 0, 'Không', 1, 1),
(15, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:54:39', 27000, 27000, 0, 0, 0, 0, 'Không', 1, 1),
(16, 1, 2, 'Không có thông tin', NULL, '2018-07-06 19:57:55', 19000, 19000, 0, 0, 0, 0, 'Không', 1, 1),
(18, 6, 2, 'Không có thông tin', NULL, '2018-07-06 21:33:28', 142500, 200000, 57500, 0, 0, 0, 'Không', 1, 1),
(19, 1, 2, 'Không có thông tin', NULL, '2018-07-07 14:06:58', 57500, 57500, 0, 0, 0, 0, 'Không', 1, 1),
(20, 3, 2, 'Không có thông tin', NULL, '2018-07-07 14:13:53', 19000, 19000, 0, 0, 0, 0, 'Không', 1, 1),
(21, 4, 2, 'Không có thông tin', NULL, '2018-07-07 14:21:15', 62500, 62500, 0, 0, 0, 0, 'Không', 1, 1),
(22, 5, 2, 'Không có thông tin', NULL, '2018-07-07 14:52:37', 57500, 57500, 0, 0, 0, 0, 'Không', 1, 1),
(24, 4, 2, 'Không có thông tin', NULL, '2018-07-08 10:52:19', 105000, 200000, 85000, 0, 10000, 0, 'Không', 1, 1),
(25, 16, 2, 'Nguyen van a', NULL, '2018-07-08 12:05:28', 45000, 55000, 0, 0, 10000, 0, 'abolo', 1, 1),
(27, 13, 2, 'Không có thông tin', NULL, '2018-07-08 13:01:53', 57500, 57500, 0, 0, 0, 0, 'Không', 1, 1),
(31, 12, 2, 'nguyen van tung', NULL, '2018-07-09 15:34:16', 84500, 500000, 415500, 0, 0, 0, 'Không', 1, 1),
(32, 16, 2, 'Không có thông tin', NULL, '2018-07-09 15:39:34', 115000, 115000, 0, 0, 0, 0, 'Không', 1, 1),
(34, 4, 2, 'Không có thông tin', NULL, '2018-07-09 15:42:39', 72500, 72500, 0, 0, 0, 0, 'Không', 1, 1),
(35, 5, 2, 'Không có thông tin', NULL, '2018-07-09 15:52:04', 47500, 47500, 0, 0, 0, 0, 'Không', 1, 1),
(36, 4, 2, 'Không có thông tin', NULL, '2018-07-09 15:56:10', 47500, 47500, 0, 0, 0, 0, 'Không', 1, 1),
(37, 2, 2, 'Không có thông tin', NULL, '2018-07-09 16:04:04', 67500, 200000, 132500, 0, 0, 0, 'Không', 1, 1),
(38, 1, 2, 'Không có thông tin', NULL, '2018-07-09 16:07:09', 82500, 82500, 0, 0, 0, 0, 'Không', 1, 1),
(39, 2, 2, 'Không có thông tin', NULL, '2018-07-09 16:17:02', 57500, 57500, 0, 0, 0, 0, 'Không', 1, 1),
(40, 1, 5, 'Không có thông tin', NULL, '2018-07-09 17:24:17', 107000, 107000, 0, 0, 0, 0, 'Không', 1, 1),
(41, 4, 5, 'Không có thông tin', NULL, '2018-07-09 19:13:59', 67500, 200000, 132500, 0, 0, 0, 'Không', 1, 1),
(42, 2, 5, 'Không có thông tin', NULL, '2018-07-09 19:22:54', 35000, 35000, 0, 0, 0, 0, 'Không', 1, 1),
(43, 2, 5, 'Không có thông tin', NULL, '2018-07-09 22:02:41', 67500, 67500, 0, 0, 0, 0, 'Không', 1, 1),
(44, 1, 5, 'Không có thông tin', NULL, '2018-07-09 22:03:12', 62500, 62500, 0, 0, 0, 0, 'Không', 1, 1),
(46, 12, 5, 'Không có thông tin', NULL, '2018-07-09 22:14:53', 29000, 29000, 0, 0, 0, 0, 'Không', 1, 1),
(47, 13, 5, 'Không có thông tin', NULL, '2018-07-09 22:15:25', 57500, 100000, 42500, 0, 0, 0, 'Không', 1, 1),
(48, 13, 5, 'Không có thông tin', NULL, '2018-07-10 23:17:21', 155000, 155000, 0, 0, 0, 0, 'Không', 1, 1),
(49, 2, 5, 'Không có thông tin', NULL, '2018-07-10 23:15:31', 106500, 200000, 93500, 10000, 10000, 0, 'Không', 1, 1),
(52, 5, 5, 'Không có thông tin', NULL, '2018-07-10 23:24:13', 20000, 20000, 0, 0, 0, 0, 'Không', 1, 1),
(54, 5, 5, 'Không có thông tin', NULL, '2018-07-11 08:13:33', 86500, 200000, 113500, 0, 0, 0, 'Không', 1, 1),
(57, 3, 1, 'Không có thông tin', NULL, '2018-09-14 20:15:34', 100000, 100000, 0, 0, 0, 0, 'Không', 1, 1),
(58, 9, 1, 'Không có thông tin', NULL, '2018-10-08 21:41:23', 160000, 160000, 0, 0, 0, 0, 'Không', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill_details`
--

CREATE TABLE `bill_details` (
  `id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `food_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `bill_details`
--

INSERT INTO `bill_details` (`id`, `bill_id`, `food_id`, `quantity`, `note`, `status`) VALUES
(1, 3, 5, 1, NULL, 1),
(2, 3, 1, 2, NULL, 1),
(7, 3, 2, 1, NULL, 1),
(8, 4, 8, 2, NULL, 1),
(9, 4, 3, 3, NULL, 1),
(10, 4, 1, 3, NULL, 1),
(11, 5, 6, 3, NULL, 1),
(12, 6, 1, 1, NULL, 1),
(13, 6, 8, 1, NULL, 1),
(14, 6, 12, 1, NULL, 1),
(15, 7, 1, 1, NULL, 1),
(16, 7, 2, 1, NULL, 1),
(17, 7, 3, 1, NULL, 1),
(18, 8, 12, 1, NULL, 1),
(19, 9, 7, 1, NULL, 1),
(20, 10, 4, 1, NULL, 1),
(21, 8, 1, 1, NULL, 1),
(22, 8, 4, 1, NULL, 1),
(23, 11, 1, 1, NULL, 1),
(24, 11, 2, 1, NULL, 1),
(25, 12, 1, 1, NULL, 1),
(26, 12, 2, 1, NULL, 1),
(27, 13, 1, 1, NULL, 1),
(28, 13, 2, 1, NULL, 1),
(29, 14, 1, 1, NULL, 1),
(30, 14, 2, 1, NULL, 1),
(31, 15, 12, 1, NULL, 1),
(32, 15, 7, 1, NULL, 1),
(33, 16, 8, 1, NULL, 1),
(35, 16, 4, 1, NULL, 1),
(36, 18, 5, 1, NULL, 1),
(37, 18, 6, 2, NULL, 1),
(38, 19, 5, 1, NULL, 1),
(39, 19, 3, 1, NULL, 1),
(40, 20, 2, 1, NULL, 1),
(41, 20, 9, 1, NULL, 1),
(42, 21, 6, 1, NULL, 1),
(43, 21, 1, 1, NULL, 1),
(44, 22, 3, 1, NULL, 1),
(46, 22, 6, 1, NULL, 1),
(47, 24, 5, 2, NULL, 1),
(48, 24, 3, 1, NULL, 1),
(49, 25, 1, 2, NULL, 1),
(50, 25, 7, 1, NULL, 1),
(55, 27, 6, 1, NULL, 1),
(56, 27, 2, 1, NULL, 1),
(57, 31, 12, 1, NULL, 1),
(62, 31, 1, 1, NULL, 1),
(63, 31, 5, 1, NULL, 1),
(64, 31, 3, 1, NULL, 1),
(66, 32, 5, 2, NULL, 1),
(67, 32, 2, 1, NULL, 1),
(69, 32, 3, 1, NULL, 1),
(70, 34, 1, 1, NULL, 1),
(71, 34, 4, 1, NULL, 1),
(72, 34, 6, 1, NULL, 1),
(73, 35, 6, 1, NULL, 1),
(74, 36, 6, 1, NULL, 1),
(75, 37, 5, 1, NULL, 1),
(76, 37, 2, 1, NULL, 1),
(77, 37, 3, 1, NULL, 1),
(78, 38, 1, 1, NULL, 1),
(79, 38, 2, 1, NULL, 1),
(80, 38, 3, 1, NULL, 1),
(81, 38, 5, 1, NULL, 1),
(82, 39, 6, 1, NULL, 1),
(83, 39, 3, 1, NULL, 1),
(84, 40, 6, 2, NULL, 1),
(85, 40, 12, 1, NULL, 1),
(86, 41, 6, 1, NULL, 1),
(87, 41, 3, 1, NULL, 1),
(88, 41, 2, 1, NULL, 1),
(89, 42, 7, 1, NULL, 1),
(90, 42, 2, 2, NULL, 1),
(91, 43, 6, 1, NULL, 1),
(92, 43, 3, 1, NULL, 1),
(93, 43, 2, 1, NULL, 1),
(94, 44, 1, 1, NULL, 1),
(95, 44, 5, 1, NULL, 1),
(96, 48, 6, 1, NULL, 1),
(99, 46, 2, 1, NULL, 1),
(100, 46, 4, 1, NULL, 1),
(101, 46, 9, 1, NULL, 1),
(102, 47, 2, 1, NULL, 1),
(103, 47, 6, 1, NULL, 1),
(104, 48, 3, 2, NULL, 1),
(105, 48, 2, 2, NULL, 1),
(106, 48, 5, 1, NULL, 1),
(107, 49, 8, 1, NULL, 1),
(108, 49, 2, 2, NULL, 1),
(111, 48, 4, 2, NULL, 1),
(114, 49, 5, 1, NULL, 1),
(117, 49, 4, 3, NULL, 1),
(118, 52, 3, 1, NULL, 0),
(119, 52, 4, 1, NULL, 0),
(121, 54, 9, 1, NULL, 1),
(122, 54, 2, 2, NULL, 1),
(123, 54, 5, 1, NULL, 1),
(125, 54, 4, 1, NULL, 1),
(132, 57, 5, 1, NULL, 0),
(133, 57, 6, 1, NULL, 0),
(134, 58, 5, 3, NULL, 0),
(135, 58, 4, 1, NULL, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `food_name` varchar(255) NOT NULL,
  `kind_food` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `promotions` int(3) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `food`
--

INSERT INTO `food` (`id`, `food_name`, `kind_food`, `quantity`, `price`, `promotions`, `status`) VALUES
(1, 'Rau muốn xào', 2, NULL, 15000, 0, 0),
(2, 'Rau lang xào', 2, NULL, 10000, 0, 0),
(3, 'Bắp cải nướng', 2, NULL, 10000, 0, 0),
(4, 'Khoai lang nướng', 2, NULL, 10000, 0, 0),
(5, 'Cocktail Shochu', 1, NULL, 50000, 5, 0),
(6, 'Cocktail Sake', 1, NULL, 50000, 5, 0),
(7, 'Cafe sữa đá', 3, NULL, 15000, 0, 0),
(8, 'Coca Cola', 1, NULL, 9000, 0, 0),
(9, 'Pessi', 1, NULL, 9000, 0, 0),
(12, 'Cafe Đen', 3, NULL, 12000, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kind_food`
--

CREATE TABLE `kind_food` (
  `id` int(11) NOT NULL,
  `kind_food_name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `res_id` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `kind_food`
--

INSERT INTO `kind_food` (`id`, `kind_food_name`, `status`, `res_id`) VALUES
(1, 'Nước uống', 0, 1),
(2, 'Món ăn', 0, 1),
(3, 'cafe2', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kind_of_staff`
--

CREATE TABLE `kind_of_staff` (
  `id` int(11) NOT NULL,
  `kind_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `kind_of_staff`
--

INSERT INTO `kind_of_staff` (`id`, `kind_name`) VALUES
(1, 'Quản Lý'),
(2, 'Phục vụ'),
(3, 'Thu ngân');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `restaurant`
--

CREATE TABLE `restaurant` (
  `id` int(11) NOT NULL,
  `res_name` varchar(255) NOT NULL,
  `res_address` varchar(300) DEFAULT NULL,
  `res_describe` varchar(2000) DEFAULT NULL,
  `phone` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `restaurant`
--

INSERT INTO `restaurant` (`id`, `res_name`, `res_address`, `res_describe`, `phone`) VALUES
(1, 'AsaAsa', 'Bouvet - Na Uy', 'là đảo xa nhất trên thế giới, hoàn toàn không có người ở.\r\nNó không có cảng hoặc cầu tàu, chỉ có bến tàu ở bờ biển, và do đó rất khó tiếp cận. Cách dễ nhất để vào đảo là bằng trực thăng xuất phát từ tàu. Những dòng sông băng tạo thành một lớp băng dày hình thành nên những vách đá cao nhô ra biển hoặc về phía những bờ biển đen với cát núi lửa. Bờ biển dài 29,6 km (18,4 dặm) thường bao bọc bởi những đám băng. Điểm cao nhất trên đảo được gọi là Olavtoppen, đỉnh của nó cao 780 m (2.559 ft) từ mặt nước biển. Có một bãi đá ngầm dung nham trên bờ biển phía tây của đảo, dường như xuất hiện khoảng giữa năm 1955 và 1958, tạo thành nơi làm tổ cho chim chóc.\r\n\r\nDo khí hậu khắc nghiệt và địa thế băng bao phủ, thực vật chỉ giới hạn ở địa y và rêu. Hải cẩu, hải âu và chim cánh cụt là những cư dân duy nhất trên đảo.', '01626051989');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staff`
--

CREATE TABLE `staff` (
  `id` int(11) NOT NULL,
  `staff_name` varchar(200) NOT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `kind_of_staff` int(11) NOT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL,
  `staff_root` int(11) NOT NULL DEFAULT '-1',
  `status` int(2) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `staff`
--

INSERT INTO `staff` (`id`, `staff_name`, `email`, `password`, `kind_of_staff`, `phone_number`, `address`, `res_id`, `staff_root`, `status`) VALUES
(1, 'Nguyễn Văn Tùng', 'tugtitu@gmail.com', '123456', 1, '01666051989', 'tp.HCM', 1, -1, 0),
(2, 'zoro', 'zolo123@yahoo.com', '123456', 2, '0122456799', 'tp.HCM', 1, 1, 0),
(4, 'Hung nguyen', 'hung999@yahoo.com', '123456', 3, '0987343222', 'tp . HCM', 1, 1, 0),
(5, 'Nami', 'nami999@yahoo.com', '123456', 3, '01626736036', 'Tp.hcm', 1, 1, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `table_f`
--

CREATE TABLE `table_f` (
  `id` int(11) NOT NULL,
  `table_name` varchar(200) NOT NULL,
  `area_id` int(11) NOT NULL,
  `time_booking` varchar(50) DEFAULT NULL,
  `customer_phone` varchar(11) DEFAULT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `table_f`
--

INSERT INTO `table_f` (`id`, `table_name`, `area_id`, `time_booking`, `customer_phone`, `status`) VALUES
(1, 'A_1', 1, NULL, NULL, 0),
(2, 'A_2', 1, NULL, NULL, 0),
(3, 'A_3', 1, NULL, NULL, 0),
(4, 'A_4', 1, NULL, NULL, 0),
(5, 'A_5', 1, NULL, NULL, 0),
(6, 'B_1', 2, NULL, NULL, 0),
(7, 'B_2', 2, NULL, NULL, 0),
(8, 'B_3', 2, NULL, NULL, 0),
(9, 'B_4', 2, NULL, NULL, 0),
(10, 'B_5', 2, NULL, NULL, 0),
(11, 'C_1', 3, NULL, NULL, 0),
(12, 'C_2', 3, NULL, NULL, 0),
(13, 'C_3', 3, NULL, NULL, 0),
(14, 'C_4', 3, NULL, NULL, 0),
(15, 'C_5', 3, NULL, NULL, 0),
(16, 'D_1', 4, NULL, NULL, 0),
(17, 'ban', 4, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `working_time`
--

CREATE TABLE `working_time` (
  `id` int(11) NOT NULL,
  `working_time_name` varchar(100) NOT NULL,
  `weekdays` varchar(150) DEFAULT NULL,
  `from_hour` time DEFAULT NULL,
  `come_hour` time DEFAULT NULL,
  `staff_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `working_time`
--

INSERT INTO `working_time` (`id`, `working_time_name`, `weekdays`, `from_hour`, `come_hour`, `staff_id`) VALUES
(1, 'Full time', '1,2,4,8,16,32,64', '07:00:00', '20:00:00', 2),
(2, 'Ca 1', '1,2,4,8,16,32', '08:00:00', '10:30:00', 1),
(4, 'ca2', '1,4,16', '13:00:00', '20:00:00', 4),
(5, 'Full', '1,2,4,8,16,32', '07:00:00', '21:30:00', 5);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `table_id` (`table_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Chỉ mục cho bảng `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_id` (`bill_id`),
  ADD KEY `food_id` (`food_id`);

--
-- Chỉ mục cho bảng `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kind_food` (`kind_food`);

--
-- Chỉ mục cho bảng `kind_food`
--
ALTER TABLE `kind_food`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `kind_of_staff`
--
ALTER TABLE `kind_of_staff`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kind_of_staff` (`kind_of_staff`),
  ADD KEY `cty_id` (`res_id`),
  ADD KEY `staff_root` (`staff_root`);

--
-- Chỉ mục cho bảng `table_f`
--
ALTER TABLE `table_f`
  ADD PRIMARY KEY (`id`),
  ADD KEY `area_id` (`area_id`);

--
-- Chỉ mục cho bảng `working_time`
--
ALTER TABLE `working_time`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `area`
--
ALTER TABLE `area`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT cho bảng `bill_details`
--
ALTER TABLE `bill_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT cho bảng `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `kind_food`
--
ALTER TABLE `kind_food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `kind_of_staff`
--
ALTER TABLE `kind_of_staff`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `staff`
--
ALTER TABLE `staff`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `table_f`
--
ALTER TABLE `table_f`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `working_time`
--
ALTER TABLE `working_time`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `table_f` (`id`),
  ADD CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`);

--
-- Các ràng buộc cho bảng `bill_details`
--
ALTER TABLE `bill_details`
  ADD CONSTRAINT `bill_details_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`),
  ADD CONSTRAINT `bill_details_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`);

--
-- Các ràng buộc cho bảng `food`
--
ALTER TABLE `food`
  ADD CONSTRAINT `food_ibfk_1` FOREIGN KEY (`kind_food`) REFERENCES `kind_food` (`id`);

--
-- Các ràng buộc cho bảng `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`kind_of_staff`) REFERENCES `kind_of_staff` (`id`);

--
-- Các ràng buộc cho bảng `table_f`
--
ALTER TABLE `table_f`
  ADD CONSTRAINT `table_f_ibfk_1` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`);

--
-- Các ràng buộc cho bảng `working_time`
--
ALTER TABLE `working_time`
  ADD CONSTRAINT `working_time_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
