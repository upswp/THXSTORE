module.exports = {
  devServer: {
    overlay: false,
    historyApiFallback: true,
  },
  lintOnSave: false,
  css: {
    loaderOptions: {
      sass: {
        additionalData: '@import "@/assets/scss/base/main.scss";',
      },
    },
  },
  transpileDependencies: ['swiper'],
};
