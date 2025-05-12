<template>
  <a-modal v-model="show" title="订单详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="未支付" />
          <a-step title="已支付" />
          <a-step title="正在发货" />
          <a-step title="已发货" />
          <a-step title="已收货" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ orderData.code }}
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate ? orderData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>订单价格：</b>
          {{ orderData.totalPrice ? orderData.totalPrice : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>当前状态：</b>
          <span v-if="orderData.payStatus == 0">未支付</span>
          <span v-if="orderData.payStatus == 1">已支付</span>
          <span v-if="orderData.payStatus == 2">正在发货</span>
          <span v-if="orderData.payStatus == 3">已发货</span>
          <span v-if="orderData.payStatus == 4">已收货</span>
        </a-col>
        <a-col :span="8"><b>订单金额：</b>
          {{ orderData.totalPrice }} 元
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="buyer != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">购买人信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
            {{ buyer.code }}
          </a-col>
        <a-col :span="8"><b>用户姓名：</b>
          {{ buyer.name }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ buyer.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="sale != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">出售人信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
          {{ sale.code }}
        </a-col>
        <a-col :span="8"><b>用户姓名：</b>
          {{ sale.name }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ sale.phone }}
        </a-col>
      </a-row>
      <br/>
      <div v-if="commodityData != null">
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">商品信息</span></a-col>
          <a-col :span="8"><b>商品编号：</b>
            {{ commodityData.code }}
          </a-col>
          <a-col :span="16"><b>标题：</b>
            {{ commodityData.title }}
          </a-col>
          <br/>
          <br/>
          <a-col :span="24"><b>商品描述：</b>
            {{ commodityData.content }}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>商品名称：</b>
            {{ commodityData.name }}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>所属类型：</b>
            {{ orderData.typeName }}
          </a-col>
          <a-col :span="8"><b>品牌：</b>
            {{ commodityData.brand }}
          </a-col>
          <a-col :span="8"><b>规格：</b>
            {{ commodityData.model }}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>点击次数：</b>
            {{ commodityData.clickNum }}
          </a-col>
          <a-col :span="8"><b>最近点击时间：</b>
            {{ commodityData.clickTime === null ? '- -' : commodityData.clickTime}}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>价格：</b>
            {{ commodityData.price }}
          </a-col>
          <a-col :span="8"><b>数量：</b>
            {{ commodityData.storeNum }}
          </a-col>
          <a-col :span="8"><b>创建时间：</b>
            {{ commodityData.createTime }}
          </a-col>
        </a-row>
      </div>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.orderShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '药品名称',
        dataIndex: 'drugName'
      }, {
        title: '品牌',
        dataIndex: 'brand'
      }, {
        title: '数量',
        dataIndex: 'quantity'
      }, {
        title: '药品图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '单价',
        dataIndex: 'unitPrice'
      }]
    },
    logisticsColumns () {
      return [{
        title: '物流信息',
        dataIndex: 'remark'
      }, {
        title: '操作时间',
        dataIndex: 'createDate'
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      buyer: null,
      sale: null,
      commodityData: null
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
        this.current = this.orderData.payStatus
      }
    }
  },
  methods: {
    dataInit (orderId) {
      // 商品信息
      this.$get(`/cos/order-info/detail/${orderId}`).then((r) => {
        this.buyer = r.data.buyer
        this.sale = r.data.sale
        this.commodityData = r.data.goods
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
