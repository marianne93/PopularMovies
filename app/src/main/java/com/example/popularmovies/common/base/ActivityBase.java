package com.example.popularmovies.common.base;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Marianne.Wazif on 05-Apr-17.
 */

public abstract class ActivityBase extends AppCompatActivity {
    private Toolbar myToolbar;


    protected void setToolbar(Toolbar toolbar, String title, boolean showUpButton, int arrowColorId, boolean withElevation) {
        myToolbar = toolbar;
        myToolbar.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.padding_small));
        }
        setSupportActionBar(myToolbar);


        if (showUpButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_ab_back_mtrl_am_alpha);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(upArrow);
                upArrow.setColorFilter(ContextCompat.getColor(this, arrowColorId), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null)
            myToolbar.setTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        if (myToolbar != null) {
            myToolbar.setSubtitle(subTitle);
        }

    }

    protected abstract void initializeViews();

    protected abstract void setListeners();

    protected abstract void loadFragment();
}
