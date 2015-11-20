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

package org.apache.falcon.atlas.Util;

import org.apache.commons.lang3.StringUtils;
import org.apache.falcon.FalconException;
import org.apache.falcon.entity.ProcessHelper;
import org.apache.falcon.entity.v0.process.Workflow;
import org.apache.falcon.security.CurrentUser;
import org.apache.falcon.workflow.WorkflowExecutionArgs;
import org.apache.falcon.workflow.WorkflowExecutionContext;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Falcon event util
 */
public final class EventUtil {

    private EventUtil() {}

    // process workflow properties
    private static final WorkflowExecutionArgs[] PROCESS_INSTANCE_WORKFLOW_PROPERTIES = {
            WorkflowExecutionArgs.USER_WORKFLOW_NAME,
            WorkflowExecutionArgs.USER_WORKFLOW_ENGINE,
            WorkflowExecutionArgs.WORKFLOW_ID,
            WorkflowExecutionArgs.RUN_ID,
            WorkflowExecutionArgs.STATUS,
            WorkflowExecutionArgs.WF_ENGINE_URL,
            WorkflowExecutionArgs.USER_SUBFLOW_ID,
            WorkflowExecutionArgs.USER_WORKFLOW_VERSION
    };

    public static Map<String, String> convertKeyValueStringToMap(final String keyValueString) {
        if (StringUtils.isBlank(keyValueString)) {
            return null;
        }

        Map<String, String> keyValueMap = new HashMap<>();

        String[] tags = keyValueString.split(",");
        for (String tag : tags) {
            int index = tag.indexOf("=");
            String tagKey = tag.substring(0, index);
            String tagValue = tag.substring(index + 1, tag.length());
            keyValueMap.put(tagKey, tagValue);
        }
        return keyValueMap;
    }

    public static List<String> convertStringToList(final String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return Arrays.asList(text.split(","));
    }

    public static UserGroupInformation getUgi() throws FalconException {
        UserGroupInformation ugi;
        try {
            ugi = CurrentUser.getAuthenticatedUGI();
        } catch (IOException ioe) {
            throw new FalconException(ioe);
        }
        return ugi;
    }

    public static Map<String, String> getWFProperties(WorkflowExecutionContext context) {
        Map<String, String> wfProperties = new HashMap<>();
        for (WorkflowExecutionArgs instanceWorkflowProperty : PROCESS_INSTANCE_WORKFLOW_PROPERTIES) {
            String value = context.getValue(instanceWorkflowProperty);
            if (StringUtils.isBlank(value)) {
                continue;
            }

            wfProperties.put(instanceWorkflowProperty.getName(), value);
        }
        return wfProperties;
    }

    public static Map<String, String> getProcessEntityWFProperties(final Workflow workflow,
                                                                   final String processName) {
        Map<String, String> wfProperties = new HashMap<>();
        wfProperties.put(WorkflowExecutionArgs.USER_WORKFLOW_NAME.getName(),
                ProcessHelper.getProcessWorkflowName(workflow.getName(), processName));
        wfProperties.put(WorkflowExecutionArgs.USER_WORKFLOW_VERSION.getName(),
                workflow.getVersion());
        wfProperties.put(WorkflowExecutionArgs.USER_WORKFLOW_ENGINE.getName(),
                workflow.getEngine().value());

        return wfProperties;
    }

    public static List<String> getClusters (org.apache.falcon.entity.v0.process.Process process) {
        List<String> clusterList = new ArrayList<>();
        for (org.apache.falcon.entity.v0.process.Cluster cluster : process.getClusters().getClusters()) {
            clusterList.add(cluster.getName());
        }
        return clusterList;
    }
}
