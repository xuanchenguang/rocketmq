/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openmessaging;

import org.apache.openmessaging.internal.MessagingEndPointFactory;
import org.apache.openmessaging.internal.URISpecParser;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class MessagingEndPointManager {
    public static MessagingEndPoint getMessagingEndPoint(String url) {
        return getMessagingEndPoint(url, new Properties());
    }

    public static MessagingEndPoint getMessagingEndPoint(String url, Properties properties) {
        Map<String, List<String>> driverUrl = URISpecParser.parseURI(url);
        if (null == driverUrl || driverUrl.size() == 0) {
            throw new IllegalArgumentException("driver url parsed result.size ==0");
        }
        return MessagingEndPointFactory.createMessagingEndPoint(driverUrl, properties);
    }
}
