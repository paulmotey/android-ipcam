package cz.janchvala.android.ipcamera.rest.dto;

import java.util.List;

/**
 * DTO object for session rest requests
 * <p/>
 * Created by jan on 10.11.2014.
 */
public class SessionDTO {
    private Long id;
    private String name;
    private List<String> urls;
    private String content;

    public SessionDTO() {
    }

    public SessionDTO(Long id, String name, List<String> urls, String content) {
        this.id = id;
        this.name = name;
        this.urls = urls;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
