package com.dljd.mail.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类编写范例，使用重载编写不同参数类型的函数组
 *
 * http://blog.didispace.com/cxy-wsm-zml-10/
 * 
 * @author 晓风轻
 *
 * https://github.com/xwjie/PLMCodeTemplate
 *
 */
public class FileUtil {

  private static final String DEFAULT_CHARSET = "UTF-8";

  public static List<String> readFile2List(String filename) throws IOException {
    return readFile2List(filename, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(String filename, String charset)
    throws IOException {
    FileInputStream fileInputStream = new FileInputStream(filename);
    return readFile2List(fileInputStream, charset);
  }

  public static List<String> readFile2List(File file) throws IOException {
    return readFile2List(file, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(File file, String charset)
    throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    return readFile2List(fileInputStream, charset);
  }

  public static List<String> readFile2List(InputStream fileInputStream)
    throws IOException {
    return readFile2List(fileInputStream, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(InputStream inputStream, String charset)
    throws IOException {
    List<String> list = new ArrayList<String>();

    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(inputStream, charset));

      String s = null;
      while ((s = br.readLine()) != null) {
        list.add(s);
      }
    } finally {
//      IOUtils.closeQuietly(br);
      br.close();
    }

    return list;
  }




//  public static List<String> readFile2List(String filename) throws IOException {
//    List<String> list = new ArrayList<String>();
//
//    File file = new File(filename);
//
//    FileInputStream fileInputStream = new FileInputStream(file);
//
//    BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream,
//            "UTF-8"));
//
//    // XXX操作
//
//    return list;
//  }

}