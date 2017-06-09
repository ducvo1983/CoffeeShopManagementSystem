package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;

public class BeverageSizeDaoFactory {
	private static BeverageSizeDaoFactory instance = new BeverageSizeDaoFactory();
	private IBeverageSizeDao impl = new BeverageSizeDaoImpl();
	public static BeverageSizeDaoFactory getInstance() { return instance; }
	
	public List<BeverageSize> getAllBeverageSizes() {
		return impl.getAllBeverageSizes();
	}
}
