package models;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;

@Entity
public class Product_product_Composition extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String principalProduct_ID;
	public String product_ID;
	public double quantity;
	public String measureUnit_ID;

	@Override
	public String toString() {
		return "Product_product_Composition [ID=" + ID + ", principalProduct_ID=" + principalProduct_ID
				+ ", product_ID=" + product_ID + ", quantity=" + quantity + ", measureUnit_ID=" + measureUnit_ID + "]";
	}

	public static Query<Product_product_Composition> all() {
		return Model.all(Product_product_Composition.class);
	}

	public void saveProductComposition() {
		if (ID == null || ID.isEmpty()) {
			this.ID = null;
			Model.batch(Product_product_Composition.class).insert(this);
		} else {
			Model.batch(Product_product_Composition.class).update(this);
		}
	}

	public static Product_product_Composition getByID(String ID) {
		try {
			Product_product_Composition obj = Model.getByKey(Product_product_Composition.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public Product getProduct() {
		try {
			return Model.getByKey(Product.class, this.product_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public MeasureUnit getUnit() {
		try {
			return Model.getByKey(MeasureUnit.class, this.measureUnit_ID);
		} catch (Exception e) {
			return null;
		}
	}

}
