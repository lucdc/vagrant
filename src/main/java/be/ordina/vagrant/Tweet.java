package be.ordina.vagrant;

import java.util.Date;

import com.mongodb.BasicDBObject;

public class Tweet extends BasicDBObject{

	private static final long serialVersionUID = 1L;

	public Tweet(String name, String text) {
		put("name", name);
		put("text", text);
		put("date", new Date());
	}
}
