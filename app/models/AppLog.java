package models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import play.*;
import siena.Model;
import siena.Entity;
import siena.Filter;
import siena.Generator;
import siena.Id;
import siena.Index;
import siena.Query;

@Entity
public class AppLog extends Model {

    @Id(Generator.UUID)
    public String ID;

    public String message;
    public Date submitDate;

    public int module_id;
    public int priority_id;
    public String employee_ID;
    public String link;
    
    public AppLog() {}
    
    public AppLog(String message, int module_id, int priority_id, String employee_ID) {
	super();
	this.message = message;
	this.module_id = module_id;
	this.priority_id = priority_id;
	this.employee_ID = employee_ID;
//	this.submitDate = new Date();
    }

    @Override
    public String toString() {
	return message;
    }

    public static Query<AppLog> all() {
	return Model.all(AppLog.class);
    }

    public void saveLog() {
	if (ID == null || ID.equals("") == true) {
	    this.ID = null;
	    submitDate = new Date();
	    Model.batch(AppLog.class).insert(this);
	} else {
	    Model.batch(AppLog.class).update(this);
	}
    }

    public static AppLog getByID(String ID) {
	try {
	    AppLog obj = Model.getByKey(AppLog.class, ID);
	    return obj;
	} catch (Exception e) {
	    return null;
	}
    }

    
    public String getModuleColor() {
    	switch (this.module_id) {
    	case 0:
    	    return "btn-success";
    	case 1:
    	    return "btn-primary";
    	case 2:
    	    return "btn-info";
    	case 3:
    	    return "btn-primary";
    	case 6:
    	    return "btn-danger";
    	case 7:
    	    return "btn-info";
    	case 8:
    	    return "btn-info";
    	default:
    	    return "";
    	}
        }

    public Employee getEmployee(){
	return Employee.getByID(employee_ID);
    }

    public boolean canBeDeleted(){
	return false;
    }

}
