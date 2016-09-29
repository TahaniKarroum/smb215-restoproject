package models;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums;

@Entity
public class Category extends Model {

	@Id(Generator.UUID)
	public String ID;

	public String name;
	@Max(1024)
	public String description;
	public boolean isActive;
	public int categoryType_ID;

	@Override
	public String toString() {
		return "Category [ID=" + ID + ", name=" + name+"]";
	}

	public static Query<Category> all() {
		return Model.all(Category.class);
	}

	public static Query<Category> allMenuCategories() {
		return Model.all(Category.class).filter("isActive", true).filter("categoryType_ID", Enums.categoryType.menu.ordinal());
	}
	public static Query<Category> allStockCategories() {
		return Model.all(Category.class).filter("isActive", true).filter("categoryType_ID", Enums.categoryType.stock.ordinal());
	}
	public void saveCategory() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			this.isActive = true;
			Model.batch(Category.class).insert(this);
		} else {
			Model.batch(Category.class).update(this);
		}
	}

	public static Category getByID(String ID) {
		try {
			Category obj = Model.getByKey(Category.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
}
