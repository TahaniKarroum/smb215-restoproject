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
public class RatingEmployee extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String EmployeeID;
	public String Rating;
	
	 
	@Override
	public String toString() {
		return "RatingEmployee [ID=" + ID + ", EmployeeID=" + EmployeeID + ", Rating=" + Rating + "]";
	}

	public static Query<RatingEmployee> all() {
		return Model.all(RatingEmployee.class);
	}

	public static RatingEmployee getByID(String ID) {
		try {
			RatingEmployee obj = Model.getByKey(RatingEmployee.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveReservation() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(RatingEmployee.class).insert(this);
		} else {

			Model.batch(RatingEmployee.class).update(this);
		}
	}

}
