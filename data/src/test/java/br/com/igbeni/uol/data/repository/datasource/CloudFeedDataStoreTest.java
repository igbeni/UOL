package br.com.igbeni.uol.data.repository.datasource;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.igbeni.uol.data.net.RestApi;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudFeedDataStoreTest {

    private CloudFeedDataStore cloudFeedDataStore;

    @Mock
    private RestApi mockRestApi;

    @Before
    public void setUp() {
        cloudFeedDataStore = new CloudFeedDataStore(mockRestApi);
    }

    @Test
    public void testGetUserEntityListFromApi() {
        cloudFeedDataStore.feedEntity();
        verify(mockRestApi).feedEntity();
    }
}