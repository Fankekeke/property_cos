<template>
  <div style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
    <a-row :gutter="8" class="count-info">
      <a-card class="head-info-card">
        <a-row>
          <a-col :span="24">
            <a-input-search placeholder="搜索商品" style="width: 200px;margin-top: 10px;float: right" v-show="!postDetailShow" @search="onSearch" />
          </a-col>
        </a-row>
        <a-tabs :activeKey="tabKey" tab-position="top" @change="tabChange" v-show="!postDetailShow">
          <a-tab-pane v-for="item in tagList" :key="item.id" :tab="item.name">
            <a-skeleton active v-if="loading" />
            <div v-if="!loading" style="padding: 25px 80px">
              <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="postList">
                <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                  <template slot="actions">
                    <span key="star">
                      <span style="color: red">{{ item.price }}</span> 元
                    </span>
                    <span key="to-top">
                      <a-icon type="to-top" style="margin-right: 8px" />
                      {{ timeFormat(item.createTime) }}
                    </span>
                  </template>
                  <a-list-item-meta :description="item.content.slice(0, 100) + '...'">
                    <a slot="title" @click="postReplyDetail(item)">{{ item.title }}</a>
                    <a-avatar shape="square" slot="avatar" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + item.images.split(',')[0]" />
                  </a-list-item-meta>
                </a-list-item>
              </a-list>
            </div>
          </a-tab-pane>
        </a-tabs>
        <div v-if="postDetailShow && postDetail !== null" style="margin: 18px; font-family: SimHei">
          <div style="margin-bottom: 10px">
            <a-breadcrumb>
              <a-breadcrumb-item><a @click="postDetailShow = false">返回</a></a-breadcrumb-item>
              <a-breadcrumb-item>{{ tabName }}</a-breadcrumb-item>
            </a-breadcrumb>
          </div>
          <p style="font-size: 25px;color: black;font-weight: 500;line-height: 150%;margin: 25px 50px;margin-top: 50px">
            {{ postDetail.title }}（{{ postDetail.status != 0 ? '已售出' : '未售出' }}）
            <a-popconfirm
              title="确认购买?"
              ok-text="Yes"
              cancel-text="No"
              @confirm="confirm"
            >
              <a href="#" v-if="postDetail.oldUserId != currentUser.userId && postDetail.status == 0">
                <a-icon type="wallet" theme="twoTone" style="font-size: 25px;margin-left: 30px"/>
              </a>
            </a-popconfirm>
          </p>
          <div style="margin: 25px 50px;font-size: 13px">
            {{ postDetail.userName }}
            <a-divider type="vertical" />
            <a-icon type="eye" style="margin-right: 10px;margin-left: 40px" />
            {{ postDetail.clickNum }} 访问
            <a-divider type="vertical" />
            <a-divider type="vertical" />
            {{ timeFormat(postDetail.createTime) }}
          </div>
          <div style="margin: 25px 50px;font-size: 15px;line-height: 1.6;word-break: break-word;letter-spacing: 1px;text-indent: 30px">
            <a-row>
              {{ postDetail.content }}
            </a-row>
          </div>
          <a-row style="margin: 0 50px">
            <a-col :span="24"><b>商品名称：</b>
              {{ postDetail.name }}
            </a-col>
            <a-col :span="24"><b>联系方式：</b>
              {{ postDetail.phone }}
            </a-col>
          </a-row>
          <br/>
          <a-row style="margin: 0 50px">
            <a-col :span="24"><b>所属类型：</b>
              {{ postDetail.typeName }}
            </a-col>
            <a-col :span="24"><b>品牌：</b>
              {{ postDetail.brand }}
            </a-col>
            <a-col :span="24"><b>规格：</b>
              {{ postDetail.model }}
            </a-col>
          </a-row>
          <br/>
          <a-row style="margin: 0 50px">
            <a-col :span="24"><b>价格：</b>
              {{ postDetail.price }}
            </a-col>
            <a-col :span="24"><b>数量：</b>
              {{ postDetail.storeNum }}
            </a-col>
          </a-row>
          <div style="margin: 25px 50px;height: 100px">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
            >
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>
          <div style="margin: 25px 50px;">
            <a-list
              class="comment-list"
              :pagination="pagination"
              :header="`${replyList.length} 回复`"
              item-layout="horizontal"
              :data-source="replyList"
            >
              <a-list-item slot="renderItem" slot-scope="item, index">
                <a-comment :author="item.username" shape="square" :avatar="'http://127.0.0.1:9527/imagesWeb/' + item.images">
                  <template slot="actions">
                    <span @click="replyUserAdd(item)">回复</span>
                  </template>
                  <p slot="content" style="white-space: pre-line;">
                    {{ item.content }}
                  </p>
                  <a-tooltip slot="datetime" :title="item.createDate">
                    <span>{{ timeFormat(item.createDate) }}</span>
                  </a-tooltip>
                </a-comment>
              </a-list-item>
            </a-list>
            <div style="margin-bottom: 200px;margin-top: 50px">
              <a-textarea
                v-model="replyContent"
                placeholder="Controlled autosize"
                :rows="5"
              />
              <a-button type="primary" style="float: right;margin-top: 15px" @click="commit">
                提交
              </a-button>
            </div>
          </div>
        </div>
      </a-card>
    </a-row>
  </div>
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
export default {
  name: 'Work',
  data () {
    return {
      roomList: [],
      roomTypeList: [],
      loading: false,
      vehicleView: {
        visiable: false,
        data: null
      },
      rentView: {
        visiable: false,
        data: null
      },
      startDate: null,
      endDate: null,
      tagList: [],
      tagListData: [],
      postList: [],
      replyList: [],
      postDetail: null,
      tabName: '',
      tabKey: '',
      postDetailShow: false,
      pagination: {
        pageSize: 20
      },
      fileList: [],
      previewVisible: false,
      previewImage: '',
      replyContent: '',
      replyUser: null,
      collectPost: 0
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  watch: {
    replyContent: function (value) {
      if (value === '') {
        this.replyUser = null
      }
    }
  },
  mounted () {
    this.getTypeList()
  },
  methods: {
    confirm (e) {
      this.$post(`/cos/order-info`, {
        buyUserId: this.currentUser.userId,
        commodityId: this.postDetail.id
      }).then((r) => {
        this.postDetailShow = false
        this.getTypeList()
        this.$message.success('添加订单成功！')
      })
    },
    onSearch (key) {
      if (key !== '') {
        this.loading = true
        if (this.tagList[this.tagList.length - 1].id !== 9999) {
          this.tagList.push({id: 9999, name: '搜索'})
        }
        this.tabKey = 9999
        this.tabName = '搜索'
        this.$get(`/cos/commodity-info/list/serach/${key}`).then((r) => {
          this.postList = r.data.data
          setTimeout(() => {
            this.loading = false
          }, 500)
        })
      }
    },
    getTypeList () {
      this.$get('/cos/commodity-type/list').then((r) => {
        this.tagList = r.data.data
        if (this.tagList.length !== 0) {
          this.tabChange(this.tagList[0].id)
        }
        let tagListData = []
        r.data.data.forEach(item => {
          tagListData.push({label: item.name, value: item.id})
        })
        this.tagListData = tagListData
      })
    },
    tabChange (key) {
      this.tabName = this.tagList.find(o => o.id === key).name
      this.tabKey = key
      if (key !== 9999 && key !== -1) {
        this.getPostList(key)
        if (this.tagList[this.tagList.length - 1].id === 9999) {
          this.tagList.pop()
        }
      }
    },
    getPostList (tagId) {
      this.loading = true
      this.$get(`/cos/commodity-info/tag/${tagId}`).then((r) => {
        this.postList = r.data.data
        setTimeout(() => {
          this.loading = false
        }, 500)
      })
    },
    commit () {
      if (this.replyContent !== '') {
        let data = {userId: this.currentUser.userId, content: this.replyContent, commodityId: this.postDetail.id, replyUserId: this.replyUser}
        this.$post(`/cos/reply-info`, data).then((r) => {
          if (r.data.code === 500) {
            this.$message.error(r.data.msg)
          } else {
            this.postReplyDetail(this.postDetail)
            this.replyContent = ''
          }
        })
      } else {
        this.$message.error('请填写评论！')
      }
    },
    replyUserAdd (reply) {
      this.replyUser = reply.userId
      this.replyContent = this.replyContent + '@' + reply.username
    },
    collectPostCheck (deleteFlag) {
      this.$post(`/cos/collect-info`, {userId: this.currentUser.userId, commodityId: this.postDetail.id, deleteFlag}).then((r) => {
        this.postReplyDetail(this.postDetail)
        this.$message.success(deleteFlag === 0 ? '收藏商品成功！' : '取消收藏成功！')
      })
    },
    postReplyDetail (post) {
      this.postInfoDetail(post.id)
      this.replyUser = []
      this.fileList = []
      this.$get(`/cos/reply-info/list/${post.id}`).then((r) => {
        this.replyList = r.data.data
        this.postDetailShow = true
      })
    },
    postInfoDetail (postId) {
      this.$get(`/cos/commodity-info/post/${postId}`).then((r) => {
        this.postDetail = r.data
        this.imagesInit(this.postDetail.images)
      })
    },
    collectByUser (postId) {
      this.$get(`/cos/commodity-info/collcet`, {userId: this.currentUser.userId, commodityId: postId}).then((r) => {
        this.collectPost = r.data.collect
      })
    },
    handlevehicleViewClose () {
      this.vehicleView.visiable = false
    },
    handlevehicleViewSuccess () {
      this.vehicleView.visiable = false
      this.$message.success('添加订单成功')
    },
    orderSuccess () {
      this.rentView.visiable = false
      this.$message.success('添加订单成功')
      this.fetch()
    },
    collectDelete (id) {
      this.$delete(`/cos/collect-info/${id}`).then((r) => {
        this.$message.success('取消收藏成功')
        this.fetch()
      })
    },
    collectAdd (roomCode) {
      let data = { userId: this.currentUser.userId, roomCode }
      this.$post(`/cos/collect-info`, data).then((r) => {
        this.$message.success('收藏成功')
        this.fetch()
      })
    },
    view (record) {
      if (this.startDate && this.endDate) {
        this.vehicleView.visiable = true
        this.vehicleView.data = record
      } else {
        this.$message.error('请选择租赁时间！')
      }
    },
    getRoomType () {
      this.$get(`/cos/vehicle-type-info/list`).then((r) => {
        this.roomTypeList = r.data.data
      })
    },
    getWorkStatusList (params) {
      if (params) {
        params.userId = this.currentUser.userId
        this.$get(`/cos/vehicle-info/order/check`, params).then((r) => {
          this.roomList = r.data.data
        })
      } else {
        this.$get(`/cos/vehicle-info/order/check`, { userId: this.currentUser.userId }).then((r) => {
          this.roomList = r.data.data
        })
      }
    },
    fetch () {
      if (this.startDate && this.endDate) {
        let params = { startDate: this.startDate, endDate: this.endDate }
        this.getWorkStatusList(params)
      } else {
        this.getWorkStatusList()
      }
    },
    timeFormat (time) {
      console.log(time)
      if (!time) {
        return ''
      }
      var nowTime = new Date()
      var day = nowTime.getDate()
      var hours = parseInt(nowTime.getHours())
      var minutes = nowTime.getMinutes()
      // 开始分解付入的时间
      var timeday = time.substring(8, 10)
      var timehours = parseInt(time.substring(11, 13))
      var timeminutes = time.substring(14, 16)
      // eslint-disable-next-line camelcase
      var d_day = Math.abs(day - timeday)
      // eslint-disable-next-line camelcase
      var d_hours = hours - timehours
      // eslint-disable-next-line camelcase
      var d_minutes = Math.abs(minutes - timeminutes)
      // eslint-disable-next-line camelcase
      if (d_day <= 1) {
        // eslint-disable-next-line camelcase
        switch (d_day) {
          case 0:
            // eslint-disable-next-line camelcase
            if (d_hours === 0 && d_minutes > 0) {
              // eslint-disable-next-line camelcase
              return d_minutes + '分钟前'
              // eslint-disable-next-line camelcase
            } else if (d_hours === 0 && d_minutes === 0) {
              return '1分钟前'
            } else {
              // eslint-disable-next-line camelcase
              return Math.abs(d_hours) + '小时前'
            }
            // eslint-disable-next-line no-unreachable
            break
          case 1:
            // eslint-disable-next-line camelcase
            if (d_hours < 0) {
              // eslint-disable-next-line camelcase
              return (24 + d_hours) + '小时前'
            } else {
              // eslint-disable-next-line camelcase
              return d_day + '天前'
            }
            // eslint-disable-next-line no-unreachable
            break
        }
        // eslint-disable-next-line camelcase
      } else if (d_day > 1 && d_day < 10) {
        // eslint-disable-next-line camelcase
        return d_day + '天前'
      } else {
        return time
      }
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
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleCancel () {
      this.previewVisible = false
    },
    onChange (date, dateString) {
      this.startDate = dateString[0]
      this.endDate = dateString[1]
      let params = { startDate: this.startDate, endDate: this.endDate }
      this.getWorkStatusList(params)
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}

</style>
