/*
 Copyright 2015-2020 Peter-Josef Meisch (pj.meisch@sothawo.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.sothawo.mapjfx.offline;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that keeps information about a cached object.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
class CachedDataInfo implements Serializable {

    /** the content-type of the data. */
    private String contentType;

    /** the content-encoding. */
    private String contentEncoding;

    /** the response headers. */
    private Map<String, List<String>> headerFields = Collections.emptyMap();

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "CachedDataInfo{" +
            "contentType='" + contentType + '\'' +
            ", contentEncoding='" + contentEncoding + '\'' +
            ", headerFields=" + headerFields +
            '}';
    }

    public void setFromHttpUrlConnection(HttpURLConnection httpUrlConnection) {
        contentType = httpUrlConnection.getContentType();
        contentEncoding = httpUrlConnection.getContentEncoding();
        headerFields = new HashMap<>();
        headerFields.putAll(httpUrlConnection.getHeaderFields());
    }
}
