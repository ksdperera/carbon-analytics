/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.database.query.manager;

import org.wso2.carbon.database.query.manager.exception.QueryTemplateNotAvailableException;
import org.wso2.carbon.database.query.manager.util.DatabaseMetaData;
import java.util.Map;

/**
 * Created by sajithd on 10/21/17.
 */
public class QueryConfigReader {
    private Map<String, String> baseQueryConfigMap;
    private DatabaseMetaData currentQueryKey;

    public QueryConfigReader(Map<String, String> baseQueryConfig, DatabaseMetaData currentQueryKey) {
        this.baseQueryConfigMap = baseQueryConfig;
        this.currentQueryKey = currentQueryKey;
    }

    public String getQueryTemplate(String queryName) throws QueryTemplateNotAvailableException
    {
        String template = null;
        if (baseQueryConfigMap != null && currentQueryKey != null) {
            template = baseQueryConfigMap.get(queryName);
        }
        if(template != null){
            throw new QueryTemplateNotAvailableException("Database type : " + "query for name: " + queryName
                    + " not found in query map fro key " + currentQueryKey.toString());
        }
        return template;
    }
}
