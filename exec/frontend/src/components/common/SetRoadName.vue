<template>
  <div>
    <div class="location--overlay"></div>
    <section v-click-outside="closeModal" class="modal--container">
      <header class="modal--head">
        <awesome icon="times" @click="closeModal"></awesome>
        <div><slot>현재 위치 설정</slot></div>
      </header>
      <hr />
      <article class="daum--post-wrapper">
        <div ref="embed" class="embed--search"></div>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  mounted() {
    new window.daum.Postcode({
      oncomplete: data => {
        this.sendNewAddress(data.roadAddress);
      },
      width: '100%',
    }).embed(this.$refs.embed);
  },
  methods: {
    closeModal() {
      this.$emit('newAddress', '');
    },
    sendNewAddress(value) {
      this.$emit('newAddress', value);
    },
  },
};
</script>

<style lang="scss" scoped>
.location--overlay {
  position: fixed;
  z-index: 10;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  transition: opacity 0.3s ease;
}
.modal--container {
  position: fixed;
  z-index: 20;
  width: clamp(320px, 50%, 500px);
  margin: 0 auto;
  background-color: white;
  padding: 10px 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-sizing: border-box;
}
.modal--head {
  position: relative;
  svg {
    position: absolute;
    top: 0px;
    right: 2px;
    cursor: pointer;
  }
  div {
    text-align: center;
  }
}
.daum--post--wrapper {
  border: 1px solid;
  width: 100%;
  height: 300px;
  margin: 5px 0px;
  position: relative;
}
</style>
