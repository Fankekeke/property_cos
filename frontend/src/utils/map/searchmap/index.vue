<template>
  <mu-dialog width="360" transition="slide-top" fullscreen :open.sync="openFullscreen" :append-body="false"
             :esc-press-close="false" :overlay="false">
    <mu-appbar color="primary" title="Get Point">
      <mu-button slot="right" flat @click="handlerClosed(localPoint)">提交并关闭</mu-button>
    </mu-appbar>

    <div class="map-area" :id="mapId">
      <div id="drawing"></div>
    </div>

    <mu-card style="width: 100%; max-width: 450px; border-radius:5px;margin: 0 auto;" :raised="true">
      <mu-card-text style="padding: 0px">

        <mu-card style="width: 100%; max-width: 450px;border-radius:5px;" :raised="true">
          <mu-button icon color="grey">
            <i class="el-icon-menu"></i>
          </mu-button>
          <!--@blur="rmCardCondition"-->
          <mu-text-field style="margin: 0 ;padding: 0;vertical-align:top;height:48px;width: 275px"
                         placeholder="搜索" v-model="localName"></mu-text-field>
          <mu-button icon color="blue" @click="searchAll">
            <i class="el-icon-s-promotion"></i>
          </mu-button>
          <b> | </b>
          <mu-button icon color="green">
            <i class="el-icon-chat-line-square"></i>
          </mu-button>
        </mu-card>
      </mu-card-text>
    </mu-card>

    <mu-drawer :open.sync="dialogFormVisible" :docked="true" :right="false" width="20%">
      <mu-list textline="two-line" v-for="(local, index) in localData" :key="index">
        <mu-list-item :ripple="true" button @click="queryLocal(local)">
          <mu-list-item-content>
            <mu-list-item-title>{{ local.title }}</mu-list-item-title>
            <mu-list-item-sub-title style="color: rgba(0, 0, 0, .87)">省份：{{ local.province }} 市区：{{ local.city }}
            </mu-list-item-sub-title>
            <mu-list-item-sub-title>
              地址：{{ local.address }}
            </mu-list-item-sub-title>
          </mu-list-item-content>
          <mu-list-item-action>
            <mu-list-item-after-text v-if="local.tags != undefined">
              <span>{{ local.tags[0] }}</span>
            </mu-list-item-after-text>
          </mu-list-item-action>
        </mu-list-item>
      </mu-list>

      <!--      <div v-for="local in localData">-->
      <!--        <el-card class="box-card" shadow="always">-->
      <!--          <h3 v-if="local.title != undefined">{{local.title}}</h3>-->
      <!--          <span v-if="local.province != undefined">{{local.province}}</span> <span  v-if="local.city != undefined">{{local.city}}</span>-->
      <!--&lt;!&ndash;          <mu-card-header :title="local.title" :sub-title="local.address" style="padding: 3px;"></mu-card-header>&ndash;&gt;-->
      <!--          <mu-card-text style="padding: 1px" v-if="local.address != undefined">-->
      <!--            {{local.address}}-->
      <!--          </mu-card-text>-->
      <!--        </el-card>-->
      <!--      </div>-->
    </mu-drawer>

  </mu-dialog>
</template>

<script>
import baiduMap from '@/util/baiduMap.js'
import {getByCode} from '@/api/gis/pointinfo'

export default {
  props: {
    gisCode: String,
    required: true
  },
  name: 'index',
  watch: {
    gisCode: function (newval) {
      baiduMap.clearOverlays()
      if (newval != null && newval !== '') {
        getByCode(newval).then(response => {
          baiduMap.pointAdd(JSON.parse(response.data.data.lngLatPoints)[0])
          setTimeout(() => {
            baiduMap.findPoint(JSON.parse(response.data.data.lngLatPoints)[0], 18)
          }, 10)
        })
      }
    }
  },
  data () {
    return {
      dialogFormVisible: false,
      localName: '',
      openFullscreen: true,
      mapId: 'getPointGisMap',
      localData: [],
      localPoint: {
        lng: '',
        lat: ''
      }
    }
  },
  mounted () {
    baiduMap.initMap(this.mapId)
    this.drawPoint()
  },
  methods: {
    queryLocal (local) {
      baiduMap.openInfoWindow(this.rlocalInfoWindow(local), local.point)
      console.log(JSON.stringify(local.point))
      var btn = document.getElementById('detail_btn')
      btn.onclick = () => {
        this.handlerClosed(local.point)
      }
      baiduMap.findPoint(local.point, 18)
    },
    searchAll () {
      this.dialogFormVisible = true
      this.searchLocal(this.localName)
      baiduMap.clearOverlays()
    },
    rlocalInfoWindow (content) {
      var opts = {
        width: 350,
        height: 120,
        title: content.title,
        enableMessage: true
      }
      // @click="'+this.handlerClosed("sss")+'"
      // eslint-disable-next-line no-undef
      return new BMap.InfoWindow('<div style="margin-top: 3px"><p style="font-size: 12px;margin-top: 2px"><span>省份：' + (content.province !== undefined ? content.province : '无') + '</span><span>市区：' + (content.city !== undefined ? content.city : '无') + '</span></p><p style="font-size: 12px;margin-top: 2px">地区类型：' + (content.tags !== undefined ? content.tags : '无') + '</p><p style="font-size: 12px;margin-top: 2px">地址：' + content.address + '</p><a style="float: right;font-size: 12px" href="#" id="detail_btn">就决定是你了</a></div>', opts)
    },
    searchLocal (localName) {
      let localData = []
      var options = {
        onSearchComplete: (results) => {
          // 判断状态是否正确
          // eslint-disable-next-line no-undef,eqeqeq
          if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
              if (i === 0) {
                setTimeout(() => {
                  baiduMap.findPoint(results.getPoi(i).point, 15)
                }, 10)
              }
              localData.push(results.getPoi(i))
              if (results.getPoi(i).point !== undefined) {
                baiduMap.localPointAdd(results.getPoi(i))
              }
            }
            this.localData = localData
          }
        }
      }
      // eslint-disable-next-line no-undef
      var local = new BMap.LocalSearch(baiduMap.rMap(), options)
      local.search(localName)
    },
    handlerClosed (localPoint) {
      if (localPoint == null || localPoint === undefined) {

      } else {
        this.$emit('handlerClosed', localPoint)
      }
    },
    drawPoint () {
      // eslint-disable-next-line no-array-constructor
      baiduMap.drawingPoint(new Array())
    },
    addPoint (point) {
      this.localPoint = point
    }
  }
}
</script>

<style scoped>
#getPointGisMap {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 0;
  margin: 0;
}
</style>
