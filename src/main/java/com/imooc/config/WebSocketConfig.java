package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import javax.websocket.server.ServerContainer;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:14 2019/9/3
 */
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
