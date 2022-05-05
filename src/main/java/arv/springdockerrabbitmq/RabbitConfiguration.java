package arv.springdockerrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ArvikV
 * @version 1.0
 * @since 05.05.2022
 * получатель - consumer
 */
@Configuration
public class RabbitConfiguration {
    Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    /**
     * устанавливаем соединение
     * @return указываем локалхост
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    /**
     * алмин для управления обменником
     * @return одменко
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * шаблон раббита
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * очередь
     */
    @Bean
    public Queue queue() {
        return new Queue("myQueue");
    }

    /**
     * самое интересное, мой получатель
     * @return контейнер
     * указываем коннект - название очереди - и сообщение при его получении
     * один из способов получения листенера
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("myQueue");
        container.setMessageListener(message -> logger.info("Received message from myQueue: "
                + new String(message.getBody()) ));
        return container;
    }
}
