<template>
  <div>
    <a-row style="margin-top: 15px" v-if="user.roleId == 74 ">
      <a-col :span="24">
        <div style="background: #ECECEC; padding: 30px;">
          <a-row :gutter="16">
            <a-col :span="user.roleId == 75 ? 4 : 6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月缴费单量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.monthOrderNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="user.roleId == 75 ? 4 : 6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月应缴费用</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.monthOrderTotal }}
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="user.roleId == 75 ? 4 : 6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月已缴费用</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.yearOrderNum }}
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="user.roleId == 75 ? 4 : 6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月未缴费用</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.yearOrderTotal }}
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px" v-if="user.roleId == 74" :gutter="15">
      <a-col :span="16">
        <a-card hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart  v-if="!loading" type="line" height="300" :options="chartOptions" :series="series"></apexchart>
        </a-card>
        <a-card hoverable :bordered="false" style="width: 100%;margin-top: 10px">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="300" :options="chartOptions1" :series="series1"></apexchart>
        </a-card>
      </a-col>
      <a-col :span="8" style="height: 300px; margin: 0 auto">
        <a-card class="visit-count">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="donut" :options="chartOptions3" :series="series3" height="309"></apexchart>
        </a-card>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px">
<!--      <a-col :span="9">-->
<!--        <a-card hoverable :bordered="false" style="width: 100%">-->
<!--          <a-skeleton active v-if="loading" />-->
<!--          <apexchart v-if="!loading" type="donut" height="270" :options="chartOptions2" :series="series2"></apexchart>-->
<!--        </a-card>-->
<!--      </a-col>-->
<!--      <a-col :span="24" v-if="user.roleId != 76">-->
<!--        <a-card hoverable :loading="loading" :bordered="false" title="公告信息" style="margin-top: 15px">-->
<!--          <div style="padding: 0 22px">-->
<!--            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">-->
<!--              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">-->
<!--                <template slot="actions">-->
<!--              <span key="message">-->
<!--                <a-icon type="message" style="margin-right: 8px" />-->
<!--                {{ item.date }}-->
<!--              </span>-->
<!--                </template>-->
<!--                <a-list-item-meta :description="item.content" style="font-size: 14px">-->
<!--                  <a slot="title">{{ item.title }}</a>-->
<!--                </a-list-item-meta>-->
<!--              </a-list-item>-->
<!--            </a-list>-->
<!--          </div>-->
<!--        </a-card>-->
<!--      </a-col>-->
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Home',
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: [],
      titleData: {
        monthOrderNum: 0,
        monthOrderTotal: 0,
        yearOrderNum: 0,
        monthExpensesTotal: 0,
        monthProfitTotal: 0,
        yearOrderTotal: 0
      },
      loading: false,
      series: [{
        name: '报修单',
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天报修统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series1: [],
      chartOptions1: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天缴费统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 元'
            }
          }
        }
      },
      series2: [],
      chartOptions2: {
        chart: {
          type: 'donut',
          height: 300
        },
        labels: [],
        title: {
          text: '类型统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 380,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series3: [44, 55, 41, 17, 15],
      chartOptions3: {
        labels: [],
        chart: {
          type: 'donut'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      }
    }
  },
  mounted () {
    this.loading = true
    this.selectHomeData()
    setTimeout(() => {
      this.loading = false
    }, 200)
  },
  methods: {
    selectHomeData () {
      if (this.user.roleId === '74') {
        this.$get('/cos/payment-manage/admin/homeData').then((r) => {
          this.titleData.monthOrderNum = r.data.monthPaymentNum
          this.titleData.monthOrderTotal = r.data.monthPayable
          this.titleData.yearOrderNum = r.data.monthPaid
          this.titleData.yearOrderTotal = r.data.monthUnpaid
          let values = []
          if (r.data.orderNumDayList !== null && r.data.orderNumDayList.length !== 0) {
            if (this.chartOptions1.xaxis.categories.length === 0) {
              this.chartOptions1.xaxis.categories = Array.from(r.data.orderNumDayList, ({days}) => days)
            }
            let itemData = { name: '缴费金额', data: Array.from(r.data.orderNumDayList, ({value}) => value) }
            values.push(itemData)
            this.series1 = values
          }
          this.series[0].data = Array.from(r.data.priceDayList, ({value}) => value)
          this.chartOptions.xaxis.categories = Array.from(r.data.priceDayList, ({days}) => days)
          if (r.data.orderType) {
            let housesTypeRateLabel = []
            let housesTypeRateData = []
            r.data.orderType.forEach(item => {
              housesTypeRateLabel.push(item.name)
              housesTypeRateData.push(item.price)
            })
            this.series3 = housesTypeRateData
            this.chartOptions3.labels = housesTypeRateLabel
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
