package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public  CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre Filter
        return (exchange, chain) -> {
            //exchange로부터 req,res를 얻을 수 있다.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom Pre Filter: request id -> {}", request.getId());

            //Custom Post Filter
            //chain을 이용해서 post filter를 추가하도록 하고, Mono가 비동기 방식 서버를 지원해준다.
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
               log.info("Custom Post Filter: response code -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config{

    }
}
