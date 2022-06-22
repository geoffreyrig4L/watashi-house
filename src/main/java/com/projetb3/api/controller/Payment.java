package com.projetb3.api.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;

import static com.projetb3.api.security.AuthenticationWithJWT.verifyJwt;

@Controller
public class Payment {

    @PostMapping("/create-payment-intent/prix={price}")
    public ResponseEntity<Object> paymentStripe(@PathVariable int price, @RequestHeader("Authentication") final String token) throws StripeException {
        if (verifyJwt(token) != null) {
            Stripe.apiKey = "sk_test_51LCT6xCrEfc8tDFi69xG85iRnYnc1j9tIksoikHRaAJ4g3JULSTfxhSiX34R7IPI3GDoEAAQcHVedkLKjhA6Dhu000cDiCiuJA";
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) price)
                    .setCurrency("eur")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods
                                    .builder()
                                    .setEnabled(true)
                                    .build()
                    ).build();
            var paymentIntent = PaymentIntent.create(params);
            Map<String, Object> clientSecret = new HashMap<>();
            clientSecret.put("clientSecret", paymentIntent.getClientSecret());
            return new ResponseEntity<>(clientSecret, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("🛑");
    }
}