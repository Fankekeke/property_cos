<template>
  <a-modal v-model="show" title="新增停车位" @cancel="onClose" :width="800">
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
          <a-form-item label='车位名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入车位名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='所属区域' v-bind="formItemLayout">
            <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入所属区域!' }] }
            ]"/>
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
  name: 'HousesAdd',
  props: {
    housesAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.housesAddVisiable
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
        if (!err) {
          values.publisher = this.currentUser.userId
          this.loading = true
          this.$post('/cos/park-info', {
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
