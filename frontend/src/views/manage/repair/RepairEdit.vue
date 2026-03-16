<template>
  <a-modal v-model="show" title="选择工作人员" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="repairData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>业主姓名：</b>
          {{ repairData.name }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ repairData.phone }}
        </a-col>
        <a-col :span="8"><b>上次登陆时间：</b>
          {{ repairData.lastTime !== null ? repairData.lastTime : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>维修编号：</b>
          {{ repairData.code }}
        </a-col>
        <a-col :span="16"><b>维修内容：</b>
          {{ repairData.content }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            disabled
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>维修类型：</b>
          <span v-if="repairData.repairType == 1">上下水管道</span>
          <span v-if="repairData.repairType == 2">落水管</span>
          <span v-if="repairData.repairType == 3">水箱</span>
          <span v-if="repairData.repairType == 4">天线</span>
          <span v-if="repairData.repairType == 5">供电线路</span>
          <span v-if="repairData.repairType == 6">通讯线路</span>
          <span v-if="repairData.repairType == 7">照明</span>
          <span v-if="repairData.repairType == 8">供气线路</span>
          <span v-if="repairData.repairType == 9">消防设施</span>
        </a-col>
        <a-col :span="8"><b>维修状态：</b>
          <span v-if="repairData.repairStatus == 0">未派修</span>
          <span v-if="repairData.repairStatus == 1">已派修</span>
          <span v-if="repairData.repairStatus == 2">已完成</span>
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ repairData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>房屋地址：</b>
          <a-tooltip>
            <template slot="title">
              {{ repairData.address }}
            </template>
            {{ repairData.address.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
        <a-col :span="8"><b>房间号：</b>
          {{ repairData.number }} 室
        </a-col>
        <a-col :span="8"><b>楼层：</b>
          {{ repairData.floor }} 层
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="repairData.worker !== null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">维修员工信息</span></a-col>
        <a-col :span="8"><b>员工姓名：</b>
          <a-popover>
            <template slot="content">
              <a-avatar v-if="repairData.workerImage !== null" shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + repairData.workerImage.split(',')[0]" />
              <a-avatar v-else shape="square" :size="132" icon="user" />
            </template>
            <a>{{ repairData.workerName !== null ? repairData.workerName : '- -' }}</a>
          </a-popover>
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ repairData.workerPhone }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">更换维修员工</span></a-col>
        <a-col :span="10">
          <a-select v-model="workerId" style="width: 100%">
            <a-select-option v-for="(item, index) in workerList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
          </a-select>
        </a-col>
        <a-col :span="3">
          <a-button key="back" @click="handleSubmit">
            提交
          </a-button>
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
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'RepairEdit',
  props: {
    repairEditVisiable: {
      default: false
    },
    repairData: {
      type: Object
    }
  },
  watch: {
    repairEditVisiable: function (value) {
      if (value && this.repairData.images !== null && this.repairData.images !== '') {
        this.imagesInit(this.repairData.images)
      }
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.repairEditVisiable
      },
      set: function () {
      }
    }
  },
  mounted () {
    this.getWorkerList()
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      workerId: null,
      workerList: ''
    }
  },
  methods: {
    getWorkerList () {
      this.$get('/cos/worker-info/list', { type: 2 }).then((r) => {
        this.workerList = r.data.data
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      let data = Object.assign(this.repairData, {})
      if (this.workerId !== null) {
        data.worker = this.workerId
        this.$put('/cos/repair-info', data).then((r) => {
          this.workerId = null
          this.$emit('success')
        }).catch(() => {
          this.loading = false
        })
      } else {
        this.$message.warning('请选择工作人员！')
      }
    }
  }
}
</script>

<style scoped>

</style>
