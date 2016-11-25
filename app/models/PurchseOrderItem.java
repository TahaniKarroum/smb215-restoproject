package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.util.*;

import controllers.WebApplication;
import models.Product;

@Entity
public class PurchseOrderItem extends Model {
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

	public static List<PurchseOrderItem> getByOrderID(String orderID) {
		return Model.all(PurchseOrderItem.class).filter("order_ID", orderID).fetch();
	}

	public static PurchseOrderItem getByOrderID_ProductID(String orderID, String product_ID) {
		return Model.all(PurchseOrderItem.class).filter("order_ID", orderID).filter("product_ID", product_ID).get();
	}

	public static Query<PurchseOrderItem> getAllOrderProducts() {
		return Model.all(PurchseOrderItem.class);
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

	public static Query<PurchseOrderItem> all() {
		return Model.all(PurchseOrderItem.class);
	}

	public void saveOrder_Product(String customer_ID) {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(PurchseOrderItem.class).insert(this);
			System.out.println("Insert new product to shopping cart");
		} else {
			Model.batch(PurchseOrderItem.class).update(this);
			System.out.println("Update product");
		}
	}

	public static PurchseOrderItem getByID(String ID) {
		try {
			PurchseOrderItem obj = Model.getByKey(PurchseOrderItem.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static PurchseOrderItem getByProductID(String product_id){
		PurchseOrderItem obj = null;
		List<PurchseOrderItem> productsList = PurchseOrderItem.getAllOrderProducts().filter("product_ID", product_id).fetch();
		int productsCount = productsList.size();
		if(productsCount > 0){
			for(PurchseOrderItem item : productsList){				
				if(item != null){
					obj = item;
				}
			}					
		}
		return obj;
	}

	public boolean canBeDeleted() {
		return false;
	}

}
