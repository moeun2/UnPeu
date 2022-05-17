import * as api from "@/api/user";
import router from "../../router";
import dayjs from "dayjs";
export const userStore = {
  namespaced: true,

  state: {
    user: null,
    currentUser: {
      id: "",
      permission: "",
      category: "",
      selectedDate: "",
    },
  },

  getters: {
    getCurUser(state) {
      // console.log("0:User/1:Guest(Logined User)/2:Guest")
      return state.currentUser;
    },
    getUser(state) {
      return state.user;
    },
  },

  mutations: {
    setUser(state, user) {
      state.user = user;
      // console.log(state.user);
    },

    setUSerNull(state) {
      state.user = null;
    },

    MU_CUR_USER_ID(state, userId) {
      state.currentUser.id = userId;
    },

    MU_CUR_USER_PERMISSION(state, permission) {
      state.currentUser.permission = permission;
    },

    MU_CUR_USER_CONCEPT(state, data) {
      state.currentUser.category = data.category;
      state.currentUser.selectedDate = data.selectedDate;
      // console.log("MU_CUR_USER_CONCEPT : ", state.currentUser);
    },
    MU_USER_DETAIL(state, user) {
      state.user = user;
    },
  },

  actions: {
    /* 컨셉 등록 or 수정 */
    AC_UPDATE_CONCEPT({ commit }, data) {
      api.updateUserConcept(
        data,
        (response) => {
          console.log("AC_UPDATE_CONCEPT : ", response.data);
          const data = {
            category: response.data.User.category,
            selectedDate: dayjs(
              response.data.User.selectedDate.join("-")
            ).format("YYYY-MM-DD"),
          };
          commit("MU_CUR_USER_CONCEPT", data);
        },
        (err) => {
          console.log(err);
        }
      );
    },

    AC_USER_DETAIL({ commit, dispatch }, userId) {
      console.log("AC_USER_DETAIL-호출");
      api.getUserDetail(
        userId,
        (response) => {
          console.log("AC_USER_DETAIL", response.data);

          let today = null;
          if (response.data.User.selectedDate != null) {
            today = dayjs(response.data.User.selectedDate.join("-")).format(
              "YYYY-MM-DD"
            );
          }
          const data = {
            category: response.data.User.category,
            selectedDate: today,
          };
          commit("MU_CUR_USER_ID", userId);
          commit("MU_CUR_USER_CONCEPT", data);
          dispatch("AC_USER_PERMISSION", userId);
          dispatch("AC_CHECK_CONCEPT", userId);
        },
        (err) => {
          console.log(err);
          if (err.response.status == 400) router.push({ name: "*" });
        }
      );
    },
    AC_USER_PERMISSION({ commit, dispatch, state }, userId) {
      // console.log("AC_USER_PERMISSION-호출");
      if (localStorage.getItem("accessToken")) {
        dispatch("AC_USER_TOKEN_DETAIL").then(() => {
          //console.log("AC_USER_PERMISSION",state.user)

          let compareId = state.user.id;
          if (compareId != userId) {
            commit("MU_CUR_USER_PERMISSION", 1); //login user
          } else {
            commit("MU_CUR_USER_PERMISSION", 0); //user
          }
          // console.log("AC_USER_PERMISSION", state.currentUser);
        });
      } else {
        commit("MU_CUR_USER_PERMISSION", 2); //guest
      }
    },
    AC_USER_TOKEN_DETAIL({ commit }) {
      // console.log("AC_USER_TOKEN_DETAIL- 호출");
      api.getUserDetailUseToken(
        localStorage.getItem("accessToken"),
        (response) => {
          // console.log("AC_USER_TOKEN_DETAIL", response.data);
          commit("MU_USER_DETAIL", response.data.User);
        },
        (err) => {
          console.log(err);
        }
      );
    },
    AC_CHECK_CONCEPT({ state }) {
      // console.log("AC_CHECK_CONCEPT-호출")
      let today = dayjs().format("YYYY-MM-DD");
      let concept = state.currentUser.category;
      let selectedDate = state.currentUser.selectedDate;
      // console.log("selectedDate : ", selectedDate);
      // console.log("today : ", this.today);
      // console.log("CheckConcept-Concept : ", concept);
      switch (concept) {
        case "default":
          if (selectedDate <= today) {
            document.documentElement.setAttribute(
              "color-theme",
              "default-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "default-close"
            );
          }
          break;
        case "birthday":
          if (selectedDate <= today) {
            document.documentElement.setAttribute(
              "color-theme",
              "birthday-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "birthday-close"
            );
          }
          break;
        case "children":
          if (selectedDate <= today) {
            document.documentElement.setAttribute(
              "color-theme",
              "children-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "children-close"
            );
          }
          break;
        default:
          console.log(concept);
          console.log("어떤 값인지 파악이 되지 않습니다.");
      }
    },
  },
};
