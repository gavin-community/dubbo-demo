package com.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
@ImportResource({ "classpath:dubbo.xml" })
public class ProducerApplication {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
        log.info("***********ProducerApplication start success***********");
        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            log.warn("COUNT_DOWN_LATCH interrupted", e);
        }
    }
}
