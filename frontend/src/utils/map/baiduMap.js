
const baiduMap = {
}

let map = {}

/* 初始化地图 地图控件 */
baiduMap.initMap = (id) => {
  map = new BMap.Map(id)
  map.centerAndZoom(new BMap.Point(116.404, 39.915), 12) // 初始化地图,设置中心点坐标和地图级别
  // 添加地图类型控件
  map.addControl(new BMap.MapTypeControl({
    mapTypes: [
      BMAP_NORMAL_MAP,
      BMAP_HYBRID_MAP
    ]
  }))
  map.setCurrentCity('北京') // 设置地图显示的城市 此项是必须设置的
  map.enableScrollWheelZoom(true) // 开启鼠标滚轮缩放

  var overView = new BMap.OverviewMapControl()
  var overViewOpen = new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT})
  map.addControl(overView) // 添加默认缩略地图控件
  map.addControl(overViewOpen) // 右下角，打开
}

baiduMap.searchNear = (lnt, lat, key) => {
  map.clearOverlays()
  var mPoint = new BMap.Point(lnt, lat);
  map.enableScrollWheelZoom();
  map.centerAndZoom(mPoint, 15);

  var circle = new BMap.Circle(mPoint,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
  map.addOverlay(circle);
  var local =  new BMap.LocalSearch(map, {renderOptions: {map: map, autoViewport: false}});
  console.log(key)
  local.searchNearby(key, mPoint,1000);
}

/* 查找点位 */
baiduMap.findPoint = (point, zoom) => {
  if (zoom == undefined) {
    zoom = 18
  }
  map.centerAndZoom(point, zoom)
}

/* 查找路线 */
baiduMap.findLine = (point, zoom) => {
  map.centerAndZoom(new BMap.Point(point.lng, point.lat), zoom)
}

/* 创建普通点位 */
baiduMap.pointAdd = (point) => {
  var marker = new BMap.Marker(point) // 创建点
  map.addOverlay(marker) // 增加点
}

baiduMap.localPointAdd = (content) => {
  var marker = new BMap.Marker(content.point) // 创建点
  map.addOverlay(marker) // 增加点
  baiduMap.localInfoWindow(marker, content)
}

/* 创建图标点位 */
baiduMap.pointIconAdd = (markerIcon) => {
  // markerIcon.disableMassClear();         //禁止删除标注
  map.addOverlay(markerIcon) // 增加点
}

/* 创建路线 */
baiduMap.polylineAddByTrack = (pointArray, color) => {
  let polylineArray = []
  pointArray.forEach((point, index) => {
    polylineArray.push(new BMap.Point(point.lng, point.lat))
  })
  var polyline = new BMap.Polyline(polylineArray, {strokeColor: color, strokeWeight: 5, strokeOpacity: 0.7}) // 创建折线
  // polyline.disableMassClear();       //禁止删除线路
  map.addOverlay(polyline) // 增加折线
}

/* 创建折线 */
baiduMap.polylineAdd = (pointArray, color) => {
  let polylineArray = []
  pointArray.forEach((point, index) => {
    if (index === 0) {
      let marker = new BMap.Icon('http://203.86.55.132:8080/platform/map/img/begin.gif', new BMap.Size(40, 40))
      map.addOverlay(new BMap.Marker(new BMap.Point(point.lng, point.lat), {icon: marker}))
    }
    if (index === pointArray.length - 1) {
      let marker = new BMap.Icon('http://203.86.55.132:8080/platform/map/img/end.gif', new BMap.Size(40, 40))
      map.addOverlay(new BMap.Marker(new BMap.Point(point.lng, point.lat), {icon: marker}))
    }
    polylineArray.push(new BMap.Point(point.lng, point.lat))
  })
  var polyline = new BMap.Polyline(polylineArray, {strokeColor: color, strokeWeight: 3, strokeOpacity: 0.6}) // 创建折线
  map.addOverlay(polyline) // 增加折线
}

/* 创建⚪圆 */
baiduMap.circleAdd = (point) => {
  var circle = new BMap.Circle(point, 500, {strokeColor: 'blue', strokeWeight: 2, strokeOpacity: 0.5}) // 创建圆
  map.addOverlay(circle) // 增加圆
}

/* 创建多边形 */
baiduMap.polygonAdd = (pointArray) => {
  var polygon = new BMap.Polygon(pointArray, {strokeColor: 'blue', strokeWeight: 2, strokeOpacity: 0.5}) // 创建多边形
  map.addOverlay(polygon) // 增加多边形
}

/* 设置点位可拖拽 */
baiduMap.enableDragging = (myMarker) => {
  myMarker.enableDragging()
}

/* 设置点位不可拖拽 */
baiduMap.disableDragging = (myMarker) => {
  myMarker.disableDragging()
}

/* 设置线位可编辑 */
baiduMap.enableEditingByLine = (polyline) => {
  polyline.enableEditing()
}

/* 设置区域可编辑 */
baiduMap.enableEditingByGon = (polygon) => {
  polygon.enableEditing()
}

/* 添加文字标签 */
baiduMap.textLableAdd = (point, text) => {
  var opts = {
    position: point, // 指定文本标注所在的地理位置
    offset: new BMap.Size(0, 30) // 设置文本偏移量
  }
  var label = new BMap.Label(text, opts) // 创建文本标注对象
  label.setStyle({
    color: 'black',
    fontSize: '12px',
    height: '20px',
    lineHeight: '20px',
    fontFamily: '微软雅黑'
  })
  map.addOverlay(label)
}

/* 添加覆盖物的文字标注 */
baiduMap.markerLableAdd = (myMarker, text) => {
  var label = new BMap.Label(text, {offset: new BMap.Size(-10, 30)})
  myMarker.setLabel(label)
}

/* 添加行政区域 */
baiduMap.getBoundary = (address) => {
  var bdary = new BMap.Boundary()
  bdary.get(address, function (rs) { // 获取行政区域
    map.clearOverlays() // 清除地图覆盖物
    var count = rs.boundaries.length // 行政区域的点有多少个
    if (count === 0) {
      alert('未能获取当前输入行政区域')
      return
    }
    var pointArray = []
    for (var i = 0; i < count; i++) {
      var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: '#ff0000'}) // 建立多边形覆盖物
      map.addOverlay(ply) // 添加覆盖物
      pointArray = pointArray.concat(ply.getPath())
    }
    map.setViewport(pointArray) // 调整视野
  })
}

