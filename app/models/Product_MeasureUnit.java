package models;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import utils.Enums.LogType;

@Entity
public class Product_MeasureUnit extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String product_ID;
	public String measureUnit_ID;
	public double cost;
	public double price;
	public double quantity;
	public boolean isBase;

	public boolean isForSale;
	public boolean isForPurchase;

	@Override
	public String toString() {
		return "Product_MeasureUnit [ID=" + ID + ", product_ID=" + product_ID + ", measureUnit_ID=" + measureUnit_ID
				+ ", price=" + price + ", quantity=" + quantity + ", isBase=" + isBase + ", isForSale=" + isForSale
				+ ", isForPurchase=" + isForPurchase + "]";
	}

	public static Query<Product_MeasureUnit> all() {
		return Model.all(Product_MeasureUnit.class);
	}

	public void saveProductMeasureUnit() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Product_MeasureUnit.class).insert(this);
		} else {
			Model.batch(Product_MeasureUnit.class).update(this);
		}
	}

	public static Product_MeasureUnit getByID(String ID) {
		try {
			Product_MeasureUnit product_MeasureUnit = Model.getByKey(Product_MeasureUnit.class, ID);
			return product_MeasureUnit;
		} catch (Exception e) {
			return null;
		}
	}

	public MeasureUnit getMeasureUnit() {
		try {
			MeasureUnit measureUnit = Model.getByKey(MeasureUnit.class, this.measureUnit_ID);
			return measureUnit;
		} catch (Exception e) {
			return null;
		}

	}

	public Product getProduct() {
		try {
			Product product = Model.getByKey(Product.class, this.product_ID);
			return product;
		} catch (Exception e) {
			return null;
		}
	}
}
