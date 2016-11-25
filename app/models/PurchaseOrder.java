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
public class PurchaseOrder extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String number;
	public String date;
	public String pvendor_id;
	public String vendor_product_id;
	public String total;
	

	public static Query<PurchaseOrder> all() {
		return Model.all(PurchaseOrder.class);
	}

	public static PurchaseOrder getByID(String ID) {
		try {
			PurchaseOrder obj = Model.getByKey(PurchaseOrder.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveReservation() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(PurchaseOrder.class).insert(this);
		} else {

			Model.batch(PurchaseOrder.class).update(this);
		}
	}

}
