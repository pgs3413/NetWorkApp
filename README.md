# NetWorkApp
互联网程序设计（java）

chapter01 : javafx页面，按键响应，从本地文件读取和保存信息到本地文

chapter02 : TCP连接通信，服务端ServerSocket 客户端Socket 多线程接受信息 字节流封装成字符流

chapter04 : tcp通信，网络文件传输，传输字节流

chapter05 : 客户端与客户端通信，服务器端转发。本包下有一个服务器类，使用线程池，三种线程模式，对应三种通信模式，使用chapter02中的客户端

chapter06 : UDP通信（无连接），两端都是DataGramSocket，可指定端口，将数据以字节流的方式封装成DataGramPacket,其中指定对方ip和端口，使用DataGramSocket发送；使用DataGramSocket接受，将收到的字节流转入DataGramPacket中的字节数组中，其中用来接受的DataGramPacket可获得对方的ip和端口。MultiCastSocket 用于组播。

chapter07 : 邮件发送程序。可采用控制台和chapter02中的客户端与smtp.qq.com服务器进行交互，达到发送邮件功能。BASE64Encode.java 对字符串进行BASE64编码，TCPMailClientFx.java封装了一些交互过程。
JavaMailAPIClient.java采用javax.mail包进行邮件发送和邮件查看功能。
