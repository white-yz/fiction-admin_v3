const configure = {
    state:{
        HOST: 'http://127.0.0.1:8888',  //后台访问地址根目录
        activeName: '首页',             //当前选中的菜单名
        loginIn: false,                 //是否登录
        top1: '',
        top2: '',
    },
    getters: {
        activeName: state => {
            let activeName = state.activeName
            if(!activeName){
                activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
            }
            return activeName
        },
        loginIn: state => {
            let loginIn = state.loginIn
            if(!loginIn){
                loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
            }
            return loginIn
        },
        isActive: state => {
            let isActive = state.isActive
            if(!isActive){
                isActive = JSON.parse(window.sessionStorage.getItem('isActive'))
            }
            return isActive
        },
        top1: state => {
            let top1 = state.top1
            if(!top1){
                top1 = JSON.parse(window.sessionStorage.getItem('top1'))
            }
            return top1
        }  ,
        top2: state => {
            let top2 = state.top2
            if(!top2){
                top2 = JSON.parse(window.sessionStorage.getItem('top2'))
            }
            return top2
        }
    },
    mutations: {
        setActiveName: (state,activeName) => {
            state.activeName = activeName
            window.sessionStorage.setItem('activeName',JSON.stringify(activeName))
        },
        setLoginIn: (state,loginIn) => {
            state.loginIn = loginIn
            window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn))
        },
        setIsActive: (state,isActive) => {
            state.isActive = isActive
            window.sessionStorage.setItem('isActive',JSON.stringify(isActive))
        },
        setTop1: (state,top1) => {
            state.top1 = top1
            window.sessionStorage.setItem('top1',JSON.stringify(top1))
        },
        setTop2: (state,top2) => {
            state.top2 = top2
            window.sessionStorage.setItem('top2',JSON.stringify(top2))
        }
    }
}

export default configure
