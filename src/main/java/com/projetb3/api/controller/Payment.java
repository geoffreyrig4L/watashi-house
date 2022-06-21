package com.projetb3.api.controller;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.post;


@Controller
public class Payment {

    @GetMapping("/paiement/prix={price}")
    public ResponseEntity<Object> paymentStripe(@RequestParam("price") final int price, @RequestHeader("Authentication") final String token) {
        Stripe.apiKey = "sk_test_51LCT6xCrEfc8tDFi69xG85iRnYnc1j9tIksoikHRaAJ4g3JULSTfxhSiX34R7IPI3GDoEAAQcHVedkLKjhA6Dhu000cDiCiuJA";
        post("/create-payment-intent", (request, response) -> {
            response.type("application/json");

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(new Long (price))
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
        });
        return ResponseEntity.badRequest().build();
    }
}