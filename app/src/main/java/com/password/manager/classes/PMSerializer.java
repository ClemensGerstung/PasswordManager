package com.password.manager.classes;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Clemens on 27.11.2014.
 */
public class PMSerializer {
    public static <T> String serialize(T clazz, User user) throws Exception {
        Serializer serializer = new Persister(new AnnotationStrategy());

        StringWriter writer = new StringWriter();
        serializer.write(clazz, writer);

        return writer.toString();
    }

    public static <T> T deserialize(String data, Class<? extends T> aClass) throws Exception {
        Serializer serializer = new Persister(new AnnotationStrategy());
        StringReader stringReader = new StringReader(data);

        return (T) serializer.read(aClass.getClass(), stringReader);
    }

}
