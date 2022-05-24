/* 收藏书架 */
<template>
  <div class="content">
    <el-button type="danger" v-if="setting" class="batch-del" plain :disabled="selectionData.length <= 0" @click="handleDelete">批量删除</el-button>
    <h1 class="title">
      <slot name="title"></slot>
      <hr />
    </h1>

    <el-table :data="bookList" style="width: 100%" @row-click="goBook" @row-dblclick="goBook" @selection-change="handleSelectionChange">
      <el-table-column v-if="setting" type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60" label="序号" :index="(index)=>index+1">
      </el-table-column>
      <el-table-column prop="name" label="书名" width="180" />
      <el-table-column prop="name" label="更新时间" width="180">
        <template slot-scope="scope">
          <span>{{scope.row.updateTime.slice(0,11)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="类型" />
      <el-table-column v-if="setting" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" icon="el-icon-delete" circle @click.stop="handleDelete(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { mixin } from '../mixins'
import { delCollect } from '../api/index'
export default {
  name: 'album-content',
  mixins: [mixin],
  props: ['bookList', 'setting'],
  props: {
    bookList: {
      type: Array,
      default: () => []
    },
    setting: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      selectionData: []
    }
  },
  methods: {
    goBook(row) {
      if (!this.setting) {
        if (!this.$store.state.user.vip && row.isCharge == 1) {
          this.notify('此vip书籍,禁止阅读,请先成为VIP', 'error')
          return
        }
      }
      let id = row.id
      let writerId = row.writerId
      this.$router.push({ path: `/Book`, query: { id, writerId } })
    },
    handleSelectionChange(selection) {
      this.selectionData = selection
    },
    handleDelete(row) {
      let ids = this.selectionData.length > 0 ? (ids = this.selectionData.map((item) => item.id).join(',')) : row.id
      this.$confirm('此操作将删除该书籍信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delCollect({
            bookIds: ids,
            userId: this.$store.state.user.userId
          })
            .then((res) => {
              if (res) {
                this.$parent.getCollection(this.$store.state.user.userId)
                this.notify('删除成功', 'success')
              } else {
                this.notify('删除失败', 'error')
              }
            })
            .catch((err) => {})
        })
        .catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
.batch-del {
  margin-bottom: 10px;
}
</style>