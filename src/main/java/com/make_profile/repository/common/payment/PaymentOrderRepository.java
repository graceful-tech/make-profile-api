package com.make_profile.repository.common.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.common.payment.PaymentOrderEntity;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

	@Query(value = "select * from payment_orders where order_id = :orderId", nativeQuery = true)
	PaymentOrderEntity getPaymentOrder(@Param("orderId") String orderId);

}
