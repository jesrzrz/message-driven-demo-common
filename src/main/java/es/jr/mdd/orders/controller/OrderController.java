package es.jr.mdd.orders.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jr.mdd.orders.controller.messaging.OrderSender;
import es.jr.mdd.orders.model.Order;
import es.jr.mdd.orders.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("orders")
@Log4j2
public class OrderController {

	@Autowired
    OrderRepository repository;
    @Autowired
    OrderSender sender;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @PostMapping
    public Order process(@RequestBody Order order) throws JsonProcessingException {
        Order o = repository.add(order);
        log.info("Order saved: {}", mapper.writeValueAsString(order));
        boolean isSent = sender.send(o);
        log.info("Order sent: {}", mapper.writeValueAsString(Collections.singletonMap("isSent", isSent)));
        return o;
    }
    
    @GetMapping("/{id}")
    public Order findById(@PathVariable("id") Long id) {
        return repository.findById(id);
    }
	
}
