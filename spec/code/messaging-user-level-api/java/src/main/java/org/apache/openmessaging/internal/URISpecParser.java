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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanyin
 */
public class URISpecParser {
    public static Map<String, List<String>> parseURI(String uri) {
        if (uri == null || uri.length() == 0) {
            return new HashMap<String, List<String>>();
        }

        int spiIndex = 0;
        int index = uri.indexOf(ServiceConstants.URL_SEPARATOR);
        Map<String, List<String>> results = new HashMap<String, List<String>>();
        String protocol = uri.substring(0, index);
        List<String> protocolSet = new ArrayList<String>();
        protocolSet.add(protocol);
        results.put(ServiceConstants.PROTOCOL_NAME, protocolSet);
        if (index > 0) {
            String spi;
            spiIndex = uri.indexOf(ServiceConstants.URL_SEPARATOR, index + 1);
            if (spiIndex > 0) {
                spi = uri.substring(index + 1, spiIndex);
            } else {
                spi = uri.substring(index + 1);
            }
            List<String> spiSet = new ArrayList<String>();
            spiSet.add(spi);
            results.put(ServiceConstants.SPI_NAME, spiSet);
        }
        if (spiIndex > 0) {
            String urlList = uri.substring(spiIndex + 1);
            String[] list = urlList.split(ServiceConstants.LIST_SEPARATOR);
            if (list.length > 0) {
                results.put(ServiceConstants.URL_NAME, Arrays.asList(list));
            }
        }
        return results;
    }
}
