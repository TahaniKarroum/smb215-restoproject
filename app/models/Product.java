package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import utils.Enums;

@Entity
public class Product extends Model {
	@Id(Generator.UUID)
	public String ID;
	public String name;
	public String barcode;
	public String imagePath;
	@Max(1024)
	public String description;

	public double lastPurchasedPrice;
	public int limitLevel;
	public int maxLimitLevel;
	public double qteInStock;
	public double stockCostPerYear;
	public double price;
	public double cost;
	@Max(256)
	public String stockLocation;
	public int nearLimit;
	public boolean isActive;
	public int status_id;
	public String mesureUnit_ID;// sale unit
	public String purchasedUnit_ID;
	public String category_ID;
	public String lastVendor_ID;
	public boolean isRemoved = false;
	public String barCodeimagePath = "/public/images/barcode.gif";
	public String manufacturer;// BrandID
	public String country_ID;
	public String countryCode;
	public int number;
	public float maxDiscount = 0.0f;
	public float currentDiscount;
	public int currentDiscountUnit;
	public boolean isTVAAble;
	public boolean isAllowAlarm;
	public boolean useBarcodeScale;
	public int numberOnSacle;
	public int measureUnitMode_ID;
	public int soldItemBeforeStartUp;
	public int priceType_ID;
	public int productType_ID;


	@Override
	public String toString() {
		return "Product [ID=" + ID + ", name=" + name + ", barcode=" + barcode + ", imagePath=" + imagePath
				+ ", description=" + description + ", lastPurchasedPrice=" + lastPurchasedPrice + ", limitLevel="
				+ limitLevel + ", maxLimitLevel=" + maxLimitLevel + ", qteInStock=" + qteInStock + ", stockCostPerYear="
				+ stockCostPerYear + ", stockLocation=" + stockLocation + ", nearLimit=" + nearLimit + ", isActive="
				+ isActive + ", status_id=" + status_id + ", mesureUnit_ID=" + mesureUnit_ID + ", purchasedUnit_ID="
				+ purchasedUnit_ID + ", category_ID=" + category_ID + ", lastVendor_ID=" + lastVendor_ID
				+ ", isRemoved=" + isRemoved + ", barCodeimagePath=" + barCodeimagePath + ", manufacturer="
				+ manufacturer + ", country_ID=" + country_ID + ", countryCode=" + countryCode + ", number=" + number
				+ ", maxDiscount=" + maxDiscount + ", currentDiscount=" + currentDiscount + ", currentDiscountUnit="
				+ currentDiscountUnit + ", isTVAAble=" + isTVAAble + ", isAllowAlarm=" + isAllowAlarm
				+ ", useBarcodeScale=" + useBarcodeScale + ", numberOnSacle=" + numberOnSacle + ", measureUnitMode_ID="
				+ measureUnitMode_ID + ", soldItemBeforeStartUp=" + soldItemBeforeStartUp + ", priceType_ID="
				+ priceType_ID + ", productType_ID=" + productType_ID + "]";
	}

	public static Query<Product> all() {
		return Model.all(Product.class);
	}

	public static Query<Product> allStockProducts() {
		return Model.all(Product.class).filter("productType_ID", Enums.ProductType.stock.ordinal()).order("name");
	}

	public static Query<Product> allMenuProducts() {
		return Model.all(Product.class).filter("productType_ID", Enums.ProductType.menu.ordinal()).order("name");
	}
	public String getCategoryName() {
		Category category = Category.getByID(category_ID);
		if (category == null)
			return "NoCategory";
		return category.name;
	}

	public void saveProduct() {
		if (ID == null || ID.equals("") == true) {
			this.ID = null;
			Model.batch(Product.class).insert(this);
		} else {
			Model.batch(Product.class).update(this);
		}
	}

	public static Product getByID(String ID) {
		try {
			Product obj = Model.getByKey(Product.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public String getUnitMeasureName() {
		MeasureUnit unit = MeasureUnit.getByID(mesureUnit_ID);
		if (unit == null)
			return "";
		return unit.label;
	}

	public String getPurchasedUnitName() {
		Unit unit = Unit.getByID(purchasedUnit_ID);
		if (unit == null)
			return "";
		return unit.unit;
	}

	public Category getCategory() {
		return Category.getByID(category_ID);
	}

	public String getCountry() {
		Country country = Model.all(Country.class).filter("ID", country_ID).get();
		if (country != null)
			return country.englishName;
		else
			return "";
	}

	public List<Product> getProductComposite() {
		List<Product> productsList = new ArrayList<Product>();
		List<Product_product_Composition> productCompositesList = Model.all(Product_product_Composition.class)
				.filter("principalProduct_ID", this.ID).fetch();
		if (productCompositesList != null && productCompositesList.size() > 0) {
			for (Product_product_Composition ppc : productCompositesList) {
				if (ppc != null)
					productsList.add(ppc.getProduct());
			}
		}
		return productsList;
	}

	public HashMap<String, Product_product_Composition> getMapProductComposite() {
		List<Product_product_Composition> productCompositesList = Model.all(Product_product_Composition.class)
				.filter("principalProduct_ID", this.ID).fetch();
		HashMap<String, Product_product_Composition> ppcMap = new HashMap<String, Product_product_Composition>();
		if (productCompositesList != null && productCompositesList.size() > 0) {
			for (Product_product_Composition ppc : productCompositesList) {
				ppcMap.put(ppc.product_ID, ppc);
			}
		}
		return ppcMap;
	}
}
