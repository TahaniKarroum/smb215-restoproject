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
public class Comment extends Model {
	@Id(Generator.UUID)
	public String ID;
	
	public String ComTitle;
	public String  ComDesc;
 
 
	@Override
	public String toString() {
		return "Comment [ID=" + ID + ", ComTitle=" + ComTitle + ", ComDesc=" + ComDesc + "]";
	}

	public static Query<Comment> getAllSites() {
		return Model.all(Comment.class);
	}
	
	public static Query<Comment> all() {
		return Model.all(Comment.class);
	}

	public static Comment getByID(String ID) {
		try {
			Comment obj = Model.getByKey(Comment.class, ID);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	public void save() {

		if (ID == null || ID.equals("") == true) {
			this.ID = null;

			Model.batch(Comment.class).insert(this);
		} else {

			Model.batch(Comment.class).update(this);
		}
	}
	
	

}
