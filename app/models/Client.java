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
public class Client extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String device_uid;
	
	public String name;
	
	public String phone;
	public String mobile;
	
	public String email;
	
	public String address;

	@Override
	public String toString() {
		return "Client [ID=" + ID + ", device_uid=" + device_uid + ", name=" + name + ", phone=" + phone + ", mobile="
				+ mobile + ", email=" + email + ", address=" + address + "]";
	}
	

	public static Query<Client> all() {
		return Model.all(Client.class);
    }
}
