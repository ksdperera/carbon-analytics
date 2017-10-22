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

package org.wso2.carbon.database.query.manager.util;

import java.util.Map;

/**
 * Pojo class for database meta data and template queries map.
 */
public class DatabaseQueryConfig {
    private Map<DatabaseMetaData, Map<String, String>> queriesMap;

    public Map<DatabaseMetaData, Map<String, String>> getQueriesMap() {
        return queriesMap;
    }

    public void setQueriesMap(Map<DatabaseMetaData, Map<String, String>> queriesMap) {
        this.queriesMap = queriesMap;
    }

    public Map<String, String> getQueries(DatabaseMetaData databaseMetaData) {
        return queriesMap.get(databaseMetaData);
    }

    public void setQueries(DatabaseMetaData databaseMetaData, Map<String, String> queries){
        this.queriesMap.put(databaseMetaData, queries);
    }
}
