package org.apache.rocketmq.namesrv.kvconfig;

import org.apache.rocketmq.common.namesrv.NamesrvUtil;
import org.apache.rocketmq.namesrv.NameServerInstance;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KVConfigManagerTest extends NameServerInstance {
    private static KVConfigManager kvConfigManager;

    @BeforeClass
    public static void setup() throws Exception {
        kvConfigManager = new KVConfigManager(nameSrvController);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }

    @Test
    public void testPutKVConfig() {
        kvConfigManager.load();
        kvConfigManager.putKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest", "test");
        byte[] kvConfig = kvConfigManager.getKVListByNamespace(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG);
        assertThat(kvConfig).isNotNull();
        String value = kvConfigManager.getKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        assertThat(value).isEqualTo("test");
    }

    @Test
    public void testDeleteKVConfig() {
        kvConfigManager.deleteKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        byte[] kvConfig = kvConfigManager.getKVListByNamespace(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG);
        assertThat(kvConfig).isNull();
        Assert.assertTrue(kvConfig == null);
        String value = kvConfigManager.getKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        assertThat(value).isNull();
    }

}