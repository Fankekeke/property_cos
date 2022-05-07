<template>
  <a-modal v-model="show" title="新增房屋" @cancel="onClose" :width="800">
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
          <a-form-item label='房屋地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入房屋地址!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='所属楼宇' v-bind="formItemLayout">
            <a-select v-decorator="[
              'buildingId',
              { rules: [{ required: true, message: '请输入所属楼宇!' }] }
              ]">
              <a-select-option v-for="(item, index) in buildList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='房间号' v-bind="formItemLayout">
            <a-input-number style="width: 100%" :min="1" v-decorator="[
            'number',
            { rules: [{ required: true, message: '请输入房间号!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='楼层' v-bind="formItemLayout">
            <a-input-number style="width: 100%" :min="1" v-decorator="[
            'floor',
            { rules: [{ required: true, message: '请输入所属楼层!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='使用面积' v-bind="formItemLayout">
            <a-input-number style="width: 100%" :min="1" v-decorator="[
            'usageArea',
            { rules: [{ required: true, message: '请输入使用面积!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='建筑面积' v-bind="formItemLayout">
            <a-input-number style="width: 100%" :min="1" v-decorator="[
            'surfaceArea',
            { rules: [{ required: true, message: '请输入建筑面积!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='房间性质' v-bind="formItemLayout">
            <a-select v-decorator="[
              'nature',
              { rules: [{ required: true, message: '请输入房间性质!' }] }
              ]">
              <a-select-option value="1">住宅楼房</a-select-option>
              <a-select-option value="2">社区用处</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='房间数量' v-bind="formItemLayout">
            <a-input-number style="width: 100%" :min="1" v-decorator="[
            'rooms',
            { rules: [{ required: true, message: '请输入房间数量!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='购房人' v-bind="formItemLayout">
            <a-input v-decorator="[
            'buyer',
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='管理员' v-bind="formItemLayout">
            <a-select v-decorator="[
              'staffId',
              ]">
              <a-select-option v-for="(item, index) in workerList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='业主' v-bind="formItemLayout">
            <a-select v-decorator="[
              'ownerId',
              ]">
              <a-select-option v-for="(item, index) in ownerList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
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
    this.getBuildList()
    this.getWorkerList()
    this.getOwnerList()
  },
  methods: {
    getOwnerList () {
      this.$get('/cos/owner-info/list').then((r) => {
        this.ownerList = r.data.data
      })
    },
    getBuildList () {
      this.$get('/cos/building-info/list').then((r) => {
        this.buildList = r.data.data
      })
    },
    getWorkerList () {
      this.$get('/cos/worker-info/list', { type: 1 }).then((r) => {
        this.workerList = r.data.data
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
          this.$post('/cos/houses-info', {
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
