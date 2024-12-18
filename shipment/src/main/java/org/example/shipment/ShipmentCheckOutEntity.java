package org.example.shipment;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "shipment_checkout")
public class ShipmentCheckOutEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shipmentId;

    @Column(name = "checkout_id")
    private Long checkOutId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
