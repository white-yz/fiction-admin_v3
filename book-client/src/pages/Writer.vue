/* 作家页面 */
<template>
    <div class="singer">
        <ul class="singer-header">
            <li v-for="(item,index) in writerStyle" :key="index" @click="handleChangeView(item)"
                :class="{active:item.name==activeName}">
                {{item.name}}
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next,jumper"
                        :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import ContentList from '../components/ContentList';
import {getAllWriter,getWriterOfSex} from '../api/index';
import {mixin} from "../mixins";
import {writerStyle} from '../assets/data/writer';

export default {
    name: 'writer',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas: [],
            pageSize: 10,        //页面大小，一页有15条数据
            currentPage: 1,      //当前页，默认第一页
            writerStyle: [],           //风格
            activeName: '全部作家',    //当前风格
        }
    },
    computed:{
        //计算当前表格中的数据
        data(){
            return this.albumDatas.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize)
        }
    },
    mounted(){
        this.writerStyle = writerStyle;
        this.getWriterList();
    },
    methods:{
        getWriterList(){
            // console.log(this.writerStyle);
            getAllWriter(this.$route.query.keywords)
                .then(res =>{
                    this.currentPage = 1;
                    this.albumDatas = res;
                })

        },
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //根据类型显示对应的作家
        handleChangeView(item){
            this.activeName = item.name;
            this.albumDatas = [];
            if(item.name == '全部作家'){
                this.getWriterList();
            }else{
                this.getWriterOfSex  (item.type)
            }
        },
        getWriterOfSex(sex){
            getWriterOfSex(sex)
                .then(res =>{
                        // console.log(res);
                        this.currentPage = 1;
                        this.albumDatas = res;
                    })
        },
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer.scss';
.pagination>div{
    margin: 0 auto;
}
</style>