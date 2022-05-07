<template>
  <a-modal v-model="show" title="楼宇信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="buildingData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>楼宇名称：</b>
          {{ buildingData.name }}
        </a-col>
        <a-col :span="8"><b>楼宇地址：</b>
          <a-tooltip>
            <template slot="title">
              {{ buildingData.address }}
            </template>
            {{ buildingData.address.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
        <a-col :span="8"><b>所属街道：</b>
          {{ buildingData.street }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>所属社区：</b>
          {{ buildingData.community }}
        </a-col>
        <a-col :span="8"><b>总使用面积：</b>
          {{ buildingData.usageArea }} ㎡
        </a-col>
        <a-col :span="8"><b>总建筑面积：</b>
          {{ buildingData.surfaceArea }} ㎡
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>房屋性质：</b>
          <span v-if="buildingData.type == 1">平房</span>
          <span v-if="buildingData.type == 2">多层楼</span>
          <span v-if="buildingData.type == 3">高层楼</span>
          <span v-if="buildingData.type == 4">简易楼</span>
        </a-col>
        <a-col :span="8"><b>间数：</b>
          {{ buildingData.rooms }} 间
        </a-col>
        <a-col :span="8"><b>单元数：</b>
          {{ buildingData.units }} 间
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>楼层：</b>
          {{ buildingData.layers }} 层
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ buildingData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <br/>
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
  name: 'BuildingView',
  props: {
    buildingShow: {
      type: Boolean,
      default: false
    },
    buildingData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.buildingShow
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
    buildingShow: function (value) {
      if (value && this.buildingData.images !== null && this.buildingData.images !== '') {
        this.imagesInit(this.buildingData.images)
      }
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
