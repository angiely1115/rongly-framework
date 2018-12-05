package com.xs.rongly.framework.stater.core.base.autoJsonConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.xs.rongly.framework.stater.core.base.BaseResult;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author: lvrongzhuan
 * @Description:json处理
 * @Date: 2018/7/12 15:28
 * @Version: 1.0
 * modified by:
 */
public class ObjectMapperJson {
    private ObjectMapper objectMapper;

    public ObjectMapperJson(ObjectMapper objectMapper) {
        // 禁用遇到未知属性抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 对于空的对象转json的时候不抛出错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.objectMapper = objectMapper;
    }
    public <T> String obj2string(T t) {
        try {
            return this.objectMapper.writeValueAsString(t);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public <T> String obj2string(T t, Class<?> serializationView) {
        try {
            return this.objectMapper.writerWithView(serializationView).writeValueAsString(t);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> List<T> str2list(String jsonStr, Class<T> cls) {
        try {
            JavaType t = this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{cls});
            return (List)this.objectMapper.readValue(jsonStr, t);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> T str2obj(String jsonStr, TypeReference typeReference) {
        try {
            return this.objectMapper.readValue(jsonStr, typeReference);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> T str2obj(String jsonStr, Class<T> cls) {
        try {
            return this.objectMapper.readValue(jsonStr, cls);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public JsonNode str2node(String jsonStr) {
        try {
            return this.objectMapper.readTree(jsonStr);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public <T> T readAs(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return this.objectMapper.readValue(bytes, typeReference);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> TypeReference<BaseResult<T>> getReference(Class<T> clz) {
        Type[] types = new Type[]{clz};
        final ParameterizedTypeImpl type = ParameterizedTypeImpl.make(BaseResult.class, types, BaseResult.class.getDeclaringClass());
        return new TypeReference<BaseResult<T>>() {
            public Type getType() {
                return type;
            }
        };
    }
}
