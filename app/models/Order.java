package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.util.*;

@Entity
public class Order extends Model {
	@Id(Generator.UUID)
	public String ID;
	public Date orderDate;
	public double total;
	public String client_ID;

	public static Query<Order> all() {
		return Model.all(Order.class);
	}

	public void saveOrder() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Order.class).insert(this);
		} else {
			Model.batch(Order.class).update(this);
		}
	}

	public static Order getByID(String ID) {
		try {
			Order obj = Model.getByKey(Order.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

}
