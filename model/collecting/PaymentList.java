package com.anastasiaeverstova.myeduserver.yookassa.model.collecting;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import com.anastasiaeverstova.myeduserver.yookassa.model.Payment;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class PaymentList {
    String type;
    Collection<Payment> items;
    UUID next_cursor;
}
