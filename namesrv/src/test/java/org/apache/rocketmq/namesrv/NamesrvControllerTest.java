package org.apache.rocketmq.namesrv;

import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class NamesrvControllerTest {
    private static final int RESTART_NUM = 3;
    protected Logger logger = LoggerFactory.getLogger(NamesrvController.class);

    /**
     * Tests if the controller can be properly stopped and started.
     *
     * @throws Exception If fails.
     */
    @Test
    public void testRestart() throws Exception {

        for (int i = 0; i < RESTART_NUM; i++) {
            NamesrvController namesrvController = new NamesrvController(
                    new NamesrvConfig(),
                    new NettyServerConfig()
            );
            boolean initResult = namesrvController.initialize();
            assertThat(initResult).isEqualTo(true);
            logger.info("nameserver is initialized " + initResult);
            namesrvController.start();
            logger.info("nameserver is started");

            namesrvController.shutdown();
            logger.info("nameserver is stopped");
        }
    }


}