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
import de.phoenix.rs.key.UpdateEntity;

/**
 * This class provides the simple request methods to<br>
 * communicate with the backend web service.<br>
 * 
 * @author Matthias Eiserloh; Markus Wolf
 *
 */

public class PhoenixRequest {
    protected final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest";
    protected final static Client CLIENT = PhoenixClient.create();

    //Saves last operation state
    private int status;
    
    public PhoenixRequest() {
        this.status = -1;
    }
    
    public int getStatus() {
        return this.status;
    }
    /**
     * This methods sends a {@link PhoenixEntity} to the web service via <br>
     * the provided web resource.
     * 
     * @param webresource
     * @param phoenixEntity
     * @return returns operation status
     */
    protected int create(WebResource webresource, PhoenixEntity phoenixEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, phoenixEntity);
        this.status = response.getStatus(); 
        return this.status;
    }
    
    /**
     * This methods sends a {@link ConnectionEntity} to the web service via <br>
     * the provided web resource.
     * 
     * @param webresource
     * @param phoenixEntity
     * @return operation status
     */
    protected int create(WebResource webresource, ConnectionEntity connectionEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, connectionEntity);
        this.status = response.getStatus(); 
        return this.status;   
    }
    
    /**
     * This methods sends a {@link SelectEntity} to the web service via <br>
     * the provided web resource.
     * 
     * @param webresource
     * @param phoenixEntity
     * @return
     */
    protected <T extends PhoenixEntity> int create(WebResource webresource, SelectEntity<T> phoenixEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, phoenixEntity);
        this.status = response.getStatus(); 
        return this.status;   
    }
    
    /**
     * Sends a get request to the web service.
     * 
     * @param webresource
     * @param selectEntity
     * @return Object of type T, matching the selectEntity 
     */
    protected <T extends PhoenixEntity> T get(WebResource webresource, SelectEntity<T> selectEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectEntity);
        this.status = response.getStatus();
        if (this.status == 200)
            return EntityUtil.extractEntity(response);
        else
            return null;
    }
    
    /**
     * Sends a get request to the web service.
     * 
     * @param webresource
     * @param selectEntity
     * @return List of Object of type T, matching the selectEntity
     */
    protected <T extends PhoenixEntity> List<T> getList(WebResource webresource, SelectEntity<T> selectEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectEntity);
        this.status = response.getStatus();
        if (this.status == 200)
            return EntityUtil.extractEntityList(response);
        else
            return null; 
    }
    
    /**
     * Sends a "get-all" request to the web service.
     * 
     * @param webresource
     * @return all PhoenixEntities
     */
    protected <T extends PhoenixEntity> List<T> getAll(WebResource webresource) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, new SelectAllEntity<>());
        this.status = response.getStatus();
        if (this.status == 200)
            return EntityUtil.extractEntityList(response);
        else
            return null; 
    }
    
    /**
     * Sends a update or delete request to the web service.
     * 
     * @param webresource
     * @param selectEntity
     * @return operation status
     */
    protected <T extends PhoenixEntity> int change(WebResource webresource, SelectEntity<T> selectEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectEntity);
        this.status = response.getStatus();
        return response.getStatus(); 
    }
    
    protected <T extends PhoenixEntity> int change(WebResource webresource, ConnectionEntity connectionEntity) {
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, connectionEntity);
        this.status = response.getStatus();
        return response.getStatus(); 
    }
    
    protected <T extends PhoenixEntity> int update(WebResource webresource, UpdateEntity<T> updateEntity){
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, updateEntity);
        this.status = response.getStatus();
        return response.getStatus();
    }
}
