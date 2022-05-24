<template>
    <div class="table">
        <div class="container" style="margin-bottom:20px;">
            <div class="handle-box">
                <el-input v-model="select_word" size="small" :placeholder="$t('book.enterName')" class="handle-input" style="float: right;"></el-input>
                <el-button-group>
                    <el-button type="primary" style="background-color: #D55969; border: #D55969;" class="iconfont icon-piliangshanchu" size="small" @click="delAll">{{$t('common.batchDel')}}</el-button>
                    <el-button type="primary" style="background-color: #4747A5; border: #4747A5 none;" icon="el-icon-plus" size="small" @click="centerDialogVisible = true">{{$t('book.add')}}</el-button>
                </el-button-group>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="42"></el-table-column>
            <el-table-column :label="$t('book.pic')" width="110" align="center">
                <template slot-scope="scope">
                    <div class="book-img">
                        <img :src="getUrl(scope.row.pic)" alt="" style="height: 100px;">
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                        :on-success="handleAvatorSuccess">
                        <el-button size="mini" type="primary" plain>{{$t('common.updatePic')}}</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" :label="$t('book.Name')" width="150" align="center"></el-table-column>
            <el-table-column prop="style" :label="$t('common.style')" width="120" align="center"></el-table-column>
            <el-table-column :label="$t('common.introduction')" >
                <template slot-scope="scope">
                    <p style="height:100px;overflow:auto;white-space:pre-wrap;">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="isCharge" :label="$t('common.isCharge')" width="120" align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.isCharge == 1">是</el-tag>
                    <el-tag type="danger" v-else>否</el-tag>
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.operation')" width="230" align="center">
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" @click="directoryEdit(scope.row.id,scope.row.name)" class="iconfont icon-mulu" circle></el-button>
                    <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)" ></el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination">
            <el-pagination
                background
                layout = "total, sizes, prev, pager, next, jumper"
                :current-page="currentPage"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                >
            </el-pagination>
        </div>
        <!-- 添加窗口  -->
        <el-dialog :title="$t('book.add')" :visible.sync="centerDialogVisible" width="550px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="95px" :rules="rules">
                <el-form-item prop="name" :label="$t('book.Name')" size="mini">
                    <el-input v-model="registerForm.name" :placeholder="$t('book.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="style" :label="$t('common.style')" size="mini">
                    <!-- <el-input v-model="registerForm.style" :placeholder="$t('common.style')"></el-input> -->
                    <el-select v-model="registerForm.style" placeholder="风格" style="width: 100%;">
                        <el-option v-for=" item in bookStyleWrite" :key="item.name" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.introduction')" size="mini">
                    <el-input v-model="registerForm.introduction" :placeholder="$t('common.introduction')" type="textarea"></el-input>
                </el-form-item>
                <el-form-item prop="isCharge" :label="$t('common.isCharge')" size="mini">
                      <el-radio-group v-model="registerForm.isCharge">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                      </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="addBook" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 编辑窗口 -->
        <el-dialog :title="$t('book.edit')" :visible.sync="editVisible" width="550px" center>
            <el-form :model="form" ref="form" label-width="95px" :rules="rules">
                <el-form-item prop="name" :label="$t('book.Name')" size="mini">
                    <el-input v-model="form.name" :placeholder="$t('book.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="style" :label="$t('common.style')" size="mini">
                    <!-- <el-input v-model="form.style" :placeholder="$t('common.style')"></el-input> -->
                    <el-select v-model="form.style" placeholder="风格" style="width: 100%;">
                        <el-option v-for=" item in bookStyleWrite" :key="item.name" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.introduction')" >
                    <el-input v-model="form.introduction" :placeholder="$t('common.introduction')" type="textarea"></el-input>
                </el-form-item>
                <el-form-item prop="isCharge" :label="$t('common.isCharge')" size="mini">
                      <el-radio-group v-model="form.isCharge">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                      </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="editSave" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 删除窗口 -->
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
import {setBook, getAllBook,  bookOfWriterId, updateBook, delBook} from '../api/index';
import {mixin} from '../mixins/index';
import { bookStyleWrite } from '../assets/data/bookList'
export default {
    mixins: [mixin],
    data() {
        return {
            writerId: '',               //作家id
            writerName: '',             //作家名
            centerDialogVisible:false,  //添加弹窗是否显示
            editVisible: false,         //编辑弹窗是否显示
            delVisible: false,          //删除弹窗是否显示
            registerForm:{      //添加框
                name: '',
                writerName:'',
                introduction:'',
                style: '',
                isCharge: 0
            },
            form:{      //编辑框
                id: '',
                name: '',
                introduction: '',
                style: '',
                isCharge: 0
            },
            tableData:[],
            tempData:[],
            select_word: '',
            pageSize: 5,    //分页每页大小
            currentPage: 1, //当前页
            idx: -1,        //当前选择项
            multipleSelection: [],   //哪些项已经打勾
            rules: {
                name: [{ required: true, message: '请输入书名', trigger: 'blur' }],
                introduction: [{ required: true, message: '请输入简介', trigger: 'blur' }],
                style: [{ required: true, message: '请选择风格', trigger: 'blur' }],
                isCharge: [
                    { required: true, message: '请选择是否收费', trigger: 'blur' }]
                }
        }
    },
    mounted(){
        this.bookStyleWrite = bookStyleWrite;
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
        this.writerId = this.$route.query.id;
        this.writerName = this.$route.query.name;
        this.getData();
    },
    methods: {
        //查询所有书籍
        getData(){
            this.tempData = [];
            this.tableData = [];
            bookOfWriterId(this.writerId).then(res => {
                this.tempData = res;
                this.tableData = res;
            })
        },
        //添加书籍
        addBook(){
            this.$refs['registerForm'].validate((valid) => {
            if (valid) {
                let params = new URLSearchParams();
                params.append('writerId',this.writerId);
                params.append('name',this.registerForm.name);
                params.append('style',this.registerForm.style);
                params.append('pic','/img/bookPic/tubiao.jpg');
                params.append('introduction',this.registerForm.introduction);
                params.append('isCharge',this.registerForm.isCharge);
                this.registerForm.name = null;
                this.registerForm.style = null;
                this.registerForm.introduction = null;
                setBook(params)
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
        //更新图片
        uploadUrl(id){
            return `${this.$store.state.HOST}/book/updateBookPic?id=${id}`
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
                introduction: row.introduction,
                style: row.style,
                isCharge: row.isCharge
            }
        },
        //保存编辑页面修改的数据
        editSave(){
            this.$refs['form'].validate((valid) => {
            if (valid) {
                let params = new URLSearchParams();
                params.append('id',this.form.id);
                params.append('name',this.form.name);
                params.append('style',this.form.style);
                params.append('introduction',this.form.introduction);
                params.append('isCharge',this.form.isCharge);
                updateBook(params)
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
        //删除一本书籍
        deleteRow(){
            delBook(this.idx)
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
        //转向书籍管理页面
        directoryEdit(id,name){
            this.$router.push({path:`/Directory`,query:{id,name}});
        }
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
</style>