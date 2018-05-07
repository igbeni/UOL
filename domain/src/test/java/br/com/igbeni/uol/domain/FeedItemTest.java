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

package br.com.igbeni.uol.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FeedItemTest {

    private static final String FAKE_FEED_ITEM_ID = UUID.randomUUID().toString();

    private FeedItem feedItem;

    @Before
    public void setUp() {
        feedItem = new FeedItem(FAKE_FEED_ITEM_ID);
    }

    @Test
    public void testFeedItemConstructorHappyCase() {
        final String feedItemId = feedItem.getId();

        assertThat(feedItemId).isEqualTo(FAKE_FEED_ITEM_ID);
    }
}