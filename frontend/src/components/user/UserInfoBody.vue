<template>
  <section ref="container" class="user-section">
    <article>
      <div ref="currentStore" class="box">a</div>
      <div ref="recentReviews" class="box">b</div>
      <div ref="profile" class="box">
        <user-profile></user-profile>
      </div>
      <div ref="resetPassword" class="box">d</div>
      <div ref="registerProductor" class="box">e</div>
    </article>
  </section>
</template>

<script>
import UserProfile from '@/components/user/main/UserProfile';
export default {
  components: {
    UserProfile,
  },
  props: {
    nav: {
      type: String,
      default: 'a',
    },
  },
  watch: {
    nav(newValue, oldValue) {
      this.$refs.container.scrollTo({
        top: this.$refs[newValue].offsetTop - 150,
        behavior: 'smooth',
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
