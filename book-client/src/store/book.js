const book = {
    state:{
        listOfBooks: [],     //当前书籍列表
        tempList: {},               //单个书籍信息或作家信息
    },
    getters: {
        listOfBooks: state => {
            let listOfBooks = state.listOfBooks;
            if(!listOfBooks.length){
                listOfBooks = JSON.parse(window.sessionStorage.getItem('listOfBooks')||null); 
            }
            return listOfBooks;
        },
    },
    mutations: {
        setlistOfBooks: (state,listOfBooks) => {
            state.listOfBooks = listOfBooks;
            window.sessionStorage.setItem('listOfBooks',JSON.stringify(listOfBooks));
        },
        setTempList: (state,tempList) => {
            state.tempList = tempList;
            window.sessionStorage.setItem('tempList',JSON.stringify(tempList));
        },
    }
}

export default book