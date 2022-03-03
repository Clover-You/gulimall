//package top.ctong.gulimall.common.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//
///**
// * █████▒█      ██  ▄████▄   ██ ▄█▀     ██████╗ ██╗   ██╗ ██████╗
// * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒      ██╔══██╗██║   ██║██╔════╝
// * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░      ██████╔╝██║   ██║██║  ███╗
// * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄      ██╔══██╗██║   ██║██║   ██║
// * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄     ██████╔╝╚██████╔╝╚██████╔╝
// * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒     ╚═════╝  ╚═════╝  ╚═════╝
// * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
// * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
// * ░     ░ ░      ░  ░
// * Copyright 2022 Clover You.
// * <p>
// *
// * </p>
// * @author Clover You
// * @email 2621869236@qq.com
// * @create 2022-03-02 8:22 上午
// */
//@Configuration
//public class SeataConfig {
//    @Bean
//    public DataSource dataSource(DataSourceProperties properties) {
//        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if (StringUtils.hasText(properties.getName())) {
//            dataSource.setPoolName(properties.getName());
//        }
//        return new DataSourceProxy(dataSource);
//    }
//}
