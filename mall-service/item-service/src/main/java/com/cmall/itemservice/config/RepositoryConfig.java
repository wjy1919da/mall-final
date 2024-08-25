package com.cmall.itemservice.config;

import com.cmall.common.config.ModelMapperConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
@Import(ModelMapperConfig.class)
@EnableSpringDataWebSupport
public class RepositoryConfig implements PageableHandlerMethodArgumentResolverCustomizer {
    @Override
    public void customize(PageableHandlerMethodArgumentResolver resolver) {
        resolver.setOneIndexedParameters(true); // 设置为 true 如果你想页数从 1 开始计数
        resolver.setFallbackPageable(PageRequest.of(0, 3)); // 设置默认的分页参数
    }
}
