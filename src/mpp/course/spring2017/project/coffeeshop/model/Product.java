package mpp.course.spring2017.project.coffeeshop.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PRODUCT")
public class Product {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private int ID;
	
	@Column(name="NAME")
	private String name;

	@Column(name="UNIT_PRICE", nullable=true)
	private Float unitPrice;

	@Column(name="IMAGE")
	private byte[] image;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATELOGY_ID")
	private ProductCatelogy productCatelogy;
	
	@Column(name="CREATE_DATE")
	private LocalDate createDate;
	
	@Column(name="UPDATE_DATE")
	private LocalDate updateDate;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public ProductCatelogy getProductCatelogy() {
		return productCatelogy;
	}

	public void setProductCatelogy(ProductCatelogy productCatelogy) {
		this.productCatelogy = productCatelogy;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return name;
	}
}
