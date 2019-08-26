package com.framecloud.common.base.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailMessageTemplate extends MessageTemplate {

    /**
     * email地址
     */
    private String email;
}
