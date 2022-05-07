<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="楼宇名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}">
                <a-input v-model="queryParams.name"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="楼宇地址"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}">
                <a-input v-model="queryParams.address"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="所属街道"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}">
                <a-input v-model="queryParams.street"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="房屋类型"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}">
                <a-select v-model="queryParams.type" allowClear>
                  <a-select-option value="1">平房</a-select-option>
                  <a-select-option value="2">多层楼</a-select-option>
                  <a-select-option value="3">高层楼</a-select-option>
                  <a-select-option value="4">简易楼</a-select-option>
                </a-select>
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
        <template slot="addressShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.address }}
              </template>
              {{ record.address.slice(0, 10) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="reconciliation" @click="view(record)" title="查 看" style="margin-right: 15px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <building-add
      v-if="buildingAdd.visiable"
      @close="handleBuildingAddClose"
      @success="handleBuildingAddSuccess"
      :buildingAddVisiable="buildingAdd.visiable">
    </building-add>
    <building-edit
      ref="buildingEdit"
      @close="handleBuildingEditClose"
      @success="handleBuildingEditSuccess"
      :buildingEditVisiable="buildingEdit.visiable">
    </building-edit>
    <building-view
      @close="handleBuildingViewClose"
      :buildingShow="buildingView.visiable"
      :buildingData="buildingView.data">
    </building-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import BuildingAdd from './BuildingAdd'
import BuildingEdit from './BuildingEdit'
import BuildingView from './BuildingView'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'Building',
  components: {BuildingAdd, BuildingEdit, BuildingView, RangeDate},
  data () {
    return {
      advanced: false,
      buildingAdd: {
        visiable: false
      },
      buildingEdit: {
        visiable: false
      },
      buildingView: {
        visiable: false,
        data: null
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
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '楼宇名称',
        dataIndex: 'name'
      }, {
        title: '楼宇地址',
        dataIndex: 'address',
        scopedSlots: { customRender: 'addressShow' }
      }, {
        title: '所属街道',
        dataIndex: 'street'
      }, {
        title: '所属社区',
        dataIndex: 'community'
      }, {
        title: '总使用面积',
        dataIndex: 'usageArea',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '㎡'
          } else {
            return '- -'
          }
        }
      }, {
        title: '总建筑面积',
        dataIndex: 'surfaceArea',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '㎡'
          } else {
            return '- -'
          }
        }
      }, {
        title: '类型',
        dataIndex: 'type',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>平房</a-tag>
            case 2:
              return <a-tag>多层楼</a-tag>
            case 3:
              return <a-tag>高层楼</a-tag>
            case 4:
              return <a-tag>简易房</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '间数',
        dataIndex: 'rooms',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '间'
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
    view (row) {
      this.buildingView.data = row
      this.buildingView.visiable = true
    },
    handleBuildingViewClose () {
      this.buildingView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.buildingAdd.visiable = true
    },
    handleBuildingAddClose () {
      this.buildingAdd.visiable = false
    },
    handleBuildingAddSuccess () {
      this.buildingAdd.visiable = false
      this.$message.success('新增楼宇成功')
      this.search()
    },
    edit (record) {
      this.$refs.buildingEdit.setFormValues(record)
      this.buildingEdit.visiable = true
    },
    handleBuildingEditClose () {
      this.buildingEdit.visiable = false
    },
    handleBuildingEditSuccess () {
      this.buildingEdit.visiable = false
      this.$message.success('修改楼宇成功')
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
          that.$delete('/cos/building-info/' + ids).then(() => {
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
      this.$get('/cos/building-info/page', {
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
