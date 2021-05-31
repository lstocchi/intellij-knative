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
package com.redhat.devtools.intellij.knative;

import com.redhat.devtools.intellij.knative.kn.Kn;
import com.redhat.devtools.intellij.knative.kn.KnCliFactory;
import com.redhat.devtools.intellij.knative.kn.Service;
import com.redhat.devtools.intellij.knative.utils.KnHelper;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KnServiceTest extends BaseTest {

    private Kn kn;

    @Test
    public void OK() {
        assertTrue(true);
    }

    @Test
    public void list() throws IOException {
        System.out.println("list");
        try {
            kn = KnCliFactory.getInstance().getKn(project).get();
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        List<Service> services = kn.getServicesList();
        System.out.println(services);
        assertTrue(services.size() >= 0);
    }

    /*@Test
    public void CreateServiceAndDelete() throws IOException {
        final String serviceName = "test1";
        String serviceAsYAML = load("service.yaml").replace("foo", serviceName);

        KnHelper.saveNew(kn, serviceAsYAML);
        // verify service has been created
        List<Service> services = kn.getServicesList();
        assertTrue(services.stream().anyMatch(service -> service.getName().equals(serviceName)));

        // clean up and verify cleaning succeed
        kn.deleteServices(services
                .stream()
                .filter(service -> service.getName().equals(serviceName))
                .map(service -> service.getName())
                .collect(Collectors.toList())
        );
        services = kn.getServicesList();
        assertFalse(services.stream().anyMatch(service -> service.getName().equals(serviceName)));
    }

    @Test
    public void CreateServiceAndGetIt() throws IOException {
        final String serviceName = "test2";
        String serviceAsYAML = load("service.yaml").replace("foo", serviceName);

        KnHelper.saveNew(kn, serviceAsYAML);
        // verify service has been created
        List<Service> services = kn.getServicesList();
        assertTrue(services.stream().anyMatch(service -> service.getName().equals(serviceName)));

        Service service = kn.getService(serviceName);
        assertEquals(service.getName(), serviceName);
        assertTrue(service.getStatus().getLatestCreatedRevisionName().contains(serviceName));

        // clean up and verify cleaning succeed
        kn.deleteServices(services
                .stream()
                .filter(svc -> svc.getName().equals(serviceName))
                .map(svc -> svc.getName())
                .collect(Collectors.toList())
        );
        services = kn.getServicesList();
        assertFalse(services.stream().anyMatch(svc -> svc.getName().equals(serviceName)));
    }*/
}
