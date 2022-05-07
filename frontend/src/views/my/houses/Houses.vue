<template>
  <div style="background:#ECECEC; padding:30px;margin-top: 30px;width: 100%">
    <a-row :gutter="55">
      <a-col v-if="housesList.length === 0" :span="24">
        <div style="color: rgba(0,0,0,.85);font-size: 24px;line-height: 1.8;text-align: center;height: 250px;">
          <p style="margin-top: 100px">暂无房屋</p>
        </div>
      </a-col>
      <a-col :span="8" v-for="(item, index) in housesList" :key="index">
        <a-card hoverable :bordered="false">
          <img
            style="height: 180px;object-fit: cover"
            slot="cover"
            alt="example"
            :src="'http://127.0.0.1:9527/imagesWeb/' + item.images.split(',')[0]"
          />
          <template slot="actions" class="ant-card-actions">
            <a-icon v-if="item.repairStatus === null" key="setting" type="setting" @click="add(item)"/>
            <a-icon v-if="item.repairStatus !== null" key="edit" type="edit" @click="edit(item)"/>
            <a-icon key="ellipsis" type="ellipsis" @click="view(item)"/>
          </template>
          <a-card-meta :title="item.number + '室'" :description="item.address"></a-card-meta>
          <div style="margin-top: 15px">
            <a-tag v-if="item.repairStatus == 0" color="#f50">未派单</a-tag>
            <a-tag v-if="item.repairStatus == 1" color="#108ee9">已派单</a-tag>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <houses-view
      @close="handleHousesViewClose"
      :housesShow="housesView.visiable"
      :housesData="housesView.data">
    </houses-view>
    <repair-add
      v-if="repairAdd.visiable"
      @close="handleRepairAddClose"
      @success="handleRepairAddSuccess"
      :housesData="repairAdd.data"
      :repairAddVisiable="repairAdd.visiable">
    </repair-add>
    <repair-edit
      ref="repairEdit"
      @close="handleRepairEditClose"
      @success="handleRepairEditSuccess"
      :housesData="repairEdit.data"
      :repairEditVisiable="repairEdit.visiable">
    </repair-edit>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import HousesView from '../../manage/houses/HousesView'
import RepairAdd from '../../manage/repair/RepairAdd'
import RepairEdit from './RepairEdit'
export default {
  name: 'Houses',
  components: {HousesView, RepairAdd, RepairEdit},
  data () {
    return {
      loading: false,
      housesList: [],
      repairAdd: {
        visiable: false,
        data: null
      },
      housesView: {
        visiable: false,
        data: null
      },
      repairEdit: {
        visiable: false,
        data: null
      }
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
    this.housesInfoByOwnerId()
  },
  methods: {
    edit (record) {
      this.repairEdit.data = record
      this.repairEdit.visiable = true
    },
    handleRepairEditClose () {
      this.repairEdit.visiable = false
    },
    handleRepairEditSuccess () {
      this.repairEdit.visiable = false
      this.$message.success('修改成功')
      this.housesInfoByOwnerId()
    },
    add (row) {
      this.repairAdd.data = row
      this.repairAdd.visiable = true
    },
    handleRepairAddClose () {
      this.repairAdd.visiable = false
    },
    handleRepairAddSuccess () {
      this.repairAdd.visiable = false
      this.$message.success('新增成功')
      this.housesInfoByOwnerId()
    },
    view (row) {
      this.housesView.data = row
      this.housesView.visiable = true
    },
    handleHousesViewClose () {
      this.housesView.visiable = false
    },
    housesInfoByOwnerId () {
      this.$get('/cos/houses-info/housesListByOwnerId', { userId: this.currentUser.userId }).then((r) => {
        this.housesList = r.data.data
      })
    }
  }
}
</script>

<style scoped>

</style>
