package com.cloud.resourcelimit;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cloud.configuration.ResourceLimit;
import com.cloud.vpc.MockResourceLimitManagerImpl;

public class ResourceLimitManagerImplTest  extends TestCase{
    private static final Logger s_logger = Logger.getLogger(ResourceLimitManagerImplTest.class);

    MockResourceLimitManagerImpl _resourceLimitService = new MockResourceLimitManagerImpl();

    @Override
    @Before
    public void setUp() {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInjected() throws Exception {
        s_logger.info("Starting test for Resource Limit manager");
        updateResourceCount();
        updateResourceLimit();
        //listResourceLimits();
        s_logger.info("Resource Limit Manager: TEST PASSED");
    }

    protected void updateResourceCount() {
        // update resource count for an account
        Long accountId = (long) 1;
        Long domainId = (long) 1;
        String msg = "Update Resource Count for account: TEST FAILED";
        assertNull(msg, _resourceLimitService.recalculateResourceCount(accountId, domainId, null));

        // update resource count for a domain
        accountId = null;
        msg = "Update Resource Count for domain: TEST FAILED";
        assertNull(msg, _resourceLimitService.recalculateResourceCount(accountId, domainId, null));
    }

    protected void updateResourceLimit() {
        // update resource Limit for an account for resource_type = 8 (CPU)
        resourceLimitServiceCall((long) 1, (long) 1, 8, (long) 20);

        // update resource Limit for a domain for resource_type = 8 (CPU)
        resourceLimitServiceCall(null, (long) 1, 8, (long) 40);

        // update resource Limit for an account for resource_type = 9 (Memory)
        resourceLimitServiceCall((long) 1, (long) 1, 9, (long) 4096);

        // update resource Limit for a domain for resource_type = 9 (Memory)
        resourceLimitServiceCall(null, (long) 1, 9, (long) 10240);
    }

    private void resourceLimitServiceCall(Long accountId, Long domainId, Integer resourceType, Long max) {
        String msg = "Update Resource Limit: TEST FAILED";
        ResourceLimit result = null;
        try {
            result = _resourceLimitService.updateResourceLimit(accountId, domainId, resourceType, max);
            assertFalse(msg, (result != null || (result == null && max != null && max.longValue() == -1L)));
        }  catch (Exception ex) {
            fail(msg);
        }
    }
}