package mpp.course.spring2017.project.coffeeshop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="BEVERAGESIZEPRICE")
public class BeverageSizePrice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SIZE_ID")
	private BeverageSize beverageSize;
	
	@Column(name="UNIT_PRICE")
	private float unitPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BeverageSize getBeverageSize() {
		return beverageSize;
	}

	public void setBeverageSize(BeverageSize beverageSize) {
		this.beverageSize = beverageSize;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
}
