package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.MenuItem;

public class CoffeeShopMenuItem extends MenuItem {
	private Object[] objs = null;

	public CoffeeShopMenuItem(String menuName, Object[] objs) {
		super(menuName);
		this.objs = objs;
	}
	public Object getObject(int index) {
		if (objs == null || index < 0 || index > objs.length) {
			return null;
		}
		return objs[index];
	}
	public Object[] getObjects() {
		return objs;
	}
}
