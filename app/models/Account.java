package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Account extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String user_ID;
	public String type;
	public String status;
	
	public double balance;
	public double credit;
	public double debit;
	
	public String created_date;
	public String created_by;
	public String updated_date;
	public String updated_by;
	
	@Override
	public String toString() {
		return "Account [ID=" + ID + ", user_ID=" + user_ID + ", type=" + type + ", status=" + status + ", balance="
				+ balance + ", credit=" + credit + ", debit=" + debit + ", created_date=" + created_date
				+ ", created_by=" + created_by + ", updated_date=" + updated_date + ", updated_by=" + updated_by + "]";
	}
	
	public static Query<Account> getAllAccounts() {
		return Model.all(Account.class);
	}
	
	public static Account getByID(String ID) {
		try {
			Account obj = Model.getByKey(Account.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void saveAccount(String sessionID) {
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
		if (ID == null || ID.equals("") == true) {
			this.ID = null;            
			this.created_date = strDate;
			this.created_by = sessionID;
			Model.batch(Account.class).insert(this);
		} else {
			this.updated_date = strDate;
			this.updated_by = sessionID;
			Model.batch(Account.class).update(this);
		}
	}

}
