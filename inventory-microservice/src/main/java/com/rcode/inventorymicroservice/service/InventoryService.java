package com.rcode.inventorymicroservice.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rcode.inventorymicroservice.dto.InventoryResponse;
import com.rcode.inventorymicroservice.model.Inventory;
import com.rcode.inventorymicroservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        log.info("wait started");
        //TimeUnit.SECONDS.sleep(10);
        log.info("wait completed");
        List<Inventory> list = inventoryRepository.getBySkuIn(skuCode);
        return inventoryRepository.getBySkuIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSku())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
    
    public boolean isInStock(String skuCode) {
    	return inventoryRepository.findBySku(skuCode).isPresent();
    }
}
