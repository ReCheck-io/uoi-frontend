package io.recheck.uoi.rest.dto;

import lombok.Data;

@Data
public class RequestAccessDTO {

    private String userId;
    private String systemId;
    private String uoi;

    public RequestAccessDTO(String userId, String systemId, String uoi) {
        this.userId = userId.trim();
        this.systemId = systemId.trim();
        this.uoi = uoi.trim();
    }
}
