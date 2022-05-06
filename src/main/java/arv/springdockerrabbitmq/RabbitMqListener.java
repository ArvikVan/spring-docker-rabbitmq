package arv.springdockerrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ArvikV
 * @version 1.0
 * @since 05.05.2022
 * второй вариант получение листенера
 */
@Component
@EnableRabbit
public class RabbitMqListener {
    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    /**
     * метод с помощью которого будем принимать сообщение
     * в параметрах аннотации указываем название очереди с которой должно придти сообщение
     */
    @RabbitListener(queues = "myQueue")
    public void processMyQueue(String message) {
        logger.info("Received from first myQueue with @RabbitListener: {} ", message);
    }

    /**
     * случай когда на одну очередь подписано два слушателя
     * @param message сообщение для вывода
     */
    @RabbitListener(queues = "myQueue")
    public void processMyQueue2(String message) {
        logger.info("Received from second myQueue with @RabbitListener: {} ", message);
    }
}
