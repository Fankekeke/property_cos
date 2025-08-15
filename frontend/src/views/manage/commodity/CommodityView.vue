<template>
  <a-modal v-model="show" title="商品详情" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="commodityData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">商品信息</span></a-col>
        <a-col :span="8"><b>商品编号：</b>
          {{ commodityData.code }}
        </a-col>
        <a-col :span="16"><b>标题：</b>
          {{ commodityData.title }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>商品描述：</b>
          {{ commodityData.content }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>商品名称：</b>
          {{ commodityData.name }}
        </a-col>
        <a-col :span="8"><b>所属用户：</b>
          {{ commodityData.userName }}
        </a-col>
        <a-col :span="8"><b>所属用户：</b>
          {{ commodityData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>所属类型：</b>
          {{ commodityData.typeName }}
        </a-col>
        <a-col :span="8"><b>品牌：</b>
          {{ commodityData.brand }}
        </a-col>
        <a-col :span="8"><b>规格：</b>
          {{ commodityData.model }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>点击次数：</b>
          {{ commodityData.quality }}
        </a-col>
        <a-col :span="8"><b>品牌：</b>
          {{ commodityData.clickNum }}
        </a-col>
        <a-col :span="8"><b>最近点击时间：</b>
          {{ commodityData.clickTime === null ? '- -' : commodityData.clickTime}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>价格：</b>
          {{ commodityData.price }}
        </a-col>
        <a-col :span="8"><b>数量：</b>
          {{ commodityData.storeNum }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ commodityData.createTime }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>更新时间：</b>
          {{ commodityData.updateTime === null ? '- -' : commodityData.updateTime}}
        </a-col>
        <a-col :span="8"><b>商品状态：</b>
          <span v-if="commodityData.status == 0" style="color: green">上架</span>
          <span v-if="commodityData.status == 1" style="color: orange">下架</span>
          <span v-if="commodityData.status == 2" style="color: red">售出</span>
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">商品图片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
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
  name: 'commodityView',
  props: {
    commodityShow: {
      type: Boolean,
      default: false
    },
    commodityData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.commodityShow
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
      previewImage: ''
    }
  },
  watch: {
    commodityShow: function (value) {
      if (value) {
        if (this.commodityData.images !== null && this.commodityData.images !== '') {
          this.imagesInit(this.commodityData.images)
        }
      }
    }
  },
  methods: {
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
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
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
