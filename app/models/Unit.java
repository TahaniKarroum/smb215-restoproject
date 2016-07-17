package models;

import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;

public class Unit extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String unit;

	public static Query<Unit> all() {
		return Model.all(Unit.class);
	}

	public void saveUnit() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Unit.class).insert(this);
		} else {
			Model.batch(Unit.class).update(this);
		}
	}

	public static Unit getByID(String ID) {
		try {
			Unit obj = Model.getByKey(Unit.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

}
