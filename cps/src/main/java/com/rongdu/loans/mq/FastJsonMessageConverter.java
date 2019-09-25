package com.rongdu.loans.mq;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.JsonMapper;


@Service("jsonMessageConverter")
public class FastJsonMessageConverter extends AbstractMessageConverter {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String DEFAULT_CHARSET = "UTF-8";

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter() {
        super();
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset : DEFAULT_CHARSET;
    }

    public Object fromMessage(Message message) throws MessageConversionException {
    	CommonMessage cm = fromMessage(message,CommonMessage.class);
        return cm;
    }

    @SuppressWarnings("unchecked")
	public <T> T fromMessage(Message message, Class<T>  clazz) {
    	String jsonString = null;
    	T object = null;
    	try {
    		jsonString = new String(message.getBody(), defaultCharset);
			object = (T)JsonMapper.fromJsonString(jsonString, clazz);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return object;
    }

    protected Message createMessage(Object objectToConvert,MessageProperties messageProperties)throws MessageConversionException {
        byte[] bytes = null;
        try {
            String jsonString = JsonMapper.toJsonString(objectToConvert);
            bytes = jsonString.getBytes(this.defaultCharset);
        } catch (UnsupportedEncodingException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(this.defaultCharset);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(bytes, messageProperties);

    }
}