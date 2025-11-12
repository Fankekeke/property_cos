<template>
  <a-modal v-model="show" title="修改活动" @cancel="onClose" :width="800">
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
          <a-form-item label='活动标题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'title',
            { rules: [{ required: true, message: '请输入活动标题!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='活动地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入活动地址!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='主办方' v-bind="formItemLayout">
            <a-input v-decorator="[
            'organizer',
            { rules: [{ required: true, message: '请输入主办方!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='活动特性' v-bind="formItemLayout">
            <a-select v-decorator="[
              'status',
              { rules: [{ required: true, message: '请输入活动特性!' }] }
              ]">
              <a-select-option value="宣传">宣传</a-select-option>
              <a-select-option value="公益">公益</a-select-option>
              <a-select-option value="环保">环保</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='开始时间' v-bind="formItemLayout">
            <a-date-picker show-time format="YYYY-MM-DD HH:mm:ss" style="width: 100%" v-decorator="[
            'startTime',
            { rules: [{ required: true, message: '请输入开始时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='结束时间' v-bind="formItemLayout">
            <a-date-picker show-time format="YYYY-MM-DD HH:mm:ss" style="width: 100%" v-decorator="[
            'endTime',
            { rules: [{ required: true, message: '请输入结束时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='活动内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入活动内容!' }] }
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
import moment from 'moment'
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
  name: 'dishesEdit',
  props: {
    dishesEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.dishesEditVisiable
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
      staffIds: [],
      staffList: [],
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
  },
  methods: {
    getStaffList () {
      this.$get('/cos/staff-info/queryStaffList', {enterpriseId: 13}).then((r) => {
        this.staffList = r.data.data
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...dishes}) {
      this.rowId = dishes.id
      let fields = ['title', 'content', 'address', 'startTime', 'endTime', 'staffIds', 'organizer', 'status']
      let obj = {}
      Object.keys(dishes).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(dishes['images'])
        }
        if (key === 'startTime') {
          dishes[key] = moment(dishes[key])
        }
        if (key === 'endTime') {
          dishes[key] = moment(dishes[key])
        }
        if (key === 'staffIds') {
          setTimeout(() => {
            dishes['staffIdList'] = dishes[key].split(',').map(Number)
            // this.staffIds = dishes[key].split(',')
            console.log(dishes['staffIdList'])
          }, 500)
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = dishes[key]
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
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (values.startTime) {
          values.startTime = moment(values.startTime).format('YYYY-MM-DD HH:mm:ss')
        }
        if (values.endTime) {
          values.endTime = moment(values.endTime).format('YYYY-MM-DD HH:mm:ss')
        }
        if (!err) {
          this.loading = true
          this.$put('/cos/conference-info', {
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
