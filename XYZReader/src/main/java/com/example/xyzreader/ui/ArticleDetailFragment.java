package com.example.xyzreader.ui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleDetailFragment extends Fragment {

    /* constants */
    public static final String ARG_DATA = "item_id";

    /* UI */
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.article_byline)
    TextView articleByline;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.article_body)
    TextView articleBody;


    /* fields */
    private ArticleDetails mData;

    public static ArticleDetailFragment newInstance(ArticleDetails data) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_DATA, data);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_DATA))
            mData = getArguments().getParcelable(ARG_DATA);

        setHasOptionsMenu(true);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (appbar != null)
            appbar.setExpanded(isVisibleToUser, isVisibleToUser);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_article_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);

        // bind data to views
        populateViews();

        // animate the app bar
        if (getUserVisibleHint())
            appbar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    appbar.setExpanded(true, true);
                }
            }, 500);
        return rootView;
    }


    private void populateViews() {

        // the toolbar's title
        String title = mData.getTitle();
        toolbar.setTitle(title);

        // lazy load the image
        String url = mData.getImageUrl();
        if (url != null && url.length() > 0)
            Picasso
                    .with(getActivity())
                    .load(url)
                    .into(imageView);

        // the by line
        articleByline.setMovementMethod(new LinkMovementMethod());
        String dateString = DateUtils.getRelativeTimeSpanString(
                mData.getPublishDate(),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL).toString();
        articleByline.setText(getString(R.string.date_by_x, dateString, mData.getAuthorName()));

        // the body
        articleBody.setText(Html.fromHtml(mData.getBody()));

    }


    @OnClick(R.id.fab)
    public void onFavClicked(){
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(mData.getBody())
                .getIntent(), getString(R.string.action_share)));
    }
}
