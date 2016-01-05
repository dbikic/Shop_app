package com.dinotest.dinogimbaltest.activities;

import com.dinotest.dinogimbaltest.R;
import com.dinotest.dinogimbaltest.mvp.presenters.MainPresenter;
import com.dinotest.dinogimbaltest.mvp.presenters.impl.MainPresenterImpl;
import com.dinotest.dinogimbaltest.mvp.views.MainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);
        presenter.initUi();

    }


    @Override
    public void initUI() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ConfigActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
