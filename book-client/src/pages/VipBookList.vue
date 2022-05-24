/* VIP小说页面 */
<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li v-for="(item,index) in bookStyle" :key="index" @click="handleChangeView(item.name)"  :class="{active:item.name==activeName}">
        {{item.name}}
      </li>
    </ul>
    <div>
      <content-list :contentList="data" :isVip="1"></content-list>
      <div class="pagination">
        <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next,jumper" :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import ContentList from '../components/VipContentList'
import { getAllBook, getBookListOfLikeStyle, getAllBookOfCharge } from '../api/index'
import { mixin } from '../mixins'
import { bookStyle } from '../assets/data/bookList'

export default {
  name: 'book-list',
  components: {
    ContentList
  },
  data() {
    return {
      albumDatas: [], //歌单数据
      pageSize: 10, //页面大小，一页有15条数据
      currentPage: 1, //当前页，默认第一页
      bookStyle: [], //风格
      activeName: '全部书籍' //当前风格
    }
  },
  computed: {
    //计算当前表格中的数据
    data() {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted() {
    this.bookStyle = bookStyle
    this.getBookList()
  },

  methods: {
    //查询vip书籍
    getBookList() {
      getAllBookOfCharge(1).then((res) => {
        this.currentPage = 1
        this.albumDatas = res
      })
    },
    //获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    //根据style显示对应的歌单
    handleChangeView(name) {
      this.activeName = name
      this.albumDatas = []
      if (name == '全部书籍') {
        this.getBookList()
      } else {
        this.goBookListOfStyle(name)
      }
    },
    //根据style查询对应的歌单
    goBookListOfStyle(style) {
      getBookListOfLikeStyle(style, 1).then((res) => {
        this.currentPage = 1
        this.albumDatas = res
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
.pagination > div {
  margin: 0 auto;
}
</style>