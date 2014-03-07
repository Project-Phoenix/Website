package meta;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.phoenix.rs.EntityUtil;
import de.phoenix.rs.PhoenixClient;
import de.phoenix.rs.key.ConnectionEntity;
import de.phoenix.rs.key.PhoenixEntity;
import de.phoenix.rs.key.SelectAllEntity;
import de.phoenix.rs.key.SelectEntity;

public class PhoenixRequest {
    protected final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest";
    protected final static Client CLIENT = PhoenixClient.create();
    
    
    protected int create(WebResource webresource, PhoenixEntity phoenixEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, phoenixEntity);
        return response.getStatus();    
    }
    
    protected int create(WebResource webresource, ConnectionEntity connectionEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, connectionEntity);
        return response.getStatus();    
    }
    
    protected <T extends PhoenixEntity> T get(WebResource webresource, SelectEntity<T> selectEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectEntity);
        return EntityUtil.extractEntity(response); 
    }
    
    protected <T extends PhoenixEntity> List<T> getAll(WebResource webresource) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, new SelectAllEntity<>());
        return EntityUtil.extractEntityList(response); 
    }
    
    protected <T extends PhoenixEntity> int change(WebResource webresource, SelectEntity<T> selectEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectEntity);
        return response.getStatus(); 
    }
}
