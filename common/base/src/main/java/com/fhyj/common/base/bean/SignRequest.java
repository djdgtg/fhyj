package com.fhyj.common.base.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/20 11:17
 */
@Getter
@Setter
public class SignRequest {

    private String sign;

    private Long timestamp;

    private String json;

}
