package arv.springdockerrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ArvikV
 * @version 1.0
 * @since 05.05.2022
 * Издатель -producer
 */
@RestController
public class ProducerController {
    Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final RabbitTemplate template;

    public ProducerController(RabbitTemplate template) {
        this.template = template;
    }


    /**
     * добавлен цикл из случайных чисел которые мы будем сыпать в очередь
     * @param message сообщение, на данной проверке не важно
     * @return на выходе получаем сообщение об успехе
     * template.setExchange("выбрать необходимое из нижеперечисленного")
     * common-exchange
     * direct-exchange
     * topic-exchange
     */
    @PostMapping("/message")
    public ResponseEntity<String> sendingMessage(@RequestBody String message) {
        logger.info("Sending message to myQueue");
        template.setExchange("common-exchange");
        template.convertAndSend("myQueue", message);
        return ResponseEntity.ok("Success of sending message");
    }
}
