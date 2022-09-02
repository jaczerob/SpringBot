package com.github.jaczerob.springbot.web.configurations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jaczerob.springbot.web.configurations.interceptors.RequestLoggingInterceptor;

@Configuration
public class Interceptors implements WebMvcConfigurer {
  private static final Logger logger = LogManager.getLogger(Interceptors.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    logger.info("registering interceptors");

    addInterceptorVarargs(registry, 
        new RequestLoggingInterceptor()
    );
  }

  private void addInterceptorVarargs(InterceptorRegistry registry, HandlerInterceptor... interceptors) {
    for (HandlerInterceptor interceptor : interceptors) {
      registry.addInterceptor(interceptor);
      logger.info("registered {}", interceptor.getClass().getSimpleName());
    }
  }
}
