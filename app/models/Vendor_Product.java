package models;

import java.util.List;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;

@Entity
public class Vendor_Product extends Model {
	@Id(Generator.UUID)
	public String ID;
	public double cost;
	public double shipDuration;
	public String Vendor_ID;
	public String Product_ID;

	@Override
	public String toString() {
		return "Vendor_Product [ID=" + ID + ", cost=" + cost + ", shipDuration=" + shipDuration + ", Vendor_ID="
				+ Vendor_ID + ", Product_ID=" + Product_ID + "]";
	}

	public static Query<Vendor_Product> all() {
		return Model.all(Vendor_Product.class);
	}

	public void saveVendor_Product() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Vendor_Product.class).insert(this);
		} else {
			Model.batch(Vendor_Product.class).update(this);
		}
	}

	public static Vendor_Product getByID(String ID) {
		try {
			Vendor_Product obj = Model.getByKey(Vendor_Product.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public static Vendor_Product getByVendorAndProduct(String vendor_id, String product_id) {
		try {
			Vendor_Product vp = all().filter("Vendor_ID", vendor_id).filter("Product_ID", product_id).get();
			return vp;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<Vendor_Product> getListByProductID(String product_ID) {
		List<Vendor_Product> list = all().filter("Product_ID", product_ID).fetch();
		return list;
	}

	public PVendor getVendor() {
		return PVendor.getByID(Vendor_ID);
	}

	public Product getProduct() {
		return Product.getByID(Product_ID);
	}

}
