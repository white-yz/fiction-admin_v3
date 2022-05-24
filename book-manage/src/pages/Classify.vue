<template>
  <div class="table">
    <div class="container" style="margin-bottom:20px;">
      <div class="handle-box">
        <el-input v-model="select_word" size="small" :placeholder="$t('book.enterName')" class="handle-input" style="float: right;"></el-input>
        <el-button-group>
          <el-button type="primary" class="iconfont icon-piliangshanchu" size="small" @click="delAll">批量删除</el-button>&nbsp;
          <el-button type="primary" icon="el-icon-plus" size="small" @click="dialogVisible = true">添加分类</el-button>
        </el-button-group>
      </div>
    </div>
    <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="index"></el-table-column>
      <el-table-column prop="name" :label="$t('book.Name')" align="center"></el-table-column>
      <el-table-column :label="$t('common.operation')" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加窗口  -->
    <el-dialog :title="formData.id && '修改 || 新增'" :visible.sync="dialogVisible" width="550px" center>
      <el-form :model="formData" ref="registerForm" label-width="95px">
        <el-form-item prop="name" :label="$t('book.Name')" size="mini">
          <el-input v-model="formData.name" :placeholder="$t('book.Name')"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="dialogVisible = false">{{$t('common.cancel')}}</el-button>
        <el-button size="mini" @click="addBook" type="primary">{{$t('common.confirm')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getClassify, updateClassify, delClassify } from '../api/index'
import { mixin } from '../mixins/index'
export default {
  mixins: [mixin],
  data() {
    return {
      formData: {
        name: null
      },
      dialogVisible: false,
      data: [],
      selectionData: []
    }
  },
  methods: {
    //查询所有书籍
    getData() {
      this.tempData = []
      this.tableData = []
      bookOfWriterId(this.writerId).then((res) => {
        this.tempData = res
        this.tableData = res
      })
    },
    //添加书籍
    addBook() {
      let params = new URLSearchParams()
      params.append('writerId', this.writerId)
      params.append('name', this.registerForm.name)
      params.append('style', this.registerForm.style)
      params.append('pic', '/img/bookPic/tubiao.jpg')
      params.append('introduction', this.registerForm.introduction)
      setBook(params)
        .then((res) => {
          if (res.code == 1) {
            this.getData()
            this.notify('添加成功', 'success')
          } else {
            this.notify('添加失败', 'error')
          }
        })
        .catch((err) => {
          console.log(err)
        })
      this.centerDialogVisible = false
    },
    handleSelectionChange(selection) {
        this.selectionData = selection
    },
    //弹出编辑页面
    handleEdit(row) {
      this.editVisible = true
      this.form = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        style: row.style
      }
    },
    //保存编辑页面修改的数据
    editSave() {
      let params = new URLSearchParams()
      params.append('id', this.form.id)
      params.append('name', this.form.name)
      params.append('style', this.form.style)
      params.append('introduction', this.form.introduction)
      updateBook(params)
        .then((res) => {
          if (res.code == 1) {
            this.getData()
            this.notify('修改成功', 'success')
          } else {
            this.notify('修改失败', 'error')
          }
        })
        .catch((err) => {
          console.log(err)
        })
      this.editVisible = false
    },
    //删除一本书籍
    deleteRow() {
      delBook(this.idx)
        .then((res) => {
          if (res) {
            this.getData()
            this.notify('删除成功', 'success')
          } else {
            this.notify('删除失败', 'error')
          }
        })
        .catch((err) => {})
      this.delVisible = false
    }
  }
}
</script>

<style scoped>
.book-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.pagination {
  display: flex;
  justify-content: center;
}
</style>