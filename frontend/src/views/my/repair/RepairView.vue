<template>
  <a-modal v-model="show" title="维修上报" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="repairData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">业主信息</span></a-col>
        <a-col :span="8"><b>业主姓名：</b>
          {{ repairData.name }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ repairData.phone }}
        </a-col>
        <a-col :span="8"><b>身份证号：</b>
          <a-tooltip>
            <template slot="title">
              {{ repairData.idNumber }}
            </template>
            {{ repairData.idNumber.slice(0, 10) }} ...
          </a-tooltip>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'RepairView',
  props: {
    repairShow: {
      type: Boolean,
      default: false
    },
    repairData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.repairShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false
    }
  },
  methods: {
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
