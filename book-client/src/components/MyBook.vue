/* 收藏页 */
<template>
  <album-content :bookList="collectList" :setting="true"  :showRecord="true"></album-content>
</template>

<script>
import { mixin } from '../mixins'
import { mapGetters } from 'vuex'
import { getUserOfId, getCollectOfUserId, bookOfBookId } from '../api/index'
import AlbumContent from '../components/AlbumContent'

export default {
  name: 'mybook',
  mixins: [mixin],
  components: {
    AlbumContent
  },
  data() {
    return {
      avator: '', //用户头像
      username: '', //昵称
      userSex: '', //性别
      birth: '', //生日
      location: '', //故乡
      introduction: '', //签名
      collection: [], //收藏的书籍列表
      collectList: [] //收藏的书籍列表（带书籍详情）
    }
  },
  computed: {
    ...mapGetters([
      'listOfSongs', //当前书籍列表
      'userId' //当前登录用户id
    ])
  },
  mounted() {
    this.getMsg(this.userId)
    this.getCollection(this.userId)
  },
  methods: {
    getMsg(userId) {
      getUserOfId(userId)
        .then((res) => {
          this.avator = res.avator
          this.username = res.username
          if (res.sex == 0) {
            this.userSex = '女'
          } else if (res.sex == 1) {
            this.userSex = '男'
          }
          this.birth = this.attachBirth(res.birth)
          this.location = res.location
          this.introduction = res.introduction
        })
        .catch((err) => {
          console.log(err)
        })
    },
    //获取我的收藏列表
    getCollection(userId) {
      this.collection = []
      this.collectList = []
      getCollectOfUserId(userId)
        .then((res) => {
          // console.log(res);
          this.collection = res
          //通过书籍id获取书籍信息
          for (let item of this.collection) {
            this.getBooksOfIds(item.bookId,item.record,item.directoryId)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    //通过书籍id获取书籍信息
    getBooksOfIds(id,record,directoryId) {
      bookOfBookId(id)
        .then((res) => {
          if(record) res.record = record
          if(directoryId) res.directoryId = directoryId
          this.collectList.push(res)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>