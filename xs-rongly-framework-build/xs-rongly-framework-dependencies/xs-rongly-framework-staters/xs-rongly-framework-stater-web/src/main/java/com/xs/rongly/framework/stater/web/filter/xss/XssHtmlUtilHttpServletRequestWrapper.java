package com.xs.rongly.framework.stater.web.filter.xss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 通过htmlutil防御xss
 */
@Slf4j
public class XssHtmlUtilHttpServletRequestWrapper extends HttpServletRequestWrapper {

  //判断是否是上传 上传忽略
  boolean isUpData = false;

  public XssHtmlUtilHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
    String contentType = request.getContentType();
    if (null != contentType) {
      isUpData = contentType.startsWith("multipart");
    }
  }

  /**
   * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
   * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
   * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
   */
  @Override
  public String getParameter(String name) {
    String value = super.getParameter(name);
    log.debug("过滤前的值:{}",value);
    if (value != null) {
      value = HtmlUtils.htmlEscape(value);
    }
    log.debug("过滤后的值:{}",value);
    return value;
  }

  /**
   * 覆盖getParameterValues方法
   * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
   */
  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    log.debug("过滤前的值:{}",values);
    if (ArrayUtils.isNotEmpty(values)) {
      values = Stream.of(values).map(s -> HtmlUtils.htmlEscape(s)).toArray(String[]::new);
    }
    log.debug("过滤后的值:{}",values);
    return values;
  }

  /**
   * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
   * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
   * getHeaderNames 也可能需要覆盖
   */
  @Override
  public String getHeader(String name) {
    String value = super.getHeader(name);
    if (value != null) {
      value = HtmlUtils.htmlEscape(value);
    }
    return value;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (isUpData) {
      return super.getInputStream();
    } else {
      //处理原request的流中的数据
      byte[] bytes = IOUtils.toByteArray(super.getInputStream());
      final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      return new ServletInputStream() {
        @Override
        public int read()  {
          return bais.read();
        }
        @Override
        public boolean isFinished() {
          return false;
        }

        @Override
        public boolean isReady() {
          return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
          
        }
      };
    }

  }


}