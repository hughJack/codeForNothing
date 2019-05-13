/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.pattern.Observer;

import lombok.Data;

@Data
public class Recorder {

  private String domain;
  private String ip;
  private String owner;

}
