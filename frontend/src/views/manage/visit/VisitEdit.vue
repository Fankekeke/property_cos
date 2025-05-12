<template>
  <a-modal v-model="show" title="修改来访记录" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit(1)">
        通过
      </a-button>
      <a-button key="submit1" type="danger" :loading="loading" @click="handleSubmit(2)">
        驳回
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='来访人员姓名' v-bind="formItemLayout">
            <a-input disabled v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入来访记录名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='联系方式' v-bind="formItemLayout">
            <a-input disabled v-decorator="[
            'phone',
            { rules: [{ required: true, message: '请输入联系方式!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='访问日期' v-bind="formItemLayout">
            <a-input disabled v-decorator="[
            'visitTime',
            { rules: [{ required: true, message: '请输入访问日期!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='访问目的' v-bind="formItemLayout">
            <a-textarea disabled :rows="6" v-decorator="[
            'purposeVisit'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='来访备注' v-bind="formItemLayout">
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
import moment from 'moment'
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
      let fields = ['visitTime', 'name', 'phone', 'purposeVisit']
      let obj = {}
      Object.keys(houses).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = houses[key]
        }
        if (key === 'visitTime' && houses[key] != null) {
          houses[key] = moment(houses[key])
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
    handleSubmit (status) {
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          values.visitTime = moment(values.visitTime).format('YYYY-MM-DD')
          values.status = status
          this.$put('/cos/visit-record', {
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
