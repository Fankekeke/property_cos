<template>
  <a-modal v-model="show" title="修改业主" @cancel="onClose" :width="800">
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
          <a-form-item label='业主姓名' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入业主姓名!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='联系电话' v-bind="formItemLayout">
            <a-input v-decorator="[
            'phone',
            { rules: [{ required: true, message: '请输入联系电话!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='邮箱地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'email',
            { rules: [{ required: true, message: '请输入邮箱地址!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='身份证号' v-bind="formItemLayout">
            <a-input v-decorator="[
            'idNumber',
            { rules: [{ required: true, message: '请输入身份证号!' }] }
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
  name: 'ownerEdit',
  props: {
    ownerEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.ownerEditVisiable
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
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    setFormValues ({...owner}) {
      this.rowId = owner.id
      let fields = ['name', 'phone', 'idNumber']
      let obj = {}
      Object.keys(owner).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = owner[key]
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
          this.$put('/cos/owner-info', {
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
