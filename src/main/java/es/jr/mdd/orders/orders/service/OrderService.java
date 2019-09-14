package es.jr.mdd.orders.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jr.mdd.orders.model.Order;
import es.jr.mdd.orders.model.OrderStatus;
import es.jr.mdd.orders.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderService {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    OrderRepository repository;
	
	public void process(final Order order) throws JsonProcessingException {
		log.info("Order processed: {}", mapper.writeValueAsString(order));
		Order o = repository.findById(order.getId());
		if (o.getStatus() != OrderStatus.REJECTED) {
			o.setStatus(order.getStatus());
			repository.update(o);
			log.info("Order status updated: {}", mapper.writeValueAsString(order));
		}
	}
	
}
