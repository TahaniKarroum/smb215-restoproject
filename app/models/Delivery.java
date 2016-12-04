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
public class Delivery extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String ClientID;
	public String DeliveryStatus;
	public String ClientLocation;
	public String Notes;  
	public String DeliveryRating;


	@Override
	public String toString() {
		return "Delivery [ID=" + ID + ", ClientID=" + ClientID + ", DeliveryStatus=" + DeliveryStatus
				+ ", ClientLocation=" + ClientLocation + ", Notes=" + Notes + "]";
	}

	public static Query<Delivery> all() {
		return Model.all(Delivery.class);
	}

	public static Delivery getByID(String ID) {
		try {
			Delivery obj = Model.getByKey(Delivery.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void save() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(Delivery.class).insert(this);
		} else {

			Model.batch(Delivery.class).update(this);
		}
	}

}
