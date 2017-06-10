package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.MenuItem;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.Product;

public class CoffeeShopMenuItem extends MenuItem {
	private Product prod = null;
	private BeverageSizePrice bsp = null;

	public CoffeeShopMenuItem(String menuName, Product prod, BeverageSizePrice bsp) {
		super(menuName);
		this.prod = prod;
		this.bsp = bsp;
	}
	public BeverageSizePrice getBeverageSizePrice() {
		return bsp;
	}
	public Product getProduct() {
		return prod;
	}
}
