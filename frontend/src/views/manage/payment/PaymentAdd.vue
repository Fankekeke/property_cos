<template>
  <a-modal v-model="show" title="抄表入库" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <div>
      <a-row :gutter="20">
        <a-col :span="14">
          <a-form-item label='所属房屋' v-bind="formItemLayout">
            <a-select v-model="housesId" style="width: 100%" showSearch optionFilterProp="children" @change="housesChange">
              <a-select-option v-for="(item, index) in housesList" :key="index" :value="item.id">{{ item.address }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24" v-if="housesData !== null">
          <a-row :gutter="15">
            <a-col :span="8">
              <b>本月电表指数（度）</b>
              <a-input-number @blur="energyChange" v-model="energyNow" v-if="item.type == 1" v-for="(item, index) in consumptionInfo" :key="index" style="width: 100%" :min="item.dose"/>
              <a-input-number @blur="energyChange" v-model="energyNow" v-if="consumptionInfo.length === 0" style="width: 100%" :min="0.1"/>
            </a-col>
            <a-col :span="8">
              <b>本月水表指数（吨）</b>
              <a-input-number @blur="waterChange" v-model="waterNow" v-if="item.type == 2" v-for="(item, index) in consumptionInfo" :key="index" style="width: 100%" :min="item.dose"/>
              <a-input-number @blur="waterChange" v-model="waterNow" v-if="consumptionInfo.length === 0" style="width: 100%" :min="0.1"/>
            </a-col>
          </a-row>
          <br/>
          <a-row>
            <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">房屋信息</span></a-col>
            <a-col :span="8"><b>房间号：</b>
              {{ housesData.number }} 室
            </a-col>
            <a-col :span="8"><b>房屋地址：</b>
              <a-tooltip>
                <template slot="title">
                  {{ housesData.address }}
                </template>
                {{ housesData.address.slice(0, 10) }} ...
              </a-tooltip>
            </a-col>
            <a-col :span="8"><b>楼层：</b>
              {{ housesData.floor }} 层
            </a-col>
          </a-row>
          <br/>
          <a-row>
            <a-col :span="8"><b>使用面积：</b>
              {{ housesData.usageArea }} ㎡
            </a-col>
            <a-col :span="8"><b>建筑面积：</b>
              {{ housesData.surfaceArea }} ㎡
            </a-col>
            <a-col :span="8"><b>房屋类型：</b>
              <span v-if="housesData.nature == 1">住宅楼房</span>
              <span v-if="housesData.nature == 2">社区用处</span>
            </a-col>
          </a-row>
          <br/>
          <a-row>
            <a-col :span="8"><b>间 数：</b>
              {{ housesData.rooms }} 间
            </a-col>
            <a-col :span="8"><b>购房人：</b>
              {{ housesData.buyer }}
            </a-col>
          </a-row>
          <br/>
          <br/>
        </a-col>
        <a-col :span="24">
          <a-row v-for="(item, index) in propertyList" :key="index">
            <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">
              {{ item.name }} -
              <span v-if="item.type == 1">统一交付</span>
              <span v-if="item.type == 2">
                按量计算
                <span v-if="item.id == 1">
                  <a-tooltip>
                  <template slot="title">
                    <p>第一档电量：每户每月0~200度 电费不做调整</p>
                    <p>第二档电量：每户每月201~400度 电价每度加0.05</p>
                    <p>第三档电量：每户每月400度以上 电价每度加0.3</p>
                  </template>
                  <a-icon type="question" />
                </a-tooltip>
                </span>
              </span>
            </span></a-col>
            <a-col :span="8"><b>价 格：</b>
              <span v-if="item.type == 1">{{ item.price }} 元</span>
              <span v-if="item.type == 2">{{ item.price }} 元/ {{ item.unit }}</span>
            </a-col>
            <a-col :span="8" v-for="(iitem, iindex) in consumptionInfo" :key="iindex" v-if="iitem.type == item.id && item.id == 1"><b>上月用电：</b>
              {{ iitem.dose }} {{ item.unit }}
            </a-col>
            <a-col :span="8" v-if="item.id == 1 && consumptionInfo.length === 0 && housesData !== null"><b>上月用电：</b> - - </a-col>
            <a-col :span="8" v-if="item.id == 1 && housesData !== null"><b>本月电费：</b>{{ electricityBill }} 元</a-col>
            <a-col :span="8" v-if="item.id == 3 && housesData !== null"><b>物业费用：</b>
              {{ (housesData.usageArea * item.price).toFixed(2)}} 元
            </a-col>
            <a-col :span="8" v-for="(iitem, iindex) in consumptionInfo" :key="iindex" v-if="iitem.type == item.id && item.id == 2"><b>上月用水：</b>
              {{ iitem.dose }} {{ item.unit }}
            </a-col>
            <a-col :span="8" v-if="item.id == 2 && consumptionInfo.length === 0 && housesData !== null"><b>上月用水：</b> - - </a-col>
            <a-col :span="8" v-if="item.id == 2 && housesData !== null"><b>本月水费：</b>{{ waterBill }} 元</a-col>
            <br/>
            <br/>
            <br/>
          </a-row>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'PaymentAdd',
  props: {
    paymentAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.paymentAddVisiable
      },
      set: function () {
      }
    }
  },
  mounted () {
    this.getPropertyList()
    this.getHousesList()
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      housesData: null,
      consumptionInfo: [],
      housesId: null,
      propertyList: [],
      housesList: [],
      energyNow: 0,
      waterNow: 0,
      energyUsed: 0,
      waterUsed: 0,
      electricityBill: 0,
      waterBill: 0
    }
  },
  methods: {
    energyChange () {
      let price = 0
      this.propertyList.forEach(item => {
        if (item.id === 1) {
          price = item.price
        }
      })
      if (this.consumptionInfo.length === 0) {
        this.electricityBill = this.electricityCalculation(this.energyNow, price)
      } else {
        this.consumptionInfo.forEach(item => {
          if (item.type === 1) {
            let energyMonth = this.energyNow - item.dose
            this.electricityBill = this.electricityCalculation(energyMonth, price)
          }
        })
      }
    },
    electricityCalculation (energyMonth, price) {
      let price0 = 0
      let price1 = 0
      let price2 = 0
      if (energyMonth > 400) {
        price2 = ((energyMonth - 400) * (price + 0.3)).toFixed(2)
        price0 = (400 * price).toFixed(2)
      } else if (energyMonth > 200) {
        price1 = ((energyMonth - 200) * (price + 0.05)).toFixed(2)
        price0 = (200 * price).toFixed(2)
      } else if (energyMonth <= 200) {
        price0 = (energyMonth * price).toFixed(2)
      }
      return Number(price0) + Number(price1) + Number(price2)
    },
    waterChange () {
      let price = 0
      this.propertyList.forEach(item => {
        if (item.id === 2) {
          price = item.price
        }
      })
      if (this.consumptionInfo.length === 0) {
        this.waterBill = (this.waterNow * price).toFixed(2)
      } else {
        this.consumptionInfo.forEach(item => {
          if (item.type === 2) {
            this.waterBill = ((this.waterNow - item.dose) * price).toFixed(2)
          }
        })
      }
    },
    housesChange (value, option) {
      this.housesList.forEach(item => {
        if (item.id === value) {
          this.housesData = item
        }
      })
      this.housesConsumption(value)
      if (value) {
        this.housesId = value
      }
    },
    housesConsumption (housesId) {
      this.$get('/cos/houses-info/housesConsumption', { housesId }).then((r) => {
        this.consumptionInfo = r.data.data
      })
    },
    filterOption (input, option) {
      return (
        option.componentOptions.address[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    getPropertyList () {
      this.$get('/cos/property-item/list').then((r) => {
        this.propertyList = r.data.data
      })
    },
    getHousesList () {
      this.$get('/cos/houses-info/list').then((r) => {
        this.housesList = r.data.data
      })
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
      this.$get('/cos/houses-info/housesConsumptionMonth', { housesId: this.housesId }).then((r) => {
        if (r.data.data.length !== 0) {
          this.$message.warning('该房屋本月已进行清算！')
        } else {
          let paymentList = []
          let consumptionList = []
          this.propertyList.forEach(item => {
            let dosage = 0
            let price = item.price
            if (item.id === 1 || item.id === 2) {
              consumptionList.push({housesId: this.housesId, type: item.id, year: new Date().getFullYear(), month: Number(new Date().getMonth()) + 1, dose: item.id === 1 ? this.energyNow : this.waterNow})
              if (this.consumptionInfo.length === 0) {
                dosage = item.id === 1 ? this.energyNow : this.waterNow
              } else {
                this.consumptionInfo.forEach(consumption => {
                  if (item.id === consumption.type && item.id === 1) {
                    dosage = Number(this.energyNow) - Number(consumption.dose)
                  }
                  if (item.id === consumption.type && item.id === 2) {
                    dosage = Number(this.waterNow) - Number(consumption.dose)
                  }
                })
              }
              if (item.id === 1) {
                price = this.electricityBill
              }
              if (item.id === 2) {
                price = this.waterBill
              }
            }
            if (item.id === 3) {
              price = item.price * this.housesData.usageArea
            }
            paymentList.push({housesId: this.housesId, type: item.id, year: new Date().getFullYear(), month: Number(new Date().getMonth()) + 1, dosage, price})
          })
          this.$post('/cos/payment-manage/addPaymentManage', { paymentManageStr: JSON.stringify(Object.values(paymentList)) }).then((r) => {
            this.$post('/cos/consumption-info/addConsumption', { consumptionInfoStr: JSON.stringify(Object.values(consumptionList)) }).then((r) => {
              this.electricityBill = 0
              this.waterBill = 0
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })
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
