package models;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;

@Entity
public class MeasureType extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String name;

	@Override
	public String toString() {
		return "MeasureType [ID=" + ID + ", name=" + name + "]";
	}

	public static Query<MeasureType> all() {
		return Model.all(MeasureType.class);
	}

	public void saveMeasureType() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(MeasureType.class).insert(this);
		} else {
			Model.batch(MeasureType.class).update(this);
		}
	}

	public static MeasureType getByID(String ID) {
		try {
			MeasureType measureType = Model.getByKey(MeasureType.class, ID);
			return measureType;
		} catch (Exception e) {
			return null;
		}
	}

}
