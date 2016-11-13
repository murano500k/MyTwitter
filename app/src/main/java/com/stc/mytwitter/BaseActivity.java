package com.stc.mytwitter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setupToolbar();
	}

	/**
	 * Finds and sets the Toolbar as the support ActionBar if it is non-null. Returns the Toolbar.
	 * @return The Toolbar view or null if not found.
	 */
	protected Toolbar setupToolbar() {
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		return toolbar;
	}
}