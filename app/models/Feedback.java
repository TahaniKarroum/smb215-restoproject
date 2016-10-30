package models;

import java.util.Date;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums;

@Entity
public class Feedback extends Model {

	@Id(Generator.UUID)
	public String ID;

	public String client_ID;
	@Max(1024)
	public String description;
	
	public Date submitdate;

		@Override
	public String toString() {
		return "Feedback [ID=" + ID + ", client_ID=" + client_ID + ", description=" + description + ", submitdate="
				+ submitdate + "]";
	}


	public static Query<Feedback> all() {
		return Model.all(Feedback.class);
	}

	
	public void saveFeed() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			this.submitdate=new Date();
			Model.batch(Feedback.class).insert(this);
		} else {
			Model.batch(Feedback.class).update(this);
		}
	}

	public static Feedback getByID(String ID) {
		try {
			Feedback obj = Model.getByKey(Feedback.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
}
