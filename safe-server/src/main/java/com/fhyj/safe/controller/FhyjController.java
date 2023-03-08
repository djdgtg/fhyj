package com.fhyj.safe.controller;

import com.fhyj.safe.config.bean.FhyjUserProperties;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/14 18:03
 */
@RestController
@RequestMapping("fhyj")
@AllArgsConstructor
public class FhyjController {

    private final FhyjUserProperties fhyjUserProperties;

    @GetMapping("user")
    public FhyjUserProperties type() {
        return fhyjUserProperties;
    }
}
