package com.xs.rongly.framework.stater.web.filter.xss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
      byte[] bytes = inputHandlers(super.getInputStream()).getBytes();
      final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      return new ServletInputStream() {
        @Override
        public int read() throws IOException {
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

  public String inputHandlers(ServletInputStream servletInputStream) {
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
      String line = "";
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (servletInputStream != null) {
        try {
          servletInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    String finl = JsoupUtil.jsonStringConvert(sb.toString());
    finl = HtmlUtils.htmlEscape(finl);
    return finl;
  }

}