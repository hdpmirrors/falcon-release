/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.falcon.hive;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Arguments for workflow execution.
 */
public enum HiveDRArgs {

    // source meta store details
    SOURCE_CLUSTER("sourceCluster", "source cluster"),
    SOURCE_METASTORE_URI("sourceMetastoreUri", "source meta store uri"),
    SOURCE_HS2_URI("sourceHiveServer2Uri", "source HS2 uri"),
    SOURCE_SERVICE_PRINCIPAL("sourceServicePrincipal", "source service principal"),
    SOURCE_DATABASE("sourceDatabase", "comma source databases"),
    SOURCE_TABLE("sourceTable", "comma source tables"),
    SOURCE_STAGING_PATH("sourceStagingPath", "source staging path for data"),

    // source hadoop endpoints
    SOURCE_NN("sourceNN", "source name node"),
    SOURCE_RM("sourceRM", "source resource manager"),

    TARGET_CLUSTER("targetCluster", "target cluster"),
    // target meta store details
    TARGET_METASTORE_URI("targetMetastoreUri", "source meta store uri"),
    TARGET_HS2_URI("targetHiveServer2Uri", "source meta store uri"),
    TARGET_SERVICE_PRINCIPAL("targetServicePrincipal", "source service principal"),
    TARGET_DATABASE("targetDatabase", "comma source databases"),
    TARGET_TABLE("targetTable", "comma source tables"),
    TARGET_STAGING_PATH("targetStagingPath", "source staging path for data"),

    // target hadoop endpoints
    TARGET_NN("targetNN", "target name node"),
    TARGET_RM("targetRM", "target resource manager"),

    // num events
    MAX_EVENTS("maxEvents", "number of events to process in this run"),

    // tuning params
    MAX_MAPS("maxMaps", "number of maps", false),

    // Map Bandwidth
    MAP_BANDWIDTH("mapBandwidth", "map bandwidth in mb", false),

    JOB_NAME("drJobName", "unique job name"),

    FALCON_LIBPATH("falconLibPath","Falcon Lib Path for Jar files", false);

    private final String name;
    private final String description;
    private final boolean isRequired;

    HiveDRArgs(String name, String description) {
        this(name, description, true);
    }

    HiveDRArgs(String name, String description, boolean isRequired) {
        this.name = name;
        this.description = description;
        this.isRequired = isRequired;
    }

    public Option getOption() {
        return new Option(this.name, true, this.description);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getOptionValue(CommandLine cmd) {
        return cmd.getOptionValue(this.name);
    }

    @Override
    public String toString() {
        return getName();
    }
}
