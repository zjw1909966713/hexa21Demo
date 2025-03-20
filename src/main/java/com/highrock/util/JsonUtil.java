package com.highrock.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtil
 * @Author: zjw
 * @Description: jackson json工具
 * @Date: 2021/02/04 16:13
 * @Version: 1.0
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    // 日起格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8日期时间API
    }

    private JsonUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 获取配置好的ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Java对象转换为JSON字符串
     *
     * @param object Java对象
     * @return JSON字符串
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转JSON字符串失败", e);
        }
    }

    /**
     * Java对象转换为格式化的JSON字符串
     *
     * @param object Java对象
     * @return 格式化的JSON字符串
     */
    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转格式化JSON字符串失败", e);
        }
    }

    /**
     * JSON字符串转换为Java对象
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   泛型参数
     * @return Java对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串转对象失败", e);
        }
    }

    /**
     * JSON字符串转换为复杂类型对象
     *
     * @param json          JSON字符串
     * @param typeReference 类型引用
     * @param <T>           泛型参数
     * @return Java对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串转复杂对象失败", e);
        }
    }

    /**
     * JSON字符串转换为List
     *
     * @param json  JSON字符串
     * @param clazz 目标元素类型
     * @param <T>   泛型参数
     * @return List对象
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串转List失败", e);
        }
    }

    /**
     * JSON字符串转换为Map
     *
     * @param json JSON字符串
     * @return Map对象
     */
    public static Map<String, Object> toMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串转Map失败", e);
        }
    }

    /**
     * JSON字符串转换为JsonNode
     *
     * @param json JSON字符串
     * @return JsonNode对象
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串转JsonNode失败", e);
        }
    }

    /**
     * 从文件中读取JSON并转换为Java对象
     *
     * @param file  JSON文件
     * @param clazz 目标类型
     * @param <T>   泛型参数
     * @return Java对象
     */
    public static <T> T fromFile(File file, Class<T> clazz) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("从文件读取JSON失败", e);
        }
    }

    /**
     * 从输入流中读取JSON并转换为Java对象
     *
     * @param inputStream 输入流
     * @param clazz       目标类型
     * @param <T>         泛型参数
     * @return Java对象
     */
    public static <T> T fromInputStream(InputStream inputStream, Class<T> clazz) {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new RuntimeException("从输入流读取JSON失败", e);
        }
    }

    /**
     * 将Java对象转换为字节数组
     *
     * @param object Java对象
     * @return 字节数组
     */
    public static byte[] toBytes(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转字节数组失败", e);
        }
    }

    /**
     * 将字节数组转换为Java对象
     *
     * @param bytes 字节数组
     * @param clazz 目标类型
     * @param <T>   泛型参数
     * @return Java对象
     */
    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException("字节数组转对象失败", e);
        }
    }

    /**
     * 对象转换，将源对象转换为目标类型
     *
     * @param fromValue   源对象
     * @param toValueType 目标类型
     * @param <T>         泛型参数
     * @return 目标类型对象
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    /**
     * 更新对象，将源JSON部分更新到目标对象
     *
     * @param json   源JSON字符串
     * @param object 目标对象
     * @param <T>    泛型参数
     * @return 更新后的对象
     */
    public static <T> T update(String json, T object) {
        try {
            return objectMapper.readerForUpdating(object).readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("更新对象失败", e);
        }
    }


}
