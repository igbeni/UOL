package br.com.igbeni.uol.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class FeedItemTest {

    private static final int FAKE_FEED_ITEM_ID = 8;

    private FeedItem feedItem;

    @Before
    public void setUp() {
        feedItem = new FeedItem(FAKE_FEED_ITEM_ID);
    }

    @Test
    public void testUserConstructorHappyCase() {
        final int feedItemId = feedItem.getId();

        assertThat(feedItemId).isEqualTo(FAKE_FEED_ITEM_ID);
    }
}