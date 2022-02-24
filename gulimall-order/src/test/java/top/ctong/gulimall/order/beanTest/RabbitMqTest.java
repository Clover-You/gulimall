package top.ctong.gulimall.order.beanTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.order.User;

import java.util.HashMap;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀     ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒      ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░      ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄      ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄     ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒     ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 * Copyright 2022 Clover You.
 * <p>
 * RabbitMQ测试
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-21 8:58 下午
 */
@DisplayName("RabbitMQ use test")
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @DisplayName("rabbit loading status")
    @BeforeEach
    public void importRabbitTest() {
        assert rabbitTemplate != null : "RabbitMQ initialization error...";
        assert amqpAdmin != null : "amqp initialization error...";
    }

    @DisplayName("create exchange test")
    @Test
    public void createExchangeTest() {
        TopicExchange topicExchange = new TopicExchange(
            "hello.java.TopicExchange",
            true,
            false
        );
        amqpAdmin.declareExchange(topicExchange);
    }

    @DisplayName("create queue test")
    @Test
    public void createQueue() {
        Queue queue = new Queue(
            "hello.java.queue",
            true,
            false,
            false,
            new HashMap<>()
        );
        amqpAdmin.declareQueue(queue);
    }

    @Test
    @DisplayName("create binding test")
    public void createBinding() {
        Binding binding = new Binding(
            "hello.java.queue",
            Binding.DestinationType.QUEUE,
            "hello.java.TopicExchange",
            "hello.java.queue",
            new HashMap<>()
        );
        amqpAdmin.declareBinding(binding);
    }

    @Test
    @DisplayName("send message test")
    public void sendMessage() {
//        String halo = "hello world";
//        Message message = new Message(halo.getBytes());
//        rabbitTemplate.send("hello.java.queue", message);
        for (int i = 0; i < 4; i++) {
            User user = new User();
            user.setName("Clover You");
            rabbitTemplate.convertAndSend(
                "hello.java.TopicExchange",
                "hello.java.queue",
                user
            );
        }

    }

    @Test
    @DisplayName("get rabbit mq message test by rabbit template for spring provide")
    public void getMessage() {

    }
}

