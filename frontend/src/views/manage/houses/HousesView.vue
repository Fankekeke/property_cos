<template>
  <a-modal v-model="show" title="房屋信息详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="housesData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">房屋信息</span></a-col>
        <a-col :span="8"><b>房间号：</b>
          {{ housesData.number }} 室
        </a-col>
        <a-col :span="8"><b>房屋地址：</b>
          <a-tooltip>
            <template slot="title">
              {{ housesData.address }}
            </template>
            {{ housesData.address.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
        <a-col :span="8"><b>楼层：</b>
          {{ housesData.floor }} 层
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>使用面积：</b>
          {{ housesData.usageArea }} ㎡
        </a-col>
        <a-col :span="8"><b>建筑面积：</b>
          {{ housesData.surfaceArea }} ㎡
        </a-col>
        <a-col :span="8"><b>房屋类型：</b>
          <span v-if="housesData.nature == 1">住宅楼房</span>
          <span v-if="housesData.nature == 2">社区用处</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>间 数：</b>
          {{ housesData.rooms }} 间
        </a-col>
        <a-col :span="8"><b>购房人：</b>
          {{ housesData.buyer }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>管理员：</b>
          <a-popover>
            <template slot="content">
              <a-avatar v-if="housesData.workerImage !== null" shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + housesData.workerImage.split(',')[0]" />
              <a-avatar v-else shape="square" :size="132" icon="user" />
            </template>
            <a>{{ housesData.workerName !== null ? housesData.workerName : '- -' }}</a>
          </a-popover>
        </a-col>
        <a-col :span="12"><b>管理员联系方式：</b>
          {{ housesData.workerPhone !== null ? housesData.workerPhone : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">业主信息</span></a-col>
        <a-col :span="8"><b>业主姓名：</b>
          {{ housesData.name }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ housesData.phone }}
        </a-col>
        <a-col :span="8"><b>身份证号：</b>
          <a-tooltip>
            <template slot="title">
              {{ housesData.idNumber }}
            </template>
            {{ housesData.idNumber.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">楼宇信息</span></a-col>
        <a-col :span="8"><b>楼宇名称：</b>
          {{ housesData.buildName }}
        </a-col>
        <a-col :span="8"><b>楼宇地址：</b>
          <a-tooltip>
            <template slot="title">
              {{ housesData.buildAddress }}
            </template>
            {{ housesData.buildAddress.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
        <a-col :span="8"><b>所属街道：</b>
          {{ housesData.street }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>所属社区：</b>
          {{ housesData.community }}
        </a-col>
        <a-col :span="8"><b>总使用面积：</b>
          {{ housesData.buildUsageArea }} ㎡
        </a-col>
        <a-col :span="8"><b>总建筑面积：</b>
          {{ housesData.buildSurfaceArea }} ㎡
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>房屋性质：</b>
          <span v-if="housesData.type == 1">平房</span>
          <span v-if="housesData.type == 2">多层楼</span>
          <span v-if="housesData.type == 3">高层楼</span>
          <span v-if="housesData.type == 4">简易楼</span>
        </a-col>
        <a-col :span="8"><b>间数：</b>
          {{ housesData.buildRooms }} 间
        </a-col>
        <a-col :span="8"><b>单元数：</b>
          {{ housesData.units }} 间
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>楼层：</b>
          {{ housesData.layers }} 层
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ housesData.createDate }}
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
    housesShow: function (value) {
      if (value && this.housesData.images !== null && this.housesData.images !== '') {
        this.imagesInit(this.housesData.images)
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
