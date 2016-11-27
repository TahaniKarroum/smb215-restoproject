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
public class Site extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String SiteName;
	public String Location;
 

	@Override
	public String toString() {
		return "Site [ID=" + ID + ", SiteName=" + SiteName + ", Location=" + Location + "]";
	}

	public static Query<Site> getAllSites() {
		return Model.all(Site.class);
	}
	
	public static Query<Site> all() {
		return Model.all(Site.class);
	}

	public static Site getByID(String ID) {
		try {
			Site obj = Model.getByKey(Site.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void savesite() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(Site.class).insert(this);
		} else {

			Model.batch(Site.class).update(this);
		}
	}
	
	

}
