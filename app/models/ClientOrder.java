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

@Entity
public class ClientOrder extends Model {
	@Id(Generator.UUID)
	public String ID;
	public Date orderDate;
	public double total;
	public String client_ID;
	//public List<Order_Product> items;

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
}
