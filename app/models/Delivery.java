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
	
	public String first_name;
	public String last_name;
	public String phone;
	public String state;
	public String guest_number;
	public String email;
	public String subject;
	public String res_status;//pending accepted canceled
	public String res_client_id;
	public int res_numb_pers;
	public Date res_date;

	public String res_date_from;
	public String res_date_to;


	@Override
	public String toString() {
		return "Reservation [res_id=" + res_id + ", first_name=" + first_name + ", phone=" + phone + ", last_name="
				+ last_name + ", state=" + state + ", guest_number=" + guest_number + ", email=" + email + ", subject="
				+ subject + ", res_status=" + res_status + ", res_client_id=" + res_client_id + ", res_numb_pers="
				+ res_numb_pers + ", res_date=" + res_date + ", res_date_from=" + res_date_from + ", res_date_to="
				+ res_date_to + "]";
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

	public void saveReservation() {

		if (res_id == null || res_id.equals("") == true) {
			this.res_id = null;

			Model.batch(Delivery.class).insert(this);
		} else {

			Model.batch(Delivery.class).update(this);
		}
	}

}
