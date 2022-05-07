<template>
  <a-modal v-model="show" title="缴费管理详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="paymentData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>业主姓名：</b>
          {{ paymentData.name }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ paymentData.phone }}
        </a-col>
        <a-col :span="8"><b>身份证号：</b>
          <a-tooltip>
            <template slot="title">
              {{ paymentData.idNumber }}
            </template>
            {{ paymentData.idNumber.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>收取年份：</b>
          {{ paymentData.year }} 年
        </a-col>
        <a-col :span="8"><b>收取月份：</b>
          {{ paymentData.month }} 月
        </a-col>
        <a-col :span="8"><b>房屋地址：</b>
          <a-tooltip>
            <template slot="title">
              {{ paymentData.address }}
            </template>
            {{ paymentData.address.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>缴费类型：</b>
          {{ paymentData.propertyName }}
        </a-col>
        <a-col :span="8"><b>本月消耗：</b>
          <span v-if="paymentData.type == 1 || paymentData.type == 2">{{ paymentData.propertyName }} {{ paymentData.dosage }} {{ paymentData.propertyUnit }}</span>
          <span v-else>- -</span>
        </a-col>
        <a-col :span="8"><b>需缴费用：</b>
          {{ paymentData.price }} 元
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">电费梯度计算</span></a-col>
        <a-col :span="24"><b>第一档电量：</b>
          每户每月0~200度 电费不做调整
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>第二档电量：</b>
          每户每月201~400度 电价每度加0.05
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>第三档电量：</b>
          每户每月400度以上 电价每度加0.3
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'PaymentView',
  props: {
    paymentShow: {
      type: Boolean,
      default: false
    },
    paymentData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.paymentShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false
    }
  },
  methods: {
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
