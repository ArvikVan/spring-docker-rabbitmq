package arv.springdockerrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ArvikV
 * @version 1.0
 * @since 05.05.2022
 * Издатель -producer
 */
@RestController
public class ProducerController {
    Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final AmqpTemplate template;

    public ProducerController(AmqpTemplate template) {
        this.template = template;
    }

    /**
     * добавлен цикл из случайных чисел которые мы будем сыпать в очередь
     * @param message сообщение, на данной проверке не важно
     * @return на выходе получаем сообщение об успехе
     */
    @PostMapping("/message")
    public ResponseEntity<String> sendingMessage(@RequestBody String message) {
        logger.info("Sending message to myQueue");
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("myQueue", ThreadLocalRandom.current().nextInt());
        }
        return ResponseEntity.ok("Success of sending message");
    }
}
