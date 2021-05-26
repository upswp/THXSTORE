<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import OvVideo from '@/components/live/OvVideo';

export default {
  name: 'UserVideo',

  components: {
    OvVideo,
  },

  props: {
    streamManager: {
      type: Object,
      default: () => {},
    },
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
