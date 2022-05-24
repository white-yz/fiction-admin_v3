<template>
    <div class="table">
        <div class="container" style="margin-bottom:20px;">
            <div class="handle-box">
                <el-input v-model="select_word" size="small" :placeholder="$t('writer.enterName')" class="handle-input" style="float: right;"></el-input>
                <el-button-group>
                    <el-button type="primary" style="background-color: #D55969; border: #D55969;" class="iconfont icon-piliangshanchu" size="small" @click="delAll">{{$t('common.batchDel')}}</el-button>
                    <el-button type="primary" style="background-color: #4747A5; border: #4747A5 none;" icon="el-icon-plus" size="small" @click="centerDialogVisible = true">{{$t('writer.addWriter')}}</el-button>
                </el-button-group>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" style="width:100%" height="700px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="42"></el-table-column>
            <el-table-column :label="$t('writer.pic')" width="110" align="center">
                <template slot-scope="scope">
                    <div class="writer-img">
                        <img :src="getUrl(scope.row.pic)" alt="" style="width: 100px;">
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
                        <el-button size="mini" type="primary" plain>{{$t('common.updatePic')}}</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="username" :label="$t('user.Name')" width="120" align="center"></el-table-column>
            <el-table-column prop="name" :label="$t('writer.writerName')" width="120" align="center"></el-table-column>
            <el-table-column :label="$t('common.sex')" width="70" align="center">
                <template slot-scope="scope">
                    {{changeSex(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.birth')" width="120" align="center">
                <template slot-scope="scope">
                    {{attachBirth(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="location" :label="$t('common.location')" width="100" align="center"></el-table-column>
            <el-table-column :label="$t('common.introduction')">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:auto;">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.state')" width="120" align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.state == 0">正常</el-tag>
                    <el-tag type="danger" v-else>已冻结</el-tag>
                </template>
            </el-table-column>
            <el-table-column :label="$t('common.operation')" width="230" align="center">
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" @click="bookEdit(scope.row.id,scope.row.name)" icon="el-icon-document" circle></el-button>
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
        <el-dialog :title="$t('writer.addWriter')" :visible.sync="centerDialogVisible" width="550px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="95px" :rules="rules">
                <el-form-item prop="username" :label="$t('user.Name')" size="mini">
                    <el-input v-model="registerForm.username" :placeholder="$t('user.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="password" :label="$t('common.pwd')" size="mini">
                    <el-input v-model="registerForm.password" :placeholder="$t('common.pwd')"></el-input>
                </el-form-item>
                <el-form-item prop="name" :label="$t('writer.writerName')" size="mini">
                    <el-input v-model="registerForm.name" :placeholder="$t('writer.writerName')"></el-input>
                </el-form-item>
                <el-form-item prop="sex" :label="$t('common.sex')" size="mini">
                    <el-radio-group v-model="registerForm.sex">
                        <el-radio :label="0">{{$t('common.woman')}}</el-radio>
                        <el-radio :label="1">{{$t('common.man')}}</el-radio>
                        <el-radio :label="2">{{$t('common.combination')}}</el-radio>
                        <el-radio :label="3">{{$t('common.unknown')}}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birth" :label="$t('common.birth')" size="mini">
                    <el-date-picker type="date" :placeholder="$t('common.selectDate')" v-model="registerForm.birth" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" :label="$t('common.location')" size="mini">
                    <el-select v-model="registerForm.location" :placeholder="$t('common.location')" style="width: 100%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.introduction')" size="mini">
                    <el-input v-model="registerForm.introduction" :placeholder="$t('common.introduction')" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="addWriter" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 编辑窗口 -->
        <el-dialog :title="$t('writer.editWriter')" :visible.sync="editVisible" width="550px" center>
            <el-form :model="form" ref="form" label-width="95px" :rules="rules">
                <el-form-item prop="username" :label="$t('user.Name')" size="mini">
                    <el-input v-model="form.username" :placeholder="$t('user.Name')"></el-input>
                </el-form-item>
                <el-form-item prop="password" :label="$t('common.pwd')" size="mini">
                    <el-input type="password" v-model="form.password" :placeholder="$t('common.pwd')"></el-input>
                </el-form-item>
                <el-form-item prop="name" :label="$t('writer.writerName')" size="mini">
                    <el-input v-model="form.name" :placeholder="$t('writer.writerName')"></el-input>
                </el-form-item>
                <el-form-item prop="sex" :label="$t('common.sex')" size="mini">
                    <el-radio-group v-model="form.sex">
                        <el-radio :label="0">{{$t('common.woman')}}</el-radio>
                        <el-radio :label="1">{{$t('common.man')}}</el-radio>
                        <el-radio :label="2">{{$t('common.combination')}}</el-radio>
                        <el-radio :label="3">{{$t('common.unknown')}}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birth" :label="$t('common.birth')" size="mini">
                    <el-date-picker type="date" :placeholder="$t('common.selectDate')" v-model="form.birth" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" :label="$t('common.location')" size="mini">
                    <el-select v-model="form.location" :placeholder="$t('common.location')" style="width: 100%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" :label="$t('common.introduction')" size="mini">
                    <el-input v-model="form.introduction" :placeholder="$t('common.introduction')" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="editSave" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>
        <!-- 删除窗口 -->
        <el-dialog :title="$t('writer.delWriter')" :visible.sync="delVisible" width="300px" center>
            <div align="center">{{$t('common.delText')}}</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">{{$t('common.cancel')}}</el-button>
                <el-button size="mini" @click="deleteRow" type="primary">{{$t('common.confirm')}}</el-button>
            </span>
        </el-dialog>

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
import { getAllWriter, setWriter, updateWriter, delWriter, updateWriterState } from '../api/index'
import { mixin } from '../mixins/index'
import { cities, rules } from '../assets/data/form'
export default {
    mixins: [mixin],
    data() {
        return {
            centerDialogVisible: false, //添加弹窗是否显示
            editVisible: false, //编辑弹窗是否显示
            delVisible: false, //删除弹窗是否显示
            registerForm: {
                username: '',
                password: '',
                //添加框
                name: '',
                sex: 0,
                birth: '',
                location: '',
                introduction: ''
            },
            form: {
                //编辑框
                id: '',
                username:'',
                password:'',
                name: '',
                sex: '',
                birth: '',
                location: '',
                introduction: ''
            },
            state: 0,
            freezeVisible: false,
            cities: cities,
            rules: rules,//表单校验规则
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 5, //分页每页大小
            currentPage: 1, //当前页
            idx: -1, //当前选择项
            multipleSelection: [] //哪些项已经打勾
        }
    },
    computed: {
        //计算当前搜索结果表里的数据
        data() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
        }
    },
    watch: {
        select_word: function () {
            if (this.select_word == '') {
                this.tableData = this.tempData
            } else {
                this.tableData = []
                for (let item of this.tempData) {
                    if (item.name.includes(this.select_word)) {
                        this.tableData.push(item)
                    }
                }
            }
        }
    },
    created() {
        this.getData()
    },
    methods: {
        //查询所有作家
        getData() {
            this.tempData = []
            this.tableData = []
            getAllWriter().then((res) => {
                this.tempData = res
                this.tableData = res
                console.log(this.tableData[0].id)
            })
        },
        //添加作家
        addWriter() {
            let d = this.registerForm.birth
            let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
            let params = new URLSearchParams()
            params.append('username', this.registerForm.username)
            params.append('password', this.registerForm.password)
            params.append('name', this.registerForm.name)
            params.append('sex', this.registerForm.sex)
            params.append('pic', '/img/default.jpg')
            params.append('birth', datetime)
            params.append('location', this.registerForm.location)
            params.append('introduction', this.registerForm.introduction)
            setWriter(params)
                .then((res) => {
                    if (res.code == 1) {
                        this.getData()
                        this.notify(res.msg, 'success')
                        this.centerDialogVisible = false//关闭窗口
                        //清楚表单内容
                        this.registerForm.username = null
                        this.registerForm.password = null
                        this.registerForm.name = null
                        this.registerForm.sex = 0
                        this.registerForm.datetime = null
                        this.registerForm.location = null
                        this.registerForm.introduction = null
                    } else {
                        this.notify(res.msg, 'error')
                    }
                })
                .catch((err) => {
                    console.log(err)
                })
            // this.centerDialogVisible = false
        },
        //更新图片
        uploadUrl(id) {
            return `${this.$store.state.HOST}/writer/updateWriterPic?id=${id}`
        },
        //获取当前页
        handleCurrentChange(val) {
            this.currentPage = val
        },
        handleSizeChange(val) {
            this.pageSize = val
        },
        //弹出编辑页面
        handleEdit(row) {
            this.editVisible = true
            this.form = {
                id: row.id,
                username: row.username,
                password: row.password,
                name: row.name,
                sex: row.sex,
                birth: row.birth,
                location: row.location,
                introduction: row.introduction
            }
        },
        //保存编辑页面修改的数据
        editSave() {
            let d = new Date(this.form.birth)
            let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
            let params = new URLSearchParams()
            params.append('id', this.form.id)
            params.append('username', this.form.username)
            params.append('password', this.form.password)
            params.append('name', this.form.name)
            params.append('sex', this.form.sex)
            params.append('birth', datetime)
            params.append('location', this.form.location)
            params.append('introduction', this.form.introduction)
            updateWriter(params)
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
        },
        //删除一个作者
        deleteRow() {
            delWriter(this.idx)
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
        //转向书籍管理页面
        bookEdit(id, name) {
            this.$router.push({ path: `/Book`, query: { id, name } })
        },
        //修改用户状态
        handleState(row) {
            this.freezeVisible = true
            this.state = row.state
            this.id = row.id
        },
        handleStateUp() {
            let params = new URLSearchParams()
            params.append('id', this.id)
            params.append('state', this.state == 1 ? 0 : 1)
            updateWriterState(params)
                .then((res) => {
                    if (res) {
                        this.getData()
                        this.notify(this.state == 1 ? '解冻成功' : '冻结成功', 'success')
                    } else {
                        this.notify(this.state == 1 ? '解冻失败' : '冻结失败', 'error')
                    }
                })
                .catch((err) => {})
            this.freezeVisible = false
        }
    }
}
</script>

<style scoped>
.writer-img {
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