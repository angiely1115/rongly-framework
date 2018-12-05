package com.xs.rongly.framework.stater.core.base.autoJsonConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/12 16:19
 * @Version: 1.0
 * modified by:
 */
public class JacksonXml {
    private XmlMapper xmlMapper;
    public JacksonXml(XmlMapper xmlMapper) {
        // 禁用遇到未知属性抛出异常
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 对于空的对象转json的时候不抛出错误
        xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.xmlMapper = xmlMapper;
    }
    public  String objToxml(Object object) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(object);
    }

    public  <T> T xmlToObject(String strxml, TypeReference<T> z) throws IOException {
        return xmlMapper.readValue(strxml,z);
    }
}
