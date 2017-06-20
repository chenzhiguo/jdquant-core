package com.jd.quant.core.main;

import com.jd.quant.core.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Import;

/**
 * JDQuant Core Application Bootloader
 *
 * @author Zhiguo.Chen
 */
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = {"com.jd.quant.core"})
public class JdquantCoreWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdquantCoreWebApplication.class, args);
    }
}
