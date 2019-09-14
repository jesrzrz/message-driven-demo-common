package es.jr.mdd.orders.model;

import java.util.List;

import lombok.Data;

@Data
public class Order {
	private Long id;
	private OrderStatus status;
	private int price;
	private Long customerId;
	private Long accountId;
	private List<Long> productIds;
}
