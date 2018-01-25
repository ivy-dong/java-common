package com.arxanfintech.common.rest;

import com.arxanfintech.common.util.ErrorCode;

/**
 * 
 * Response http/rpc response
 *
 */
public class Response {
    public String Method;
    public ErrorCode Err;
    public String ErrMessage;
    public Object Payload;
}