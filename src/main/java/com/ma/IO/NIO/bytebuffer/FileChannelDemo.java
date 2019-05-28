/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO.bytebuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
// https://www.cnblogs.com/XuYiHe/p/9111458.html
// Channel通道只负责传输数据、不直接操作数据的。操作数据都是通过Buffer缓冲区来进行操作！
public class FileChannelDemo {

  public static void main(String[] args) throws IOException {
    String path = "E:\\CloudMusic\\4円 - Life is like a boat.mp3";
    String pathOut = "E:\\CloudMusic\\円 - Life is like a boat2.mp3";
    // 1. 通过本地IO的方式来获取通道 , 得到文件的输入通道
    FileInputStream fileInputStream = new FileInputStream(path);
    FileChannel finput = fileInputStream.getChannel();
    // 2. jdk1.7后通过静态方法.open()获取通道
    FileChannel fout = FileChannel.open(Paths.get(path), StandardOpenOption.WRITE);

  }
}
