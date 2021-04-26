<template>
  <div class="user-container">
    <nav class="user-nav">
      <div class="profile-summary">
        <div class="profile-img">
          <div class=""></div>
        </div>
        <div class="profile-button"></div>
        <div class="profile-info">profile-info</div>
      </div>
      <div class="nav-buttons">
        <button @click="clickButton('a')">currentReservations</button>
        <button @click="clickButton('b')">recentReviews</button>
        <button @click="clickButton('c')">profile</button>
        <button @click="clickButton('d')">resetPassword</button>
        <button @click="clickButton('e')">registerProductor</button>
      </div>
    </nav>
    <section class="user-section">
      <article>
        <div ref="a" class="box">a</div>
        <div ref="b" class="box">b</div>
        <div ref="c" class="box">c</div>
        <div ref="d" class="box">d</div>
        <div ref="e" class="box">e</div>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  methods: {
    clickButton(e) {
      // let node = this.$el.querySelector(`div[ref=${e}]`);
      // node.scrollIntoView(true, { behavior: 'smooth' });
      // this.$refs[e].scrollIntoView();
      // window.scrollTo(0, this.$refs[e].offsetTop);

      window.scrollTo(0, this.$refs[e].offsetTop);
      // let scrolledY = window.scrollY;
      // console.log(scrolledY);
      // if (scrolledY) window.scroll(0, scrolledY - 50);
    },
  },
};
</script>

<style lang="scss" scoped>
.user-container {
  min-height: 100vh;
  z-index: -1;
  .user-nav {
    position: fixed;
    width: 200px;
    min-height: 100vh;
    border: 1px solid $gray600;
    .profile-summary {
      .profile-img {
        @include flexbox;
        @include justify-content(center);
        & > div {
          width: 150px;
          height: 150px;
          border-radius: 50%;
          background: $gray400;
        }
        margin-bottom: 20px;
      }
      .profile-info {
        padding-left: 40px;
      }
    }
    .nav-buttons {
      padding-left: 40px;
      & > button {
        display: block;
        border: none;
        background-color: white;
        padding: 5px 0;
      }
    }

    @include mobile() {
      width: 100%;
      height: 100px;
      min-height: 0;
      .profile-summary {
        display: none;
      }
      .nav-buttons {
        @include flexbox;
        width: 100%;
      }
    }
  }
  .user-section {
    z-index: -1;
    top: 0px;
    left: 250px;
    position: relative;
    width: 100%;
    height: 100vh;
    @include mobile() {
      top: 100px;
      left: 0;
    }
    .box {
      border: 1px solid $gray600;
      height: 500px;
    }
  }
}
</style>
