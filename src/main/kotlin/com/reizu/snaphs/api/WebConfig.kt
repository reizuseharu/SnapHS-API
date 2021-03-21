package com.reizu.snaphs.api

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("*")
            .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                "Access-Control-Request-Headers")
            .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
    }
}
