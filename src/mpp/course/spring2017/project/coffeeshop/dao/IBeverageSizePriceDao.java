package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;

public interface IBeverageSizePriceDao {
	public boolean newBeverageSizePrice(BeverageSizePrice s);
	public List<BeverageSizePrice> getAllBeverageSizePrices();
	public List<BeverageSizePrice> getBeverageSizePrices(int pID);
	//public List<BeverageSize> getProducts(int categoryID);
	public boolean updateBeverageSizePrice(BeverageSizePrice s);
	public boolean deleteBeverageSizePrice(BeverageSizePrice s);
	public BeverageSizePrice findBeverageSizePrice(int pID, int sizeID);
}
