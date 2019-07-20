package com.contact_view.serializers;

import com.contact_view.util.ConfigUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class MongoClientFactory {


    private final String hosts = ConfigUtils.INSTANCE.getStringValue("MONGO_HOST");
    private final String port = ConfigUtils.INSTANCE.getStringValue("MONGO_PORT");
    private final boolean useAuthentication = ConfigUtils.INSTANCE.getBooleanValue("useAuthentication");
    private final String mongoUsername = ConfigUtils.INSTANCE.getStringValue("MONGO_USERNAME");
    private final String mongoPassword = ConfigUtils.INSTANCE.getStringValue("MONGO_PASSWD");
    private final String mongoDBName = ConfigUtils.INSTANCE.getStringValue("MONGO_DB_NAME");
    private final int connectionsPerHost = 50;

    private MongoOperations mongoOperations = null;
    private MongoClient mongoClient = null;

    public final MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public MongoClientFactory () {
        super();
        initMongoOperations(hosts, port, useAuthentication, mongoUsername,
                mongoPassword, mongoDBName, connectionsPerHost);
        System.out.println("Mongo connection created successfully, connections created: " + connectionsPerHost);
    }

    void initMongoOperations (String hosts, String port, boolean useAuthentication, String mongoUsername, String mongoPassword,
                         String mongoDBName, int connectionsPerHost) {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        MongoClientOptions options = builder.connectionsPerHost(connectionsPerHost).build();

        try {
            List<ServerAddress> seeds = new ArrayList<>();

            if (hosts != null && !hosts.isEmpty() && port != null && !port.isEmpty()) {
                String[] hostArray = hosts.split(",");
                for (String host : hostArray) {
                    seeds.add(new ServerAddress(host, Integer.parseInt(port)));
                }
            }

            if (useAuthentication) {
                List<MongoCredential> credentials = new ArrayList<>();
                credentials.add(MongoCredential.createScramSha1Credential(mongoUsername,
                        mongoDBName,
                        mongoPassword.toCharArray()));
                mongoClient = new MongoClient(seeds, credentials, options);
            } else {
                mongoClient = new MongoClient(seeds, options);
            }
            MongoTemplate mongoTemp = new MongoTemplate(mongoClient, mongoDBName);
            this.mongoOperations = (MongoOperations) mongoTemp;
            System.out.println("MongoClientFactory is initialized");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    @PreDestroy
    public void cleanUp() {
        try {
            if(mongoClient!=null){
                mongoClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
