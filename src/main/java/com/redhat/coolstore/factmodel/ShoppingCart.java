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
package com.redhat.coolstore.factmodel;

import java.math.BigDecimal;

public class ShoppingCart {

    private double cartItemTotal;

    private double cartItemPromoSavings;

    private double shippingTotal;

    private double shippingPromoSavings;

    private double cartTotal;

    public double getCartItemTotal() {
        return cartItemTotal;
    }

    public void setCartItemTotal(double cartItemTotal) {

        this.cartItemTotal = (new BigDecimal(cartItemTotal)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

    }

    public double getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(double shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal) {

        this.cartTotal = (new BigDecimal(cartTotal)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

    }

    public double getCartItemPromoSavings() {
        return cartItemPromoSavings;
    }

    public void setCartItemPromoSavings(double cartItemPromoSavings) {
        this.cartItemPromoSavings = (new BigDecimal(cartItemPromoSavings)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public double getShippingPromoSavings() {
        return shippingPromoSavings;
    }

    public void setShippingPromoSavings(double shippingPromoSavings) {
        this.shippingPromoSavings = (new BigDecimal(shippingPromoSavings)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    @Override
    public String toString() {
        return "ShoppingCart [cartItemTotal=" + cartItemTotal
            + ", cartItemPromoSavings=" + cartItemPromoSavings
            + ", shippingTotal=" + shippingTotal
            + ", shippingPromoSavings=" + shippingPromoSavings
            + ", cartTotal=" + cartTotal + "]";
    }

}
