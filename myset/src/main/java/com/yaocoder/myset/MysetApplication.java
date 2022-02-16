package com.yaocoder.myset;

import com.yaocoder.myset.CustomConfiguration.SpringUtil;
import com.yaocoder.myset.common.SessionCommon;
import com.yaocoder.myset.netty.WebSocketServer;
import com.yaocoder.myset.netty1.NettyServer;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetSocketAddress;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@Import({SpringUtil.class,SessionCommon.class})
//@ComponentScan("com.yaocoder.myset")
@Import({SpringUtil.class})
@SpringBootApplication
@EnableScheduling
public class MysetApplication implements CommandLineRunner {
//public class MysetApplication{

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private WebSocketServer server;

    public static void main(String[] args) {
        SpringApplication.run(MysetApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(url,port);
        System.out.println("run  .... . ... "+url);
        server.start(address);
    }


//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        configurableEmbeddedServletContainer.setPort(8003);
//    }
}
