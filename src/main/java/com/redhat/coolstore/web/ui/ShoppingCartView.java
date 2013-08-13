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
package com.redhat.coolstore.web.ui;

import java.text.DecimalFormat;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.web.CoolStoreApplication;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ShoppingCartView extends Panel {

    private static final String labelWidth = "8em";

    private String buttonWidth = "7em";

    private DecimalFormat df = new DecimalFormat("'$'0.00");

    private CoolStoreApplication app = null;

    private Button checkoutButton;

    private Button clearButton;

    private Label subtotalValue;

    private Label cartPromoValue;

    private Label shippingValue;

    private Label shippingPromoValue;

    private Label cartTotalValue;

    private static final long serialVersionUID = 962893447423474540L;

    public ShoppingCartView(CoolStoreApplication app) {

        super();

        this.app = app;

        VerticalLayout vl = new VerticalLayout();

        vl.setWidth("100%");

        Label inventoryLabel = new Label("Shopping Cart:");

        inventoryLabel.setStyleName(Reindeer.LABEL_H1);

        vl.addComponent(inventoryLabel);

        vl.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setSizeFull();

        Label subtotalLabel = new Label("<b>Subtotal:</b>", Label.CONTENT_XHTML);
        subtotalLabel.setWidth(labelWidth);

        subtotalValue = new Label();
        subtotalValue.setValue(df.format(0));
        subtotalValue.setWidth(labelWidth);

        hl1.addComponent(subtotalLabel);
        hl1.addComponent(subtotalValue);
        hl1.setComponentAlignment(subtotalValue, Alignment.MIDDLE_RIGHT);

        vl.addComponent(hl1);

        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.setSizeFull();

        Label cartPromoLabel = new Label("  <i>Promotion(s):</i>", Label.CONTENT_XHTML);
        cartPromoLabel.setWidth(labelWidth);

        cartPromoValue = new Label();
        cartPromoValue.setValue(df.format(0));
        cartPromoValue.setWidth(labelWidth);

        hl2.addComponent(cartPromoLabel);
        hl2.addComponent(cartPromoValue);
        hl2.setComponentAlignment(cartPromoValue, Alignment.MIDDLE_RIGHT);

        vl.addComponent(hl2);

        HorizontalLayout hl3 = new HorizontalLayout();
        hl3.setSizeFull();

        Label shippingLabel = new Label("<b>Shipping:</b>", Label.CONTENT_XHTML);
        shippingLabel.setWidth(labelWidth);
        shippingValue = new Label();
        shippingValue.setValue(df.format(0));
        shippingValue.setWidth(labelWidth);

        hl3.addComponent(shippingLabel);
        hl3.addComponent(shippingValue);
        hl3.setComponentAlignment(shippingValue, com.vaadin.ui.Alignment.MIDDLE_RIGHT);

        vl.addComponent(hl3);

        HorizontalLayout hl4 = new HorizontalLayout();
        hl2.setSizeFull();

        Label shippingPromoLabel = new Label("  <i>Promotion(s):</i>", Label.CONTENT_XHTML);
        shippingPromoLabel.setWidth(labelWidth);

        shippingPromoValue = new Label();
        shippingPromoValue.setValue(df.format(0));
        shippingPromoValue.setWidth(labelWidth);

        hl4.addComponent(shippingPromoLabel);
        hl4.addComponent(shippingPromoValue);
        hl4.setComponentAlignment(shippingPromoValue, Alignment.MIDDLE_RIGHT);

        vl.addComponent(hl4);

        HorizontalLayout hl5 = new HorizontalLayout();
        hl5.setSizeFull();

        Label cartTotalLabel = new Label("<b>Cart Total:</b>", Label.CONTENT_XHTML);
        cartTotalLabel.setWidth(labelWidth);

        cartTotalValue = new Label();
        cartTotalValue.setValue(df.format(0));
        cartTotalValue.setWidth(labelWidth);

        hl5.addComponent(cartTotalLabel);
        hl5.addComponent(cartTotalValue);
        hl5.setComponentAlignment(cartTotalValue, com.vaadin.ui.Alignment.MIDDLE_RIGHT);

        vl.addComponent(hl5);

        vl.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));

        HorizontalLayout hl = new HorizontalLayout();

        hl.setSpacing(true);

        checkoutButton = new Button("Checkout");
        checkoutButton.addListener((ClickListener) app);
        checkoutButton.setWidth(buttonWidth);
        checkoutButton.setEnabled(false);
        hl.addComponent(checkoutButton);

        clearButton = new Button("Clear");
        clearButton.addListener((ClickListener) app);
        clearButton.setWidth(buttonWidth);
        hl.addComponent(clearButton);

        vl.addComponent(hl);

        vl.setSizeFull();

        vl.setSpacing(true);

        addComponent(vl);

        setSizeFull();

        setHeight("100%");

    }

    public Button getCheckoutButton() {
        return checkoutButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public void updateShoppingCart(ShoppingCart sc) {

        if (sc != null) {

            subtotalValue.setValue(df.format(sc.getCartItemTotal()));
            cartPromoValue.setValue(df.format(sc.getCartItemPromoSavings()));
            shippingValue.setValue(df.format(sc.getShippingTotal()));
            shippingPromoValue.setValue(df.format(sc.getShippingPromoSavings()));
            cartTotalValue.setValue(df.format(sc.getCartTotal()));

        }

    }

}
