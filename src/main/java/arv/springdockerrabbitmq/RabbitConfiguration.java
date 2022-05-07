package arv.springdockerrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
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
    /*@Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("myQueue");
        container.setMessageListener(message -> logger.info("Received message from myQueue: "
                + new String(message.getBody()) ));
        return container;
    }*/

    /**
     * создаем вторую очередь
     * @return очередь
     */
    @Bean
    public Queue queue2() {
        return new Queue("myQueue2");
    }

    /**
     * создаем обменник, чтоб каждый слушатель получил одно сообщение сообщение, создаем свою очередь и
     * ОБМЕННИК, в который мы посылаем общее сообщение, при том что между очередью есть связки(Binding)
     * @return обменник
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("common-exchange");
    }

    /**
     * Связка для первой очереди
     * @return свЯЗЬ
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    /**
     * Связка для второй очереди
     * @return свЯЗЬ
     */
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

    /**
     * для того чтобы каждый листенер отвечал за свое поле деятельности
     * @return директ на выходе
     * также необходим роутинг
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    /**
     * указываем ключи с которым будет идти сообщение,
     * по этому ключу будет определяться в какую очередь полетит сообщение, чтобы не перекладывать заботу
     * об обработке на самого отправителя
     * @return ключ
     * к обменнику привязываем очередь с ключом
     */
    @Bean
    public Binding bindingDE() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("error");
    }
}
