package be.ordina.vagrant;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;

import be.ordina.vagrant.elasticsearch.ElasticSearchFactory;
import be.ordina.vagrant.mongodb.MongoFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Hello world!
 *
 */
public class App {
	
	
	public App() throws UnknownHostException {
		
	}
	
    public static void main( String[] args ) throws UnknownHostException, InterruptedException, ElasticsearchException, ExecutionException
    {
        System.out.println( "Start our tesing application..." );
        App app = new App();
        
        Collection<Tweet> tweets = app.getDummyTweets();
        
        app.addToElasticSearch(tweets);
        app.addToMongoDB(new ArrayList<DBObject>(tweets));
        
    }
    
    public void addToMongoDB( List<DBObject> tweets) throws UnknownHostException{
    	DBCollection coll = MongoFactory.getCollection();
    	coll.insert(tweets);
    	
    	System.out.println("Collection contains " + coll.getCount() + " elements.");
    }
    
    public void addToElasticSearch(Collection<Tweet> tweets) throws ElasticsearchException, InterruptedException, ExecutionException  {
    	for (BasicDBObject tweet : tweets) {
    		IndexResponse response = ElasticSearchFactory.addToIndex(tweet);
    		System.out.println("saved with id: " + response.getId());
		}
    }
    
	private Collection<Tweet> getDummyTweets() throws InterruptedException{
		Collection<Tweet> tweets = new ArrayList<Tweet>();
		
		tweets.add(new Tweet("Luc", "This owns!"));
		tweets.add(new Tweet("Tom", "Great job Luc!!"));
		tweets.add(new Tweet("Luc", "Thanks!"));
		tweets.add(new Tweet("Tom", "No problem."));

		return tweets;
	}
	
}

