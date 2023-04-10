package com.example.news_api.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration{

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext){
        MappingMongoConverter mmc=super.mappingMongoConverter(databaseFactory,customConversions,mappingContext);
        mmc.setTypeMapper(defaultMongoTypeMapper());
        return mmc;
    }
    @Bean
    public MongoTypeMapper defaultMongoTypeMapper(){
        return new DefaultMongoTypeMapper(null);
    }

    @Override
    protected String getDatabaseName() {
        return "news";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        List<ServerAddress> hosts = new ArrayList<>();
        //参数为 数据库地址 端口号有多个可以添加
        hosts.add(new ServerAddress("127.0.0.1", 27017));
        //参数为 用户名 数据库名 数据库密码
        builder.applyToClusterSettings(settings -> {
                    settings.hosts(hosts);
                });
    }
}
