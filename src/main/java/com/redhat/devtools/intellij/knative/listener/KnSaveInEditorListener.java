/*******************************************************************************
 * Copyright (c) 2021 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc.
 ******************************************************************************/
package com.redhat.devtools.intellij.knative.listener;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.redhat.devtools.intellij.common.editor.SaveInEditorListener;
import com.redhat.devtools.intellij.common.utils.YAMLHelper;
import com.redhat.devtools.intellij.knative.tree.ParentableNode;
import com.redhat.devtools.intellij.knative.utils.KnHelper;
import com.redhat.devtools.intellij.knative.utils.TreeHelper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.redhat.devtools.intellij.knative.Constants.KNATIVE;
import static com.redhat.devtools.intellij.knative.Constants.NOTIFICATION_ID;

public class KnSaveInEditorListener extends SaveInEditorListener {

    private static final Logger logger = LoggerFactory.getLogger(KnSaveInEditorListener.class);

    protected void notify(Document document) {
        try {
            String kind = YAMLHelper.getStringValueFromYAML(document.getText(), new String[] { "kind" });
            String name = YAMLHelper.getStringValueFromYAML(document.getText(), new String[] { "metadata", "name" });
            Notification notification = new Notification(NOTIFICATION_ID, "Save Successful", kind + " " + name + " has been saved!", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
        } catch (IOException e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
    }

    @Override
    protected void refresh(Project project, Object node) {
        if (node != null && node instanceof ParentableNode) {
            TreeHelper.refresh(project, (ParentableNode) node);
        }
    }

    protected boolean save(Document document, Project project) {
        try {
            return KnHelper.saveOnCluster(project, document.getText(), false);
        } catch (IOException e) {
            Notification notification = new Notification(NOTIFICATION_ID, "Error", "An error occurred while saving \n" + e.getLocalizedMessage(), NotificationType.ERROR);
            Notifications.Bus.notify(notification);
            logger.warn("Error: " + e.getLocalizedMessage(), e);
            return false;
        }
    }

    protected boolean isFileToPush(Project project, VirtualFile vf) {
        if (vf == null || vf.getUserData(KNATIVE) == null || !vf.getUserData(KNATIVE).equalsIgnoreCase(NOTIFICATION_ID)) {
            return false;
        }
        return super.isFileToPush(project, vf);
    }
}
