package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import siena.Entity;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;

@Entity
public class Employee extends Model {
    @Id(Generator.UUID)
    public String ID;
    public String name;
    @Max(512)
    public String address;
    public String username;
    public String password;

    public String email;
    public String phone;
    public String mobile;
    public String extensionNb;
    public long profileNumber;

    public boolean isActive;
    public boolean isAdmin;

    @Override
    public String toString() {
	return "Employee [ID=" + ID + ", name=" + name + ", address=" + address + ", username=" + username
		+ ", password=" + password + ", email=" + email + ", phone=" + phone + ", mobile=" + mobile
		+ ", extensionNb=" + extensionNb + ", profileNumber=" + profileNumber + ", isActive=" + isActive
		+ ", isAdmin=" + isAdmin + "]";
    }

    public static Query<Employee> all() {
	return Model.all(Employee.class);
    }

    public void saveEmployee() {
	if (ID == null || ID.equals("") == true) {
	    this.ID = null;
	    setProfileNumber();
	    Model.batch(Employee.class).insert(this);
	} else {
	    Model.batch(Employee.class).update(this);
	}
    }

    public static Employee getByID(String ID) {
	try {
	    Employee emp = Model.getByKey(Employee.class, ID);
	    return emp;
	} catch (Exception e) {
	    return null;
	}
    }

    public boolean setProfileNumber() {
	Employee lastEmployee = Model.all(Employee.class).order("-profileNumber").get();
	profileNumber = 1;
	if (lastEmployee != null)
	    profileNumber += lastEmployee.profileNumber;
	return true;
    }
 
}
