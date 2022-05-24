/* 作者书架页 */
<template>
  <div class="content">
    <el-button type="primary" style="background-color: #4747A5; border-color: #4747A5;" icon="el-icon-plus" @click="addDia">新增</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <el-button type="danger" style="background-color: #D55969; color: #fff" class="batch-del" plain :disabled="selectionData.length <= 0" @click="handleDelete">批量删除</el-button>

    <h1 class="title">
      <slot name="title"></slot>
      <hr />
    </h1>

    <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="42"></el-table-column>
      <el-table-column label="书籍图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="book-img">
            <img :src="getUrl(scope.row.pic)" alt="" style="height: 100px;">
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
            <el-button size="mini" type="primary" plain>更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="书籍名" width="150" align="center">
        <template slot-scope="scope">
          <p class="handname" @click="goBook(scope.row)">{{scope.row.name}}</p>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" width="120" align="center"></el-table-column>
      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height:100px;overflow:auto;white-space:pre-wrap;">{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column prop="isCharge" label="是否收费" width="120" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.isCharge == 1">是</el-tag>
          <el-tag type="danger" v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="230" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>

          <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagination">
      <el-pagination background layout="total, sizes, prev, pager, next, jumper" :current-page="currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" :total="tableData.length"
        @current-change="handleCurrentChange" @size-change="handleSizeChange">
      </el-pagination>
    </div>
    <!-- 添加窗口  -->
    <el-dialog :title="registerForm.id && '编辑' || '新增'" v-if="centerDialogVisible" :visible.sync="centerDialogVisible" width="550px" center>
      <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="95px">
        <el-form-item prop="name" label="名称" size="mini">
          <el-input v-model="registerForm.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <!-- <el-input v-model="registerForm.style" placeholder="风格"></el-input> -->
            <el-select v-model="registerForm.style" placeholder="风格" style="width: 100%;">
                <el-option v-for=" item in bookStyle" :key="item.name" :label="item.name" :value="item.name"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="isCharge" label="是否收费" size="mini">
          <el-radio-group v-model="registerForm.isCharge">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="addBook" type="primary">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { setBook, bookOfWriterId, updateBook, batchDelBook } from '../api/index'
import { bookStyleWrite } from '../assets/data/bookList'
import { mixin } from '../mixins/index'
export default {
  mixins: [mixin],
  data() {
    return {
      writerId: '', //作家id
      writerName: '', //作家名
      centerDialogVisible: false, //添加弹窗是否显示
      editVisible: false, //编辑弹窗是否显示
      delVisible: false, //删除弹窗是否显示
      registerForm: {
        //添加框
        name: '',
        writerId: '',
        writerName: '',
        introduction: '',
        style: '',
        isCharge: 0
      },
      tableData: [],
      tempData: [],
      bookStyle: [], //风格
      select_word: '',
      pageSize: 5, //分页每页大小
      currentPage: 1, //当前页
      idx: -1, //当前选择项
      selectionData: [], //哪些项已经打勾
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        style: [{ required: true, message: '请输入内容', trigger: 'blur' }],
        introduction: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      }
    }
  },
  mounted(){
      this.bookStyle = bookStyleWrite;
  },
  computed: {
    //计算当前搜索结果表里的数据
    data() {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    select_word: function () {
      if (this.select_word == '') {
        this.tableData = this.tempData
      } else {
        this.tableData = []
        for (let item of this.tempData) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    //查询所有书籍
    getData() {
      this.tempData = []
      this.tableData = []
      bookOfWriterId(this.$store.state.user.userId).then((res) => {
        this.tempData = res
        this.tableData = res
      })
    },
    addDia() {
     // this.$refs.registerForm.resetFields()
      this.registerForm = {
        //添加框
        name: '',
        writerName: '',
        introduction: '',
        style: '',
        isCharge: 0,
        writerId: this.$store.state.user.userId
      }
      this.centerDialogVisible = true
    },
    //添加书籍
    addBook() {
      this.$refs['registerForm'].validate((valid) => {
        if (valid) {
          if (!this.registerForm.id) {
            let params = new URLSearchParams()
            params.append('writerId', this.$store.state.user.userId)
            params.append('name', this.registerForm.name)
            params.append('style', this.registerForm.style)
            params.append('pic', '/img/bookPic/tubiao.jpg')
            params.append('introduction', this.registerForm.introduction)
            params.append('isCharge', this.registerForm.isCharge)
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
          } else {
            this.editSave()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    //更新图片
    uploadUrl(id) {
      return `${this.$store.state.HOST}/book/updateBookPic?id=${id}`
    },
    handleSelectionChange(val) {
      this.selectionData = val
    },
    //获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    //弹出编辑页面
    handleEdit(row) {
      this.centerDialogVisible = true
      this.registerForm = { ...row }
    },
    //保存编辑页面修改的数据
    editSave() {
      let params = new URLSearchParams()
      params.append('id', this.registerForm.id)
      params.append('name', this.registerForm.name)
      params.append('style', this.registerForm.style)
      params.append('introduction', this.registerForm.introduction)
      params.append('isCharge', this.registerForm.isCharge)
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
      this.centerDialogVisible = false
    },
    //删除一本书籍
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
                this.getData()
                this.notify('删除成功', 'success')
              } else {
                this.notify('删除失败', 'error')
              }
            })
            .catch((err) => {})
        })
        .catch(() => {})
    },
    goBook(row) {
      let id = row.id
      let writerId = row.writerId
      this.$router.push({ path: `/write-book`, query: { id, writerId } })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
.batch-del {
  margin-bottom: 10px;
}
.handname {
  color: #3385ff;
}
.handname:hover {
  color: #4ddbff;
}
</style>