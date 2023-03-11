package opensearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.eclipse.jetty.util.Index;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.base.RestClientTransport;
import org.opensearch.client.base.Transport;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._global.IndexRequest;

public class OpensearchClientExample {
	public static void main(String[] args) {
		RestClient restClient = null;
		try{
			System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
			System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

			//Only for demo purposes. Don't specify your credentials in code.
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials("admin", "admin"));

			//Initialize the client with SSL and TLS enabled
			restClient = RestClient.builder(new HttpHost("localhost", 9200, "https")).
					setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
						@Override
						public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
							return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
						}
					}).build();
			Transport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
			OpenSearchClient client = new OpenSearchClient(transport);

			IndexData data = new IndexData("", "");
			IndexRequest<IndexData>

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
