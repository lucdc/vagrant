package be.ordina.vagrant.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoFactory {
	private static MongoClient mongoClient;
	private static DB db;
	
	private final static String DB_NAME = "twitter";
	private final static String COLLECTION_NAME = "tweet";
	
	public static DB getDB() throws UnknownHostException{
		if (db == null) {
			init();
		}
		return db;
		
	}
	
	public static DBCollection getCollection() throws UnknownHostException{
		System.out.println("Getting Collection:" + COLLECTION_NAME);
		DBCollection coll = getDB().getCollection(COLLECTION_NAME);
		return coll;
	}
	
	private static synchronized void init() throws UnknownHostException {
		System.out.println("Try connecting to MongoDB server: localhost:27017");
		mongoClient = new MongoClient( "localhost" , 27017 );
		db = mongoClient.getDB( DB_NAME );
		System.out.println("Connection established...");
	}
}
