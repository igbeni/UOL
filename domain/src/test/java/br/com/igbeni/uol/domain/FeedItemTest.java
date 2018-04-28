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