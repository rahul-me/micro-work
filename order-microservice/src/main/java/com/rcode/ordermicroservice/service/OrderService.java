package com.rcode.ordermicroservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.rcode.ordermicroservice.dto.InventoryResponse;
import com.rcode.ordermicroservice.dto.OrderLineItemsDto;
import com.rcode.ordermicroservice.dto.OrderRequest;
import com.rcode.ordermicroservice.event.OrderPlacedEvent;
import com.rcode.ordermicroservice.model.Order;
import com.rcode.ordermicroservice.model.OrderLineItem;
import com.rcode.ordermicroservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();

		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDto().stream().map(this::mapToOrderLineItem)
				.toList();

		order.setOrderLineItemList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemList().stream().map(OrderLineItem::getSkuCode).toList();

		// only place if available
		InventoryResponse[] inventoryResponse = webClientBuilder.build().get()
			.uri("http://inventory-service/api/inventory", 
					uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
			.retrieve()
			.bodyToMono(InventoryResponse[].class)
			.block();
		
		boolean isAllInStock = Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isInStock);
		
		if(isAllInStock) {
			orderRepository.save(order);
			kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
			return "Order placed successfully";
		} else {
			throw new IllegalArgumentException("Not available at the moment, please try later");
		}
	}

	private OrderLineItem mapToOrderLineItem(OrderLineItemsDto orderLineItemDto) {
		return OrderLineItem.builder().skuCode(orderLineItemDto.getSkuCode()).price(orderLineItemDto.getPrice())
				.quantity(orderLineItemDto.getQuantity()).build();
	}
}
