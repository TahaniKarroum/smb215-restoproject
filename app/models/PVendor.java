package models;

import java.util.ArrayList;
import java.util.List;

import controllers.AppLogs;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums.LogType;

@Entity
public class PVendor extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String name;
	public String contactName;
	public String email;
	public String landPhone;
	public String mobile;
	public String fax;
	public String website;
	@Max(512)
	public String address;
	@Max(512)
	public String remarks;
	public double balance;
	public boolean isActive;
	public int vendorNumber;
	public int vendorType_ID;
	public int shippingMethod_ID;
	public String coverage;
	public int currency_ID;
	public double debt;

	@Override
	public String toString() {
		return "PVendor [ID=" + ID + ", name=" + name + ", contactName=" + contactName + ", email=" + email
				+ ", landPhone=" + landPhone + ", mobile=" + mobile + ", fax=" + fax + ", website=" + website
				+ ", address=" + address + ", remarks=" + remarks + ", balance=" + balance + ", isActive=" + isActive
				+ ", vendorNumber=" + vendorNumber + ", vendorType_ID=" + vendorType_ID + ", shippingMethod_ID="
				+ shippingMethod_ID + ", coverage=" + coverage + ", currency_ID=" + currency_ID + ", debt=" + debt
				+ "]";
	}

	public void saveVendor() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			setVendorNumber();
			isActive = true;
			Model.batch(PVendor.class).insert(this);
			 AppLogs.createLog(LogType.Vendor.ordinal(), ID);
		} else {
			Model.batch(PVendor.class).update(this);
		}
	}

	public static PVendor getByID(String ID) {
		try {
			PVendor vendor = Model.getByKey(PVendor.class, ID);
			return vendor;
		} catch (Exception e) {
			return null;
		}
	}

	public static Query<PVendor> all() {
		return Model.all(PVendor.class);
	}

	public void setVendorNumber() {
		PVendor lastVendor = Model.all(PVendor.class).order("-vendorNumber").get();
		vendorNumber = 1;
		if (lastVendor != null)
			vendorNumber += lastVendor.vendorNumber;
	}

	public boolean canBeDeleted() {
		return true;
	}

	public String getProductsByVendor() {
		String products = "";
		List<Vendor_Product> vendorProductsList = Model.all(Vendor_Product.class).filter("Vendor_ID", this.ID).fetch();
		List<Product> productsList = new ArrayList<Product>();
		if (vendorProductsList != null && vendorProductsList.size() > 0) {
			for (Vendor_Product vp : vendorProductsList) {
				productsList.add(vp.getProduct());
			}
		}
		if (productsList != null && productsList.size() > 0) {
			for (Product product : productsList) {
				products += product.name + ",";
			}
		}
		if (products.length() > 0)
			products = products.substring(0, products.length() - 2);
		return products;
	}

	public double getCreditTotal() {
		double total = 0;
		return total;
	}
}
