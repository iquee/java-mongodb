package com.luiztaira.m101j;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ique on 18/10/16.
 */
public class HelloFreemarker {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(
                HelloFreemarker.class, "/");

        try {
            Template tmp = config.getTemplate("helloFreemarker.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "Mane");
            tmp.process(map, writer);

            System.out.println(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
