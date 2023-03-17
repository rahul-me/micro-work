package com.rcode.inventorymicroservice.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rcode.inventorymicroservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	@Query(value = "select * from t_inventory ti WHERE sku in (\"iphone_13\", \"iphone_13_red\")", nativeQuery = true)
    List<Inventory> getBySkuIn(@Param("skus") Collection<String> skus);

	Optional<Inventory> findBySku(String skuCode);
}
