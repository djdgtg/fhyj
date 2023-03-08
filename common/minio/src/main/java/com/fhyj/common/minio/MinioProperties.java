package com.fhyj.common.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/2 17:38
 */
@Getter
@Setter
public class MinioProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
}
