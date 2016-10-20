package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import controllers.WebApplication;

@Entity
public class ClientOrder extends Model {
	@Id(Generator.UUID)
	public String ID;
	public Date orderDate;
	public double total;
	public String client_ID;
	
	public static List<Order_Product> orderItems;
	
	public ClientOrder(){
		this.orderItems=new ArrayList<Order_Product>();
	}

	public static Query<ClientOrder> all() {
		return Model.all(ClientOrder.class);
	}

	public void saveOrder() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(ClientOrder.class).insert(this);
		} else {
			Model.batch(ClientOrder.class).update(this);
		}
	}

	public static ClientOrder getByID(String ID) {
		try {
			ClientOrder obj = Model.getByKey(ClientOrder.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void addItem(Order_Product op){
		orderItems.add(op);
	}

	public List<Order_Product> getListOrderProduct() {
		List<Order_Product> listOrderProducts = Model.all(Order_Product.class).filter("order_ID", ID).fetch();
		return listOrderProducts;
	}	
	
	public void updateOrderProduct(String product_id, int quantity){
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		int productsCount = productsList.size();
		if(productsCount > 0){
			for(Order_Product item : productsList){
				if(item != null){
					item.quantity = quantity;
					item.total = quantity * item.unitPrice;
					Model.batch(Order_Product.class).update(item);
					System.out.println("Update product quantity and total");
				}
			}
		}	
	}
	
}
