package br.com.igbeni.uol.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import br.com.igbeni.uol.data.ApplicationTestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeedDataStoreFactoryTest extends ApplicationTestCase {

    private FeedDataStoreFactory feedDataStoreFactory;

    @Before
    public void setUp() {
        feedDataStoreFactory = new FeedDataStoreFactory(RuntimeEnvironment.application);
    }

    @Test
    public void testCreateCloudDataStore() {
        FeedDataStore feedDataStore = feedDataStoreFactory.create();

        assertThat(feedDataStore, is(notNullValue()));
        assertThat(feedDataStore, is(instanceOf(CloudFeedDataStore.class)));
    }
}