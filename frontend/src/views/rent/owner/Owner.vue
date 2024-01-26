<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="出租标题"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.title"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="房屋地址"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.address"/>
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
            <a-badge status="processing" v-if="record.rackUp === 1"/>
            <a-badge status="error" v-if="record.rackUp === 0"/>
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
                {{ record.houseAddress }}
              </template>
              {{ record.houseAddress.slice(0, 10) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="rentalRequestShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.rentalRequest }}
              </template>
              {{ record.rentalRequest.slice(0, 15) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon v-if="record.delFlag === 1" type="caret-down" @click="audit(record.id, 2)" title="下 架" style="margin-right: 10px"></a-icon>
          <a-icon v-if="record.delFlag === 2" type="caret-up" @click="audit(record.id, 1)" title="上 架" style="margin-right: 10px"></a-icon>
          <a-icon type="bulb" @click="view(record)" title="详 情" style="margin-right: 10px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <rent-add
      v-if="rentAdd.visiable"
      @close="handlerentAddClose"
      @success="handlerentAddSuccess"
      :rentAddVisiable="rentAdd.visiable">
    </rent-add>
    <rent-edit
      ref="rentEdit"
      @close="handlerentEditClose"
      @success="handlerentEditSuccess"
      :rentEditVisiable="rentEdit.visiable">
    </rent-edit>
    <rent-view :rentShow="rentView.visiable" :rentData="rentView.data" @close="rentView.visiable = false"></rent-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import rentAdd from './RentAdd'
import rentEdit from './RentEdit'
import {mapState} from 'vuex'
import moment from 'moment'
import RentView from './RentView'
moment.locale('zh-cn')
export default {
  name: 'rent',
  components: {RentView, rentAdd, rentEdit, RangeDate},
  data () {
    return {
      advanced: false,
      rentAdd: {
        visiable: false
      },
      rentEdit: {
        visiable: false
      },
      rentView: {
        visiable: false,
        data: null
      },
      rentPushpin: {
        visiable: false,
        chargeId: ''
      },
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
      userDrawer: false,
      staff: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '出租标题',
        dataIndex: 'title',
        scopedSlots: { customRender: 'titleShow' }
      }, {
        title: '房屋地址',
        dataIndex: 'address'
      }, {
        title: '联系方式',
        dataIndex: 'phone',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '联系人',
        dataIndex: 'contact',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '出租类型',
        dataIndex: 'type',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>出租</a-tag>
            case 2:
              return <a-tag>售卖</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '租金',
        dataIndex: 'rentPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '元'
          } else {
            return '- -'
          }
        }
      }, {
        title: '出租状态',
        dataIndex: 'delFlag',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag color="green">上架</a-tag>
            case 2:
              return <a-tag color="red">下架</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '房屋照片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '门牌号',
        dataIndex: 'number',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '楼层',
        dataIndex: 'floor',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '层'
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
    handlerUserClosed () {
      this.userDrawer = false
      this.search()
    },
    rentPushpinClose () {
      this.rentPushpin.visiable = false
      this.search()
    },
    rentPushpinSuccess () {
      this.rentPushpin.visiable = false
      this.$message.success('成功添加合同')
      this.search()
    },
    checkStaff (rent) {
      this.userDrawer = true
      this.rentView.data = rent
    },
    pushpin (rent) {
      this.rentPushpin.visiable = true
      this.rentPushpin.chargeId = rent.chargeId
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.rentAdd.visiable = true
    },
    handlerentAddClose () {
      this.rentAdd.visiable = false
    },
    handlerentAddSuccess () {
      this.rentAdd.visiable = false
      this.$message.success('新增租房信息成功')
      this.search()
    },
    audit (rentId, status) {
      this.$get(`/cos/rent-info/setStatus`, {
        rentId,
        status
      }).then((r) => {
        this.$message.success('更新状态成功')
        this.search()
      })
    },
    view (record) {
      this.rentView.visiable = true
      this.rentView.data = record
    },
    edit (record) {
      this.$refs.rentEdit.setFormValues(record)
      this.rentEdit.visiable = true
    },
    handlerentEditClose () {
      this.rentEdit.visiable = false
    },
    handlerentEditSuccess () {
      this.rentEdit.visiable = false
      this.$message.success('修改租房信息成功')
      this.search()
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
          that.$delete('/cos/rent-info/' + ids).then(() => {
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
      params.ownerId = this.currentUser.userId
      this.$get('/cos/rent-info/page', {
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
