package com.microservicios1.shoppingService.repository;

import com.microservicios1.shoppingService.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}