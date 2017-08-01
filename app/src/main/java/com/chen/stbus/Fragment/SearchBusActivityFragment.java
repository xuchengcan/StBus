package com.chen.stbus.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chen.stbus.Activity.BusLineActivity;
import com.chen.stbus.Base.BaseFragment;
import com.chen.stbus.Base.UrlHelper;
import com.chen.stbus.Bean.LineBean;
import com.chen.stbus.R;
import com.chen.stbus.Utils.KeyboardUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchBusActivityFragment extends BaseFragment {

    private EditText et_bus;
    private Button bt_search, bt_init;
    private RecyclerView rv;
    private ItemAdapter mItemAdapter;
    private List<LineBean> mLineBeens;
    private ProgressDialog mProgressDialog;

    public SearchBusActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_search_bus, container, false);
        rv = (RecyclerView) rootview.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        et_bus = (EditText) rootview.findViewById(R.id.et_bus);
        bt_search = (Button) rootview.findViewById(R.id.bt_search);
        bt_init = (Button) rootview.findViewById(R.id.bt_init);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLineBeens = new ArrayList<>();
        mItemAdapter = new ItemAdapter(mLineBeens);
        rv.setAdapter(mItemAdapter);
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KeyboardUtil.closeKeyboard(et_bus);
            }
        });

        mProgressDialog = new ProgressDialog(getContext());
        et_bus.clearFocus();
        et_bus.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_bus.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        et_bus.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    searchLine();
                    return true;
                }
                return false;
            }
        });


//        mProgressDialog.setTitle("加载数据");
        loadData();
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLine();
            }
        });

    }

    private void loadData() {
        mProgressDialog.show();
        OkHttpUtils.post()
                .headers(getUrlhead())
                .addParams("userID", "guest")
                .addParams("phoneType", "1")
                .addParams("appId", "Z3MXkuYTzB7Cyu3petSbC1")//Z3MXkuYTzB7Cyu3petSbC1
                .addParams("appKey", "fRPiisiGhD7sbL3PulBri9")//fRPiisiGhD7sbL3PulBri9
                .addParams("clientId", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("token", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("alias", "undefined")//undefined
                .addParams("busAppId", "com.gmcc.stgj")//com.gmcc.stgj
                .addParams("busVersion", "1.9.2")//1.9.2
                .addParams("busAppName", "汕头公交")//汕头公交
                .addParams("phoneImei", "861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneImsi", "")//*
                .addParams("phoneModel", "vivo Y51A")//vivo Y51A
                .addParams("phoneVendor", "vivo")//vivo
                .addParams("phoneUuid", "861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneSysName", "Android")//Android
                .addParams("phoneVersion", "5.0.2")//5.0.2
                .addParams("phoneLanguage", "zh_CN")//zh_CN
                .addParams("ticket", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .url(UrlHelper.BaseUrl + UrlHelper.CreateBizGetui + "?ticket=" + UrlHelper.Ticket)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
                mProgressDialog.dismiss();
                showToast("连接服务器失败，请检查网络或重新打开应用");
            }

            @Override
            public void onResponse(String response, int id) {

                KLog.i(response);
                if (response.contains("0")) {
//                    showToast(response);
                    getAllLine();
                } else {
                    mProgressDialog.dismiss();
                    showToast(response);
                }

            }
        });
    }

    private void getAllLine() {
        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl + UrlHelper.Lines + "?ticket=" + UrlHelper.Ticket)
//                        .addParams("ticket",UrlHelper.Ticket)
                .addParams("lineName", et_bus.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
                mProgressDialog.dismiss();
                showToast("数据初始化失败，请重新查询");
                connectServer();
            }

            @Override
            public void onResponse(String response, int id) {
                KLog.i(response);
                List<LineBean> lineListBean = new ArrayList<LineBean>();
                Gson gson = new Gson();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();

                //加强for循环遍历JsonArray
                for (JsonElement bean : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    LineBean lineBean = gson.fromJson(bean, LineBean.class);
                    lineListBean.add(lineBean);
                }
                mLineBeens = lineListBean;
                mItemAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }
        });
    }

    private void searchLine(){
        mProgressDialog.show();
        KeyboardUtil.closeKeyboard(et_bus);
        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl + UrlHelper.Lines + "?ticket=" + UrlHelper.Ticket)
                .addParams("lineName", et_bus.getText().toString())
                .addParams("ticket", UrlHelper.Ticket)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
                showToast("查询失败，请检查网络");
                mProgressDialog.dismiss();
                connectServer();
            }

            @Override
            public void onResponse(String response, int id) {
                KLog.i(response);
                List<LineBean> lineListBean = new ArrayList<LineBean>();
                Gson gson = new Gson();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();

                //加强for循环遍历JsonArray
                for (JsonElement bean : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    LineBean lineBean = gson.fromJson(bean, LineBean.class);
                    lineListBean.add(lineBean);
                }


                mProgressDialog.dismiss();
                mLineBeens = lineListBean;
                mItemAdapter.notifyDataSetChanged();

            }
        });
    }


    public interface Listener {
        void onItemClicked(int position);
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
                    Intent intent = new Intent(getContext(), BusLineActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("line", mLineBeens.get((int) text.getTag()));
                    intent.putExtra("line", bundle);
                    startActivity(intent);

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
            holder.text.setText(mLineBeens.get(position).LineName);
            holder.text.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mLineBeens.size();
        }

    }
}
