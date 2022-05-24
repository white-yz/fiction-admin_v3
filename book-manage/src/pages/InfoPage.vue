<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <!-- 读者总数 -->
            <el-col :span="8">
                <el-card class="card">
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{consumerCount}}</div>
                            <div>{{$t('common.consumerCount')}}</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <!-- 书籍总数 -->
            <el-col :span="8">
                <el-card class="card">
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{bookCount}}</div>
                            <div>{{$t('common.bookCount')}}</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <!-- 作者总数 -->
            <el-col :span="8">
                <el-card class="card">
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{writerCount}}</div>
                            <div>{{$t('common.writerCount')}}</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 统计图表 -->
        <el-row :gutter="20" class="mgb20">
            <!-- 读者性别比例 -->
            <el-col :span="12" >
                <h3 class="mgb20 txt">{{$t('common.t1')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-pie :data="consumerSex" :theme="options"></ve-pie>
                </div>
            </el-col>
            <!-- 作家性别比例 -->
            <el-col :span="12">
                <h3 class="mgb20 txt">{{$t('common.t3')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-ring
                    :legend-visible="true"
                    :extend="sexExtend"
                    :resizeable="true"
                    :settings="sexSettings"
                    :data="sexData"
                    />
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mgb20">
            <!-- 书籍类型分布 -->
            <el-col :span="12">
                <h3 class="mgb20 txt">{{$t('common.t2')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-histogram :data="bookStyle" :theme="options1"></ve-histogram>
                </div>
            </el-col>
            <!-- 作家地区分布 -->
            <el-col :span="12">
                <h3 class="mgb20 txt">{{$t('common.t4')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-histogram :data="country" :theme="options2"></ve-histogram>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mgb20">
            <!-- 读者年龄段比例 -->
            <el-col :span="12" >
                <h3 class="mgb20 txt">{{$t('common.t5')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-pie :data="year" :theme="options3"></ve-pie>
                </div>
            </el-col>
            <!-- 作家年龄段分布及排序 -->
            <el-col :span="12" >
                <h3 class="mgb20 txt">{{$t('common.t6')}}</h3>
                <div style="background-color:white" class="p20 card">
                    <ve-bar class="barStyle"
                        :data="areaData"
                        :legend-visible="false"
                        :settings="areaSettings"
                        :extend="areaExtend"
                        :grid="{ containLabel: true, right: 40, top: 30, left: 60 }"
                    />
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {getAllConsumer,getAllBook,getAllWriter} from '../api/index';
export default {
    data(){
        /* 作家性别圆环 */
        this.sexSettings = {
            dimension: "talkDuration",
            metrics: "总数",
            // radius: ["60px", "100px"],  //圆环内径和外径，可以用px单位
            radius: ["30%", "50%"],  //之所以注释掉换成百分比，是为了做屏幕适配，使图表在大屏小屏电脑上能自动变大和变小
        };
        /* 作家性别图例 */
        this.sexExtend = {
            legend: {
                show: true,  //显示图例
                orient: "horizontal",  //使图例横向排列
                icon: "circle",  //图例的标识改成圆点
                bottom: 0,  //距离底部0
                textStyle:{color: '#888888'},//图例字颜色
                itemGap: 20,  //图例各个元素之间的距离为20
                formatter: (name) => {   //自定义图例上的文字格式
                    let param = "";
                    if (this.sexData) {
                        param = this.sexData.rows.find((item) => {
                        return item["talkDuration"] === name;
                        })["总数"];
                    }
                    return name + "   " + param + "   " ;
                },
            },
            series: {
            center: ["50%", "50%"],  //图表在容器的位置
            label: {
                normal: {
                    show: false,
                    },
                },
            },
            color: ["#D55969", "#4747A5", "#19D4AE","#FFB980"],  //改变环形每个区域的颜色
        };
        /* 作家年龄 */
        this.areaSettings = {
            dimension: ["talkDuration"],
            metrics: ["总数"],
            dataOrder: {   //按照由大到小进行排序
                label: "总数",
                order: "desc",
            },
            itemStyle: {
                normal: {   //这里没有使用百分数的单位，所以%是自己加上去的
                    label: { show: true, position: "right",},
                },
            },
            max: [100, 100],
        };
        /* 作家年龄图例 */
        this.areaExtend = {
            textStyle:{color: '#888888'},//图例字颜色
            yAxis: {
                axisLabel: {
                    textStyle: {
                        color: "#888888", // y轴字体颜色
                        padding: [10, 15, 5, 6], // 上右下左
                    },
                },
            },
            // 每个柱子
            series(v) {
            // console.log("v", v);
            // 设置柱子的样式
                v.forEach((i) => {
                    i.barWidth = 45;  //柱子高度
                    i.color = '#D55969' //柱子颜色
                });
                return v;
            },
        };

        return {
            consumerCount: 0,       //读者总数
            bookCount: 0,           //书籍总数
            writerCount: 0,         //作家总数
            consumer: [],           //所有读者
            consumerSex:{           //按性别分类的读者数
                columns: ['性别','总数'],
                rows: [
                    {'性别':'女','总数': 0},
                    {'性别': '男','总数': 0}
                ]
            },
            year:{//读者年龄
                columns: ['年份','总数'],
                rows: [
                    {'年份':'50后','总数': 0,'标准': '195'},
                    {'年份':'60后','总数': 0,'标准': '196'},
                    {'年份':'70后','总数': 0,'标准': '197'},
                    {'年份':'80后','总数': 0,'标准': '198'},
                    {'年份':'90后','总数': 0,'标准': '199'},
                    {'年份':'00后','总数': 0,'标准': '200'},
                    {'年份':'10后','总数': 0,'标准': '201'},
                    {'年份':'其他','总数': 0},
                ]
            },
            options: {//读者性别样式
                color: ['#D55969','#4747A5'],//图颜色
                textStyle:{color: '#888888'},//图中字颜色
                legend: {
                    textStyle:{color: '#888888'},//图例字颜色
                }
            },
            options1: {//书籍类型
                color:["#4747A5"],//图色
                textStyle:{color: '#888888'},//图中字颜色
                legend: {
                    textStyle:{color: '#888888'},//图例字颜色
                }
            },
            options2: {//作家地区样式
                color: ['#D55969'],//图颜色
                textStyle:{color: '#888888'},//图中字颜色
                legend: {
                    textStyle:{color: '#888888'},//图例字颜色
                }
            },
            options3: {//读者年龄样式
                // color: ['#D55969'],//图颜色
                // textStyle:{color: '#888888'},//图中字颜色
                legend: {
                    textStyle:{color: '#888888'},//图例字颜色
                }
            },
            bookStyle:{//按书籍风格分类
                columns: ['风格','总数'],
                rows: [
                    {'风格': '武侠仙侠','总数': 0},
                    {'风格': '历史军事','总数': 0},
                    {'风格': '都市生活','总数': 0},
                    {'风格': '宫斗权谋','总数': 0},
                    {'风格': '科幻游戏','总数': 0},
                    {'风格': '悬疑灵异','总数': 0},
                    {'风格': '青春校园','总数': 0},
                    {'风格': '其他','总数': 0},
                    // {'风格': '','总数': 0}
                ]
            },
            country:{
                columns: ['国籍','总数'],
                rows: [
                    {'国籍': '上海','总数': 0},
                    {'国籍': '山东','总数': 0},
                    {'国籍': '成都','总数': 0},
                    {'国籍': '重庆','总数': 0},
                    {'国籍': '北京','总数': 0},
                    {'国籍': '浙江','总数': 0},
                    {'国籍': '广东','总数': 0},
                    {'国籍': '厦门','总数': 0}
                ]
            },
            sexData: {
                rows: [
                    { 'talkDuration': '女', '总数': 0 },
                    { 'talkDuration': '男', '总数': 0 },
                    { 'talkDuration': '组合', '总数': 0 },
                    { 'talkDuration': '不明', '总数': 0 }
                ],
            },
            areaData: {//作家年龄段分布
                rows: [
                    { 'talkDuration': '50后', '总数': 0 ,'标准': '195'},
                    { 'talkDuration': '60后', '总数': 0 ,'标准': '196'},
                    { 'talkDuration': '70后', '总数': 0 ,'标准': '197'},
                    { 'talkDuration': '80后', '总数': 0 ,'标准': '198'},
                    { 'talkDuration': '90后', '总数': 0 ,'标准': '199'},
                    { 'talkDuration': '00后', '总数': 0 ,'标准': '200'},
                    { 'talkDuration': '10后', '总数': 0 ,'标准': '201'},
                    { 'talkDuration': '其他', '总数': 0 },
                ],
            },
        }
    },
    mounted() {
        this.getConsumer();
        this.getBook();
        this.getWriter();
    },
    methods: {
        getConsumer() {  //读者总数
            getAllConsumer().then(res => {//调用接口函数获取所有读者
                this.consumer = res;
                this.consumerCount = res.length;//读者数量
                this.consumerSex.rows[0]['总数'] = this.setSex(0,this.consumer);//女读者
                this.consumerSex.rows[1]['总数'] = this.setSex(1,this.consumer);//男读者
                for(let item of res){
                    this.getYear(item.birth.slice(0,4));
                }
                this.year.rows[7]['总数'] -= res.length * 7;
            })
        },
        getYear(birth){//计算读者年龄
            for(let item of this.year.rows){
                if(birth.includes(item['标准'])){
                    item['总数']++;
                }else{
                    this.year.rows[7]['总数']++;
                }
            }
        },
        getYears(birth){//计算作者年龄
            for(let item of this.areaData.rows){
                if(birth.includes(item['标准'])){
                    item['总数']++;
                }else{
                    this.areaData.rows[7]['总数']++;
                }
            }
        },
        setSex(sex,val) {//根据性别获取读者数
            let count = 0;
            for(let item of val){
                if(sex == item.sex){
                    count++;
                }
            }
            return count;
        },
        getBook() {//书籍总数
            getAllBook().then(res => {
                this.bookCount = res.length;
                for(let item of res){
                    this.getByStyle(item.style);
                }
            })
        },
        getWriter() {//作家总数
            getAllWriter().then(res => {
                this.writerCount = res.length;
                this.sexData.rows[0]['总数'] = this.setSex(0,res);//女
                this.sexData.rows[1]['总数'] = this.setSex(1,res);//男
                this.sexData.rows[2]['总数'] = this.setSex(2,res);//组合
                this.sexData.rows[3]['总数'] = this.setSex(3,res);//不明
                for(let item of res){
                    this.getByCountry(item.location);
                    this.getYears(item.birth.slice(0,4));
                }
                this.areaData.rows[7]['总数'] -= res.length * 7;
            })
        },
        getByStyle(style) {//根据书籍风格获取数量
            for(let item of this.bookStyle.rows){
                if(style.includes(item['风格'])){
                    item['总数']++;
                }
            }
        },
        getByCountry(location) {//根据国籍获取数量
            for(let item of this.country.rows){
                if(location.includes(item['国籍'])){
                    item['总数']++;
                }
            }
        },
    }
}
</script>

<style scoped>
.grid-content {
    display: flex;
    align-items: center;
    height: 50px;
}

.grid-cont-center {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #4747A5;
}
.mgb20 {
    color: #888888 !important;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
    color: #D55969;
}
.card{
    border: none;
}
.dark .card{
    background-color: #141414 !important;
}
.dark .txt{
    color: #dfdada;
}
.p20{
    padding: 20px;
}
</style>
