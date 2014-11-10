package be.ordina.vagrant.elasticsearch;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchFactory {
	private static TransportClient transportClient;
	
	private static String INDEX_NAME = "twitter";
	private static String INDEX_TYPE = "tweet";
	
	public static IndexResponse addToIndex(Map<String, Object> content) throws ElasticsearchException, InterruptedException, ExecutionException{

		//Create Index and set settings and mappings
		ActionFuture<IndicesExistsResponse> future = client().admin().indices().exists(new IndicesExistsRequest(INDEX_NAME));
		
		if( !future.get().isExists() ){
			CreateIndexRequestBuilder createIndexRequestBuilder = client().admin().indices().prepareCreate(INDEX_NAME);
			createIndexRequestBuilder.execute().actionGet();
		}

		//Add documents
		IndexRequestBuilder indexRequestBuilder = client().prepareIndex(INDEX_NAME, INDEX_TYPE);
		indexRequestBuilder.setSource(content);
		IndexResponse response = indexRequestBuilder.execute().actionGet();

		return response;
	}

	private static synchronized TransportClient client() {
		
		if (transportClient == null) {
			//Create Client
			System.out.println("Create the client...");
			Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
			transportClient = new TransportClient(settings);
			transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		}
		return transportClient;
	}
}
