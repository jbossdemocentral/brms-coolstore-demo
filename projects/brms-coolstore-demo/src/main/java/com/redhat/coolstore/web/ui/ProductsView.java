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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;
import com.redhat.coolstore.web.CoolStoreApplication;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ProductsView extends Panel {

    private String buttonWidth = "8em";

    private DecimalFormat df = new DecimalFormat("'$'0.00");

    private CoolStoreApplication app = null;

    private ProductService productService = new ProductService();

    private Map<String, CheckBox> checkBoxMap = new HashMap<String, CheckBox>();

    private Button addToCartButton;

    private Button checkAllButton;

    private Button uncheckAllButton;

    private static final long serialVersionUID = 962893447423474540L;

    public ProductsView(CoolStoreApplication app) {

        super();

        this.app = app;

        VerticalLayout vl = new VerticalLayout();

        Label inventoryLabel = new Label("Products:");

        inventoryLabel.setStyleName(Reindeer.LABEL_H1);

        vl.addComponent(inventoryLabel);

        for (Product product : productService.getProducts()) {

            vl.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));

            CheckBox cb = new CheckBox(product.getName() + " (" + df.format(product.getPrice()) + ")");

            cb.setData(product);

            cb.setImmediate(true);

            cb.addListener(new ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {

                    System.out.println(event);

                }
            });

            vl.addComponent(cb);

            checkBoxMap.put(product.getName(), cb);

        }

        vl.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));

        HorizontalLayout hl = new HorizontalLayout();

        hl.setSpacing(true);

        addToCartButton = new Button("Add To Cart");
        addToCartButton.addListener((ClickListener) app);
        addToCartButton.setClickShortcut(KeyCode.ENTER);
        addToCartButton.setWidth(buttonWidth);
        hl.addComponent(addToCartButton);

        checkAllButton = new Button("Check All");
        checkAllButton.addListener((ClickListener) app);
        checkAllButton.setWidth(buttonWidth);
        hl.addComponent(checkAllButton);

        uncheckAllButton = new Button("Uncheck All");
        uncheckAllButton.addListener((ClickListener) app);
        uncheckAllButton.setWidth(buttonWidth);
        hl.addComponent(uncheckAllButton);

        vl.addComponent(hl);

        vl.setSizeFull();

        vl.setSpacing(true);

        addComponent(vl);

        setSizeFull();

        setHeight("100%");

    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getCheckAllButton() {
        return checkAllButton;
    }

    public Button getUncheckAllButton() {
        return uncheckAllButton;
    }

    public void checkAllBoxes(boolean select) {

        if (checkBoxMap != null) {

            for (CheckBox cb : checkBoxMap.values()) {

                cb.setValue(select);

            }

        }

    }

    public List<Product> getSelectedProducts() {

        List<Product> selectedProductList = new ArrayList<Product>();

        if (checkBoxMap != null) {

            for (CheckBox cb : checkBoxMap.values()) {

                if (cb.booleanValue()) {

                    selectedProductList.add((Product) cb.getData());

                }

            }

        }

        return selectedProductList;

    }

}
