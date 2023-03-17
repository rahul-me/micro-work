package com.rcode.inventorymicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.rcode.inventorymicroservice.model.Inventory;
import com.rcode.inventorymicroservice.repository.InventoryRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroserviceApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSku("iphone_13");
            inventory.setQuantity(100);

            Inventory inventory1 = new Inventory();
            inventory1.setSku("iphone_13_red");
            inventory1.setQuantity(0);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);
        };
    }

}
