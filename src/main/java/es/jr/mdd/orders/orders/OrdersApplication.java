package es.jr.mdd.orders.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jr.mdd.orders.model.Order;
import es.jr.mdd.orders.orders.service.OrderService;
import lombok.extern.log4j.Log4j2;

@EnableBinding(Processor.class)
@Log4j2
@SpringBootApplication
@ComponentScan("es.jr.mdd")
public class OrdersApplication {

	@Autowired
    OrderService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

	@StreamListener(Processor.INPUT)
    public void receiveOrder(Order order) throws JsonProcessingException {
        log.info("Order received: {}", mapper.writeValueAsString(order));
        service.process(order);
    }
}
