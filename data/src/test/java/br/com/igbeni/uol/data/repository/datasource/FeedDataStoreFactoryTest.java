/*
 * (C) Copyright 2018.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      Iggor Alves
 */

package br.com.igbeni.uol.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.UUID;

import br.com.igbeni.uol.data.ApplicationTestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeedDataStoreFactoryTest extends ApplicationTestCase {

    private static final String FAKE_FEED_ITEM_ID = UUID.randomUUID().toString();

    private FeedDataStoreFactory feedDataStoreFactory;

    @Before
    public void setUp() {
        feedDataStoreFactory = new FeedDataStoreFactory(RuntimeEnvironment.application);
    }

    @Test
    public void testCreateCloudDataStore() {
        FeedDataStore feedDataStore = feedDataStoreFactory.create(null);

        assertThat(feedDataStore, is(notNullValue()));
        assertThat(feedDataStore, is(instanceOf(CloudFeedDataStore.class)));
    }

    @Test
    public void testLocalCloudDataStore() {
        FeedDataStore feedDataStore = feedDataStoreFactory.create(FAKE_FEED_ITEM_ID);

        assertThat(feedDataStore, is(notNullValue()));
        assertThat(feedDataStore, is(instanceOf(LocalFeedDataStore.class)));
    }
}