/* 车辆添加信息窗口 */
baiduMap.infoWindow = (marker, content) => {
  let fuleName = '' // 燃油类型
  let operationName = (content.operation_state == 0) ? '未运营' : '运营中' // 运营状态
  let signal = '' // 信号强度
  switch (true) {
    case ((content.signal >= 0 && content.signal <= 1) || content.signal == null):
      signal = '很弱'
      break
    case content.signal >= 2 && content.signal <= 3:
      signal = '较弱'
      break
    case content.signal >= 4 && content.signal <= 5:
      signal = '一般'
      break
    case content.signal >= 6 && content.signal <= 7:
      signal = '较好'
      break
    case content.signal >= 8 && content.signal <= 9:
      signal = '非常好'
      break
  }

  for (let fule in fuleData) {
    if (fuleData[fule].value == content.fule_type) {
      fuleName = fuleData[fule].label
    }
  }

  let info = '<div style="margin:0;line-height:20px;padding:2px;width: 700px;height: 270px">' +
    '<div style="float: left;width: 100%"><img src="http://file2.5ihw.cn:9033/comm/image/biz/5af3f480c1cb6173dc6b0195/0.1f" alt="" style="float:left;zoom:1;overflow:hidden;width:100px;height:100px;margin-right:30px;"/>' +
    '<ul style="float: left;width: 150px"><li>高度：' + content.altitude + 'm</li><li>速度：' + content.speed + '/km</li><li>经度：' + content.longitude + '</li><li>纬度：' + content.latitude + '</li></ul>' +
    '<ul style="float: left;width: 200px"><li>所属部门：' + content.dept_name + '</li><li>燃油类型：' + fuleName + '</li><li>运营状态：' + operationName + '</li><li>工作状态：' + content.work_state + '</li></ul>' +
    '<ul style="float: left;width: 180px"><li>车辆内部编号：' + content.intenal_no + '</li><li>信号强度：' + signal + '</li><li>报警状态：' + content.alarm_state + '</li></ul>' +
    '</div>' +
    '<h4>连线互动</h4><ul style="margin-top: 10px;margin-left: 30px;">' +
    '<li style="display: inline-block;width: 100px"><a href="testOpen()"><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/messaging.png">下发短信</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/globe_16.png">开启对讲</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">视频抓拍</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">今日轨迹</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">昨日轨迹</a></li>' +
    '</ul>' +
    '<div style="float: left;margin-top: 10px">' +
    '<h4>报警信息</h4><ul style="margin-top: 10px;margin-left: 30px;float: left"><li>报警信息1：________________________</li><li>报警信息2：________________________</li><li>报警信息3：________________________</li></ul>' +
    '</div>'

  marker.addEventListener('click', function () {
    this.openInfoWindow(new BMap.InfoWindow(info))
  })
}

