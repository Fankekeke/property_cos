<template>
  <a-modal v-model="show" title="订单评价" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
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
          {{ orderData.totalCost }} 元
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
            {{ commodityData.quality }}
          </a-col>
          <a-col :span="8"><b>品牌：</b>
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
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">当前物流</span></a-col>
        <a-col :span="24">
          <a-table :columns="logisticsColumns" :data-source="logisticsList">
          </a-table>
        </a-col>
      </a-row>
    </div>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='评价分数' v-bind="formItemLayout">
            <a-rate v-decorator="[
            'score',
            { rules: [{ required: true, message: '请输入评价分数!' }] }
            ]" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='评价内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'remark',
             { rules: [{ required: true, message: '请输入评价内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='图册' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'evaluateAdd',
  props: {
    evaluateAddVisiable: {
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.evaluateAddVisiable
      },
      set: function () {
      }
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
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      durgList: [],
      logisticsList: [],
      current: 0,
      buyer: null,
      sale: null,
      commodityData: null
    }
  },
  watch: {
    evaluateAddVisiable: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
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
      // 物流信息
      this.$get(`/cos/order-info/logistics/${orderId}`).then((r) => {
        this.logisticsList = r.data.data
      })
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.orderId = this.orderData.id
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$post('/cos/evaluate-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
