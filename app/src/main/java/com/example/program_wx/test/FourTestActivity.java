package com.example.program_wx.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.program_wx.R;
import com.example.program_wx.widget.ListViewHeader;
import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FourTestActivity extends AppCompatActivity
{

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.springView)
    SpringView springView;

    private int j = 0, k = 10;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_test);
        ButterKnife.bind(this);
        initView();

        springView.setListener(new SpringView.OnFreshListener()
        {
            @Override
            public void onRefresh()
            {
                j = j + -1;
                data.add(0, "A" + j);
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                springView.onFinishFreshAndLoad();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }.start();

            }

            @Override
            public void onLoadmore()
            {
                k = k + 1;
                data.add("A" + k);
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                springView.onFinishFreshAndLoad();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }.start();
            }
        });

    }

    private void initView()
    {
        springView.setHeader(new ListViewHeader(this));
        data = new ArrayList<>();
        for (int i = 0; i < 10; ++i)
        {
            data.add("A" + i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
}
