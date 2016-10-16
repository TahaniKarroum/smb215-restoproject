package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.util.*;
import models.Product;

@Entity
public class Order_Product extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String productname;
	public String image;
	public String order_ID;
	public String product_ID;
	public int quantity;
	public double unitPrice;
	public double total;
	public Date submitDate;


	@Override
	public String toString() {
		return "Order_Product [ID=" + ID + ", productname=" + productname + ", image=" + image + ", order_ID="
				+ order_ID + ", product_ID=" + product_ID + ", quantity=" + quantity + ", unitPrice=" + unitPrice
				+ ", total=" + total + ", submitDate=" + submitDate + "]";
	}

	public static List<Order_Product> getByOrderID(String orderID) {
		return Model.all(Order_Product.class).filter("order_ID", orderID).fetch();
	}

	public static Order_Product getByOrderID_ProductID(String orderID, String product_ID) {
		return Model.all(Order_Product.class).filter("order_ID", orderID).filter("product_ID", product_ID).get();
	}

	public static Query<Order_Product> getAllOrderProducts() {
		return Model.all(Order_Product.class);
	}

	public String getProductName() {
		Product product = Product.getByID(product_ID);
		if (product == null)
			return " ";
		return product.name;
	}

	public Product getProduct() {
		return Product.getByID(product_ID);
	}

	public ClientOrder getOrder() {
		return ClientOrder.getByID(order_ID);
	}

	public double getProductQuantityInStock() {
		Product product = Product.getByID(product_ID);
		if (product == null)
			return 0;
		return product.qteInStock;
	}

	public static Query<Order_Product> all() {
		return Model.all(Order_Product.class);
	}

	public void saveOrder_Product(String customer_ID) {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Order_Product.class).insert(this);
			System.out.println("Insert new product to shopping cart");
		} else {
			Model.batch(Order_Product.class).update(this);
			System.out.println("Update product");
		}
	}

	public static Order_Product getByID(String ID) {
		try {
			Order_Product obj = Model.getByKey(Order_Product.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean canBeDeleted() {
		return false;
	}

}
