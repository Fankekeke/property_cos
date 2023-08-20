<template>
  <a-modal v-model="show" title="修改停车位" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='车位名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入车位名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='所属区域' v-bind="formItemLayout">
            <a-select v-decorator="[
              'address',
               { rules: [{ required: true, message: '请输入所属区域!' }] }
              ]">
              <a-select-option value="A区">A区</a-select-option>
              <a-select-option value="B区">B区</a-select-option>
              <a-select-option value="C区">C区</a-select-option>
              <a-select-option value="D区">D区</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='业主' v-bind="formItemLayout">
            <a-select v-decorator="[
              'ownerId',
              ]">
              <a-select-option v-for="(item, index) in ownerList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='车位大小' v-bind="formItemLayout">
            <a-input v-decorator="[
            'parkSize',
            { rules: [{ required: true, message: '请输入车位大小!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='车位状态' v-bind="formItemLayout">
            <a-select v-decorator="[
              'status',
               { rules: [{ required: true, message: '请输入车位状态!' }] }
              ]">
              <a-select-option value="1">空闲</a-select-option>
              <a-select-option value="2">出售</a-select-option>
              <a-select-option value="3">出租</a-select-option>
              <a-select-option value="4">已预定</a-select-option>
              <a-select-option value="5">维修</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'remark'
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'HousesEdit',
  props: {
    housesEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.housesEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      buildList: [],
      workerList: [],
      ownerList: []
    }
  },
  mounted () {
    this.getOwnerList()
  },
  methods: {
    getOwnerList () {
      this.$get('/cos/owner-info/list').then((r) => {
        this.ownerList = r.data.data
      })
    },
    setFormValues ({...houses}) {
      this.rowId = houses.id
      let fields = ['address', 'name', 'remark', 'ownerId']
      let obj = {}
      Object.keys(houses).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = houses[key]
        }
        if (key === 'buildingId' && houses[key] !== null) {
          houses[key] = houses[key].toString()
        }
        if (key === 'staffId' && houses[key] !== null) {
          houses[key] = houses[key].toString()
        }
        if (key === 'ownerId' && houses[key] !== null) {
          houses[key] = houses[key].toString()
        }
      })
      this.form.setFieldsValue(obj)
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
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          this.$put('/cos/park-info', {
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
