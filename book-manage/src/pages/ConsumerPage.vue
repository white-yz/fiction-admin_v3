<template>
    <div class="table">
        <div class="container" style="margin-bottom:20px;">
            <div class="handle-box">
                <el-input v-model="select_word" size="small" :placeholder="$t('user.enterName')" class="handle-input" style="float: right;"></el-input>
                <el-button-group>
                    <el-button type="primary" style="background-color: #D55969; border: #D55969;" class="iconfont icon-piliangshanchu" size="small" @click="delAll">{{$t('common.batchDel')}}</el-button>
                    <el-button type="primary" style="background-color: #4747A5; border: #4747A5 none;" icon="el-icon-plus" size="small" @click="centerDialogVisible = true">{{$t('user.add')}}</el-button>
                </el-button-group>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="42"></el-table-column>
            <el-table-column :label="$t('user.pic')" width="110" align="center">
                <template slot-scope="scope">
                    <div class="consumer-img">
                        <img :src="getUrl(scope.row.avator)" style="width:100%" />
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
                        <el-button size="mini">{{$t('common.updatePic')}}</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="username" :label="$t('user.Name')" width="110" align="center"></el-table-column>
            <el-table-column :label="$t('common.sex')" width="62" align="center">
                <template slot-scope="scope">
                    {{changeSex(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column prop="phoneNum" :label="$t('common.phone')" width="106" align="center"></el-table-column>
            <el-table-column prop="email" :label="$t('common.email')" width="190" align="center"></el-table-column>
            <el-table-column :label="$t('common.birth')" width="120" align="center">
                <template slot-scope="scope">
                    {{attachBirth(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="introduction" :label="$t('common.signature')" align="center"></el-table-column>
            <el-table-column prop="location" :label="$t('common.location')" width="75" align="center"></el-table-column>
            <el-table-column :label="$t('common.state')" width="120" align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.state == 0">正常</el-tag>
                    <el-tag type="danger" v-else>已冻结</el-tag>
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.operation')" width="260" align="center">
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" @click="getCollect(data[scope.$index].id)" icon="icon-shoucang iconfont" circle></el-button>
                    <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)"></el-button>
                    <el-button size="mini" type="success" v-if="scope.row.state==0" icon="el-icon-success" circle @click="handleState(scope.row)"></el-button>
                    <el-button size="mini" type="danger" v-if="scope.row.state==1" icon="el-icon-warning" circle @click="handleState(scope.row)"></el-button>
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
        <el-dialog :title="$t('user.add')" :visible.sync="centerDialogVisible" width="550px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="120px" :rules="rules">
                <el-form-item prop="username" :label="$t('user.Name')" size="mini">
                    <el-input v-model="registerForm.username" :placeholder="$t('user.Name')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="password" :label="$t('common.pwd')" size="mini">
                    <el-input type="password" v-model="registerForm.password" :placeholder="$t('common.pwd')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="sex" :label="$t('common.sex')" size="mini">
                    <input type="radio" name="sex" value="0" v-model="registerForm.sex">&nbsp;{{$t('common.woman')}}&nbsp;&nbsp;
                    <input type="radio" name="sex" value="1" v-model="registerForm.sex">&nbsp;{{$t('common.man')}}
                </el-form-item>
                <el-form-item prop="phoneNum" :label="$t('common.phone')" size="mini">
                    <el-input v-model="registerForm.phoneNum" :placeholder="$t('common.phone')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="email" :label="$t('common.email')" size="mini">
                    <el-input v-model="registerForm.email" :placeholder="$t('common.email')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="birth" :label="$t('common.birth')" size="mini">
                    <el-date-picker type="date" :placeholder="$t('common.selectDate')" v-model="registerForm.birth" style="width: 90%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.signature')" size="mini">
                    <el-input v-model="registerForm.introduction" :placeholder="$t('common.signature')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="location" :label="$t('common.location')" size="mini">
                    <el-select v-model="registerForm.location" :placeholder="$t('common.location')" style="width: 90%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="addConsumer">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 编辑窗口 -->
        <el-dialog :title="$t('user.edit')" :visible.sync="editVisible" width="550px" center>
            <el-form :model="form" ref="form" label-width="120px" :rules="rules">
                <el-form-item prop="username" :label="$t('user.Name')" size="mini">
                    <el-input v-model="form.username" :placeholder="$t('user.Name')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="password" :label="$t('common.pwd')" size="mini">
                    <el-input type="password" v-model="form.password" :placeholder="$t('common.pwd')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item :label="$t('common.sex')" size="mini">
                    <input type="radio" name="sex" value="0" v-model="form.sex">&nbsp;{{$t('common.woman')}}&nbsp;&nbsp;
                    <input type="radio" name="sex" value="1" v-model="form.sex">&nbsp;{{$t('common.man')}}
                </el-form-item>
                <el-form-item prop="phoneNum" :label="$t('common.phone')" size="mini">
                    <el-input v-model="form.phoneNum" :placeholder="$t('common.phone')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="email" :label="$t('common.email')" size="mini">
                    <el-input v-model="form.email" :placeholder="$t('common.email')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="birth" :label="$t('common.birth')" size="mini">
                    <el-date-picker type="date" :placeholder="$t('common.selectDate')" v-model="form.birth" style="width: 90%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.signature')" size="mini">
                    <el-input v-model="form.introduction" :placeholder="$t('common.signature')" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item prop="location" :label="$t('common.location')" size="mini">
                    <el-select v-model="form.location" :placeholder="$t('common.location')" style="width: 90%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="editSave">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 删除窗口 -->
        <el-dialog :title="$t('user.del')" :visible.sync="delVisible" width="300px" center>
            <div align="center">{{$t('common.delText')}}</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="deleteRow">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 冻结窗口 -->
        <el-dialog :title="state && $t('user.thaw') || $t('user.freeze')" :visible.sync="freezeVisible" width="300px" center>
            <div align="center">{{state && $t('common.thawText') || $t('common.freezeText')}}</div>
            <span slot="footer">
                <el-button size="mini" @click="freezeVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="handleStateUp">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getAllConsumer, setConsumer, updateConsumer, delConsumer, updateConsumerState } from '../api/index'
import { mixin } from '../mixins/index'
import { cities, rules } from '../assets/data/form'
export default {
    mixins: [mixin],
    data() {
        return {
            centerDialogVisible: false, //添加弹窗是否显示
            editVisible: false, //编辑弹窗是否显示
            delVisible: false, //删除弹窗是否显示
            freezeVisible: false, //改变状态弹窗是否显示
            state: 0, //用户状态
            id: 0, //用户id
            registerForm: {
                //添加框
                username: '',
                password: '',
                sex: '0',
                phoneNum: '',
                email: '',
                birth: '',
                introduction: '',
                location: ''
            },
            cities: cities,//地区
            rules: rules,
            form: {
                //编辑框
                id: '',
                username: '',
                password: '',
                sex: '',
                phoneNum: '',
                email: '',
                birth: '',
                introduction: '',
                location: ''
            },
            tableData: [],//当前窗口内容
            tempData: [],
            select_word: '',//搜索关键词
            pageSize: 5, //分页每页大小
            currentPage: 1, //当前页
            idx: -1, //当前选择项
            multipleSelection: [], //选中的多项内容
        }
    },
    computed: {
        //计算当前搜索结果表里的数据
        data() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
        }
    },
    watch: {
        //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
        select_word: function () {
            if (this.select_word == '') {
                this.tableData = this.tempData
            } else {
                this.tableData = []
                for (let item of this.tempData) {
                    if (item.username.includes(this.select_word)) {
                        this.tableData.push(item)
                    }
                }
            }
        }
    },
    created() {
        this.getData()//获取所有用户
    },
    methods: {
        //获取当前页
        handleCurrentChange(val) {
            this.currentPage = val
        },
        //获取当前每页数量
        handleSizeChange(val) {
            this.pageSize = val
        },
        //查询所有用户
        getData() {
            this.tempData = []
            this.tableData = []
            getAllConsumer().then((res) => {
                this.tempData = res
                this.tableData = res
                this.currentPage = 1
            })
        },
        //添加用户
        addConsumer() {
            this.$refs['registerForm'].validate((valid) => {
                if (valid) {
                    let d = this.registerForm.birth
                    let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
                    let params = new URLSearchParams()
                    params.append('username', this.registerForm.username)
                    params.append('password', this.registerForm.password)
                    params.append('sex', this.registerForm.sex)
                    params.append('phoneNum', this.registerForm.phoneNum)
                    params.append('email', this.registerForm.email)
                    params.append('birth', datetime)
                    params.append('introduction', this.registerForm.introduction)
                    params.append('location', this.registerForm.location)
                    params.append('avator', '/img/default.jpg')
                    setConsumer(params)//添加用户
                        .then((res) => {
                            if (res.code == 1) {
                                this.getData()
                                this.notify(res.msg, 'success')
                                this.centerDialogVisible = false//关闭窗口
                                // 清空表单内容
                                this.registerForm.username = null
                                this.registerForm.password = null
                                this.registerForm.sex = '0'
                                this.registerForm.phoneNum = null
                                this.registerForm.email = null
                                this.registerForm.introduction = null
                                this.registerForm.location = null
                            } else {
                                this.notify(res.msg, 'error')
                            }
                        })
                        .catch((err) => {
                            console.log(err)
                        })
                }
            })
        },
        //弹出编辑页面
        handleEdit(row) {
            this.editVisible = true
            this.form = {
                id: row.id,
                username: row.username,
                password: row.password,
                sex: row.sex,
                phoneNum: row.phoneNum,
                email: row.email,
                birth: row.birth,
                introduction: row.introduction,
                location: row.location
            }
        },
        //保存编辑页面修改的数据
        editSave() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    let d = new Date(this.form.birth)
                    let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
                    let params = new URLSearchParams()
                    params.append('id', this.form.id)
                    params.append('username', this.form.username)
                    params.append('password', this.form.password)
                    params.append('sex', this.form.sex)
                    params.append('phoneNum', this.form.phoneNum)
                    params.append('email', this.form.email)
                    params.append('birth', datetime)
                    params.append('introduction', this.form.introduction)
                    params.append('location', this.form.location)
                    //修改读者
                    updateConsumer(params)
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
                    this.editVisible = false
                }
            })
        },
        //更新图片
        uploadUrl(id) {
            return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`
        },
        //删除一名读者
        deleteRow() {
            delConsumer(this.idx)
                .then((res) => {
                    if (res) {
                        this.getData()
                        this.notify('删除成功', 'success')
                    } else {
                        this.notify('删除失败', 'error')
                    }
                })
                .catch((err) => {
                    console.log(err)
                })
            this.delVisible = false
        },
        //转向该用户的收藏列表
        getCollect(id) {
            this.$router.push({ path: '/collect', query: { id } })
        },
        //修改用户状态
        handleState(row) {//弹出窗口
            this.freezeVisible = true
            this.state = row.state
            this.id = row.id
        },
        handleStateUp() {
            let params = new URLSearchParams()
            params.append('id', this.id)
            params.append('state', this.state == 1 ? 0 : 1)//修改状态
            updateConsumerState(params)//保存状态
                .then((res) => {
                    if (res) {
                        this.getData()
                        this.notify(this.state == 1 ? '解冻成功' : '冻结成功', 'success')
                    } else {
                        this.notify(this.state == 1 ? '解冻失败' : '冻结失败', 'error')
                    }
                })
                .catch((err) => {})
            this.freezeVisible = false//关闭窗口
        }
    }
}
</script>

<style scoped>
.consumer-img {
    width: 100%;
    height: 80px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
}
.handle-input {
    width: 300px;
    display: inline-block;
}
.pagination {
    display: flex;
    justify-content: center;
}
</style>