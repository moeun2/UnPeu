<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <!-- <button class="modal-default-button" @click="$emit('close')">
            <i style="color: #505352" class="far fa-times-circle fa-2x"></i>
          </button> -->
          <div class="modal-header">
            <slot name="header"> 선물 선택 </slot>
          </div>

          <div class="modal-body">
            <img
              src="https://i.imgur.com/vaBFer6.png"
              alt=""
              class="modal-img"
            />
            <br />
            <p>
              헉 너무 큰 금액이야 <br />
              가격을 다시 한 번 생각해봐~!
            </p>
            <br />
            <table class="dabo_table">
              <tr>
                <td>
                  <input
                    id="radio-1"
                    type="radio"
                    name="radio"
                    value="10000"
                    v-model="selectDabo"
                  />
                  <label for="radio-1">10000원</label>
                </td>
                <td>
                  <input
                    id="radio-2"
                    type="radio"
                    name="radio"
                    value="20000"
                    v-model="selectDabo"
                  />
                  <label for="radio-2">20000원</label>
                </td>
                <td>
                  <input
                    id="radio-3"
                    type="radio"
                    name="radio"
                    value="30000"
                    v-model="selectDabo"
                  />
                  <label for="radio-3">30000원</label>
                </td>
              </tr>
              <tr>
                <td>
                  <input
                    id="radio-4"
                    type="radio"
                    name="radio"
                    value="40000"
                    v-model="selectDabo"
                  />
                  <label for="radio-4">40000원</label>
                </td>
                <td>
                  <input
                    id="radio-5"
                    type="radio"
                    name="radio"
                    value="50000"
                    v-model="selectDabo"
                  />
                  <label for="radio-5">50000원</label>
                </td>
                <td>
                  <input
                    id="radio-6"
                    type="radio"
                    name="radio"
                    value=""
                    v-model="selectDabo"
                  />
                  <label for="radio-6" @click="textbox">직접입력</label>
                  <div id="unpeu_input"></div>
                  <!-- <input
                    type="text"
                    id="amount"
                    name="amount"
                    placeholder="직접입력"
                    size="5"
                  /> -->
                </td>
              </tr>
            </table>

            <slot name="body"> </slot>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <div style="display: flex; justify-content: flex-end;">
              <v-btn depressed color="primary" style="margin-right : 5px;"
                >가격선택
              </v-btn>
              <v-btn depressed color="warning" @click="$emit('close')"
                >닫기
              </v-btn>
              </div>
            </slot>
            
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  methods: {
    textbox() {
      document.getElementById("unpeu_input").innerHTML =
        '<input type="text" name="" id="" size=5 style="background-color:white; text-align:center; border: 1px solid black; margin: 1px 1px 1px 1px" >원';
      document.getElementById("unpeu_input").style.display = "";
    },
    saveBtn() {
      alert("saveBtn");
    },
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
  width: 340px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  /* font-family: "Dancing Script", cursive; */
}
.modal-footer {
  font-family: sans-serif;

}
.modal-header {
  display: flex;
  align-items: center;
  justify-content: center !important;
}
.modal-header h3 {
  margin-top: 0;
  color: #505352;
  text-align: center;
  font-weight: bold;
}
.modal-body {
  margin: 10px 0;
  text-align: center;
}
.modal-default-button {
  float: right;
  background-color: transparent;
  border: 0;
}
.modal-enter {
  opacity: 0;
}
.modal-leave-active {
  opacity: 0;
}
.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modal-body,
.modal {
  color: #666 !important;
  /* font-family: "Nanumsquare", Tahoma, Geneva, Verdana, sans-serif; */
  font-size: 12px;
}

.warning {
  background-color: #e52d27;
  color: white;
  font-size: 12px;
  padding: 1rem;
}

table {
  width: 100%;
  /* border: 1px solid #f08986; */
  background-color: #f08986;
  border-collapse: collapse;
}
td {
  border: 1px solid white;
}

input[type="radio"] {
  /* display: inline-block; */
  /* float: left;*/
  display: none;
}
label {
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Hover */
label:hover {
  color: #666;
}

/* Checked */
input[type="radio"]:checked + label {
  color: white;
}
#unpeu_input {
  display: flex;
  justify-content: center;
}
</style>
