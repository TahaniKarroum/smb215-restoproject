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

import org.apache.commons.lang.NumberUtils;

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

	@SuppressWarnings("deprecation")
	public String getStars() {

		String res = "<div class=\"star-rating\">";
		int i=1;
		for(i=1 ; i<=5; i++)
		{
			if(Rating != null )
			
				try {
					if(Integer.parseInt(Rating) == i)
						res += "  <input type=\"radio\" name=\"example\" class=\"rating rating"+i+"\" value=\""+i+"\" checked />";
					else
						res += "  <input type=\"radio\" name=\"example\" class=\"rating rating"+i+"\" value=\""+i+"\" />";
					} catch(Exception e)
					{
						res += "  <input type=\"radio\" name=\"example\" class=\"rating rating"+i+"\" value=\""+i+"\" />";
					}
			
			else
				res += "  <input type=\"radio\" name=\"example\" class=\"rating rating"+i+"\" value=\""+i+"\" />";
		}
		res += "</div>";

		return res;
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
