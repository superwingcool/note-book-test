package com.wing.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@Configuration
public class HbaseAutoConfiguration {

    @Value("${spring.data.hbase.quorum}")
    private String ZK_URL;

    @Value("${spring.data.hbase.port}")
    private String ZK_PORT;

    @Bean
    public HbaseTemplate hbaseTemplate() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("hbase.zookeeper.quorum", ZK_URL);
        configuration.set("hbase.zookeeper.property.clientPort", ZK_PORT);
        HbaseTemplate hbaseTemplate = new HbaseTemplate(configuration);
        hbaseTemplate.setEncoding("UTF-8");
        return hbaseTemplate;
    }
}
