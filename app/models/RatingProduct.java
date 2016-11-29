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
public class RatingProduct extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String ProductID;
	public String Rating;
	
	@Override
	public String toString() {
		return "RatingProduct [ID=" + ID + ", ProductID=" + ProductID + ", Rating=" + Rating + "]";
	}

	public static Query<RatingProduct> all() {
		return Model.all(RatingProduct.class);
	}

	public static RatingProduct getByID(String ID) {
		try {
			RatingProduct obj = Model.getByKey(RatingProduct.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void save() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(RatingProduct.class).insert(this);
		} else {

			Model.batch(RatingProduct.class).update(this);
		}
	}

}
