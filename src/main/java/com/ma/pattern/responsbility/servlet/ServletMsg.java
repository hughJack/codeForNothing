/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.pattern.responsbility.servlet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class ServletMsg {

  public static void main(String[] args) {
    ServletMsg msg = new ServletMsg();
    Request request = new Request();
    request.setRequest("request");
    Response response = new Response();
    response.setResponse("response");
    // 责任链
    FilterChain filterChain = new FilterChain();
    filterChain.add(new HtmlFilter()).add(new SenstiveFilter());
    // 遍历责任链
    filterChain.doFilter(request, response, filterChain);

    System.out.println(request.getRequest());
    System.out.println(response.getResponse());
  }

  String name;
  String msg;

  @Override
  public String toString() {
    return "ServletMsg{" +
        "msg='" + msg + '\'' +
        '}';
  }
}

interface Filter {

  // 实现链式执行的终止,  只有为true的时候,  可以继续执行
  boolean doFilter(Request request, Response response, FilterChain filterChain);
}

class HtmlFilter implements Filter {


  @Override
  public boolean doFilter(Request request, Response response, FilterChain filterChain) {
    request.setRequest(request.getRequest().concat("html"));
    filterChain.doFilter(request, response, filterChain);// 就是指定下一个处理器是谁
    response.setResponse(response.getResponse().concat("html"));
    return true;
  }
}

class SenstiveFilter implements Filter {

  @Override
  public boolean doFilter(Request request, Response response, FilterChain filterChain) {
    request.setRequest(request.getRequest().concat("Senstive"));
    filterChain.doFilter(request, response, filterChain);
    response.setResponse(response.getResponse().concat("Senstive"));
    return false;
  }
}


@Data
class FilterChain implements Filter {

  private List<Filter> filters = new ArrayList<>();
  // private LinkedList filters =  new LinkedList<>();
  private int index = 0;


  // 如果实现了Filter接口, 那么也可以实现添加  另外一个链条, 因为List支持
  public FilterChain add(Filter f) {
    this.filters.add(f);
    return this;
  }


  @Override
  public boolean doFilter(Request request, Response response, FilterChain filterChain) {
    if (index == filters.size()) {
      return false;
    }
    Filter filter = filters.get(index++);
    return filter.doFilter(request, response, filterChain);
  }
}

@Data
class Request {

  String request;
}

@Data
class Response {

  String response;
}

