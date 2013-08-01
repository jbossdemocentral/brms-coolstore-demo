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

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.inject.Inject;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

import com.redhat.coolstore.factmodel.PromoEvent;
import com.redhat.coolstore.model.Promotion;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.util.BRMSUtil;

@Stateful
public class ShoppingCartServiceImplBRMS implements ShoppingCartService, Serializable {

    private static final long serialVersionUID = 6821952169434330759L;

    @Inject
    private BRMSUtil brmsUtil;

    @Inject
    private PromoService promoService;

    public ShoppingCartServiceImplBRMS() {

    }

    public void priceShoppingCart(ShoppingCart sc) {

        if (sc != null) {

            com.redhat.coolstore.factmodel.ShoppingCart factShoppingCart = new com.redhat.coolstore.factmodel.ShoppingCart();

            StatefulKnowledgeSession ksession = null;

            try {

                // if at least one shopping cart item exist
                if (sc.getShoppingCartItemList().size() > 0) {

                    ksession = brmsUtil.getStatefulSession();

                    WorkingMemoryEntryPoint promoStream = ksession.getWorkingMemoryEntryPoint("Promo Stream");

                    for (Promotion promo : promoService.getPromotions()) {

                        PromoEvent pv = new PromoEvent(promo.getItemId(), promo.getPercentOff());

                        promoStream.insert(pv);

                    }

                    ksession.insert(factShoppingCart);

                    for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {

                        com.redhat.coolstore.factmodel.ShoppingCartItem factShoppingCartItem = new com.redhat.coolstore.factmodel.ShoppingCartItem();
                        factShoppingCartItem.setItemId(sci.getProduct().getItemId());
                        factShoppingCartItem.setName(sci.getProduct().getName());
                        factShoppingCartItem.setPrice(sci.getProduct().getPrice());
                        factShoppingCartItem.setQuantity(sci.getQuanity());
                        factShoppingCartItem.setShoppingCart(factShoppingCart);

                        ksession.insert(factShoppingCartItem);

                    }

                    ksession.startProcess("com.redhat.coolstore.PriceProcess");

                    ksession.fireAllRules();

                }

                sc.setCartItemTotal(factShoppingCart.getCartItemTotal());
                sc.setCartItemPromoSavings(factShoppingCart.getCartItemPromoSavings());
                sc.setShippingTotal(factShoppingCart.getShippingTotal());
                sc.setShippingPromoSavings(factShoppingCart.getShippingPromoSavings());
                sc.setCartTotal(factShoppingCart.getCartTotal());

            } finally {

                if (ksession != null) {

                    ksession.dispose();

                }
            }
        }

    }

}
