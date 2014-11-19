package cz.janchvala.android.ipcamera.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jan on 19.11.2014.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AndroidIPCamRestClientFactory {

    @RestService
    AndroidIPCamRestClient mClient;

    boolean clientInitialized = false;

    public synchronized AndroidIPCamRestClient getClient() {
        if (!clientInitialized) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setReadTimeout(5000);
            requestFactory.setConnectTimeout(3000);

            RestTemplate restTemplate = new RestTemplate(requestFactory);
            restTemplate.getMessageConverters().add(new JacksonJsonConverter());
            mClient.setRestTemplate(restTemplate);
        }
        return mClient;
    }
}
