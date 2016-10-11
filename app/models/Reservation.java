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
public class Reservation extends Model {
	@Id(Generator.UUID)
	public String resv_id;

	public String resv_account_id;
	public int resv_numb_pers;
	public Date res_date;

	public Date res_date_from;
	public Date res_date_to;

	

	@Override
	public String toString() {
		return "Reservation [resv_id=" + resv_id + ", resv_account_id=" + resv_account_id + ", resv_numb_pers="
				+ resv_numb_pers + ", res_date=" + res_date + ", res_date_from=" + res_date_from + ", res_date_to="
				+ res_date_to + "]";
	}

	public static Query<Reservation> all() {
		return Model.all(Reservation.class);
	}

	public static Reservation getByID(String ID) {
		try {
			Reservation obj = Model.getByKey(Reservation.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveReservation() {

		if (resv_id == null || resv_id.equals("") == true) {
			this.resv_id = null;

			Model.batch(Reservation.class).insert(this);
		} else {

			Model.batch(Reservation.class).update(this);
		}
	}

}
