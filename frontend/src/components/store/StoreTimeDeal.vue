<template>
  <div class="time-deal-container">
    <div class="tiem-deal-accordian">
      <ul>
        <li v-for="(value, index) in 4" :key="index">
          <label :for="`menu-item-${index}`" class="menu-item-label"> {{ index }}</label>
          <input :id="`menu-item-${index}`" v-model="active" type="radio" :value="index" class="menu-item-radio" />
          <div class="menu-content">Lorem, ipsum dolor.</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 0,
    };
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
