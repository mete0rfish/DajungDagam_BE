package com.dajungdagam.dg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

// 1. 당장 DB를 사용하지 않고 스프링 부트를 쓸 경우, exclude로 DataSource 클래스 호출을 제외시킨다.
// 2. DB를 사용하는 경우, application.properties 파일이나 application.yml 파일에 DataSource 정보를 정의한다.
@EnableJpaAuditing
@SpringBootApplication
public class DgApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgApplication.class, args);
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}
