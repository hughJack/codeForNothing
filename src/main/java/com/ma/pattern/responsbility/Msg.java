/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.pattern.responsbility;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Msg {

  public static void main(String[] args) {
    Msg msg = new Msg();
    msg.setMsg("Hello world,<script>,:),996 bad guy from http://www.github.com");
    msg.setName("yangguan");
    // 责任链1
    FilterChain filterChain = new FilterChain();
    // 链式编程???
    filterChain.add(new HtmlFilter()).add(new SenstiveFilter());
    // 责任链2
    FilterChain filterChain2 = new FilterChain();
    filterChain2.add(new FaceFilter()).add(new UrlFilter());
    filterChain.add(filterChain2);
    // 遍历责任链
    filterChain.doFilter(msg);
    System.out.println(msg);
  }

  String name;
  String msg;

  @Override
  public String toString() {
    return "Msg{" +
        "msg='" + msg + '\'' +
        '}';
  }
}

interface Filter {

  void doFilter(Msg msg);
}

class HtmlFilter implements Filter {

  @Override
  public void doFilter(Msg msg) {
    String replace = msg.getMsg();
    replace = replace.replaceAll("<", "[");
    replace = replace.replaceAll(">", "]");
    msg.setMsg(replace);

  }
}

class SenstiveFilter implements Filter {

  @Override
  public void doFilter(Msg msg) {
    String replace = msg.getMsg();
    replace = replace.replaceAll("996", "007");
    msg.setMsg(replace);
  }
}

class FaceFilter implements Filter {

  @Override
  public void doFilter(Msg msg) {
    String replace = msg.getMsg();
    replace = replace.replaceAll(":", "V");
    msg.setMsg(replace);
  }
}

class UrlFilter implements Filter {

  @Override
  public void doFilter(Msg msg) {
    String replace = msg.getMsg();
    replace = replace.replaceAll("http", "https");
    msg.setMsg(replace);
  }
}

@Data
class FilterChain implements Filter {

  List<Filter> filters = new ArrayList<>();

  // 如果实现了Filter接口, 那么也可以实现添加  另外一个链条, 因为List支持
  public FilterChain add(Filter f) {
    filters.add(f);
    return this;
  }

  @Override
  public void doFilter(Msg msg) {
    for (Filter filter : filters) {
      filter.doFilter(msg);
    }
  }
}
