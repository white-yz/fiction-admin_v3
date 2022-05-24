<template>
  <div class="content">
      <el-button type="primary" icon="el-icon-plus" size="small">新增</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <el-button type="danger" class="batch-del" plain :disabled="selectionData.length <= 0" @click="handleDelete">批量删除</el-button>
    
    <h1 class="title">
      <slot name="title"></slot>
      <hr />
    </h1>

    <el-table :data="bookList" style="width: 100%" @row-click="goBook" @row-dblclick="goBook" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60" label="序号" :index="(index)=>index+1">
      </el-table-column>
      <el-table-column prop="name" label="书名" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="introduction" label="简介" width="180" />
      <el-table-column prop="name" label="更新时间" width="180">
        <template slot-scope="scope">
          <span>{{scope.row.updateTime.slice(0,11)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" />
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" icon="el-icon-delete" circle @click.stop="handleDelete(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

       <!-- 添加窗口  -->
        <el-dialog :title="'新增书籍'" :visible.sync="bookDialogVisible" width="550px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="95px">
                <el-form-item prop="username" label="用户名" size="mini">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码" size="mini">
                    <el-input v-model="registerForm.password" :placeholder="$t('writer.writerName')"></el-input>
                </el-form-item>
                <el-form-item prop="name" :label="$t('writer.writerName')" size="mini">
                    <el-input v-model="registerForm.name" :placeholder="$t('writer.writerName')"></el-input>
                </el-form-item>
                <el-form-item :label="$t('common.sex')" size="mini">
                    <el-radio-group v-model="registerForm.sex">
                        <el-radio :label="0">{{$t('common.woman')}}</el-radio>
                        <el-radio :label="1">{{$t('common.man')}}</el-radio>
                        <el-radio :label="2">{{$t('common.combination')}}</el-radio>
                        <el-radio :label="3">{{$t('common.unknown')}}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birth" :label="$t('common.birth')" size="mini">
                    <el-date-picker type="date" :placeholder="$t('common.selectDate')" v-model="registerForm.birth" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" :label="$t('common.location')" size="mini">
                    <el-select v-model="registerForm.location" :placeholder="$t('common.location')" style="width: 100%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.introduction')" size="mini">
                    <el-input v-model="registerForm.introduction" :placeholder="$t('common.introduction')" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="addWriter" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
  </div>
</template>
<script>
import { bookOfWriterId, batchDelBook } from '../api/index'
import { mixin } from '../mixins'
export default {
  name: 'Works',
  mixins: [mixin],
  data() {
    return {
      selectionData: [],
      bookList: [],
      bookDialogVisible: []
    }
  },
  created() {
    this.getBook()
  },
  methods: {
    getBook(row) {
      bookOfWriterId(this.userId).then((res) => {
        if (res) {
          this.bookList = res
        } else {
          this.bookList = []
        }
      })
    },
    goBook(row) {
      let id = row.id
      let writerId = row.writerId
      this.$router.push({ path: `/write-book`, query: { id, writerId } })
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
          batchDelBook(ids)
            .then((res) => {
              if (res) {
                this.getBook()
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