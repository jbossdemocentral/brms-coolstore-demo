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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import com.redhat.coolstore.model.Product;

@Singleton
public class ProductService {

    private List<Product> products;

    public ProductService() {

        products = new ArrayList<Product>();

        products.add(new Product("329299", "Red Fedora", "Official Red Hat Fedora", 34.99));
        products
            .add(new Product(
                "165613",
                "Solid Performance Polo",
                "Moisture-wicking, antimicrobial 100% polyester design wicks for life of garment. No-curl, rib-knit collar; special collar band maintains crisp fold; three-button placket with dyed-to-match buttons; hemmed sleeves; even bottom with side vents; Import. Embroidery. Red Pepper.",
                17.80));
        products
            .add(new Product(
                "165614",
                "Ogio Caliber Polo",
                "Moisture-wicking 100% polyester. Rib-knit collar and cuffs; Ogio jacquard tape inside neck; bar-tacked three-button placket with Ogio dyed-to-match buttons; side vents; tagless; Ogio badge on left sleeve. Import. Embroidery. Black.",
                28.75));
        products.add(new Product("165954", "16 oz. Vortex Tumbler",
                "   Double-wall insulated, BPA-free, acrylic cup. Push-on lid with thumb-slide closure; for hot and cold beverages. Holds 16 oz. Hand wash only. Imprint. Clear.", 6.00));

    }

    public List<Product> getProducts() {

        return new ArrayList<Product>(products);

    }

    public void setProducts(List<Product> products) {

        if (products != null) {

            this.products = new ArrayList<Product>(products);

        } else {

            this.products = new ArrayList<Product>();
        }

    }

    public Map<String, Product> getProductMap() {

        Map<String, Product> productMap = new HashMap<String, Product>();

        for (Product p : getProducts()) {

            productMap.put(p.getItemId(), p);

        }

        return productMap;

    }

}
