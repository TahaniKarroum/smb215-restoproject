package models;

import java.util.List;

import play.*;
import siena.Model;
import siena.Entity;
import siena.Filter;
import siena.Generator;
import siena.Id;
import siena.Index;
import siena.Query;

@Entity
public class Country extends Model {

	@Id(Generator.UUID)
	public String ID;

	public String englishName;
	public String code;
	public String ICC;

	@Override
	public String toString() {
		return "Country [ID=" + ID + ", name=" + englishName + ", code=" + code + ", ICC=" + ICC + "]";
	}

	public static Query<Country> all() {
		return Model.all(Country.class);
	}

	public void saveCountry() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Country.class).insert(this);
		} else {
			Model.batch(Country.class).update(this);
		}
	}

	public static Country getByID(String ID) {
		try {
			Country obj = Model.getByKey(Country.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

}