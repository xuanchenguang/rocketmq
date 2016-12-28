/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openmessaging.internal;


import org.apache.openmessaging.MessagingEndPoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


class MessagingEndPointAdapter {
    private static Map<String, String> serviceEndPointClassMap = new HashMap<String, String>();

    static {
        serviceEndPointClassMap.put(ServiceConstants.DEFAULT_SERVICE_END_POINT,
                ServiceConstants.DEFAULT_SERVICE_IMPL);
    }

    static MessagingEndPoint instantiateMessagingEndPoint(String url, Properties properties)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        String serviceImpl = ServiceConstants.DEFAULT_SERVICE_IMPL;
        if (serviceEndPointClassMap.containsKey(url))
            serviceImpl = serviceEndPointClassMap.get(url);
        Class<?> serviceEndPointClass = Class.forName(serviceImpl);
        if (serviceEndPointClass == null)
            return null;

        String serviceUrl = ServiceConstants.DEFAULT_SERVICE_DISCOVERY_URL;
        if (properties.get(ServiceConstants.URL) != null) {
            String[] propertySplits = ((String) properties.get(ServiceConstants.URL)).split(ServiceConstants.PARAM_SEPARATOR);
            if (propertySplits.length > 0) {
                serviceUrl = propertySplits[0];
                for (int index = 1; index < propertySplits.length; index++) {
                    String[] kv = propertySplits[index].split(ServiceConstants.KV_SEPARATOR);
                    properties.put(kv[0], kv[1]);
                }
            }
        }
        properties.remove(ServiceConstants.URL);
        properties.put(ServiceConstants.DISCOVERY_URL, serviceUrl);
        Class[] paramTypes = {Properties.class};
        Constructor constructor = serviceEndPointClass.getConstructor(paramTypes);
        assert constructor != null;
        return (MessagingEndPoint) constructor.newInstance(properties);
    }
}