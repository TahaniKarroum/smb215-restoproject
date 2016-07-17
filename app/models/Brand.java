package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.util.*;

@Entity
public class Brand extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String name;
	@Max(1024)
	public String description;

	@Override
	public String toString() {
		return "Brand [ID=" + ID + ", name=" + name + ", description=" + description + "]";
	}

	public static Query<Brand> all() {
		return Model.all(Brand.class);
	}

	public void saveBrand() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Brand.class).insert(this);
		} else {
			Model.batch(Brand.class).update(this);
		}
	}

	public static Brand getByID(String ID) {
		try {
			Brand obj = Model.getByKey(Brand.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

}
