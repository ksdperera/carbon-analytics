/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.wso2.carbon.database.query.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.config.ConfigurationException;
import org.wso2.carbon.config.provider.ConfigProvider;
import org.wso2.carbon.database.query.manager.config.Component;
import org.wso2.carbon.database.query.manager.config.ComponentChildConfiguration;
import org.wso2.carbon.database.query.manager.config.Database;
import org.wso2.carbon.database.query.manager.config.RootConfiguration;
import org.wso2.carbon.database.query.manager.internal.DataHolder;
import org.wso2.carbon.database.query.manager.util.DatabaseMetaData;
import org.wso2.carbon.database.query.manager.util.DatabaseQueryConfig;

/**
 * Holds the database queries.
 */
public class QueryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryManager.class);

    public QueryConfigReader init(String componentName, DatabaseMetaData databaseMetaData,
                                  DatabaseQueryConfig defaultDatabaseQueryConfig) {
        DatabaseQueryConfig baseDatabaseQueryConfig = mergeConfigs(componentName,
                defaultDatabaseQueryConfig);
        return new QueryConfigReader(baseDatabaseQueryConfig, databaseMetaData);
    }

    private DatabaseQueryConfig mergeConfigs(String componentNamespace, DatabaseQueryConfig databaseQueryConfig) {
        ConfigProvider configProvider = DataHolder.getInstance().getConfigProvider();
        if (configProvider != null) {
            try {
                RootConfiguration rootConfiguration = configProvider.getConfigurationObject(RootConfiguration.class);
                if (null != rootConfiguration && null != rootConfiguration.components) {
                    for (Component component : rootConfiguration.components) {
                        ComponentChildConfiguration componentChildConfiguration = component.getComponent();
                        if (null != componentChildConfiguration && null != componentChildConfiguration.getName()
                                && componentChildConfiguration.getName().equals(componentNamespace) &&
                                componentChildConfiguration.getDatabases() != null
                                ) {
                            for (Database database : componentChildConfiguration.getDatabases()) {
                                if (database.getDatabase().getQueries() != null) {
                                    DatabaseMetaData databaseMetaDataTemp = new DatabaseMetaData();
                                    databaseMetaDataTemp.setName(database.getDatabase().getName());
                                    databaseMetaDataTemp.setVersion(database.getDatabase().getVersion());
                                    databaseQueryConfig.setQueries(databaseMetaDataTemp,
                                            database.getDatabase().getQueries());
                                }
                            }

                        }
                    }
                }
            } catch (ConfigurationException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return databaseQueryConfig;
    }
}