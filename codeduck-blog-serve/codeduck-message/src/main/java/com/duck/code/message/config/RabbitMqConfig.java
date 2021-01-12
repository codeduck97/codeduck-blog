package com.duck.code.message.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: codeduck-blog-serve
 * @description: RabbitMQ 配置文件
 * @author: Code Duck
 * @create: 2021-01-09 21:00
 */
@Configuration
public class RabbitMqConfig {

    public static final String CODEDUCK_BLOG = "codeduck.blog";
    public static final String CODEDUCK_EMAIL = "codeduck.email";
    public static final String CODEDUCK_MSG = "codeduck.msg";
    public static final String EXCHANGE_DIRECT = "exchange.direct";
    public static final String ROUTING_KEY_BLOG = "codeduck.blog";
    public static final String ROUTING_KEY_EMAIL = "codeduck.email";
    public static final String ROUTING_KEY_SMS = "codeduck.sms";

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(true).build();
    }

    /**
     * 声明Blog队列
     *
     * @return
     */
    @Bean(CODEDUCK_BLOG)
    public Queue CODEDUCK_BLOG() {
        return new Queue(CODEDUCK_BLOG);
    }

    /**
     * 声明Email队列
     *
     * @return
     */
    @Bean(CODEDUCK_EMAIL)
    public Queue CODEDUCK_EMAIL() {
        return new Queue(CODEDUCK_EMAIL);
    }

    /**
     * 声明SMS队列
     *
     * @return
     */
    @Bean(CODEDUCK_MSG)
    public Queue CODEDUCK_MSG() {
        return new Queue(CODEDUCK_MSG);
    }

    /**
     * mogu.blog 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_BLOG(@Qualifier(CODEDUCK_BLOG) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_BLOG).noargs();
    }

    /**
     * mogu.mail 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(CODEDUCK_EMAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    /**
     * mogu.sms 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(CODEDUCK_MSG) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

