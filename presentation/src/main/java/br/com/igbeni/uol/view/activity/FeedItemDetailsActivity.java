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

package br.com.igbeni.uol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.HasComponent;
import br.com.igbeni.uol.internal.di.components.DaggerFeedComponent;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.view.fragment.FeedItemDetailFragment;

/**
 * Activity that shows a list of FeedItem.
 */
public class FeedItemDetailsActivity extends BaseActivity implements HasComponent<FeedComponent> {

    private static final String INTENT_EXTRA_PARAM_FEED_ITEM_ID = "INTENT_EXTRA_PARAM_FEED_ITEM_ID";
    private static final String INSTANCE_STATE_PARAM_FEED_ITEM_ID = "INSTANCE_STATE_PARAM_FEED_ITEM_ID";

    @Nullable
    private String itemId;
    private FeedComponent feedComponent;

    @NonNull
    public static Intent getCallingIntent(Context context, String feedItemId) {
        Intent callingIntent = new Intent(context, FeedItemDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_FEED_ITEM_ID, feedItemId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_FEED_ITEM_ID, this.itemId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.itemId = getIntent().getStringExtra(INTENT_EXTRA_PARAM_FEED_ITEM_ID);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.feeditem_detail_container, FeedItemDetailFragment.forItem(itemId))
                    .commit();
        } else {
            this.itemId = savedInstanceState.getString(INSTANCE_STATE_PARAM_FEED_ITEM_ID);
        }
    }

    private void initializeInjector() {
        this.feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public FeedComponent getComponent() {
        return feedComponent;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
