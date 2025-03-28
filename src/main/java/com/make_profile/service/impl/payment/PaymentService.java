package com.make_profile.service.impl.payment;

import com.make_profile.dto.payment.PaymentDto;
import com.make_profile.dto.payment.PaymentOrderDto;

public interface PaymentService {

	PaymentOrderDto generatePayment(PaymentOrderDto paymentOrderDto);

	boolean savePayment(PaymentDto paymentDto);

}
