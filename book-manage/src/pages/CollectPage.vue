<template>
    <div class="table">
        <div class="container" style="margin-bottom:20px;">
            <div class="handle-box">
                <el-button type="primary" style="background-color: #D55969; border: #D55969;" size="small" @click="delAll">{{$t('common.batchDel')}}</el-button>
                <el-input style="float: right;" v-model="select_word" size="mini" :placeholder="$t('common.search')" class="handle-input"></el-input>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="42"></el-table-column>
            <el-table-column prop="name" :label="$t('book.Name')" align="center"></el-table-column>
            <el-table-column :label="$t('common.operation')" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="$t('book.del')" :visible.sync="delVisible" width="300px" center>
            <div align="center">{{$t('common.delText')}}</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="deleteRow" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mixin } from '../mixins/index';
import {bookOfBookId,getCollectOfUserId,deleteCollection} from '../api/index';

export default {
    mixins: [mixin],
    props: ['id'],
    data(){
        return{
            delVisible: false,          //删除弹窗是否显示
            tableData: [],
            tempData: [],
            select_word: '',
            idx: -1,          //当前选择项
            multipleSelection: [],   //哪些项已经打勾
        }
    },
    watch:{
        //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
        select_word: function(){
            if(this.select_word == ''){
                this.tableData = this.tempData;
            }else{
                this.tableData = [];
                for(let item of this.tempData){
                    if(item.name.includes(this.select_word)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){
        this.getData();
    },
    methods:{
        //查询该用户所有收藏的歌曲
        getData(){
            this.tempData = [];
            this.tableData = [];
            getCollectOfUserId(this.$route.query.id).then(res => {
                for(let item of res){
                    this.getSong(item.bookId);
                }
            })
        },
        //根据歌曲id查询歌曲对象，放到tempData和tableData里面
        getSong(id){
            bookOfBookId(id)
            .then(res => {
                this.tempData.push(res);
                this.tableData.push(res);
            })
            .catch(err => {
                console.log(err);
            });
        },
        //删除一条歌曲
        deleteRow(){
            deleteCollection(this.$route.query.id,this.idx.id)
            .then(res => {
                if(res){
                    this.getData();
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.delVisible = false;
        },
        //批量删除已经选择的项
        delAll(){
            for(let item of this.multipleSelection){
                this.handleDelete(item);
                this.deleteRow();
            }
            this.multipleSelection = [];
        }
    }
}
</script>

<style scoped>
    .handle-input{
        width: 300px;
        display: inline-block;
    }
</style>