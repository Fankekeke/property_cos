import request from '@/router/axios'
import baiduMap from "./baiduMap";
const gisMap = {
}

let map = {}

/*初始化地图 地图控件*/
gisMap.initMap = (id) => {
  map = new BMap.Map(id);
  map.centerAndZoom(new BMap.Point(116.404, 39.915), 12);  // 初始化地图,设置中心点坐标和地图级别
  //添加地图类型控件
  map.addControl(new BMap.MapTypeControl({
    mapTypes: [
      BMAP_NORMAL_MAP,
      BMAP_HYBRID_MAP
    ]
  }));
  map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
  map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
  var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
  map.addControl(top_left_control);

  var mapType1 = new BMap.MapTypeControl(
    {
      mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP],
      anchor: BMAP_ANCHOR_TOP_LEFT
    }
  );
  var overView = new BMap.OverviewMapControl();
  var overViewOpen = new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
  map.addControl(mapType1);          //2D图，混合图
  map.addControl(overView);          //添加默认缩略地图控件
  map.addControl(overViewOpen);      //右下角，打开
}

/*查找点位*/
gisMap.findPoint = (point,zoom) => {
  if(zoom==undefined) {
    zoom = 18;
  }
  map.centerAndZoom(new BMap.Point(point.lng, point.lat), zoom);
}

/*创建普通点位*/
gisMap.pointAdd = (point) => {
  var marker = new BMap.Marker(point); // 创建点
  map.addOverlay(marker);            //增加点
  return marker;
}

/*创建图标点位*/
gisMap.pointIconAdd = (markerIcon) => {
  // markerIcon.disableMassClear();         //禁止删除标注
  map.addOverlay(markerIcon);            //增加点
}

/*清除所有覆盖物*/
gisMap.clearOverlays = () => {
  map.clearOverlays();
}

/*添加覆盖物的文字标注*/
gisMap.markerLableAdd = (myMarker, text) => {
  var label = new BMap.Label(text,{offset:new BMap.Size(-15,30)});
  myMarker.setLabel(label);
}

/*创建折线*/
gisMap.polylineAdd = (pointArray,color,roadInfo) => {
  let polylineArray =[];
  pointArray.forEach((line) => {
    polylineArray =[];
    line.forEach((point,index) => {
      if(index === 0) {
        let marker = new BMap.Icon("http://203.86.55.132:8080/platform/map/img/begin.gif", new BMap.Size(40, 40));
        let startMarker = new BMap.Marker(new BMap.Point(point.lng,point.lat), {icon: marker});
        let label = new BMap.Label(roadInfo.roadName,{offset:new BMap.Size(-15,30)});
        gisMap.polylineWindon(startMarker,roadInfo,point);
        startMarker.setLabel(label);
        map.addOverlay(startMarker);
      }
      if(index === line.length - 1) {
        let marker = new BMap.Icon("http://203.86.55.132:8080/platform/map/img/end.gif", new BMap.Size(40, 40));
        map.addOverlay(new BMap.Marker(new BMap.Point(point.lng,point.lat), {icon: marker}));
      }
      polylineArray.push(new BMap.Point(point.lng,point.lat))
    })
    var polyline = new BMap.Polyline(polylineArray, {strokeColor: color, strokeWeight: 8, strokeOpacity: 0.8});   //创建折线
    map.addOverlay(polyline);          //增加折线
  })
}

/*创建区域*/
gisMap.polygonAdd = (pointArray,areaInfo) => {
  pointArray.forEach(gon => {
    var polygon = new BMap.Polygon(gon, {strokeColor:areaInfo.borderColor,fillColor:areaInfo.backgroundColor,fillColorOpacity:"0.6", strokeWeight:5, strokeOpacity:0.6});  //创建多边形
    map.addOverlay(polygon);           //增加多边形
    gisMap.polygonWindon(polygon,areaInfo,gon[0]);
  })
}

/*区域窗口*/
gisMap.polygonWindon = (polygon,polygonInfo,point) => {
  var opts = {
    width : 350,
    height: 180,
  }
  var infoWindow = new BMap.InfoWindow('<table border="1" style="width: 100%">\n' +
    '    <thead>\n' +
    '        <tr>\n' +
    '            <th colspan="2" style="text-align: center">'+polygonInfo.areaName+'</th>\n' +
    '        </tr>\n' +
    '    </thead>\n' +
    '    <tbody>\n' +
    '        <tr>\n' +
    '            <td>工作开始时间</td>\n' +
    '            <td>'+(polygonInfo.workBeginTime == null ? '暂无信息' : polygonInfo.workBeginTime)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>工作结束时间</td>\n' +
    '            <td>'+(polygonInfo.workEndTime == null ? '暂无信息' : polygonInfo.workEndTime)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>区域形状</td>\n' +
    '            <td>'+(polygonInfo.$areaType == null ? '暂无信息' : polygonInfo.$areaType)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>报警状态</td>\n' +
    '            <td>'+(polygonInfo.$alarmRule == null ? '暂无信息' : polygonInfo.$alarmRule)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>所属部门</td>\n' +
    '            <td>'+polygonInfo.$deptId+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>备注</td>\n' +
    '            <td>'+(polygonInfo.workAreaMemo == null ? '暂无信息' : polygonInfo.workAreaMemo)+'</td>\n' +
    '        </tr>\n' +
    '    </tbody>\n' +
    '</table>', opts);  // 创建信息窗口对象
  polygon.addEventListener("click", function(){
    map.openInfoWindow(infoWindow,new BMap.Point(point.lng,point.lat)); //开启信息窗口
  });
}

