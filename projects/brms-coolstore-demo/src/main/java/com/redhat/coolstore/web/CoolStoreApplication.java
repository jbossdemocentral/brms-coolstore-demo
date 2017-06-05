package com.redhat.coolstore.web;

import com.redhat.coolstore.web.ui.ProductsView;
import com.redhat.coolstore.web.ui.ShoppingCartView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;

@Theme("coolstoretheme")
@Title("Red Hat Cool Store")
@CDIUI("")
public class CoolStoreApplication extends UI {

	private static final long serialVersionUID = 8436561253049378320L;

	private Embedded logo = new Embedded("", new ThemeResource(
			"./images/logo.png"));

	@Inject
	private ProductsView productView;

	@Inject
	private ShoppingCartView shoppingCartView;

	@Override
	public void init(VaadinRequest request) {

		VerticalLayout vl = new VerticalLayout();
		vl.setHeight("100%");
		vl.setWidth("80em");

		vl.addComponent(logo);

		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.addStyleName(ValoTheme.SPLITPANEL_LARGE);
		hsp.setWidth("100%");
		hsp.setHeightUndefined();
		hsp.setFirstComponent(productView);
		hsp.setSplitPosition(65);
		hsp.setSecondComponent(shoppingCartView);

		vl.addComponent(hsp);
		vl.setExpandRatio(hsp, 1);

		setContent(vl);
	}
}
