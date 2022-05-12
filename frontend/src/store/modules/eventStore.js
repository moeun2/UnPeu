export const eventStore = {
    namespaced: true,
    state: {
        month: 0,
        date: 0
    },
    getters: {
        GET_MONTH(state) {
            return state.month;
        },
        GET_DATE(state) {
            return state.date;
        }
    },
    mutations: {
        SET_MONTH(state, month) {
            state.month = month;
        },
        SET_DATE(state, date) {
            state.date = date;
        }
    }
}