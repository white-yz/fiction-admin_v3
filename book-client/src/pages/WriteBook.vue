/* 从小说详情页修改小说 */
<template>
  <div class="book">
    <div class="detail-box-lf">
      <div class="detail-info-box clearfix">
        <div class="detail-info-header-container clearfix">
          <div class="clearfix">
            <div class="detail-info-header-box-lf">
              <span class="cover_mask">
                <a><img class="lazy" :src="getUrl(book.pic)" alt="" /></a>
              </span>
            </div>
            <div style="margin-left: 23px" class="detail-info-header-box-rt detail-info-list">
              <h2>{{ book.name }}</h2>
              <ul>
                <li>
                  作者&nbsp;：<a href="javascript:;">{{ writer.name }}</a>
                </li>
                <li>
                  <p>章节&nbsp;:&nbsp;{{ directory.length }}</p>
                </li>
                <li>
                  <p>发布时间&nbsp;：{{ book.createTime.slice(0, 11) }}</p>
                </li>
                <li>
                  <p>更新时间&nbsp;：{{ book.updateTime.slice(0, 11) }}</p>
                </li>
                <li>
                  <p>总字数&nbsp;：{{ this.count }}</p>
                </li>
                <li>
                  <p>类型&nbsp;：{{ book.style }}</p>
                </li>
              </ul>
            </div>
          </div>
          <div class="detail_info_intro">
            <h4 style="margin-bottom: 15px;"><span style="color: #5151b6">简介</span></h4>
            <div class="actual-height" style="padding-bottom: 0px">
              <p>{{ book.introduction }}</p>
            </div>
          </div>
        </div>
        <div class="directory clearfix">
          <h4 style="
              margin-bottom: 15px;
              font-size: 20px;
              font-weight: normal;
              display: block;
              color: #5151b6
            ">
            <span style="color: #5151b6">章节列表</span>
          </h4>
          <el-button type="success" style="margin-bottom: 10px; background-color: #5151b6" @click="addDirectory">新增章节</el-button>
          <ul>
            <span v-for="item in directory" :key="item.id">
              <li @click="contentPage(item.id, item.bookId)" class="dirname">
                {{ item.name }}
              </li>
              <i class="el-icon-edit" @click="editSection(item)"></i>
              <i class="el-icon-delete" @click="deleteSection(item.id)"></i>
            </span>
          </ul>

        </div>
      </div>
    </div>

    <!-- 添加窗口  -->
    <el-dialog :title="registerForm.id && '编辑' || '新增'" :visible.sync="dialogVisible" width="1100px" center>
      <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="90px">
        <el-form-item prop="name" label="章节名称" size="mini">
          <el-input v-model="registerForm.name" placeholder="章节名称"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="内容" size="mini">
          <el-input v-model="registerForm.content" :rows="14" placeholder="内容" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="dialogVisible = false">取消</el-button>
        <el-button size="mini" @click="saveDirectory" type="primary">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {
  bookOfBookId,
  writerOfWriterId,
  directoryOfBookId,
  getCollectOfUserId,
  setDirectory,
  updateDirectory,
  delDirectory
} from '../api/index'
import { mixin } from '../mixins/index'
import { mapGetters } from 'vuex'
import Comment from '../components/Comment'
export default {
  name: 'WriterBook',
  mixins: [mixin],
  components: {
    Comment
  },
  data() {
    return {
      bookId: '',
      writerId: '',
      wId: 'writerId',
      book: [],
      writer: [],
      directory: [],
      count: 0,
      average: 0, //平均分
      rank: 0, //提交评价的分数
      sum: '', //评分人数
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      dialogVisible: false,
      registerForm: {
        name: null
      },
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      }
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', //用户是否已登录
      'userId', //当前登录用户id
      'isActive' //当前书籍是否已收藏
    ])
  },
  created() {
    this.bookId = this.$route.query.id
    this.writerId = this.$route.query.writerId
    this.getBook()
    this.getWriter()
    this.getDirectory()
  },
  methods: {
    getBook() {
      this.book = []
      this.$store.commit('setIsActive', false)
      bookOfBookId(this.bookId).then((res) => {
        this.book = res
        // console.log(res);
      })
      if (this.loginIn) {
        getCollectOfUserId(this.userId).then((res) => {
          // console.log(res);
          for (let item of res) {
            if (item.bookId == this.bookId) {
              this.$store.commit('setIsActive', true)
              break
            }
          }
        })
      }
    },
    getWriter() {
      this.writer = []
      writerOfWriterId(this.writerId).then((res) => {
        this.writer = res
        // console.log(res);
      })
    },
    editSection(row) {
      this.registerForm = { ...row }
      this.dialogVisible = true
    },
    getDirectory() {
      this.directory = []
      this.count = 0
      directoryOfBookId(this.bookId).then((res) => {
        this.directory = res
        for (var i = 0; i < res.length; i++) {
          this.count += res[i].content.replace(/\s+/g, '').length
        }
      })
    },
    contentPage(id, bookId) {
      this.$store.commit('setActiveName', '')
      let edit = 'true'
      this.$router.push({ path: `/Content`, query: { id, bookId, edit } })
    },
    goWriter(id) {
      this.$router.push({ path: `/writer-album`, query: { id } })
    },
    addDirectory() {
      this.dialogVisible = true
      this.$refs.registerForm.resetFields()
      this.registerForm = {
        id: null,
        name: '',
        content: null
      }
    },
    //添加章节
    saveDirectory() {
      if (!this.registerForm.id) {
        this.$refs['registerForm'].validate((valid) => {
          if (valid) {
            let params = new URLSearchParams()
            params.append('bookId', this.bookId)
            params.append('name', this.registerForm.name)
            params.append('content', this.registerForm.content)
            setDirectory(params)
              .then((res) => {
                if (res.code == 1) {
                  this.getDirectory()
                  this.notify('添加成功', 'success')
                } else {
                  this.notify('添加失败', 'error')
                }
              })
              .catch((err) => {
                console.log(err)
              })
            this.dialogVisible = false
          } else {
            console.log('error submit!!')
            return false
          }
        })
      } else {
        this.editSave()
      }
    },
    ///保存编辑页面修改的数据
    editSave() {
      let params = new URLSearchParams()
      params.append('id', this.registerForm.id)
      params.append('name', this.registerForm.name)
      params.append('content', this.registerForm.content)
      updateDirectory(params)
        .then((res) => {
          if (res.code == 1) {
            this.getDirectory()
            this.notify('修改成功', 'success')
          } else {
            this.notify('修改失败', 'error')
          }
        })
        .catch((err) => {
          console.log(err)
        })
      this.dialogVisible = false
    },
    deleteSection(id) {
      this.$confirm('此操作将删除该书籍章节信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delDirectory(id)
            .then((res) => {
              if (res) {
                this.getDirectory()
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
@import '../assets/css/book.scss';
.album-score .re {
  position: relative;
}
.album-score .le {
  float: left;
}
#score {
  position: absolute;
  right: -50px;
  top: 22px;
  font-size: 40px;
}
.sum {
  color: #5151b6;
}
.el-icon-edit {
  color: #5151b6;
}
.el-icon-delete {
  margin-left: 5px;
  color: red;
}
i:hover {
  color: #6464c9;
}
.dirname{
  color: #5a5959  !important;
}
.dirname:hover {
  color: #7777d7 !important;;
}
h4 span[data-v-245a9974] {
  color: #5151b6;
  border-left: 3px solid #5151b6;
  padding-left: 8px;
}
</style>
