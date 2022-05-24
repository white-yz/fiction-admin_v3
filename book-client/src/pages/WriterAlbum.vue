/* 作家详情页面 */
<template>
    <div class="personal_wrap">
        <div class="personal_left">
            <div class="personalbox">
                <div class="friend_home_info">
                    <img class="f_headphoto" :src="getUrl(writer.pic)" alt="">
                    <p style="margin-top:10px;">{{writer.name}}</p>
                    <p>性别：{{sex}}</p>
                    <p>地区：{{writer.location}}</p>
                    <p>出生日期：{{writer.birth.slice(0,11)}}</p>
                    <p>简介：{{writer.introduction}}</p>
                </div>
            </div>
        </div>
        <div class="personal_main">
            <div class="main_h1tab">
                <h1><span>作品（&nbsp;{{book.length}}&nbsp;）</span></h1>
            </div>
            <div class="mybook_list">
                <div class="bestbook_list fr_dl">
                    <div v-for="(item) in book" :key="item.id" class="clearfix div">
                        <span style="cursor: pointer;" @click="goBook(item.id,item.writerId)"><img :src="getUrl(item.pic)" alt=""></span>
                        <div class="bestbook_info f_bestbook">
                            <h4>《{{item.name}}》</h4>
                            <p>
                                <span class="gray">作品类别：{{item.style}}</span>
                                <span class="gray" style="margin-left:20px;">最近更新：{{item.updateTime}}</span>
                            </p>
                            <p>{{item.introduction}}</p>
                            <p><span @click="goBook(item.id,item.writerId)" class="online_read color">在线阅读</span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {writerOfWriterId,bookOfWriterId} from '../api/index';
import {mixin} from '../mixins/index';

export default {
    name: 'WriterAlbum',
    mixins: [mixin],
    data() {
        return {
            bookId: '',
            writerId: '',
            wId: 'writerId',
            sex: '',
            book: [],
            writer: [],
            count: 0,
        }
    },
    created(){
        this.writerId = this.$route.query.id;
        this.getBook();
        this.getWriter();
    },
    methods: {
        getWriter(){
            this.writer = [];
            writerOfWriterId(this.writerId).then(res =>{
                this.writer = res;
                if(res.sex == 0 ){
                    this.sex = '女';
                }
                if(res.sex == 1 ){
                    this.sex = '男';
                }
                if(res.sex == 2 ){
                    this.sex = '组合';
                }
                if(res.sex == 3 ){
                    this.sex = '不明';
                }
                console.log(res);
            });
        },
        getBook(){
            this.book = [];
            bookOfWriterId(this.writerId).then(res =>{
                this.book = res;
                console.log(res);
            });
        },
        goBook(id,writerId){
            this.$router.push({path:`/Book`,query:{id,writerId}});
        }
    },
}
</script>

<style lang="scss" scoped>
@import '../assets/css/writer.scss';
</style>