/* 清洁站添加信息窗口 */
baiduMap.stationInfoWindow = (marker, content) => {
  let info = '<div style="margin:0;line-height:20px;padding:2px;width: 700px;height: 270px">' +
    '<div style="float: left;width: 100%"><img src="http://file2.5ihw.cn:9033/comm/image/biz/5af3f480c1cb6173dc6b0195/0.1f" alt="" style="float:left;zoom:1;overflow:hidden;width:100px;height:100px;margin-right:30px;"/>' +
    '<ul style="float: left;width: 150px"><li>编号：' + content.stationCode + 'm</li><li>名称：' + content.stationName + '/km</li><li>经度：</li><li>纬度：</li></ul>' +
    '<ul style="float: left;width: 200px"><li>设施类型：' + content.stationType + '</li><li>位置：' + content.stationAddress + '</li><li>月清运次数：' + content.clearTimesMonth + '</li><li>日清运次数：' + content.clearTimesDay + '</li></ul>' +
    '<ul style="float: left;width: 180px"><li>所属区域：' + content.belongArea + '</li><li>服务范围：' + content.serviceScope + '</li><li>所属部门：' + content.dept_name + '</li></ul>' +
    '</div>' +
    '<h4>连线互动</h4><ul style="margin-top: 10px;margin-left: 30px;">' +
    '<li style="display: inline-block;width: 100px"><a href="testOpen()"><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/messaging.png">下发短信</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/globe_16.png">开启对讲</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">视频抓拍</a></li>' +
    '</ul>' +
    '<div style="float: left;margin-top: 10px">' +
    '<h4>报警信息</h4><ul style="margin-top: 10px;margin-left: 30px;float: left"><li>报警信息1：________________________</li><li>报警信息2：________________________</li><li>报警信息3：________________________</li></ul>' +
    '</div>'

  marker.addEventListener('click', function () {
    this.openInfoWindow(new BMap.InfoWindow(info))
  })
}

/* 建筑地区添加信息窗口 */
baiduMap.localInfoWindow = (marker, content) => {
  var opts = {
    width: 350, // 信息窗口宽度
    height: 150, // 信息窗口高度
    title: content.title, // 信息窗口标题
    enableMessage: true// 设置允许信息窗发送短息
  }
  var infoWindow = new BMap.InfoWindow('<div style="margin-top: 3px"><p style="font-size: 12px;margin-top: 2px"><span>省份：' + (content.province != undefined ? content.province : '无') + '</span><span>市区：' + (content.city != undefined ? content.city : '无') + '</span></p><p style="font-size: 12px;margin-top: 2px">地区类型：' + (content.tags != undefined ? content.tags : '无') + '</p><p style="font-size: 12px;margin-top: 2px">地址：' + content.address + '</p><a style="font-size: 12px" href="#" id="detail_btn">就决定是你了</a></div>', opts) // 创建信息窗口对象
  marker.addEventListener('click', function (rs) {
    map.openInfoWindow(infoWindow, content.point) // 开启信息窗口
    var btn = document.getElementById('detail_btn')
    btn.onclick = () => {
      window.queryPoint(rs.point)
    }
  })
}

/* return infoWindows */
baiduMap.rlocalInfoWindow = (content) => {
  var opts = {
    width: 350, // 信息窗口宽度
    height: 120, // 信息窗口高度
    title: content.title, // 信息窗口标题
    enableMessage: true// 设置允许信息窗发送短息
  }
  var infoWindow = new BMap.InfoWindow('<div style="margin-top: 3px"><p style="font-size: 12px;margin-top: 2px"><span>省份：' + (content.province != undefined ? content.province : '无') + '</span><span>市区：' + (content.city != undefined ? content.city : '无') + '</span></p><p style="font-size: 12px;margin-top: 2px">地区类型：' + (content.tags != undefined ? content.tags : '无') + '</p><p style="font-size: 12px;margin-top: 2px">地址：' + content.address + '</p><a style="float: right;font-size: 12px" href="#" id="detail_btn">就决定是你了</a></div>', opts) // 创建信息窗口对象
  return infoWindow
}

