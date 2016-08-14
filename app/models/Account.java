package models;

import play.*;
import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

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
	
	public static Query<Account> all() {
		return Model.all(Account.class);
	}

}
