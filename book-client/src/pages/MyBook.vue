<template>
    <div class="my-music">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(avator)">
            </div>
            <ul class="album-info">
                <li>昵称: {{username}}</li>
                <li>性别: {{userSex}}</li>
                <li>生日：{{birth}}</li>
                <li>故乡：{{location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                个性签名：{{introduction}}
            </div>
            <div class="songs-body">
                <album-content :bookList="collectList" :setting="true">
                    <template slot="title">我的收藏</template>
                </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {getUserOfId,getCollectOfUserId,bookOfBookId} from '../api/index';
import AlbumContent from "../components/AlbumContent";

export default {
    name: 'my-book',
    mixins: [mixin],
    components:{
        AlbumContent
    },
    data(){
        return {
            avator: '',       //用户头像
            username: '',     //昵称
            userSex: '',      //性别
            birth: '',        //生日
            location: '',     //故乡
            introduction: '', //签名
            collection: [],     //收藏的书籍列表
            collectList: [],    //收藏的书籍列表（带书籍详情）
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs',      //当前收藏列表
            'userId',           //当前登录用户id
        ])
    },
    mounted(){
        this.getMsg(this.userId);
        this.getCollection(this.userId);
    },
    methods:{
        getMsg(userId){
            getUserOfId(userId)
                .then(res =>{
                    this.avator = res.avator;
                    this.username = res.username;
                    if(res.sex==0){
                        this.userSex = '女';
                    }else if (res.sex==1){
                        this.userSex = '男';
                    }
                    this.birth = this.attachBirth(res.birth);
                    this.location = res.location;
                    this.introduction = res.introduction;
                })
                .catch(err => {
                    console.log(err);
                })
        },
        //获取我的收藏列表
        getCollection(userId){
            getCollectOfUserId(userId)
                .then(res =>{
                        // console.log(res);
                        this.collection = res;
                        //通过书籍id获取书籍信息
                        for(let item of this.collection){
                            this.getBooksOfIds(item.bookId);
                        }
                    })
                .catch(err => {
                    console.log(err);
                })
        },
        //通过书籍id获取书籍信息
        getBooksOfIds(id){
            bookOfBookId(id)
                .then(res =>{
                        this.collectList.push(res);
                    })
                .catch(err => {
                    console.log(err);
                })
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>