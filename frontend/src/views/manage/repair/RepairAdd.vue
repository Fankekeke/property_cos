<template>
  <a-modal v-model="show" title="维修上报" @cancel="onClose" :width="800">
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
            <a-input v-model="housesData.address" disabled/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='业主姓名' v-bind="formItemLayout">
            <a-input v-model="housesData.buyer" disabled/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修类型' v-bind="formItemLayout">
            <a-select allowClear v-decorator="[
              'repairType',
              { rules: [{ required: true, message: '请输入维修类型!' }] }
              ]">
              <a-select-option value="1">上下水管道</a-select-option>
              <a-select-option value="2">落水管</a-select-option>
              <a-select-option value="3">水箱</a-select-option>
              <a-select-option value="4">天线</a-select-option>
              <a-select-option value="5">供电线路</a-select-option>
              <a-select-option value="6">通讯线路</a-select-option>
              <a-select-option value="7">照明</a-select-option>
              <a-select-option value="8">供气线路</a-select-option>
              <a-select-option value="9">消防设施</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='维修内容' v-bind="formItemLayout">
            <a-textarea :auto-size="{ minRows: 3, maxRows: 5 }" v-decorator="[
            'content',
            { rules: [{ required: true, message: '请输入维修内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='照片' v-bind="formItemLayout">
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
  name: 'RepairAdd',
  props: {
    repairAddVisiable: {
      default: false
    },
    housesData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.repairAddVisiable
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
      previewImage: ''
    }
  },
  methods: {
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
        values.image = images.length > 0 ? images.join(',') : null
        values.housesId = this.housesData.id
        values.userId = this.currentUser.userId
        if (!err) {
          this.loading = true
          this.$post('/cos/repair-info', {
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
