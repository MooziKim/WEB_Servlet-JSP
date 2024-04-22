package com.moozi.mooziweb.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moozi.mooziweb.product.domain.Product;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public final class CookieUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String PRODUCT_QUEUE_COOKIE_NAME = "productQueue";

    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie getCookie(HttpServletRequest req, String name) {
        return Optional.ofNullable(req.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(name))
                        .findFirst())
                .orElse(null);
    }

    public static Queue<Product> getQueueFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PRODUCT_QUEUE_COOKIE_NAME)) {
                    try {
                        return convertBase64ToQueue(cookie.getValue());
                    } catch (IOException e) {
                        log.error("Error deserializing queue from Base64", e);
                    }
                }
            }
        }

        return new ArrayDeque<>();
    }

    public static void saveQueueToCookie(HttpServletResponse response, Queue<Product> queue) {
        try {
            String base64Queue = convertQueueToBase64(queue);

            Cookie cookie = new Cookie(PRODUCT_QUEUE_COOKIE_NAME, base64Queue);
            cookie.setMaxAge(60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            log.error("Error serializing queue to JSON", e);
        }
    }

    private static String convertQueueToBase64(Queue<Product> queue) throws JsonProcessingException {
        String jsonQueue = objectMapper.writeValueAsString(queue);
        return Base64.getEncoder().encodeToString(jsonQueue.getBytes());
    }

    private static Queue<Product> convertBase64ToQueue(String base64) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String jsonQueue = new String(decodedBytes);
        return objectMapper.readValue(jsonQueue, objectMapper.getTypeFactory().constructCollectionType(Queue.class, Product.class));
    }
}
