package cn.zhangxd.platform.system.provider.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

	@Autowired
    private RabbitTemplate rabbitTemplate;

	@Scheduled(fixedDelay = 10000L)
	public void send() {
		this.rabbitTemplate.convertAndSend("foo", "hello");
	}

}