/* 开启信息窗口 */
baiduMap.openInfoWindow = (infoWindow, point) => {
  map.openInfoWindow(infoWindow, point)
}

/* 自定义信息窗口 */
baiduMap.infoWindowSize = (vehicleNo, content) => {
  let fuleName = '' // 燃油类型
  let operationName = (content.operation_state == 0) ? '未运营' : '运营中' // 运营状态
  let signal = '' // 信号强度
  switch (true) {
    case ((content.signal >= 0 && content.signal <= 1) || content.signal == null):
      signal = '很弱'
      break
    case content.signal >= 2 && content.signal <= 3:
      signal = '较弱'
      break
    case content.signal >= 4 && content.signal <= 5:
      signal = '一般'
      break
    case content.signal >= 6 && content.signal <= 7:
      signal = '较好'
      break
    case content.signal >= 8 && content.signal <= 9:
      signal = '非常好'
      break
  }

  for (let fule in fuleData) {
    if (fuleData[fule].value == content.fule_type) {
      fuleName = fuleData[fule].label
    }
  }

  let info = '<div style="margin:0;line-height:20px;padding:2px;">' +
    '<div style="float: left;width: 100%"><img src="http://182.92.64.172/cy.png" alt="" style="float:left;zoom:1;overflow:hidden;width:100px;height:100px;margin-right:30px;"/>' +
    '<ul style="float: left;width: 130px"><li>高度：' + content.altitude + 'm</li><li>速度：' + content.speed + '/km</li><li>经度：' + content.longitude + '</li><li>纬度：' + content.latitude + '</li></ul>' +
    '<ul style="float: left;width: 150px"><li>所属部门：' + content.dept_name + '</li><li>燃油类型：' + fuleName + '</li><li>运营状态：' + operationName + '</li><li>工作状态：' + content.work_state + '</li></ul>' +
    '<ul style="float: left;width: 150px"><li>车辆内部编号：' + content.intenal_no + '</li><li>信号强度：' + signal + '</li><li>报警状态：' + content.alarm_state + '</li></ul>' +
    '</div>' +
    '<h4>连线互动</h4><ul style="margin-top: 10px;margin-left: 30px;">' +
    '<li style="display: inline-block;width: 100px"><a href="testOpen()"><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/messaging.png">下发短信</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/globe_16.png">开启对讲</a></li>' +
    '<li style="display: inline-block;width: 100px"><a href=""><img style="width:16px;height:16px;vertical-align:middle;margin-right: 2px" src="http://210.14.146.39:8080/platform/image/ico/command_pz3.png">视频抓拍</a></li>' +
    '</ul>' +
    '<div style="float: left;margin-top: 10px">' +
    '<h4>报警信息</h4><ul style="margin-top: 10px;margin-left: 30px;float: left"><li>报警信息1：________________________</li><li>报警信息2：________________________</li><li>报警信息3：________________________</li></ul>' +
    '</div>'

  return new BMapLib.SearchInfoWindow(map, info, {
    title: vehicleNo, // 标题
    width: 600, // 宽度
    height: 270, // 高度
    panel: 'panel', // 检索结果面板
    enableAutoPan: true, // 自动平移
    searchTypes: [
    ]
  })
}

/* 创建矩形 */
baiduMap.rectangleAdd = (pStart, pEnd) => {
  var rectangle = new BMap.Polygon([
    new BMap.Point(pStart.lng, pStart.lat),
    new BMap.Point(pEnd.lng, pStart.lat),
    new BMap.Point(pEnd.lng, pEnd.lat),
    new BMap.Point(pStart.lng, pEnd.lat)
  ], {strokeColor: 'blue', strokeWeight: 2, strokeOpacity: 0.5}) // 创建矩形
  map.addOverlay(rectangle) // 增加矩形
}

/* 点聚合 */
baiduMap.MarkerClusterer = (markers) => {
  var markerClusterer = new BMapLib.MarkerClusterer(map, {markers: markers})
}

