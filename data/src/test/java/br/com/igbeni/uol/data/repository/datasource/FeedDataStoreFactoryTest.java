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

    public static final String FAKE_FEED_ITEM_ID = "FAKE_FEED_ITEM_ID";

    private FeedDataStoreFactory feedDataStoreFactory;

    @Before
    public void setUp() {
        feedDataStoreFactory = new FeedDataStoreFactory(RuntimeEnvironment.application);
    }

    @Test
    public void testCreateCloudDataStore() {
        FeedDataStore feedDataStore = feedDataStoreFactory.create(FAKE_FEED_ITEM_ID);

        assertThat(feedDataStore, is(notNullValue()));
        assertThat(feedDataStore, is(instanceOf(CloudFeedDataStore.class)));
    }
}