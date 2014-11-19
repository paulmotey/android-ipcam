package cz.janchvala.android.ipcamera.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Jackson json object mapper set for ignoring unknown fields
 * <p/>
 * Created by jan on 10.11.2014.
 */
public class JacksonJsonConverter extends MappingJackson2HttpMessageConverter {

    public JacksonJsonConverter() {
        super();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setObjectMapper(mapper);
    }

}
