/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.pattern.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DnsServer extends Observable implements Observer {

  private DnsServer parentServer;

  /*
   * 描述 : method from Observer
   *
   */
  @Override
  public final void update(Observable o, Object arg) {
    Recorder recorder = (Recorder) arg;
    if (isLocal(recorder)) {
      // 本机可以解析
      recorder.setIp(IPUtil.generateIpAddr());
    } else {
      // 本机不能解析,那么提交给父服务器进行解析
      // 需要立即  执行,所以  changed  == true;
      super.setChanged();
      super.notifyObservers(recorder);
    }
    // 签名
    sign(recorder);// ????
  }

  public void setUpperServer(DnsServer dnsServer) {
    super.deleteObservers(); // ????
    super.addObserver(dnsServer);
  }

  // 需要子类特定的实现
  protected abstract void sign(Recorder recorder);

  // 需要子类特定的实现
  protected abstract boolean isLocal(Recorder recorder);
}

class Client {

  public static void main(String[] args) throws IOException {
    // 建立观察者 ???链???
    LocalDnsServer localDnsServer = new LocalDnsServer();
    ChinaDnsServer chinaDnsServer = new ChinaDnsServer();
    GlobalDnsServer globalDnsServer = new GlobalDnsServer();

    localDnsServer.setUpperServer(chinaDnsServer);
    chinaDnsServer.setUpperServer(globalDnsServer);
    //
    while (true) {
      System.out.println("请输入域名(按 N 退出):");
      String domain = new BufferedReader(new InputStreamReader(System.in)).readLine();
      if (domain.equalsIgnoreCase("n")) {
        return;
      }
      Recorder recorder = new Recorder();
      recorder.setDomain(domain);

      localDnsServer.update(null, recorder);
      System.out.println("-----解析结果-----");
      System.out.println(recorder);
    }
  }
}

class LocalDnsServer extends DnsServer {

  @Override
  protected void sign(Recorder recorder) {
    recorder.setOwner("本地服务器解析");
  }

  @Override
  protected boolean isLocal(Recorder recorder) {
    return recorder.getDomain().endsWith("bj.cn");
  }
}

class ChinaDnsServer extends DnsServer {

  @Override
  protected void sign(Recorder recorder) {
    recorder.setOwner("中国服务器解析");
  }

  @Override
  protected boolean isLocal(Recorder recorder) {
    return recorder.getDomain().endsWith(".cn");
  }
}

class GlobalDnsServer extends DnsServer {

  @Override
  protected void sign(Recorder recorder) {
    recorder.setOwner("全球服务器解析");
  }

  @Override
  protected boolean isLocal(Recorder recorder) {
    return true;
  }
}
