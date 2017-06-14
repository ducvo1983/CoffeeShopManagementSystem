package mpp.course.spring2017.project.coffeeshop.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERORDER")
public class CustomerOrder {
	@Id
	@Column(name="ORDER_NO")
	private String orderNo;
	
	@Column(name="ORDER_DATE")
	private LocalDate orderDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	
	@OneToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="TOKEN_ID")
	private Token token;
	
	@Column(name="TAX")
	private float tax;
	
	@Column(name="AMOUNT")
	private float amount;
	
	@Column(name="UPDATE_TIME")
	private LocalDate updateTime;
	
	@OneToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;

	@Column(name="ORDER_TYPE", nullable=true)
	private String orderType;
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="customerOrder")
	private List<OrderLine> listOrderLine;
		
	public List<OrderLine> getListOrderLine() {
		return listOrderLine;
	}

	public void setListOrderLine(List<OrderLine> listOrderLine) {
		this.listOrderLine = listOrderLine;
		for(OrderLine ol : this.listOrderLine) {
			ol.setCustomerOrder(this);
		}
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public LocalDate getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDate updateTime) {
		this.updateTime = updateTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
