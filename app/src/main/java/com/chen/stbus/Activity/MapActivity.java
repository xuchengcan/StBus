package com.chen.stbus.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chen.stbus.Base.BaseActivity;
import com.chen.stbus.Bean.BusBean;
import com.chen.stbus.Bean.LineBean;
import com.chen.stbus.BusLineOverlay;
import com.chen.stbus.R;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity {

    private LineBean lineBean;
    private BusBean busBean;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mPolyline;


    // 搜索相关
    private BusLineResult route = null; // 保存驾车/步行路线数据的变量，供浏览节点时使用
    private PoiSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private BusLineSearch mBusLineSearch = null;
    private List<String> busLineIDList = null;
    private int busLineIndex = 0;//上行，下行
    private BusLineOverlay overlay; // 公交路线绘制对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        lineBean = (LineBean) getIntent().getSerializableExtra("line");
        busBean = (BusBean) getIntent().getSerializableExtra("bus");
        busBean.getCenterPosition();

        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(busBean.Center_Latitude, busBean.Center_Longitude));
        builder.zoom(19.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        drawPolyLine();

        busLineIDList = new ArrayList<String>();
        overlay = new BusLineOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(overlay);
        drawBusLine();
    }

    private void drawBusLine() {

        mSearch = PoiSearch.newInstance();
        mBusLineSearch = BusLineSearch.newInstance();
        mSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    showToast("抱歉，未找到结果");
                    return;
                }
                // 遍历所有poi，找到类型为公交线路的poi
                busLineIDList.clear();
                for (PoiInfo poi : result.getAllPoi()) {
                    if (poi.type == PoiInfo.POITYPE.BUS_LINE
                            || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                        busLineIDList.add(poi.uid);
                    }
                }
                searchNextBusline(null);
                route = null;
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    showToast("抱歉，未找到结果");
                    return;
                }
                mBaiduMap.clear();
                route = result;
//                nodeIndex = -1;
                overlay.removeFromMap();
                overlay.setData(result);
                overlay.addToMap();
                overlay.zoomToSpan();

                showToast(result.getBusLineName());

            }
        });
        mSearch.searchInCity((new PoiCitySearchOption()).city("汕头")
                .keyword(busBean.LineName));
    }

    public void searchNextBusline(View v) {
        if (busLineIndex >= busLineIDList.size()) {
            busLineIndex = 0;
        }
        if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
                && busLineIDList.size() > 0) {
            mBusLineSearch.searchBusLine((new BusLineSearchOption()
                    .city("汕头").uid(busLineIDList.get(busLineIndex))));

            busLineIndex++;
        }

    }


    private void drawPolyLine() {

        List<LatLng> polylines = new ArrayList<>();

        for (int i = 0; i < busBean.List.size(); i++) {
            polylines.add(new LatLng(Double.parseDouble(busBean.List.get(i).Latitude), Double.parseDouble(busBean.List.get(i).Longitude)));
        }

        PolylineOptions polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.RED);
        mPolyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);

//        OverlayOptions markerOptions;
//        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)).position(polylines.get(0))
//                .rotate((float) getAngle(0));
//        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);


        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_mark);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);


    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mSearch.destroy();
        mBusLineSearch.destroy();
        mBaiduMap.clear();
    }
}
