package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;

public interface IBeverageSizeDao {
	public boolean newBeverageSize(BeverageSize s);
	public List<BeverageSize> getAllBeverageSizes();
	//public List<BeverageSize> getProducts(int categoryID);
	public boolean updateBeverageSize(BeverageSize s);
	public boolean deleteBeverageSize(BeverageSize s);
	public BeverageSize findBeverageSize(int ID);
}
