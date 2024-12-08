<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-row :gutter="8" class="head-info">
      <a-card class="head-info-card">
        <a-col :span="12">
          <a-skeleton active v-if="loading" />
          <div class="head-info-count" v-if="!loading">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{user.roleName ? user.roleName : '暂无角色'}}</p>
            </div>
            <div class="head-info-time">上次登录时间：{{user.lastLoginTime ? user.lastLoginTime : '第一次访问系统'}}</div>
          </div>
        </a-col>
        <a-col :span="12">
          <a-skeleton active v-if="loading" />
          <div v-if="!loading">
            <a-row class="more-info" v-if="user.roleId == 75">
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
                <head-info title="房屋数量" :content="housesNum" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="用水消耗" :content="water" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="用电消耗" :content="electricity" :center="false" />
              </a-col>
              <a-col :span="4">
                <head-info title="未缴费用" :content="unpaid" :center="false" />
              </a-col>
            </a-row>
            <a-row class="more-info" v-else>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
                <head-info title="业主数量" :content="ownerNum" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="房屋数量" :content="housesNum" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="预收金额" :content="received" :center="false" />
              </a-col>
              <a-col :span="4">
                <head-info title="已收金额" :content="advance" :center="false" />
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-card>
    </a-row>
    <a-row :gutter="8" class="count-info">
      <a-col :span="16" class="visit-count-wrapper">
        <a-row :gutter="8">
          <a-col :span="24" v-show="user.roleId == 75">
            <a-card hoverable>
              <weather></weather>
            </a-card>
          </a-col>
          <a-col :span="24" v-show="user.roleId != 75">
            <a-card class="visit-count">
              <a-skeleton active v-if="loading" />
              <apexchart v-if="!loading" type="area" height="300" :options="chartOptions1" :series="series1"></apexchart>
            </a-card>
          </a-col>
          <a-col :span="24" v-if="user.roleId != 75">
            <a-row :gutter="8" class="count-info" style="margin-top: 8px;margin-left: 0px;margin-right: 0px">
              <a-col :span="24" class="visit-count-wrapper">
                <a-row :gutter="8">
                  <a-col :span="12">
                    <a-card class="visit-count">
                      <a-skeleton active v-if="loading" />
                      <apexchart v-if="!loading" type="donut" :options="chartOptions3" :series="series3" height="309"></apexchart>
                    </a-card>
                  </a-col>
                  <a-col :span="12">
                    <a-card class="visit-count">
                      <a-skeleton active v-if="loading" />
                      <apexchart v-show="!loading" ref="count" type=bar height=300 :options="chartOptions" :series="series" />
                    </a-card>
                  </a-col>
                </a-row>
              </a-col>
            </a-row>
          </a-col>
        </a-row>
      </a-col>
      <a-col :span="8" class="project-wrapper">
        <a-card hoverable :loading="loading" title="公告信息">
          <div>
            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
        <a-carousel effect="fade" style="margin-top: 35px">
          <div style="width: 100%;height: 300px"><img :src="'http://127.0.0.1:9527/imagesWeb/1.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
          <div style="width: 100%;height: 300px"><img :src="'http://127.0.0.1:9527/imagesWeb/2.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
          <div style="width: 100%;height: 300px"><img :src="'http://127.0.0.1:9527/imagesWeb/3.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
        </a-carousel>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import HeadInfo from '@/views/common/HeadInfo'
