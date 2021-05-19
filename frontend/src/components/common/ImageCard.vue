<template>
  <div
    class="card"
    :style="{
      height: tH + 'px',
      gridRowEnd: gap,
      backgroundColor: 'gray',
      borderRadius: '10px',
    }"
  >
    <div class="image">
      <img :src="image" class="image" :style="{ width: '100%' }" />
    </div>
  </div>
</template>
<script>
export default {
  props: {
    image: {
      type: String,
      required: true,
    },
    th: {
      type: Number,
      required: true,
    },
  },

  created() {
    const gap = Math.round(this.tH / 10);
    this.gap = `span ${gap}`;
  },
};
</script>
