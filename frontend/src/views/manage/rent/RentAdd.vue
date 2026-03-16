<template>
  <a-modal v-model="show" title="新增出租信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='出租标题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'title',
            { rules: [{ required: true, message: '请输入出租标题!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='选择房屋' v-bind="formItemLayout">
            <a-select
              show-search
              :value="houseCode"
              placeholder="input search text"
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @search="handleSearch"
              @change="handleChange"
            >
              <a-select-option v-for="d in houseList" :key="d.code">
                {{ d.address }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='房间类型' v-bind="formItemLayout">
            <a-select v-decorator="[
              'roomType',
              { rules: [{ required: true, message: '请输入房间类型!' }] }
              ]">
              <a-select-option value="1">主卧</a-select-option>
              <a-select-option value="2">次卧</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='合租类型' v-bind="formItemLayout">
            <a-select v-decorator="[
              'rentType',
              { rules: [{ required: true, message: '请输入合租类型!' }] }
              ]">
              <a-select-option value="1">整租</a-select-option>
              <a-select-option value="2">合租</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='租金（每月）'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'rentPrice'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24" style="margin: 25px 0px;font-size: 13px;font-family: SimHei">
          <a-row :gutter="10">
            <a-col :span="3" style="text-align: center">
              <div>床</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasBed"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/chuang.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>冰箱</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasRefrigerator"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/dankaimenbingxiang.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>空调</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasAirConditioner"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/kongtiao.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>宽带</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasBroadband"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/kuandai.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>阳台</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasBalcony"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/loutianyangtai.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>燃气灶</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasGasStoves"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/ranqizao.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>热水器</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasWaterHeater"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/reshuiqi.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>沙发</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasSofa"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/shafa_1.png">
            </a-col>
            <a-col :span="24" style="margin: 20px 0px"></a-col>
            <a-col :span="3" style="text-align: center">
              <div>做饭</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasCook"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/weibolu.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>卫生间</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasBathroom"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/weiyu.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>电视</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasTelevision"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/dianshi.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>洗衣机</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasWashingMachine"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/xiyiji.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>衣柜</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasWardrobe"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/yigui.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>油烟机</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasRangeHood"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/youyanji.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>暖气</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasHeating"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/zaotai.png">
            </a-col>
            <a-col :span="3" style="text-align: center">
              <div>智能门锁</div>
              <div style="margin-bottom: 8px"><a-switch size="small" v-model="hasDoorLock"/></div>
              <img src="http://127.0.0.1:9527/imagesWeb/zhinengmensuo.png">
            </a-col>
          </a-row>
        </a-col>
        <a-col :span="24">
          <a-form-item label='租金备注' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'rentRemark',
             { rules: [{ required: true, message: '请输入!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='出租要求' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'rentalRequest',
             { rules: [{ required: true, message: '请输入!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='房间图片' v-bind="formItemLayout">
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
  name: 'rentAdd',
  props: {
    rentAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.rentAddVisiable
      },
      set: function () {
      }
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
      houseList: [],
      houseInfo: null,
      houseCode: '',
      hasTelevision: true,
      hasAirConditioner: true,
      hasWardrobe: true,
      hasBed: true,
      hasBathroom: true,
      hasDoorLock: true,
      hasBalcony: true,
      hasHeating: true,
      hasRefrigerator: true,
      hasWashingMachine: true,
      hasWaterHeater: true,
      hasBroadband: true,
      hasSofa: true,
      hasRangeHood: true,
      hasGasStoves: true,
      hasCook: true
    }
  },
  methods: {
    handleSearch (value) {
      this.$get('/cos/house-info/remote', { address: value }).then((r) => {
        this.houseList = r.data.data
      })
    },
    handleChange (value) {
      this.houseCode = value
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
    setValues (values) {
      values.hasTelevision = this.hasTelevision === true ? 1 : 2
      values.hasAirConditioner = this.hasAirConditioner === true ? 1 : 2
      values.hasWardrobe = this.hasWardrobe === true ? 1 : 2
      values.hasBed = this.hasBed === true ? 1 : 2
      values.hasBathroom = this.hasBathroom === true ? 1 : 2
      values.hasDoorLock = this.hasDoorLock === true ? 1 : 2
      values.hasBalcony = this.hasBalcony === true ? 1 : 2
      values.hasHeating = this.hasHeating === true ? 1 : 2
      values.hasRefrigerator = this.hasRefrigerator === true ? 1 : 2
      values.hasWashingMachine = this.hasWashingMachine === true ? 1 : 2
      values.hasWaterHeater = this.hasWaterHeater === true ? 1 : 2
      values.hasBroadband = this.hasBroadband === true ? 1 : 2
      values.hasSofa = this.hasSofa === true ? 1 : 2
      values.hasRangeHood = this.hasRangeHood === true ? 1 : 2
      values.hasGasStoves = this.hasGasStoves === true ? 1 : 2
      values.hasCook = this.hasCook === true ? 1 : 2
    },
    handleSubmit () {
      if (this.houseCode === '') {
        this.$message.error('请选择房屋')
        return false
      }
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.roomPictures = images.length > 0 ? images.join(',') : null
        this.setValues(values)
        if (!err) {
          values.houseCode = this.houseCode
          this.loading = true
          this.$post('/cos/rent-info', {
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
