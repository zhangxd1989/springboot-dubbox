package cn.zhangxd.platform.system.provider.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Date;

@Configuration
@RabbitListener(queues = "foo")
public class SampleAmqpSimpleApplication {

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public Sender mySender() {
        return new Sender();
    }

    @Bean
    public Queue fooQueue() {
        return new Queue("foo");
    }

    @RabbitHandler
    public void process(@Payload String foo) {
        //邮件发送
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("aaa@xxx.com");
//        message.setTo("bbb@xxx.com");
//        message.setSubject("主题：测试邮件");
//        message.setText("测试邮件内容" + new Date() + ": " + foo);
//        mailSender.send(message);

        System.out.println(new Date() + ": " + foo);
    }

}