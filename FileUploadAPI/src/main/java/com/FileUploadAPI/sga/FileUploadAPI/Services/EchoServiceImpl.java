package com.FileUploadAPI.sga.FileUploadAPI.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.TException;

public class EchoServiceImpl implements EchoService.Iface {
    public String echo(String input) throws TException {
        try {
            return "from " + InetAddress.getLocalHost().getHostAddress() + " : " + input;
        } catch (UnknownHostException e) {
            throw new TException(e);
        }
    }
}
