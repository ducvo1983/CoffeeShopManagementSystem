package mpp.course.spring2017.project.coffeeshop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDERLINE")
public class OrderLine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_NO", nullable=false)
	private CustomerOrder customerOrder;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
	/*@Id
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BEVERAGE_SIZE", nullable=true)
	private BeverageSize beverageSize;*/
	@Column(name="BEVERAGE_SIZE", nullable=true)
	private Character beverageSize;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@Column(name="UNIT_PRICE")
	private float unitPrice;
	
	@Column(name="PRICE")
	private float price;
	
	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Character getBeverageSize() {
		return beverageSize;
	}

	public void setBeverageSize(Character beverageSize) {
		this.beverageSize = beverageSize;
	}
}
