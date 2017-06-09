package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;

public class BeverageSizePriceDaoFactory {
	private static BeverageSizePriceDaoFactory instance = new BeverageSizePriceDaoFactory();
	private IBeverageSizePriceDao impl = new BeverageSizePriceDaoImpl();
	public static BeverageSizePriceDaoFactory getInstance() { return instance; }
	
	public List<BeverageSizePrice> getAllBeverageSizePrices() {
		return impl.getAllBeverageSizePrices();
	}
	
	public BeverageSizePrice findBeverageSizePrice(int pID, int sizeID) {
		return impl.findBeverageSizePrice(pID, sizeID);
	}
	
	public List<BeverageSizePrice> getBeverageSizePrices(int pID) {
		return impl.getBeverageSizePrices(pID);
	}
}
