package com.xs.rongly.framework.stater.core.base.autoJsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/12 15:27
 * @Version: 1.0
 * modified by:
 */
@Configuration
@ConditionalOnBean({ObjectMapper.class})
//在指定的配置类初始化后再加载
@AutoConfigureAfter(
        name = {"org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration"}
)
public class JsonConfig {

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Bean
    public ObjectMapperJson objectMapperJson(){
        return new ObjectMapperJson(objectMapper);
    }

    @Bean
    public JacksonXml jacksonXml(){
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        return new JacksonXml(xmlMapper);
    }
}
