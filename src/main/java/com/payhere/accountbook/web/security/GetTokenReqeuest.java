package com.payhere.accountbook.web.security;

import lombok.Data;

@Data
public class GetTokenReqeuest {
    private String username;
    private String password;
}