/* 清除所有覆盖物 */
baiduMap.clearOverlays = () => {
  map.clearOverlays()
}

/* 清除路书小车 */
baiduMap.clearOverlaysByGis = () => {
  let allOverlay = map.getOverlays()
  for (let i = 0; i < allOverlay.length; i++) {
    if (allOverlay[i]._html == 'saber') {
      map.removeOverlay(allOverlay[i + 1])
      map.removeOverlay(allOverlay[i])
      map.removeOverlay(allOverlay[i - 1])
      return false
    }
    // if(allOverlay[i].toString()=="[object Marker]"){
    //   if (allOverlay[i].getPosition().lng == longitude(待删除标注的经度) && allOverlay[i].getPosition().lat == latitude(待删除标注纬度)) {
    //     map.removeOverlay(allOverlay[i]);
    //   }
    // }
  }
}

// baiduMap.clearOverlaysByPoint =(gisInfo) => {
//   let allOverlay = map.getOverlays();
//   for(let i = 0;i<allOverlay.length;i++) {
//     if(allOverlay[i].toString()=="[object Marker]"){
//       gisPoint.forEach((point) => {
//         if(gisInfo.pointCoordinate === 0) {
//           if(Array.isArray(JSON.parse(gisInfo.lngLatPoints))) {
//             gisInfo.lngLatPoints = JSON.parse(gisInfo.lngLatPoints)[0]
//           }else {
//             gisInfo.lngLatPoints = JSON.parse(gisInfo.lngLatPoints)
//           }
//           let conversionPoint = conversion.GPSToBaidu(gisInfo.lngLatPoints.lat, gisInfo.lngLatPoints.lng);
//           //gps
//           let baiduPoint =new BMap.Point(conversionPoint.lng,conversionPoint.lat);
//         }
//         if (allOverlay[i].getPosition().lng == baiduPoint.lng && allOverlay[i].getPosition().lat == baiduPoint.lat) {
//           map.removeOverlay(allOverlay[i]);
//         }
//       })
//     }
//   }
// }

/* 添加绘制工具 */
baiduMap.drawingManager = (overlays) => {
  var overlaycomplete = function (e) {
    overlays.push(e.overlay)
    baiduMap.enableEditingByGon(e.overlay)
  }
  var styleOptions = {
    strokeColor: 'red', // 边线颜色。
    fillColor: 'red', // 填充颜色。当参数为空时，圆形将没有填充效果。
    strokeWeight: 3, // 边线的宽度，以像素为单位。
    strokeOpacity: 0.8, // 边线透明度，取值范围0 - 1。
    fillOpacity: 0.6, // 填充的透明度，取值范围0 - 1。
    strokeStyle: 'dashed' // 边线的样式，solid或dashed。
  }
  // 实例化鼠标绘制工具
  var drawingManager = new BMapLib.DrawingManager(map, {
    isOpen: false, // 是否开启绘制模式
    enableDrawingTool: true, // 是否显示工具栏
    enableCalculate: true,
    drawingToolOptions: {
      anchor: BMAP_ANCHOR_TOP_RIGHT, // 位置
      offset: new BMap.Size(0, 65), // 偏离值
      drawingModes: [BMAP_DRAWING_CIRCLE, BMAP_DRAWING_POLYGON, BMAP_DRAWING_RECTANGLE ]
    },

    circleOptions: styleOptions, // 圆的样式
    polylineOptions: styleOptions, // 线的样式
    polygonOptions: styleOptions, // 多边形的样式
    rectangleOptions: styleOptions // 矩形的样式
  })
  // 添加鼠标绘制工具监听事件，用于获取绘制结果
  drawingManager.addEventListener('overlaycomplete', baiduMap.overlaycomplete)
}

