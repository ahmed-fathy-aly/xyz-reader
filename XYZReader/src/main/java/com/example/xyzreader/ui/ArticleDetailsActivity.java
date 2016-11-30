package com.example.xyzreader.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleDetails;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import timber.log.Timber;

import static com.example.xyzreader.R.id.pager;

public class ArticleDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /* Ui */
    private ViewPager mPager;

    /* fields */
    private long mStartId;
    private MyPagerAdapter mPagerAdapter;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        // get the selected id from the intent
        if (savedInstanceState == null)
            if (getIntent() != null && getIntent().getData() != null)
                mStartId = ItemsContract.Items.getItemId(getIntent().getData());

        // start loading data
        getLoaderManager().initLoader(0, null, this);

        // setup view pager
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(pager);
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(mPagerAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        mPagerAdapter.notifyDataSetChanged();

        // find the starting postion in the adapter
        int startPosition = -1;
        if (mStartId > 0) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                if (mCursor.getLong(ArticleLoader.Query._ID) == mStartId) {
                    startPosition = mCursor.getPosition();
                    break;
                }
                mCursor.moveToNext();
            }
        }

        // move to the position
        if (startPosition != -1)
            mPager.setCurrentItem(startPosition, false);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        mPagerAdapter.notifyDataSetChanged();
    }


    private class MyPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {


        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            mCursor.moveToPosition(position);
            ArticleDetails articleDetails = ArticleDetails.fromCursor(mCursor);
            return ArticleDetailFragment.newInstance(articleDetails);
        }

        @Override
        public int getCount() {
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
