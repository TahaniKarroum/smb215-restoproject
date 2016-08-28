package utils;

public class Enums {

	public enum MesureUnit {
		none(0), kg(1), litre(2);
		private int mesureUnitId;

		MesureUnit(int id) {
			mesureUnitId = id;
		}

		public int getMesureUnitId() {
			return mesureUnitId;
		}
	}

	public enum LogType {
		Product, Vendor, Sale, PurchasedOrder, Login, Logout, Client, ReturnedSales, CanceledSale;
	}

	public enum ProductType {
		none, menu, stock;
	}

	public enum categoryType {
		none, menu, stock;
	}

	public enum clientType {
		None, table, TakeOut;
	}

	public enum saleOrderType {
		None, indoor, takeout, delivery;
	}

	public enum MeasureUnitMode {
		None, One, Multiple;
	}

	public enum ProductStatus {
		NotDefined(0, "Not Defined"), Under(1, "Under"), Near(2, "Near"), Normal(3, "Normal"), Above(4, "Above");

		private int productStatusId;
		private String name;

		ProductStatus(int id, String aname) {
			productStatusId = id;
			name = aname;
		}

		public int getProductStatus() {
			return productStatusId;
		}

		public enum DiscountType {
			None, percentage, amount;
		}

		public String getStatusName() {
			return name;
		}
	}

	public enum VendorType {
		None, importer, distributor, wholesaler, industry;
	}

	public enum ShippingMethod {
		None, RoadTransport, ShippingByShip, RiverTransport, AirTransport, InternalTransport, ExternalTransport;
	}

	public enum VendorPaymentType_ID {
		None, Short_term, Medium_term, long_term;
	}
}