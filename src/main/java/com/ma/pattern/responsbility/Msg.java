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
    msg.setMsg("Hello <world>, 996 bad guy");
    msg.setName("yangguan");
    // 责任链模式
    List<Filter> filters = new ArrayList<>();
    filters.add(new HtmlFilter());
    filters.add(new SenstiveFilter());
    // 责任链模式
    for (Filter filter : filters) {
      filter.doFilter(msg);
    }
    //
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

