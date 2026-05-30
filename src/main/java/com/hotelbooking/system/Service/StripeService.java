package com.hotelbooking.system.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;
    public PaymentIntent createPaymentIntent(Double amount)
            throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long)(amount * 100))
                        .setCurrency("inr")
                        .build();

        return PaymentIntent.create(params);

    }

}

