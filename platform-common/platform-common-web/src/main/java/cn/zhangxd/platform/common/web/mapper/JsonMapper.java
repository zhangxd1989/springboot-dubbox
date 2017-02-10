package cn.zhangxd.platform.common.web.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;

/**
 * 自定义 Spring MVC 序列化方式
 *
 * @author zhangxd
 */
public class JsonMapper extends ObjectMapper {

    public JsonMapper() {
        this(Include.ALWAYS);
    }

    public JsonMapper(Include include) {
        // 设置输出时包含属性的风格
        if (include != null) {
            this.setSerializationInclusion(include);
        }
        // Json 格式定义
        this.enableSimple();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen,
                                  SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });
        // 进行HTML解码。
        this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator jgen,
                                  SerializerProvider provider) throws IOException {
                jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
            }
        }));
    }

    /**
     * 不允许单引号
     * 不允许不带引号的字段名称
     * 数字转换为字符串格式
     * 禁止重复字段
     *
     * @return the json mapper
     */
    public JsonMapper enableSimple() {
        this
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false)
            .configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true)
            .configure(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION, true)
        ;
        return this;
    }

}
