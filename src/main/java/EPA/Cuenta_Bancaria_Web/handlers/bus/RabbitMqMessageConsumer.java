package EPA.Cuenta_Bancaria_Web.handlers.bus;


import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

@Component
public class RabbitMqMessageConsumer implements CommandLineRunner {

    @Autowired
    private Receiver receiver;

    @Autowired
    private Gson gson;

    @Autowired
    I_Repositorio_TransaccionMongo repository;

    @Override
    public void run(String... args) throws Exception {
        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME)
                .map(message -> {
                    M_Cuenta_DTO transaction = gson
                            .fromJson(new String(message.getBody()),
                                    M_Cuenta_DTO.class);

                    System.out.println("La cuenta creada fue:  " + transaction);
                    return transaction;
                }).subscribe();

        receiver.consumeAutoAck(RabbitConfig.QUEUE_ERROR_CREATING_ACCOUNT)
                .map(message -> {
                    M_TransaccionMongo transaction = gson
                            .fromJson(new String(message.getBody()),
                                    M_TransaccionMongo.class);

                    repository.deleteById(transaction.getId()).subscribe();

                    System.out.println("La transaccion restablecida fue:  " + transaction);
                    return transaction;
                }).subscribe();
    }
}
