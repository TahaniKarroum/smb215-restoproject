package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums;
import utils.Enums.StatusOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import controllers.WebApplication;

@Entity
public class PurchaseOrder extends Model {
	@Id(Generator.UUID)
	public String ID;
	public Date orderDate;
	public double total;
	public String pvendor_ID;
	public int status;
	public int ordernb;
	public static List<Order_Product> orderItems;

	@Override
	public String toString() {
		return "ClientOrder [ID=" + ID + ", orderDate=" + orderDate + ", total=" + total + ", pvendor_ID=" + pvendor_ID
				+ ", status=" + status + "]";
	}

	public PurchaseOrder() {
		this.orderItems = new ArrayList<Order_Product>();
	}

	public static Query<PurchaseOrder> all() {
		return Model.all(PurchaseOrder.class);
	}

	public int getLastOrderNb() {
		PurchaseOrder lastOrder = Model.all(PurchaseOrder.class).order("-ordernb").get();
		if (lastOrder != null)
			return lastOrder.ordernb;
		else
			return 0;
	}

	public String getStatus() {
		if (this.status == 0)
			return " NA ";
		StatusOrder st = StatusOrder.values()[this.status];
		if (st == null)
			return " NA ";
		return st.name();
	}

	public Client getClient() {
		if (this.pvendor_ID == null || this.pvendor_ID.length() == 0)
			return null;
		Client client = Client.getByID(pvendor_ID);
		if (client == null)
			return null;
		return client;
	}

	public void saveOrder() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			this.status = Enums.StatusOrder.UnderPrepare.ordinal();
			this.ordernb = getLastOrderNb() + 1;
			Model.batch(PurchaseOrder.class).insert(this);
		} else {
			Model.batch(PurchaseOrder.class).update(this);
		}
	}

	public static PurchaseOrder getByID(String ID) {
		try {
			PurchaseOrder obj = Model.getByKey(PurchaseOrder.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public static void addItem(Order_Product op) {
		orderItems.add(op);
	}

	public List<Order_Product> getListOrderProduct() {
		List<Order_Product> listOrderProducts = Model.all(Order_Product.class).filter("order_ID", ID).fetch();
		return listOrderProducts;
	}

	public static void removeOrderProduct(String product_id) {
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		String order_id;
		int productsCount = productsList.size();
		if (productsCount > 0) {
			for (Order_Product item : productsList) {
				if (item != null) {
					order_id = item.order_ID;
					item.delete();
					PurchaseOrder order = getByID(order_id);
					//WebApplication.index(order);
				}
			}
		}
	}

	public void updateOrderProduct(String product_id, int quantity) {
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		int productsCount = productsList.size();
		if (productsCount > 0) {
			for (Order_Product item : productsList) {
				if (item != null) {
					item.quantity = quantity;
					item.total = quantity * item.unitPrice;
					Model.batch(Order_Product.class).update(item);
					System.out.println("Update product quantity and total");
				}
			}
		}
	}

}
