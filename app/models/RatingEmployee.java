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
public class RatingEmployee extends Model {
	@Id(Generator.UUID)
	public String ID;

	public String EmployeeID;
	public String Rating;

	@Override
	public String toString() {
		return "RatingEmployee [ID=" + ID + ", EmployeeID=" + EmployeeID + ", Rating=" + Rating + "]";
	}
	
	


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

	public String getStars2() {

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
	public void save() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(RatingEmployee.class).insert(this);
		} else {

			Model.batch(RatingEmployee.class).update(this);
		}
	} 

	
}
