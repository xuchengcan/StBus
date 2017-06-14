package com.chen.stbus.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chen.stbus.Base.BaseActivity;
import com.chen.stbus.Base.UrlHelper;
import com.chen.stbus.Bean.BusBean;
import com.chen.stbus.Bean.LineBean;
import com.chen.stbus.Bean.StateBean;
import com.chen.stbus.R;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class BusLineActivity extends BaseActivity {

    private LineBean lineBean;
    private BusBean busBean;
    private TextView tv_line_start,tv_line_end,tv_line_time,tv_line_price;
    private ImageButton iv_updown_change;
    private RecyclerView rv_bus_line;
    private Boolean UpDown = true;
    private String siteId = "undefined";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();
        initData();
    }

    private void initView() {
        tv_line_start = (TextView) findViewById(R.id.tv_line_start);
        tv_line_end = (TextView) findViewById(R.id.tv_line_end);
        tv_line_time = (TextView) findViewById(R.id.tv_line_time);
        tv_line_price = (TextView) findViewById(R.id.tv_line_price);

        iv_updown_change = (ImageButton)findViewById(R.id.iv_updown_change);
        rv_bus_line = (RecyclerView) findViewById(R.id.rv_bus_line);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("line");
        lineBean = (LineBean) bundle.getSerializable("line");
        setTitle(lineBean.LineName);

        lineBean.getStateName();
        tv_line_start.setText(lineBean.UpStartState);
        tv_line_end.setText(lineBean.UpEndState);
        tv_line_time.setText("首末班车时间：" + lineBean.UpStartTime + "-" + lineBean.UpEndTime);
        tv_line_price.setText("票价：" + lineBean.Fare);

        iv_updown_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpDown = !UpDown;
                if (UpDown){
                    tv_line_start.setText(lineBean.UpStartState);
                    tv_line_end.setText(lineBean.UpEndState);
                    tv_line_time.setText("Up首末班车时间：" + lineBean.UpStartTime + "-" + lineBean.UpEndTime);
                    loadBusData();
                }else {
                    tv_line_start.setText(lineBean.DownStartState);
                    tv_line_end.setText(lineBean.DownEndState);
                    tv_line_time.setText("Down首末班车时间：" + lineBean.DownStartTime + "-" + lineBean.DownEndTime);
                    loadBusData();
                }
            }
        });


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在查询");

        loadBusData();

    }

    private void loadBusData() {
        mProgressDialog.show();
        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl+UrlHelper.StationLicense)
                .addParams("ticket",UrlHelper.Ticket)
                .addParams("lineId",lineBean.Id)
                .addParams("upDown",UpDown?"1":"2")
                .addParams("siteId",siteId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
                showToast("查询数据失败");
                connectServer();
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                mProgressDialog.dismiss();
                KLog.i(response);
                if (response.contains("Result:'E3'")){
                    connectServer();
                    showToast("弹窗，服务器数据出错是否重试");
                    return;
                }
                Gson gson = new Gson();

                busBean = gson.fromJson(response,BusBean.class);
                KLog.i(busBean.LineName + busBean.IsUseGPS);

                String test = "";
                for (StateBean stateBean:busBean.List){
                    if (stateBean.BusList != null){
                        for (String bus:stateBean.BusList){
                            KLog.i(stateBean.SiteName + "---"+ bus);
                            test = test.concat(stateBean.SiteName + "---"+ bus + "\n");
                        }
                    }
                }
                if (test.equals("")){
                    ((TextView) findViewById(R.id.tv_test)).setText("当前路线没有班车");
                    return;
                }

                ((TextView) findViewById(R.id.tv_test)).setText(test);

            }
        });

    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.item_lineslist, parent, false));
            text = (TextView) itemView.findViewById(R.id.tv_line);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), BusLineActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("line",mLineBeens.get((int)text.getTag()));
//                    intent.putExtra("line",bundle);
//                    startActivity(intent);

                }
            });
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

//        private final int mItemCount;
//        private List<LineBean> mLineBeen;

        ItemAdapter(List<LineBean> mLineBeen) {
//            this.mLineBeen = mLineBeen;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.text.setText(mLineBeens.get(position).LineName);
//            holder.text.setTag(position);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

    }
}
