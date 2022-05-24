<template>
    <div class="table">
        <div class="container" style="margin-bottom:20px;">
            <div class="handle-box">
                <el-input v-model="select_word" size="small" :placeholder="$t('directory.enterName')" class="handle-input" style="float: right;"></el-input>
                <el-button type="primary" style="background-color: #4747A5; border: #4747A5 none;" icon="el-icon-plus" size="small" @click="centerDialogVisible = true">{{$t('directory.add')}}</el-button>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column prop="name" :label="$t('directory.Name')" width="150" align="center"></el-table-column>
            <el-table-column :label="$t('directory.content')" >
                <template slot-scope="scope">
                    <p style="height:auto;overflow:auto;white-space:pre-wrap;">{{scope.row.content}}</p>
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.operation')" width="110" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)" ></el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination">
            <el-pagination
                background
                layout = "total, prev, pager, next, jumper"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                >
            </el-pagination>
        </div>
        <!-- 添加窗口  -->
        <el-dialog :title="$t('directory.add')" :visible.sync="centerDialogVisible" width="1100px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="70px" :rules="rules">
                <el-form-item prop="name" :label="$t('directory.Name')" size="mini">
                    <el-input v-model="registerForm.name" :placeholder="$t('directory.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="content" :label="$t('directory.content')" size="mini">
                    <el-input v-model="registerForm.content" :rows="14" :placeholder="$t('directory.content')" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="addDirectory" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 编辑窗口 -->
        <el-dialog :title="$t('directory.edit')" :visible.sync="editVisible" width="1100px" center>
            <el-form :model="form" ref="form" label-width="70px" :rules="rules">
                <el-form-item prop="name" :label="$t('directory.Name')" size="mini">
                    <el-input v-model="form.name" :placeholder="$t('directory.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="content" :label="$t('directory.content')" >
                    <el-input v-model="form.content" :rows="14" :placeholder="$t('directory.content')" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="editSave" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 删除窗口 -->
        <el-dialog :title="$t('directory.del')" :visible.sync="delVisible" width="300px" center>
            <div align="center">{{$t('common.delText')}}</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="deleteRow" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {setDirectory, getAllDirectory,  directoryOfBookId, updateDirectory, delDirectory} from '../api/index';
import {mixin} from '../mixins/index';
export default {
    mixins: [mixin],
    data() {
        return {
            bookId: '',               //书籍id
            bookName: '',             //书名
            centerDialogVisible:false,  //添加弹窗是否显示
            editVisible: false,         //编辑弹窗是否显示
            delVisible: false,          //删除弹窗是否显示
            registerForm:{      //添加框
                name: '',
                bookName:'',
                content:'',
            },
            form:{      //编辑框
                id: '',
                name: '',
                content: ''
            },
            tableData:[],
            tempData:[],
            select_word: '',
            pageSize: 1,    //分页每页大小
            currentPage: 1, //当前页
            idx: -1,        //当前选择项
            rules: {
                name: [{ required: true, message: '请输入章节名', trigger: 'blur' }],
                content: [{ required: true, message: '请输入章节内容', trigger: 'blur' }]
                }
        }
    },
    computed:{
        //计算当前搜索结果表里的数据
        data(){
            return this.tableData.slice((this.currentPage - 1) * this.pageSize,this.currentPage * this.pageSize);
        }
    },
    watch:{
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
        this.bookId = this.$route.query.id;
        this.bookName = this.$route.query.name;
        this.getData();
    },
    methods: {
        //查询所有章节
        getData(){
            this.tempData = [];
            this.tableData = [];
            directoryOfBookId(this.bookId).then(res => {
                this.tempData = res;
                this.tableData = res;
            })
        },
        //添加章节
        addDirectory(){
             this.$refs['registerForm'].validate((valid) => {
            if (valid) {
                let params = new URLSearchParams();
                params.append('bookId',this.bookId);
                params.append('name',this.registerForm.name);
                params.append('content',this.registerForm.content);
                this.registerForm.name = null;
                this.registerForm.content = null;
                setDirectory(params)
                .then(res => {
                    if(res.code == 1){
                        this.getData();
                        this.notify(res.msg,"success");
                    }else{
                        this.notify(res.msg,"error");
                    }
                })
                .catch(err => {
                    console.log(err);
                });
                this.centerDialogVisible = false;
            }
            })
        },
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        handleSizeChange(val) {
            this.pageSize = val;
        },
        //弹出编辑页面
        handleEdit(row){
            this.editVisible = true;
            this.form = {
                id: row.id,
                name: row.name,
                content: row.content
            }
        },
        //保存编辑页面修改的数据
        editSave(){
             this.$refs['form'].validate((valid) => {
            if (valid) {
                let params = new URLSearchParams();
                params.append('id',this.form.id);
                params.append('name',this.form.name);
                params.append('content',this.form.content);
                updateDirectory(params)
                .then(res => {
                    if(res.code == 1){
                        this.getData();
                        this.notify("修改成功","success");
                    }else{
                        this.notify("修改失败","error");
                    }
                })
                .catch(err => {
                    console.log(err);
                });
                this.editVisible = false;
            }
            })
        },
        //删除章节
        deleteRow(){
            delDirectory(this.idx)
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
    },
}
</script>

<style scoped>
    .book-img{
        width: 100%;
        height: 80px;
        border-radius: 5px;
        margin-bottom: 5px;
        overflow: hidden;
    }
    .handle-input{
        width: 300px;
        display: inline-block;
    }
    .pagination{
        display: flex;
        justify-content: center;
    }
    .el-button--primary {
        background: #4747A5;
        border-color: #4747A5;
        color: #fff;
    }
    .el-button--primary:hover {
        background: #5151b6;
        color: #fff;
    }
    .el-button--danger {
    background: #D55969;
    border-color: #D55969;
    color: #fff;
    }
    .el-button--danger:hover {
    background: #e26374;
    color: #fff;
    }
</style>