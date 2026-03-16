<template>
  <a-row :gutter="20" style="width: 100%;margin-top: 20px">
    <a-col :span="6" v-for="(item, index) in rentList" :key="index">
      <div style="width: 100%;margin-bottom: 15px;text-align: left;box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;">
        <a-divider orientation="left">
          <span style="font-size: 13px;font-family: SimHei;">{{item.title}} - {{item.number}}</span>
        </a-divider>
        <a-card :bordered="false" @click="rentDetail(item)" hoverable>
          <a-carousel autoplay style="height: 250px;" v-if="item.images !== undefined && item.images !== ''">
            <div style="width: 100%;height: 150px" v-for="(item, index) in item.images.split(',')" :key="index">
              <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 250px">
            </div>
          </a-carousel>
          <a-card-meta :title="item.address" :description="item.content.slice(0, 25)+'...'" style="margin-top: 10px"></a-card-meta>
          <div style="font-size: 12px;font-family: SimHei;margin-top: 8px">
            <span>{{ item.contact }}</span> |
            <span  style="margin-left: 2px">{{ item.phone }}</span> |
            <span style="margin-left: 2px">{{ item.rooms }}室</span> |
            <span style="margin-left: 2px" v-if="item.type == 1">出租</span>
            <span style="margin-left: 2px" v-if="item.type == 2">售卖</span>
            <span style="color: #f5222d; font-size: 13px;float: right">{{ item.rentPrice }}元</span>
          </div>
        </a-card>
      </div>
    </a-col>
    <rent-view :rentShow="rentView.visiable" :rentData="rentView.data" @close="rentView.visiable = false"></rent-view>
  </a-row>
</template>

<script>
import RentView from '../owner/RentView.vue'

export default {
  name: 'House',
  components: {RentView},
  data () {
    return {
      rentList: [],
      rentView: {
        visiable: false,
        data: null
      }
    }
  },
  mounted () {
    this.selectRentList()
  },
  methods: {
    rentDetail (row) {
      this.rentView.visiable = true
      this.rentView.data = row
    },
    selectRentList () {
      this.$get('/cos/rent-info/rent/list').then((r) => {
        this.rentList = r.data.data
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}
</style>
