import QRCode from "qrcodejs2";
export const qr = {
  created() {
    this.$nextTick(() => {
      this.crateQrcode();
    });
  },
  methods: {
    crateQrcode() {
      this.qr = new QRCode("qrcode", {
        width: 150,
        height: 150,
        text: "如果给你寄一本书，我不会寄给你诗歌，我要一本关于植物，关于庄稼的，告诉你稻子和稗子的区别，告诉你一棵稗子，提心吊胆的春天",
        //colorDark: "#333333", //二维码颜色
        // colorLight: "#ffffff", //二维码背景色
        render: "canvas", // 设table / canvas 默认canvas
        correctLevel: QRCode.CorrectLevel.L
      });
    },
    cleanCode() {
        this.qr = null
        this.$refs.qrcode.innerHTML = "";
    }
  }
};
