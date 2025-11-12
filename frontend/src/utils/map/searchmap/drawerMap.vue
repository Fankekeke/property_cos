<template>
  <a-drawer
    title="选择地区"
    width="700"
    :destroyOnClose="true"
    :closable="false"
    :maskClosable="true"
    @close="onChildrenDrawerClose"
    :visible="childrenDrawerShow"
  >
    <a-input-search v-model="local" placeholder="选择区域" style="width: 200px;position: absolute;z-index: 10" @search="onSearch" size="small"/>
    <div class="map-area" id="areass"></div>
    <div v-show="cardShow">
      <a-card hoverable style="width: 100%" v-for="local in localData" :key="local.uid">
        <template class="ant-card-actions" slot="actions">
        </template>
        <a-card-meta :title="local.title" :description="local.address"></a-card-meta>
      </a-card>
    </div>
  </a-drawer>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'

export default {
  name: 'drawerMap',
  data () {
    return {
      mapId: 'areass',
      cardShow: false,
      local: '',
      localData: []
    }
  },
  props: {
    childrenDrawerShow: {default: false}
  },
  watch: {
    childrenDrawerShow: function (newval, oldval) {
      if (newval) {
        console.log(newval)
        setTimeout(function () {
          baiduMap.initMap('areass')
        }, 500)
        // if(Object.keys(baiduMap.rMap()).length === 0) {
        //
        // }
      }
    }
  },
  mounted () {
    window.addPoint = this.addPoint
    window.queryPoint = this.handlerClosed
  },
  methods: {
    onChildrenDrawerClose () {
      this.handlerClosed()
    },
    handlerClosed (localPoint) {
      this.$emit('handlerClosed', localPoint)
    },
    addPoint (point) {
      this.localPoint = point
    },
    onSearch () {
      let localData = []
      var options = {
        onSearchComplete: (results) => {
          // 判断状态是否正确
          // eslint-disable-next-line no-undef,eqeqeq
          if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
              if (i === 0) {
                setTimeout(() => {
                  baiduMap.findPoint(results.getPoi(0).point, 15)
                }, 10)
              }
              localData.push(results.getPoi(i))
              if (results.getPoi(i).point !== undefined) {
                baiduMap.localPointAdd(results.getPoi(i))
              }
            }
            this.localData = localData
            this.cardShow = true
          }
        }
      }
      // eslint-disable-next-line no-undef
      var local = new BMap.LocalSearch(baiduMap.rMap(), options)
      local.search(this.local)
    }
  }
}
</script>

<style scoped>
#areass {
  width: 100%;
  height: 400px;
  padding: 0;
  margin: 0;
}
</style>