import Weather from '@/views/web/Weather'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'HomePage',
  components: {HeadInfo, Weather},
  data () {
    return {
      loading: false,
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 3
      },
      bulletinList: [],
      series: [],
      chartOptions: {
        chart: {
          toolbar: {
            show: false
          }
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '35%'
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
        fill: {
          opacity: 1

        }
      },
      series1: [{
        name: '预收金额',
        data: [31, 40, 28, 51, 42, 109, 100, 100, 100, 100, 100, 100]
      }],
      chartOptions1: {
        chart: {
          height: 350,
          type: 'area'
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        xaxis: {
          categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        }
      },
      series2: [{
        name: 'Net Profit',
        data: [44, 55, 57, 56, 61, 58, 63, 60, 66]
      }, {
        name: 'Revenue',
        data: [76, 85, 101, 98, 87, 105, 91, 114, 94]
      }],
      chartOptions2: {
        chart: {
          type: 'bar',
          height: 350
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
          categories: ['Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct']
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return '$ ' + val + ' thousands'
            }
          }
        }
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
      },
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: '',
      ownerNum: 0,
      propertyItemYear: [],
      housesNum: 0,
      electricity: 0,
      unpaid: 0,
      water: 0,
      received: 0,
      housesTypeRate: [],
      bulletinInfo: [],
      advance: []
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    openNotification (unpaid) {
      const key = `open${Date.now()}`
      this.$notification.open({
        message: '缴费通知',
        placement: 'bottomRight',
        description:
          `您共欠金额${unpaid}元，请尽快缴费`,
        btn: h => {
          return h(
            'a-button',
            {
              props: {
                type: 'primary',
                size: 'small'
              },
              on: {
                click: () => this.$router.push('/my/payment')
              }
            },
            '立即支付'
          )
        },
        key,
        onClose: close
      })
    },
    homeData () {
      this.loading = true
      this.$get('/cos/houses-info/home', { type: this.user.roleId === '75' ? this.user.userId : null }).then((r) => {
        if (this.user.roleId === '75') {
          this.housesNum = r.data.housesNum
          this.electricity = r.data.electricity
          this.unpaid = r.data.unpaid
          if (Number(this.unpaid) !== 0) {
            this.openNotification(this.unpaid)
          }
          this.water = r.data.water
          this.bulletinList = r.data.bulletinInfo
          let epidemicRateLabel = []
          let epidemicRateData = []
          r.data.epidemicRate.forEach(item => {
            epidemicRateLabel.push(item.days)
            epidemicRateData.push(item.temperature)
          })
          this.series1[0].data = epidemicRateData
          this.series1[0].name = '体温'
          this.chartOptions1.xaxis.categories = epidemicRateLabel
          setTimeout(() => {
            this.loading = false
          }, 500)
        } else {
          this.ownerNum = r.data.ownerNum
          let propertyItemYear = r.data.propertyItemYear[0]
          let dataArr = [propertyItemYear.month1, propertyItemYear.month2, propertyItemYear.month3, propertyItemYear.month4, propertyItemYear.month5, propertyItemYear.month6, propertyItemYear.month7, propertyItemYear.month8, propertyItemYear.month9, propertyItemYear.month10, propertyItemYear.month11, propertyItemYear.month12]
          this.series1[0].data = dataArr
          this.propertyItemYear = r.data.propertyItemYear
          this.housesNum = r.data.housesNum
          this.received = r.data.received
          let housesTypeRateLabel = []
          let housesTypeRateData = []
          r.data.housesTypeRate.forEach(item => {
            housesTypeRateLabel.push(item.nature)
            housesTypeRateData.push(item.num)
          })
          this.series3 = housesTypeRateData
          this.chartOptions3.labels = housesTypeRateLabel
          this.housesTypeRate = r.data.housesTypeRate
          this.bulletinList = r.data.bulletinInfo
          this.advance = r.data.advance
          setTimeout(() => {
            this.loading = false
          }, 500)
        }
      })
    },
    welcome () {
      const date = new Date()
      const hour = date.getHours()
      let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
      return `${time}，${this.user.username}`
    }
  },
  mounted () {
    this.homeData()
    this.welcomeMessage = this.welcome()
    this.$get(`index/${this.user.username}`).then((r) => {
      let data = r.data.data
      this.todayIp = data.todayIp
      this.todayVisitCount = data.todayVisitCount
      this.totalVisitCount = data.totalVisitCount
      let sevenVisitCount = []
      let dateArr = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenVisitCount) {
          if (o.days === time) {
            contain = true
            sevenVisitCount.push(o.count)
          }
        }
        if (!contain) {
          sevenVisitCount.push(0)
        }
        dateArr.push(time)
      }
      let sevenUserVistCount = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenUserVisitCount) {
          if (o.days === time) {
            contain = true
            sevenUserVistCount.push(o.count)
          }
        }
        if (!contain) {
          sevenUserVistCount.push(0)
        }
      }
      if (this.user.roleId !== '75') {
        this.$refs.count.updateSeries([
          {
            name: '您',
            data: sevenUserVistCount
          },
          {
            name: '总数',
            data: sevenVisitCount
          }
        ], true)
        this.$refs.count.updateOptions({
          xaxis: {
            categories: dateArr
          },
          title: {
            text: '近七日系统访问记录',
            align: 'left'
          }
        }, true, true)
      }
    }).catch((r) => {
      console.error(r)
      this.$message.error('获取首页信息失败')
    })
  }
}
</script>
<style lang="less">
  .home-page {
    .head-info {
      margin-bottom: .5rem;
      .head-info-card {
        padding: .5rem;
        border-color: #f1f1f1;
        .head-info-avatar {
          display: inline-block;
          float: left;
          margin-right: 1rem;
          img {
            width: 5rem;
            border-radius: 2px;
          }
        }
        .head-info-count {
          display: inline-block;
          float: left;
          .head-info-welcome {
            font-size: 1.05rem;
            margin-bottom: .1rem;
          }
          .head-info-desc {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
            p {
              margin-bottom: 0;
            }
          }
          .head-info-time {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
          }
        }
      }
    }
    .count-info {
      .visit-count-wrapper {
        padding-left: 0 !important;
        .visit-count {
          padding: .5rem;
          border-color: #f1f1f1;
          .ant-card-body {
            padding: .5rem 1rem !important;
          }
        }
      }
      .project-wrapper {
        padding-right: 0 !important;
        .project-card {
          border: none !important;
          .ant-card-head {
            border-left: 1px solid #f1f1f1 !important;
            border-top: 1px solid #f1f1f1 !important;
            border-right: 1px solid #f1f1f1 !important;
          }
          .ant-card-body {
            padding: 0 !important;
            table {
              width: 100%;
              td {
                width: 50%;
                border: 1px solid #f1f1f1;
                padding: .6rem;
                .project-avatar-wrapper {
                  display:inline-block;
                  float:left;
                  margin-right:.7rem;
                  .project-avatar {
                    color: #42b983;
                    background-color: #d6f8b8;
                  }
                }
              }
            }
          }
          .project-detail {
            display:inline-block;
            float:left;
            text-align:left;
            width: 78%;
            .project-name {
              font-size:.9rem;
              margin-top:-2px;
              font-weight:600;
            }
            .project-desc {
              color:rgba(0, 0, 0, 0.45);
              p {
                margin-bottom:0;
                font-size:.6rem;
                white-space:normal;
              }
            }
          }
        }
      }
    }
  }
</style>
