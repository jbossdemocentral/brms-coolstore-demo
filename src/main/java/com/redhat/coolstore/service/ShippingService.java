/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.coolstore.service;

import javax.ejb.Stateless;

import com.redhat.coolstore.model.ShoppingCart;

@Stateless
public class ShippingService {

    public void calculateShipping(ShoppingCart sc) {

        if (sc != null) {

            if (sc.getCartItemTotal() >= 0 && sc.getCartItemTotal() < 25) {

                sc.setShippingTotal(2.99);

            } else if (sc.getCartItemTotal() >= 25 && sc.getCartItemTotal() < 50) {

                sc.setShippingTotal(4.99);

            } else if (sc.getCartItemTotal() >= 50 && sc.getCartItemTotal() < 75) {

                sc.setShippingTotal(6.99);

            } else if (sc.getCartItemTotal() >= 75 && sc.getCartItemTotal() < 100) {

                sc.setShippingTotal(8.99);

            } else if (sc.getCartItemTotal() >= 100 && sc.getCartItemTotal() < 10000) {

                sc.setShippingTotal(10.99);

            }

        }

    }

}