/*线路窗口*/
gisMap.polylineWindon = (polyline,polylineInfo,point) => {
  var opts = {
    width : 420,
    height: 250,
  }
  var infoWindow = new BMap.InfoWindow('<table border="1" style="width: 100%">\n' +
    '    <thead>\n' +
    '        <tr>\n' +
    '            <th colspan="4" style="text-align: center">'+polylineInfo.roadName+'</th>\n' +
    '        </tr>\n' +
    '    </thead>\n' +
    '    <tbody>\n' +
    '        <tr>\n' +
    '            <td>开始时间</td>\n' +
    '            <td colspan="3">'+(polylineInfo.workBeginTime == null ? '暂无信息' : polylineInfo.workBeginTime)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>结束时间</td>\n' +
    '            <td colspan="3">'+(polylineInfo.workEndTime == null ? '暂无信息' : polylineInfo.workEndTime)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>道路类型</td>\n' +
    '            <td>'+(polylineInfo.$rodeType == null ? '暂无信息' : polylineInfo.$rodeType)+'</td>\n' +
    '            <td>道路等级</td>\n' +
    '            <td>'+(polylineInfo.rodeLevel == null ? '暂无信息' : polylineInfo.rodeLevel)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>所属部门</td>\n' +
    '            <td>'+polylineInfo.$deptId+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>线路长度</td>\n' +
    '            <td>'+(polylineInfo.lineLength == null ? '暂无信息' : polylineInfo.lineLength)+'</td>\n' +
    '            <td>作业面积</td>\n' +
    '            <td>'+(polylineInfo.lineWorkArea == null ? '暂无信息' : polylineInfo.lineWorkArea)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>限速</td>\n' +
    '            <td>'+(polylineInfo.speedLimit == null ? '暂无信息' : polylineInfo.speedLimit)+'</td>\n' +
    '            <td>停车限时</td>\n' +
    '            <td>'+(polylineInfo.stopLimit == null ? '暂无信息' : polylineInfo.stopLimit)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>线路属性</td>\n' +
    '            <td>'+(polylineInfo.lineAttr == null ? '暂无信息' : polylineInfo.lineAttr)+'</td>\n' +
    '            <td>用于作业计算</td>\n' +
    '            <td>'+(polylineInfo.$useCalc == null ? '未选择' : polylineInfo.$useCalc)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>作业类型</td>\n' +
    '            <td colspan="3">'+(polylineInfo.workType == null ? '暂无信息' : polylineInfo.workType)+'</td>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <td>备注</td>\n' +
    '            <td colspan="3">'+(polylineInfo.lineMemo == null ? '暂无信息' : polylineInfo.lineMemo)+'</td>\n' +
    '        </tr>\n' +
    '    </tbody>\n' +
    '</table>', opts);  // 创建信息窗口对象
  polyline.addEventListener("click", function(){
    map.openInfoWindow(infoWindow,new BMap.Point(point.lng,point.lat)); //开启信息窗口
  });
}

gisMap.trashWindon = (trashMarker,trashInfo,point) => {
  var opts = {
    width : 500,
    height: 180,
  }
  let info = '<div style="margin:0;line-height:20px;padding:2px;width: 700px;height: 270px">' +
    '<div style="float: left;width: 100%"><img src="http://img5.imgtn.bdimg.com/it/u=503688232,1988133337&fm=26&gp=0.jpg" alt="" style="float:left;zoom:1;overflow:hidden;width:100px;height:100px;margin-right:30px;"/>' +
    '<ul style="float: left;width: 150px"><li>所属部门：'+trashInfo.$deptId+'m</li><li>位置：'+trashInfo.trashAddress+'/km</li><li>经度：'+point.lng+'</li><li>纬度：'+point.lat+'</li></ul>'+
    '<ul style="float: left;width: 200px"><li>备注：'+trashInfo.trashMemo+'</li></ul>'+
    '</div>'+
    '<h4 style="margin-top: 5px">连线互动</h4><ul style="margin-top: 10px;margin-left: 30px;">' +
    '<li style="display: inline-block;width: 100px"><a href="testOpen()"><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/messaging.png">下发短信</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/globe_16.png">开启对讲</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">视频抓拍</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">今日轨迹</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">昨日轨迹</a></li>' +
    '</ul>'
  var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
  trashMarker.addEventListener("click", function(){
    map.openInfoWindow(infoWindow,new BMap.Point(point.lng,point.lat)); //开启信息窗口
  });
}

/*点聚合*/
gisMap.MarkerClusterer = (markers) => {
  var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
}

gisMap.rMap = () => {
  return map;
}

export default gisMap
