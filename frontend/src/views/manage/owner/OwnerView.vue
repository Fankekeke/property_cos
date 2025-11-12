<template>
  <a-modal v-model="show" title="业主信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="ownerData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">业主信息</span></a-col>
        <a-col :span="8"><b>业主姓名：</b>
          <a-popover>
            <template slot="content">
              <a-avatar v-if="ownerData.images !== null" shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + ownerData.images" />
              <a-avatar v-else shape="square" :size="132" icon="user" />
            </template>
            <a>{{ ownerData.name !== null ? ownerData.name : '- -' }}</a>
          </a-popover>
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ ownerData.phone }}
        </a-col>
        <a-col :span="8"><b>身份证号：</b>
          <a-tooltip>
            <template slot="title">
              {{ ownerData.idNumber }}
            </template>
            {{ ownerData.idNumber.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>邮箱地址：</b>
          {{ ownerData.email }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">业主房屋</span></a-col>
        <div v-if="housesList.length === 0" style="color: rgba(0,0,0,.85);font-size: 24px;line-height: 1.8;text-align: center;height: 250px;">
          <p style="margin-top: 100px">暂无房屋</p>
        </div>
        <a-col :span="12" v-for="(item, index) in housesList" :key="index">
          <a-card hoverable>
            <img
              style="height: 180px;object-fit: cover"
              slot="cover"
              alt="example"
              :src="'http://127.0.0.1:9527/imagesWeb/' + item.images.split(',')[0]"
            />
            <a-card-meta :title="item.number + '室'" :description="item.address"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'OwnerView',
  props: {
    ownerShow: {
      type: Boolean,
      default: false
    },
    ownerData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.ownerShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      housesList: []
    }
  },
  watch: {
    ownerShow: function (value) {
      if (value) {
        this.housesInfoByOwnerId(this.ownerData.id)
      }
    }
  },
  methods: {
    housesInfoByOwnerId (ownerId) {
      this.$get('/cos/houses-info/housesInfoByOwnerId', { ownerId }).then((r) => {
        console.log(r.data.data)
        this.housesList = r.data.data
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
