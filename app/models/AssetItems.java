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
public class AssetItems extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String ItemName;
	public String ItemStatus;
	public String ItemDesc;
	public String ItemCost;
	public String ItemValue;
	public String SiteId;
 
 
	@Override
	public String toString() {
		return "AssetItems [ID=" + ID + ", ItemName=" + ItemName + ", ItemStatus=" + ItemStatus + ", ItemDesc="
				+ ItemDesc + ", ItemCost=" + ItemCost + ", ItemValue=" + ItemValue + "]";
	}

	public static Query<AssetItems> all() {
		return Model.all(AssetItems.class);
	}

	public static AssetItems getByID(String ID) {
		try {
			AssetItems obj = Model.getByKey(AssetItems.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getSiteName() {
		Site site = Site.getByID(SiteId);
		if (site == null)
			return "No Site";
		return site.SiteName;
	}

	public void save() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(AssetItems.class).insert(this);
		} else {

			Model.batch(AssetItems.class).update(this);
		}
	}

}
