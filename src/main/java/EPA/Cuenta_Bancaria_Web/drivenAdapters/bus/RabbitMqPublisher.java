package EPA.Cuenta_Bancaria_Web.drivenAdapters.bus;

import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqPublisher {

    @Autowired
    private Sender sender;

    @Autowired
    private Gson gson;

    public void publishMessage(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(RabbitConfig.EXCHANGE_NAME,
                        RabbitConfig.ROUTING_KEY_NAME, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishError(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(RabbitConfig.EXCHANGE_NAME,
                        RabbitConfig.ROUTING_ERROR_KEY_NAME, gson.toJson(object).getBytes()))).subscribe();
    }
}
