import {get,post} from "./http";

//============作家相关================
//查询作家
export const getAllWriter = () => get(`writer/allWriter`);
//根据作家id查询作家
export const writerOfWriterId =(id) => get(`writer/selectByPrimaryKey?id=${id}`);
//根据书名模糊查询
export const writerOfName =(keywords) => get(`writer/writerOfName?name=${keywords}`);
//根据性别查询歌手
export const getWriterOfSex = (sex) => get(`writer/writerOfSex?sex=${sex}`);
//作者注册
export const authorSignUp =(params) => post(`writer/add`,params);
//作者修改
export const updateWriterMsg =(params) => post(`writer/update`,params);
//成为VIP
export const updateWriterVip =(params) => post(`/writer/update/vip`,params);
//============书籍相关================
//根据作家id查询书籍
export const bookOfWriterId =(id) => get(`book/writer/detail?writerId=${id}`);
//根据书籍id查询书籍对象
export const bookOfBookId =(id) => get(`book/detail?bookId=${id}`); 
//根据书籍id查询书籍目录
export const directoryOfBookId =(id) => get(`directory/book/detail?bookId=${id}`);
//根据书名模糊查询
export const bookOfName =(keywords, isCharge) => get(`book/bookOfName?name=${keywords}&isCharge=${isCharge}`);
//查询所有书籍
export const getAllBook =() => get(`book/allBook`);
//查询所有收费书籍
export const getAllBookOfCharge =(isCharge) => get(`book/isChargeBook?isCharge=${isCharge}`);
//根据目录id查询
export const bookContent =(id) => get(`directory/detail?id=${id}`); 
//删除书籍
export const delBook =(id) => get(`book/delete?id=${id}`); 
export const batchDelBook =(id) => get(`book/delete/batch?ids=${id}`); 
//根据风格模糊查询歌单列表
export const getBookListOfLikeStyle = (style, isCharge) => get(`book/likeStyle?style=${style}&isCharge=${isCharge}`);
//添加书籍 
export const setBook = (params) => post(`book/add`,params);
//编辑书籍
export const updateBook = (params) => post(`book/update`,params);
//============目录相关================
//添加目录
export const setDirectory = (params) => post(`directory/add`,params);
//查询目录
export const getAllDirectory = () => get(`directory/allDirectory`);
//编辑目录
export const updateDirectory = (params) => post(`directory/update`,params);
//删除目录
export const delDirectory = (id) => get(`directory/delete?id=${id}`);

//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/allConsumer`);
//注册
export const SignUp =(params) => post(`/consumer/add`,params);
//登录
export const loginIn =(params) => post(`/consumer/login`,params);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectByPrimaryKey?id=${id}`);
//更新用户信息
export const updateUserMsg =(params) => post(`/consumer/update`,params);
//成为VIP
export const updateUserVip =(params) => post(`/consumer/update/vip`,params);

//===========评价======================
//提交评分
export const setRank =(params) => post(`/rank/add`,params);
//获取指定书籍的平均分
export const getRankOfBookID = (bookId) => get(`/rank?bookId=${bookId}`);
//获取指定书籍的评分人数
export const getRankOfSum = (bookId) => get(`/rank/sum?bookId=${bookId}`);

//===========评论======================
//提交评论
export const setComment =(params) => post(`/comment/add`,params);
//点赞
export const setLike =(params) => post(`/comment/like`,params);
//删除评论
export const delComment =(params) => get(`/comment/delete`,params);
//返回当前书籍的评论列表
export const getAllComment = (id) => {return get(`/comment/commentOfBookId?bookId=${id}`);}

//===============收藏===================
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
//删除收藏
export const delCollect =(params) => get(`/collect/delete`,params);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);
//返回当前书籍的收藏列表
export const getAllCollect = (id) => {return get(`/collect/collectOfBookId?bookId=${id}`);}
//支付  process.env.HOST
export const payOrder = () => {return `http://localhost:8888/ali/pay`;}

//===============阅读记录===================
//新增收藏
export const setRecord =(params) => post(`/record/add`,params);