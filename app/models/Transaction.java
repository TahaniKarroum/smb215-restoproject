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
public class Transaction extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String account_ID;	
	public String user_ID;
	public String date;
	public String type;
	public String description;
	
	public double credit;
	public double debit;
	
	@Override
	public String toString() {
		return "Transaction [ID=" + ID + ", account_ID=" + account_ID + ", user_ID=" + user_ID + ", date=" + date
				+ ", type=" + type + ", description=" + description + ", credit=" + credit + ", debit=" + debit + "]";
	}
	
	public static Query<Transaction> all() {
		return Model.all(Transaction.class);
	}
}
