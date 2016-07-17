package models;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;

@Entity
public class MeasureUnit extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String label;
	public boolean isStandard;
	public String baseUnit_ID;
	public String measureType_ID;

	@Override
	public String toString() {
		return "MeasureUnit [ID=" + ID + ", label=" + label + ", baseUnit_ID=" + baseUnit_ID + ", measureType_ID="
				+ measureType_ID + "]";
	}

	public static Query<MeasureUnit> all() {
		return Model.all(MeasureUnit.class);
	}

	public void saveMeasureUnit() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(MeasureUnit.class).insert(this);
		} else {
			Model.batch(MeasureUnit.class).update(this);
		}
	}

	public static MeasureUnit getByID(String ID) {
		try {
			MeasureUnit measureUnit = Model.getByKey(MeasureUnit.class, ID);
			return measureUnit;
		} catch (Exception e) {
			return null;
		}
	}

	public MeasureUnit getMeasureUnit() {
		try {
			MeasureUnit measureUnit = Model.getByKey(MeasureUnit.class, this.baseUnit_ID);
			return measureUnit;
		} catch (Exception e) {
			return null;
		}

	}

	public MeasureType getMeasureType() {
		try {
			MeasureType measureType = Model.getByKey(MeasureType.class, this.measureType_ID);
			return measureType;
		} catch (Exception e) {
			return null;
		}

	}
}
