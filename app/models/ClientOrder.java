package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums;

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
	public int status;
	public int ordernb;
	public static List<Order_Product> orderItems;
	
	@Override
	public String toString() {
		return "ClientOrder [ID=" + ID + ", orderDate=" + orderDate + ", total=" + total + ", client_ID=" + client_ID
				+ ", status=" + status + "]";
	}

	public ClientOrder(){
		this.orderItems=new ArrayList<Order_Product>();
	}

	public static Query<ClientOrder> all() {
		return Model.all(ClientOrder.class);
	}
	
	public int getLastOrderNb(){
		ClientOrder lastOrder=Model.all(ClientOrder.class).order("-ordernb").get();
		if(lastOrder!=null)
			return lastOrder.ordernb;
		else
			return 0;
	}
	
	public void saveOrder() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			this.status=Enums.StatusOrder.UnderPrepare.ordinal();
			this.ordernb=getLastOrderNb()+1;
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
	
	public static void removeOrderProduct(String product_id){
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		String order_id;
		int productsCount = productsList.size();
		if(productsCount > 0){
			for(Order_Product item : productsList){				
				if(item != null){
					order_id = item.order_ID;
					item.delete();
					ClientOrder order = getByID(order_id);
					WebApplication.index(order);
				}
			}					
		}		
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
