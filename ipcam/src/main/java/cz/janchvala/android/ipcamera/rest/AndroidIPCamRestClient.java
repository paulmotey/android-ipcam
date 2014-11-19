package cz.janchvala.android.ipcamera.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;

import cz.janchvala.android.ipcamera.rest.dto.SessionDTO;

/**
 * simple http rest interface template for (un)registering session object
 * <p/>
 * Created by jan on 10.11.2014.
 */
@Rest(converters = {JacksonJsonConverter.class})
public interface AndroidIPCamRestClient extends RestClientRootUrl, RestClientSupport {
    @Get("/rest/session/register?name={name}&url={url}&content={content}&status=1")
    SessionDTO registerSession(String name, String url, String content);

    @Get("/rest/session/{id}/close")
    void unregisterSession(long id);

}
