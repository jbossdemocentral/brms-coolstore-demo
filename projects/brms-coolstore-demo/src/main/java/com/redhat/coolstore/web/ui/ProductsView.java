package com.redhat.coolstore.web.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScoped
public class ProductsView extends AbstractView {

	@Inject
	private ProductService productService;

	private String buttonWidth = "8em";
	
	private DecimalFormat df = new DecimalFormat("'$'0.00");

	private Map<String, CheckBox> checkBoxMap = new HashMap<String, CheckBox>();
	
	private Button addToCartButton;
	
	private Button checkAllButton;
	
	private Button uncheckAllButton;

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		Label inventoryLabel = new Label("Products:");
		
		inventoryLabel.addStyleName(ValoTheme.LABEL_H1);
		
		layout.addComponent(inventoryLabel);
		
		for (Product product : productService.getProducts()) {
			
			CheckBox cb = new CheckBox(product.getName() + " (" + df.format(product.getPrice()) + ")");
					
			cb.setData(product);
			
			cb.setImmediate(true);
			
			cb.addValueChangeListener(new ValueChangeListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -9074656668897487720L;

				@Override
				public void valueChange(ValueChangeEvent event) {

					System.out.println(event);
				}
			});

			layout.addComponent(cb);
			
			checkBoxMap.put(product.getName(), cb);
			
		}

		HorizontalLayout hl = new HorizontalLayout();
		hl.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		hl.setSpacing(true);

		addToCartButton = new Button("Add To Cart");
		addToCartButton.addClickListener(this);
		addToCartButton.setClickShortcut(KeyCode.ENTER);
		addToCartButton.setWidth(buttonWidth);
		hl.addComponent(addToCartButton);

		checkAllButton = new Button("Check All");
		checkAllButton.addClickListener(this);
		checkAllButton.setWidth(buttonWidth);
		hl.addComponent(checkAllButton);

		uncheckAllButton = new Button("Uncheck All");
		uncheckAllButton.addClickListener(this);
		uncheckAllButton.setWidth(buttonWidth);
		hl.addComponent(uncheckAllButton);

		layout.addComponent(hl);
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
		
		if ( checkBoxMap != null ) {
		
			for (CheckBox cb : checkBoxMap.values()) {
				
				cb.setValue(select);
				
			}
			
		}	
		
	}
	
	public List<Product> getSelectedProducts() {
		
		List<Product> selectedProductList = new ArrayList<Product>();
		
		if ( checkBoxMap != null ) {
			
			for (CheckBox cb : checkBoxMap.values()) {
																				
				if (cb.getValue()) {
				
					selectedProductList.add((Product) cb.getData());
					
				}
				
			}
			
		}
		
		return selectedProductList;
		
	}
	
}
