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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelTrensfer {

  // 直接与非直接缓冲区
  // 非直接缓冲区是需要经过一个：copy的阶段的(从内核空间copy到用户空间)
  // 直接缓冲区不需要经过copy阶段，也可以理解成--->内存映射文件，(上面的图片也有过例子)。
  public static void main(String[] args) throws IOException {
    // 1.文件的路径
    String pathIn = "E:\\CloudMusic\\4円 - Life is like a boat.mp3";
    String pathOut = "E:\\CloudMusic\\円 - Life is like a boat2.mp3";
    // 2. 文件的读写  ---  使用FileChannel配合缓冲区实现文件复制的功能：
    FileInputStream inputStream = new FileInputStream(pathIn);
    FileOutputStream fileOutputStream = new FileOutputStream(pathOut);
    FileChannel fin = inputStream.getChannel();
    FileChannel fos = fileOutputStream.getChannel();

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (fin.read(buffer) != -1) {
      buffer.flip();
      fos.write(buffer);
      buffer.clear();
    }
    //3. 使用内存映射文件的方式实现文件复制的功能(直接操作缓冲区)：
    FileChannel in = FileChannel.open(Paths.get(pathIn), StandardOpenOption.READ);
    FileChannel out = FileChannel.open(Paths.get(pathOut), StandardOpenOption.WRITE);
    System.out.println(in.size());
    long transferTo = in.transferTo(0, in.size(), out);
    in.close();
    out.close();
  }
}
