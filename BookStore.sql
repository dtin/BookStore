USE [master]
GO
/****** Object:  Database [BookStore]    Script Date: 12/12/2020 10:20:19 PM ******/
CREATE DATABASE [BookStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BookStore', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS2014\MSSQL\DATA\BookStore.mdf' , SIZE = 4288KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'BookStore_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS2014\MSSQL\DATA\BookStore_log.ldf' , SIZE = 1072KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [BookStore] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BookStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BookStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BookStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [BookStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BookStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BookStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BookStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BookStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BookStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BookStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BookStore] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BookStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BookStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BookStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BookStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BookStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BookStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BookStore] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BookStore] SET  MULTI_USER 
GO
ALTER DATABASE [BookStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BookStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BookStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BookStore] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [BookStore] SET DELAYED_DURABILITY = DISABLED 
GO
USE [BookStore]
GO
/****** Object:  Table [dbo].[tblCategory]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblCategory](
	[categoryId] [int] IDENTITY(1,1) NOT NULL,
	[categoryName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblCategory] PRIMARY KEY CLUSTERED 
(
	[categoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblDiscounts]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblDiscounts](
	[discountCode] [varchar](30) NOT NULL,
	[discountPercent] [float] NOT NULL,
	[createdAt] [date] NOT NULL,
 CONSTRAINT [PK_tblDiscounts] PRIMARY KEY CLUSTERED 
(
	[discountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrderDetails]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblOrderDetails](
	[detailId] [int] IDENTITY(1,1) NOT NULL,
	[orderId] [int] NOT NULL,
	[productId] [int] NOT NULL,
	[price] [bigint] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_tblOrderDetails] PRIMARY KEY CLUSTERED 
(
	[detailId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblOrders]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrders](
	[orderId] [int] IDENTITY(1,1) NOT NULL,
	[userId] [varchar](50) NOT NULL,
	[totalPrice] [bigint] NOT NULL,
	[orderDate] [date] NOT NULL,
	[discountCode] [varchar](20) NULL,
 CONSTRAINT [PK_tblOrders] PRIMARY KEY CLUSTERED 
(
	[orderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblProducts]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblProducts](
	[productId] [int] IDENTITY(1,1) NOT NULL,
	[productName] [nvarchar](200) NOT NULL,
	[categoryId] [int] NOT NULL,
	[image] [varchar](200) NOT NULL,
	[description] [nvarchar](2000) NOT NULL,
	[price] [bigint] NOT NULL,
	[author] [nvarchar](100) NOT NULL,
	[quantity] [int] NOT NULL,
	[createdAt] [date] NOT NULL,
	[status] [bit] NOT NULL,
 CONSTRAINT [PK_tblProducts] PRIMARY KEY CLUSTERED 
(
	[productId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoles](
	[roleId] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [varchar](10) NOT NULL,
 CONSTRAINT [PK_tblRoles] PRIMARY KEY CLUSTERED 
(
	[roleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 12/12/2020 10:20:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsers](
	[userId] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[status] [bit] NOT NULL,
	[roleId] [int] NOT NULL,
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tblCategory] ON 

INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (1, N'Novel')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (2, N'Thriller')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (3, N'Action')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (4, N'Fiction')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (5, N'Economic')
SET IDENTITY_INSERT [dbo].[tblCategory] OFF
INSERT [dbo].[tblDiscounts] ([discountCode], [discountPercent], [createdAt]) VALUES (N'CHAONGAYMOI', 49.9, CAST(N'2020-12-11' AS Date))
INSERT [dbo].[tblDiscounts] ([discountCode], [discountPercent], [createdAt]) VALUES (N'KHACHHANGMOI', 50.5, CAST(N'2020-12-11' AS Date))
INSERT [dbo].[tblDiscounts] ([discountCode], [discountPercent], [createdAt]) VALUES (N'SINHNHAT', 12, CAST(N'2020-12-12' AS Date))
SET IDENTITY_INSERT [dbo].[tblOrderDetails] ON 

INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (1, 16, 3, 111900, 380)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (2, 16, 5, 62200, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (3, 16, 7, 54400, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (4, 17, 3, 111900, 380)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (5, 17, 5, 62200, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (6, 17, 7, 54400, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (7, 18, 5, 62200, 200)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (8, 18, 7, 54400, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (9, 19, 5, 62200, 2)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (10, 19, 7, 54400, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (11, 20, 5, 62200, 2)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (12, 20, 7, 54400, 2)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (13, 21, 3, 111900, 1)
INSERT [dbo].[tblOrderDetails] ([detailId], [orderId], [productId], [price], [quantity]) VALUES (14, 21, 7, 54400, 1)
SET IDENTITY_INSERT [dbo].[tblOrderDetails] OFF
SET IDENTITY_INSERT [dbo].[tblOrders] ON 

INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (16, N'tin@gmail.com', 228500, CAST(N'2020-12-11' AS Date), NULL)
INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (17, N'tin@gmail.com', 228500, CAST(N'2020-12-11' AS Date), N'KHACHHANGMOI')
INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (18, N'tin@gmail.com', 116600, CAST(N'2020-12-11' AS Date), N'CHAONGAYMOI')
INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (19, N'tin@gmail.com', 178800, CAST(N'2020-12-11' AS Date), NULL)
INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (20, N'tin@gmail.com', 233200, CAST(N'2020-12-11' AS Date), NULL)
INSERT [dbo].[tblOrders] ([orderId], [userId], [totalPrice], [orderDate], [discountCode]) VALUES (21, N'tin@gmail.com', 166300, CAST(N'2020-12-12' AS Date), NULL)
SET IDENTITY_INSERT [dbo].[tblOrders] OFF
SET IDENTITY_INSERT [dbo].[tblProducts] ON 

INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (3, N'Con Chim Xanh Biếc Bay Về', 1, N'1.jpg', N'Không giống như những tác phẩm trước đây lấy bối cảnh vùng quê miền Trung đầy ắp những hoài niệm tuổi thơ dung dị, trong trẻo với các nhân vật ở độ tuổi dậy thì, trong quyển sách mới lần này nhà văn Nguyễn Nhật Ánh lấy bối cảnh chính là Sài Gòn – Thành phố Hồ Chí Minh nơi tác giả sinh sống (như là một sự đền đáp ân tình với mảnh đất miền Nam). Các nhân vật chính trong truyện cũng “lớn” hơn, với những câu chuyện mưu sinh lập nghiệp lắm gian nan thử thách của các sinh viên trẻ đầy hoài bão. Tất nhiên không thể thiếu những câu chuyện tình cảm động, kịch tính và bất ngờ khiến bạn đọc ngẩn ngơ, cười ra nước mắt. Và như trong mọi tác phẩm Nguyễn Nhật Ánh, sự tử tế và tinh thần hướng thượng vẫn là điểm nhấn quan trọng trong quyển sách mới này.', 111900, N'Nguyễn Nhật Ánh', 211, CAST(N'2001-01-01' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (7, N'Nhà Giả Kim', 4, N'2.jpg', N'Trong lần xuất bản đầu tiên tại Brazil vào năm 1988, sách chỉ bán được 900 bản. Nhưng, với số phận đặc biệt của cuốn sách dành cho toàn nhân loại, vượt ra ngoài biên giới quốc gia, Nhà giả kim đã làm rung động hàng triệu tâm hồn, trở thành một trong những cuốn sách bán chạy nhất mọi thời đại, và có thể làm thay đổi cuộc đời người đọc.

“Nhưng nhà luyện kim đan không quan tâm mấy đến những điều ấy. Ông đã từng thấy nhiều người đến rồi đi, trong khi ốc đảo và sa mạc vẫn là ốc đảo và sa mạc. Ông đã thấy vua chúa và kẻ ăn xin đi qua biển cát này, cái biển cát thường xuyên thay hình đổi dạng vì gió thổi nhưng vẫn mãi mãi là biển cát mà ông đã biết từ thuở nhỏ. Tuy vậy, tự đáy lòng mình, ông không thể không cảm thấy vui trước hạnh phúc của mỗi người lữ khách, sau bao ngày chỉ có cát vàng với trời xanh nay được thấy chà là xanh tươi hiện ra trước mắt. ‘Có thể Thượng đế tạo ra sa mạc chỉ để cho con người biết quý trọng cây chà là,’ ông nghĩ.”', 54400, N'Paulo Coelho', 120, CAST(N'2001-01-01' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (8, N'Đời Ngắn Đừng Ngủ Dài', 3, N'3.jpg', N'“Mọi lựa chọn đều giá trị. Mọi bước đi đều quan trọng. Cuộc sống vẫn diễn ra theo cách của nó, không phải theo cách của ta. Hãy kiên nhẫn. Tin tưởng. Hãy giống như người thợ cắt đá, đều đặn từng nhịp, ngày qua ngày. Cuối cùng, một nhát cắt duy nhất sẽ phá vỡ tảng đá và lộ ra viên kim cương. Người tràn đầy nhiệt huyết và tận tâm với việc mình làm không bao giờ bị chối bỏ. Sự thật là thế.”

Bằng những lời chia sẻ thật ngắn gọn, dễ hiểu về những trải nghiệm và suy ngẫm trong đời, Robin Sharma tiếp tục phong cách viết của ông từ cuốn sách Điều vĩ đại đời thường để mang đến cho độc giả những bài viết như lời tâm sự, vừa chân thành vừa sâu sắc.', 56100, N'Robin Sharma', 230, CAST(N'2020-12-24' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (12, N'Đừng Lựa Chọn An Nhàn Khi Còn Trẻ', 5, N'5.jpg', N'Trong độ xuân xanh phơi phới ngày ấy, bạn không dám mạo hiểm, không dám nỗ lực để kiếm học bổng, không chịu tìm tòi những thử thách trong công việc, không phấn đấu hướng đến ước mơ của mình. Bạn mơ mộng rằng làm việc xong sẽ vào làm ở một công ty nổi tiếng, làm một thời gian sẽ thăng quan tiến chức. Mơ mộng rằng khởi nghiệp xong sẽ lập tức nhận được tiền đầu tư, cầm được tiền đầu tư là sẽ niêm yết trên sàn chứng khoán. Mơ mộng rằng muốn gì sẽ có đó, không thiếu tiền cũng chẳng thiếu tình, an hưởng những năm tháng êm đềm trong cuộc đời mình. Nhưng vì sao bạn lại nghĩ rằng bạn chẳng cần bỏ ra chút công sức nào, cuộc sống sẽ dâng đến tận miệng những thứ bạn muốn? Bạn cần phải hiểu rằng: Hấp tấp muốn mau chóng thành công rất dễ khiến chúng ta đi vào mê lộ. Thanh xuân là khoảng thời gian đẹp đẽ nhất trong đời, cũng là những năm tháng then chốt có thể quyết định tương lai của một người. Nếu bạn lựa chọn an nhàn trong 10 năm, tương lai sẽ buộc bạn phải vất vả trong 50 năm để bù đắp lại. Nếu bạn bươn chải vất vả trong 10 năm, thứ mà bạn chắc chắn có được là 50 năm hạnh phúc. Điều quý giá nhất không phải là tiền mà là tiền bạc. Thế nên, bạn à, đừng lựa chọn an nhàn khi còn trẻ.', 48600, N'Cảnh Thiên', 100, CAST(N'2020-12-12' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (13, N'Không Phải Chưa Đủ Năng Lực , Mà Là Chưa Đủ Kiên Định', 2, N'6.jfif', N'Bạn định sẽ giảm cân sớm thôi, nhưng không bao giờ ngừng nuông chiều bản thân, ăn uống vô độ?

Bạn quyết tâm học hành, làm việc chăm chỉ, nhưng cứ bật máy tính là xem phim, nghe nhạc hết cả ngày?

Bạn tự nhủ rằng tiền không còn nhiều, phải tiết kiệm, nhưng lại không cầm lòng được trước các món đồ mà hiện tại mình không thực sự cần

Hay có những lúc bạn rất bận rộn với việc của mình, nhưng vì cả nể vẫn chấp nhận giúp đỡ, làm hộ người khác cả phần việc của họ?

Bạn có rất nhiều dự định, rất nhiều quyết tâ nhưng tất cả đều không thể thành hiện thực bởi khả năng tự kiểm soát chưa đủ mạnh mẽ.

Khả năng đó là gì? Tại sao người không có năng lực ấy sẽ buông thả bản thân, khiến cuộc sống đi lệch hướng khỏi quỹ đạo đúng đắn, chẳng thể kiên trì theo đuổi việc gì đến cùng? Ngược lại, người biết tự kiểm soát tốt, dù không đạt được sự thỏa mãn nhất thời, nhưng chắc chắn sẽ thành công trên đường đời và đạt được hạnh phúc dài lâu?

KHÔNG PHẢI CHƯA ĐỦ NĂNG LỰC, MÀ LÀ CHƯA ĐỦ KIÊN ĐỊNH - CUỐN SÁCH GIÚP BẠN LÀM CHỦ SỐ MỆNH CỦA CHÍNH MÌNH.

Tác giả cuốn sách là Hàn Xuân Trạch, nhà tư vấn tâm lý thuộc thế hệ 7x. Là một người lạc quan, nhiệt tình, thích du lịch, mê đọc sách. Các tác phẩm của cô đều ẩn chứa sự dịu dàng của phái nữ trong đó. Hiện tại, cô có chuyên mục riêng trên nhiều tờ tạp chí phụ nữ nổi tiếng, đã xuất bản nhiều cuốn sách được bạn đọc yêu mên.', 42600, N'Hàn Xuân Trạch', 56, CAST(N'2020-12-12' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (15, N'Tony Buổi Sáng - Trên Đường Băng', 1, N'7.jpg', N'"Khi còn trẻ, hãy ra ngoài nhiều hơn ở nhà. Hãy nhào vô xin người khác “bóc hết, lột sạch” khả năng của mình. Chỉ sợ bất tài nộp hồ sơ “xin việc”, mà chả ai thèm cho, chả ai thèm bóc lột. Khi đã được bóc và lột hết, dù sau này đi đâu, làm gì, bạn đều cực kỳ thành công. Vì năng lực được trui rèn trong quá trình làm cho người khác. Sự chăm chỉ, tính kỷ luật, quen tay quen chân, quen ngáp, quen lười… cũng từ công việc mà ra. Mọi ông chủ vĩ đại đều từng là những người làm công ở vị trí thấp nhất. Họ đều rẽ trái trong khi mọi người rẽ phải. Họ có những quyết định không theo đám đông, không cam chịu sống một cuộc đời tầm thường, nhạt nhòa… rồi chết.

Còn những bạn thu nhập 6 triệu cũng túng thiếu, 20 triệu cũng đi vay mượn để tiêu dùng, thì thôi, cuộc đời họ chấm dứt giấc mơ lớn. Tiền nong cá nhân quản lý không được, thì làm sao mà quản trị tài chính một cơ nghiệp lớn?”. Tư duy thế nào thì nó ra số phận thế đó."

Trên đường băng là tập hợp những bài viết được ưa thích trên Facebook của Tony Buổi Sáng. Nhưng khác với một tập tản văn thông thường, nội dung các bài được chọn lọc có chủ đích, nhằm chuẩn bị về tinh thần, kiến thức…cho các bạn trẻ vào đời. Sách gồm 3 phần: “Chuẩn bị hành trang”, “Trong phòng chờ sân bay” và “Lên máy bay”, tương ứng với những quá trình một bạn trẻ phải trải qua trước khi “cất cánh” trên đường băng cuộc đời, bay vào bầu trời cao rộng.', 43000, N'Tony Buổi Sáng', 320, CAST(N'2020-12-12' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (16, N'Bạn Không Thông Minh Lắm Đâu', 3, N'8.jpg', N'Hiệu ứng Dunning-Kruger

Bạn Vẫn Tưởng Bạn có thể dự đoán được chính xác khả năng của mình ở mọi tình huống.
Sự ThậT Là Về cơ bản thì bạn khá kém trong việc đánh giá khả năng của bản thân và độ khó của những công việc phức tạp.
Hãy tưởng tượng rằng bạn chơi rất giỏi một trò nào đó. Trò nào cũng được - cờ vua, Street Fighter1, bài poker - không quan trọng. Bạn thường xuyên chơi trò này với lũ bạn và lúc nào cũng thắng. Bạn cảm thấy mình thật giỏi, và bạn tin rằng mình có thể giành chiến thắng ở một giải đấu nếu có cơ hội. Vậy là bạn lên mạng tìm xem giải đấu khu vực sẽ diễn ra ở đâu và khi nào. Bạn trả phí tham gia và rồi bị hạ đo ván ngay trong hiệp đầu tiên. Hóa ra là bạn không giỏi lắm đâu. Bạn cứ nghĩ rằng mình thuộc dạng đỉnh của đỉnh, nhưng mà thực chất bạn chỉ là một tay ngang thôi. Đây được gọi là hiệu ứng Dunning-Kruger, và nó là một phần cơ bản trong bản chất của con người.

Hãy thử nghĩ về những ngôi sao Youtube mới nổi trong mấy năm gần đây - những người múa máy một cách vụng về với các loại vũ khí, hoặc là hát chẳng bao giờ đúng tông cả. Những màn trình diễn này thường rất tệ. Không phải họ cố tình dùng bản thân để mua vui cho người khác đâu. Trình độ của họ thực sự tệ, và chắc hẳn bạn cũng thắc mắc là tại sao lại có người có thể tự đặt mình lên một sân khấu toàn cầu với những màn trình diễn đáng xấu hổ như vậy. Vấn đề nằm ở chỗ họ không tưởng tượng được là công chúng toàn cầu có thể khắt khe và đòi hỏi cao hơn so với lượng khán giả nhỏ bé họ vốn đã quen thuộc là bạn bè, gia đình và những người đồng cấp. Như nhà triết học Bertrand Russell đã từng phải than thở: “Trong thế giới hiện đại ngày nay thì những kẻ khờ khạo luôn tự tin hết mức trong khi những người khôn ngoan thì lại luôn nghi ngờ.”

Hiệu ứng Dunning-Kruger là lý do tại sao mà những chương trình như America’s Got Talent1 hay American Idol2 có thể diễn ra. Ở trong phòng karaoke với lũ bạn, bạn có thể là người hát hay nhất. Nhưng để đối chọi với các thí sinh trong cả một quốc gia? Khó lắm.', 49000, N'David McRaney', 210, CAST(N'2000-12-12' AS Date), 1)
INSERT [dbo].[tblProducts] ([productId], [productName], [categoryId], [image], [description], [price], [author], [quantity], [createdAt], [status]) VALUES (18, N'Cuộc Sống "Đếch" Giống Cuộc Đời', 2, N'9.jpg', N'Ghi dấu ấn mạnh mẽ nhờ giọng văn phóng khoáng, hóm hỉnh, sâu cay và đặc biệt là biệt tài gây cười đặc trưng có một không hai, chắc chắn với cuốn sách tiếp theo này, tác giả Hoàng Hải Nguyễn sẽ tiếp tục chinh phục các độc giả khó tính nhất.
Luôn tâm niệm mình là một người viết yêu văn chương, chính vì thế mà các bài viết của anh không tạo cảm giác ức chế hay nhàm chán với những lối đi cũ mòn của văn chương hoa mĩ. Dưới góc nhìn của một người đàn ông U40, đã có gia đình và hai con, anh nhìn nhận sự xoay vần của cuộc đời theo cách của người từng trải có nhiều kinh nghiệm.
Những câu chuyện về tổ ấm gia đình, chuyện xã hội, chuyện cuộc sống, chuyện phiếm bên lề được kết hợp khéo léo với chất văn “rất đàn ông” là điểm sáng giúp anh ghi dấu trong lòng bạn đọc. Từ những con chữ thông minh, ,sắc lẹm như lưỡi dao ấy, lại khiến người đọc trăn trở, đau đáu, có một khoảng lặng để suy ngẫm và nhận ra nhiều điều có ích.
“Gia đình là thứ bất khả xâm phạm. Cứ tin tôi, mọi sự thành công trong xã hội đều không thể bù đắp được thất bại trong cuộc sống gia đình”.
Nếu mong chờ một tác phẩm văn học chính thống với những câu chữ khô khốc, thì hẳn là cuốn sách sẽ khiến bạn thất vọng. Nhưng nếu bạn đang cảm thấy bế tắc trong cuộc sống, cần một ai đó xốc lại tinh thần để tiếp tục chiến đấu với cuộc đời thì chắc chắn không nên bỏ lỡ cuốn sách này. Cuộc sống đếch giống cuộc đời sẽ đem lại cho bạn những tiếng cười sảng khoái, những phút giây thư giãn cùng những bài học sâu sắc, tâm đắc qua từng trang sách.
Giống như lời tác giả tâm sự “Cuộc sống rất giống cuộc đời vì cuộc sống là tập hợp của vô số cuộc đời, cuộc sống không giống cuộc đời vì cuộc đời là hữu hạn còn cuộc sống là vô hạn. Cho dù cuộc sống hay cuộc đời của bạn có khó khan vất vả thế nào thì tôi vẫn mong các bạn hãy luôn suy nghĩ một cách tích cực và hài hước nhất.
Có người đã nói rằng: Cuối cùng thì mọi thứ đều sẽ ổn, nếu chưa ổn thì tức là chưa phải cuối cùng. Vậy đấy các bạn ạ!', 45000, N'Hoàng Hải Nguyễn', 123, CAST(N'2020-12-12' AS Date), 1)
SET IDENTITY_INSERT [dbo].[tblProducts] OFF
SET IDENTITY_INSERT [dbo].[tblRoles] ON 

INSERT [dbo].[tblRoles] ([roleId], [roleName]) VALUES (1, N'User')
INSERT [dbo].[tblRoles] ([roleId], [roleName]) VALUES (2, N'Admin')
SET IDENTITY_INSERT [dbo].[tblRoles] OFF
INSERT [dbo].[tblUsers] ([userId], [password], [name], [status], [roleId]) VALUES (N'admin@gmail.com', N'1', N'Admin', 1, 2)
INSERT [dbo].[tblUsers] ([userId], [password], [name], [status], [roleId]) VALUES (N'khachmoi@gmail.com', N'1', N'Khách Mới', 1, 1)
INSERT [dbo].[tblUsers] ([userId], [password], [name], [status], [roleId]) VALUES (N'tin@gmail.com', N'1', N'Tín', 1, 1)
USE [master]
GO
ALTER DATABASE [BookStore] SET  READ_WRITE 
GO
