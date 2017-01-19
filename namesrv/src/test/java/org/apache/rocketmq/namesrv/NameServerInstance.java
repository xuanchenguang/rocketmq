package org.apache.rocketmq.namesrv;

import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public class NameServerInstance {
    protected static NamesrvController nameSrvController = null;
    protected static NettyServerConfig nettyServerConfig = new NettyServerConfig();
    protected static NamesrvConfig namesrvConfig = new NamesrvConfig();

    @BeforeClass
    public static void startup() throws Exception {
        nettyServerConfig.setListenPort(9876);
        nameSrvController = new NamesrvController(namesrvConfig, nettyServerConfig);
        boolean initResult = nameSrvController.initialize();
        Assert.assertTrue(initResult);
        nameSrvController.start();
    }

    @AfterClass
    public static void shutdown() throws Exception {
        if (nameSrvController != null) {
            nameSrvController.shutdown();
        }
        //maybe need to clean the file store. But we do not suggest deleting anything.
    }
}
