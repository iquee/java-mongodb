package com.luiztaira.m101j;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ique on 18/10/16.
 */
public class HelloSparkFreemarker {
    public static void main(String[] args) {

        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(
                HelloSparkFreemarker.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {

                StringWriter writer = new StringWriter();

                try {
                    Template tmp = config.getTemplate("helloFreemarker.ftl");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", "Mane");
                    tmp.process(map, writer);

                    System.out.println(writer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
