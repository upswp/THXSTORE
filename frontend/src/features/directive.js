const useDirective = Vue => {
  // v-focus
  // 자동으로 포커스 맞춰주기
  Vue.directive('focus', {
    inserted(el) {
      el.focus();
    },
  });
  // 요소 밖으로 클릭했을 경우에 발생
  Vue.directive('click-outside', {
    bind(el, binding, vNode) {
      // Provided expression must evaluate to a function.
      if (typeof binding.value !== 'function') {
        const compName = vNode.context.name;
        let warn = `[Vue-click-outside:] provided expression '${binding.expression}' is not a function, but has to be`;
        if (compName) {
          warn += `Found in component '${compName}'`;
        }

        console.warn(warn);
      }
      // Define Handler and cache it on the element
      const bubble = binding.modifiers.bubble;
      // const capture = binding.modifiers.capture;
      const handler = e => {
        if (bubble || (!el.contains(e.target) && el !== e.target)) {
          binding.value(e);
        }
      };
      el.__vueClickOutside__ = handler;

      // add Event Listeners
      document.addEventListener('click', handler, {
        capture: true,
      });
    },

    unbind(el) {
      // Remove Event Listeners
      document.removeEventListener('click', el.__vueClickOutside__);
      el.__vueClickOutside__ = null;
    },
  });

  // 사용자 디렉티브 계속 추가 가능
};

export { useDirective };
