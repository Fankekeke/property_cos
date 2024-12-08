<template>
  <a-modal v-model="show" title="停车位信息详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei;" v-if="housesData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">停车位信息</span></a-col>
        <a-col :span="8"><b>停车编号：</b>
          {{ housesData.code }}
        </a-col>
        <a-col :span="8"><b>车位号：</b>
          {{ housesData.name }}
        </a-col>
        <a-col :span="8"><b>所属区域：</b>
          {{ housesData.address }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>创建时间：</b>
          {{ housesData.createDate }}
        </a-col>
        <a-col :span="16"><b>备注：</b>
          {{ housesData.remark }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="housesData.ownerId">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">业主信息</span></a-col>
        <a-col :span="8"><b>业主名称：</b>
          <a-popover>
            <template slot="content">
              <a-avatar v-if="housesData.images !== null" shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + housesData.images.split(',')[0]" />
              <a-avatar v-else shape="square" :size="132" icon="user" />
            </template>
            <a>{{ housesData.ownerName !== null ? housesData.ownerName : '- -' }}</a>
          </a-popover>
        </a-col>
        <a-col :span="12"><b>联系方式：</b>
          {{ housesData.phone !== null ? housesData.phone : '- -' }}
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'HousesView',
  props: {
    housesShow: {
      type: Boolean,
      default: false
    },
    housesData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.housesShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      replyList: []
    }
  },
  watch: {
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
