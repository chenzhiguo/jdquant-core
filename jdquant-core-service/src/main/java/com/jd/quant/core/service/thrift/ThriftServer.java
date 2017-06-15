package com.jd.quant.core.service.thrift;

import com.jd.quant.core.service.thrift.handler.QuantCoreServiceHandler;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Thrift Server
 *
 * @author Zhiguo.Chen
 */
@Component
public class ThriftServer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftServer.class);

//    @Autowired
//    private StrategyCoreServiceHandler strategyCoreServiceHandler;
//
//    @Autowired
//    private RedisServiceHandler redisServiceHandler;

    @Autowired
    private QuantCoreServiceHandler quantCoreServiceHandler;

    @Value(value = "${thrift.serverPort}")
    private int thriftServerPort = 22006;

    @Value(value = "${thrift.protocol}")
    private String thriftProtocol = "compact";

    void simple(TMultiplexedProcessor multiplexedProcessor) {
        try {
            TServerTransport serverTransport = new TServerSocket(thriftServerPort);

            TThreadPoolServer.Args trArgs = new TThreadPoolServer.Args(serverTransport);
            //trArgs.processor(processor);
            trArgs.processor(multiplexedProcessor);
            trArgs.maxWorkerThreads(1024);
            // 使用二进制来编码应用层的数据
            trArgs.protocolFactory(getProtocolFactory());
            // 使用普通的socket来传输数据
            trArgs.transportFactory(new TTransportFactory());

            TServer server = new TThreadPoolServer(trArgs);

            LOGGER.info("Starting thrift server, port={}, protocol={}", thriftServerPort, thriftProtocol);
            server.serve();
            LOGGER.info("Thrift started, port={}, protocol={}", thriftServerPort, thriftProtocol);
        } catch (Exception e) {
            LOGGER.error("Error to start thrift server:{}", e.getMessage(), e);
        }
    }

    private TProtocolFactory getProtocolFactory() {
        if (thriftProtocol.equals("compact")) {
            return new TCompactProtocol.Factory();
        }

        return new TBinaryProtocol.Factory(true, true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            TMultiplexedProcessor multiplexedProcessor = new TMultiplexedProcessor();
//            StrategyCoreService.Processor<StrategyCoreServiceHandler> strategyCoreServiceProcessor
//                    = new StrategyCoreService.Processor<>(strategyCoreServiceHandler);
//            multiplexedProcessor.registerProcessor("strategyCoreService", strategyCoreServiceProcessor);
//            RedisService.Processor<RedisServiceHandler> redisServiceProcessor
//                    = new RedisService.Processor<>(redisServiceHandler);
//            multiplexedProcessor.registerProcessor("redisService", redisServiceProcessor);
            QuantCoreService.Processor<QuantCoreServiceHandler> quantCoreServiceHandlerProcessor
                    = new QuantCoreService.Processor<>(quantCoreServiceHandler);
            multiplexedProcessor.registerProcessor("quantCoreService", quantCoreServiceHandlerProcessor);

            Runnable simple = () -> simple(multiplexedProcessor);

            new Thread(simple).start();
            LOGGER.info("Thrift server started.");
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void setThriftServerPort(int thriftServerPort) {
        this.thriftServerPort = thriftServerPort;
    }

    public void setThriftProtocol(String thriftProtocol) {
        this.thriftProtocol = thriftProtocol;
    }
}
