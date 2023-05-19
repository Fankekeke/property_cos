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
                label="房屋地址"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.address"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="缴费类型"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-select v-model="queryParams.type" allowClear>
                  <a-select-option v-for="(item, index) in propertyList" :key="index" :value="item.id">{{ item.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="缴费状态"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-select v-model="queryParams.payStatus" allowClear>
                  <a-select-option value="1">未缴费</a-select-option>
                  <a-select-option value="2">已缴费</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <a-row v-if="advanced" :gutter="15">
            <a-col :md="6" :sm="24" >
              <a-form-item
                label="缴费年份"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-select v-model="queryParams.year" allowClear>
                  <a-select-option value="2022">2022</a-select-option>
                  <a-select-option value="2023">2023</a-select-option>
                  <a-select-option value="2024">2024</a-select-option>
                  <a-select-option value="2025">2025</a-select-option>
                  <a-select-option value="2026">2026</a-select-option>
                  <a-select-option value="2027">2027</a-select-option>
                  <a-select-option value="2028">2028</a-select-option>
                  <a-select-option value="2029">2029</a-select-option>
                  <a-select-option value="2030">2030</a-select-option>
                  <a-select-option value="2031">2031</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24" >
              <a-form-item
                label="缴费月份"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-select v-model="queryParams.month" allowClear>
                  <a-select-option value="1">1</a-select-option>
                  <a-select-option value="2">2</a-select-option>
                  <a-select-option value="3">3</a-select-option>
                  <a-select-option value="4">4</a-select-option>
                  <a-select-option value="5">5</a-select-option>
                  <a-select-option value="6">6</a-select-option>
                  <a-select-option value="7">7</a-select-option>
                  <a-select-option value="8">8</a-select-option>
                  <a-select-option value="9">9</a-select-option>
                  <a-select-option value="10">10</a-select-option>
                  <a-select-option value="11">11</a-select-option>
                  <a-select-option value="12">12</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            <a @click="toggleAdvanced" style="margin-left: 8px">
            {{advanced ? '收起' : '展开'}}
            <a-icon :type="advanced ? 'up' : 'down'" />
          </a>
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
        <template slot="propertyNameShow" slot-scope="text, record">
          <template>
            <a-tooltip v-if="record.type == 1 || record.type == 2">
              <template slot="title">
                本月消耗 - {{ record.dosage }} {{ record.propertyUnit }}
              </template>
              <a>{{ record.propertyName }}</a>
            </a-tooltip>
            <span v-else>{{ record.propertyName }}</span>
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
          <a-icon type="reconciliation" @click="view(record)" title="查 看"></a-icon>
<!--          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>-->
        </template>
      </a-table>
    </div>
    <payment-add
      v-if="paymentAdd.visiable"
      @close="handlePaymentAddClose"
      @success="handlePaymentAddSuccess"
      :paymentAddVisiable="paymentAdd.visiable">
    </payment-add>
<!--    <payment-add-new-->
<!--      v-if="paymentAdd.visiable"-->
<!--      @close="handlePaymentAddClose"-->
<!--      @success="handlePaymentAddSuccess"-->
<!--      :paymentAddVisiable="paymentAdd.visiable">-->
<!--    </payment-add-new>-->
    <payment-edit
      ref="paymentEdit"
      @close="handlePaymentEditClose"
      @success="handlePaymentEditSuccess"
      :paymentEditVisiable="paymentEdit.visiable">
    </payment-edit>
    <payment-view
      @close="handlePaymentViewClose"
      :paymentShow="paymentView.visiable"
      :paymentData="paymentView.data">
    </payment-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import PaymentAdd from './PaymentAdd'
import PaymentEdit from './PaymentEdit'
import PaymentView from './PaymentView'
import {mapState} from 'vuex'
import moment from 'moment'
import PaymentAddNew from './PaymentAddNew.vue'
moment.locale('zh-cn')

export default {
  name: 'payment',
  components: {PaymentAddNew, PaymentAdd, PaymentEdit, PaymentView, RangeDate},
  data () {
    return {
      advanced: false,
      paymentAdd: {
        visiable: false
      },
      paymentEdit: {
        visiable: false
      },
      paymentView: {
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
      propertyList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '所属房屋',
        dataIndex: 'address',
        scopedSlots: {customRender: 'addressShow'}
      }, {
        title: '业主姓名',
        dataIndex: 'name'
      }, {
        title: '联系方式',
        dataIndex: 'phone'
      }, {
        title: '缴费金额',
        dataIndex: 'price',
        customRender: (text, row, index) => {
          if (text !== null) {
            return '￥' + text
          } else {
            return '- -'
          }
        }
      }, {
        title: '缴费类型',
        dataIndex: 'propertyName',
        scopedSlots: {customRender: 'propertyNameShow'}
      }, {
        title: '计算方式',
        dataIndex: 'propertyType',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>统一交付</a-tag>
            case 2:
              return <a-tag>按量计算</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '费用时间',
        dataIndex: 'year',
        customRender: (text, row, index) => {
          return row.year + '年' + row.month + '月'
        }
      }, {
        title: '缴费状态',
        dataIndex: 'createDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return <a-tag color="green">已缴费</a-tag>
          } else {
            return <a-tag color="red">未缴费</a-tag>
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
    this.getPropertyList()
  },
  methods: {
    getPropertyList () {
      this.$get('/cos/property-item/list').then((r) => {
        this.propertyList = r.data.data
      })
    },
    view (row) {
      console.log(JSON.stringify(row))
      this.paymentView.data = row
      this.paymentView.visiable = true
    },
    handlePaymentViewClose () {
      this.paymentView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.paymentAdd.visiable = true
    },
    handlePaymentAddClose () {
      this.paymentAdd.visiable = false
    },
    handlePaymentAddSuccess () {
      this.paymentAdd.visiable = false
      this.$message.success('添加成功')
      this.search()
    },
    edit (record) {
      this.$refs.paymentEdit.setFormValues(record)
      this.paymentEdit.visiable = true
    },
    handlePaymentEditClose () {
      this.paymentEdit.visiable = false
    },
    handlePaymentEditSuccess () {
      this.paymentEdit.visiable = false
      this.$message.success('修改工作人员成功')
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
          that.$delete('/cos/payment-info/' + ids).then(() => {
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
      if (params.payStatus === undefined) {
        delete params.payStatus
      }
      if (params.year === undefined) {
        delete params.year
      }
      if (params.month === undefined) {
        delete params.month
      }
      this.$get('/cos/payment-manage/page', {
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