/* 点位绘制工具 */
baiduMap.drawingPoint = (overlays) => {
  var overlaycomplete = function (e) {
    overlays.push(e.overlay)
    baiduMap.enableEditingByGon(e.overlay)
  }
  var styleOptions = {
    strokeColor: 'red', // 边线颜色。
    fillColor: 'red', // 填充颜色。当参数为空时，圆形将没有填充效果。
    strokeWeight: 3, // 边线的宽度，以像素为单位。
    strokeOpacity: 0.8, // 边线透明度，取值范围0 - 1。
    fillOpacity: 0.6, // 填充的透明度，取值范围0 - 1。
    strokeStyle: 'dashed' // 边线的样式，solid或dashed。
  }
  // 实例化鼠标绘制工具
  var drawingManager = new BMapLib.DrawingManager(map, {
    isOpen: false, // 是否开启绘制模式
    enableDrawingTool: true, // 是否显示工具栏
    enableCalculate: true,
    drawingToolOptions: {
      anchor: BMAP_ANCHOR_TOP_RIGHT, // 位置
      offset: new BMap.Size(0, 65), // 偏离值
      drawingModes: [BMAP_DRAWING_MARKER]
    },

    circleOptions: styleOptions, // 圆的样式
    polylineOptions: styleOptions, // 线的样式
    polygonOptions: styleOptions, // 多边形的样式
    rectangleOptions: styleOptions // 矩形的样式
  })
  // 添加鼠标绘制工具监听事件，用于获取绘制结果
  drawingManager.addEventListener('overlaycomplete', baiduMap.overlaycompleteByPoint)
}

baiduMap.overlaycompleteByPoint = (e, m) => {
  map.clearOverlays()
  let marker = new BMap.Marker(m.overlay.point) // 创建点
  window.addPoint(m.overlay.point)
  map.addOverlay(marker) // 增加点
}

/* 监听绘制 */
baiduMap.overlaycomplete = (e, m) => {
  if (m.drawingMode === 'marker') {
    this.lngLatPoints.push(m.overlay.point)
    // console.log(this.lngLatPoints)
  } else {
    this.lngLatPoints.push(m.overlay.na)
    // console.log(this.lngLatPoints)
  }
  /// 创建右键菜单
  var markerMenu = new BMap.ContextMenu()
  markerMenu.addItem(new BMap.MenuItem('删除', removeMarker.bind(m.overlay)))
  m.overlay.addContextMenu(markerMenu)
}
/* delete */
var removeMarker = function (e, ee, marker) {
  map.removeOverlay(marker)
}

/* 路书 */
baiduMap.driverRoad = (arrPois, speed) => {
  // 实例化一个驾车导航用来生成路线
  map.setViewport(arrPois)
  return new BMapLib.LuShu(map, arrPois, {
    defaultContent: 'saber', // "从天安门到百度大厦"
    autoView: true, // 是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
    icon: new BMap.Icon('http://203.86.55.132:8080/platform/map/img/showico/online-270.png', new BMap.Size(40, 40), {anchor: new BMap.Size(27, 13)}),
    speed: speed,
    enableRotation: false, // 是否设置marker随着道路的走向进行旋转
    landmarkPois: [
      {lng: 116.627096, lat: 39.955301, html: '清洁站<div><img src="http://file2.5ihw.cn:9033/comm/image/biz/5adede24c1cb614be87f331e" width="200" height="100"/></div>', pauseTime: 2},
      {lng: 116.315391, lat: 39.964429, html: '高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>', pauseTime: 3},
      {lng: 116.381476, lat: 39.974073, html: '肯德基早餐', pauseTime: 2}
    ]})
}

/* 开启鼠标测距 */
baiduMap.mapDisOpen = () => {
  var myDis = new BMapLib.DistanceTool(map)
  myDis.open() // 开启鼠标测距
}

/* 关闭鼠标测距 */
baiduMap.mapDisClose = () => {
  // eslint-disable-next-line no-undef
  var myDis = new BMapLib.DistanceTool(map)
  myDis.close() // 关闭鼠标测距
}

/* 地区搜索 */
baiduMap.localSearch = (localName) => {
  let localData = new Array()
  var options = {
    onSearchComplete: function (results) {
      // 判断状态是否正确
      // eslint-disable-next-line no-undef
      if (local.getStatus() == BMAP_STATUS_SUCCESS) {
        for (var i = 0; i < results.getCurrentNumPois(); i++) {
          localData.push(results.getPoi(i))
        }
      }
    }
  }
  // eslint-disable-next-line no-undef
  var local = new BMap.LocalSearch(map, options)
  local.search(localName)
}

/* 逆地址解析 */
baiduMap.getAddress = () => {
  // eslint-disable-next-line no-undef
  return new BMap.Geocoder()
}

baiduMap.rMap = () => {
  return map
}

export default baiduMap
