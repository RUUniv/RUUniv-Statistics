package com.ruunivstatisticsserver.app.statistics.entity;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Api {
    CREATE_VERIFICATION("/v1/verification/email", "POST"),
    VERIFY_STUDENT("/v1/verification/email/verify", "POST"),
    DELETE_VERIFIED_STUDENTS("/v1/verification/email", "DELETE"),
    DELETE_VERIFIED_STUDENT("/v1/verification/email/", "DELETE"),
    GET_ALL_SUPPORTED_UNIVERSITY("/v1/verification/univ", "GET"),
    CHECK_SUPPORTED_UNIVERSITY("/v1/verification/univ/", "GET"),
    GET_VERIFIED_STUDENTS("/v1/verification/email", "GET");

    private String url;

    private String method;

    Api(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public static Api createByUrlAndMethod(String url, String method) {
        return Arrays.stream(Api.values())
                .filter(api -> api.getUrl().contains(url) && api.getMethod().equals(method))
                .findFirst().orElse(null);
    }
}
