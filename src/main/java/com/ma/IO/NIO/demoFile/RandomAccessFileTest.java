package com.ma.IO.NIO.demoFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomAccessFileTest {

  private static final Logger logger = LoggerFactory.getLogger(RandomAccessFileTest.class);

  private static final String ENCODING = "UTF-8";
  private static final int NUM = 50;

  private static File file = new File(
      ClassLoader.getSystemResource("").getPath() + File.separator + "test.txt");
  private static File randomFile = new File(
      ClassLoader.getSystemResource("").getPath() + File.separator + "RandomFile.txt");

  /**
   * 生成1000w随机文本文件
   */
  @Test
  public void makePin() {
    String prefix = "_$#";
    OutputStreamWriter out = null;
    try {
      out = new OutputStreamWriter(new FileOutputStream(file, true), ENCODING);
      // 在1500w里随机1000w数据
      for (int j = 0; j < 1037; j++) {
        out.write(prefix + (int) (130000000 * Math.random()) + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(out);
    }
    logger.info(file.getAbsolutePath());
  }

  //按行读取，只能从第一行向后遍历，到需要读取的行时开始读入，直到完成；
  // 在我的测试用例中，
  // 读取1000W行数据每次5万行，用时93秒，效率实测比RandomAccessFile要高，
  // 但读取一亿跳数据时效率太低了（因为每次都要从头遍历），因为测试时超过1个小时，放弃测试；


  /**
   * 测试RandomAccessFile读取文件
   */
  @Test
  public void testRandomAccessRead() {
    long start = System.currentTimeMillis();
//  
    logger.info(String.valueOf(file.exists()));
    long pos = 0L;
    while (true) {
      Map<String, Object> res = FileUtil.readLine(file, ENCODING, pos, NUM);
      // 如果返回结果为空结束循环
      if (MapUtils.isEmpty(res)) {
        break;
      }
      Object po = res.get("pins");
      List<String> pins = (List<String>) res.get("pins");
      if (CollectionUtils.isNotEmpty(pins)) {
//                logger.info(Arrays.toString(pins.toArray()));  
        if (pins.size() < NUM) {
          break;
        }
      } else {
        break;
      }
      pos = (Long) res.get("pos");
    }
    logger.info(((System.currentTimeMillis() - start) / 1000) + "");
  }

  /**
   * 测试RandomAccessFile读取文件
   *
   * 实际不适用于这种大数据读取，RandomAccessFile是为了磁盘文件的随机访问，所以效率很低，
   *
   * 1000w行测试时用时140秒，一亿行数据测试用时1438秒但由于可以通过 getFilePointer 方法记录位置，
   *
   * 并通过seek方法指定读取位置，所以从理论上比较适用这种大数据按行读取的场景；
   *
   * RandomAccessFile只能按照8859_1这种方法读取，所以需要对内容重新编码，
   *
   * 方法如下 Java代码
   *
   * new String(pin.getBytes("8859_1"), "")
   */
  @Test
  public void testBufferedRandomAccessRead() {
    long start = System.currentTimeMillis();
//  
    logger.info(String.valueOf(file.exists()));
    long pos = 0L;
    while (true) {
      Map<String, Object> res = FileUtil.BufferedRandomAccessFileReadLine(file, ENCODING, pos, NUM);
      // 如果返回结果为空结束循环
      if (MapUtils.isEmpty(res)) {
        break;
      }
      List<String> pins = (List<String>) res.get("pins");
      if (CollectionUtils.isNotEmpty(pins)) {
//                logger.info(Arrays.toString(pins.toArray()));  
        if (pins.size() < NUM) {
          break;
        }
      } else {
        break;
      }
      pos = (Long) res.get("pos");
    }
    logger.info(((System.currentTimeMillis() - start) / 1000) + "");
  }

  /**
   * 测试普通读取文件
   */
  @Test
  public void testCommonRead() {
    long start = System.currentTimeMillis();
    logger.info(String.valueOf(randomFile.exists()));
    int index = 0;
    while (true) {
      List<String> pins = FileUtil.readLine(file, ENCODING, index, NUM);
      if (CollectionUtils.isNotEmpty(pins)) {
//                logger.info(Arrays.toString(pins.toArray()));  
        if (pins.size() < NUM) {
          break;
        }
      } else {
        break;
      }
      index += NUM;
    }
    logger.info(((System.currentTimeMillis() - start) / 1000) + "");
  }
}  