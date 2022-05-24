import {mapGetters} from 'vuex';
import {bookOfName,getCollectOfUserId } from '../api/index';
export const mixin = {
    computed: {
        ...mapGetters([            
            'loginIn',              //用户是否已登录
            'userId',               //当前登录用户的id
            'userType',
            'vip'
        ])
    },
    computed: {
        "计算属性名" () {
            return "值"
        }
    },
    methods: {
        //提示信息
        notify(title,type) {
            this.$notify({
                title: title,
                type: type
            })
        },
        //获取图片地址
        attachImageUrl (srcUrl) {
            return srcUrl? this.$store.state.configure.HOST+srcUrl : '../assets/img/user.jpg';
        },
        //根据相对地址获取绝对地址
        getUrl(url){
            return `http://127.0.0.1:8888/${url}`
        },
        //根据书名模糊查询
        getBook() {
            if(!this.$route.query.keywords){
                this.$store.commit('setlistOfBooks',[]);
                this.notify('您输入的内容为空','warning');
            }else{
                let isCharge = this.$store.state.user.vip ? 1 : 0
                bookOfName(this.$route.query.keywords,isCharge).then(res => {
                    if(!res.length){
                        this.$store.commit('setlistOfBooks',[]);
                        this.notify('系统暂无符合条件的书籍','warning');
                    }else{
                        this.$store.commit('setlistOfBooks',res);
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        //随机数组中随机取几个元素
        getRandomArrayElements(arr, count) {
            var shuffled = arr.slice(0), i = arr.length, min = i - count, temp, index;
            while (i-- > min) {
                index = Math.floor((i + 1) * Math.random());
                temp = shuffled[index];
                shuffled[index] = shuffled[i];
                shuffled[i] = temp;
            }
            return shuffled.slice(min);
        },
        //获取生日
        attachBirth(val){
            return val.substr(0,10);
        },
           //上传图片之前的校验
           beforeAvatorUpload(file){
            const isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png');
            if(!isJPG){
                this.$message.error('上传头像图片只能是jpg或png格式');
                return false;
            }
            const isLt2M = (file.size / 1024 /1024) < 2;
            if(!isLt2M){
                this.$message.error('上传头像图片大小不能超过2MB');
                return false;
            }
            return true;
        },
        //上传图片成功之后
        handleAvatorSuccess(res){
            let _this = this;
            if(res.code == 1){
                _this.getData();
                _this.$message({
                    message: '上传成功',
                    type: 'success'
                });
            }else{
                _this.$message({
                    message: '上传失败',
                    type: 'error'
                });
            }
        }
    }
}