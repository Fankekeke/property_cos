<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="业主姓名"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.name"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="联系电话"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.phone"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="身份证号"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.idNumber"/>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">新增</a-button>
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-badge status="processing"/>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="contentShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.content }}
              </template>
              {{ record.content.slice(0, 30) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="picture" v-if="record.images === null" @click="face(record)" title="照 片" style="margin-right: 15px"></a-icon>
          <a-icon type="reconciliation" @click="view(record)" title="查 看" style="margin-right: 15px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <a-modal v-model="faceView.visiable" title="上传人脸照片">
      <template slot="footer">
        <a-button key="back" @click="faceView.visiable = false">
          取消
        </a-button>
      </template>
      <div style="height: 120px">
        <a-upload
          v-if="faceView.visiable"
          name="avatar"
          action="http://127.0.0.1:9527/cos/face/registered/"
          list-type="picture-card"
          :data="{'name': faceView.data.name, 'ownerId': faceView.data.id}"
          :file-list="fileList"
          @preview="handlePreview"
          @change="picHandleChange"
        >
          <div v-if="fileList.length < 1">
            <a-icon type="plus" />
            <div class="ant-upload-text">
              Upload
            </div>
          </div>
        </a-upload>
      </div>
      <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
        <img alt="example" style="width: 100%" :src="previewImage" />
      </a-modal>
    </a-modal>
    <owner-add
      v-if="ownerAdd.visiable"
      @close="handleOwnerAddClose"
      @success="handleOwnerAddSuccess"
      :ownerAddVisiable="ownerAdd.visiable">
    </owner-add>
    <owner-edit
      ref="ownerEdit"
      @close="handleOwnerEditClose"
      @success="handleOwnerEditSuccess"
      :ownerEditVisiable="ownerEdit.visiable">
    </owner-edit>
    <owner-view
      @close="handleOwnerViewClose"
      :ownerShow="ownerView.visiable"
      :ownerData="ownerView.data">
    </owner-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import {mapState} from 'vuex'
import moment from 'moment'
import OwnerAdd from './OwnerAdd'
import OwnerEdit from './OwnerEdit'
import OwnerView from './OwnerView'
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
  name: 'Owner',
  components: {OwnerView, OwnerAdd, OwnerEdit, RangeDate},
  data () {
    return {
      ownerAdd: {
        visiable: false
      },
      ownerEdit: {
        visiable: false
      },
      ownerView: {
        visiable: false,
        data: null
      },
      faceView: {
        visiable: false,
        data: null
      },
      advanced: false,
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: [],
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '业主姓名',
        dataIndex: 'name'
      }, {
        title: '联系电话',
        dataIndex: 'phone'
      }, {
        title: '邮箱地址',
        dataIndex: 'email'
      }, {
        title: '身份证号码',
        dataIndex: 'idNumber'
      }, {
        title: '系统账户',
        dataIndex: 'account',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '房屋数量',
        dataIndex: 'housesNum',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '间'
          } else {
            return '- -'
          }
        }
      }, {
        title: '创建时间',
        dataIndex: 'createDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
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
    picHandleChange (info) {
      console.log(info.file.response)
      if (info.file.response !== undefined && info.file.response.msg !== undefined) {
        if (info.file.response.msg === 'success') {
          this.$message.success('添加照片成功')
          this.faceView.visiable = false
          this.fetch()
        } else {
          this.$message.error(info.file.response.msg)
        }
      }
      this.fileList = info.fileList
    },
    face (row) {
      this.fileList = []
      this.faceView.visiable = true
      this.faceView.data = row
    },
    add () {
      this.ownerAdd.visiable = true
    },
    handleOwnerAddClose () {
      this.ownerAdd.visiable = false
    },
    handleOwnerAddSuccess () {
      this.ownerAdd.visiable = false
      this.$message.success('新增业主成功')
      this.search()
    },
    edit (record) {
      this.$refs.ownerEdit.setFormValues(record)
      this.ownerEdit.visiable = true
    },
    handleOwnerEditClose () {
      this.ownerEdit.visiable = false
    },
    handleOwnerEditSuccess () {
      this.ownerEdit.visiable = false
      this.$message.success('修改业主成功')
      this.search()
    },
    view (row) {
      this.ownerView.data = row
      this.ownerView.visiable = true
    },
    handleOwnerViewClose () {
      this.ownerView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/owner-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.type === undefined) {
        delete params.type
      }
      this.$get('/cos/owner-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
