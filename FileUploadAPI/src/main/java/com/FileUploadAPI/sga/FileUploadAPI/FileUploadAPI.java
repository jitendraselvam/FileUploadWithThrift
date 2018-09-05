package com.FileUploadAPI.sga.FileUploadAPI;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.FileUploadAPI.sga.FileUploadAPI.Services.DownloadService;
import com.FileUploadAPI.sga.FileUploadAPI.Services.DownloadServiceImpl;
import com.FileUploadAPI.sga.FileUploadAPI.Services.EchoService;
import com.FileUploadAPI.sga.FileUploadAPI.Services.EchoServiceImpl;
import com.FileUploadAPI.sga.FileUploadAPI.Services.UploadService;

/*
 * This is the File upload server
 */

public class FileUploadAPI 
{
	public static void main( String[] args )
    {
    	final int uploadPort = Integer.getInteger("port.upload", 10400);
    	final int echoPort = Integer.getInteger("port.echo", 10004);
        final int downloadPort = Integer.getInteger("port.download", 10040);
        
    	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
        executor.execute(new Runnable() {
            public  void  run () {
                try {
                    startEchoServer (echoPort);
                } catch (TTransportException e) {
                    e . printStackTrace ();
                }
            }
        });

        executor.execute(new Runnable() {
            public  void  run () {
                try {
                    startDownloadServer(downloadPort);
                } catch (TTransportException e) {
                    e . printStackTrace ();
                }
            }
        });

        executor.execute(new Runnable() {
            public  void  run () {
                try {
                    startUploadServer(uploadPort);
                } catch (TTransportException e) {
                    e . printStackTrace ();
                }
            }
        });
    }

    private static void startEchoServer(int port) throws TTransportException {
        TServerTransport transport = new TServerSocket(port);
        TSimpleServer.Args simpleServerArgs = new TSimpleServer.Args(transport);
        EchoServiceImpl serviceImpl = new EchoServiceImpl();
        EchoService . Processor < EchoService . Iface > processor =  new  EchoService . Processor < EchoService . Iface > (serviceImpl);
        simpleServerArgs.processor(processor);
        TSimpleServer simpleServer = new TSimpleServer(simpleServerArgs);
        System.out.println("Start echo service.");
        simpleServer.serve();
    }

    private static void startDownloadServer(int port) throws TTransportException {
        TServerTransport transport = new TServerSocket(port);
        TSimpleServer.Args simpleServerArgs = new TSimpleServer.Args(transport);
        DownloadServiceImpl serviceImpl = new DownloadServiceImpl();
        DownloadService.Processor<DownloadService.Iface> processor = new DownloadService.Processor<DownloadService.Iface>(serviceImpl);
        simpleServerArgs.processor(processor);
        TSimpleServer simpleServer = new TSimpleServer(simpleServerArgs);
        System.out.println("Start download service.");
        simpleServer.serve();
    }

    private static void startUploadServer(int port) throws TTransportException {
        TServerTransport transport = new TServerSocket(port);
        TSimpleServer.Args simpleServerArgs = new TSimpleServer.Args(transport);
        Server serviceImpl = new Server();
        UploadService.Processor<UploadService.Iface> processor = new UploadService.Processor<UploadService.Iface>(serviceImpl);
        simpleServerArgs.processor(processor);
        TSimpleServer simpleServer = new TSimpleServer(simpleServerArgs);
        System.out.println("Start upload service.");
        simpleServer.serve();
    }
}

